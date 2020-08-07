package com.cuit.smsb.Model;

public class SchaduleInfo {
    private Long schaNo;
    private Long staNo;
    private String  scha_name;
    private String scha_startTime;
    private String scha_endTime;
    private String scha_priceTime;
    private String scha_price;

    public SchaduleInfo() {
    }

    public Long getSchaNo() {
        return schaNo;
    }

    public void setSchaNo(Long schaNo) {
        this.schaNo = schaNo;
    }

    public Long getStaNo() {
        return staNo;
    }

    public void setStaNo(Long staNo) {
        this.staNo = staNo;
    }

    public String getScha_name() {
        return scha_name;
    }

    public void setScha_name(String scha_name) {
        this.scha_name = scha_name;
    }

    public String getScha_startTime() {
        return scha_startTime;
    }

    public void setScha_startTime(String scha_startTime) {
        this.scha_startTime = scha_startTime;
    }

    public String getScha_endTime() {
        return scha_endTime;
    }

    public void setScha_endTime(String scha_endTime) {
        this.scha_endTime = scha_endTime;
    }

    public String getScha_priceTime() {
        return scha_priceTime;
    }

    public void setScha_priceTime(String scha_priceTime) {
        this.scha_priceTime = scha_priceTime;
    }

    public String getScha_price() {
        return scha_price;
    }

    public void setScha_price(String scha_price) {
        this.scha_price = scha_price;
    }

    @Override
    public String toString() {
        return "SchaduleInfo{" +
                "schaNo=" + schaNo +
                ", staNo=" + staNo +
                ", scha_name='" + scha_name + '\'' +
                ", scha_startTime='" + scha_startTime + '\'' +
                ", scha_endTime='" + scha_endTime + '\'' +
                ", scha_priceTime='" + scha_priceTime + '\'' +
                ", scha_price='" + scha_price + '\'' +
                '}';
    }
}
