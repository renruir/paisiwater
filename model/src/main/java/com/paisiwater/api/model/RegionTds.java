package com.paisiwater.api.model;

/**
 * create by renrui at 2018/1/10/0010 10:00
 */
public class RegionTds {
    private String reg_row_id;
    private String city_id;
    private String region;
    private String regiontds;

    public String getReg_row_id() {
        return reg_row_id;
    }

    public void setReg_row_id(String reg_row_id) {
        this.reg_row_id = reg_row_id;
    }

    public String getCityid() {
        return city_id;
    }

    public void setCityid(String city_id) {
        this.city_id = city_id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion_tds() {
        return regiontds;
    }

    public void setRegion_tds(String region_tds) {
        this.regiontds = region_tds;
    }
}
