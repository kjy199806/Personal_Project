package com.example.mealbuddy.datatools;
import android.util.JsonReader;

import com.example.mealbuddy.apitools.model.RouteModels.GroceryItem;

import org.json.JSONArray;

public class DataToolModels {

    public static class User{

        private String username;
        private String apiKey;
        private String defaultGroceryList;
        private JSONArray  groceryLists;
        private JSONArray dgl;


        public User(String _username, String _apiKey, String GL, JSONArray GLs, JSONArray DGL){
            this.username = _username;
            this.apiKey = _apiKey;
            this.defaultGroceryList = GL;
            this.groceryLists = GLs;
            this.dgl = DGL;


        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGL() {
            return defaultGroceryList;
        }

        public void setGL(String GL) {this.defaultGroceryList = GL; }

        public JSONArray getGroceryLists() {
            if(groceryLists==null){
                JSONArray arr = new JSONArray();
                return arr;
            }
            else{
                return groceryLists;
            }
             }

        public void setGroceryLists(JSONArray GL) {this.groceryLists = GL; }

        public JSONArray getdgl() {
            if(dgl==null){
                JSONArray arr = new JSONArray();
                return arr;
            }
            else{
                return dgl;
            }
            }

        public void setDgl(JSONArray GL) {this.dgl = GL; }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        @Override
        public String toString(){
            return "Username: " + username + " <> ApiKey: "+ apiKey + " <> Default grocery list: " + defaultGroceryList;
        }
    }
}
