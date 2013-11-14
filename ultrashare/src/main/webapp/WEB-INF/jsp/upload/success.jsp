<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:template>
	<jsp:body>
    	<div class="row">
	   		<div class="col-lg-12">
				<div class="alert alert-success">
					<label><b>Success!</b> Your file has been uploaded!</label>
				</div>
	        </div>
	        <div class="col-lg-12">
				<div class="alert alert-warning">
					<label>The download link will be sent to ${upload.senderEmail} shortly.</label>
				</div>
	        </div>
	        <div class="col-lg-12">
				<div class="alert alert-danger">
					<label>Please make sure the confirmation email is not being marked as Spam!</label>
				</div>
	        </div>
    	</div>
	</jsp:body>
</t:template>