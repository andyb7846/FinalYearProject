package com.PrototypeServer.spring.model;

// This is the response sent to the android application if the login is successful.
public class SuccessResponse {

    private int id;

    private String unique_id;

    private String username;

    private String create_time;

    private String update_time;

    public SuccessResponse(int id, String unique_id, String username, String create_time, String update_time) {
        this.id = id;
        this.unique_id = unique_id;
        this.username = username;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public SuccessResponse(int id, User user){
        this.id = id;
        this.unique_id = user.getUnique_id();
        this.username = user.getUsername();
        this.create_time = user.getCreate_time();
        this.update_time = user.getUpdate_time();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
