' FileSystemObjectInstance.OpenTextFile(FullFilePath[, iomode[, Created]]) 
Sub FileCreate()
	Const fsoForReading = 1
	Const fsoForWriting = 2
	Const fsoForAppend  = 8

	Dim objFSO
	Set objFSO = CreateObject("Scripting.FileSystemObject")

	'Create/Open the text file
	Dim objTextStream
	Set objTextStream = objFSO.OpenTextFile(".\File.txt", fsoForWriting, True)

	'Overwrite the content
	objTextStream.WriteLine "Hello, World!"

	'Close the file and clean up
	objTextStream.Close
	Set objTextStream = Nothing
	Set objFSO = Nothing
End Sub

Sub FileAppend()
	Const fsoForReading = 1
	Const fsoForWriting = 2
	Const fsoForAppend  = 8

	Dim objFSO
	Set objFSO = CreateObject("Scripting.FileSystemObject")

	'Open the text file
	Dim objTextStream
	Set objTextStream = objFSO.OpenTextFile(".\File.txt", fsoForAppend)

	'Display the contents of the text file
	objTextStream.WriteLine "Hello, World!"

	'Close the file and clean up
	objTextStream.Close
	Set objTextStream = Nothing
	Set objFSO = Nothing
End Sub