' Variable declaration and Loop definitions
Sub Basics(arg)
	' The basics of VBScript
	Const CONSTANT = 42
	Dim variable_1 As Integer
	Dim variable_2: variable_2 = "Meaning of life"
	Dim array: array = {1, 2, 3, 4}
	Dim array_2(10)
	Dim array_3()
	Redim array_3(2,10)
	Set Object_1 = Application.GetItem("Object")
	
	If condition1 Then
		code
	ElseIf condtion2 Then
		code
	Else
		code
	End If
	
	Select Case var
		Case condition1
			code
		Case or_condition2 : or_condition3
			code
	End Select
	
	' Interation of integer
	For var = 0 To 10 [ Step 1 ]
		code
		[ If condition Then Exit For ] 'To force the exit from the For loop
	Next
	
	' Object Interator
	For Each item in Object1
		code
	Next
	
	' While condition is True, repeat the code
	' (check condtion first then execute) 
	Do While [condition]
		code
		[ If condition2 Then Exit Do ] 'To force the exit from the Do loop
	Loop

	' Do the code while condition is True
	' (execute then check condition)
	Do
		code
	Loop While [condition]
	
	' Repeat the code until condition became True
	Do Until [condition]
		code
		[ If condition2 Then Exit Do ] 'To force the exit from the Do loop
	Loop
		
	' Work exacly as Do While
	While condition
		code
	Wend
End Sub