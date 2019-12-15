package com.example.mealbuddy;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mealbuddy.apitools.MealBuddyTasks;
import com.example.mealbuddy.apitools.model.RouteModels;
import com.example.mealbuddy.datatools.DataToolModels;

import com.example.mealbuddy.datatools.UserDataStore;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {
    private static final String TAG = "MyActivity";
    private EditText gender;
    private Button sendbtn;
    private EditText emailedt;
    private EditText usernameedt;
    private EditText passwordedt;
    private EditText rptpassword;
    private EditText year_edt;
    private EditText month_edt;
    private EditText day_edt;
    private EditText heightedt;
    private EditText weightedt;
    Calendar c;
    DatePickerDialog dpdialog;
    private DataToolModels.User user;

    private UserDataStore userDataStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        emailedt = findViewById(R.id.email);
        usernameedt = findViewById(R.id.username);
        passwordedt = findViewById(R.id.passwordent);
        rptpassword = findViewById(R.id.repeatpwd);
        gender = findViewById(R.id.gender);
        year_edt = findViewById(R.id.year);
        month_edt = findViewById(R.id.month);
        day_edt = findViewById(R.id.day);
        heightedt = findViewById(R.id.height);
        weightedt = findViewById(R.id.weight);
        sendbtn = findViewById(R.id.signupbtn);





        sendbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //strings for fields
                final String emailstr = emailedt.getText().toString().trim();
                final String usernametxt = usernameedt.getText().toString();
                final String pwdtxt = passwordedt.getText().toString().trim();
                final String rptpwdtxt = rptpassword.getText().toString().trim();
                final String gendertxt = gender.getText().toString();
                final String yeartxt = year_edt.getText().toString();
                final String monthtxt = month_edt.getText().toString();
                final String daytxt = day_edt.getText().toString();
                final String heightxt = heightedt.getText().toString();
                final String weighttxt = weightedt.getText().toString();
                //setting up default date
                String year = "0001";
                String month = "01";
                String day = "01";

                String agefinal = "0000-00-00";
                String gender = gendertxt;
                Float heightflt = 0.0f;
                Float weightflt = 0.0f;

                //checking if password is vallid
                if(!emailstr.contains("@")){
                    ShowError(signup.this, "Wrong type of email address");
                } else  if(usernametxt.isEmpty()){
                    ShowError(signup.this, "Username cannot be empty!");
                } else if (pwdtxt.length() < 8) {
                    ShowError(signup.this, "Passwords too short");
                } else if(!isValidPassword(pwdtxt)){
                    ShowError(signup.this, "Password doesn't meet the requirements. Password should contain ");
                } else if(!pwdtxt.equals(rptpwdtxt)){
                    ShowError(signup.this, "Passwords do not match");
                } else if(emailstr.contains("@") && !usernametxt.isEmpty() && pwdtxt.length() >= 8 && isValidPassword(pwdtxt) && pwdtxt.equals(rptpwdtxt)){
                    //parsing height and weight from string to float
                    if(heightxt.isEmpty()){
                        heightflt = Float.parseFloat("0.0");
                    } else {
                        heightflt = Float.parseFloat(heightxt);
                    }
                    if(weighttxt.isEmpty()){
                        weightflt = Float.parseFloat("0.0");
                    } else {
                        weightflt = Float.parseFloat(weighttxt);
                    }
                    //parsing date
                    if(!yeartxt.isEmpty()) {
                        year = yeartxt;
                    } else {
                        year = "0001";
                    }

                    if(!monthtxt.isEmpty()) {
                        month = monthtxt;
                    } else {
                        month = "01";
                    }

                    if(!daytxt.isEmpty()) {
                        day = daytxt;
                    } else {
                        day = "01";
                    }

                    agefinal = year + "-" + month + "-" + day;
                    //setting up gender if empty
                    if(gendertxt.isEmpty()){
                        gender = "Empty";
                    }
                    //making and API call and regestering user
                    RouteModels.Registration regUser = new RouteModels.Registration(usernametxt, pwdtxt, emailstr, agefinal, weightflt,heightflt, gender);
                    MealBuddyTasks.RegistrationTask myTask = new MealBuddyTasks.RegistrationTask(signup.this);
                    myTask.execute(regUser);

                }
            }
        });

    }
    //method for showing error box
     public void ShowError(Context context, String msg){
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
    //method for showing success message and then closing activity
     public void ShowMsg(final Context context, String msg){
        final String emailstr = emailedt.getText().toString().trim();
        final String usernametxt = usernameedt.getText().toString();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Successful Registration!");
        builder1.setMessage(msg);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent (signup.this, VerifyUsername.class);
                        i.putExtra("myUserID",usernametxt);
                        i.putExtra("myEmail", emailstr);
                        startActivity(i);
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    //boolean for valid password pattern
    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=/])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    //receiving reply and showing error or success

    public void registrationReply(RouteModels.RegistrationReply _reply){
        // Use Reply object here
        String status = _reply.getStatus();
        String msg = _reply.getMessage();
        //System.out.println(_reply);

        //user = new DataToolModels.User(_reply.getUsername(), _reply.getApiKey());

        //user.setUsername(_reply.getUsername());
        //user.setApiKey(_reply.getApiKey());
        // = new UserDataStore(getApplicationContext());
        //userDataStore.storeUserData(user);
        //just to show it works

        if(status.equals("success")){
            ShowMsg(signup.this, msg);
        } else {
            ShowError(signup.this, msg);
        }
    }

}
