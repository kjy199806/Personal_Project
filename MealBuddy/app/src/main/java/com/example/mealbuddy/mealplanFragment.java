package com.example.mealbuddy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealbuddy.apitools.SpoonacularApi;
import com.example.mealbuddy.databasetools.databasehelper;
import com.example.mealbuddy.decorators.EventDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class mealplanFragment extends Fragment {
    databasehelper db;

    private MaterialCalendarView cv_date_m;
    private Button showmp_m;
    private Button for_day;
    private Button for_week;

    ListView list;

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
    private int lel = 0;

    SpoonacularApi.GenerateMealplanByDay searchAPI = null;
    SpoonacularApi.SearchRecipeById searchAPIrecipy = null;
    SpoonacularApi.SearchIngredientById searchIngredientApi = null;

    String datestr = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.meal_plan, container, false);
        db = new databasehelper(view.getContext());
        //assigning listview

        cv_date_m = view.findViewById(R.id.CV_date);
        showmp_m = view.findViewById(R.id.shwmeplan);
        for_day = view.findViewById(R.id.foraday);
        cv_date_m.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                datestr = date.toString();
            }
        });

        showmp_m.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {

                    datestr = cv_date_m.getSelectedDate().toString();
                    toastMessage(datestr);
                    datestr = datestr.replace("CalendarDay", "");
                    datestr = datestr.replaceAll("[\\{\\}]", "");
                } catch (Exception e){

                }
                if(datestr != null){
                    Intent i = new Intent(getActivity(), daymealplan.class);
                    i.putExtra("datemealplan", datestr);
                    startActivity(i);
                } else {
                    toastMessage("Choose the date!");
                }
            }
        });

        Cursor data = db.getDates("ALLDAYS");
        String[] realdates = new String[data.getCount()];

        if (data.moveToFirst())
        {
            for (int i = 0; i < data.getCount(); i++)
            {
                realdates[i] = data.getString(0);
                data.moveToNext();
                System.out.println(realdates[i]);
            }
        }
        List<CalendarDay> callendardays = new ArrayList<CalendarDay>();
        for(int i = 0; i < realdates.length; i++){
            try {
                String[] date = realdates[i].split("-");
                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int day = Integer.parseInt(date[2]);
                CalendarDay calday = CalendarDay.from(year, month, day);
                callendardays.add(calday);
            } catch (Exception e){

            }
        }

        cv_date_m.addDecorator(new EventDecorator(Color.parseColor("#FF0000"), callendardays));

        for_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DayPlanGenerator.class);
                startActivityForResult(i, 4);
            }
        });
        return view;
    };
    private void toastMessage(String message){
        Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show();
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

}

