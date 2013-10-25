<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:basepage>
	<jsp:attribute name="header">
		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="${pageContext.request.contextPath}">UltraSHARE</a>
				</div>
				<div class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li class="active"><a href="${pageContext.request.contextPath}">Home</a></li>
						<li><a href="#about">About</a></li>
						<li><a href="#contact">Contact</a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="#">Action</a></li>
								<li><a href="#">Another action</a></li>
								<li><a href="#">Something else here</a></li>
								<li class="divider"></li>
								<li class="dropdown-header">Nav header</li>
								<li><a href="#">Separated link</a></li>
								<li><a href="#">One more separated link</a></li>
							</ul>
						</li>
					</ul>
					<form class="navbar-form navbar-right">
						<div class="form-group">
							<input type="text" disabled="disabled" placeholder="Email" class="form-control">
						</div>
						<div class="form-group">
							<input type="password" disabled="disabled" placeholder="Password" class="form-control">
						</div>
						<button type="submit" disabled="disabled" class="btn btn-success">Sign in</button>
					</form>
				</div>
			</div>
		</div>
	</jsp:attribute>
	<jsp:attribute name="footer">
		<div class="container">
		  <p class="text-muted credit">Powered by <a href="http://valvezon.com">valvezon.com</a></p>
		</div>
	  </jsp:attribute>
	<jsp:body>
		<div class="jumbotron">
			<div class="container">
				<h1>UltraSHARE</h1>
				<p>Share AnyTHING, with AnyONE.</p>
			</div>
	    </div>
		<jsp:doBody />
	</jsp:body>
</t:basepage>