package com.mazeworks.boringapp.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mambisiz on 4/10/17.
 */

public class Post {
    private String objectID;
    private String message;
    private String authorProfileUrl;
    private String authorId;
    private String recipientId;
    private String authorName;
    private int timeStamp;
    public Post() {
    }

    public Post(String id, String title, String authorProfileUrl, String authorId) {
        this.objectID = id;
        this.message = title;
        this.authorProfileUrl = authorProfileUrl;
        this.authorId = authorId;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String id) {
        this.objectID = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthorProfileUrl() {
        return authorProfileUrl;
    }

    public void setAuthorProfileUrl(String authorProfileUrl) {
        this.authorProfileUrl = authorProfileUrl;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
