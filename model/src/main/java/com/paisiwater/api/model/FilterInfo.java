package com.paisiwater.api.model;

/**
 * create by renrui at 2018/1/10/0010 10:00
 */
public class FilterInfo {
    private String model;
    private int totalFilters;
    private int rank;
    private String filterName;
    private String filterLife;
    private String filterDetail;
    private String otherInfo;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTotalFilters() {
        return totalFilters;
    }

    public void setTotalFilters(int totalFilters) {
        this.totalFilters = totalFilters;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getFilterLife() {
        return filterLife;
    }

    public void setFilterLife(String filterLife) {
        this.filterLife = filterLife;
    }

    public String getFilterDetail() {
        return filterDetail;
    }

    public void setFilterDetail(String filterDetail) {
        this.filterDetail = filterDetail;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }
}
