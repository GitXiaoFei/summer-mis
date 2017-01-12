package cn.cerc.pay.wxpay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jboss.logging.Logger;

import com.google.gson.Gson;

import cn.cerc.jbean.core.Application;
import cn.cerc.jdb.core.IConfig;
import cn.cerc.jdb.core.IHandle;

//APP支付
public class WxpayAPI {
    private static Logger log = Logger.getLogger(WxpayAPI.class);
    // 申请支付金额
    private String amount;
    // 申请支付订单号
    private String orderNo;
    // 发起申请的当前主机IP
    private String clientIP = "127.0.0.1";
    // 帐套代码
    private String corpNo = "000000";
    // 回调网址, 微信支付成功后通知地址 必须要求80端口并且地址不能带参数
    private String notifyUrl;
    // 连接配置参数
    public static final String config_appId = "wx.Open_AppID";
    public static final String config_appSecret = "wx.Open_ApiKey";
    // 商户代码
    public static final String config_appMachId = "wx.Open_MchId";
    private String appId;
    private String appSecret;
    private String appMachId;
    // 当前webSite
    private String rootSite;

    public WxpayAPI(IHandle handle, IConfig config) {
        appId = config.getProperty(config_appId);
        appMachId = config.getProperty(config_appMachId);
        appSecret = config.getProperty(config_appSecret);
        rootSite = config.getProperty("app.rootSite");
        if (handle != null)
            this.corpNo = handle.getCorpNo();
    }

    @SuppressWarnings({ "static-access", "unchecked" })
    public Map<String, String> requestPay(String body) {
        String notify_url = String.format("%s/%s/%s", rootSite, Application.getConfig().getPathForms(), this.notifyUrl);
        String trade_type = "APP";
        String nonce_str = Sha1Util.getNonceStr();
        String total_fee = String.valueOf(new BigDecimal(amount).multiply(new BigDecimal(100)).intValue());// 金额
                                                                                                           // 微信是以分为单位的;
        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", this.appId);
        // 商户号
        packageParams.put("mch_id", this.appMachId);
        // 随机字符串
        packageParams.put("nonce_str", nonce_str);
        // 商品描述根据情况修改
        packageParams.put("body", body);
        // 附加数据 原样返回
        packageParams.put("attach", corpNo);
        // 商户订单号
        packageParams.put("out_trade_no", this.orderNo);
        // 总金额以分为单位，不带小数点
        packageParams.put("total_fee", total_fee);
        packageParams.put("spbill_create_ip", clientIP);
        // 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
        packageParams.put("notify_url", notify_url);
        // 交易类型，原生扫码
        packageParams.put("trade_type", trade_type);

        RequestHandler reqHandler = new RequestHandler(null, null);
        reqHandler.init(this.appId, this.appSecret);

        String sign = reqHandler.createSign(packageParams);
        log.info("my sign:" + sign);
        String xml = "<xml>" + "<appid>" + this.appId + "</appid>" + "<mch_id>" + this.appMachId + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>" + "<body><![CDATA[" + body
                + "]]></body>" + "<out_trade_no>" + this.orderNo + "</out_trade_no>" + "<attach>" + corpNo + "</attach>"
                + "<total_fee>" + total_fee + "</total_fee>" + "<spbill_create_ip>" + clientIP + "</spbill_create_ip>"
                + "<notify_url>" + notify_url + "</notify_url>" + "<trade_type>" + trade_type + "</trade_type></xml>";
        String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        Map<String, String> prepaid = new cn.cerc.pay.wxpay.GetWxOrderno().getResultMap(createOrderURL, xml);
        prepaid.put("timestamp", Long.toString(new Date().getTime()).substring(0, 10));

        StringBuffer sb = new StringBuffer();
        sb.append("appid=" + prepaid.get("appid"));
        sb.append("&noncestr=" + prepaid.get("nonce_str"));
        sb.append("&package=Sign=WXPay");
        sb.append("&partnerid=" + prepaid.get("mch_id"));
        sb.append("&prepayid=" + prepaid.get("prepay_id"));
        sb.append("&timestamp=" + prepaid.get("timestamp"));
        sb.append("&key=" + this.appSecret);

        sign = MD5Util.MD5Encode(sb.toString(), "utf-8").toUpperCase();
        prepaid.put("sign", sign);
        return prepaid;
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

    public static void main(String[] args) {
        WxpayAPI pay = new WxpayAPI(null, (key, def) -> {
            if (config_appId.equals(key))
                return "wx880d8fc48ac1e88e";
            else if (config_appSecret.equals(key))
                return "e10adc3949ba59abbe56e057f20f883e";
            else if (config_appMachId.equals(key))
                return "1281260601";
            else
                return null;
        });
        pay.setAmount("0.01");
        pay.setOrderNo("165491961984");
        pay.setNotifyUrl("http://115.28.150.165/forms/FrmWxMessage");
        Map<String, String> result = pay.requestPay("测试");
        System.out.println(new Gson().toJson(result));
    }
}
