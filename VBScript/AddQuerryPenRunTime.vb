Sub CommandButton_Click()
	
	dim strselect, strfrom, strwhere
	set chart = Screen.Item("E3Chart1")
	'set the horizontal axis to show the last 10 minutes
	chart.Axes.Item("HorizontalAxis").SetRealTimePeriod 10, 1
	chart.Axes.Item("VerticalAxis").SetMinMax 0, 100
	
	if chart.Pens.Count = 0 then
		
		'Add Query
		chart.Queries.AddQuery("Query1")
		set query = chart.Item("Query1")
		query.DataSource = "DB1"
		query.Fields = "E3TimeStamp, Field1"
		query.Table = "Table1"
		query.Where = "E3TimeStamp >= #<%IniDate%>#"
		query.OrderBy = "E3TimeStamp ASC"
		'set the query variable to filter the last 10 minutes
		query.SetVariableValue "IniDate", Now-1/144
		
		'Add Pen
		set pen = chart.Pens.AddPen("Pen1")
		pen.PenType = 0 'line
		pen.Width = 3
		pen.Color = RGB(0,0,0)
		pen.DataSourceType = 2 'Realtime & Historic
		pen.QueryName = query.Name
		pen.UseTimeStamp = true
		pen.YLink = "DataServer.DemoTag1.Value"
		pen.XField = "E3TimeStamp"
		pen.YField = "Field1"
		pen.Connect()
		
	else
		
		MsgBox "Pen has already been created."
		
	end if
	
End Sub