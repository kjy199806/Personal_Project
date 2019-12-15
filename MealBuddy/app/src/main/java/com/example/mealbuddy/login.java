package com.example.mealbuddy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.mealbuddy.apitools.MealBuddyTasks.LoginTask;



import com.example.mealbuddy.apitools.model.RouteModels.Login;
import com.example.mealbuddy.apitools.model.RouteModels.LoginReply;
import com.example.mealbuddy.datatools.DataToolModels;
import com.example.mealbuddy.datatools.UserDataStore;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class login extends AppCompatActivity {

    // Username & Password
    private EditText usernameedt;
    private EditText passwordedt;
    private DataToolModels.User user;

    // Login & Forgot username/password button
    private Button loginBtn;
    private Button forgotUsernameBtn;
    private Button forgotPasswordBtn;
    private UserDataStore userDataStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usernameedt = findViewById(R.id.username);
        passwordedt = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginButton);
        forgotUsernameBtn = findViewById(R.id.forgotUsernameButton);
        forgotPasswordBtn = findViewById(R.id.forgotPasswordButton);

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String usernameText = usernameedt.getText().toString();
                final String pwdText = passwordedt.getText().toString().trim();

                if (usernameText.isEmpty()) {
                    ShowError(login.this, "Username cannot be empty!");
                } else if (pwdText.length() < 8) {
                    ShowError(login.this, "Passwords too short");
                } else if (!isValidPassword(pwdText)) {
                    ShowError(login.this, "Password doesn't meet the requirements. Password should contain ");
                } else if(!usernameText.isEmpty()  &&
                          pwdText.length() >= 8    &&
                          isValidPassword(pwdText) ){

                    Login loggedUser = new Login(usernameText, pwdText);  //replace with usernmae, password...this is just test data
                    LoginTask myLoginTask = new LoginTask(login.this);
                    myLoginTask.execute(loggedUser);

                }


            }

        });

        forgotUsernameBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent (login.this, forgotUsername.class);
                startActivity(i);
            }
        });

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this, forgotPassword.class);
                startActivity(i);
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

    public void ShowSuccessful(final Context context, String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Successful");
        builder1.setMessage(msg);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK,returnIntent);
                        ((Activity) context).finish();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=/])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public void loginReply(LoginReply _reply){

        System.out.println("Login Reply: " + _reply.toString());

        String mess = _reply.getMessage();
        String groceryStatus = _reply.getGroceryListStatus();
        if(mess.equals("Password Accepted")){

                user = new DataToolModels.User(_reply.getUsername(), _reply.getApiKey(), _reply.getDefaultGroceryList(), _reply.getGroceryListsJson(), _reply.getdgljson());
                user.setUsername(_reply.getUsername());
                user.setApiKey(_reply.getApiKey());
                user.setGL(_reply.getDefaultGroceryList());
                user.setGroceryLists(_reply.getGroceryListsJson());
                user.setDgl(_reply.getdgljson());

                userDataStore = new UserDataStore(getApplicationContext());
                userDataStore.storeUserData(user);


                ShowSuccessful(login.this, mess);

        }
        else{
            ShowError(login.this, mess);
        }

    }


}