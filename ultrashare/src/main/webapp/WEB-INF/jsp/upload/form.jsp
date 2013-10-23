<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:template>
	<jsp:body>
	<div id="erros">
		<ul>
			<c:forEach items="${errors}" var="error">
				<li>${error.category} - ${error.message}</li>
			</c:forEach>
		</ul>
	</div>
	<div class="jumbotron">
		<div class="container">
			<h1>UltraSHARE!</h1>
			<p>Share ANYTHING you like, with ANYONE you want.</p>
		</div>
    </div>
    <div class="container">
    	<div class="row">
	   		<div class="col-lg-8">
				<div class="alert alert-info">
					<form action="${pageContext.request.contextPath}/upload/upload" enctype="multipart/form-data" method="post">
						<label>Choose A File</label>
						<input type="file" style="visibility: hidden;" id="arquivo" name="arquivo" />
						<div class="input-group">
							<span class="input-group-btn">
								<button class="btn btn-default" type="button" onclick="$('#arquivo').click();">Browse</button>
							</span>
							<input type="text" id="subfile" class="form-control">
						</div>
						<br />
						<button class="btn btn-danger" type="submit">Upload it!</button>
					</form>
				</div>
	        </div>
    	</div>
    </div>
    <script src="../js/jquery-2.0.2.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
   	<script>
		$(document).ready(function() {
			$('#arquivo').change(function() {
				$('#subfile').val($(this).val());
			});
		});
	</script>
	</jsp:body>
</t:template>