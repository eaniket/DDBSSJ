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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ddbssj.adapter.ListAdapter;
import com.example.ddbssj.model.GalleryListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    ArrayList<GalleryListItem> listItems = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.navColor));
        }
        setContentView(R.layout.activity_gallery);

        //hooks
        listView = (ListView)findViewById(R.id.gallery_menu);
        progressBar = findViewById(R.id.progress_bar);

        FirebaseDatabase.getInstance().getReference().child("Gallery")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            listItems.add(new GalleryListItem(data.getKey().toString(), R.drawable.ic_baseline_keyboard_arrow_right_16));
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                        ListAdapter listAdapter = new ListAdapter(getApplicationContext(), listItems);
                        listView.setAdapter(listAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {
                        Log.e("DB error", "Gallery images call failed");
                    }
                });

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(GalleryActivity.this, DetailedGalleryActivity.class);
                intent.putExtra("name", listItems.get(i).getName().toString());
                startActivity(intent);
            }
        });
    }
}
