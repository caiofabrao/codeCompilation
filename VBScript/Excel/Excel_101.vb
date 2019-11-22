' The basic actions to be performed when working with Excel via VBS
Sub Excel101()
	' Bind to the Excel object
	Set ObjExcel = CreateObject("Excel.Application")
	
	' Creates a new workbook
	Set workbook = ObjExcel.Workbooks.Add

	' Opens a existing file
	Set workbook = ObjExcel.Workbooks.Open(file_name)
	
	' Sheet can be passed as an index number or as a name
	' Bind to worksheet.
	Set ObjSheet = ObjExcel.ActiveWorkbook.Worksheets(sheet)
	
	' Name/Rename the worksheet
	ObjSheet.Name = "VBS_Excel_Example"
	
	'--------------------------------------------------------
	' Populating the worksheet with data
	'	 		----------------------------------
	'   ObjSheet.Cells(row, column).Value = "Value"
 	'	ObjSheet.Range(start, end).Value = Array
	'--------------------------------------------------------
	ObjSheet.Cells(1, 1).Value = "Name" ' Row 1 Column 1 (A1)
	ObjSheet.Cells(1, 2).Value = "Description" ' (B1)
	ObjSheet.Cells(1, 3).Value = "Something Else" ' (C1)
 	
	Dim array: array = {"Name", "Description", "Something Else"}
	ObjSheet.Range("A1:C1").Value = array
	ObjSheet.Range(ObjSheet.Cells(1,1),ObjSheet.Cells(1,3).Value = array
	
	'--------------------------------------------------------
	' Formating the spreadsheet
	'--------------------------------------------------------
	' Put the first row in bold
	ObjSheet.Range("A1:C1").Font.Bold = True
	' Change the font size of the first row to 14
	ObjSheet.Range("A1:C1").Font.Size = 14
	' Freeze the panes
	ObjSheet.Range("A2").Select
	ObjExcel.ActiveWindow.FreezePanes = True
	' Change column A and B to use a fixed width
	ObjExcel.Columns(1).ColumnWidth = 20
	ObjExcel.Columns(2).ColumnWidth = 30
	' Change column C to autofit
	ObjExcel.Columns(3).AutoFit()
	' Change the background colour of column A to a light yellow
	ObjExcel.Columns(1).Interior.ColorIndex = 36
	' Change the font colour of column C to blue
	ObjExcel.Columns(3).Font.ColorIndex = 5
 	
	'--------------------------------------------------------
	' Saving the spreadsheet and closing the workbook
	'--------------------------------------------------------
	' Overwrite the existing file
	ObjExcel.ActiveWorkbook.Save

	' Save as a new file
	ObjExcel.ActiveWorkbook.SaveAs file_output

	' Safe Exit
	ObjExcel.ActiveWorkbook.Close
	ObjExcel.Application.Quit
	Set ObjSheet = Nothing
	Set ObjExcel = Nothing
End Sub