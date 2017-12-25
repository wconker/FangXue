package com.android.teacher.entity.Carousel;

/**
 * Created by caik on 2016/10/19.
 */

public class Picture {
    private String path;
    private String name;


    private int drawable;

    public Picture(String path, String name, int drawable) {
        this.path = path;
        this.name = name;
        this.drawable = drawable;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
