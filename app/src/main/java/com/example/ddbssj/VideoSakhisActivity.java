package com.example.ddbssj;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ddbssj.adapter.ImageAdapter;
import com.example.ddbssj.adapter.ListAdapter;
import com.example.ddbssj.model.GalleryListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoSakhisActivity extends AppCompatActivity {
    ListView listView;
    ProgressBar progressBar;
    String videoId;
    List<String> urlList = new ArrayList<>();
    List<String> videoUrlList = new ArrayList<>();
    String name;
    String secondaryName;
    TextView heading;
    Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.navColor));
        }
        setContentView(R.layout.activity_videos_sakhis);

        //Hooks
        listView = findViewById(R.id.video_sakhis_menu);
        progressBar = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.video_sakhis_activity_heading);
        toolbar = findViewById(R.id.toolbar);

        //ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = this.getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            secondaryName = intent.getStringExtra("secondaryName");
            heading.setText(secondaryName);
        }


        FirebaseDatabase.getInstance().getReference().child("Sakhis")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.child(name).child(secondaryName).getChildren()) {
                            videoUrlList.add(data.getValue().toString());
                            videoId = extractVideoIdFromUrl(data.getValue().toString());
                            if (videoId != null || videoId != "") {
                                String img_url = "http://img.youtube.com/vi/" + videoId + "/0.jpg";
                                urlList.add(img_url);
                            }
                        }

                        ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext(), urlList);
                        listView.setAdapter(imageAdapter);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

                    }
                });

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(videoUrlList.get(i)));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }

    public static String extractVideoIdFromUrl(String url) {
        Pattern pattern = Pattern.compile("(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
