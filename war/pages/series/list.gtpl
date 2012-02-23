<!DOCTYPE html>
<html>
<head>
	<title>:: Lector de mangas ::</title>
	<link rel="stylesheet" href="/css/bootstrap.css">
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