package com.example.mealbuddy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.content.Context;

import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings;

import com.example.mealbuddy.apitools.MealBuddyTasks;
import com.example.mealbuddy.apitools.SpoonacularApi;
import com.example.mealbuddy.apitools.model.RouteModels;
import com.example.mealbuddy.databasetools.databasehelper;
import com.example.mealbuddy.datatools.DataToolModels;
import com.example.mealbuddy.datatools.UserDataStore;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DetailRecipe extends AppCompatActivity {

    databasehelper db = new databasehelper(this);


    private UserDataStore userDataStore;
    private DataToolModels.User user;
    String TAG = "TEST:";
    SpoonacularApi.SearchRecipeById searchAPI = null;
    SpoonacularApi.SearchIngredientById searchIngredientApi = null;
    String[] imageURL = {};
    String[] ingredientName = {};
    String[] amount = {};
    String[] ingredientInfo = {};
    String[] sentences = {};
    String firstimg = "";
    String titleimgurl = "";
    ListView list;
    private PopupWindow mPopupWindow;
    ListView ingredientList;
    TextView recipeNameTxt;
    TextView shortDescriptionTxt;
    private ScrollView mScroll;
    private MaterialCalendarView cv_date_m;
    String date = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_recipe);
        Intent i = getIntent();

        String type = i.getStringExtra("type");
        String recipeID = i.getStringExtra("recipeID");
        // Receive Recipe ID from previous page
        if(type.equals("pro")){

            titleimgurl = i.getStringExtra("titleimg");
            // Call api to get the data
            searchAPI = (SpoonacularApi.SearchRecipeById) new SpoonacularApi.SearchRecipeById(new SpoonacularApi.SearchRecipeById.SpoonacularReply() {

                @Override
                public void processFinish(SpoonacularApi.SearchDetailData output) {
                    // Put recipe name
                    recipeNameTxt = (TextView) findViewById(R.id.rcptittledetail);
                    recipeNameTxt.setText(output.getTitle());

                    // Put recipe instruction
                    String longSentence= output.getInstructions();
                    longSentence = longSentence.replaceAll("\\s"," ");
                    longSentence = longSentence.trim().replaceAll(" +", " ");
                    sentences = longSentence.trim().split("(?<=\\.\\s)|(?<=[?!]\\s)");

                    // If there is no instruction of the recipe
                    if (output.getInstructions() == "null"){
                        sentences = new String[]{"No instruction is found for this recipe..."};
                    }

                    ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(DetailRecipe.this, R.layout.simple_list, R.id.stepList, sentences);
                    ingredientList = (ListView) findViewById(R.id.instruction);
                    ingredientList.setAdapter(itemAdapter);

                    // Put recipe image
                    ImageView imageView = (ImageView) findViewById(R.id.firstimage);
                    Picasso.with(DetailRecipe.this).load(output.getImageURL()).into(imageView);
                    firstimg = output.getImageURL();
                    // Put Some time on short description
                    shortDescriptionTxt = (TextView) findViewById(R.id.shrtdscription);
                    shortDescriptionTxt.setText("This recipe takes: " + output.getReadyInMinutes() + " minutes");

                }
            }).execute(recipeID);

            // Call api to get the data
            searchIngredientApi = (SpoonacularApi.SearchIngredientById) new SpoonacularApi.SearchIngredientById(new SpoonacularApi.SearchIngredientById.SpoonacularReply() {

                @Override
                public void processFinish(SpoonacularApi.SearchIngredient[] output) {
                    if (output.length > 0){
                        imageURL = new String[output.length];
                        ingredientName = new String[output.length];
                        amount = new String[output.length];
                        ingredientInfo = new String[output.length];
                        for (int i = 0; i < output.length; i++){
                            imageURL[i] = output[i].getImageURL();
                            ingredientName[i] = output[i].getIngredientName();
                            amount[i] = output[i].getAmount();
                            ingredientInfo[i] = ingredientName[i] + ": " + amount[i];
                        }

                        MyIngredientAdapter adapter= new MyIngredientAdapter(DetailRecipe.this, ingredientInfo, imageURL);
                        list = findViewById(R.id.ingredientList);
                        list.setAdapter(adapter);
                    }
                    else {
                        ShowError(DetailRecipe.this,"No ingredient is found");
                    }
                }
            }).execute(recipeID);


            // "Add recipe to meal plan" button
            Button mealplanBtn = (Button) findViewById(R.id.addMealPlanBtn);
            mealplanBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    final String db_title = recipeNameTxt.getText().toString();
                    final String db_shortdcp = shortDescriptionTxt.getText().toString();
                    final String strimgarray = convertArrayToString(imageURL);
                    final String ingrlist = convertArrayToString(ingredientInfo);
                    final String description = convertArrayToString(sentences);

                    mScroll = findViewById(R.id.rcpthing);

                    LayoutInflater inflater = (LayoutInflater) DetailRecipe.this.getSystemService(LAYOUT_INFLATER_SERVICE);

                    View customView = inflater.inflate(R.layout.popactivity,null);

                    mPopupWindow = new PopupWindow(
                            customView,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

                    if(Build.VERSION.SDK_INT>=21){
                        mPopupWindow.setElevation(5.0f);
                    }

                    ImageButton closeButton = customView.findViewById(R.id.ib_close);
                    Button addbtn = customView.findViewById(R.id.addtomeal);
                    cv_date_m = customView.findViewById(R.id.CV_date);


                    final Spinner DaytimeSpinner = customView.findViewById(R.id.daytimespnr);

                    String[] daytimes = new String[]{"Breakfast", "Lunch", "Dinner"};

                    ArrayAdapter<String> daytimeadapter = new ArrayAdapter<>(DetailRecipe.this, android.R.layout.simple_spinner_dropdown_item, daytimes);

                    DaytimeSpinner.setAdapter(daytimeadapter);

                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Dismiss the popup window
                            mPopupWindow.dismiss();
                        }
                    });

                    addbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String daytimestr;
                            daytimestr = DaytimeSpinner.getSelectedItem().toString();
                            try {
                                date = cv_date_m.getSelectedDate().toString();
                                date = date.replace("CalendarDay", "");
                                date = date.replaceAll("[\\{\\}]", "");
                            } catch (Exception e) {

                            }
                            System.out.println("choosen day: " + date);
                            if(daytimestr == "Breakfast"){
                                daytimestr = "B";
                            }
                            if(daytimestr == "Lunch"){
                                daytimestr = "L";
                            }
                            if(daytimestr == "Dinner"){
                                daytimestr = "D";
                            }
                            if(date != null) {
                                Cursor data = db.findifexist("ALLDAYS", date, daytimestr, db_title);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date chosendate = null;
                                try {
                                    chosendate = sdf.parse(date);
                                } catch (Exception e){

                                }
                                System.out.println("choosen day: " + chosendate);
                                Date something = new Date();
                                System.out.println("today day: " +something);
                                if(new Date().after(chosendate)){
                                    toastMessage("Can't add to the days that already passed.");
                                } else {
                                    if (data.getCount() == 0) {
                                        AddData("ALLDAYS", db_title, db_shortdcp, ingrlist, description, strimgarray, titleimgurl, firstimg, daytimestr, date);
                                    } else {
                                        toastMessage("Already exists in this day and time of meal!");
                                    }
                                }
                            } else {
                                toastMessage("Pick a day!");
                            }
                        }
                    });
                    mPopupWindow.showAtLocation(mScroll, Gravity.CENTER,0,0);
                }
            });
        }
        else if(type.equals("user")){
            String apiKey = i.getStringExtra("apiKey");
            String username = i.getStringExtra("username");

            RouteModels.GetRecipeById2 recipe = new RouteModels.GetRecipeById2(username, apiKey, recipeID);
            MealBuddyTasks.GetRecipeById2 myGetRecipeTask = new MealBuddyTasks.GetRecipeById2(DetailRecipe.this);
            myGetRecipeTask.execute(recipe);

            recipeNameTxt = (TextView) findViewById(R.id.rcptittledetail);
            shortDescriptionTxt = (TextView) findViewById(R.id.shrtdscription);

            ingredientList = (ListView) findViewById(R.id.instruction);
            list = findViewById(R.id.ingredientList);


            Button mealplanBtn = (Button) findViewById(R.id.addMealPlanBtn);
            mealplanBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    final String db_title = recipeNameTxt.getText().toString();
                    final String db_shortdcp = shortDescriptionTxt.getText().toString();
                    final String strimgarray = convertArrayToString(imageURL);
                    final String ingrlist = convertArrayToString(ingredientInfo);
                    final String description = convertArrayToString(sentences);

                    mScroll = findViewById(R.id.rcpthing);

                    LayoutInflater inflater = (LayoutInflater) DetailRecipe.this.getSystemService(LAYOUT_INFLATER_SERVICE);

                    View customView = inflater.inflate(R.layout.popactivity,null);

                    mPopupWindow = new PopupWindow(
                            customView,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

                    if(Build.VERSION.SDK_INT>=21){
                        mPopupWindow.setElevation(5.0f);
                    }

                    ImageButton closeButton = customView.findViewById(R.id.ib_close);
                    Button addbtn = customView.findViewById(R.id.addtomeal);
                    cv_date_m = customView.findViewById(R.id.CV_date);


                    final Spinner DaytimeSpinner = customView.findViewById(R.id.daytimespnr);

                    String[] daytimes = new String[]{"Breakfast", "Lunch", "Dinner"};

                    ArrayAdapter<String> daytimeadapter = new ArrayAdapter<>(DetailRecipe.this, android.R.layout.simple_spinner_dropdown_item, daytimes);

                    DaytimeSpinner.setAdapter(daytimeadapter);

                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Dismiss the popup window
                            mPopupWindow.dismiss();
                        }
                    });

                    addbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String daytimestr;
                            daytimestr = DaytimeSpinner.getSelectedItem().toString();
                            try {
                                date = cv_date_m.getSelectedDate().toString();
                                date = date.replace("CalendarDay", "");
                                date = date.replaceAll("[\\{\\}]", "");
                            } catch (Exception e) {

                            }
                            System.out.println("choosen day: " + date);
                            if(daytimestr == "Breakfast"){
                                daytimestr = "B";
                            }
                            if(daytimestr == "Lunch"){
                                daytimestr = "L";
                            }
                            if(daytimestr == "Dinner"){
                                daytimestr = "D";
                            }
                            if(date != null) {
                                Cursor data = db.findifexist("ALLDAYS", date, daytimestr, db_title);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date chosendate = null;
                                try {
                                    chosendate = sdf.parse(date);
                                } catch (Exception e){

                                }
                                System.out.println("choosen day: " + chosendate);
                                Date something = new Date();
                                System.out.println("today day: " +something);
                                if(new Date().after(chosendate)){
                                    toastMessage("Can't add to the days that already passed.");
                                } else {
                                    if (data.getCount() == 0) {
                                        AddData("ALLDAYS", db_title, db_shortdcp, ingrlist, description, strimgarray, titleimgurl, firstimg, daytimestr, date);
                                    } else {
                                        toastMessage("Already exists in this day and time of meal!");
                                    }
                                }
                            } else {
                                toastMessage("Pick a day!");
                            }
                        }
                    });
                    mPopupWindow.showAtLocation(mScroll, Gravity.CENTER,0,0);
                }
            });


        }


    }

    public void AddData(String table, String title, String description, String ingrdlist, String fullsteps, String ingimgg, String titleimg, String mainimg, String daytime, String date) {
        boolean insertData = db.addData(table, title, description, ingrdlist, fullsteps, ingimgg, titleimg, mainimg, daytime, date);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public static String strSeparator = "__,__";
    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
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



    public void getRecipeByIdReply2(RouteModels.GetRecipeByIdReply2 _reply) {

        System.out.println(_reply.toString());
       String name = _reply.getName();
        String desc = _reply.getDescription();
        String diet = _reply.getDiet();
        String cuisine = _reply.getCuisine();
        String [] array;
        JSONArray  ingredients = _reply.getIngredients();
        try{

            array = new String[ingredients.length()];
            for(int i = 0; i < ingredients.length(); i++){
                String str = ingredients.getJSONObject(i).getString("name");
                str = str + " : 1";
                array[i] = str;
            }
        }
        catch(JSONException e){
            System.out.println("Error parsing ingredient array");
            array = new String[0];
        }

        recipeNameTxt.setText(name);
        shortDescriptionTxt.setText(desc);
      //  ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(DetailRecipe.this, R.layout.simple_list, R.id.stepList, array);
      //  ingredientList.setAdapter(itemAdapter);

        MyIngredientAdapter adapter= new MyIngredientAdapter(DetailRecipe.this, array);
        list.setAdapter(adapter);

    }
}