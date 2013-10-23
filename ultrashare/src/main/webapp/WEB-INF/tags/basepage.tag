<%@tag language="java" pageEncoding="ISO-8859-1"%>
<%@attribute name="header" fragment="true"%>
<%@attribute name="footer" fragment="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>UltraSHARE</title>
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body style="zoom: 1;">
	<div id="header">
		<jsp:invoke fragment="header" />
	</div>
	<div id="body">
		<jsp:doBody />
	</div>
	<div id="footer">
		<jsp:invoke fragment="footer" />
	</div>
</body>
</html>