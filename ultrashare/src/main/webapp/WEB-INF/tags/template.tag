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
						<li class="active"><a href="${headerContext}/">Home</a></li>
						<li><a href="${headerContext}/">About</a></li>
						<li><a href="${headerContext}/">Contact</a></li>
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
		  <p class="text-muted credit">v. BETA - - - Copyright © 2013 ultrashare.valvezon.com - - - Powered by <a href="http://valvezon.com">valvezon.com</a></p>
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
						<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
						<!-- Ultrashare_UploadForm -->
						<ins class="adsbygoogle"
						     style="display:inline-block;width:250px;height:250px"
						     data-ad-client="ca-pub-3660879360573027"
						     data-ad-slot="3711213734"></ins>
						<script>
						(adsbygoogle = window.adsbygoogle || []).push({});
						</script>
						<br />
						<!-- Ultrashare_UploadForm2 -->
						<ins class="adsbygoogle"
						     style="display:inline-block;width:250px;height:250px"
						     data-ad-client="ca-pub-3660879360573027"
						     data-ad-slot="9190840934"></ins>
						<script>
						(adsbygoogle = window.adsbygoogle || []).push({});
						</script>
	    			</div>
	    		</div>
	    	</div>
	    </div>
	</jsp:body>
</t:basepage>