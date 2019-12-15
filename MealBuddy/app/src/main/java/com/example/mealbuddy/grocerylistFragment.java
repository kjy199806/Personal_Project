package com.example.mealbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;


import com.example.mealbuddy.apitools.MealBuddyTasks;
import com.example.mealbuddy.apitools.model.RouteModels.GroceryItem;
import com.example.mealbuddy.apitools.model.RouteModels;
import com.example.mealbuddy.datatools.DataToolModels;
import com.example.mealbuddy.datatools.UserDataStore;
import com.example.mealbuddy.GroceryListAdapter;

public class grocerylistFragment extends Fragment implements GroceryListAdapter.EventListener{
    private UserDataStore userDataStore;
    ArrayList<GroceryItem> groceryItems = new ArrayList<GroceryItem>();
    String[] ingredientName = {};
    int[] ingredientQuantity = {};
    String[] ingredientUnit = {};
    int[] ingredientID = {};
    GroceryListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.grocery_list, container, false);

        userDataStore = new UserDataStore(getContext());

        DataToolModels.User user = userDataStore.getStoredUserData();
        String key = user.getApiKey();
        String usern = user.getUsername();
        String defaultList = user.getGL();
        JSONArray GroceryLists = user.getGroceryLists();
        JSONArray dgl = user.getdgl();



        if (key != "" && usern != ""){

            ArrayList<GroceryItem> groceryItems = new ArrayList<GroceryItem>();
            try{
                for(int i = 0 ; i < dgl.length() ; i++) {
                    String name = dgl.getJSONObject(i).getString("name");
                    int id = dgl.getJSONObject(i).getInt("id");
                    String unit = dgl.getJSONObject(i).getString("unit");
                    int amount = dgl.getJSONObject(i).getInt("amount");
                    groceryItems.add(new GroceryItem(name, id, amount, unit));
                }
                int size = groceryItems.size();
                ingredientName = new String[size];
                ingredientQuantity = new int[size];
                ingredientUnit = new String[size];
                ingredientID = new int[size];
                for(int i = 0 ; i < size ; i++){
                    GroceryItem item = groceryItems.get(i);
                    ingredientName[i] = item.itemName;
                    ingredientID[i] = item.itemID;
                    ingredientQuantity[i] = item.itemAmount;
                    ingredientUnit[i] = item.itemUnit;
                }
            }
            catch (JSONException e) {
                System.out.println("Error parsing list items json array in grocerylist fragment");
            }


            setBtnHandler(view);
            setIngredientListHandler(view);


        }
        else {
            ShowErrorOnLogin(getContext(), "Required to login in order to use grocery list");
        }



        /* // dont need view grocery list call because we have it in local memory from login
        RouteModels.GroceryList viewGroceryList = new RouteModels.GroceryList(key, defaultList, usern);
        MealBuddyTasks.ViewGroceryListTask myViewGroceryListTask = new MealBuddyTasks.ViewGroceryListTask(grocerylistFragment.this);
        myViewGroceryListTask.execute(viewGroceryList);
         */




            /* // dont need view grocery list call because we have it in local memory from login
            RouteModels.GroceryList viewGroceryList = new RouteModels.GroceryList(key, defaultList, usern);
            MealBuddyTasks.ViewGroceryListTask myViewGroceryListTask = new MealBuddyTasks.ViewGroceryListTask(grocerylistFragment.this);
            myViewGroceryListTask.execute(viewGroceryList);
             */



        return view;
    }

    public void ShowErrorOnLogin(Context context, String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Error");
        builder1.setMessage(msg);
        builder1.setPositiveButton(
                "Login",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), login.class);
                        startActivityForResult(i, 1);
                    }
                });
        builder1.setNegativeButton(
                "Go back to home page",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new homepageFragment()).commit();
                    }
                }
        );
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void onEvent(int data) {
        //  doSomething(data);
        System.out.println("heyheyhey");
        getActivity().recreate();
    }

    public void setIngredientListHandler(View view){
        adapter= new GroceryListAdapter(getActivity(), ingredientName, ingredientQuantity, ingredientUnit, ingredientID, this);
        ListView ingredientNamesList = view.findViewById(R.id.groceryIngredientList);
        ingredientNamesList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void setBtnHandler(View view){
        FloatingActionButton searchBtn = view.findViewById(R.id.searchRecipeButton);
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ShowSuccessful(getActivity(),"Where do you find your recipes?");
            }

        });
    }
    public void ShowSuccessful(final Context context, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(msg);
        builder.setItems(new CharSequence[]
                        {"Recipe page", "User recipe page", "Advanced recipe page", "Later"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new homepageFragment()).commit();
                                break;
                            case 1:
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new userrecipiesFragment()).commit();
                                break;
                            case 2:
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new advancedsearchFragment()).commit();
                                break;
                            case 3:
                                // Do nothing
                                break;
                        }
                    }
                });
        builder.create().show();
    }


    public void viewGroceryListReply(RouteModels.ViewGroceryListReply _reply){
        System.out.println("View Grocery List Item Reply: " + _reply.toString());

        String mess = _reply.getStatus();
        String message = _reply.getMessage();
        if(mess.equals("failed")){
            // ShowError(VerifyUsername.this, mess);
        }
        else{ //successful view request
            JSONArray arr = _reply.getGroceryList();
            try {
                for (int i = 0; i < arr.length(); i++) {
                    String name = arr.getJSONObject(i).getString("name");
                    int id = arr.getJSONObject(i).getInt("id");
                    String unit = arr.getJSONObject(i).getString("unit");
                    int amount = arr.getJSONObject(i).getInt("amount");
                    groceryItems.add(new GroceryItem(name, id, amount, unit));
                }

                int size = groceryItems.size();
                ingredientName = new String[size];
                ingredientID = new int[size];
                ingredientQuantity = new int[size];
                ingredientUnit = new String[size];
                for(int i = 0 ; i < size ; i++){
                    GroceryItem item = groceryItems.get(i);

                    ingredientName[i] = item.itemName;
                    System.out.println(ingredientName[i]);
                    ingredientID[i] = item.itemID;
                    ingredientQuantity[i] = item.itemAmount;
                    ingredientUnit[i] = item.itemUnit;
                }
            } catch (JSONException e) {
                System.out.println("Error parsing list items json array");
            }


        }

    }


}
