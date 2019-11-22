' A simples and direct demonstrantion on declaring a class and how to use it
Sub Main()
	Dim n: Set n = new Note
	n.id =  1
	n.data = "Data"
End Sub

Class Note
	Public id As Integer
	Public data As String
End Class