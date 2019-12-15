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

import  com.example.mealbuddy.apitools.MealBuddyTasks.CheckTokenTask;
import com.example.mealbuddy.apitools.model.RouteModels.ForgotReply;
import com.example.mealbuddy.apitools.model.RouteModels.Forgot;

public class VerifyPassword extends AppCompatActivity {

    // Verification Code
    private EditText verificationCodedt;
    private String forgotToken;
    private String emailString;
    private String userIdString;
    // Submit button
    private Button submitBtn;

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
                Forgot checkToken = new Forgot(emailString, forgotToken, userIdString);
                CheckTokenTask myCheckTokenTask = new CheckTokenTask(VerifyPassword.this);
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
                        Intent i = new Intent (VerifyPassword.this, ChangePassword.class);
                        i.putExtra("myUserID",userIdString);
                        i.putExtra("myEmail", emailString); //pass data into change password activity
                        i.putExtra("myForgotToken",forgotToken);
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

    public void forgotReply(ForgotReply _reply){
        System.out.println("Forgot Reply: " + _reply.toString());

        String mess = _reply.getMessage();
        if(mess.equals("correct token")){
            ShowSuccessful(VerifyPassword.this,mess);
        }
        else{
            ShowError(VerifyPassword.this, mess);
        }

    }
}