package com.goldentech.model;

import java.io.Serializable;

public class Category implements Serializable {
    private String id;
    private String name;
    private String image;
    private boolean fromLive;
    private String url;

    public Category(String id, String name, String image, boolean fromLive, String url) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.fromLive = fromLive;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFromLive() {
        return fromLive;
    }

    public void setFromLive(boolean fromLive) {
        this.fromLive = fromLive;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
