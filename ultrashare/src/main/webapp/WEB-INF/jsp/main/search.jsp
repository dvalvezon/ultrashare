<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:template>
	<jsp:body>
		<c:set var="bodyContext" value="${pageContext.request.contextPath}"/>
    	<div class="row">
    		<div class="col-lg-12">
    			<c:forEach items="${searchVOList}" var="item" varStatus="num">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">#${num.toString()} - ${item.fileName}</h3>
						</div>
						<div class="panel-body">
							<div class="form-horizontal">
								<div class="form-group">
									<label class="col-sm-2 control-label">File Name</label>
									<div class="col-sm-10">
										<p class="form-control-static">${item.fileName}</p>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">File Size</label>
									<div class="col-sm-10">
										<p class="form-control-static">${item.fileSize}</p>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Upload Date</label>
									<div class="col-sm-10">
										<p class="form-control-static">${item.creationDate}</p>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Share Link</label>
									<div class="col-sm-10">
										<div class="input-group" onclick="$('#shareLink${num}').select();">
											<span class="input-group-addon">Select</span>
											<input id="shareLink${num}" type="text" class="form-control" readonly="readonly" value="${item.link}">
										</div>
									</div>
								</div>
								<div class="col-sm-11 col-sm-offset-1">
										<button type="button" class="btn btn-danger">Download</button>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
  			</div>
    	</div>
    	<script src="../js/jquery-2.0.2.min.js"></script>
	</jsp:body>
</t:template>