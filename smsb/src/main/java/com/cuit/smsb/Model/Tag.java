package com.cuit.smsb.Model;

public class Tag {
    private Long tagNo;
    private String tagName;

    public Tag() {
    }

    public Tag(Long tagNo, String tagName) {
        this.tagNo = tagNo;
        this.tagName = tagName;
    }

    public Long getTagNo() {
        return tagNo;
    }

    public void setTagNo(Long tagNo) {
        this.tagNo = tagNo;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagNo=" + tagNo +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
