package com.kostornychenko.web.jdbc.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import org.apache.log4j.Logger;

import com.kostornychenko.web.jdbc.dao.ClientDAOImpl;
import com.kostornychenko.web.jdbc.entity.Client;


/**
 * Servlet implementation class ClientControllerServlet
 */
@WebServlet("/ClientControllerServlet")
public class ClientControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static final Logger LOGGER = Logger.getLogger(ClientControllerServlet.class);
	
	//declare ClientDAO object 
	private ClientDAOImpl clientDAOImpl;
	
	//declare DataSource object for connecting to database
	@Resource(name="jdbc/web_client_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		
		LOGGER.info("Entering the init method");
		
		super.init();
		//inject connection into initialization method
		try {
		clientDAOImpl = new ClientDAOImpl(dataSource);
		LOGGER.info("Inject connection into initialization method");
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		//read "command" parameter
		String command = request.getParameter("command");
		
		if(command == null) {
			command="LIST";
		}
			//choose the appropriate method
			switch(command) {
			
			case "LIST":
				//command to show list of clients
				listClients(request,response);
				
				break;
				
			case "ADD":
				//command to add client to the list
				addClient(request,response);
				break;
				
			case "LOAD":
				//command to load client for update and send to update-client-form.jsp
				loadClient(request,response);
				break;
				
			case "UPDATE":
				//command to update client
				updateClient(request,response);
				break;
				
			case "DELETE":
				//command to delete client from list
				deleteClient(request,response);
				break;
				
			default:
				listClients(request,response);
				LOGGER.info("Default list of clients as a start page");
				break;
			}
				
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	//get client id from list-client.jsp, delete client from database and go back to list-client.jsp
	private void deleteClient(HttpServletRequest request, HttpServletResponse response)throws Exception {
		LOGGER.info("Entering into deleteClient method in a servlet");
		
		//read client id from list-clients.jsp
		int id = Integer.parseInt(request.getParameter("clientId"));
		
		//delete client from database
		clientDAOImpl.deleteClient(id);
		//go back to client-list.jsp
		listClients(request, response);
	}

	//read client param from update-client-form.jsp, update database with new client object
	//and go to list-clients.jsp
	private void updateClient(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Entering into updateClient method in a servlet");
		
		//read client parameters by id from update-client-form.jsp 
		int id = Integer.parseInt(request.getParameter("clientId"));
		String firstName= request.getParameter("firstName");
		String lastName  = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//create a new client object
		Client client = new Client(id, firstName, lastName, email);
			
		//update the database
		clientDAOImpl.updateClient(client);
			
				
		//go to client-list.jsp
		listClients(request, response);
	}

	//read client object from database by id, add client object to request attribute
	//and go to update-client.jsp
	private void loadClient(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Entering into loadClient method in a servlet");
		
		//read client id from list-clients.jsp
		String clientId = request.getParameter("clientId");
		
		//get client object from database by id
		Client client = clientDAOImpl.getClient(clientId);
		
		//add client object into request attribute
		request.setAttribute("CLIENT", client);
		
		//go to update-client-form.jsp
		request.getRequestDispatcher("/update-client-form.jsp").forward(request, response);
		
	}
	
	//read client parameters from add-client-form.jsp, add client object
	//to database and go to list-clients.jsp
	private void addClient(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Entering into addClient method in a servlet");
		
		//read client parameters from add-client-form.jsp
		String firstName= request.getParameter("firstName");
		String lastName  = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//create new client object with param in constructor
		Client client = new Client(firstName, lastName, email);
		
		
		
		//add client object to database
		clientDAOImpl.addClient(client);
		
		//send to list-clients.jsp
		listClients(request, response);
	}
	
	//get list of clients from database and go to list-clients.jsp
	private void listClients(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("Entering into listClients method in a servlet");
		
		//get Students list from database
		List<Client> clients = clientDAOImpl.getClients();
		
		//add clients list to the request attribute
		request.setAttribute("CLIENT_LIST", clients);
		LOGGER.info("Getting  clients list from a database");
		
		//go to list-clients.jsp
		request.getRequestDispatcher("/list-clients.jsp").forward(request, response);
		
	}
	
}
