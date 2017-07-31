package com.mazeworks.boringapp.entities.realm;

import io.realm.RealmObject;

/**
 * Created by mambisiz on 4/11/17.
 */

public class TopicTag extends RealmObject {

    private String name;

    private int used;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
