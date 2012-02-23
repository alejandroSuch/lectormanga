<% import com.google.appengine.api.datastore.* %>
<!DOCTYPE HTML>
<html>
<head>
	<title>:: My first gtpl::</title>
</head>
<body>
	<table>
		<tr>
			<td>key</td>
			<td>desc</td>
			<td>amount</td>
			<td>update</td>
			<td>delete</td>
		</tr>
		<% request.getAttribute("entityList").each{ entry-> %>
		<form action="/editItems.groovy" method="post">
			<input type="hidden" name="key" value="${KeyFactory.keyToString(entry.key)}"/>
		<tr>
			<td>${entry.key.id}</td>
			<td><input type="text" name="desc" value="${entry.desc}" /></td>
			<td><input type="text" name="amount" value="${entry.amount}" /></td>
			<td><input type="submit" name="submit" value="update" /></td>
			<td><input type="submit" name="submit" value="delete" /></td>
		</tr>
		</form>
		<% } %>
	</table>
	
	<table>
		<tr>
			<th colspan="4">New Item</th>
		</tr>
		<tr>
			<td>key</td>
			<td>desc</td>
			<td>amount</td>
			<td>update</td>
		</tr>
		<form action="/editItems.groovy" method="post">
		<tr>
			<td>#</td>
			<td><input type="text" name="desc" value="" /></td>
			<td><input type="text" name="amount" value="" /></td>
			<td><input type="submit" name="submit" value="save" /></td>
		</tr>
		</form>
	</table>
</body>
</html>