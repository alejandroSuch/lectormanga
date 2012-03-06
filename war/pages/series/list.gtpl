<!DOCTYPE html>
<html>
<head>
	<title>:: Lector de mangas ::</title>
	<link rel="stylesheet" href="/css/bootstrap.css">
	<script type="text/javascript">
	  var _gaq = _gaq || [];
	  _gaq.push(['_setAccount', 'UA-29763989-1']);
	  _gaq.push(['_trackPageview', '/home']);
	
	  (function() {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	  })();
	</script>
</head>
<body>
	<div class="topbar">
		<div class="topbar-inner">
			<div class="container">
				<a class="brand" href="#">Lector de Mangas</a>
			</div>
		</div>
	</div>
	<div style="padding-top:40px"></div>
	<table class="zebra-striped">
		<thead>
			<tr>
				<th>
					<div class="container">
						<div class="row">
							<div class="span16">Selecciona una serie</div>
						</div>
					</div>
				</th>
			</tr>
		</thead>
		<tbody>
			<% request.getAttribute("series").each{ %>
			<tr>
				<td>
					<div class="container">
						<div class="row">
							<div class="span8"><%=it.chapter%></div>
							<div class="span8 right">
								<a class="btn primary" href="/serie/<%=((it.link.toString() - "http://submanga.me/") - "http://submanga.com/") %>">Ver</a>
							</div>
						</div>
					</div>
				</td>
			</tr>
			<% } %>
		</tbody>
	</table>
	
	
	
</body>
</html>