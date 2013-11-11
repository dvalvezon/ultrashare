<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:template>
	<jsp:body>
    	<div class="row">
	   		<div class="col-lg-12">
				<div class="alert alert-success">
					<label><b>Thanks for your confirmation!</b> Recipients will now receive a link to download the file!</label>
					<br />
					<a href="${pageContext.request.contextPath}">
						<button type="button" class="btn btn-info">Upload Again!</button>
					</a>
				</div>
	        </div>
    	</div>
	</jsp:body>
</t:template>