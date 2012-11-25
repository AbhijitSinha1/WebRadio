<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome To WebRadio</title>
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>\stylesheets\common.css">
<script type="text/javascript">
function submitForm()
{
	document.form1.submit();
}
</script>
</head>
<body>
<form action="<%=request.getContextPath()%>/Radio" method="post" name="form1">
Click <a href="javascript:void(0);" onclick="submitForm()">here</a> to go to radio
</form>
</body>
</html>