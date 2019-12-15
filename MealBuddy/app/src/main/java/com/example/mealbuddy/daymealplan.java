package com.example.mealbuddy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mealbuddy.databasetools.databasehelper;

import java.util.ArrayList;

public class daymealplan extends AppCompatActivity {
    databasehelper db;

    ListView breakfast;
    ListView lunch;
    ListView dinner;

    LinearLayout single;
    LinearLayout empty;
    LinearLayout btn;

    String dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singlemealplan);
        db = new databasehelper(daymealplan.this);
        Intent receivedIntent = getIntent();
        dates = receivedIntent.getStringExtra("datemealplan");

        breakfast = findViewById(R.id.Blist);
        lunch = findViewById(R.id.Llist);
        dinner = findViewById(R.id.Dlist);
        single = findViewById(R.id.singleLL);
        empty = findViewById(R.id.itsempty);
        btn = findViewById(R.id.dltbnLL);
        Button deletemp = findViewById(R.id.deletebtn);
        TextView TTMB = findViewById(R.id.Btext);
        TextView TTMD = findViewById(R.id.Ltext);
        TextView TTML = findViewById(R.id.Dtext);
        TextView fortoday = findViewById(R.id.textday);

        fortoday.setText("Meal plan for: " + dates);

        populatelist(breakfast, "ALLDAYS", dates, "B");
        populatelist(lunch, "ALLDAYS", dates, "L");
        populatelist(dinner, "ALLDAYS", dates, "D");
        hideemptyones(single, empty, btn, breakfast, dinner, lunch,  TTMB, TTMD, TTML);

        deletemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteallrowsbydate("ALLDAYS", dates);
                finish();
            }
        });
    }
    private void populatelist(ListView list, final String tablename, final String date, String daytime) {
        Cursor data = db.getItemByDateandTime(tablename, date,daytime);
        ArrayList<String> listtitle = new ArrayList<>();
        ArrayList<String> listimg = new ArrayList<>();
        while (data.moveToNext()) {
            listtitle.add(data.getString(0));
            listimg.add(data.getString(5));
        }

        String[] titletxt = new String[listtitle.size()];
        String[] titleimg = new String[listimg.size()];
        titletxt = listtitle.toArray(titletxt);
        titleimg = listimg.toArray(titleimg);

        MyListAdapter adapter= new MyListAdapter(daymealplan.this, titletxt, titleimg);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int num = i + 1;
                String idnum = "" + num;
                TextView something = view.findViewById(R.id.title);
                String recipyname = something.getText().toString();
                Cursor data = db.getItembyName(tablename, recipyname);
                String title = "";
                String shrtdscp = "";
                String ingrdlist = "";
                String fullsteps = "";
                String ingdimg = "";
                String firstimg = "";
                String daytime = "";
                while (data.moveToNext()) {

                    title = data.getString(0);
                    shrtdscp = data.getString(1);
                    ingrdlist = data.getString(2);
                    fullsteps = data.getString(3);
                    ingdimg = data.getString(4);
                    firstimg = data.getString(6);
                    daytime = data.getString(7);
                }

                Intent editScreenIntent = new Intent(daymealplan.this, SavedRecipy.class);
                editScreenIntent.putExtra("id", idnum);
                editScreenIntent.putExtra("title", title);
                editScreenIntent.putExtra("shrtdescp", shrtdscp);
                editScreenIntent.putExtra("ingrdlist", ingrdlist);
                editScreenIntent.putExtra("fullsteps", fullsteps);
                editScreenIntent.putExtra("ingimg", ingdimg);
                editScreenIntent.putExtra("firstimgs", firstimg);
                editScreenIntent.putExtra("tableNM", tablename);
                editScreenIntent.putExtra("daytime", daytime);
                editScreenIntent.putExtra("datetime", date);
                startActivityForResult(editScreenIntent, 2);

            }
        });
    }

    private void hideemptyones(LinearLayout ll_used, LinearLayout empty1,LinearLayout btn, ListView daytimeB, ListView daytimeD, ListView daytimeL, TextView textB, TextView textD, TextView textL){
        if(daytimeB.getAdapter().getCount() == 0 && daytimeD.getAdapter().getCount() == 0 && daytimeL.getAdapter().getCount() == 0){
            ll_used.setVisibility(View.GONE);
            empty1.setVisibility(View.VISIBLE);
            btn.setVisibility(View.GONE);
        }
        if(daytimeB.getAdapter().getCount() == 0){
            daytimeB.setVisibility(View.GONE);
            textB.setVisibility(View.GONE);
        }
        if(daytimeD.getAdapter().getCount() == 0){
            daytimeD.setVisibility(View.GONE);
            textD.setVisibility(View.GONE);
        }
        if(daytimeL.getAdapter().getCount() == 0){
            daytimeL.setVisibility(View.GONE);
            textL.setVisibility(View.GONE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // recreate your fragment here
            if (resultCode == Activity.RESULT_OK) {
                recreate();
            }
        }
    }
}
