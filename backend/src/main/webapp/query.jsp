<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.example.edreichua.myapplication.backend.data.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Query Result</title>
</head>
<body>
	<%
		String retStr = (String) request.getAttribute("_retStr");
		if (retStr != null) {
	%>
	<%=retStr%><br>
	<%
		}
	%>
	<p style="font-size:20px">
		<b>Exercise Entries for your device:</b>
	</p>

	<TABLE BORDER="1">
		<TR>
			<TH>id</TH>
			<TH>inputType</TH>
			<TH>activityType</TH>
			<TH>dateTime</TH>
			<TH>duration</TH>
			<TH>distance</TH>
			<TH>avgSpeed</TH>
			<TH>calorie</TH>
			<TH>climb</TH>
			<TH>heartrate</TH>
			<TH>comment</TH>
		</TR>
		<%
			ArrayList<ExerciseData> resultList = (ArrayList<ExerciseData>) request.getAttribute("result");
			if (resultList != null) {
				for (ExerciseData entry : resultList) {
		%>
					<TR>
						<TD> <%= entry.mID %></TD>
						<TD> <%= entry.mInput %></TD>
						<TD> <%= entry.mActivity %></TD>
						<TD> <%= entry.mDateTime %></TD>
						<TD> <%= entry.mDuration %></TD>
						<TD> <%= entry.mDistance %></TD>
						<TD> <%= entry.mAverageSpeed %></TD>
						<TD> <%= entry.mCalories %></TD>
						<TD> <%= entry.mClimb %></TD>
						<TD> <%= entry.mHeartRate %></TD>
						<TD> <%= entry.mComment %></TD>
						<TD> <a href="/SendDeleteMessage.do?id=<%=entry.mID%>">delete</a> </TD>
					</TR>
			<% }
			} %>
	</TABLE>

</body>
</html>