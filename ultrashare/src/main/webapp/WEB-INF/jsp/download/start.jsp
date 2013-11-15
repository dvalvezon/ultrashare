<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:template>
	<jsp:body>
		<c:set var="bodyContext" value="${pageContext.request.contextPath}" />
		<div class="row" id="row0">
	   		<div class="col-lg-12">
				<div class="alert alert-warning">
					<label><b>Loading... Please wait...</b></label>
				</div>
	        </div>
    	</div>
		<div class="row" id="row1" hidden="hidden">
	   		<div class="col-lg-12">
				<div class="alert alert-info">
					<label><b>Your download will start in <span id="counter"></span> seconds!</b></label>
					<br />
					<form action="${bodyContext}/download/download" method="post">
						<input type="hidden" name="pid" value="${downloadConfirmVO.id}">
						<input type="hidden" name="pcon" value="${downloadConfirmVO.confirmationCode}">
						<button type="submit" class="btn btn-danger">Download!</button>
					</form>
				</div>
	        </div>
    	</div>
		<div class="row" id="row2" hidden="hidden">
    		<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">File Information</h3>
					</div>
					<div class="panel-body">
						<form class="form-horizontal">
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
						</form>
					</div>
				</div>
   			</div>
    	</div>
    	<script src="../js/jquery-2.0.2.min.js"></script>
    	<script>
			$(document).ready(function() {
				var remainingTime = 10;
				$('#counter').html(remainingTime);
				
				$('#row0').hide();
				$('#row1').show();
				$('#row2').show();
			});
		</script>
	</jsp:body>
</t:template>