<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basepage>
	<jsp:attribute name="header">
		<c:set var="headerContext" value="${pageContext.request.contextPath}"/>
		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="${headerContext}/">UltraSHARE</a>
				</div>
				<div class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li><a href="${headerContext}/">Home</a></li>
						<li><a href="${headerContext}/main/about">About</a></li>
						<li><a href="${headerContext}/main/contact">Contact</a></li>
					</ul>
<!-- 					<form class="navbar-form navbar-right"> -->
<!-- 						<div class="form-group"> -->
<!-- 							<input type="text" disabled="disabled" placeholder="Email" class="form-control"> -->
<!-- 						</div> -->
<!-- 						<div class="form-group"> -->
<!-- 							<input type="password" disabled="disabled" placeholder="Password" class="form-control"> -->
<!-- 						</div> -->
<!-- 						<button type="submit" disabled="disabled" class="btn btn-success">Sign in</button> -->
<!-- 					</form> -->
				</div>
			</div>
		</div>
	</jsp:attribute>
	<jsp:attribute name="footer">
		<div class="container">
		  <p class="text-muted credit">Copyright © 2013 ultrashare.valvezon.com</p>
		</div>
	  </jsp:attribute>
	<jsp:body>
		<div class="jumbotron">
			<div class="container">
				<h1>UltraSHARE</h1>
				<p>Share AnyTHING, with AnyONE.</p>
			</div>
	    </div>
	    <div class="container">
	    	<div class="row">
	    		<div class="col-lg-9">
	    			<div class="well">
						<jsp:doBody />
					</div>
	    		</div>
	    		<div class="col-lg-3">
	    			<div class="alert">
					<!-- ADs Here... -->
	    			</div>
	    		</div>
	    	</div>
	    </div>
	</jsp:body>
</t:basepage>