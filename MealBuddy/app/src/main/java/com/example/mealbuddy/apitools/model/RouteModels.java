package com.example.mealbuddy.apitools.model;

import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;


public class RouteModels {

    public static class GroceryItem{
         public String itemName;
         public int itemID;
         public int itemAmount;
         public String itemUnit;

        public GroceryItem(String name, int id, int amo, String unit){
            this.itemAmount = amo;
            this.itemID = id;
            this.itemName = name;
            this.itemUnit = unit;
        }
    }
    // Data required to run RegistrationTask
    public static class Registration {

        private String userid;
        private String password;
        private String email;
        private String age;
        private float weight;
        private float height;
        private String gender;


        public Registration(String _userid, String _password, String _email, String _age, float _weight, float _height, String _gender) {

            this.userid = _userid;
            this.password = _password;
            this.email = _email;
            this.age = _age;
            this.weight = _weight;
            this.height = _height;
            this.gender = _gender;
        }

        // Returns a JSON string
        public String getJson() {

            JSONObject regJson = new JSONObject();

            try {
                regJson.put("userid", this.userid);
                regJson.put("password", this.password);
                regJson.put("email", this.email);
                regJson.put("age", this.age);
                regJson.put("weight", this.weight);
                regJson.put("height", this.height);
                regJson.put("gender", this.gender);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return regJson.toString();
        }
    }

    // Contains fields from api reply JSON string, status and message from ReplyMessage
    public static class RegistrationReply extends ReplyMessage {

        private String apiKey;
        private String username;

        public RegistrationReply() {
            super();
        }

        public void setApiKey(String _apiKey) {
            this.apiKey = _apiKey;
        }

        public String getApiKey() {
            return this.apiKey;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            JSONObject jsonObj = new JSONObject();
            if (this.getMessage().isEmpty()) {
                this.setMessage("Successfully Registered User.");
            }
            Boolean flag = true;
            String jsonError = "{ \"status\" : \"Faield\", \"message\" : \"Bad JSON object\" }";
            try {
                jsonObj.put("status", this.getStatus());
                jsonObj.put("message", this.getMessage());
                jsonObj.put("apiKey", this.getApiKey());
                jsonObj.put("username", this.username);
            } catch (Exception ex) {
                flag = false;
            }
            return flag == true ? jsonObj.toString() : jsonError;
        }
    }

    // Post recipe
    public static class PostRecipe {

        private String username;
        private String apiKey;
        private String name;
        private String cuisine;
        private String diet;
        private String type;
        private String description;
        private String meal_item;
        private JSONArray ingredients;

        public PostRecipe(String _username, String _apiKey, String _name, String _cuisine, String _diet, String _type, String _description, String _meal_item, JSONArray ingredients) {

            this.username = _username;
            this.apiKey = _apiKey;
            this.name = _name;
            this.cuisine = _cuisine;
            this.diet = _diet;
            this.type = _type;
            this.description = _description;
            this.meal_item = _meal_item;
            this.ingredients = ingredients;
        }

        // Returns a JSON string
        public String getJson() {

            JSONObject regJson = new JSONObject();
            JSONArray arr = new JSONArray();

            try {
                regJson.put("userid", this.username);
                regJson.put("username", this.username); // can probably remove
                regJson.put("api_key", this.apiKey);
                regJson.put("name", this.name);
                regJson.put("cuisine", this.cuisine);
                regJson.put("diet", this.diet);
                regJson.put("type", this.type);
                regJson.put("description", this.description);
                regJson.put("meal_item", this.meal_item);
                regJson.put("ingredients", this.ingredients);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return regJson.toString();
        }
    }

    // Reply from a PostRecipe
    public static class PostRecipeReply extends ReplyMessage {

        public PostRecipeReply() {
            super();
        }

        @Override
        public String toString() {
            JSONObject jsonObj = new JSONObject();
            if (this.getMessage().isEmpty()) {
                this.setMessage("Successfully Posted Recipe.");
            }
            Boolean flag = true;
            String jsonError = "{ \"status\" : \"Failed\", \"message\" : \"Bad JSON object\" }";
            try {
                jsonObj.put("status", this.getStatus());
                jsonObj.put("message", this.getMessage());
            } catch (Exception ex) {
                flag = false;
            }
            return flag == true ? jsonObj.toString() : jsonError;
        }
    }

    // Get Recipe By Recipe ID
    public static class GetRecipeById {

        private String username;
        private String apiKey;
        private String recipeId;

        public GetRecipeById(String _username, String _apiKey, String _recipeId) {

            this.username = _username;
            this.apiKey = _apiKey;
            this.recipeId = _recipeId;
        }

        // Returns a JSON string
        public String getJson() {
            JSONObject regJson = new JSONObject();
            try {

                regJson.put("userid", this.username);
                regJson.put("api_key", this.apiKey);
                regJson.put("name", this.recipeId);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return regJson.toString();
        }
    }
    public static class GetRecipeById2 {

        private String username;
        private String apiKey;
        private String recipeId;

        public GetRecipeById2(String _username, String _apiKey, String _recipeId) {

            this.username = _username;
            this.apiKey = _apiKey;
            this.recipeId = _recipeId;
        }

        // Returns a JSON string
        public String getJson() {
            JSONObject regJson = new JSONObject();
            try {

                regJson.put("userid", this.username);
                regJson.put("api_key", this.apiKey);
                regJson.put("id", this.recipeId);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return regJson.toString();
        }
    }

    public static class GetRecipeByIdReply extends ReplyMessage {

        private String recipeId;
        private String name;
        private String user;
        private JSONArray recipes;

        public GetRecipeByIdReply() {
            super();
        }

        @Override
        public String toString() {
            JSONObject jsonObj = new JSONObject();

            Boolean flag = true;
            String jsonError = "{ \"status\" : \"Failed\", \"message\" : \"Bad JSON object\" }";
            try {
                jsonObj.put("status", this.getStatus());
                jsonObj.put("message", this.getMessage());
                jsonObj.put("recipes", this.getRecipes());
                jsonObj.put("userid", this.getUser());
            } catch (Exception ex) {
                flag = false;
            }
            return flag == true ? jsonObj.toString() : jsonError;
        }

        public JSONArray getRecipes() {
            return recipes;
        }

        public void setRecipes(JSONArray recipes) {
            this.recipes = recipes ;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String name) {
            this.user = name;
        }

        public String getRecipeId() {
            return recipeId;
        }
        public void setRecipeId(String recipeId) {
            this.recipeId = recipeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }



    }


    public static class GetRecipeByIdReply2 extends ReplyMessage {

        private String recipeId;
        private String name;
        private String cuisine;
        private String diet;
        private String type;
        private String description;
        private String mealItem;
        private String user;
        private String intolerances;
        private JSONArray ingredients;
        private String nutritionInfo;
        private JSONArray recipes;

        public GetRecipeByIdReply2() {
            super();
        }

        @Override
        public String toString() {
            JSONObject jsonObj = new JSONObject();
            if (this.getMessage().isEmpty()) {
                this.setMessage("Successfully Got Recipe.");
            }
            Boolean flag = true;
            String jsonError = "{ \"status\" : \"Failed\", \"message\" : \"Bad JSON object\" }";
            try {
                jsonObj.put("status", this.getStatus());
                jsonObj.put("message", this.getMessage());
                jsonObj.put("recipeId", this.getRecipeId());
                jsonObj.put("name", this.getName());
                jsonObj.put("cuisine", this.getCuisine());
                jsonObj.put("diet", this.getDiet());
                jsonObj.put("type", this.getType());
                jsonObj.put("description", this.getDescription());
                jsonObj.put("mealItem", this.getMealItem());
                jsonObj.put("intolerances", this.getIntolerances());
                jsonObj.put("nutritionInfo", this.getNutritionInfo());
                jsonObj.put("ingredients", this.getIngredients());
            } catch (Exception ex) {
                flag = false;
            }
            return flag == true ? jsonObj.toString() : jsonError;
        }


        public String getRecipeId() {
            return recipeId;
        }
        public void setRecipeId(String recipeId) {
            this.recipeId = recipeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }



        public String getCuisine() {
            return cuisine;
        }

        public void setCuisine(String cuisine) {
            this.cuisine = cuisine;
        }

        public String getDiet() {
            return diet;
        }

        public void setDiet(String diet) {
            this.diet = diet;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMealItem() {
            return mealItem;
        }

        public void setMealItem(String mealItem) {
            this.mealItem = mealItem;
        }

        public String getIntolerances() {
            return intolerances;
        }

        public void setIntolerances(String intolerances) {
            this.intolerances = intolerances;
        }

        public String getNutritionInfo() {
            return nutritionInfo;
        }

        public void setNutritionInfo(String nutritionInfo) {
            this.nutritionInfo = nutritionInfo;
        }

        public JSONArray getIngredients() {
            return ingredients;
        }

        public void setIngredients(JSONArray ingredients) {
            this.ingredients = ingredients;
        }


    }
    //Login model
    public static class Login {
        private String userid;
        private String password;

        public Login(String user, String pass){this.userid = user; this.password = pass;}

        // Returns a JSON string
        public String getJson(){

            JSONObject regJson = new JSONObject();

            try {
                regJson.put("userid", this.userid);
                regJson.put("password", this.password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return regJson.toString();
        }

    }
    //Login reply model
    public static class LoginReply extends ReplyMessage {
        private String apiKey;
        private String username;
        private String defaultGroceryList;
        private String [] groceryLists;
        private GroceryItem[] dgl;
        private JSONArray dgljson;
        private JSONArray groceryListsjson;
        private String groceryListStatus;

        public LoginReply() {super();}  //accessing replyMessage variables

        public String getApiKey(){return this.apiKey;}  //setters and getters

        public void setApiKey(String _apiKey) {this.apiKey = _apiKey;}

        public String getGroceryListStatus() {
            return groceryListStatus;
        }

        public void setGroceryListStatus(String _status) {
            this.groceryListStatus = _status;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String _username) {
            this.username = _username;
        }

        public JSONArray getdgljson() {
            return dgljson;
        }

        public void setDgljson(JSONArray _json) {
            this.dgljson = _json;
        }

        public JSONArray getGroceryListsJson() {
            return groceryListsjson;
        }

        public void setGroceryListsjson(JSONArray _json) {
            this.groceryListsjson = _json;
        }

        public String getDefaultGroceryList() {
            return defaultGroceryList;
        }

        public void setDefaultGroceryList(String _defaultGroceryList) {
            this.defaultGroceryList = _defaultGroceryList;
        }

        public String[] getGroceryLists() {
            return groceryLists;
        }

        public void setGroceryLists(JSONArray _groceryLists) {
            ArrayList<String> groceryItems = new ArrayList<String>();
            try{
                for(int i = 0 ; i < _groceryLists.length() ; i++) {
                    String name = _groceryLists.getJSONObject(i).getString("name");
                    groceryItems.add(name);
                }
                int size = groceryItems.size();
                this.groceryLists = new String[size];
                for(int i = 0 ; i < size ; i++){
                    this.groceryLists[i] = groceryItems.get(i);
                }
            }
            catch (JSONException e) {
                System.out.println("Error parsing list items json array");
            }
        }

        public GroceryItem[] getdgl() {
            return dgl;
        }

        public void setdgl(JSONArray _dgl) {
            ArrayList<GroceryItem> groceryItems = new ArrayList<GroceryItem>();
            try{
                for(int i = 0 ; i < _dgl.length() ; i++) {
                    String name = _dgl.getJSONObject(i).getString("name");
                    int id = _dgl.getJSONObject(i).getInt("id");
                    String unit = _dgl.getJSONObject(i).getString("unit");
                    int amount = _dgl.getJSONObject(i).getInt("amount");
                    groceryItems.add(new GroceryItem(name, id, amount, unit));
                }
                int size = groceryItems.size();
                this.dgl = new GroceryItem[size];
                for(int i = 0 ; i < size ; i++){
                    GroceryItem item = groceryItems.get(i);

                    this.dgl[i].itemName = item.itemName;
                    this.dgl[i].itemID = item.itemID;
                    this.dgl[i].itemAmount = item.itemAmount;
                    this.dgl[i].itemUnit = item.itemUnit;
                }
            }
            catch (JSONException e) {
                System.out.println("Error parsing list items json array");
            }
        }


        @Override
        public String toString(){
            JSONObject jsonObj = new JSONObject();

            Boolean flag = true;
            String jsonError = "{ \"status\" : \"Failed\", \"message\" : \"Bad JSON object\" }";
            try {
                jsonObj.put("status", this.getStatus());
                jsonObj.put("message", this.getMessage());
                jsonObj.put("apiKey", this.getApiKey());
                jsonObj.put("username", this.getUsername());
                jsonObj.put("default_grocery_name", this.getDefaultGroceryList());
                jsonObj.put("groceryLists", this.getGroceryListsJson());
                jsonObj.put("default_grocery_list", this.getdgljson());
                jsonObj.put("grocery_list_status", this.getGroceryListStatus());


            } catch (Exception ex){
                flag = false;
            }
            return flag == true? jsonObj.toString() : jsonError;
        }


    }

    //forgot model
    public static class Forgot {
        private String email;
        private String forgotToken;
        private String userid;

        public Forgot(String em){this.email = em;this.forgotToken="null";} //this constructor used for forgot pass and forgot user
        public Forgot(String em, String FT){this.email = em;this.forgotToken=FT;} //this constructor used for checktoken route
        public Forgot(String em, String FT, String user){this.email = em;this.forgotToken=FT;this.userid = user;}

        // Returns a JSON string
        public String getJson(){

            JSONObject regJson = new JSONObject();

            try {
                regJson.put("email", this.email);
                regJson.put("forgot_token", this.forgotToken);
                regJson.put("userid", this.userid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return regJson.toString();
        }

    }
    //Forgot reply model
    public static class ForgotReply extends ReplyMessage {

        public ForgotReply() {super();}  //accessing replyMessage variables

        @Override
        public String toString(){
            JSONObject jsonObj = new JSONObject();
            if (this.getMessage().isEmpty()){
                this.setMessage("Error");
            }
            Boolean flag = true;
            String jsonError = "{ \"status\" : \"Failed\", \"message\" : \"Bad JSON object\" }";
            try {
                jsonObj.put("message", this.getMessage());
            } catch (Exception ex){
                flag = false;
            }
            return flag == true? jsonObj.toString() : jsonError;
        }

    }

    //reset password model
    public static class ResetPass {
        private String email;
        private String forgotToken;
        private String pass;
        private String userid;

        public ResetPass(String em, String FT, String password, String username){this.email = em;this.forgotToken=FT;this.pass=password;this.userid=username;}

        // Returns a JSON string
        public String getJson(){

            JSONObject regJson = new JSONObject();

            try {
                regJson.put("email", this.email);
                regJson.put("forgot_token", this.forgotToken);
                regJson.put("userid", this.userid);
                regJson.put("password", this.pass);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return regJson.toString();
        }

    }

    //Reset pass reply model
    public static class ResetReply extends ReplyMessage {
        private String apiKey;

        public ResetReply() {super();}  //accessing replyMessage variables

        public String getApiKey(){return this.apiKey;}  //setters and getters

        public void setApiKey(String _apiKey) {this.apiKey = _apiKey;}

        @Override
        public String toString(){
            JSONObject jsonObj = new JSONObject();
            if (this.getMessage().isEmpty()){
                this.setMessage("Successfully Reset Password.");
            }
            Boolean flag = true;
            String jsonError = "{ \"status\" : \"Failed\", \"message\" : \"Bad JSON object\" }";
            try {
                jsonObj.put("status", this.getStatus());
                jsonObj.put("message", this.getMessage());
                jsonObj.put("apiKey", this.getApiKey());
            } catch (Exception ex){
                flag = false;
            }
            return flag == true? jsonObj.toString() : jsonError;
        }


    }

    public static class ConfirmRegister {
        private String email;
        private String registerToken;
        private String userid;

        public ConfirmRegister(String em, String RT, String username){this.email = em;this.registerToken=RT;this.userid=username;}

        // Returns a JSON string
        public String getJson(){

            JSONObject regJson = new JSONObject();

            try {
                regJson.put("email", this.email);
                regJson.put("register_token", this.registerToken);
                regJson.put("userid", this.userid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return regJson.toString();
        }

    }

    public static class EditGroceryList { //remove or add grocery list item
        private String apiKey;
        private String groceryList;
        private String groceryListItem;
        private String unit;
        private int amount;
        private String userid;
        private int id;

        public EditGroceryList(String api, String GL, String GLI, String username, String unit, int amount, int id){this.apiKey = api;this.groceryList=GL;this.groceryListItem = GLI;this.userid=username;this.unit = unit;this.amount = amount;this.id = id;}


        // Returns a JSON string
        public String getJson(){

            JSONObject regJson = new JSONObject();

            try {
                regJson.put("api_key", this.apiKey);
                regJson.put("grocery_list", this.groceryList);
                regJson.put("name", this.groceryListItem);
                regJson.put("userid", this.userid);
                regJson.put("item_id", this.id);
                if(this.unit!=null){
                    regJson.put("unit", this.unit);
                }
                if(this.amount>0 && this.amount<100){
                    regJson.put("amount", this.amount);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return regJson.toString();
        }

    }


    public static class GroceryList { //view, create or delete grocery list
        private String apiKey;
        private String groceryList;
        private String userid;

        public GroceryList(String api, String GL, String username){this.apiKey = api;this.groceryList=GL;this.userid=username;}

        // Returns a JSON string
        public String getJson(){

            JSONObject regJson = new JSONObject();

            try {
                regJson.put("api_key", this.apiKey);
                regJson.put("grocery_list", this.groceryList);
                regJson.put("userid", this.userid);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return regJson.toString();
        }

    }

    public static class ViewGroceryListReply extends ReplyMessage {

        private String GroceryListName;
        private boolean privacy;
        private JSONArray GroceryList;


        public ViewGroceryListReply() {super();}

        @Override
        public String toString(){
            JSONObject jsonObj = new JSONObject();

            Boolean flag = true;
            String jsonError = "{ \"status\" : \"Failed\", \"message\" : \"Bad JSON object\" }";
            try {
                jsonObj.put("grocery_list_name", this.getGroceryListName());
                jsonObj.put("privacy", this.getPrivacy());
                jsonObj.put("list_items", this.getGroceryList());
            } catch (Exception ex){
                flag = false;
            }
            return flag == true? jsonObj.toString() : jsonError;
        }
        public String getGroceryListName() {
            return GroceryListName;
        }

        public void setGroceryListName(String name) {
            this.GroceryListName = name;
        }

        public JSONArray getGroceryList() {
            return GroceryList;
        }

        public void setGroceryList(JSONArray list) {
            this.GroceryList = list;
        }

        public boolean getPrivacy() {
            return privacy;
        }

        public void setPrivacy(boolean p) {
            this.privacy = p;
        }
    }

    public static class AddGroceryListReply extends ReplyMessage {

        private String GroceryListName;
        private boolean privacy;
        private JSONArray GroceryList;


        public AddGroceryListReply() {super();}

        @Override
        public String toString(){
            JSONObject jsonObj = new JSONObject();

            Boolean flag = true;
            String jsonError = "{ \"status\" : \"Failed\", \"message\" : \"Bad JSON object\" }";
            try {
                jsonObj.put("status", this.getStatus());
                jsonObj.put("message", this.getMessage());
                jsonObj.put("grocery_list_name", this.getGroceryListName());
                jsonObj.put("privacy", this.getPrivacy());
                jsonObj.put("list_items", this.getGroceryList());
            } catch (Exception ex){
                flag = false;
            }
            return flag == true? jsonObj.toString() : jsonError;
        }
        public String getGroceryListName() {
            return GroceryListName;
        }

        public void setGroceryListName(String name) {
            this.GroceryListName = name;
        }

        public JSONArray getGroceryList() {
            return GroceryList;
        }

        public void setGroceryList(JSONArray list) {
            this.GroceryList = list;
        }

        public boolean getPrivacy() {
            return privacy;
        }

        public void setPrivacy(boolean p) {
            this.privacy = p;
        }
    }

    public static class RemoveGroceryListReply extends ReplyMessage {

        private String GroceryListName;
        private boolean privacy;
        private JSONArray GroceryList;


        public RemoveGroceryListReply() {super();}

        @Override
        public String toString(){
            JSONObject jsonObj = new JSONObject();

            Boolean flag = true;
            String jsonError = "{ \"status\" : \"Failed\", \"message\" : \"Bad JSON object\" }";
            try {
                jsonObj.put("status", this.getStatus());
                jsonObj.put("message", this.getMessage());
                jsonObj.put("grocery_list_name", this.getGroceryListName());
                jsonObj.put("privacy", this.getPrivacy());
                jsonObj.put("list_items", this.getGroceryList());
            } catch (Exception ex){
                flag = false;
            }
            return flag == true? jsonObj.toString() : jsonError;
        }
        public String getGroceryListName() {
            return GroceryListName;
        }

        public void setGroceryListName(String name) {
            this.GroceryListName = name;
        }

        public JSONArray getGroceryList() {
            return GroceryList;
        }

        public void setGroceryList(JSONArray list) {
            this.GroceryList = list;
        }

        public boolean getPrivacy() {
            return privacy;
        }

        public void setPrivacy(boolean p) {
            this.privacy = p;
        }
    }


    public static class ConfirmRegisterReply extends ReplyMessage {

        public ConfirmRegisterReply() {super();}  //accessing replyMessage variables

        @Override
        public String toString(){
            JSONObject jsonObj = new JSONObject();

            Boolean flag = true;
            String jsonError = "{ \"status\" : \"Failed\", \"message\" : \"Bad JSON object\" }";
            try {
                jsonObj.put("status", this.getStatus());
                jsonObj.put("message", this.getMessage());
            } catch (Exception ex){
                flag = false;
            }
            return flag == true? jsonObj.toString() : jsonError;
        }


    }


}