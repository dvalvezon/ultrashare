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
	    <div class="container">
		    <form id="uploadForm" action="${pageContext.request.contextPath}/upload/upload" enctype="multipart/form-data" method="post">
		    	<div class="row" id="row1">
			   		<div class="col-lg-8">
						<div class="alert alert-info">
							<label>Choose A File</label>
							<input type="file" style="visibility: hidden;" id="arquivo" name="arquivo" />
							<div class="input-group">
								<span class="input-group-btn">
									<button class="btn btn-default" type="button" onclick="$('#arquivo').click();">Browse</button>
								</span>
								<input type="text" disabled="disabled" id="subfile" class="form-control" />
							</div>
							<br />
							<button id="nextRow1" class="btn btn-info" type="button">Next!</button>
						</div>
			        </div>
		    	</div>
		    	<div class="row" id="row2">
			   		<div class="col-lg-8">
						<div class="alert alert-info">
							<label>Provide us your Name and eMail</label>
							<div class="input-group">
								<span class="input-group-addon">Name</span>
								<input type="text" name="userName" class="form-control" placeholder="Your Name" />
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon">m@il</span>
								<input id="userMail" type="email" name="userMail" class="form-control" placeholder="Your eMail" required data-validation-required-message="Please enter your eMail" />
							</div>
							<br />
							<button id="nextRow2" class="btn btn-info" type="button">Next!</button>
						</div>
			        </div>
		    	</div>
		    	<div class="row" id="row3">
			   		<div class="col-lg-8">
						<div class="alert alert-info">
							<label>Now just input your friend's eMail</label>
							<input type="hidden" name="friendsMails" id="friendsMails">
							<div class="input-group">
								<span class="input-group-addon">m@ails</span>
								<input type="text" id="mailInput" class="form-control" placeholder="Use ',' to input multiple emails" />
								<span class="input-group-btn">
									<button id="addMails" class="btn btn-default" type="button">Add</button>
								</span>
							</div>
							<br />
							<ul id="mailList" class="list-group">
							</ul>
							<br />
							<button id="nextRow3" class="btn btn-danger" type="button">Send it!</button>
						</div>
			        </div>
		    	</div>
		    	<div class="row" id="row4">
			   		<div class="col-lg-8">
						<div class="alert alert-warning">
							<label>Hold on! Your file is being uploaded..</label>
						</div>
			        </div>
		    	</div>
	    	</form>
	    </div>
	    <script src="../js/jquery-2.0.2.min.js"></script>
		<script src="../js/bootstrap.min.js"></script>
<!-- 		<script src="../js/jqBootstrapValidation.js"></script> -->
	   	<script>
			$(document).ready(function() {
				$('#row1').hide();
// 				$('#row2').hide();
				$('#row3').hide();
				$('#row4').hide();
				$('#arquivo').change(function() {
					$('#subfile').val($(this).val());
				});
				$('#nextRow1').click(function() {
					$('#row1').hide();
					$('#row2').show();
				});
				$('#nextRow2').click(function() {
					if(isValidEmail($('#userMail').val())) {
						$('#row2').hide();
						$('#row3').show();	
					} 
					else {
// 						$('#userMail').next('.help-inline').show();
						alert('Email Errado Caralho!!!');
					}
				});
				$('#nextRow3').click(function() {
					$('#row3').hide();
					$('#row4').show();
					$('#uploadForm').submit();
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
			function isValidEmail(email) {
				var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
				return regex.test(email);
			}
		</script>
	</jsp:body>
</t:template>