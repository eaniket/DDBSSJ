package com.example.ddbssj;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

public class JoinWAActivity extends AppCompatActivity {

    EditText name;
    EditText city;
    EditText phoneNumber;
    Toolbar toolbar;
    ProgressBar progressBar;
    AppCompatButton submitbutton;

    String formName;
    String formCity;
    String formPhoneNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.navColor));
        }
        setContentView(R.layout.activity_joinwa);

        //hooks
        name = findViewById(R.id.form_name);
        city = findViewById(R.id.form_city);
        phoneNumber = findViewById(R.id.form_phone);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar);
        submitbutton = findViewById(R.id.submit);

        //ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formName = name.getText().toString();
                formCity = city.getText().toString();
                formPhoneNumber = phoneNumber.getText().toString();


            }
        });

    }
}
