package com.example.ddbssj.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ddbssj.AboutActivity;
import com.example.ddbssj.ContactUsActivity;
import com.example.ddbssj.GalleryActivity;
import com.example.ddbssj.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    public HomeFragment(){};

    ImageSlider imageSlider;
    CardView aboutCard;
    CardView galleryCard;
    CardView liveCard;
    CardView facebookCard;
    CardView joinWACard;
    CardView contactUsCard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Hooks
        imageSlider = getView().findViewById(R.id.image_slider);
        aboutCard = getView().findViewById(R.id.aboutCard);
        galleryCard = getView().findViewById(R.id.galleryCard);
        liveCard = getView().findViewById(R.id.liveCard);
        facebookCard = getView().findViewById(R.id.facebookCard);
        joinWACard = getView().findViewById(R.id.joinwaCard);
        contactUsCard = getView().findViewById(R.id.contactUsCard);

        final ArrayList<SlideModel> remoteImages = new ArrayList<>();
        try {
            FirebaseDatabase.getInstance().getReference().child("Slider_Image")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                remoteImages.add(
                                        new SlideModel(
                                                data.child("url").getValue().toString(), ScaleTypes.FIT
                                        )
                                );
                            }

                            imageSlider.setImageList(remoteImages, ScaleTypes.FIT);
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {
                            throw databaseError.toException();
                        }
                    });
        }catch (Exception e){
            Log.e("DB Error", String.valueOf(e));
        }


        aboutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AboutActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);
            }
        });

        galleryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), GalleryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);
            }
        });

        liveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_VIEW ,
                        Uri.parse("https://www.youtube.com/@unasahib3123"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);
            }
        });

        facebookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_VIEW ,
                        Uri.parse("https://www.facebook.com/nanakvansh"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);
            }
        });

        joinWACard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_VIEW ,
                        Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSduKhbcKYd9YBSNERATScF7xeK1FZms3Cowy-JNIh__pnzZQw/viewform?usp=sf_link"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);
            }
        });

        contactUsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ContactUsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().getApplicationContext().startActivity(intent);
            }
        });
    }
}
