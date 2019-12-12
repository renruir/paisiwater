package com.paisiwater.api.model;

/**
 * Created by renrui on 2017/2/15 0015.
 */
public class BaseTemplateStruct {
    private String value;
    private String color;

    public BaseTemplateStruct(String mValue, String mColor){
        this.value = mValue;
        this.color = mColor;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
