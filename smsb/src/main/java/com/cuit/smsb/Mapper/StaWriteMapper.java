package com.cuit.smsb.Mapper;

import com.cuit.smsb.Model.SchaduleInfo;
import com.cuit.smsb.Model.SpaceInfo;
import com.cuit.smsb.Model.StaInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StaWriteMapper {
    int insertStaInfo(StaInfo staInfo);
    int insertNewTags(@Param("newTag") List<String> newTags);
    int insertSelectedTags(@Param("staNo") Long staNo, @Param("selectedTags") List<String> seletedTags);
    int insertSchadule(List<SchaduleInfo> schaduleInfos);
    void insertSpaceInfo(List<SpaceInfo> spaceInfo);

    void deleteSpaData(String spaNo);
    int deleteSchaData(String schaNo);
    void pitchDelete(List<String> schaList);
    void pitchDeleteSpa(List<String> spaList);

    void updateSchaData(SchaduleInfo schaduleInfo);
    void updateSpaData(Map<String,String> spaInfo);
   //更新场馆信息
    void updateStaInfo(StaInfo staInfo);
    void deleteOldSeleTagsStaTag(List<String> oldSeleTags);
    void deleteOldSeleTagsStaSpace(List<String> oldSeleTags);
    void deleteOldTag(List<String> deleteOldTag);
    //删除场馆信息
    void deleteStaBaseInfo(String staNo);
    void deleteStaTagByStaNo(String staNo);
    void deleteStaspaceByStaNo(String staNo);
    void deleteSchaByStaNo(String staNo);
}
