<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:template>
	<jsp:body>
		<c:set var="bodyContext" value="${pageContext.request.contextPath}"/>
    	<div class="row">
    		<div class="col-lg-12">
    			<t:uploadResultsPanel searchVOList="${searchVOList}" panelTitle="Search Results (${searchVOList.size()})" />
<!--     			<div class="panel panel-default"> -->
<%--     				<div class="panel-heading">Search Results (${searchVOList.size()})</div> --%>
<%--     				<c:if test="${searchVOList.isEmpty()}"> --%>
<!--     					<div class="panel-body"> -->
<!-- 							<p>No file(s) found...</p> -->
<!-- 						</div> -->
<%--     				</c:if> --%>
<%--     				<c:if test="${!searchVOList.isEmpty()}"> --%>
<%--     					<table class="table table-striped"> --%>
<%--     						<thead> --%>
<%--     							<tr> --%>
<%--     								<th>#</th> --%>
<%--     								<th>File Name</th> --%>
<%--     								<th>File Size</th> --%>
<%--     								<th>Upload Date</th> --%>
<%--     								<th></th> --%>
<%--     							</tr> --%>
<%--     						</thead> --%>
<%--     						<tbody> --%>
<%--     							<c:forEach items="${searchVOList}" var="item" varStatus="num"> --%>
<%-- 	    							<tr> --%>
<%-- 	    								<td>${num.index + 1}</td> --%>
<%-- 	    								<td>${item.fileName}</td> --%>
<%-- 	    								<td>${item.fileSize}</td> --%>
<%-- 	    								<td>${item.creationDate}</td> --%>
<%-- 	    								<td> --%>
<!-- 	    									<button type="button" class="btn btn-xs btn-danger"><span class="glyphicon glyphicon-download-alt"></span></button> -->
<%-- 	    								</td> --%>
<%-- 	    							</tr> --%>
<%-- 	    						</c:forEach> --%>
<%--     						</tbody> --%>
<%--     					</table> --%>
<%--     				</c:if> --%>
<!--     			</div> -->
    		
<%--     			<c:forEach items="${searchVOList}" var="item" varStatus="num"> --%>
<!--     				<div class="list-group"> -->
<!--     					<div class="list-group-item"> -->
<%-- 	    					<h4 class="list-group-item-heading">#${num.index + 1} - ${item.fileName}</h4> --%>
<%-- 	    					<p class="list-group-item-text">Size: ${item.fileSize}</p> --%>
<!-- 	    				</div> -->
<!--     				</div> -->
<!-- 					<div class="panel panel-default"> -->
<!-- 						<div class="panel-heading"> -->
<%-- 							<h3 class="panel-title">#${num.index + 1} - ${item.fileName}</h3> --%>
<!-- 						</div> -->
<!-- 						<div class="panel-body"> -->
<!-- 							<div class="form-horizontal"> -->
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-sm-2 control-label">File Name</label> -->
<!-- 									<div class="col-sm-10"> -->
<%-- 										<p class="form-control-static">${item.fileName}</p> --%>
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-sm-2 control-label">File Size</label> -->
<!-- 									<div class="col-sm-10"> -->
<%-- 										<p class="form-control-static">${item.fileSize}</p> --%>
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-sm-2 control-label">Upload Date</label> -->
<!-- 									<div class="col-sm-10"> -->
<%-- 										<p class="form-control-static">${item.creationDate}</p> --%>
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 								<div class="form-group"> -->
<!-- 									<label class="col-sm-2 control-label">Share Link</label> -->
<!-- 									<div class="col-sm-10"> -->
<%-- 										<div class="input-group" onclick="$('#shareLink${num.index}').select();"> --%>
<!-- 											<span class="input-group-addon">Select</span> -->
<%-- 											<input id="shareLink${num.index}" type="text" class="form-control" readonly="readonly" value="${item.link}"> --%>
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 								</div> -->
<!-- 								<div class="col-sm-11 col-sm-offset-1"> -->
<!-- 										<button type="button" class="btn btn-danger">Download</button> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<%-- 				</c:forEach> --%>
  			</div>
    	</div>
    	<script src="../js/jquery-2.0.2.min.js"></script>
	</jsp:body>
</t:template>