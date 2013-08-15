<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basepage>
	<jsp:attribute name="header">
	<div id="usuario">
		<h1>Header.</h1>
	</div>
	</jsp:attribute>
	<jsp:attribute name="footer">
		<p id="copyright">Copyright Valvezon Consultoria®</p>
	  </jsp:attribute>
	<jsp:body>
		<jsp:doBody />
	</jsp:body>
</t:basepage>