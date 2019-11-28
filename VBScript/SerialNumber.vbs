' Save the Serial Number Key of the Windows OS to a file

Set WshShell = CreateObject("WScript.Shell")
SaveTo( ConvertToKey(WshShell.RegRead("HKLM\SOFTWARE\Microsoft\Windows NT\CurrentVersion\DigitalProductId")) )

Function ConvertToKey(Key)
	Const KeyOffset = 52
	i = 28
	Chars = "BCDFGHJKMPQRTVWXY2346789"
	Do
		Cur = 0
		x = 14
		Do
			Cur = Cur * 256
			Cur = Key(x + KeyOffset) + Cur
			Key(x + KeyOffset) = (Cur \ 24) And 255
			Cur = Cur Mod 24
			x = x - 1
		Loop While x >= 0
		i = i - 1
		KeyOutput = Mid(Chars, Cur + 1, 1) & KeyOutput
		If (((29 - i) Mod 6) = 0) And (i <> - 1) Then
			i = i - 1 
			KeyOutput = "-" & KeyOutput
		End If
	Loop While i >= 0
	ConvertToKey = KeyOutput
End Function

Sub SaveTo(str)
	Const fsoForWriting = 2
	Dim objFSO
	Set objFSO = CreateObject("Scripting.FileSystemObject")
	Dim objTextStream
	Set objTextStream = objFSO.OpenTextFile(".\SerialWin.txt", fsoForWriting, True)
	objTextStream.WriteLine str
	objTextStream.Close
	Set objTextStream = Nothing
	Set objFSO = Nothing
	MsgBox "Serial Saved as SerialWin.txt"
End Sub