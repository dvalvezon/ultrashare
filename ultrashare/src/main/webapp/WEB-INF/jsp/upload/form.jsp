<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:template>
	<jsp:body>
	<div id="erros">
		<ul>
			<c:forEach items="${errors}" var="error">
				<li>${error.category} - ${error.message}</li>
			</c:forEach>
		</ul>
	</div>
		<form action="${pageContext.request.contextPath}/upload/upload"
			enctype="multipart/form-data" method="post">
  			Imagem: <input type="file" id="arquivo" name="arquivo" /> <input
				type="submit" />
		</form>
	</jsp:body>
</t:template>