<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
			<title>Client Tracker Application</title>
			
			<link type="text/css" rel="stylesheet" href="css/style.css">
			
	</head>

	<body>
	
		<div id="wrapper">
		
			<div id="header">
			
				<h2>CRM</h2>
				
			</div>
			
		</div>
	
		<div id="container">
		
			<div id="content">
		
		<input type="button" value="Add Client"
		onclick="window.location.href='add-client-form.jsp';return false;"
		class="add-client-button"/>
		
			<table border="2">
			
				<tr>
					<th>First name</th>
					<th>Last name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				
				<c:forEach var="tempClient" items="${CLIENT_LIST}">
					
			<!-- create a link for client update	-->
			<c:url var="loadLink" value="ClientControllerServlet">
			<c:param name="command" value="LOAD"/>
			<c:param name="clientId" value="${tempClient.id}"/>
			</c:url>
			
			<!-- create a link for client delete	-->
			<c:url var="deleteLink" value="ClientControllerServlet">
			<c:param name="command" value="DELETE"/>
			<c:param name="clientId" value="${tempClient.id}"/>
			</c:url>
			
				<tr>					
					<td>${tempClient.firstName}</td>
					<td>${tempClient.lastName}</td>
					<td>${tempClient.email}</td>
					<td><a href="${loadLink}">Update</a>
					|
					<a href="${deleteLink}"
					onclick="if(!(confirm('Are you sure want to delete this client?')))return false">
					Delete</a></td>				
				</tr>
				
				</c:forEach>
			
			</table>
		
			</div>
		
		</div>
		
	</body>
</html>