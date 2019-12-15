package com.example.mealbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mealbuddy.DetailRecipe;
import com.example.mealbuddy.MyIngredientAdapter;
import com.example.mealbuddy.R;
import com.example.mealbuddy.databasetools.databasehelper;
import com.squareup.picasso.Picasso;

public class SavedRecipy extends AppCompatActivity {
    TextView title;
    TextView shortdesc;
    ListView ingrdlist;
    ListView stepslist;
    ImageView firstimages;
    LinearLayout addbtn;
    Button delete;
    databasehelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_recipe);

        db = new databasehelper(SavedRecipy.this);

        addbtn = findViewById(R.id.addtomealplanLL);

        delete = findViewById(R.id.addMealPlanBtn);
        delete.setText("Delete Recipy from Meal Plan");

        Intent receivedIntent = getIntent();
        final String id = receivedIntent.getStringExtra("id");
        final String titletxt = receivedIntent.getStringExtra("title");
        String shortdescptxt = receivedIntent.getStringExtra("shrtdescp");
        String ingridientlist = receivedIntent.getStringExtra("ingrdlist");
        String fullstep = receivedIntent.getStringExtra("fullsteps");
        String imgstr = receivedIntent.getStringExtra("ingimg");
        String firstimg = receivedIntent.getStringExtra("firstimgs");
        final String tablename = receivedIntent.getStringExtra("tableNM");
        final String daytime = receivedIntent.getStringExtra("daytime");
        final String date = receivedIntent.getStringExtra("datetime");

        String[] ingridlistarr = convertStringToArray(ingridientlist);
        String[] allsteps = convertStringToArray(fullstep);
        String[] imgarr = convertStringToArray(imgstr);

        title = findViewById(R.id.rcptittledetail);
        shortdesc = findViewById(R.id.shrtdscription);
        ingrdlist = findViewById(R.id.ingredientList);
        stepslist = findViewById(R.id.instruction);
        firstimages = findViewById(R.id.firstimage);

        Picasso.with(SavedRecipy.this).load(firstimg).into(firstimages);

        title.setText(titletxt);
        shortdesc.setText(shortdescptxt);

        MyIngredientAdapter adapter= new MyIngredientAdapter(SavedRecipy.this, ingridlistarr, imgarr);
        ingrdlist.setAdapter(adapter);

        ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(SavedRecipy.this, R.layout.simple_list, R.id.stepList, allsteps);
        stepslist.setAdapter(itemAdapter);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deletespecificrows(tablename, titletxt, daytime, date);

                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
    public static String strSeparator = "__,__";
    public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }

}
