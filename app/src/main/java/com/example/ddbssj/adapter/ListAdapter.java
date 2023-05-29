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

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    ArrayList<GalleryListItem> galleryListItems;
    Context context;
    LayoutInflater inflater;

    public ListAdapter(Context context, ArrayList<GalleryListItem> galleryListItems){
       this.context = context;
       this.galleryListItems = galleryListItems;
       inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return galleryListItems.size();
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
    public View getView(int position,View convertView,ViewGroup parent) {
        GalleryListItem galleryListItem = (GalleryListItem) galleryListItems.get(position);
        convertView = inflater.inflate(R.layout.custom_list_item, null);
        TextView textView = convertView.findViewById(R.id.menu_name);
        ImageView imageView = convertView.findViewById(R.id.menu_chevron);

        textView.setText(galleryListItem.getName());
        imageView.setImageResource(R.drawable.ic_baseline_keyboard_arrow_right_16);

        return convertView;
    }
}
