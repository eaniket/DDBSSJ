package com.example.ddbssj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ddbssj.R;
import com.example.ddbssj.model.GalleryListItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    List<String> urlList;
    Context context;
    LayoutInflater inflater;

    public ImageAdapter(Context context, List<String> urlList){
        this.context = context;
        this.urlList = urlList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String url  = urlList.get(i);
        view = inflater.inflate(R.layout.custom_image_item, null);
        ImageView imageView = view.findViewById(R.id.gallery_image);
        Picasso.get().load(url).into(imageView);
        return view;
    }
}
