package com.example.mealbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mealbuddy.apitools.SpoonacularApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class advancedsearchFragment extends Fragment {

    public static int MAXFAT = 99;
    public static int MAXCAL = 750;
    public static int MAXCARB = 90;
    public static int MAXPRO = 90;
    public static int MAXCAFFEINE = 200;
    public static int MAXALCOHOL = 60;
    public static int MAXCALCIUM = 1000;
    public static int MAXCHOLESTEROL = 1000;
    public static int MAXVITAMIN = 1000;

    // Ingredient information
    private CheckBox pantryCheckBox;
    private EditText ingredientName;
    private Button ingredientBtn;

    // Recipe information
    private EditText recipeName;
    private EditText cusine;
    private EditText includeIngredient;
    private EditText excludeIngredient;
    private EditText intolerance;

    private TextView minFat;
    private TextView maxFat;

    private TextView minCal;
    private TextView maxCal;

    private TextView minCarbs;
    private TextView maxCarbs;

    private TextView minProtein;
    private TextView maxProtein;

    private TextView minCaffeine;
    private TextView maxCaffeine;

    private TextView minAlcohol;
    private TextView maxAlcohol;

    private TextView minCalcium;
    private TextView maxCalcium;

    private TextView minCholesterol;
    private TextView maxCholesterol;

    private TextView minVitaminA;
    private TextView maxVitaminA;
    private TextView minVitaminC;
    private TextView maxVitaminC;
    private TextView minVitaminD;
    private TextView maxVitaminD;
    private TextView minVitaminE;
    private TextView maxVitaminE;
    private TextView minVitaminK;
    private TextView maxVitaminK;

    // Seekbar Min/Max Fat, Calories, Carbs, Protein
    private SeekBar minFatBar;
    private SeekBar maxFatBar;
    private SeekBar minCalBar;
    private SeekBar maxCalBar;
    private SeekBar minCarbsBar;
    private SeekBar maxCarbsBar;
    private SeekBar minProteinBar;
    private SeekBar maxProteinBar;
    private SeekBar minCaffeineBar;
    private SeekBar maxCaffeineBar;
    private SeekBar minAlcoholBar;
    private SeekBar maxAlcoholBar;
    private SeekBar minCalciumBar;
    private SeekBar maxCalciumBar;
    private SeekBar minCholesterolBar;
    private SeekBar maxCholesterolBar;
    private SeekBar minVitABar;
    private SeekBar maxVitABar;
    private SeekBar minVitCBar;
    private SeekBar maxVitCBar;
    private SeekBar minVitDBar;
    private SeekBar maxVitDBar;
    private SeekBar minVitEBar;
    private SeekBar maxVitEBar;
    private SeekBar minVitKBar;
    private SeekBar maxVitKBar;

    // Search button
    private Button recipeSearchBtn;

    // Linear layout for recipe advanced
    private RelativeLayout recipeLayout;
    private RelativeLayout ingredientLayout;

    private Spinner spinner;
    private String[] values = {"Ingredient", "Recipe"};
    private SpoonacularApi.SearchRecipeByIngredient searchAPI = null;
    private ListView list;

    private SpoonacularApi.SearchAdvancedRecipe advancedSearchAPI = null;
    private ListView advancedList;

    private int[] id;
    private String[] maintitle = {};
    private String[] imgURL = {};

    private int[] advance_id;
    private String[] advance_maintitle = {};
    private String[] advance_imgURL = {};

    private MultiSelectionSpinner multiSpinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.advanced_search, container, false);

        setFindById(view);
        setBarListener();
        setBtnHandler();
        setMultiSpinner(view);
        return view;
    }

    private void setMultiSpinner(View view){


        multiSpinner= view.findViewById(R.id.nutritionSpinner);

        List<String> list = new ArrayList<String>();
        list.add("Fat");
        list.add("Calories");
        list.add("Carbohydrate");
        list.add("Protein");
        list.add("Caffeine");
        list.add("Alcohol");
        list.add("Calcium");
        list.add("Cholesterol");
        list.add("Vitamin A");
        list.add("Vitamin C");
        list.add("Vitamin D");
        list.add("Vitamin E");
        list.add("Vitamin K");

        List<Integer> minLayoutList = new ArrayList<>();
        minLayoutList.add(R.id.minFatLayout);
        minLayoutList.add(R.id.minCaloriesLayout);
        minLayoutList.add(R.id.minCarbsLayout);
        minLayoutList.add(R.id.minProLayout);
        minLayoutList.add(R.id.minCaffLayout);
        minLayoutList.add(R.id.minAlcLayout);
        minLayoutList.add(R.id.minCalciumLayout);
        minLayoutList.add(R.id.minCholLayout);
        minLayoutList.add(R.id.minVitALayout);
        minLayoutList.add(R.id.minVitCLayout);
        minLayoutList.add(R.id.minVitDLayout);
        minLayoutList.add(R.id.minVitELayout);
        minLayoutList.add(R.id.minVitKLayout);



        List<Integer> maxLayoutList = new ArrayList<>();
        maxLayoutList.add(R.id.maxFatLayout);
        maxLayoutList.add(R.id.maxCalLayout);
        maxLayoutList.add(R.id.maxCarbsLayout);
        maxLayoutList.add(R.id.maxProLayout);
        maxLayoutList.add(R.id.maxCaffLayout);
        maxLayoutList.add(R.id.maxAlcLayout);
        maxLayoutList.add(R.id.maxCalciumLayout);
        maxLayoutList.add(R.id.maxCholLayout);
        maxLayoutList.add(R.id.maxVitALayout);
        maxLayoutList.add(R.id.maxVitCLayout);
        maxLayoutList.add(R.id.maxVitDLayout);
        maxLayoutList.add(R.id.maxVitELayout);
        maxLayoutList.add(R.id.maxVitKLayout);

        multiSpinner.setItems(list,minLayoutList,maxLayoutList, view);

    }

    private void setFindById(View view) {
        // Ingredient information
        pantryCheckBox = view.findViewById(R.id.pantryCheckbox);
        ingredientName = view.findViewById(R.id.ingredientName);
        ingredientBtn = view.findViewById(R.id.ingredientSearchBtn);

        // Recipe information
        recipeName = view.findViewById(R.id.recipeName);
        cusine = view.findViewById(R.id.cuisine);
        includeIngredient = view.findViewById(R.id.includeIngredient);
        excludeIngredient = view.findViewById(R.id.excludeIngredient);
        intolerance = view.findViewById(R.id.intolerance);

        minFat = view.findViewById(R.id.minFatValue);
        maxFat = view.findViewById(R.id.maxFatValue);
        minCal = view.findViewById(R.id.minCaloriesValue);
        maxCal = view.findViewById(R.id.maxCalValue);
        minCarbs = view.findViewById(R.id.minCarbsValue);
        maxCarbs = view.findViewById(R.id.maxCarbsValue);
        minProtein = view.findViewById(R.id.minProValue);
        maxProtein = view.findViewById(R.id.maxProValue);
        minCaffeine = view.findViewById(R.id.minCaffValue);
        maxCaffeine = view.findViewById(R.id.maxCaffValue);
        minAlcohol = view.findViewById(R.id.minAlcValue);
        maxAlcohol = view.findViewById(R.id.maxAlcValue);
        minCalcium = view.findViewById(R.id.minCalciumValue);
        maxCalcium = view.findViewById(R.id.maxCalciumValue);
        minCholesterol = view.findViewById(R.id.minCholValue);
        maxCholesterol = view.findViewById(R.id.maxCholValue);
        minVitaminA = view.findViewById(R.id.minVitAValue);
        maxVitaminA = view.findViewById(R.id.maxVitAValue);
        minVitaminC = view.findViewById(R.id.minVitCValue);
        maxVitaminC = view.findViewById(R.id.maxVitCValue);
        minVitaminD = view.findViewById(R.id.minVitDValue);
        maxVitaminD = view.findViewById(R.id.maxVitDValue);
        minVitaminE = view.findViewById(R.id.minVitEValue);
        maxVitaminE = view.findViewById(R.id.maxVitEValue);
        minVitaminK = view.findViewById(R.id.minVitKValue);
        maxVitaminK = view.findViewById(R.id.maxVitKValue);

        // Seekbars
        minFatBar = view.findViewById(R.id.minFatBar);
        maxFatBar = view.findViewById(R.id.maxFatBar);
        minCalBar = view.findViewById(R.id.minCalBar);
        maxCalBar = view.findViewById(R.id.maxCalBar);
        minCarbsBar = view.findViewById(R.id.minCarbsBar);
        maxCarbsBar = view.findViewById(R.id.maxCarbsBar);
        minProteinBar = view.findViewById(R.id.minProBar);
        maxProteinBar = view.findViewById(R.id.maxProBar);
        minCaffeineBar = view.findViewById(R.id.minCaffBar);
        maxCaffeineBar = view.findViewById(R.id.maxCaffBar);
        minAlcoholBar = view.findViewById(R.id.minAlcBar);
        maxAlcoholBar = view.findViewById(R.id.maxAlcBar);
        minCalciumBar = view.findViewById(R.id.minCalciumBar);
        maxCalciumBar = view.findViewById(R.id.maxCalciumBar);
        minCholesterolBar = view.findViewById(R.id.minCholBar);
        maxCholesterolBar = view.findViewById(R.id.maxCholBar);
        minVitABar = view.findViewById(R.id.minVitABar);
        maxVitABar = view.findViewById(R.id.maxVitABar);
        minVitCBar = view.findViewById(R.id.minVitCBar);
        maxVitCBar = view.findViewById(R.id.maxVitCBar);
        minVitDBar = view.findViewById(R.id.minVitDBar);
        maxVitDBar = view.findViewById(R.id.maxVitDBar);
        minVitEBar = view.findViewById(R.id.minVitEBar);
        maxVitEBar = view.findViewById(R.id.maxVitEBar);
        minVitKBar = view.findViewById(R.id.minVitKBar);
        maxVitKBar = view.findViewById(R.id.maxVitKBar);


//        RelativeLayout setInvisible = view.findViewById(R.id.minCaffLayout);
//        setInvisible.setVisibility(View.GONE);
//        setInvisible = view.findViewById(R.id.maxCaffLayout);
//        setInvisible.setVisibility(View.GONE);
//        setInvisible = view.findViewById(R.id.minAlcLayout);
//        setInvisible.setVisibility(View.GONE);
//        setInvisible = view.findViewById(R.id.maxAlcLayout);
//        setInvisible.setVisibility(View.GONE);
//        setInvisible = view.findViewById(R.id.minVitDLayout);
//        setInvisible.setVisibility(View.GONE);
//        setInvisible = view.findViewById(R.id.maxVitDLayout);
//        setInvisible.setVisibility(View.GONE);
//        setInvisible = view.findViewById(R.id.minVitELayout);
//        setInvisible.setVisibility(View.GONE);
//        setInvisible = view.findViewById(R.id.maxVitELayout);
//        setInvisible.setVisibility(View.GONE);

        // Btn
        recipeSearchBtn = view.findViewById(R.id.recipeSearchBtn);

        // Layout for recipe advanced
        recipeLayout = view.findViewById(R.id.recipeLayout);
        ingredientLayout = view.findViewById(R.id.ingredientlayout);


        spinner = view.findViewById(R.id.spinner1);
        ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(LTRadapter);

        // Set spinner handler
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Your code here
                System.out.println(values[i]);
                if (values[i] == "Ingredient") {
                    recipeLayout.setVisibility(View.GONE);
                    ingredientLayout.setVisibility(View.VISIBLE);
                } else {
                    recipeLayout.setVisibility(View.VISIBLE);
                    ingredientLayout.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    private void setBarListener() {
        minFat.setText(String.valueOf(minFatBar.getProgress() + 1) );
        maxFat.setText(String.valueOf(maxFatBar.getProgress() + 1) );
        minProtein.setText(String.valueOf(minProteinBar.getProgress() + 10) );
        maxProtein.setText(String.valueOf(maxProteinBar.getProgress()  + 10));
        minCarbs.setText(String.valueOf(minCarbsBar.getProgress() + 10));
        maxCarbs.setText(String.valueOf(maxCarbsBar.getProgress() + 10) );
        minCal.setText(String.valueOf(minCalBar.getProgress() + 50));
        maxCal.setText(String.valueOf(maxCalBar.getProgress() + 50));
        minCaffeine.setText(String.valueOf(minCaffeineBar.getProgress()));
        maxCaffeine.setText(String.valueOf(maxCaffeineBar.getProgress()));
        minAlcohol.setText(String.valueOf(minAlcoholBar.getProgress()));
        maxAlcohol.setText(String.valueOf(maxAlcoholBar.getProgress()));
        minCalcium.setText(String.valueOf(minCalciumBar.getProgress()));
        maxCalcium.setText(String.valueOf(maxCalciumBar.getProgress()));
        minCholesterol.setText(String.valueOf(minCholesterolBar.getProgress()));
        maxCholesterol.setText(String.valueOf(maxCholesterolBar.getProgress()));
        minVitaminA.setText(String.valueOf(minVitABar.getProgress()));
        maxVitaminA.setText(String.valueOf(maxVitABar.getProgress()));
        minVitaminC.setText(String.valueOf(minVitCBar.getProgress()));
        maxVitaminC.setText(String.valueOf(maxVitCBar.getProgress()));
        minVitaminD.setText(String.valueOf(minVitDBar.getProgress()));
        maxVitaminD.setText(String.valueOf(maxVitDBar.getProgress()));
        minVitaminE.setText(String.valueOf(minVitEBar.getProgress()));
        maxVitaminE.setText(String.valueOf(maxVitEBar.getProgress()));
        minVitaminK.setText(String.valueOf(minVitKBar.getProgress()));
        maxVitaminK.setText(String.valueOf(maxVitKBar.getProgress()));


        minFatBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                progress += 1;
                if (progress > Integer.valueOf((String) maxFat.getText())){
                    progress = Integer.valueOf((String)maxFat.getText());
                    minFatBar.setProgress(progress);
                }
                minFat.setText(String.valueOf(progress));
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

        maxFatBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                progress += 1;
                if (progress < Integer.valueOf((String) minFat.getText())){
                    progress = Integer.valueOf((String) minFat.getText());
                    maxFatBar.setProgress(progress);
                }
                maxFat.setText(String.valueOf(progress));
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
        minCalBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                progress += 50;
                if (progress > Integer.valueOf((String) maxCal.getText())){
                    progress = Integer.valueOf((String) maxCal.getText());
                    minCalBar.setProgress(progress);
                }
                minCal.setText(String.valueOf(progress));
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
        maxCalBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                progress += 50;
                if (progress < Integer.valueOf((String) minCal.getText())){
                    progress = Integer.valueOf((String) minCal.getText());
                    maxCalBar.setProgress(progress);
                }
                maxCal.setText(String.valueOf(progress));
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
        minCarbsBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                progress += 10;
                if (progress > Integer.valueOf((String) maxCarbs.getText())){
                    progress = Integer.valueOf((String) maxCarbs.getText());
                    minCarbsBar.setProgress(progress);
                }
                minCarbs.setText(String.valueOf(progress));
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
        maxCarbsBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                progress += 10;
                if (progress < Integer.valueOf((String) minCarbs.getText())){
                    progress = Integer.valueOf((String) minCarbs.getText());
                    maxCarbsBar.setProgress(progress);
                }
                maxCarbs.setText(String.valueOf(progress));
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

        minProteinBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                progress += 10;
                if (progress > Integer.valueOf((String) maxProtein.getText())){
                    progress = Integer.valueOf((String) maxProtein.getText());
                    minProteinBar.setProgress(progress);
                }
                minProtein.setText(String.valueOf(progress));
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

        maxProteinBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                progress += 10;
                if (progress < Integer.valueOf((String) minProtein.getText())){
                    progress = Integer.valueOf((String) minProtein.getText());
                    maxProteinBar.setProgress(progress);
                }
                maxProtein.setText(String.valueOf(progress));
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

        minCaffeineBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress > Integer.valueOf((String) maxCaffeine.getText())){
                    progress = Integer.valueOf((String) maxCaffeine.getText());
                    minCaffeineBar.setProgress(progress);
                }
                minCaffeine.setText(String.valueOf(progress));
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

        maxCaffeineBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress < Integer.valueOf((String) minCaffeine.getText())){
                    progress = Integer.valueOf((String) minCaffeine.getText());
                    maxCaffeineBar.setProgress(progress);
                }
                maxCaffeine.setText(String.valueOf(progress));
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

        minAlcoholBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress > Integer.valueOf((String) maxAlcohol.getText())){
                    progress = Integer.valueOf((String) maxAlcohol.getText());
                    minAlcoholBar.setProgress(progress);
                }
                minAlcohol.setText(String.valueOf(progress));
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

        maxAlcoholBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress < Integer.valueOf((String) minAlcohol.getText())){
                    progress = Integer.valueOf((String) minAlcohol.getText());
                    maxAlcoholBar.setProgress(progress);
                }
                maxAlcohol.setText(String.valueOf(progress));
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

        minCalciumBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress > Integer.valueOf((String) maxCalcium.getText())){
                    progress = Integer.valueOf((String) maxCalcium.getText());
                    minCalciumBar.setProgress(progress);
                }
                minCalcium.setText(String.valueOf(progress));
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

        maxCalciumBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress < Integer.valueOf((String) minCalcium.getText())){
                    progress = Integer.valueOf((String) minCalcium.getText());
                    maxCalciumBar.setProgress(progress);
                }
                maxCalcium.setText(String.valueOf(progress));
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

        minCholesterolBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress > Integer.valueOf((String) maxCholesterol.getText())){
                    progress = Integer.valueOf((String) maxCholesterol.getText());
                    minCholesterolBar.setProgress(progress);
                }
                minCholesterol.setText(String.valueOf(progress));
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

        maxCholesterolBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress < Integer.valueOf((String) minCholesterol.getText())){
                    progress = Integer.valueOf((String) minCholesterol.getText());
                    maxCholesterolBar.setProgress(progress);
                }
                maxCholesterol.setText(String.valueOf(progress));
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

        minVitABar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress > Integer.valueOf((String) maxVitaminA.getText())){
                    progress = Integer.valueOf((String) maxVitaminA.getText());
                    minVitABar.setProgress(progress);
                }
                minVitaminA.setText(String.valueOf(progress));
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

        maxVitABar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress < Integer.valueOf((String) minVitaminA.getText())){
                    progress = Integer.valueOf((String) minVitaminA.getText());
                    maxVitABar.setProgress(progress);
                }
                maxVitaminA.setText(String.valueOf(progress));
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

        minVitCBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress > Integer.valueOf((String) maxVitaminC.getText())){
                    progress = Integer.valueOf((String) maxVitaminC.getText());
                    minVitCBar.setProgress(progress);
                }
                minVitaminC.setText(String.valueOf(progress));
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

        maxVitCBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress < Integer.valueOf((String) minVitaminC.getText())){
                    progress = Integer.valueOf((String) minVitaminC.getText());
                    maxVitCBar.setProgress(progress);
                }
                maxVitaminC.setText(String.valueOf(progress));
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

        minVitDBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress > Integer.valueOf((String) maxVitaminD.getText())){
                    progress = Integer.valueOf((String) maxVitaminD.getText());
                    minVitDBar.setProgress(progress);
                }
                minVitaminD.setText(String.valueOf(progress));
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

        maxVitDBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress < Integer.valueOf((String) minVitaminD.getText())){
                    progress = Integer.valueOf((String) minVitaminD.getText());
                    maxVitDBar.setProgress(progress);
                }
                maxVitaminD.setText(String.valueOf(progress));
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

        minVitEBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress > Integer.valueOf((String) maxVitaminE.getText())){
                    progress = Integer.valueOf((String) maxVitaminE.getText());
                    minVitEBar.setProgress(progress);
                }
                minVitaminE.setText(String.valueOf(progress));
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

        maxVitEBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress < Integer.valueOf((String) minVitaminE.getText())){
                    progress = Integer.valueOf((String) minVitaminE.getText());
                    maxVitEBar.setProgress(progress);
                }
                maxVitaminE.setText(String.valueOf(progress));
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

        minVitKBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress > Integer.valueOf((String) maxVitaminK.getText())){
                    progress = Integer.valueOf((String) maxVitaminK.getText());
                    minVitKBar.setProgress(progress);
                }
                minVitaminK.setText(String.valueOf(progress));
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

        maxVitKBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (progress < Integer.valueOf((String) minVitaminK.getText())){
                    progress = Integer.valueOf((String) minVitaminK.getText());
                    maxVitKBar.setProgress(progress);
                }
                maxVitaminK.setText(String.valueOf(progress));
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
    }

    private void setBtnHandler() {
        ingredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isPantryChecked = pantryCheckBox.isChecked();
                String ingredientNames = String.valueOf(ingredientName.getText());
                RecipeSearchByIngredientParam param = new RecipeSearchByIngredientParam(isPantryChecked, ingredientNames);
                searchAPI = (SpoonacularApi.SearchRecipeByIngredient) new SpoonacularApi.SearchRecipeByIngredient(new SpoonacularApi.SearchRecipeByIngredient.SpoonacularReply() {

                    //this override the implemented method from asyncTask
                    @Override
                    public void processFinish(SpoonacularApi.SearchData[] output) {
                        id = new int[output.length];
                        maintitle = new String[output.length];
                        imgURL = new String[output.length];
                        for (int i = 0; i < output.length; i++) {
                            id[i] = output[i].getID();
                            maintitle[i] = output[i].getTitle();
                            imgURL[i] = output[i].getImageURL();

                        }
                        if (output.length > 0){
                            RecipeByIngredientListAdapter adapter = new RecipeByIngredientListAdapter(getActivity(), maintitle, imgURL);
                            list = getActivity().findViewById(R.id.recipeListByIngredient);
                            list.setAdapter(adapter);

                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id_) {

                                    Intent i = new Intent(getActivity(), DetailRecipe.class);
                                    i.putExtra("recipeID", Integer.toString(id[position]));
                                    startActivity(i);
                                }
                            });
                        }
                        else {
                            ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list, R.id.stepList, Collections.singletonList("No recipe is found..."));
                            list = getActivity().findViewById(R.id.recipeListByIngredient);
                            list.setAdapter(itemAdapter);
                            list.setClickable(false);
                        }

                    }
                }).execute(param);
            }
        });

        recipeSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipeTxt = String.valueOf(recipeName.getText());
                String cuisineTxt = String.valueOf(cusine.getText());
                String includeIngredientTxt = String.valueOf(includeIngredient.getText());
                String excludeIngredientTxt = String.valueOf(excludeIngredient.getText());
                String intolerancetxt = String.valueOf(intolerance.getText());
                int minFatVal = Integer.valueOf(String.valueOf(minFat.getText()));
                int maxFatVal = Integer.valueOf(String.valueOf(maxFat.getText()));
                int minCalVal = Integer.valueOf(String.valueOf(minCal.getText()));
                int maxCalVal = Integer.valueOf(String.valueOf(maxCal.getText()));
                int minCarbsVal = Integer.valueOf(String.valueOf(minCarbs.getText()));
                int maxCarbsVal = Integer.valueOf(String.valueOf(maxCarbs.getText()));
                int minProteinVal = Integer.valueOf(String.valueOf(minProtein.getText()));
                int maxProteinVal = Integer.valueOf(String.valueOf(maxProtein.getText()));
                int minCaffVal = Integer.valueOf(String.valueOf(minCaffeine.getText()));
                int maxCaffVal = Integer.valueOf(String.valueOf(maxCaffeine.getText()));
                int minAlcVal = Integer.valueOf(String.valueOf(minAlcohol.getText()));
                int maxAlcVal = Integer.valueOf(String.valueOf(maxAlcohol.getText()));
                int minCalciumVal = Integer.valueOf(String.valueOf(minCalcium.getText()));
                int maxCalciumVal = Integer.valueOf(String.valueOf(maxCalcium.getText()));
                int minCholVal = Integer.valueOf(String.valueOf(minCholesterol.getText()));
                int maxCholVal = Integer.valueOf(String.valueOf(maxCholesterol.getText()));
                int minVitAVal = Integer.valueOf(String.valueOf(minVitaminA.getText()));
                int maxVitAVal = Integer.valueOf(String.valueOf(maxVitaminA.getText()));
                int minVitCVal = Integer.valueOf(String.valueOf(minVitaminC.getText()));
                int maxVitCVal = Integer.valueOf(String.valueOf(maxVitaminC.getText()));
                int minVitDVal = Integer.valueOf(String.valueOf(minVitaminD.getText()));
                int maxVitDVal = Integer.valueOf(String.valueOf(maxVitaminD.getText()));
                int minVitEVal = Integer.valueOf(String.valueOf(minVitaminE.getText()));
                int maxVitEVal = Integer.valueOf(String.valueOf(maxVitaminE.getText()));
                int minVitKVal = Integer.valueOf(String.valueOf(minVitaminK.getText()));
                int maxVitKVal = Integer.valueOf(String.valueOf(maxVitaminK.getText()));

                RecipeAdvancedSearchParam param = new RecipeAdvancedSearchParam(recipeTxt,cuisineTxt,
                        includeIngredientTxt,excludeIngredientTxt,intolerancetxt,minFatVal,maxFatVal,
                        minCalVal,maxCalVal,minCarbsVal,maxCarbsVal,minProteinVal,maxProteinVal,
                        minCaffVal, maxCaffVal,minAlcVal, maxAlcVal, minCalciumVal, maxCalciumVal, minCholVal, maxCholVal,
                        minVitAVal, maxVitAVal, minVitCVal, maxVitCVal, minVitDVal, maxVitDVal, minVitEVal, maxVitEVal, minVitKVal, maxVitKVal);

                // Sent to the spoonacular api
                advancedSearchAPI = (SpoonacularApi.SearchAdvancedRecipe) new SpoonacularApi.SearchAdvancedRecipe(new SpoonacularApi.SearchAdvancedRecipe.SpoonacularReply() {

                    //this override the implemented method from asyncTask
                    @Override
                    public void processFinish(SpoonacularApi.SearchData[] output) {
                        advance_id = new int[output.length];
                        advance_maintitle = new String[output.length];
                        advance_imgURL = new String[output.length];
                        for (int i = 0; i < output.length; i++) {
                            advance_id[i] = output[i].getID();
                            advance_maintitle[i] = output[i].getTitle();
                            advance_imgURL[i] = output[i].getImageURL();

                        }

                        if (output.length > 0){
                            RecipeByIngredientListAdapter adapter = new RecipeByIngredientListAdapter(getActivity(), advance_maintitle, advance_imgURL);
                            list = getActivity().findViewById(R.id.advancedSearchList);
                            list.setAdapter(adapter);

                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id_) {

                                    Intent i = new Intent(getActivity(), DetailRecipe.class);
                                    i.putExtra("recipeID", Integer.toString(advance_id[position]));
                                    startActivity(i);
                                }
                            });
                        }
                        else {
                            ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list, R.id.stepList, Collections.singletonList("No recipe is found..."));
                            list = getActivity().findViewById(R.id.advancedSearchList);
                            list.setAdapter(itemAdapter);
                            list.setClickable(false);
                        }

                    }
                }).execute(param);

            }
        });

    }



    public static class RecipeSearchByIngredientParam {
        private boolean isPantryChecked;
        private String ingredients;

        RecipeSearchByIngredientParam(boolean isPantryChecked, String ingredients) {
            this.isPantryChecked = isPantryChecked;
            this.ingredients = ingredients;
        }

        public boolean isPantryChecked() {
            return isPantryChecked;
        }

        public String getIngredients() {
            return ingredients;
        }
    }

    public static class RecipeAdvancedSearchParam {

        // Params for the advanced search
        private String recipeName;
        private String cusine;
        private String includeIngredient;
        private String excludeIngerdient;
        private String intolerance;
        private int minFat;
        private int maxFat;
        private int minCal;
        private int maxCal;
        private int minCarbs;
        private int maxCarbs;
        private int minProtein;
        private int maxProtein;
        private int minCaff;
        private int maxCaff;
        private int minAlc;
        private int maxAlc;
        private int minCalcium;
        private int maxCalcium;
        private int minChol;
        private int maxChol;
        private int minVitA;
        private int maxVitA;
        private int minVitC;
        private int maxVitC;
        private int minVitD;
        private int maxVitD;
        private int minVitE;
        private int maxVitE;
        private int minVitK;
        private int maxVitK;

        RecipeAdvancedSearchParam(String recipeName, String cusine, String includeIngredient, String excludeIngredient,
                                  String intolerance, int minFat, int maxFat, int minCal, int maxCal, int minCarbs, int maxCarbs,
                                  int minProtein, int maxProtein, int minCaff, int maxCaff, int minAlc, int maxAlc, int minCalcium,
                                  int maxCalcium, int minChol, int maxChol, int minVitA, int maxVitA, int minVitC, int maxVitC,
                                  int minVitD, int maxVitD, int minVitE, int maxVitE, int minVitK, int maxVitK){
            this.recipeName = recipeName;
            this.cusine = cusine;
            this.includeIngredient = includeIngredient;
            this.excludeIngerdient = excludeIngredient;
            this.intolerance = intolerance;
            this.minFat = minFat;
            this.maxFat = maxFat;
            this.minCal = minCal;
            this.maxCal = maxCal;
            this.minCarbs = minCarbs;
            this.maxCarbs = maxCarbs;
            this.minProtein = minProtein;
            this.maxProtein = maxProtein;
            this.minCaff = minCaff;
            this.maxCaff = maxCaff;
            this.minAlc = minAlc;
            this.maxAlc = maxAlc;
            this.minCalcium = minCalcium;
            this.maxCalcium = maxCalcium;
            this.minChol = minChol;
            this.maxChol = maxChol;
            this.minVitA = minVitA;
            this.maxVitA = maxVitA;
            this.minVitC = minVitC;
            this.maxVitC = maxVitC;
            this.minVitD = minVitD;
            this.maxVitD = maxVitD;
            this.minVitE = minVitE;
            this.maxVitE = maxVitE;
            this.minVitK = minVitK;
            this.maxVitK = maxVitK;

        }

        public String getRecipeName() {
            return recipeName;
        }

        public String getCusine() {
            return cusine;
        }

        public String getIncludeIngredient() {
            return includeIngredient;
        }

        public String getExcludeIngerdient() {
            return excludeIngerdient;
        }

        public String getIntolerance() {
            return intolerance;
        }

        public int getMinFat() {
            return minFat;
        }

        public int getMaxFat() {
            return maxFat;
        }

        public int getMinCal() {
            return minCal;
        }

        public int getMaxCal() {
            return maxCal;
        }

        public int getMinCarbs() {
            return minCarbs;
        }

        public int getMaxCarbs() {
            return maxCarbs;
        }

        public int getMinProtein() {
            return minProtein;
        }

        public int getMaxProtein() {
            return maxProtein;
        }

        public int getMinCaff() {
            return minCaff;
        }

        public int getMaxCaff() {
            return maxCaff;
        }

        public int getMinAlc() {
            return minAlc;
        }

        public int getMaxAlc() {
            return maxAlc;
        }

        public int getMinCalcium() {
            return minCalcium;
        }

        public int getMaxCalcium() {
            return maxCalcium;
        }

        public int getMinChol() {
            return minChol;
        }

        public int getMaxChol() {
            return maxChol;
        }

        public int getMinVitA() {
            return minVitA;
        }

        public int getMaxVitA() {
            return maxVitA;
        }

        public int getMinVitC() {
            return minVitC;
        }

        public int getMaxVitC() {
            return maxVitC;
        }

        public int getMinVitD() {
            return minVitD;
        }

        public int getMaxVitD() {
            return maxVitD;
        }

        public int getMinVitE() {
            return minVitE;
        }

        public int getMaxVitE() {
            return maxVitE;
        }

        public int getMinVitK() {
            return minVitK;
        }

        public int getMaxVitK() {
            return maxVitK;
        }
    }
}
