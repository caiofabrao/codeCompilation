'----Constant------Value--------Description 
' vbOKOnly			 0		Display OK button only. 
' vbOKCancel		 1		Display OK and Cancel buttons. 
' vbAbortRetryIgnore 2		Display Abort, Retry, and Ignore buttons. 
' vbYesNoCancel		 3		Display Yes, No, and Cancel buttons. 
' vbYesNo			 4		Display Yes and No buttons. 
' vbRetryCancel		 5		Display Retry and Cancel buttons. 
'
' vbCritical 		 16		Display Critical Message icon.  
' vbQuestion 		 32		Display Warning Query icon. 
' vbExclamation 	 48		Display Warning Message icon. 
' vbInformation 	 64		Display Information Message icon. 
'
' vbDefaultButton1	 0		First button is default. 
' vbDefaultButton2	 256	Second button is default. 
' vbDefaultButton3	 512	Third button is default. 
' vbDefaultButton4	 768	Fourth button is default. 
'
' vbApplicationModal 0		Application modal; the user must respond to
'							the message box before continuing work in 
'							the current application. 
' vbSystemModal		 4096	System modal; all applications are suspended
'							until the user responds to the message box. 
'------------------------------------------------------------------------

' Direct use
MsgBox "Some simples string"

' Customization of the dialog box
MsgBox("Body message",Value,"Title")

' Treating user response
If MsgBox("Work complete" + vbNewLine + "Would you like to procede?",_ 
	vbQuestion + vbYesNo,"Success") = vbYes Then
	code
End If

' Constants to better display the message
vbNewLine ' create a new line in the string
vbCr      ' is the carriage return (return to line beginning)
vbLf      ' is the line feed (go to next line)
vbCrLf    ' is the carriage return / line feed (similar to pressing Enter)
Chr(34)   ' double quotes "
