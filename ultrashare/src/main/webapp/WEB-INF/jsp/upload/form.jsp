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
		    <form id="uploadForm" role="form" action="${pageContext.request.contextPath}/upload/upload" enctype="multipart/form-data" method="post">
		    	<div class="row">
		    		<div class="col-lg-9">
		    			<div class="row" id="row0">
					   		<div class="col-lg-12">
								<div class="alert alert-warning">
									<label>Loading...</label>
								</div>
					        </div>
				    	</div>
		    			<div class="row" hidden="hidden" id="row1">
					   		<div class="col-lg-12">
								<div class="alert alert-info">
									<label>Choose A File</label>
									<div class="form-group">
										<div class="input-group">
											<span class="input-group-btn">
												<button class="form-control" type="button" onclick="$('#arquivo').click();">Browse</button>
											</span>
											<input type="text" disabled="disabled" id="subfile" name="subfile" class="form-control" />
										</div>
										<input class="sr-only" type="file" id="arquivo" name="arquivo" />
									</div>
									<button id="nextRow1" class="btn btn-info" type="button">Next!</button>
								</div>
					        </div>
				    	</div>
				    	<div class="row" hidden="hidden" id="row2">
					   		<div class="col-lg-12">
								<div class="alert alert-info">
									<div class="form-group">
										<label>Provide us your Name and eMail</label>
										<div class="input-group">
											<span class="input-group-addon">Name</span>
											<input type="text" name="userName" class="form-control" placeholder="Your Name" />
										</div>
									</div>
									<div class="form-group">
										<div class="input-group">
											<span class="input-group-addon">m@il</span>
											<input id="userMail" type="email" name="userMail" class="form-control" placeholder="Your eMail" required data-validation-required-message="Please enter your eMail" />
										</div>
									</div>
									<button id="nextRow2" class="btn btn-info" type="button">Next!</button>
								</div>
					        </div>
				    	</div>
				    	<div class="row" hidden="hidden" id="row3">
					   		<div class="col-lg-12">
								<div class="alert alert-info">
									<label>Now just input your friend's eMail</label>
									<div class="form-group">
										<div class="input-group">
											<span class="input-group-addon">m@ails</span>
											<input type="text" id="mailInput" class="form-control" placeholder="Use ',' to input multiple emails" />
											<span class="input-group-btn">
												<button id="addMails" class="btn btn-default" type="button">Add</button>
											</span>
											<input type="text" class="sr-only" name="friendsMails" id="friendsMails">
										</div>
									</div>
									<ul id="mailList" class="list-group">
									</ul>
									<br />
									<button id="nextRow3" class="btn btn-danger" type="button">Send it!</button>
								</div>
					        </div>
				    	</div>
				    	<div class="row" hidden="hidden" id="row4">
					   		<div class="col-lg-12">
								<div class="alert alert-warning">
									<label>Hold on! Your file is being uploaded..</label>
								</div>
					        </div>
				    	</div>
		    		</div>
		    		<div class="col-lg-3">
		    			<div class="alert alert-warning">
							<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
							<!-- Ultrashare_UploadForm -->
							<ins class="adsbygoogle"
							     style="display:inline-block;width:250px;height:250px"
							     data-ad-client="ca-pub-3660879360573027"
							     data-ad-slot="3711213734"></ins>
							<script>
							(adsbygoogle = window.adsbygoogle || []).push({});
							</script>
		    			</div>
		    		</div>
		    	</div>
	    	</form>
	    </div>
	    <script src="../js/jquery-2.0.2.min.js"></script>
	    <script src="../js/jquery.validate.min.js"></script>
	    <script src="../js/additional-methods.min.js"></script>
		<script src="../js/bootstrap.min.js"></script>
<!-- 		<script src="../js/jqBootstrapValidation.js"></script> -->
	   	<script>
	   		var emails = new Array();
			$(document).ready(function() {
				$('#row0').hide();
				$('#row1').show();
// 				$('#row2').hide();
// 				$('#row3').hide();
// 				$('#row4').hide();
				$('#arquivo').change(function() {
					$('#subfile').val($(this).val());
				});
				$('#nextRow1').click(function() {
					if($('#uploadForm').valid()){
						$('#row1').hide();
						$('#row2').show();
					}
				});
				$('#nextRow2').click(function() {
					if($('#uploadForm').valid()){
						$('#row2').hide();
						$('#row3').show();	
					} 
				});
				$('#nextRow3').click(function() {
					$('#friendsMails').val(emails.join());
					if($('#uploadForm').valid()){
						$('#row3').hide();
						$('#row4').show();
						$('#uploadForm').submit();
					} else {
						$('#friendsMails').val('');
					}
				});
				$('#addMails').click(function() {
					$.each($('#mailInput').val().split(','), function (index, value) {
						if(isValidEmail(value) && !isRepeatedEmail(value)) {
							emails.push(value);
							$('#mailList').append('<li class="list-group-item">' + value + '</li>');
						}
					});
					$('#mailInput').val('');
				});
				$('#uploadForm').validate({
			        rules: {
			        	arquivo: {
			        		required: true
			        	},
			        	userName: {
			                minlength: 3,
			                required: true
			            },
			            userMail: {
			                minlength: 6,
			                required: true
			            },
			            friendsMails: {
			            	required: true
			            }
			        },
			        highlight: function(element) {
			            $(element).closest('.form-group').addClass('has-error');
			        },
			        unhighlight: function(element) {
			            $(element).closest('.form-group').removeClass('has-error');
			        },
			        errorElement: 'span',
			        errorClass: 'help-block',
			        errorPlacement: function(error, element) {
			            if(element.parent('.input-group').length) {
			                error.insertAfter(element.parent());
			            } else {
			                error.insertAfter(element);
			            }
			        }
			    });
			});
			function isValidEmail(email) {
				var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
				return regex.test(email);
			}
			function isRepeatedEmail(value) {
				var lenght = emails.length;
				for(var i = 0; i < lenght; i++){
					if(emails[i] == value) {
						return true;
					}
				}
				return false;
			}
		</script>
	</jsp:body>
</t:template>