package com.paisiwater.api.model;

/**
 * create by renrui at 2018/1/10/0010 10:00
 */
public class FilterInfo {
    private String model;
    private int total_filters;
    private int grade;
    private String filter_name;
    private String filter_life;
    private String filter_detail;
    private String other;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTotal_filters() {
        return total_filters;
    }

    public void setTotal_filters(int total_filters) {
        this.total_filters = total_filters;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getFilter_name() {
        return filter_name;
    }

    public void setFilter_name(String filter_name) {
        this.filter_name = filter_name;
    }

    public String getFilter_life() {
        return filter_life;
    }

    public void setFilter_life(String filter_life) {
        this.filter_life = filter_life;
    }

    public String getFilter_detail() {
        return filter_detail;
    }

    public void setFilter_detail(String filter_detail) {
        this.filter_detail = filter_detail;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
