package com.example.ddbssj;

import android.content.Intent;
import android.net.Uri;
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

import com.example.ddbssj.adapter.ListAdapter;
import com.example.ddbssj.model.GalleryListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    ArrayList<GalleryListItem> listItems = new ArrayList<>();
    Toolbar toolbar;
    List<String> listUrls = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.navColor));
        }
        setContentView(R.layout.activity_books);

        //Hooks

        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progress_bar);
        listView = (ListView)findViewById(R.id.books_menu);

        //ToolBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FirebaseDatabase.getInstance().getReference().child("Books")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            listItems.add(new GalleryListItem(data.getKey().toString(), R.drawable.ic_baseline_keyboard_arrow_right_16));
                            listUrls.add(data.getValue().toString());
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
                Intent intent = new Intent(
                        Intent.ACTION_VIEW ,
                        Uri.parse(listUrls.get(i).toString()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

    }
}
