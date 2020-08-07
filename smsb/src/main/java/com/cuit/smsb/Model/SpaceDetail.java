package com.cuit.smsb.Model;

public class SpaceDetail {
    private Long spaNo;
    private Long schaNo;
    private String spaName;

    public Long getSpaNo() {
        return spaNo;
    }

    public void setSpaNo(Long spaNo) {
        this.spaNo = spaNo;
    }

    public Long getSchaNo() {
        return schaNo;
    }

    public void setSchaNo(Long schaNo) {
        this.schaNo = schaNo;
    }

    public String getSpaName() {
        return spaName;
    }

    public void setSpaName(String spaName) {
        this.spaName = spaName;
    }

    @Override
    public String toString() {
        return "SpaceDetail{" +
                "spaNo=" + spaNo +
                ", schaNo=" + schaNo +
                ", spaName='" + spaName + '\'' +
                '}';
    }
}
