<!DOCTYPE html>
<html>
<head>
	<title>:: Lector de mangas - <%=request.getAttribute('name')%> ::</title>
	<link rel="stylesheet" href="/css/bootstrap.css">
</head>
<body>
	<div class="topbar">
		<div class="topbar-inner">
			<div class="container">
				<a class="brand" href="#">Lector de Mangas - <%=request.getAttribute('name')%></a>
			</div>
		</div>
	</div>
	<div style="padding-top:20px"></div>
	<table class="zebra-striped">
		<thead>
			<tr>
				<th>
					<div class="container">
						<div class="row">
							<div class="span16">Selecciona un cap&iacute;tulo</div>
						</div>
					</div>
				</th>
			</tr>
		</thead>
		<tbody>
			<% request.getAttribute("chapters").eachWithIndex{ it, i -> %>
			<tr>
				<td>
					<div class="container">
						<div class="row">
							<div class="span8"><%=it.chapter%></div>
							<div class="span8 right">
								<a href="/serie/ver/<%=((it.link.toString() - "http://submanga.me/") - "http://submanga.com/") %>" class="btn primary">Leer</a>
								<a href="/serie/descargar/<%=((it.link.toString() - "http://submanga.me/") - "http://submanga.com/") %>" class="btn">Descargar</a>
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