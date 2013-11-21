<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:template>
	<jsp:body>
		<c:set var="bodyContext" value="${pageContext.request.contextPath}" />
		<div class="row" id="row0">
	   		<div class="col-lg-12">
				<div class="alert alert-warning">
					<label><strong>Loading... Please wait...</strong></label>
				</div>
	        </div>
    	</div>
		<div class="row" id="row1" hidden="hidden">
	   		<div class="col-lg-12">
				<div class="alert alert-info">
					<label><strong>Your download will start in <span id="counter"></span> seconds...</strong></label>
					<br />
					<form id="downloadForm" action="${bodyContext}/download/download" method="POST">
						<input type="hidden" name="pid" value="${downloadConfirmVO.id}">
						<input type="hidden" name="pcon" value="${downloadConfirmVO.confirmationCode}">
					</form>
				</div>
	        </div>
    	</div>
    	<div class="row" id="row3" hidden="hidden">
	   		<div class="col-lg-12">
				<div class="alert alert-success">
					<label><strong>Thanks for downloading at UltraSHARE!</strong></label>
				</div>
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
					<label><strong>Your friends will now receive an email containing the download link.</strong></label>
				</div>
				<div class="alert alert-info">
					<label>Now you can...</label>
					<div class="col-lg-12">
						<span id="shareSpan">
							<button id="share" type="button" class="btn btn-warning">SHARE</button>
							THIS File <strong>OR</strong>
						</span>
						<button id="upload" type="button" class="btn btn-danger">UPLOAD</button>
						a NEW File
					</div>
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
								<label class="col-sm-2 control-label">Share Link</label>
								<div class="col-sm-10">
									<div class="input-group" onclick="$('#shareLink').select();">
										<span class="input-group-addon">Select</span>
										<input id="shareLink" type="text" class="form-control" readonly="readonly" value="${downloadConfirmVO.downloadLink}">
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
   			</div>
    	</div>
    	<script src="../js/jquery-2.0.2.min.js"></script>
    	<script src="../js/jquery.validate.min.js"></script>
    	<script>
    		var emails = new Array();
			$(document).ready(function() {
				$('#upload').click(function() {
					window.location.href = "${bodyContext}";
				});
				$('#share').click(function() {
					$('#shareSpan').hide();
					$('#shareDiv').show();
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
				
				var remainingTime = 10;
				var counter = $('#counter'); 
				counter.html(remainingTime);
				
				timer = setInterval(function(){
					if(remainingTime == 0){
						clearInterval(timer);
						$('#downloadForm').submit();
						$('#row1').hide();
						$('#row3').show();
					} else {
						counter.html(--remainingTime);
					}
				}, 1000);
				
				$('#row0').hide();
				$('#row1').show();
				$('#row2').show();
			});
		</script>
	</jsp:body>
</t:template>