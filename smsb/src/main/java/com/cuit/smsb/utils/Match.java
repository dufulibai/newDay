package com.cuit.smsb.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Match {

    public static String findMoney(String content){
        String pattern = "\\d+(\\.\\d+)?元";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(content);
        if (m.find( )) {
            String f = m.group(0).substring(0,m.group(0).indexOf("元"));
            return f;
        }else{
            System.out.println("未找到");
            return null;
        }
    }
}
