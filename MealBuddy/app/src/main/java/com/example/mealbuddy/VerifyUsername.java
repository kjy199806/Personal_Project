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

import com.example.mealbuddy.apitools.MealBuddyTasks;
import com.example.mealbuddy.apitools.model.RouteModels;

public class VerifyUsername extends AppCompatActivity {

    // Verification Code
    private EditText verificationCodedt;

    // Submit button
    private Button submitBtn;

    private String forgotToken;
    private String emailString;
    private String userIdString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_code);

        verificationCodedt = findViewById(R.id.verificationCode);
        submitBtn = findViewById(R.id.submitButton);


        if (savedInstanceState == null) {  //recieving intents data passed from forgot password activity
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                emailString= null;
                userIdString = null;
            } else {
                emailString= extras.getString("myEmail");
                userIdString= extras.getString("myUserID");
            }
        } else {
            emailString= (String) savedInstanceState.getSerializable("myEmail");
            userIdString= (String) savedInstanceState.getSerializable("myUserID");
        }

        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 forgotToken = verificationCodedt.getText().toString().trim();
                System.out.println(forgotToken + "\n" + emailString);
                RouteModels.ConfirmRegister checkToken = new RouteModels.ConfirmRegister(emailString, forgotToken, userIdString);
                MealBuddyTasks.ConfirmRegisterTask myCheckTokenTask = new MealBuddyTasks.ConfirmRegisterTask(VerifyUsername.this);
                myCheckTokenTask.execute(checkToken);


            }

        });
    }
    public void ShowSuccessful(Context context, String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Successful");
        builder1.setMessage(msg);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent (VerifyUsername.this, login.class);
                        startActivity(i);
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
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

    public void confirmRegisterReply(RouteModels.ConfirmRegisterReply _reply){
        System.out.println("Forgot Reply: " + _reply.toString());

        String mess = _reply.getMessage();
        if(mess.equals("correct token")){
            ShowSuccessful(VerifyUsername.this,mess);
        }
        else{
            ShowError(VerifyUsername.this, mess);
        }

    }


}