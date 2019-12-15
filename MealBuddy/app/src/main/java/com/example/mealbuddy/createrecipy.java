package com.example.mealbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mealbuddy.apitools.MealBuddyTasks;
import com.example.mealbuddy.apitools.model.RouteModels;
import com.example.mealbuddy.datatools.DataToolModels;
import com.example.mealbuddy.datatools.UserDataStore;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.Arrays;
import java.util.List;


public class createrecipy extends AppCompatActivity {
    private Button sbmt;
    private EditText title;
    private EditText cuisine;
    private EditText diet;
    private EditText type;
    private EditText inglist;
    private EditText steps; private UserDataStore userDataStore;
    private DataToolModels.User user;
    private String apiKey;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edt_recipy);

        sbmt = findViewById(R.id.sbmtrcpy);
        title = findViewById(R.id.rcpedttitle);
        diet = findViewById(R.id.diet);
        cuisine = findViewById(R.id.cuisine);
        type = findViewById(R.id.type);
        inglist = findViewById(R.id.ingridientlist);
        steps = findViewById(R.id.edtstepsrcp);
        userDataStore = new UserDataStore(getApplicationContext());

        // Get user if one is setup on device
        if (userDataStore.isFirstTime()) {
            user = userDataStore.getStoredUserData();
            username = user.getUsername();
            apiKey = user.getApiKey();
            System.out.println("Username: " + user.getUsername() + "<> API KEY: " + user.getApiKey());
        } else {
            username = "";
            apiKey = "";
            System.out.println("it's empty");
        }

        sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tittle_txt = title.getText().toString();
                String diet_txt = diet.getText().toString();
                String cuisine_txt = cuisine.getText().toString();
                String type_txt = type.getText().toString();
                String inglist_txt = inglist.getText().toString();
                String desc = type.getText().toString();
                String mealitem = "Yes";
                if(inglist_txt.isEmpty()){

                }
                List<String> items = Arrays.asList(inglist_txt.split("\\s*,\\s*"));
                JSONArray arr = new JSONArray();
                String json = new String();

                    for (int i = 0; i < items.size(); i++) {
                        JSONObject arrayItem = new JSONObject();
                        try {
                            arrayItem.put("name", items.get(i));
                            arr.put(arrayItem);
                        } catch (JSONException e) {
                            System.out.println("Error parsing ingredients into json array");
                        }

                }

                if(tittle_txt.isEmpty()){
                    ShowError(createrecipy.this, "Title can't be empty");
                } else if (desc.isEmpty()){
                    ShowError(createrecipy.this, "Description can't be empty");
                }
                if(!tittle_txt.isEmpty() && !desc.isEmpty()) {
                    RouteModels.PostRecipe rcpsend = new RouteModels.PostRecipe(username, apiKey, tittle_txt, cuisine_txt, diet_txt, type_txt, desc, mealitem, arr);
                    MealBuddyTasks.PostRecipeTask myTask = new MealBuddyTasks.PostRecipeTask(createrecipy.this);
                    myTask.execute(rcpsend);
                }
            }
        });

    }

    static public void ShowError(Context context, String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        if(msg.isEmpty()){
            builder1.setMessage("Error");
        }
        else{
            builder1.setMessage(msg);
        }

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    //method for showing success message and then closing activity
    static public void ShowMsg(final Context context, String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Congratulations!");
        builder1.setMessage(msg);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        ((Activity) context).finish();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void postRecipeReply(RouteModels.PostRecipeReply _reply){
        // recipe post succese or fail message
        String status = _reply.getStatus();
        String msg = _reply.getMessage();
        if(status.equals("success")){
            ShowMsg(createrecipy.this, "Successfully Posted the recipe!");
        } else {
            ShowError(createrecipy.this, msg);
        }

    }
}