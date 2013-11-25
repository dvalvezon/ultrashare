<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:template>
	<jsp:body>
		<c:set var="bodyContext" value="${pageContext.request.contextPath}"/>
		<form id="uploadForm" role="form" action="${bodyContext}/upload/upload" enctype="multipart/form-data" method="post">
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
						<label>Choose a file to SHARE</label>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-btn">
									<button class="form-control" type="button" onclick="$('#userFile').click();">Browse</button>
								</span>
								<input type="text" disabled="disabled" id="subfile" name="subfile" class="form-control" placeholder="Current file size limit is 10 GB" />
							</div>
							<input class="sr-only" type="file" id="userFile" name="userFile" />
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
								<input type="text" id="userName" name="userName" class="form-control" placeholder="Your Name" />
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon">m@il</span>
								<input id="userMail" type="email" name="userMail" class="form-control" placeholder="Your eMail" required data-validation-required-message="Please enter your eMail" />
							</div>
						</div>
						<button id="nextRow2" class="btn btn-danger" type="button">Send it!</button>
					</div>
		        </div>
	    	</div>
	    	<div class="row" hidden="hidden" id="row3">
		   		<div class="col-lg-12">
					<div class="alert alert-warning">
						<label>Hold on! Your file is being uploaded..</label>
					</div>
		        </div>
	    	</div>
		</form>
	    <script src="../js/jquery-2.0.2.min.js"></script>
	    <script src="../js/jquery.validate.min.js"></script>
	    <script src="../js/additional-methods.min.js"></script>
		<script src="../js/bootstrap.min.js"></script>
	   	<script>
			$(document).ready(function() {
				$('#row0').hide();
				$('#row1').show();
// 				$('#row2').hide();
// 				$('#row3').hide();
// 				$('#row4').hide();
				$('#userFile').change(function() {
					var pathArray = $(this).val().split('\\');
					$('#subfile').val(pathArray[pathArray.length - 1]);
				});
				$('#nextRow1').click(function() {
					if($('#uploadForm').valid()){
						$('#row1').hide();
						$('#row2').show();
						$('#userName').focus();
					}
				});
				$('#nextRow2').click(function() {
					if($('#uploadForm').valid()){
						$('#row2').hide();
						$('#row3').show();
						$('#uploadForm').submit();
					} 
				});
				$('#uploadForm').validate({
			        rules: {
			        	userFile: {
			        		required: true
			        	},
			        	userName: {
			                minlength: 3,
			                required: true
			            },
			            userMail: {
			                minlength: 6,
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