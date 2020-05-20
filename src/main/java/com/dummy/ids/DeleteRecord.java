package com.dummy.ids;

public class DeleteRecord {

	
	private  String req_id;
	private String user_id;
	private String email;
	private String table_guid;
	
	
	public DeleteRecord() {
		super();
	}
	public String getReq_id() {
		return req_id;
	}
	public void setReq_id(String req_id) {
		this.req_id = req_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTable_guid() {
		return table_guid;
	}
	public void setTable_guid(String table_guid) {
		this.table_guid = table_guid;
	}
	public DeleteRecord(String req_id, String user_id, String email, String table_guid) {
		super();
		this.req_id = req_id;
		this.user_id = user_id;
		this.email = email;
		this.table_guid = table_guid;
	}
	@Override
	public String toString() {
		return "DeleteRecord [req_id=" + req_id + ", user_id=" + user_id + ", email=" + email + ", table_guid="
				+ table_guid + "]";
	}
	
	
	
	
}
