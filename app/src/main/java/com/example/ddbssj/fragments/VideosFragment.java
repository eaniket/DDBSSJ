package com.example.ddbssj.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ddbssj.DetailedGalleryActivity;
import com.example.ddbssj.GalleryActivity;
import com.example.ddbssj.R;
import com.example.ddbssj.adapter.ImageAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideosFragment  extends Fragment {
    public VideosFragment(){}

    ListView listView;
    ProgressBar progressBar;
    String videoId;
    List<String> urlList = new ArrayList<>();
    List<String> videoUrlList = new ArrayList<>();

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_videos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Hooks
        listView = getView().findViewById(R.id.video_image_list);
        progressBar = getView().findViewById(R.id.progress_bar);

        FirebaseDatabase.getInstance().getReference().child("Videos")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            videoUrlList.add(data.getValue().toString());
                            videoId = extractVideoIdFromUrl(data.getValue().toString());
                            if(videoId != null || videoId != ""){
                                String img_url = "http://img.youtube.com/vi/"+videoId+"/0.jpg";
                                urlList.add(img_url);
                            }
                        }

                        ImageAdapter imageAdapter = new ImageAdapter(getActivity().getApplicationContext(), urlList);
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
                        Intent.ACTION_VIEW ,
                        Uri.parse(videoUrlList.get(i)));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);
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
