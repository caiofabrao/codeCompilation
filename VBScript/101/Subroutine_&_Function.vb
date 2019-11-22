' Subroutine are blocks of code that does not return values
' TypeOfArg is the type of the argument
' ByRef (default) means that the passed argument is beam manipulated directly (similar to pointer in C)
' ByVal means that the passed argument is copied and preserved from the manipulation
Sub NameSub([ arg1 [ As TypeOfArg ] ] [, ByRef arg2 As SomeType] [, ByVal arg3 As SomeType ])
	coding
End Sub 

' Functions are Subroutines that return values at the end of it execution
' Functions have a data type according to what it returns
' Arguments can be passed iquali as in Subroutines
Function NameFunction ( [ args ] ) As TypeOfReturn
	coding
	NameFunction = var_return
End Function

' Calling Subroutines and Functions
Sub Calling()
	'Two ways to call Subroutines
	SubRout1					'direct (no arguments required)
	SubRout2 arg1, arg2			'direct (with arguments)
	Call SubRout1()				'using the call function (no arguments required)
	Call SubRout2(arg1, arg2)	'using the call function (with arguments)
	
	'One way to call Functions
	var = Func1(arg1, arg2)
End sub