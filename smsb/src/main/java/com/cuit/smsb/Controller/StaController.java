package com.cuit.smsb.Controller;




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cuit.smsb.Model.SchaduleInfo;
import com.cuit.smsb.Model.SpaceInfo;
import com.cuit.smsb.Model.StaInfo;
import com.cuit.smsb.Model.User;
import com.cuit.smsb.Service.StaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class StaController {
    @Resource
    StaService staService;
    @CrossOrigin
    @PostMapping("/createStaInfo")
    public String createStaInfo(@RequestBody StaInfo staInfo) {
        System.out.println(staInfo);
        if (staInfo.getStaNo() == null || "".equals(staInfo.getStaNo())){
            int n = staService.insertStaInfo(staInfo);
            staService.insertNewTags(staInfo);
            if (n>0){
                staService.insertSelectedTags(staInfo);
            }
            return "ok";
        }else {
           staService.updateStaInfo(staInfo);
            return "updateOk";
        }

    }

    @PostMapping("/createSpaceInfo")
    public List<SpaceInfo> createSpaceInfo(@RequestBody List<SpaceInfo> spaceInfo){
        System.out.println(spaceInfo);
        staService.insertSpaceInfo(spaceInfo);
        return spaceInfo;
    }

    @PostMapping("/createSchadule")
    public List<SchaduleInfo> createSchadule(@RequestBody List<SchaduleInfo>schaduleInfo){
        staService.insertSchadule(schaduleInfo);
        return schaduleInfo;
    }

    @CrossOrigin
    @GetMapping("/getTags")
    public List<String> getTags(){
        List<String> tags = staService.getTags();
        return tags;
    }

    @CrossOrigin
    @GetMapping("/getAllStaInfo")
    public List<StaInfo> getAllStaInfo(){
        List<StaInfo> allStaInfo = staService.getAllStaInfo();
        return allStaInfo;
    }

    @PostMapping("/getAllScha")
    public List<SchaduleInfo> getAllScha(@RequestBody Map<String, String> staNo){
        List<SchaduleInfo> staNo1 = staService.getAllScha(staNo.get("staNo"));
        return staNo1;
    }
    @PostMapping("/getAllSpace")
    public List<SpaceInfo> getAllSpace(@RequestBody Map<String, String> staNo){
        List<SpaceInfo> staNo1 = staService.getAllSpace(staNo.get("staNo"));
        return staNo1;
    }

    @PostMapping("/deleteSchaData")
    public String deleteSchaData(@RequestBody Map<String,String> schaNo){
        staService.deleteSchaData(schaNo.get("schaNo"));
        return "ok";
    }

    @PostMapping("/deleteSpaData")
    public String deleteSpaData(@RequestBody Map<String,String> spaNo){
        staService.deleteSpaData(spaNo.get("spaNo"));
        return "ok";
    }

    @PostMapping("/pitchDelete")
    public String pitchDelete(@RequestBody List<String> schaList){
        staService.pitchDelete(schaList);
        return "ok";
    }

    @PostMapping("/pitchDeleteSpa")
    public String pitchDeleteSpa(@RequestBody List<String> spaList){
        staService.pitchDeleteSpa(spaList);
        return "ok";
    }

    @PostMapping("/editSchaData")
    public String editSchaData(@RequestBody SchaduleInfo schaduleInfo){
          staService.updateSchaData(schaduleInfo);
        return "ok";
    }

    @PostMapping("/editSpaData")
    public String editSpaData(@RequestBody Map<String,String> spaInfo){
        System.out.println(spaInfo);
        staService.updateSpaData(spaInfo);
        return "ok";
    }

    @PostMapping("/deleteSta")
    public String deleteSta(@RequestBody Map<String,String> staNo){
        System.out.println(staNo.get("staNo"));
        staService.deleteSta(staNo.get("staNo"));
        return "ok";
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return staService.getAllUsers();
    }
}
