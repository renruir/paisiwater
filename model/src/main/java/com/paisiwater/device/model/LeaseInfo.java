package com.paisiwater.device.model;

public class LeaseInfo {

    private String openId;
    private String dept_name;
    private String phone_number;
    private String province;//province
    private String city;//city
    private String zone;//zone
    private String address_detail;
    private String model;
    private String lease_period;
    private String start_date;
    private String end_date;
    private String sales_id;
    private String device_sn;
    private String device_id;

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

    public String getLease_period() {
        return lease_period;
    }

    public void setLease_period(String lease_period) {
        this.lease_period = lease_period;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getSales_id() {
        return sales_id;
    }

    public void setSales_id(String sales_id) {
        this.sales_id = sales_id;
    }

    public String getDevice_sn() {
        return device_sn;
    }

    public void setDevice_sn(String device_sn) {
        this.device_sn = device_sn;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    @Override
    public String toString() {
        return "A new Lease Info: \nopenId:"+getOpenId() + "\n单位："+getDept_name() + "\n电话："+getPhone_number()
                +"\n地址："+getProvince() + getCity()+getZone()+getAddress_detail()
                +"\n型号："+getModel()+"\n租期："+getLease_period()+"\n起止时间："
                +getStart_date()+"-->"+getEnd_date()+"\n经销商id:"+getSales_id()
                +"\n设备SN："+getDevice_sn()+"\n设备ID："+getDevice_id();
    }
}
