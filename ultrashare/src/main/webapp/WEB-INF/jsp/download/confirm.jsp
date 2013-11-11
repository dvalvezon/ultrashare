<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:template>
	<jsp:body>
    	<div class="row">
	   		<div class="col-lg-12">
				<div class="alert alert-info">
					<label><b>File Information</b></label>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon">File Name</span>
							<input class="form-control" readonly="readonly" value="${share.sharedUpload.fileName}" />
						</div>
						<div class="input-group">
							<span class="input-group-addon">File Size</span>
							<input class="form-control" readonly="readonly" value="${share.fileSizeAsString}" />
						</div>
						<div class="input-group">
							<span class="input-group-addon">Upload Date</span>
							<input class="form-control" readonly="readonly" value="${share.sharedUpload.creationDate}" />
						</div>
						<div class="input-group">
							<span class="input-group-addon">Uploader Name</span>
							<input class="form-control" readonly="readonly" value="${share.sharedUpload.senderName}" />
						</div>
						<div class="input-group">
							<span class="input-group-addon">Uploader Email</span>
							<input class="form-control" readonly="readonly" value="${share.sharedUpload.senderEmail}" />
						</div>
					</div>
					<form action="${pageContext.request.contextPath}/download/start" method="post">
						<input type="hidden" name="pi" value="${share.id}">
						<input type="hidden" name="pc" value="${share.confirmationCode}">
						<a href="${pageContext.request.contextPath}">
							<button type="button" class="btn btn-info">Back</button>
						</a>
						<button type="submit" class="btn btn-danger" >Download!</button>
					</form>
				</div>
	        </div>
    	</div>
	</jsp:body>
</t:template>