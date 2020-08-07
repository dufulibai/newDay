package com.cuit.smsb.Service;

import com.cuit.smsb.Model.SchaduleInfo;
import com.cuit.smsb.Model.SpaceInfo;
import com.cuit.smsb.Model.StaInfo;
import com.cuit.smsb.Model.User;

import java.util.List;
import java.util.Map;

public interface StaService {
    int insertStaInfo(StaInfo staInfo);
    int insertNewTags(StaInfo staInfo);
    int insertSelectedTags(StaInfo staInfo);
    int insertSchadule(List<SchaduleInfo> schaduleInfos);
    void insertSpaceInfo(List<SpaceInfo> spaceInfo);

    List<String> getTags();
    List<StaInfo> getAllStaInfo();
    List<SchaduleInfo> getAllScha(String staNo);
    List<SpaceInfo> getAllSpace(String staNo);


    void deleteSpaData(String spaNo);
    int deleteSchaData(String schaNo);
    void pitchDelete(List<String> schaList);
    void pitchDeleteSpa(List<String> spaList);

    void updateSchaData(SchaduleInfo schaduleInfo);
    void updateSpaData(Map<String,String> spaInfo);
    void updateStaInfo(StaInfo staInfo);

    void deleteSta(String staNo);

    List<User> getAllUsers();
}
