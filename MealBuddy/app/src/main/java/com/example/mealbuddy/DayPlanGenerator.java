package com.example.mealbuddy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealbuddy.apitools.SpoonacularApi;
import com.example.mealbuddy.databasetools.databasehelper;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DayPlanGenerator extends AppCompatActivity {

    private SeekBar cals;
    private EditText diet;
    private EditText items;
    private MaterialCalendarView cv_date_m;
    private Button addtherec;
    private TextView valueheck;

    SpoonacularApi.GenerateMealplanByDay searchAPI = null;
    SpoonacularApi.SearchRecipeById searchAPIrecipy = null;
    SpoonacularApi.SearchIngredientById searchIngredientApi = null;

    databasehelper db;

    private int[] id;
    private String[] maintitle = {};
    private String[] imgURL = {};
    private String[] sentences = {};
    private String firstimg = "";
    private String[] imageURL = {};
    private String db_title = "";
    private String db_shortdcp = "";
    private String[] titleara = {};
    private String[] ingredientName = {};
    private String[] amount = {};
    private String[] ingredientInfo = {};
    private String[] idtoexec = {};
    String date = null;
    private int lel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_plan);

        db = new databasehelper(getApplicationContext());

        cals = findViewById(R.id.targetcal);
        diet = findViewById(R.id.dietET);
        items = findViewById(R.id.itemsET);
        cv_date_m = findViewById(R.id.CV_date);
        addtherec = findViewById(R.id.foraday);
        valueheck = findViewById(R.id.targetcalValue);
        valueheck.setText(String.valueOf(cals.getProgress()));

        cals.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                valueheck.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });


        addtherec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = "day";
                lel = 0;

                String[] exclude = items.getText().toString().trim().split(",");
                String dietstr = diet.getText().toString();
                int calorie = cals.getProgress();
                String caloriesthing = Integer.toString(calorie);

                MealPlanParam parametrs = new MealPlanParam(day, exclude, dietstr, caloriesthing);

                try {
                    date = cv_date_m.getSelectedDate().toString();
                    date = date.replace("CalendarDay", "");
                    date = date.replaceAll("[\\{\\}]", "");
                } catch (Exception e) {

                }
                if(date != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
                    Date chosendate = null;
                    Date comparedate = new Date();

                    try {
                        chosendate = sdf.parse(date);
                    } catch (Exception e){

                    }

                    if(new Date().compareTo(chosendate) > 0){
                        toastMessage("Can't add to the days that already passed.");
                    } else {
                        searchAPI = (SpoonacularApi.GenerateMealplanByDay) new SpoonacularApi.GenerateMealplanByDay(new SpoonacularApi.GenerateMealplanByDay.SpoonacularReply() {
                            @Override
                            public void processFinish(final SpoonacularApi.SearchData[] outputlel) {

                                if (outputlel.length > 0) {
                                    id = new int[outputlel.length];
                                    maintitle = new String[outputlel.length];
                                    imgURL = new String[outputlel.length];
                                    idtoexec = new String[outputlel.length];
                                    for (int i = 0; i < outputlel.length; i++) {
                                        id[i] = outputlel[i].getID();
                                        imgURL[i] = outputlel[i].getImageURL();
                                        maintitle[i] = outputlel[i].getTitle();
                                        idtoexec[i] = Integer.toString(id[i]);
                                        System.out.println(id[i]);
                                    }
                                    for (int i = 0; i < id.length; i++) {

                                        searchAPIrecipy = (SpoonacularApi.SearchRecipeById) new SpoonacularApi.SearchRecipeById(new SpoonacularApi.SearchRecipeById.SpoonacularReply() {
                                            @Override
                                            public void processFinish(SpoonacularApi.SearchDetailData output) {
                                                // Put recipe name
                                                db_title = output.getTitle();
                                                System.out.println(db_title);

                                                // Put recipe instruction
                                                String longSentence = output.getInstructions();
                                                System.out.println(longSentence);
                                                longSentence = longSentence.replaceAll("\\s", " ");
                                                longSentence = longSentence.trim().replaceAll(" +", " ");
                                                sentences = longSentence.trim().split("(?<=\\.\\s)|(?<=[?!]\\s)");

                                                // If there is no instruction of the recipe
                                                if (output.getInstructions() == "null") {
                                                    sentences = new String[]{"No instruction is found for this recipe..."};
                                                }

                                                firstimg = output.getImageURL();
                                                // Put Some time on short description
                                                db_shortdcp = ("This recipe takes: " + output.getReadyInMinutes() + " minutes");
                                                searchIngredientApi = (SpoonacularApi.SearchIngredientById) new SpoonacularApi.SearchIngredientById(new SpoonacularApi.SearchIngredientById.SpoonacularReply() {

                                                    @Override
                                                    public void processFinish(SpoonacularApi.SearchIngredient[] output) {
                                                        if (output.length > 0) {
                                                            imageURL = new String[output.length];
                                                            ingredientName = new String[output.length];
                                                            amount = new String[output.length];
                                                            ingredientInfo = new String[output.length];
                                                            for (int i = 0; i < output.length; i++) {
                                                                imageURL[i] = output[i].getImageURL();
                                                                ingredientName[i] = output[i].getIngredientName();
                                                                amount[i] = output[i].getAmount();
                                                                ingredientInfo[i] = ingredientName[i] + ": " + amount[i];
                                                            }
                                                        } else {
                                                            ShowError(getApplicationContext(), "No ingredient is found");
                                                        }

                                                        final String ingrlist = convertArrayToString(ingredientInfo);
                                                        final String description = convertArrayToString(sentences);
                                                        final String strimgarray = convertArrayToString(imageURL);
                                                        String daytimestr = "";
                                                        if (lel == 0) {
                                                            daytimestr = "B";
                                                        }
                                                        if (lel == 1) {
                                                            daytimestr = "L";
                                                        }
                                                        if (lel == 2) {
                                                            daytimestr = "D";
                                                        }
                                                        System.out.println("Right now number is " + lel);
                                                        AddData("ALLDAYS", maintitle[lel], db_shortdcp, ingrlist, description, strimgarray, imgURL[lel], firstimg, daytimestr, date);
                                                        lel++;
                                                    }
                                                }).execute(idtoexec[lel]);
                                            }
                                        }).execute(idtoexec[i]);
                                    }
                                } else {
                                    ShowError(getApplicationContext(), "No matched recipes are found");
                                }
                            }
                        }).execute(parametrs);
                    }
                } else {
                    toastMessage("Pick a day!");
                }
            }
        });
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

    private void toastMessage(String message){
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
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

    public void AddData(String table, String title, String description, String ingrdlist, String fullsteps, String ingimgg, String titleimg, String mainimg, String daytime, String date) {
        boolean insertData = db.addData(table, title, description, ingrdlist, fullsteps, ingimgg, titleimg, mainimg, daytime, date);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    public static class MealPlanParam {
        private String m_day;
        private String[] exclude1;
        private String diet;
        private String calory;

        MealPlanParam(String day, String[] exclude, String diet, String calory){
            this.m_day = day;
            exclude1 = new String[exclude.length];
            for(int i = 0; i < exclude.length; i++){
                this.exclude1[i] = exclude[i];
            }
            this.diet = diet;
            this.calory = calory;
        }
        public String getDay(){return  m_day;}
        public String[] getExcludes(){return  exclude1;}
        public String getDiet(){return  diet;}
        public String getCalory(){return  calory;}
    }
}
