package com.example.mealbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ViewOtherProfile extends AppCompatActivity {

    private EditText usernameValue;
    private Button viewOtherProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_other_profile);
        setViewByID();
        setBtnHandler();
    }

    private void setViewByID() {
        viewOtherProfileBtn = findViewById(R.id.viewOtherProfileBtn);
        usernameValue = findViewById(R.id.viewOtherProfileValue);
    }

    private void setBtnHandler(){
        viewOtherProfileBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent (ViewOtherProfile.this, ViewOther.class);
                String username = usernameValue.getText().toString();
                i.putExtra("username", username);
                startActivity(i);
            }
        });
    }
}