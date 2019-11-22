'/*****************************************************************
'* Function that shows a date picker when clicking on an input box
'* loading the choosen date in it
'******************************************************************/	
Sub InputBox_Click()
	Dim data_sp: data_sp = Value		'Store the current setted date
	
	'Calls the date picker with the stored date pre-selected
	If Application.ShowDatePicker(new_date, PosX, PosY, data_sp) Then
		Value = new_date	'Uptade de input box value with the new date
	End If		
End Sub