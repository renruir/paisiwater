package com.paisiwater.api.model;

/**
 * create by renrui on 2018-01-26-15:17
 */

public class ServiceInfo {

    private String category;
    private String openId;
    private String dept_name;
    private String phone_number;
    private String province;
    private String city;
    private String zone;
    private String address_detail;
    private String model;
    private String order_date;
    private String fault_describe;
    private String remarks;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(String address_detail) {
        this.address_detail = address_detail;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getFault_describe() {
        return fault_describe;
    }

    public void setFault_describe(String fault_describe) {
        this.fault_describe = fault_describe;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "category: " + getCategory() + ", name: " + getDept_name() + ", phone: " + getPhone_number() +
                ", address: " + getProvince() + getCity() + getZone() + getAddress_detail() + ", model: " + getModel() +
                ", date: " + getOrder_date() + ", faultDescribe: " + getFault_describe() + ",remark: " + getRemarks();
    }
}
