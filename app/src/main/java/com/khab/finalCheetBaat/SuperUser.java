package com.khab.finalCheetBaat;

public class SuperUser {
    public String image;
    public String thumb_image;

    public SuperUser(String name, String status) {
        this.image = "default image";
        this.thumb_image = "default thumb image";
    }

    public SuperUser() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }
}
