package com.example.ddbssj.model;

public class GalleryListItem {
    public GalleryListItem(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    String name;
    int imageId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


}
