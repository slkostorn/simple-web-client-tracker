package com.kostornychenko.web.jdbc.dao;

import java.sql.SQLException;
import java.util.List;

import com.kostornychenko.web.jdbc.entity.Client;

public interface ClientDAO {

	List<Client> getClients();
	
	public void addClient(Client client);
	
	public Client getClient(String clientIdString) throws SQLException;
	
	public void updateClient(Client client);
	
	public void deleteClient(int id);
	

}
