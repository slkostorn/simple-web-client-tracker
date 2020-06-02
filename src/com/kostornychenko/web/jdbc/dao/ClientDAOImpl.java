package com.kostornychenko.web.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.kostornychenko.web.jdbc.entity.Client;

public class ClientDAOImpl implements ClientDAO {
	
	private static final Logger LOGGER = Logger.getLogger(ClientDAOImpl.class);
	
	//source factory connection from context.xml
	private DataSource dataSource;
	
	
	//constructor for connection injection in servlet
	public ClientDAOImpl (DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	//list all the clients method
	@Override
	public List<Client> getClients(){
		
		//create empty ArrayList for client objects
		List<Client> clients = new ArrayList<>();
		
		//declare JDBC objects
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			
		//create connection, SQL statement and execute query
		connection = dataSource.getConnection();
		statement = connection.createStatement();
		resultSet = statement.executeQuery(SqlQuery.SELECT.getQuery());
		
		while(resultSet.next()) {
			int id = resultSet.getInt("id");
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			String email = resultSet.getString("email");
			
			Client tempClient = new Client(id, firstName, lastName, email);
			
			clients.add(tempClient);
		}
		
		}
		catch (SQLException e) {
			printSQLException(e);
			LOGGER.error("SQL exception during"
					+ " getting a list of students from database");
		}
		finally {
			// close JDBC objects
			close(connection,statement,resultSet);
		}
		return clients;
	}
	
	//add client to database
	@Override
	public void addClient(Client client){
		
		//declare JDBC objects
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
		//create connection and sql statement
		connection = dataSource.getConnection();	
		statement = connection.prepareStatement(SqlQuery.ADD.getQuery());
		
		//set parameters to SQL statement
		statement.setString(1, client.getFirstName());
		statement.setString(2, client.getLastName());
		statement.setString(3, client.getEmail());
		
		//execute statement
		statement.execute();
		LOGGER.info("Add client into database - SQL query executed");
		}
		catch(SQLException e) {
			printSQLException(e);
			LOGGER.error("SQL exception during adding a client into database");
		}
		finally {
			// close JDBC objects
			close(connection, statement,null);
		}
	}
	
	//load client from database and return to servlet
	@Override
	public Client getClient(String clientIdString) throws SQLException {
		
		//declare JDBC objects
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		Client client = null;
		int clientId;
		
		try {
			// convert client id to int
			clientId = Integer.parseInt(clientIdString);
			
			// get connection to database
			connection = dataSource.getConnection();
			
			// create prepared statement
			statement = connection.prepareStatement(SqlQuery.LOAD.getQuery());
			
			// set parameter into SQL query
			statement.setInt(1, clientId);
			
			// execute statement
			resultSet = statement.executeQuery();
			
			// get data from result set row
			if (resultSet.next()) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				
				// use the clientId during construction
				client = new Client(clientId, firstName, lastName, email);
			}
			else {
				throw new SQLException();
			}				
			LOGGER.info("Get client from database by id - SQL query executed");
			return client;
		}
		finally {
			// close JDBC objects
			close(connection, statement, resultSet);
		}
	}
	
	//update client in database and return to servlet
	@Override
	public void updateClient(Client client) {
		
		//declare JDBC objects
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
		//create connection and sql statement
		connection = dataSource.getConnection();
		statement = connection.prepareStatement(SqlQuery.UPDATE.getQuery());
		
		//set parameters into SQL query
		statement.setString(1, client.getFirstName());
		statement.setString(2, client.getLastName());
		statement.setString(3, client.getEmail());
		statement.setInt(4, client.getId());
		
		//execute SQL statement
		statement.execute();
		LOGGER.info("Update a client in a database - SQL query executed");
	}
	catch (SQLException e) {
		printSQLException(e);
		LOGGER.error("SQL exception during updating a client");
	}
	finally {	
		// close JDBC objects
		close(connection, statement, null);
	}
	}
	
	//delete a client from database
	@Override
	public void deleteClient(int id) {
		
		//declare JDBC objects
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
		// get connection to database
		connection = dataSource.getConnection();
					
		// create prepared statement
		statement = connection.prepareStatement(SqlQuery.DELETE.getQuery());
		
		//set id parameter into SQL statement
		statement.setInt(1, id);
		
		//execute SQL statement
		statement.execute();
		LOGGER.info("Delete a client from database - SQL query executed");
		}
		catch (SQLException e) {
			printSQLException(e);
		}
		finally {
			// close JDBC objects
			close(connection, statement, null);
		}
	}

	
	//common method for generating SQL exceptions messages
	private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                LOGGER.error("SQL exception " +e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

	//method for closing all JDBC objects
		private void close(Connection connection, Statement statement, ResultSet resultSet) {
			try {
				if(resultSet!=null) {
					resultSet.close();
				}
				
				if(statement!=null) {
					statement.close();
				}
				if(connection!=null) {
					connection.close();
				}
			}
			catch (SQLException e) {
				printSQLException(e);;
			}
			
		}

}
