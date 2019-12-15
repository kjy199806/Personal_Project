package com.example.mealbuddy.datatools;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class UserDataStore {

    public static final String SP_NAME = "userData"; //file name used
    SharedPreferences userDatabase;

    public UserDataStore(Context _context){
        userDatabase = _context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(DataToolModels.User _user){
        SharedPreferences.Editor editor = userDatabase.edit();

        editor.putString("defaultGroceryListName", _user.getGL());
        editor.putString("username", _user.getUsername());
        editor.putString("apikey", _user.getApiKey());
        editor.putString("groceryLists", (_user.getGroceryLists()!=null)?_user.getGroceryLists().toString():"");
        editor.putString("defaultGroceryList", (_user.getdgl()!=null)?_user.getdgl().toString():"");
        editor.apply();
    }

    public void clearUserData(){
        SharedPreferences.Editor editor = userDatabase.edit();
        editor.clear();
        editor.apply();
    }

    public DataToolModels.User getStoredUserData(){
        String username = userDatabase.getString("username", "");
        String apiKey = userDatabase.getString("apikey", "");
        String defaultGroceryList = userDatabase.getString("defaultGroceryListName", "");
        String dGroceryList = userDatabase.getString("defaultGroceryList", "");
        String groceryLists = userDatabase.getString("groceryLists", "");
        //convert strings to json arrays
        JSONArray gLists;
        JSONArray defaultGroceryLister;
       // System.out.println(groceryLists);
       // System.out.println(dGroceryList);
        try{
            gLists = new JSONArray(groceryLists);
            defaultGroceryLister = new JSONArray(dGroceryList);

        }catch (JSONException e) {
            gLists = null;
            defaultGroceryLister = null;
            System.out.println("Error parsing list items json array in user data store");
        }
        DataToolModels.User storedUser = new DataToolModels.User(username, apiKey, defaultGroceryList, gLists, defaultGroceryLister);
        return storedUser;
    }

    public boolean isFirstTime() {
        boolean ranBefore = userDatabase.getBoolean("RanBefore", false);
        return !ranBefore;
    }

}