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
						<label class="col-sm-3 control-label">File Name</label>
						<div class="col-sm-9">
							<p class="form-control-static">${downloadConfirmVO.fileName}</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">File Size</label>
						<div class="col-sm-9">
							<p class="form-control-static">${downloadConfirmVO.fileSize}</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">Upload Date</label>
						<div class="col-sm-9">
							<p class="form-control-static">${downloadConfirmVO.uploadDate}</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">Uploader Name</label>
						<div class="col-sm-9">
							<p class="form-control-static">${downloadConfirmVO.uploaderName}</p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">Uploader Email</label>
						<div class="col-sm-9">
							<p class="form-control-static">${downloadConfirmVO.uploaderEmail}</p>
						</div>
					</div>
					<form action="${homeContext}/download/start" method="post">
						<input type="hidden" name="pi" value="${downloadConfirmVO.id}">
						<input type="hidden" name="pc" value="${downloadConfirmVO.confirmationCode}">
						<a href="${homeContext}/">
							<button type="button" class="btn btn-info">Back</button>
						</a>
						<button type="submit" class="btn btn-danger">Download!</button>
					</form>
				</div>
	        </div>
    	</div>
	</jsp:body>
</t:template>