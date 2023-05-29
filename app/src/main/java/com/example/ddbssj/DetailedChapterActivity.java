package com.example.ddbssj;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ddbssj.adapter.ListAdapter;
import com.example.ddbssj.model.GalleryListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class DetailedChapterActivity extends AppCompatActivity {

    String chapterText;
    TextView textView;
    Toolbar toolbar;
    ProgressBar progressBar;
    TextView heading;
    String name;
    String secondaryName;
    String chapterName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.navColor));
        }
        setContentView(R.layout.activity_detailed_chapter);

        //Hooks
        heading = findViewById(R.id.chapter_activity_heading);
        textView = findViewById(R.id.chapter_activity_text);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar);

        //ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = this.getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            secondaryName = intent.getStringExtra("secondaryName");
            chapterName = intent.getStringExtra("chapterName");
            heading.setText(chapterName);
        }

        FirebaseDatabase.getInstance().getReference().child("Sakhis")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                        chapterText = dataSnapshot.child(name).child(secondaryName).child(chapterName).getValue().toString();
                        progressBar.setVisibility(View.INVISIBLE);
                        textView.setText(chapterText);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

                    }
                });

    }
}
