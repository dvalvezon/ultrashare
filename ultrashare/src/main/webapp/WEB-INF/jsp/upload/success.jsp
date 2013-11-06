<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:template>
	<jsp:body>
    	<div class="row">
	   		<div class="col-lg-12">
				<div class="alert alert-success">
					<label><b>Success!</b> Your file has been sent!</label>
				</div>
	        </div>
	        <div class="col-lg-12">
				<div class="alert alert-danger">
					<label>A confirmation email has been sent to ${upload.senderEmail}.</label>
				</div>
	        </div>
    	</div>
	</jsp:body>
</t:template>