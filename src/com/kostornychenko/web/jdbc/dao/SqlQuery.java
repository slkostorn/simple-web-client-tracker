package com.kostornychenko.web.jdbc.dao;

public enum SqlQuery {
	
	SELECT("select * from client order by last_name"),
	ADD("insert into client (first_name, last_name, email) values (?,?,?)"),
	LOAD("select * from client where id=?"),
	UPDATE("update client set first_name=?, last_name=?, email=? where id=?"),
	DELETE("delete from client where id=?");
	
	private String query;

	private SqlQuery(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}
	
}
