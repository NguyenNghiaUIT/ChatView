package com.example.nguyennghia.circleimageview.model;

/**
 * Created by nguyennghia on 05/07/2016.
 */
public class ChatInfo {
    private String mTitle;
    private String mContent;
    private String mStatus;
    private Picture mPictures;

    public ChatInfo() {

    }

    public ChatInfo(String title, String content, String status, Picture picture) {
        this.mTitle = title;
        this.mContent = content;
        this.mStatus = status;
        this.mPictures = picture;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public String getStatus() {
        return mStatus;
    }

    public Picture getPictures() {
        return mPictures;
    }
}
