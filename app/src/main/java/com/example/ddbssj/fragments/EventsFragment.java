package com.example.ddbssj.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ddbssj.DetailedEventActivity;
import com.example.ddbssj.DetailedGalleryActivity;
import com.example.ddbssj.GalleryActivity;
import com.example.ddbssj.R;
import com.example.ddbssj.adapter.ListAdapter;
import com.example.ddbssj.model.GalleryListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EventsFragment extends Fragment {
    public EventsFragment(){}

    ListView listView;
    ProgressBar progressBar;
    ArrayList<GalleryListItem> listItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView)getView().findViewById(R.id.events_menu);
        progressBar = getView().findViewById(R.id.progress_bar);

        FirebaseDatabase.getInstance().getReference().child("Events")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            listItems.add(new GalleryListItem(data.getKey().toString(), R.drawable.ic_baseline_keyboard_arrow_right_16));
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                        ListAdapter listAdapter = new ListAdapter(getActivity().getApplicationContext(), listItems);
                        listView.setAdapter(listAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {
                        Log.e("DB error", "Events call failed");
                    }
                });

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity().getApplicationContext(), DetailedEventActivity.class);
                intent.putExtra("name", listItems.get(i).getName().toString());
                startActivity(intent);
            }
        });
    }
}
