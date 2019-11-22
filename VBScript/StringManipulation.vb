Function StrClean(str)
	'Remove all special characters in a string and replace spaces with underscore "_"
	Dim objRegExp, outputStr
	Set objRegExp = New Regexp
	objRegExp.IgnoreCase = True
	objRegExp.Global = True
	'Remove Special Char
	objRegExp.Pattern = "[()?*"",\\<>&#~%{}+.@:\/!;]+"
	outputStr = objRegExp.Replace(str, "")
	'Change " " to "_"
	objRegExp.Pattern = " "
	outputStr = objRegExp.Replace(outputStr, "_")
	StrClean = outputStr
End Function

Function StrSanitize(str)
	'Replaces all characters with accent in a string to normal characters
	Dim charArray, tmpChar, changeTo
	charArray = Array("á", "â", "à", "ã", "ä", "é", "ê", "è", "ë", "í", "î", "ì", "ï", "ó", "ô", "ò", "õ", "ö", "ú", "û", "ù", "ü", "ç", "ñ", "ý")
	For Each tmpChar in charArray
		Select Case tmpChar
			Case "á","â","à","ã","ä": changeTo = "a"
			Case "é","ê","è","ë": changeTo = "e"
			Case "í","î","ì","ï": changeTo = "i"
			Case "ó","ô","ò","õ","ö": changeTo = "o"
			Case "ú","û","ù","ü": changeTo = "u"
			Case "ç": changeTo = "c"
			Case "ñ": changeTo = "n"
			Case "ý": changeTo = "y"
			Case Else
			changeTo = " "
		End Select
		str = replace( str, tmpChar, changeTo )
	Next
	StrSanitize = str
End Function

Function FormatDate(strDate)
	'Format a string to date time format
	FormatDate = CStr( E3Format(CDate(strDate), "yyyy/MM/dd HH:mm:ss") )
End Function