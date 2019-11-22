'/*****************************************************************
'* Subroutine that demonstrate the expected struture to open,
'* manipulate and then closes an Excel file
'*
'* param file_original = String with the original file path
'* param file_destiny = String with the file path destination to save in a copy
'******************************************************************/					
Sub ExcelTemplate(file_original As String, file_destiny As String)
	' Opens an Excel file and store data in one of its spreadsheet
	Dim ObjExcel, workbook				
	Set ObjExcel = CreateObject("Excel.Application")
	With ObjExcel
		' Opens the file
		Set workbook = .Workbooks.Open(file_name)
		
		' Code the disere manipulation
		Call Manipulation()
		
		' Save in a new file
		.ActiveWorkbook.SaveAs file_destiny
		' End Manipulation
		.ActiveWorkbook.Close
		.Quit
	End With
	' Close Objects
	Set workbook = Nothing
	Set ObjExcel = Nothing
End Sub

'/*****************************************************************
'* Function that creates an Excel file to futher manipulation.
'*
'* return ObjExcel = Object binded to Excel
'******************************************************************/					
Function ExcelCreate( )
	' Creates a new Excel file
	Dim ObjExcel, workbook				
	Set ObjExcel = CreateObject("Excel.Application")
	Set workbook = ObjExcel.Workbooks.Add
	ExcelOpen = ObjExcel
End Function

'/*****************************************************************
'* Function that opens an Excel file to futher manipulation.
'*
'* param file_name = String with the file path to the Excel file
'* return ObjExcel Object binded to Excel
'******************************************************************/					
Function ExcelOpen(file_name As String)
	' Opens an Excel file
	Dim ObjExcel, workbook				
	Set ObjExcel = CreateObject("Excel.Application")
	Set workbook = ObjExcel.Workbooks.Open(file_name)
	ExcelOpen = ObjExcel
End Function

'/*****************************************************************
'* Function that opens an Excel file to futher manipulation. If no
'* file name is given, a new Excel file is created
'*
'* param file_name = String with the file path to the Excel file
'* return ObjExcel Object binded to Excel
'******************************************************************/					
Function ExcelOpen2(file_name As String)
	' Opens or creates an Excel file
	Dim ObjExcel, workbook				
	Set ObjExcel = CreateObject("Excel.Application")
	If file_name = "" OR file_name = Nothing Then
		Set workbook = ObjExcel.Workbooks.Add
	Else
		Set workbook = ObjExcel.Workbooks.Open(file_name)
	End If
	ExcelOpen = ObjExcel
End Function

'/*****************************************************************
'* Subroutine that add data to a spreadsheet
'* WARNING ObjExcel.WorksheetFunction.Transpose(arrData) transposes not only the array, but also date values when they are stored as actual date.
'* That is, it transposes day/month to month/day, then for 01/04, "1 of April", becomes January 4.
'*
'* param ObjExcel = Object binded to Excel
'* param sheet = Index (String or Integer) of the spreadsheet to be modify
'* param data = Data (Variant) to be add to the spreadsheet
'* param row = Integer with the starting row where the data will be add
'* param column = Integer with the starting column where the data will be add
'******************************************************************/					
Sub ExcelAddData(ObjExcel, sheet As Variant, data As Variant, row As Integer, column As Integer)
	' Adds a dataset to a given spreadsheet starting in a row and column position
	Set ObjSheet = ObjExcel.ActiveWorkbook.Worksheets(sheet)
	With ObjSheet
		Set Target = .Range(.Cells(row, col), .Cells(row + UBound(data,2), col + UBound(data,1))
		Target.Value = ObjExcel.WorksheetFunction.Transpose(data)
	Wend
End Sub

'/*****************************************************************
'* Subroutine that saves the Excel Object in the same file
'*
'* param ObjExcel = Object binded to Excel
'******************************************************************/					
Sub ExcelSave(ObjExcel)
	' Saves the Excel file
	With ObjExcel
		' Save as a new file
		.ActiveWorkbook.Save
		' End Manipulation
		.ActiveWorkbook.Close
		.Quit
	End With
End Sub

'/*****************************************************************
'* Subroutine that saves the Excel Object in a new file
'*
'* param ObjExcel = Object binded to Excel
'* param file_destiny = String with the file path of the new file
'******************************************************************/					
Sub ExcelSaveAs(ObjExcel, file_destiny As String)
	' Save a copy of the Excel file
	With ObjExcel
		' Save as a new file
		.ActiveWorkbook.SaveAs file_destiny
		' End Manipulation
		.ActiveWorkbook.Close
		.Quit
	End With
End Sub
'/*****************************************************************
'* Subroutine that opens an Excel file to the user
'*
'* param file_origin = String with the file path
'******************************************************************/					
Sub OpenExcel(file_origin As String)
	' Opens a Excel File to the user
	If MsgBox("Would you like to open the file?", vbQuestion + vbYesNo,"Open Excel") = vbYes Then
		Set ObjExcel = CreateObject("Excel.Application")
		Set workbook = ObjExcel.Workbooks.Open(file_origin)
		ObjExcel.Visible = True
	End If
End Sub
