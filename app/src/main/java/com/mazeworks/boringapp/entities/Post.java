package com.mazeworks.boringapp.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mambisiz on 4/10/17.
 */

public class Post {
    private String objectID;
    private String title;
    private String authorProfileUrl;
    private String authorId;
    private String authorName;
    private List<String> tags = new ArrayList<>();
    private int timeStamp;
    public Post() {
        tags = new ArrayList<>();
    }

    public Post(String id, String title, String authorProfileUrl, String authorId) {
        this.objectID = id;
        this.title = title;
        this.authorProfileUrl = authorProfileUrl;
        this.authorId = authorId;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String id) {
        this.objectID = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
