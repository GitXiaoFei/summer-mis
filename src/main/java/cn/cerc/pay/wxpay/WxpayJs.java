package cn.cerc.pay.wxpay;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jboss.logging.Logger;

import com.google.gson.Gson;

import cn.cerc.jdb.core.IConfig;
import cn.cerc.jdb.core.IHandle;

//微信支付（网页JS版）
public class WxpayJs {

    private static Logger log = Logger.getLogger(WxpayJs.class);
    // 申请支付金额
    private String amount;
    // 申请支付订单号
    private String orderNo;
    // 发起申请的当前主机IP
    private String clientIP = "127.0.0.1";
    // 帐套代码
    private String corpNo = "000000";
    // 用户代码
    private String userCode = "testUser";
    // 回调网址, 微信支付成功后通知地址 必须要求80端口并且地址不能带参数
    private String notifyUrl = "";
    // 连接配置参数
    public static final String config_appId = "wx.AppID";
    public static final String config_appSecret = "wx.AppSecret";
    // 商户代码
    public static final String config_appMachId = "wx.MchId";
    private String appId;
    private String appSecret;
    private String appMachId;

    public WxpayJs(IHandle handle, IConfig config) {
        appId = config.getProperty(config_appId);
        appSecret = config.getProperty(config_appSecret);
        appMachId = config.getProperty(config_appMachId);

        if (handle != null) {
            this.corpNo = handle.getCorpNo();
            this.userCode = handle.getUserCode();
        }
    }

    @SuppressWarnings("static-access")
    public String requestPay(String body) {
        String trade_type = "JSAPI";
        // 商户号
        String mch_id = this.appMachId;
        // 随机字符串
        String nonce_str = Sha1Util.getNonceStr();
        String totalFee = String.valueOf(new BigDecimal(amount).multiply(new BigDecimal(100)).intValue());// 金额
                                                                                                          // 微信是以分为单位的;
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", this.appId);
        packageParams.put("attach", corpNo);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("body", body);
        packageParams.put("out_trade_no", orderNo);
        packageParams.put("total_fee", totalFee);
        packageParams.put("spbill_create_ip", this.clientIP);
        packageParams.put("notify_url", this.notifyUrl);
        packageParams.put("trade_type", trade_type);
        packageParams.put("openid", userCode);

        RequestHandler reqHandler = new RequestHandler(null, null);
        reqHandler.init(this.appId, this.appSecret);

        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>" + "<appid>" + this.appId + "</appid>" + "<attach>" + corpNo + "</attach>" + "<mch_id>"
                + mch_id + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>"
                + "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>" + orderNo + "</out_trade_no>"
                + "<total_fee>" + totalFee + "</total_fee>" + "<spbill_create_ip>" + this.clientIP
                + "</spbill_create_ip>" + "<notify_url>" + this.notifyUrl + "</notify_url>" + "<trade_type>"
                + trade_type + "</trade_type>" + "<openid>" + userCode + "</openid>" + "</xml>";

        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        Map<String, String> map = new GetWxOrderno().getPayNo(createOrderURL, xml);
        if (map.get("return_code").equals("FAIL")) {
            map.put("error", "true");
            map.put("errMsg", map.get("result_msg"));
            return new Gson().toJson(map);
        }
        String prepay_id = map.get("prepay_id");
        log.info("获取到的预支付ID：" + prepay_id);

        // 获取prepay_id后，拼接最后请求支付所需要的package
        SortedMap<String, String> finalpackage = new TreeMap<String, String>();
        String timestamp = Sha1Util.getTimeStamp();
        String packages = "prepay_id=" + prepay_id;
        finalpackage.put("appId", this.appId);
        finalpackage.put("timeStamp", timestamp);
        finalpackage.put("nonceStr", nonce_str);
        finalpackage.put("package", packages);
        finalpackage.put("signType", "MD5");
        finalpackage.put("paySign", reqHandler.createSign(finalpackage));
        finalpackage.put("packages", packages);
        return new Gson().toJson(finalpackage);
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public static void main(String[] args) {
        WxpayJs pay = new WxpayJs(null, (key, def) -> {
            if (config_appId.equals(key))
                return "wx8302b6636974854e";
            else if (config_appSecret.equals(key))
                return "529da5a3e26339bbf960e91879dfad5c";
            else if (config_appMachId.equals(key))
                return "1262880401";
            else
                return null;
        });

        pay.setAmount("0.01");
        pay.setOrderNo("165491961984");
        pay.setUserCode("oy1AuwRNd08Fkr9pvgUTmuPiHuu4");
        pay.setNotifyUrl("http://115.28.150.165/forms/FrmWxMessage");
        String result = pay.requestPay("测试");
        log.info(result);
    }

}
