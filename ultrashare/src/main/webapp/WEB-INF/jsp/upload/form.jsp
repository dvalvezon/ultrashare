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
			<h1>UltraSHARE</h1>
			<p>Share ANYTHING you like, with ANYONE you want.</p>
		</div>
    </div>
    <div class="container">
	    <form action="${pageContext.request.contextPath}/upload/upload" enctype="multipart/form-data" method="post">
	    	<div class="row" id="row1">
		   		<div class="col-lg-8">
					<div class="alert alert-info">
						<label>Choose A File</label>
						<input type="file" style="visibility: hidden;" id="arquivo" name="arquivo" />
						<div class="input-group">
							<span class="input-group-btn">
								<button class="btn btn-default" type="button" onclick="$('#arquivo').click();">Browse</button>
							</span>
							<input type="text" disabled="disabled" id="subfile" class="form-control">
						</div>
						<br />
						<button class="btn btn-info" type="button" onclick="$('#row1').hide();$('#row2').show();">Next!</button>
					</div>
		        </div>
	    	</div>
	    	<div class="row" id="row2" hidden="true">
		   		<div class="col-lg-8">
					<div class="alert alert-info">
						<label>Provide us your Name and eMail</label>
						<div class="input-group">
							<span class="input-group-addon">Name</span>
							<input type="text" name="userName" class="form-control" placeholder="Your Name">
						</div>
						<br />
						<div class="input-group">
							<span class="input-group-addon">m@il</span>
							<input type="text" name="userMail" class="form-control" placeholder="Your eMail">
						</div>
						<br />
						<button class="btn btn-info" type="button" onclick="$('#row2').hide();$('#row3').show();">Next!</button>
					</div>
		        </div>
	    	</div>
	    	<div class="row" id="row3" hidden="true">
		   		<div class="col-lg-8">
					<div class="alert alert-info">
						<label>Now just input your friend's eMail</label>
						<input type="hidden" name="friendsMails" id="friendsMails">
						<div class="input-group">
							<span class="input-group-addon">m@ails</span>
							<input type="text" id="mailInput" class="form-control" placeholder="Use ',' to input multiple emails">
							<span class="input-group-btn">
								<button id="addMails" class="btn btn-default" type="button">Add</button>
							</span>
						</div>
						<br />
						<ul id="mailList" class="list-group">
						</ul>
						<br />
						<button class="btn btn-danger" type="submit">Send it!</button>
					</div>
		        </div>
	    	</div>
    	</form>
    </div>
    <script src="../js/jquery-2.0.2.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
   	<script>
		$(document).ready(function() {
			$('#arquivo').change(function() {
				$('#subfile').val($(this).val());
			});
			$('#addMails').click(function() {
				$.each($('#mailInput').val().split(','), function (index, value) {
					if($('#friendsMails').val() == ""){
						$('#friendsMails').val(value);
					} else {
						$('#friendsMails').val($('#friendsMails').val() + ',' + value);
					}
					$('#mailList').append('<li class="list-group-item">' + value + '</li>');
				});
				$('#mailInput').val('');
			});
		});
	</script>
	</jsp:body>
</t:template>