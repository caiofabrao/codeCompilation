'/*****************************************************************
'* Subroutine that reajust the x and y values of a popup if so that
'* it does not extend beyond the edges of the screen
'*
'* param height = Double value of the popup heights in himetric
'* param width  = Double value of the popup width in himetric
'* param margin = Integer value of the margin between the popup
'*	extremes and the edges of the screen
'******************************************************************/	
Sub ScreenReajust( height As Double, width As Double, margin As Integer )
	' Get Mouse position on the screen
	Dim pos_x: pos_x = Application.GetMouseX()
	Dim pos_y: pos_y = Application.GetMouseY()
	' Viewer's resolution
	Dim resolution_x: resolution_x = Application.WindowWidth
	Dim resolution_y: resolution_y = Application.WindowHeight
	' Adjusting position
	If (HmToPx(height) + pos_y) > resolution_y Then
		pos_y = resolution_y - HmToPx(height) - MARGIN
	End If	
		If (HmToPx(width) + pos_x) > resolution_x Then
		pos_x = resolution_x - HmToPx(width) - MARGIN
	End If
	' Opens the popup with the position adjusted
	Application.DoModal "Screen_Name", "Title_Bar_Text", pos_x, pos_y, , , Arg, 0
End Sub

'/*****************************************************************
'* Function that calculates the conversion of himetric to pixels
'*
'* param hm = Double value of himetric to be converted
'* return a Integer with the calculate pixels value
'******************************************************************/	
Function HmToPx( hm As Double)
	HmToPx = hm * 96 / 2540
End Function

'/*****************************************************************
'* Function that calculates the conversion of pixels to himetric
'*
'* param px = Integer value of pixels to be converted
'* return a Double with the calculate himetric value
'******************************************************************/	
Function PxToHm( px As Integer )
	PxToHm = px * 2540 / 96
End Function