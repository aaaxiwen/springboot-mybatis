package org.spring.springboot.config;

/**
 * Created by Hyman on 2017/2/27.
 * update by xxw on 2018/9/10
 */
public class Constant {

    public static final String DOMAIN = "https://i-test.com.cn";

    public static final String APP_ID = "wxcdbcb495a006f7a4";

    public static final String APP_SECRET = "29c1cbd4de1c3fb31859f36abac04a7f";

    public static final String APP_KEY = "29c1cbd4de1c3fb31859f36abac04a7f";

    public static final String MCH_ID = "1518707291";  //商户号

    public static final String URL_UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    public static final String URL_NOTIFY = Constant.DOMAIN + "/wxpay/views/payInfo.jsp";//通知回调地址

    public static final String TIME_FORMAT = "yyyyMMddHHmmss";

    public static final int TIME_EXPIRE = 2;  //单位是day

}
