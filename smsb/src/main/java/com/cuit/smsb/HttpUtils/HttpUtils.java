package com.cuit.smsb.HttpUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;

import static com.alibaba.druid.util.Utils.md5;

public class HttpUtils {

    public static String post(JSONObject json, String url){
        String result = "";
        HttpPost httpPost = new HttpPost (url);
        CloseableHttpClient aDefault = HttpClients.createDefault();
        httpPost.setHeader("Content-Type","application/json;charset=utf-8");
        StringEntity postingString = new StringEntity(json.toString(),"utf-8");
        httpPost.setEntity(postingString);
        HttpResponse response;
        try {
             response = aDefault.execute(httpPost);
            InputStream content = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(content, "utf-8"));
            StringBuilder strber= new StringBuilder();
            String line = null;
            while((line = br.readLine())!=null){
                strber.append(line+'\n');
            }
            br.close();
            content.close();
            result = strber.toString();
            System.out.println("======");
            System.out.println(result);
            if(response.getStatusLine().getStatusCode()!= HttpStatus.SC_OK){
                result = "error";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //请求二维码
    public static Map getQrcode(double totalPrice){
        JSONObject jsonObject = new JSONObject();
        long order_id = new Date().getTime();
        String order_price = totalPrice+"";
        String secretkey = "lantian";
        jsonObject.put("order_id",order_id);
        jsonObject.put("order_type","wechat");
        jsonObject.put("order_price",order_price);
        jsonObject.put("order_name","支付"+order_price+"角");
        jsonObject.put("sign",md5(md5(order_id + order_price) + secretkey));
        jsonObject.put("redirect_url","http://11111");
        String url = "http://localhost:7001/api/order";
        String result = HttpUtils.post(jsonObject,url);
        Map map = JSON.parseObject(result);
        return map;
    }
}
