package com.example.mealbuddy.apitools.model;

import org.json.JSONObject;
import java.io.Serializable;

public abstract class ReplyMessage implements Serializable {

    private String status;
    private String message;

    public ReplyMessage(){
        this.status = "Reply Failed.";
        this.message = "";
    }

    public void setStatus(String _status){ this.status = _status; }

    public String getStatus(){
        return this.status;
    }

    public void setMessage(String _message){
        this.message = _message;
    }

    public String getMessage(){
        return this.message;
    }

    public String toString(){
        JSONObject jsonObj = new JSONObject();
        Boolean flag = true;
        String jsonError = "{ \"status\" : \"Faield\", \"message\" : \"Bad JSON object\" }";
        try {
            jsonObj.put("status", this.status);
            jsonObj.put("message", this.message);
        } catch (Exception ex){
            flag = false;
        }
        return flag == true? jsonObj.toString() : jsonError;
    }
}
