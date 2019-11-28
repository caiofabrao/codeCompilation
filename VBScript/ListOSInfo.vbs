' Saves a file with information about all installed OS in the machine

Set WshShell = CreateObject("WScript.Shell")
Call Main
MsgBox "Information saved in " & Chr(34) & "OSInfo.txt" & Chr(34)

Sub Main()
	Const fsoForWriting = 2

	Dim objFSO
	Set objFSO = CreateObject("Scripting.FileSystemObject")

	'Open the text file
	Dim objTextStream
	Set objTextStream = objFSO.OpenTextFile(".\OSInfo.txt", fsoForWriting, True)

	Set dtmConvertedDate = CreateObject("WbemScripting.SWbemDateTime")
	strComputer = "."
	Set objWMIService = GetObject("winmgmts:" & "{impersonationLevel=impersonate}!\\" &_
		strComputer & "\root\cimv2")
	' Get a list with all the installed Windows OS
	Set listOperatingSystems = objWMIService.ExecQuery ("Select * from Win32_OperatingSystem")
	Dim osInfo, dtmInstallDate, licensedUsers, language

	For Each objOperatingSystem in listOperatingSystems
		dtmConvertedDate.Value = objOperatingSystem.InstallDate
		dtmInstallDate = dtmConvertedDate.GetVarDate
		licensedUsers = DecodeLincensedUser(objOperatingSystem.NumberOfLicensedUsers)
		language = DecodeLanguage(objOperatingSystem.OSLanguage)
		' TODO: List all installed language in the system using the array objOperatingSystem.MUILanguages
		'   vbNewLine & "  Available Language: " & objOperatingSystem.MUILanguages &_
		osInfo = objOperatingSystem.Caption & "- " & objOperatingSystem.OSArchitecture &_
			vbNewLine & "  Boot Device: " & objOperatingSystem.BootDevice &_
			vbNewLine & "  System Drive: " & objOperatingSystem.SystemDrive &_
			vbNewLine & "  Is Primary: " & objOperatingSystem.Primary &_
			vbNewLine & "  Build Number: " & objOperatingSystem.BuildNumber &_
			vbNewLine & "  Build Type: " & objOperatingSystem.BuildType &_
			vbNewLine & "  Build Debug: " & objOperatingSystem.Debug &_
			vbNewLine & "  Encryption Level: " & objOperatingSystem.EncryptionLevel & "-bit" &_
			vbNewLine & "  Service Pack: " & objOperatingSystem.CSDVersion &_
			vbNewLine & "  Install Date: " & dtmInstallDate &_
			vbNewLine & "  Code Set: " & objOperatingSystem.CodeSet &_
			vbNewLine & "  Country Code: " & objOperatingSystem.CountryCode &_
			vbNewLine & "  OS Language: " & language &_
			vbNewLine & "  Licensed Users: " & licensedUsers &_
			vbNewLine & "  Organization: " & objOperatingSystem.Organization &_
			vbNewLine & "  Registered User: " & objOperatingSystem.RegisteredUser &_
			vbNewLine & "  OS Product Suite: " & objOperatingSystem.OSProductSuite &_
			vbNewLine & "  OS Type: " & objOperatingSystem.OSType &_
			vbNewLine & "  Serial Number: " & objOperatingSystem.SerialNumber &_
			vbNewLine & "  OS Version: " & objOperatingSystem.Version &_ 
			vbNewLine & "  Memory Visible: " & objOperatingSystem.TotalVisibleMemorySize & " mb"
		objTextStream.WriteLine osInfo
		objTextStream.WriteLine ""
	Next

	objTextStream.Close
	Set objTextStream = Nothing
	Set objFSO = Nothing
End Sub

Function DecodeLincensedUser(code)
	Select Case code
		Case 0: DecodeLincensedUser = "Unlimited"
		Case -1: DecodeLincensedUser = "Unknown"
	End Select 
End Function

Function DecodeLanguage(code)
	Select Case code
		Case 1: DecodeLanguage = "Arabic"
		Case 4: DecodeLanguage = "Chinese (Simplified)– China"
		Case 9: DecodeLanguage = "English"
		Case 1025: DecodeLanguage = "Arabic - Saudi Arabia"
		Case 1026: DecodeLanguage = "Bulgarian"
		Case 1027: DecodeLanguage = "Catalan"
		Case 1028: DecodeLanguage = "Chinese (Traditional) - Taiwan"
		Case 1029: DecodeLanguage = "Czech"
		Case 1030: DecodeLanguage = "Danish"
		Case 1031: DecodeLanguage = "German - Germany"
		Case 1032: DecodeLanguage = "Greek"
		Case 1033: DecodeLanguage = "English - United States"
		Case 1034: DecodeLanguage = "Spanish - Traditional Sort"
		Case 1035: DecodeLanguage = "Finnish"
		Case 1036: DecodeLanguage = "French - France"
		Case 1037: DecodeLanguage = "Hebrew"
		Case 1038: DecodeLanguage = "Hungarian"
		Case 1039: DecodeLanguage = "Icelandic"
		Case 1040: DecodeLanguage = "Italian - Italy"
		Case 1041: DecodeLanguage = "Japanese"
		Case 1042: DecodeLanguage = "Korean"
		Case 1043: DecodeLanguage = "Dutch - Netherlands1044 (0x414) Norwegian - Bokmal"
		Case 1045: DecodeLanguage = "Polish"
		Case 1046: DecodeLanguage = "Portuguese - Brazil"
		Case 1047: DecodeLanguage = "Rhaeto-Romanic"
		Case 1048: DecodeLanguage = "Romanian"
		Case 1049: DecodeLanguage = "Russian"
		Case 1050: DecodeLanguage = "Croatian"
		Case 1051: DecodeLanguage = "Slovak"
		Case 1052: DecodeLanguage = "Albanian"
		Case 1053: DecodeLanguage = "Swedish"
		Case 1054: DecodeLanguage = "Thai"
		Case 1055: DecodeLanguage = "Turkish"
		Case 1056: DecodeLanguage = "Urdu"
		Case 1057: DecodeLanguage = "Indonesian"
		Case 1058: DecodeLanguage = "Ukrainian"
		Case 1059: DecodeLanguage = "Belarusian"
		Case 1060: DecodeLanguage = "Slovenian"
		Case 1061: DecodeLanguage = "Estonian"
		Case 1062: DecodeLanguage = "Latvian"
		Case 1063: DecodeLanguage = "Lithuanian"
		Case 1065: DecodeLanguage = "Persian"
		Case 1066: DecodeLanguage = "Vietnamese"
		Case 1069: DecodeLanguage = "Basque (Basque)"
		Case 1070: DecodeLanguage = "Serbian"
		Case 1071: DecodeLanguage = "Macedonian (Macedonia (FYROM))"
		Case 1072: DecodeLanguage = "Sutu"
		Case 1073: DecodeLanguage = "Tsonga"
		Case 1074: DecodeLanguage = "Tswana"
		Case 1076: DecodeLanguage = "Xhosa1077 (0x435) Zulu"
		Case 1078: DecodeLanguage = "Afrikaans"
		Case 1080: DecodeLanguage = "Faeroese"
		Case 1081: DecodeLanguage = "Hindi"
		Case 1082: DecodeLanguage = "Maltese1084 (0x43C) Scottish Gaelic (United Kingdom)"
		Case 1085: DecodeLanguage = "Yiddish"
		Case 1086: DecodeLanguage = "Malay - Malaysia"
		Case 2049: DecodeLanguage = "Arabic - Iraq"
		Case 2052: DecodeLanguage = "Chinese (Simplified) - PRC"
		Case 2055: DecodeLanguage = "German - Switzerland"
		Case 2057: DecodeLanguage = "English - United Kingdom"
		Case 2058: DecodeLanguage = "Spanish - Mexico"
		Case 2060: DecodeLanguage = "French - Belgium"
		Case 2064: DecodeLanguage = "Italian - Switzerland"
		Case 2067: DecodeLanguage = "Dutch - Belgium"
		Case 2068: DecodeLanguage = "Norwegian - Nynorsk"
		Case 2070: DecodeLanguage = "Portuguese - Portugal"
		Case 2072: DecodeLanguage = "Romanian - Moldova"
		Case 2073: DecodeLanguage = "Russian - Moldova2074 (0x81A) Serbian - Latin"
		Case 2077: DecodeLanguage = "Swedish - Finland"
		Case 3073: DecodeLanguage = "Arabic - Egypt"
		Case 3076: DecodeLanguage = "Chinese (Traditional) - Hong Kong SAR"
		Case 3079: DecodeLanguage = "German - Austria"
		Case 3081: DecodeLanguage = "English - Australia"
		Case 3082: DecodeLanguage = "Spanish - International Sort"
		Case 3084: DecodeLanguage = "French - Canada"
		Case 3098: DecodeLanguage = "Serbian - Cyrillic"
		Case 4097: DecodeLanguage = "Arabic - Libya4100 (0x1004) Chinese (Simplified) - Singapore"
		Case 4103: DecodeLanguage = "German - Luxembourg"
		Case 4105: DecodeLanguage = "English - Canada"
		Case 4106: DecodeLanguage = "Spanish - Guatemala"
		Case 4108: DecodeLanguage = "French - Switzerland"
		Case 5121: DecodeLanguage = "Arabic - Algeria"
		Case 5127: DecodeLanguage = "German - Liechtenstein"
		Case 5129: DecodeLanguage = "English - New Zealand"
		Case 5130: DecodeLanguage = "Spanish - Costa Rica"
		Case 5132: DecodeLanguage = "French - Luxembourg"
		Case 6145: DecodeLanguage = "Arabic - Morocco"
		Case 6153: DecodeLanguage = "English - Ireland"
		Case 6154: DecodeLanguage = "Spanish - Panama"
		Case 7169: DecodeLanguage = "Arabic - Tunisia"
		Case 7177: DecodeLanguage = "English - South Africa"
		Case 7178: DecodeLanguage = "Spanish - Dominican Republic"
		Case 8193: DecodeLanguage = "Arabic - Oman"
		Case 8201: DecodeLanguage = "English - Jamaica8202 (0x200A) Spanish - Venezuela"
		Case 9217: DecodeLanguage = "Arabic - Yemen"
		Case 9226: DecodeLanguage = "Spanish - Colombia"
		Case 10241: DecodeLanguage = "Arabic - Syria"
		Case 10249: DecodeLanguage = "English - Belize"
		Case 10250: DecodeLanguage = "Spanish - Peru"
		Case 11265: DecodeLanguage = "Arabic - Jordan"
		Case 11273: DecodeLanguage = "English - Trinidad"
		Case 11274: DecodeLanguage = "Spanish - Argentina"
		Case 12289: DecodeLanguage = "Arabic - Lebanon"
		Case 12298: DecodeLanguage = "Spanish - Ecuador"
		Case 13313: DecodeLanguage = "Arabic - Kuwait"
		Case 13322: DecodeLanguage = "Spanish - Chile"
		Case 14337: DecodeLanguage = "Arabic - U.A.E."
		Case 14346: DecodeLanguage = "Spanish - Uruguay"
		Case 15361: DecodeLanguage = "Arabic - Bahrain"
		Case 15370: DecodeLanguage = "Spanish - Paraguay"
		Case 16385: DecodeLanguage = "Arabic - Qatar"
		Case 16394: DecodeLanguage = "Spanish - Bolivia"
		Case 17418: DecodeLanguage = "Spanish - El Salvador"
		Case 18442: DecodeLanguage = "Spanish - Honduras"
		Case 19466: DecodeLanguage = "Spanish - Nicaragua"
		Case 20490: DecodeLanguage = "Spanish - Puerto Rico"
	End Select
End Function