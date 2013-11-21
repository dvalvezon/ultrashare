<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:template>
	<jsp:body>
		<c:set var="bodyContext" value="${pageContext.request.contextPath}"/>
    	<div class="row">
    		<div class="col-lg-12">
    			<div id="shareDiv" hidden="hidden" class="alert alert-warning">
					<form id="shareForm">
						<div class="form-group">
							<label>Provide us your name and your friends mails</label>
							<div class="input-group">
								<span class="input-group-addon">Name</span>
								<input type="text" id="sharerName" name="sharerName" class="form-control" placeholder="Your Name" />
							</div>
							<br />
							<div class="input-group">
								<span class="input-group-addon">m@ils</span>
								<input type="text" id="mailInput" class="form-control" placeholder="Your friends mails  (Use ',' to input multiple emails)" />
								<span class="input-group-btn">
									<button id="addMails" class="btn btn-default" type="button">Add</button>
								</span>
								<input type="text" class="sr-only" name="sid" value="${downloadConfirmVO.id}">
								<input type="text" class="sr-only" name="scon" value="${downloadConfirmVO.confirmationCode}">
								<input type="text" class="sr-only" name="friendsMails" id="friendsMails">
							</div>
							<ul id="mailList" class="list-group">
							</ul>
						</div>
						<button id="shareSubmit" class="btn btn-warning" type="button">Share it!</button>
					</form>
				</div>
				<div id="shareConfirm" hidden="hidden" class="alert alert-success">
					<label><b>Your friends will now receive an email containing the download link.</b></label>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">File Information</h3>
					</div>
					<div class="panel-body">
						<div class="form-horizontal">
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
								<label class="col-sm-2 control-label">Share Link</label>
								<div class="col-sm-10">
									<div class="input-group" onclick="$('#shareLink').select();">
										<span class="input-group-addon">Select</span>
										<input id="shareLink" type="text" class="form-control" readonly="readonly" value="${downloadConfirmVO.downloadLink}">
									</div>
								</div>
							</div>
							<div class="form-group">
								<form class="form-horizontal" action="${bodyContext}/download/start" method="post">
									<input type="hidden" name="pi" value="${downloadConfirmVO.id}">
									<input type="hidden" name="pc" value="${downloadConfirmVO.confirmationCode}">
									<div class="col-sm-11 col-sm-offset-1">
										<div class="btn-group btn-group">
											<button id="backButton" type="button" class="btn btn-default">Back</button>
											<button id="shareButton" type="button" class="btn btn-warning">Share</button>
											<button type="submit" class="btn btn-danger">Download</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
   			</div>
    	</div>
    	<script src="../js/jquery-2.0.2.min.js"></script>
    	<script src="../js/jquery.validate.min.js"></script>
    	<script>
    		var emails = new Array();
			$(document).ready(function() {
				$('#shareButton').click(function() {
					$('#shareButton').hide();
					$('#shareDiv').show();
				});
				$('#backButton').click(function() {
					window.location.href = "${bodyContext}";
				});
				$('#addMails').click(function() {
					$.each($('#mailInput').val().split(','), function (index, value) {
						if(isValidEmail(value) && !isRepeatedEmail(value)) {
							emails.push(value);
							$('#mailList').append('<li class="list-group-item">' + value + '</li>');
						}
					});
					$('#friendsMails').val(emails.join());
					$('#mailInput').val('');
					$('#mailInput').focus();
				});
				$('#shareSubmit').click(function() {
					if($('#shareForm').valid()){
						$('#shareDiv').hide();
						$('#shareConfirm').show();
						$.ajax({
							type: 'POST',
							url: '${bodyContext}/share/share',
							data: $('#shareForm').serialize()
						});
					    return false;
					}
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
				$('#shareForm').validate({
			        rules: {
			        	sharerName: {
			                minlength: 3,
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
    	</script>
	</jsp:body>
</t:template>