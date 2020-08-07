package com.cuit.smsb.Service.Imp;


import com.cuit.smsb.Mapper.StaReadMapper;
import com.cuit.smsb.Mapper.StaWriteMapper;
import com.cuit.smsb.Model.SchaduleInfo;
import com.cuit.smsb.Model.SpaceInfo;
import com.cuit.smsb.Model.StaInfo;
import com.cuit.smsb.Model.User;
import com.cuit.smsb.Service.StaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StaServiceImp implements StaService {
    @Resource
    StaWriteMapper staWriteMapper;
    @Resource
    StaReadMapper staReadMapper;
    @Override
    public int insertStaInfo(StaInfo staInfo) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        staInfo.setSta_createTime(df.format(new Date()));
        int n = staWriteMapper.insertStaInfo(staInfo);
        return n;
    }

    @Override
    public int insertNewTags(StaInfo staInfo) {
        List<String> newTags = staInfo.getNewTag();
        if(!newTags.isEmpty()){
            int n = staWriteMapper.insertNewTags(newTags);
            return n;
        } else {
            return -1;
        }


    }

    @Override
    public int insertSelectedTags(StaInfo staInfo) {
        List<String> selectedTags = staInfo.getSelectedTags();
        Long staNo = staInfo.getStaNo();
        int n = staWriteMapper.insertSelectedTags(staNo,selectedTags);
        return 0;
    }

    @Override
    public List<String> getTags() {
        List<String> tags = staReadMapper.getTags();
        return tags;
    }

    @Override
    public List<StaInfo> getAllStaInfo() {
        List<StaInfo> allStaInfo = staReadMapper.getAllStaInfo();
        return allStaInfo;
    }

    @Override
    public List<SchaduleInfo> getAllScha(String staNo) {
        List<SchaduleInfo> allScha = staReadMapper.getAllScha(staNo);
        return allScha;
    }

    @Override
    public List<SpaceInfo> getAllSpace(String staNo) {
        List<SpaceInfo> allSpace = staReadMapper.getAllSpace(staNo);
        return allSpace;
    }

    @Override
    public void deleteSpaData(String spaNo) {
        staWriteMapper.deleteSpaData(spaNo);
    }

    @Override
    public int deleteSchaData(String schaNo) {
        return staWriteMapper.deleteSchaData(schaNo);
    }

    @Override
    public void pitchDelete(List<String> schaList) {
        staWriteMapper.pitchDelete(schaList);
    }

    @Override
    public void pitchDeleteSpa(List<String> spaList) {
        staWriteMapper.pitchDeleteSpa(spaList);
    }

    @Override
    public void updateSchaData(SchaduleInfo schaduleInfo) {
        staWriteMapper.updateSchaData(schaduleInfo);
    }

    @Override
    public void updateSpaData(Map<String, String> spaInfo) {
       staWriteMapper.updateSpaData(spaInfo);
    }

    @Override
    public void updateStaInfo(StaInfo staInfo) {
        if (staInfo.getSta_createTime().isEmpty()){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            staInfo.setSta_createTime(df.format(new Date()));
        }
       staWriteMapper.updateStaInfo(staInfo);
       if (staInfo.getDeleteOldSeleTags().size()>0){
           staWriteMapper.deleteOldSeleTagsStaTag(staInfo.getDeleteOldSeleTags());
           staWriteMapper.deleteOldSeleTagsStaSpace(staInfo.getDeleteOldSeleTags());
       }
       if (staInfo.getDeleteOldTag().size()>0){
           staWriteMapper.deleteOldTag(staInfo.getDeleteOldTag());
       }
       if (staInfo.getNewTag().size()>0){
           staWriteMapper.insertNewTags(staInfo.getNewTag());
       }
       if (staInfo.getNewSelectedTags().size()>0){
           staWriteMapper.insertSelectedTags(staInfo.getStaNo(),staInfo.getNewSelectedTags());
       }
    }

    @Override
    public void deleteSta(String staNo) {
        staWriteMapper.deleteStaTagByStaNo(staNo);
        staWriteMapper.deleteStaspaceByStaNo(staNo);
        staWriteMapper.deleteSchaByStaNo(staNo);
        staWriteMapper.deleteStaBaseInfo(staNo);
    }

    @Override
    public List<User> getAllUsers() {

        return staReadMapper.getAllUsers();
    }

    @Override
    public int insertSchadule(List<SchaduleInfo> schaduleInfos) {
       int n = staWriteMapper.insertSchadule(schaduleInfos);
        return n;
    }

    @Override
    public void insertSpaceInfo(List<SpaceInfo> spaceInfo) {
        staWriteMapper.insertSpaceInfo(spaceInfo);
    }

}
