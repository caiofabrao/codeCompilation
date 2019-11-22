'/*****************************************************************
'* Subroutine that add an object to a given folder. If the name passed
'* is the same of a existing object in the folder, the new object receive
'* a sequential number at the end of the name to differentiate it.
'*
'* param folder_path = String with the path location of the destination folder
'* param obj_name = String with the name of the new object
'* param obj_type = String with the Object Type
'******************************************************************/	
Sub AddObject(folder_path As String, obj_name As String, obj_type As String)
	' Add and name an object to a given folder
	Set Folder = Application.GetObject(folder_path)
	Set Obj = Folder.AddObject(obj_name, True, obj_type)
End Sub

'/*****************************************************************
'* Subroutine that deletes an object from a given folder.
'*
'* param folder_path = String with the path location of the destination folder
'* param obj_name = String with the name of the object to be deleted
'******************************************************************/	
Sub DeleteObject(folder_path As String, object_name As String)
	' Delete an object from a given folder
	Set Folder = Application.GetObject(folder_path)
	Folder.DeleteObject(object_name)
End Sub

'/*****************************************************************
'* Subroutine that delete all objects from a given folder.
'*
'* param folder_path = String with the path location of the destination folder
'******************************************************************/	
Sub DeleteAllObject(folder_path As String)
	' Delete all objects from a given folder
	Set Folder = Application.GetObject(folder_path)
	For Each Obj in Folder
		Folder.DeleteObject(Obj.Name)
	Next
End Sub