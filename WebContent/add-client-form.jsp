<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Add Client</title>
		
		<link type="text/css" rel="stylesheet" href="css/style.css">
		<link type="text/css" rel="stylesheet" href="css/add-client-style.css">
		
	</head>
	
	<body>
	
		<div id="wrapper">
		<div id="header">
		
			<h2>CRM</h2>
			
		</div>
		</div>
	
		<div id="container">
			<h3>Add Client</h3>
		
		<form action="ClientControllerServlet" method="get">
			
			<input type="hidden" name="command" value="ADD">
			
			<table>
				<tbody>
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="firstName" required="required"></td>
					</tr>
					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="lastName" required="required"></td>
					</tr>
					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email"></td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save"></td>
					</tr>
				</tbody>
			</table>
		
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="ClientControllerServlet">Back to list</a>
		</p>
		
		</div>

	</body>
</html>