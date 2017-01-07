package cn.cerc.pay.wxpay;

public class WeixinConfig {
    // 微信支付商户开通后 微信会提供appid和appsecret和商户号partner
    private String appId = "";
    private String appSecret = "";
    // 商户号
    private String mch_id = "";
    // 这个参数partnerkey是在商户后台配置的一个32位的key,微信商户平台-账户设置-安全设置-api安全
    private String apiKey = "";

    // HTTPS证书的本地路径
    private String certLocalPath = "";

    // HTTPS证书密码，默认密码等于商户号MCHID
    private String certPassword = "";

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    //
    // public String getNotifyUrl() {
    // return notifyUrl;
    // }
    //
    // public void setNotifyUrl(String notifyUrl) {
    // this.notifyUrl = notifyUrl;
    // }

    public String getCertLocalPath() {
        return certLocalPath;
    }

    public void setCertLocalPath(String certLocalPath) {
        this.certLocalPath = certLocalPath;
    }

    public String getCertPassword() {
        return certPassword;
    }

    public void setCertPassword(String certPassword) {
        this.certPassword = certPassword;
    }

}
