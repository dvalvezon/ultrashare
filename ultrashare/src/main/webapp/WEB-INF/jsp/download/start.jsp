<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:template>
	<jsp:body>
    	<div class="row">
	   		<div class="col-lg-12">
				<div class="alert alert-info">
					<label><b>Your download will start shortly!</b></label>
					<br />
					<form action="${homeContext}/download/download" method="post">
						<input type="hidden" name="pid" value="${downloadConfirmVO.id}">
						<input type="hidden" name="pcon" value="${downloadConfirmVO.confirmationCode}">
						<button type="submit" class="btn btn-danger">Download!</button>
					</form>
				</div>
	        </div>
    	</div>
	</jsp:body>
</t:template>