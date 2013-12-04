<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="searchVOList" required="true" type="java.util.List" %>

<div class="panel panel-default">
   	<div class="panel-heading">Search Results (${searchVOList.size()})</div>
	<c:if test="${searchVOList.isEmpty()}">
		<div class="panel-body">
			<p>No file(s) found...</p>
		</div>
	</c:if>
	<c:if test="${!searchVOList.isEmpty()}">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>#</th>
					<th>File Name</th>
					<th>File Size</th>
					<th>Upload Date</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${searchVOList}" var="item" varStatus="num">
					<tr>
						<td>${num.index + 1}</td>
						<td>${item.fileName}</td>
						<td>${item.fileSize}</td>
						<td>${item.creationDate}</td>
						<td>
							<button type="button" class="btn btn-xs btn-danger"><span class="glyphicon glyphicon-download-alt"></span></button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>