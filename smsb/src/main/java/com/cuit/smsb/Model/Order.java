package com.cuit.smsb.Model;

public class Order {
    private Long orderNo;
    private String day;
    private String hanWeekDay;
    private String month;
    private String schaNo;
    private String scha_endTime;
    private String scha_name;
    private String scha_price;
    private String scha_priceTime;
    private String scha_startTime;
    private String spaName;
    private String spaNo;
    private String staNo;
    private String tag;
    private String timeNode;
    private String weekDay;
    private String year;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    private String orderNum;


    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private Long userId;


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHanWeekDay() {
        return hanWeekDay;
    }

    public void setHanWeekDay(String hanWeekDay) {
        this.hanWeekDay = hanWeekDay;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSchaNo() {
        return schaNo;
    }

    public void setSchaNo(String schaNo) {
        this.schaNo = schaNo;
    }

    public String getScha_endTime() {
        return scha_endTime;
    }

    public void setScha_endTime(String scha_endTime) {
        this.scha_endTime = scha_endTime;
    }

    public String getScha_name() {
        return scha_name;
    }

    public void setScha_name(String scha_name) {
        this.scha_name = scha_name;
    }

    public String getScha_price() {
        return scha_price;
    }

    public void setScha_price(String scha_price) {
        this.scha_price = scha_price;
    }

    public String getScha_priceTime() {
        return scha_priceTime;
    }

    public void setScha_priceTime(String scha_priceTime) {
        this.scha_priceTime = scha_priceTime;
    }

    public String getScha_startTime() {
        return scha_startTime;
    }

    public void setScha_startTime(String scha_startTime) {
        this.scha_startTime = scha_startTime;
    }

    public String getSpaName() {
        return spaName;
    }

    public void setSpaName(String spaName) {
        this.spaName = spaName;
    }

    public String getSpaNo() {
        return spaNo;
    }

    public void setSpaNo(String spaNo) {
        this.spaNo = spaNo;
    }

    public String getStaNo() {
        return staNo;
    }

    public void setStaNo(String staNo) {
        this.staNo = staNo;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTimeNode() {
        return timeNode;
    }

    public void setTimeNode(String timeNode) {
        this.timeNode = timeNode;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Order{" +
                "day='" + day + '\'' +
                ", hanWeekDay='" + hanWeekDay + '\'' +
                ", month='" + month + '\'' +
                ", schaNo='" + schaNo + '\'' +
                ", scha_endTime='" + scha_endTime + '\'' +
                ", scha_name='" + scha_name + '\'' +
                ", scha_price='" + scha_price + '\'' +
                ", scha_priceTime='" + scha_priceTime + '\'' +
                ", scha_startTime='" + scha_startTime + '\'' +
                ", spaName='" + spaName + '\'' +
                ", spaNo='" + spaNo + '\'' +
                ", staNo='" + staNo + '\'' +
                ", tag='" + tag + '\'' +
                ", timeNode='" + timeNode + '\'' +
                ", weekDay='" + weekDay + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
