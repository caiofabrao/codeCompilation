'/*****************************************************************
'* Working example of the use of Excel to store information about a
'* trial.
'*
'* Project: Electrolux - Camara de Bocais 1
'* Script location: Screen Calor_Balanceado, CommandButton Parar 
'* Author: Caio Fabrao
'*
'* Description: The data to be stored in the excel file first is 
'* recovered from the database using the trial id. After that, a 
'* Excel template file is opened and populate first with the trial
'* information (stored in internal tags) and then with the trial data
'* recovered from the DB. It is then saved in a new file, named with 
'* the trial name and type, and prompt the user to open the finalized
'* file.
'******************************************************************/	
Sub Excel_WorkingExample()
	'Recupera do banco de dados o ensaio realizado
	Dim records
	Dim id_teste: id_teste = Application.GetObject("Dados_Balanceado.Id_Teste").Value
	Set Query = Screen.Item("Consultar_Ensaio")
	Query.SetVariableValue "IdTeste", id_teste
	Query.SQL = "EXEC BALANCEADO_BATELADA_HIST_CONSULTAR <%IdTeste%>"
	Set records = Query.GetADORecordset()
	
	'Aquisição dos dados do ensaio
	Dim table_content: table_content = records.GetRows
	Dim row_size: row_size = records.RecordCount
	'Necessário pois Transpose inverte dia e mês
	For shift = 0 To UBound(table_content,2)
		table_content(0,shift) = E3Format(table_content(0,shift),"MM/dd/yyyy HH:mm:ss")
	Next
	
	'As linhas abaixo montam o cabeçalho
	Dim table_header() 
	Dim header_size: header_size = records.Fields.Count
	ReDim table_header(header_size) 
	For i = 0 To header_size-1
		table_header(i) = records.Fields(i).Name
	Next

	'Abre o arquivo Template
	Const TEMPLATE_PATH = "C:\Supervisorio_Elipse_E3\Electrolux\Calorimetro\Aplicativo\Templates\"
	Const FILE_PATH = "C:\RESULTADOS\BALANCEADO\"
	Dim ObjExcel, workbook
	Dim test_type: test_type = TestType(Application.GetObject("Dados_Balanceado.Tipo_Teste").Value)
	Dim test_name: test_name = Application.GetObject("Dados_Balanceado.Nome_Teste").Value
	Dim template_name: template_name = "Template_" & test_type & "_Balanceado"
	Dim file_name: file_name = test_type & "_Balanceado_" & test_name
				
	Set ObjExcel = CreateObject("Excel.Application")
	With ObjExcel
		Set workbook = .Workbooks.Open(TEMPLATE_PATH & template_name)
		'Exporta as informações do Registro 
		Dim sheet: sheet = "Info"
		Set ObjSheet = .ActiveWorkbook.Worksheets(sheet)
		With ObjSheet
			'Teste (B1)
			.Cells(1,2).Value = Application.GetObject("Dados_Balanceado.Nome_Teste").Value
			'Tipo (B2)
			.Cells(2,2).Value = Application.GetObject("Dados_Balanceado.Tipo_Teste").Value
			'Modelo (B3)
			.Cells(3,2).Value = Application.GetObject("Dados_Balanceado.Amostra.Modelo").Value
			'Numero de Serie (B4)
			.Cells(4,2).Value = Application.GetObject("Dados_Balanceado.Amostra.NSerie").Value
			'Compressor (B5)
			.Cells(5,2).Value = Application.GetObject("Dados_Balanceado.Amostra.Comp").Value
			'Capilar (B6)
			.Cells(6,2).Value = Application.GetObject("Dados_Balanceado.Amostra.Cap").Value
			'Carga (B7)
			.Cells(7,2).Value = Application.GetObject("Dados_Balanceado.Amostra.Carga").Value
			'Skip 1 linha
			'Obs (A9:B13)
			For i = 1 To 5
				.Cells(8+i,1).Value = Application.GetObject("Dados_Balanceado.Amostra.TitOutro" & CStr(i)).Value
				.Cells(8+i,2).Value = Application.GetObject("Dados_Balanceado.Amostra.Outro" & CStr(i)).Value
			Next
		End With

		sheet = "hist_balanceado"
		Set ObjSheet = .ActiveWorkbook.Worksheets(sheet)
		Set Eventos = Application.GetObject("Dados_Balanceado.Eventos")
		Dim col_shift
		With ObjSheet
			'Exporta os dados do ensaio
			Dim row_init: row_init = 5 
			Dim col_init: col_init = 2
			'Preenche a linha de cabeçalho
			Dim target: Set target = .Range(.Cells(row_init, col_init), .Cells(row_init, col_init+header_size-1))
			target.Value = table_header
			'Incrementa row para a área de dados
			row_init = row_init + 1
			'Preenche o conteudo
			'WARNING ObjExcel.WorksheetFunction.Transpose(arrDate) transposes not only the array, but also date values when they are stored as actual date.
			'That is, it transposes day/month to month/day, then for 01/04, 1 of April, becames Januari 4.
			Set target = .Range(.Cells(row_init, col_init), .Cells(row_init+row_size-1, col_init+header_size-1))
			target.Value = ObjExcel.WorksheetFunction.Transpose(table_content)
			
			'Adiciona os Evetos cadastrados
			'Adiciona ao cabeçalho a coluna "Eventos"
			.Cells(row_init-1,col_init+header_size).Value = "Eventos"				
			For Each Evento in Eventos
				'Armazena a coluna da pena em que ocorreu o evento
				For col = col_init to col_init+header_size
					If .Cells(row_init-1,col).Value = Evento.pen Then
						Exit For
					End If
				Next
				'Armazena a linha em que ocorreu o evento, Pinta a célula da pena e adiciona a descrição
				For row = row_init to row_init+row_size-2
					If FormatDateTime(.Cells(row,2).Value,0) <= FormatDateTime(Evento.dateTime,0) And _
					   FormatDateTime(.Cells(row+1,2).Value,0) > FormatDateTime(Evento.dateTime,0) Then
						.Cells(row,col).Interior.ColorIndex = 6
						col_shift = 0
						Do While .Cells(row,col_init+header_size+col_shift).Value <> "" 
							col_shift = col_shift+1
						Loop
						.Cells(row,col_init+header_size+col_shift).Value = Evento.description
						Exit For
					End If
				Next
			Next
		End With
		workbook.SaveAs FILE_PATH & file_name
		Set workbook = Nothing
		.Quit
	End With
	Set ObjExcel = Nothing
	
	For Each Evento in Eventos
		Eventos.DeleteObject(Evento.Name)
	Next
	
	If MsgBox("Deseja abir o arquivo?", vbQuestion + vbYesNo,"Compilação Finalizada") = vbYes Then
		Set ObjExcel = CreateObject("Excel.Application")
		Set workbook = ObjExcel.Workbooks.Open(file_path & file_name)
		ObjExcel.Visible = True
	End If
End Sub