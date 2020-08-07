package com.cuit.smsb.Model;

import java.util.Date;
import java.util.List;

public class StaInfo {
  private Long staNo;
  private String sta_name;
  private String sta_addr;
  private String sta_phone;
  private String sta_notice;
  private String sta_createTime;
  private String sta_startDayTime;
  private String sta_endDayTime;
  private List<String> selectedTags;
  private List<String> newTag;
  private List<String> deleteTags;
  private List<String> deleteOldSeleTags;
  private List<String> deleteOldTag;
  private List<String> newSelectedTags;

    public List<String> getDeleteOldSeleTags() {
        return deleteOldSeleTags;
    }

    public void setDeleteOldSeleTags(List<String> deleteOldSeleTags) {
        this.deleteOldSeleTags = deleteOldSeleTags;
    }

    public List<String> getDeleteOldTag() {
        return deleteOldTag;
    }

    public void setDeleteOldTag(List<String> deleteOldTag) {
        this.deleteOldTag = deleteOldTag;
    }

    public List<String> getNewSelectedTags() {
        return newSelectedTags;
    }

    public void setNewSelectedTags(List<String> newSelectedTags) {
        this.newSelectedTags = newSelectedTags;
    }

    public List<String> getDeleteTags() {
        return deleteTags;
    }

    public void setDeleteTags(List<String> deleteTags) {
        this.deleteTags = deleteTags;
    }

    public String getSta_startDayTime() {
        return sta_startDayTime;
    }

    public void setSta_startDayTime(String sta_startDayTime) {
        this.sta_startDayTime = sta_startDayTime;
    }

    public String getSta_endDayTime() {
        return sta_endDayTime;
    }

    public void setSta_endDayTime(String sta_endDayTime) {
        this.sta_endDayTime = sta_endDayTime;
    }

    public String getSta_createTime() {
        return sta_createTime;

    }

    public void setSta_createTime(String sta_createTime) {
        this.sta_createTime = sta_createTime;
    }

    public List<String> getSelectedTags() {
        return selectedTags;
    }

    public void setSelectedTags(List<String> selectedTags) {
        this.selectedTags = selectedTags;
    }

    public List<String> getNewTag() {
        return newTag;
    }

    public void setNewTag(List<String> newTag) {
        this.newTag = newTag;
    }

    public StaInfo() {
    }

    public Long getStaNo() {
        return staNo;
    }

    public void setStaNo(Long staNo) {
        this.staNo = staNo;
    }

    public String getSta_name() {
        return sta_name;
    }

    public void setSta_name(String sta_name) {
        this.sta_name = sta_name;
    }

    public String getSta_addr() {
        return sta_addr;
    }

    public void setSta_addr(String sta_addr) {
        this.sta_addr = sta_addr;
    }

    public String getSta_phone() {
        return sta_phone;
    }

    public void setSta_phone(String sta_phone) {
        this.sta_phone = sta_phone;
    }

    public String getSta_notice() {
        return sta_notice;
    }

    public void setSta_notice(String sta_notice) {
        this.sta_notice = sta_notice;
    }

    @Override
    public String toString() {
        return "StaInfo{" +
                "staNo=" + staNo +
                ", sta_name='" + sta_name + '\'' +
                ", sta_addr='" + sta_addr + '\'' +
                ", sta_phone='" + sta_phone + '\'' +
                ", sta_notice='" + sta_notice + '\'' +
                ", sta_createTime='" + sta_createTime + '\'' +
                ", sta_startDayTime='" + sta_startDayTime + '\'' +
                ", sta_endDayTime='" + sta_endDayTime + '\'' +
                ", selectedTags=" + selectedTags +
                ", newTag=" + newTag +
                ", deleteTags=" + deleteTags +
                ", deleteOldSeleTags=" + deleteOldSeleTags +
                ", deleteOldTag=" + deleteOldTag +
                ", newSelectedTags=" + newSelectedTags +
                '}';
    }
}