package com.example.mealbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealbuddy.apitools.MealBuddyTasks;
import com.example.mealbuddy.apitools.model.RouteModels.GroceryItem;
import com.example.mealbuddy.apitools.model.RouteModels;
import com.example.mealbuddy.datatools.DataToolModels;
import com.example.mealbuddy.datatools.UserDataStore;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.EventListener;

public class GroceryListAdapter extends ArrayAdapter<String> {
    EventListener listener;
    private final Activity context;
    private final String[] ingredientName;
    private final int[] ingredientQuantity;
    private  final String[] ingredientUnit;
    private  final int[] ingredientID;
    private UserDataStore userDataStore;
    ArrayList<GroceryItem> groceryItems = new ArrayList<GroceryItem>();


    public GroceryListAdapter(Activity context, String[] ingredientName, int[] ingredientQuantity, String[] ingredientUnit, int[] ingredientID, EventListener listen) {
        super(context, R.layout.mylist, ingredientName);
        this.context = context;
        this.ingredientID = ingredientID;
        this.ingredientName = ingredientName;
        this.ingredientUnit = ingredientUnit;
        this.ingredientQuantity = ingredientQuantity;
        this.listener = listen;
        System.out.println("Come to the constructor?");

    }
  
    public interface EventListener {
        void onEvent(int data);
    }

    public View getView(final int position, View view, ViewGroup parent) {
        System.out.println(ingredientQuantity[position]);
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.grocery_view_list, null,true);
        TextView groceryIngredientName = (TextView) rowView.findViewById(R.id.groceryIngredientName);
        TextView groceryIngredientQuantity = (TextView) rowView.findViewById(R.id.groceryIngredientQuantity);
        ImageButton removeIngredientBtn = (ImageButton) rowView.findViewById(R.id.removeIngredientBtn);
        groceryIngredientName.setText(ingredientName[position]);
        groceryIngredientQuantity.setText(Integer.toString(ingredientQuantity[position]));
        removeIngredientBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                userDataStore = new UserDataStore(context);
                DataToolModels.User user = userDataStore.getStoredUserData();
                String key = user.getApiKey();
                String gl = user.getGL();
                String us = user.getUsername();
                if(key.length() == 36){ // if there is an api key of correct size
                    RouteModels.EditGroceryList removeGroceryItem = new RouteModels.EditGroceryList(key, gl , ingredientName[position],us , ingredientUnit[position], ingredientQuantity[position], ingredientID[position]);
                    MealBuddyTasks.RemoveGroceryListItemTask myRGroceryListItemTask = new MealBuddyTasks.RemoveGroceryListItemTask(GroceryListAdapter.this);
                    myRGroceryListItemTask.execute(removeGroceryItem);

                }
                else{
                    Toast.makeText(view.getContext(), "Need to be signed in to edit grocery list", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return rowView;
    }

    public void ShowSuccessful(Context context, String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Successful");
        builder1.setMessage(msg);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       // Intent i = new Intent (GroceryListAdapter.this, grocerylistFragment.class);
                       // startActivity(i);
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    static public void ShowError(Context context, String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Error");
        builder1.setMessage(msg);
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

    public void removeGroceryListReply(RouteModels.RemoveGroceryListReply _reply){
        System.out.println("Remove Grocery List Item Reply: " + _reply.toString());

        String mess = _reply.getStatus();
        if(mess.equals("success")){

            //set local memory grocery list from response array
            userDataStore = new UserDataStore(context);
            DataToolModels.User user = userDataStore.getStoredUserData();
            user.setDgl(_reply.getGroceryList());
            userDataStore.storeUserData(user);
           //ShowSuccessful(VerifyUsername.this,mess);
           listener.onEvent(2);


            //reset view to insert new array into array adapter
        }
        else{
           // ShowError(VerifyUsername.this, mess);
        }

    }



}









