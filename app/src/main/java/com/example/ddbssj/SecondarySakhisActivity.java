package com.example.ddbssj;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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

public class SecondarySakhisActivity  extends AppCompatActivity {
    TextView heading;
    String name;
    Toolbar toolbar;
    ProgressBar progressBar;
    ArrayList<GalleryListItem> listItems = new ArrayList<>();
    ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.navColor));
        }
        setContentView(R.layout.activity_secondary_sakhis);

        //hooks
        heading = findViewById(R.id.secondary_sakhi_activity_heading);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar);
        listView = findViewById(R.id.secondary_sakhis_menu);

        //ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = this.getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            heading.setText(name);
        }

        FirebaseDatabase.getInstance().getReference().child("Sakhis")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.child(name).getChildren()) {
                            listItems.add(new GalleryListItem(data.getKey().toString(), R.drawable.ic_baseline_keyboard_arrow_right_16));
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                        ListAdapter listAdapter = new ListAdapter(getApplicationContext(), listItems);
                        listView.setAdapter(listAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

                    }
                });


        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                if(name.equals("Read Sakhis")){
                    intent = new Intent(SecondarySakhisActivity.this, ChapterSakhisActivity.class);
                }else{
                    intent = new Intent(SecondarySakhisActivity.this, VideoSakhisActivity.class);
                }

                intent.putExtra("secondaryName", listItems.get(i).getName().toString());
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }
}
