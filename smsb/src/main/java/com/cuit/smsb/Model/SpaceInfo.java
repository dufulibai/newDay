package com.cuit.smsb.Model;

import java.util.List;

public class SpaceInfo {
    private Long staNo;
    private String tag;
    private List<SpaceDetail> spaces;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getStaNo() {
        return staNo;
    }

    public void setStaNo(Long staNo) {
        this.staNo = staNo;
    }

    public List<SpaceDetail> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<SpaceDetail> spaces) {
        this.spaces = spaces;
    }

    @Override
    public String toString() {
        return "SpaceInfo{" +
                "staNo=" + staNo +
                ", tag='" + tag + '\'' +
                ", spaces=" + spaces +
                '}';
    }
}
