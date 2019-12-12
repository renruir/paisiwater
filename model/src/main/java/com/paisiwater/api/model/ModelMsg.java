package com.paisiwater.api.model;

import java.util.Map;

/**
 * create by renrui at 2018/1/10/0010 10:00
 */
public class ModelMsg {
    private String touser;
    private String template_id;
    private String url;
    private String topcolor;
    private Map<String, BaseTemplateStruct> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public Map<String, BaseTemplateStruct> getData() {
        return data;
    }

    public void setData(Map<String, BaseTemplateStruct> data) {
        this.data = data;
    }
}
