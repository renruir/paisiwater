package com.paisiwater.api.model;

/**
 * create by renrui at 2018/1/10/0010 10:00
 */
public class CityTds {
    private String city_row_id;
    private String provinceid;
    private String city;
    private String citytds;

    public String getCity_row_id() {
        return city_row_id;
    }

    public void setCity_row_id(String city_row_id) {
        this.city_row_id = city_row_id;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_tds() {
        return citytds;
    }

    public void setCity_tds(String city_tds) {
        this.citytds = city_tds;
    }
}
