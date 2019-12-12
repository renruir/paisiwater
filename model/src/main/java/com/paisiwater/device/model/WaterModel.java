package com.paisiwater.device.model;

/**
 * Created by renrui on 2017/8/16/0016.
 */
public class WaterModel {
    private String model;
    private int filterTotal;
    private String[] filterName;
    private int[] filterSurplus;

    public int[] getFilterSurplus() {
        return filterSurplus;
    }

    public void setFilterSurplus(int[] filterSurplus) {
        this.filterSurplus = filterSurplus;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getFilterTotal() {
        return filterTotal;
    }

    public void setFilterTotal(int filterTotal) {
        this.filterTotal = filterTotal;
    }

    public String[] getFilterName() {
        return filterName;
    }

    public void setFilterName(String[] filterName) {
        this.filterName = filterName;
    }
}
