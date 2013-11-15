	<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:template>
	<jsp:body>
		<c:set var="bodyContext" value="${pageContext.request.contextPath}"/>
    	<div class="row">
    		<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">File Information</h3>
					</div>
					<div class="panel-body">
						<form class="form-horizontal" action="${bodyContext}/download/start" method="post">
							<div class="form-group">
								<label class="col-sm-2 control-label">File Name</label>
								<div class="col-sm-10">
									<p class="form-control-static">${downloadConfirmVO.fileName}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">File Size</label>
								<div class="col-sm-10">
									<p class="form-control-static">${downloadConfirmVO.fileSize}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">Upload Date</label>
								<div class="col-sm-10">
									<p class="form-control-static">${downloadConfirmVO.uploadDate}</p>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">Link</label>
								<div class="col-sm-10">
									<p class="form-control-static">${downloadConfirmVO.downloadLink}</p>
								</div>
							</div>
							<input type="hidden" name="pi" value="${downloadConfirmVO.id}">
							<input type="hidden" name="pc" value="${downloadConfirmVO.confirmationCode}">
							<div class="form-group">
								<div class="col-sm-11 col-sm-offset-1">
									<div class="btn-group btn-group">
										<button type="button" class="btn btn-default" onclick="window.location='${bodyContext}'">Back</button>
										<button type="submit" class="btn btn-info">Download</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
   			</div>
    	</div>
	</jsp:body>
</t:template>