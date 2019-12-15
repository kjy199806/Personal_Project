package com.example.mealbuddy;

import android.app.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealbuddy.apitools.MealBuddyTasks;
import com.example.mealbuddy.apitools.SpoonacularApi;
import com.example.mealbuddy.apitools.model.RouteModels;
import com.example.mealbuddy.datatools.DataToolModels;
import com.example.mealbuddy.datatools.UserDataStore;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

public class MyIngredientAdapter extends ArrayAdapter<String> {
    private UserDataStore userDataStore;
    private final Activity context;
    private final String[] maintitle;
    private final String[] imgURL;
    private  String measure;
    private  String name;
    private  String units;
    TextView title;
    EditText quantity;
    TextView measurement;

    public MyIngredientAdapter(Activity context, String[] maintitle, String[] imgURL) {
        super(context, R.layout.mylist, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.imgURL=imgURL;


    }

    public MyIngredientAdapter(Activity context, String[] maintitle) {
        super(context, R.layout.mylist, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.imgURL=null;


    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.ingredient_list, null,true);



        final TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        ImageButton shoppingListBtn = (ImageButton) rowView.findViewById(R.id.addShoppingListBtn);

        titleText.setText(maintitle[position]);
        if(imgURL==null){

        }
        else{
            Picasso.with(context).load("https://spoonacular.com/cdn/ingredients_100x100/"+ imgURL[position]).into(imageView);
        }


        shoppingListBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                ShowAddGroceryListOption(view, maintitle[position]);
            }
        });

        return rowView;

    }
    public void ShowAddGroceryListOption(View view, final String titleText){

        LayoutInflater inflater=context.getLayoutInflater();
        View groceryDialogView = inflater.inflate(R.layout.grocerydialog, null);
//        ViewGroup viewGroup = view.findViewById(android.R.id.content);
//        View groceryDialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.grocerydialog, viewGroup, false);


        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());


//        System.out.println("quantity " + view.findViewById(R.id.quantityValueText) + " quantityValueText " + R.id.quantityValueText);
//        System.out.println("measurement " + measurement + " measurement " + R.id.measurement);

        final String[] data = titleText.split(":", 0);


        builder.setTitle("How much/many do you want to add " + data[0] + " into your grocery list?");
//        LayoutInflater inflater = LayoutInflater.from(context);
        title = groceryDialogView.findViewById(R.id.ingredientNameText);
        title.setText("testing");
        quantity = groceryDialogView.findViewById(R.id.quantityValueText);
        quantity.setText("testing");
        measurement = groceryDialogView.findViewById(R.id.measurement);
        measurement.setText("Testing");

        title.setText(data[0]);
        final String[] data2 = data[1].split(" ", 0);
        name = data[0];
        if(data2.length > 2){
            measure = data2[2];
            measurement.setText(data2[2]);
        }
        else{
            measurement.setText("");
            measure = "";
        }
         units = data2[1];
        quantity.setText(data2[1]);


        builder.setView(groceryDialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        userDataStore = new UserDataStore(getContext());
                        DataToolModels.User user = userDataStore.getStoredUserData();
                        String key = user.getApiKey();
                        String usern = user.getUsername();
                        String defaultList = user.getGL();

                       String q = units.toString();
                       float i= Float.parseFloat(q);

                       int quant = (int) i;
                        System.out.println(key + defaultList + name + usern + measure + quant);
                        RouteModels.EditGroceryList addGroceryItem = new RouteModels.EditGroceryList(key, defaultList , name ,usern , measure, quant, 4);
                        MealBuddyTasks.AddGroceryListItemTask myAGroceryListItemTask = new MealBuddyTasks.AddGroceryListItemTask(MyIngredientAdapter.this);
                        myAGroceryListItemTask.execute(addGroceryItem);
                        Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(context, "CANCEL", Toast.LENGTH_SHORT).show();
                    }
                });


        builder.create().show();
    }

    public void addGroceryListReply(RouteModels.AddGroceryListReply _reply) {
        System.out.println("View Add Grocery List Item Reply: " + _reply.toString());
        String mess = _reply.getStatus();
        if(mess.equals("success")){
            //set local memory grocery list from response array
            userDataStore = new UserDataStore(context);
            DataToolModels.User user = userDataStore.getStoredUserData();
            user.setDgl(_reply.getGroceryList());
            userDataStore.storeUserData(user);
            //ShowSuccessful(VerifyUsername.this,mess);


        }
        else{
            // ShowError(VerifyUsername.this, mess);
        }

    }
}
