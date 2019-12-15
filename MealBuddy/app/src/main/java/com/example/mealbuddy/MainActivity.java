package com.example.mealbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mealbuddy.apitools.model.RouteModels;
import com.example.mealbuddy.datatools.DataToolModels;
import com.example.mealbuddy.datatools.UserDataStore;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private static final String TAG = "MyActivity";
    private String apiKey;
    private String username;
    private DataToolModels.User user;  // provides logged in user data
    private UserDataStore userDataStore;  //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the users data store for main activity
        userDataStore = new UserDataStore(this);

        // Get user if one is setup on device
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

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawed_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        LinearLayout login = header.findViewById(R.id.login);
        LinearLayout signup = header.findViewById(R.id.signup);
        LinearLayout logedin = header.findViewById(R.id.profilelogged);
        ImageView logout = header.findViewById(R.id.logout);
        TextView usernamedsp = header.findViewById(R.id.usernametxt);
        //changing header depending if user logged in or not

        if(username.isEmpty() && apiKey.isEmpty()){
            login.setVisibility(View.VISIBLE);
            signup.setVisibility(View.VISIBLE);
            logedin.setVisibility(View.GONE);
        } else {

            login.setVisibility(View.GONE);
            signup.setVisibility(View.GONE);
            logedin.setVisibility(View.VISIBLE);
            usernamedsp.setText(username);
        }

        //starting login activity
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, login.class);
                startActivityForResult(i, 1);
            }
        });
        //starting sign up activity
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, signup.class);
                startActivity(i);
            }
        });
        //logging out of your account and deleting username and API key stored.
        logout.setOnClickListener(new View.OnClickListener() {
             @Override
              public void onClick(View view) {
                 AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                 builder1.setTitle("Login out.");
                 builder1.setMessage("Are you sure you want to logout?");
                 builder1.setPositiveButton(
                         "Yes",
                         new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {
                                 dialog.cancel();
                                 userDataStore.clearUserData();
                                 recreate();
                             }
                         });
                 builder1.setNegativeButton(
                         "No",
                         new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int id) {
                                 dialog.cancel();
                             }
                         });
                 AlertDialog alert11 = builder1.create();
                 alert11.show();
              }
        });

        logedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Profile.class);
                startActivityForResult(i, 1);
            }
        });

        //drawing sidebar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new homepageFragment()).commit();
            navigationView.setCheckedItem(R.id.home_page);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_page:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new homepageFragment()).commit();
                break;
//            case R.id.usr_rcp:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new userrecipiesFragment()).commit();
//                break;
            case R.id.meal_plan:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new mealplanFragment()).commit();
                break;
            case R.id.grocery_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new grocerylistFragment()).commit();
                break;
            case R.id.advcn_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new advancedsearchFragment()).commit();
                break;
//            case R.id.manage_user:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new userManagementFragment()).commit();
//                Toast.makeText(this, "This extr1 feature is currently unavailable", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.extr2:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new extrafeatureextraFragment()).commit();
//                Toast.makeText(this, "This extr2 feature is currently unavailable", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.policy:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new policyFragment()).commit();
                break;
            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new aboutFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    // MealBuddy Api Interactions

    /* Registration call :

        Registration regUser = new Registration(userid, password, email, age, weight, height, gender);
        RegistrationTask myTask = new RegistrationTask(this); // pass in this activity so task can call reply func.
        myTask.execute(regUser);   // run task and pass in user details.
     */

    public void getRecipeByIdReply (RouteModels.GetRecipeByIdReply _reply){
        // recipe post succese or fail message

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                recreate();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

    }//onActivityResult

}