package com.cuit.smsb.Mapper;

import com.cuit.smsb.Model.SchaduleInfo;
import com.cuit.smsb.Model.SpaceInfo;
import com.cuit.smsb.Model.StaInfo;
import com.cuit.smsb.Model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface StaReadMapper {
    List<String> getTags();

    List<StaInfo> getAllStaInfo();

    List<SchaduleInfo> getAllScha(@Param("staNo") String staNo);

    List<SpaceInfo> getAllSpace(@Param("staNo") String staNo);

    List<User> getAllUsers();

}
