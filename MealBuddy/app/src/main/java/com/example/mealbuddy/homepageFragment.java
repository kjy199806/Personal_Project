package com.example.mealbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.ListView;
import android.widget.AdapterView;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.widget.Toast;

import com.example.mealbuddy.apitools.MealBuddyTasks;
import com.example.mealbuddy.apitools.SpoonacularApi;
import com.example.mealbuddy.apitools.model.RouteModels;
import com.example.mealbuddy.datatools.DataToolModels;
import com.example.mealbuddy.datatools.UserDataStore;

import java.io.File;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class homepageFragment extends Fragment{
    private UserDataStore userDataStore;
    private DataToolModels.User user;
    ListView list;

    private int[] id;
    private String[] maintitle = {};
    private String[] imgURL = {};
    private int size;
    private String username;
    private String apiKey;
    private PopupWindow mPopupWindow;
    private RelativeLayout mScroll;

    SpoonacularApi.SearchRecipeByName searchAPI = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.home_page, container, false);
        final SearchView searchViewResult = (SearchView) view.findViewById(R.id.recipeSearchView);
        size = 0;
        ShowWelcome(getContext(), "Recipes: To find professional recipes and user recipes use home page!\n\n" +
                "Meal Plan: You can save the recipes in your meal plan or generate a meal plan for a day!\n\n" +
                "Ingredients list: You can save them from the recipes or find them on ingridients list!\n\n" +
                "Advanced search: If you need a bit more precise searching, use advanced search page!\n\n" +
                "Sharing a recipe: In order to post a recipe in our app, you need to be signed in.");
        // perform set on query text listener event
        searchViewResult.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){
                    // Getting query
                    System.out.println(query);
                    ShowSearchOptions(getContext(), query);
                }
                else{
                    ShowError(getContext(),"Query length must be longer than 2");
                }

                searchViewResult.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // do something when text changes
                return false;
            }
        });



        userDataStore = new UserDataStore(getContext());
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



        // Get user if one is setup on device

        MyListAdapter adapter= new MyListAdapter(getActivity(), maintitle, imgURL);
        list = view.findViewById(R.id.recipeList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

                Intent i = new Intent(getActivity(), DetailRecipe.class);
                startActivity(i);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);

        if(username.isEmpty() && apiKey.isEmpty()){
            fab.hide();
        } else {
            fab.show();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), createrecipy.class);
                startActivity(i);
            }
        });
        return view;
    }


    public void ShowSearchOptions(final Context context, String query){
        final String searchQuery = query;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Where do you want to find your recipe from?");
        builder.setItems(new CharSequence[]
                        {"Professional recipe", "User recipe", "Later"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                searchAPI = (SpoonacularApi.SearchRecipeByName) new SpoonacularApi.SearchRecipeByName(new SpoonacularApi.SearchRecipeByName.SpoonacularReply(){

                                    //this override the implemented method from asyncTask
                                    @Override
                                    public void processFinish(SpoonacularApi.SearchData[] output){
                                        if (output.length > 0){
                                            id = new int[output.length];
                                            maintitle = new String[output.length];
                                            imgURL = new String[output.length];
                                            for (int i = 0; i < output.length; i++){
                                                id[i] = output[i].getID();
                                                maintitle[i] = output[i].getTitle();
                                                imgURL[i] = output[i].getImageURL();
                                            }
                                            MyListAdapter adapter= new MyListAdapter(getActivity(), maintitle, imgURL);
                                            list = getActivity().findViewById(R.id.recipeList);
                                            list.setAdapter(adapter);

                                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view,int position, long id_) {

                                                    Intent i = new Intent(getActivity(), DetailRecipe.class);
                                                    i.putExtra("recipeID", Integer.toString(id[position]));
                                                    i.putExtra("titleimg", imgURL[position]);
                                                    i.putExtra("type", "pro");
                                                    startActivity(i);
                                                }
                                            });
                                        }
                                        else {
                                            ShowError(getContext(),"No matched recipes are found");
                                        }
                                    }
                                }).execute(searchQuery);
                                break;
                            case 1:
                                // Call our api user recipe here
                                RouteModels.GetRecipeById recipe = new RouteModels.GetRecipeById(username, apiKey, searchQuery );
                                MealBuddyTasks.GetRecipeById myGetRecipeTask = new MealBuddyTasks.GetRecipeById(homepageFragment.this);
                                myGetRecipeTask.execute(recipe);
                                // searchQuery -> query from the user

                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,int position, long id_) {

                                        Intent i = new Intent(getActivity(), DetailRecipe.class);
                                        i.putExtra("recipeID", Integer.toString(id[position]));
                                        i.putExtra("type", "user");
                                        i.putExtra("username", username);
                                        i.putExtra("apiKey", apiKey);
                                        startActivity(i);
                                    }
                                });
                                System.out.println("Search query: " + searchQuery);
                                break;
                            case 2:
                                // Do nothing
                                break;
                        }
                    }
                });
        builder.create().show();
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

    static public void ShowWelcome(Context context, String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Welcome to our app!");
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

    public void getRecipeByIdReply(RouteModels.GetRecipeByIdReply _reply){
        System.out.println(_reply.toString());
        JSONArray recipes = _reply.getRecipes();

        if(recipes!=null){
            size = recipes.length();
        }
        else{
            size = 0;
        }

        if (size > 0) {
            id = new int[size];
            maintitle = new String[size];
            imgURL = new String[size];
            for (int i = 0; i < size; i++) {
                try {
                    JSONObject recipe = recipes.getJSONObject(i);
                    id[i] = recipe.getInt("id");
                    maintitle[i] = recipe.getString("name") + "    " + "Created By : " +  recipe.getString("userid");
                } catch (JSONException e) {
                    System.out.println("error parsing recipe json array");
                }
            }
            MyListAdapter adapter= new MyListAdapter(getActivity(), maintitle);
            list = getActivity().findViewById(R.id.recipeList);
            list.setAdapter(adapter);
        }
        else{
            ShowError(getContext(),"No matched recipes are found");
        }



    }


}
