package org.spring.springboot.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.spring.springboot.config.Constant;
import org.spring.springboot.domain.PayInfo;
import org.spring.springboot.util.weixinpay.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PayController {//微信小程序的支付 1.PayController 2.PayInfo实体3.util 4.依赖

    private static Logger log = Logger.getLogger(PayController.class);
    //, produces = "text/html;charset=UTF-8"
    @ResponseBody
    @RequestMapping(value = "/prepay")
    public Map<String, String> prePay(String code, ModelMap model, HttpServletRequest request) throws Exception {

        String content = null;
        Map map = new HashMap();
        ObjectMapper mapper = new ObjectMapper();

        boolean result = true;
        String info = "";

        log.error("\n======================================================");
        log.error("code: " + code);

        String openId = getOpenId(code);
        if(StringUtils.isBlank(openId)) {
            result = false;
            info = "获取到openId为空";
        } else {
            openId = openId.replace("\"", "").trim();

            String clientIP = CommonUtil.getClientIp(request);

            log.error("openId: " + openId + ", clientIP: " + clientIP);

            String randomNonceStr = RandomUtils.generateMixString(32);
            //统一下单
            String prepayId = unifiedOrder(openId, clientIP, randomNonceStr);

            log.error("prepayId: " + prepayId);

            if(StringUtils.isBlank(prepayId)) {
                result = false;
                info = "出错了，未获取到prepayId";
            } else {
            	//
            	String newpackage = "prepay_id="+prepayId;
                map.put("package", newpackage);//相当于package
                map.put("appId",Constant.APP_ID);
                map.put("nonceStr", randomNonceStr);
                map.put("timeStamp", String.valueOf(System.currentTimeMillis()));
                map.put("signType","MD5");
                String paySign = createSignByASCII(map);
                log.error("再次签名paySign"+paySign);
                map.put("paySign", paySign);
            }
        }

        try {
            map.put("result", String.valueOf(result));
            map.put("info", info);
           
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }


    private String getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Constant.APP_ID +
                "&secret=" + Constant.APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";

        HttpUtil httpUtil = new HttpUtil();
        try {

            HttpResult httpResult = httpUtil.doGet(url, null, null);

            if(httpResult.getStatusCode() == 200) {

                JsonParser jsonParser = new JsonParser();
                JsonObject obj = (JsonObject) jsonParser.parse(httpResult.getBody());

                log.error("getOpenId: " + obj.toString());

                if(obj.get("errcode") != null) {
                    log.error("getOpenId returns errcode: " + obj.get("errcode"));
                    return "";
                } else {
                    return obj.get("openid").toString();
                }
                //return httpResult.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 调用统一下单接口
     * @param openId
     */
    private String unifiedOrder(String openId, String clientIP, String randomNonceStr) {

        try {

            String url = Constant.URL_UNIFIED_ORDER;

            PayInfo payInfo = createPayInfo(openId, clientIP, randomNonceStr);
            String md5 = getSign(payInfo);
            payInfo.setSign(md5);

            log.error("md5 value: " + md5);

            String xml = CommonUtil.payInfoToXML(payInfo);
            xml = xml.replace("__", "_");//.replace("<![CDATA[1]]>", "1")
            //xml = xml.replace("__", "_").replace("<![CDATA[", "").replace("]]>", "");
            log.error(xml);

            StringBuffer buffer = HttpUtil.httpsRequest(url, "POST", xml);
            log.error("unifiedOrder request return body: \n" + buffer.toString());
            Map<String, String> result = CommonUtil.parseXml(buffer.toString());


            String return_code = result.get("return_code");
            if(StringUtils.isNotBlank(return_code) && return_code.equals("SUCCESS")) {

                String return_msg = result.get("return_msg");
                if(StringUtils.isNotBlank(return_msg) && !return_msg.equals("OK")) {
                    //log.error("统一下单错误！");
                    return "";
                }

                String prepay_Id = result.get("prepay_id");
                return prepay_Id;

            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    private PayInfo createPayInfo(String openId, String clientIP, String randomNonceStr) {

        Date date = new Date();
        String timeStart = TimeUtils.getFormatTime(date, Constant.TIME_FORMAT);
        String timeExpire = TimeUtils.getFormatTime(TimeUtils.addDay(date, Constant.TIME_EXPIRE), Constant.TIME_FORMAT);

        String randomOrderId = CommonUtil.getRandomOrderId();

        PayInfo payInfo = new PayInfo();
        payInfo.setAppid(Constant.APP_ID);//appid
        payInfo.setMch_id(Constant.MCH_ID);//商户号
        payInfo.setDevice_info("WEB");//设备
        payInfo.setNonce_str(randomNonceStr);//随机字符串
        payInfo.setSign_type("MD5");  //默认即为MD5
        payInfo.setBody("JSAPI支付测试");//商品描述
        payInfo.setAttach("支付测试4luluteam");//附加信息 非必要
        payInfo.setOut_trade_no(randomOrderId);//商户系统内部订单号
        payInfo.setTotal_fee(2);//订单总额
        payInfo.setSpbill_create_ip(clientIP);//后台ip
        payInfo.setTime_start(timeStart);//统一订单下单时间
        payInfo.setTime_expire(timeExpire);//统一订单失效时间
        payInfo.setNotify_url(Constant.URL_NOTIFY);//通知回调地址
        payInfo.setTrade_type("JSAPI");//微信小程序就是JSAPI
        payInfo.setLimit_pay("no_credit");//不能使用信用卡支付
        payInfo.setOpenid(openId);

        return payInfo;
    }

    private String getSign(PayInfo payInfo) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("appid=" + payInfo.getAppid())
                .append("&attach=" + payInfo.getAttach())
                .append("&body=" + payInfo.getBody())
                .append("&device_info=" + payInfo.getDevice_info())
                .append("&limit_pay=" + payInfo.getLimit_pay())
                .append("&mch_id=" + payInfo.getMch_id())
                .append("&nonce_str=" + payInfo.getNonce_str())
                .append("&notify_url=" + payInfo.getNotify_url())
                .append("&openid=" + payInfo.getOpenid())
                .append("&out_trade_no=" + payInfo.getOut_trade_no())
                .append("&sign_type=" + payInfo.getSign_type())
                .append("&spbill_create_ip=" + payInfo.getSpbill_create_ip())
                .append("&time_expire=" + payInfo.getTime_expire())
                .append("&time_start=" + payInfo.getTime_start())
                .append("&total_fee=" + payInfo.getTotal_fee())
                .append("&trade_type=" + payInfo.getTrade_type())
                .append("&key=" + Constant.APP_KEY);

        log.error("排序后的拼接参数：" + sb.toString());

        return CommonUtil.getMD5(sb.toString().trim()).toUpperCase();
    }

    private String createSignByASCII(Map<String, String> param) throws Exception {
    	//签名步骤一：按字典排序参数
    	List<String> list = new ArrayList<>(param.keySet());
    	list = list.stream().sorted().collect(Collectors.toList());
    	String str = "";
    	for(int i = 0;i < list.size();i++) {
    		str += list.get(i) + "=" + param.get(list.get(i))+"&";
    	}
    	//签名步骤二：加上key
    	str += "key=" + Constant.APP_KEY;
    	//步骤三:加密并大写
    	str = CommonUtil.getMD5(str).toUpperCase();
    	return str;
    }

}


