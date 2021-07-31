package com.paipayales.app.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeasurementResponse {

    @SerializedName("pool_id")
    @Expose
    private Integer poolId;

    @SerializedName("device_id")
    @Expose
    private String deviceId;

    @SerializedName("params")
    @Expose
    private List<Param> params = null;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getPoolId() {
        return poolId;
    }

    public void setPoolId(Integer poolId) {
        this.poolId = poolId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public class Param {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("value")
        @Expose
        private Double value;
        @SerializedName("alert")
        @Expose
        private String alert;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        public String getAlert() {
            return alert;
        }

        public void setAlert(String alert) {
            this.alert = alert;
        }

    }

}