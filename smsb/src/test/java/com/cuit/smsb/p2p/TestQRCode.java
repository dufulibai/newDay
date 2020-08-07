package com.cuit.smsb.p2p;

import com.alibaba.fastjson.JSONObject;
import com.cuit.smsb.HttpUtils.HttpUtils;
import com.google.zxing.EncodeHintType;

import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.junit.jupiter.api.Test;
import top.jfunc.json.JsonObject;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.alibaba.druid.util.Utils.md5;
import static com.google.zxing.BarcodeFormat.QR_CODE;

public class TestQRCode {

    @Test
    public void createQRCode(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","小黄");
        jsonObject.put("lastName","狗子");
//        String content = jsonObject.toString();
        String content = "wxp://f2f1WTRmtFIT9-6GK5OjLvGOucSUmJceifWN";
        Map<EncodeHintType,Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        BitMatrix bt;
        try {
             bt = new MultiFormatWriter().encode(content, QR_CODE,200,200,hints);
            String filePath = "G://";
            String fileName = "QRCode.jpg";
            Path path = FileSystems.getDefault().getPath(filePath,fileName);
            MatrixToImageWriter.writeToPath(bt,"jpg",path);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test2(){
        child c = new child();
        c.setId("ddd");
        c.testId();
    }

    @Test
    public void testPostOrder(){
        JSONObject jsonObject = new JSONObject();
        String order_id = new Date().toString();
        String order_price = 0.10+"";
        String secretkey = "lantian";
        jsonObject.put("order_id",order_id);
        jsonObject.put("order_type","wechat");
        jsonObject.put("order_price",order_price);
        jsonObject.put("order_name","支付0.05角");
        jsonObject.put("sign",md5(md5(order_id + order_price) + secretkey));
        jsonObject.put("redirect_url","http://localhost:8000/notifyPayResult");
        String url = "http://localhost:7001/api/order";
        HttpUtils.post(jsonObject,url);
    }


}
