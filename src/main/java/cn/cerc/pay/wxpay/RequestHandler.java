package cn.cerc.pay.wxpay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 '微信支付服务器签名支付请求请求类
 '============================================================================
 'api说明：
 'init(app_id, app_secret, partner_key, app_key);
 '初始化函数，默认给一些参数赋值，如cmdno,date等。
 'setKey(key_)'设置商户密钥
 'getLasterrCode(),获取最后错误号
 'GetToken();获取Token
 'getTokenReal();Token过期后实时获取Token
 'createMd5Sign(signParams);生成Md5签名
 'genPackage(packageParams);获取package包
 'createSHA1Sign(signParams);创建签名SHA1
 'sendPrepay(packageParams);提交预支付
 'getDebugInfo(),获取debug信息
 '============================================================================
 '*/
public class RequestHandler {

    private String key;
    /** 请求的参数 */
    private SortedMap<String, String> parameters;
    /** Token */
    private String charset;
    /** debug信息 */
    private String debugInfo;
    private String last_errcode;

    private HttpServletRequest request;

    private HttpServletResponse response;

    // 初始构造函数。
    public RequestHandler(HttpServletRequest request, HttpServletResponse response) {
        this.last_errcode = "0";
        this.request = request;
        this.response = response;
        this.charset = "UTF-8";
        this.parameters = new TreeMap<String, String>();
    }

    // 初始化函数。
    public void init(String app_id, String app_secret, String partner_key) {
        this.last_errcode = "0";
        this.debugInfo = "";
        this.key = partner_key;
    }

    // 初始化函数。
    public void init(String app_id, String partner_key) {
        this.key = partner_key;
    }

    public void init() {
    }

    // 获取最后错误号
    public String getLasterrCode() {
        return last_errcode;
    }

    // 获取参数值
    public String getParameter(String parameter) {
        String s = (String) this.parameters.get(parameter);
        return (null == s) ? "" : s;
    }

    // 特殊字符处理
    public String UrlEncode(String src) throws UnsupportedEncodingException {
        return URLEncoder.encode(src, this.charset).replace("+", "%20");
    }

    // 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
    @SuppressWarnings("rawtypes")
    public String createSign(SortedMap<String, String> packageParams) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + this.getKey());

        String sign = MD5Util.MD5Encode(sb.toString(), this.charset).toUpperCase();
        System.out.println("我的签名：" + sb.toString());
        return sign;

    }

    // 创建package签名
    @SuppressWarnings("rawtypes")
    public boolean createMd5Sign(String signParams) {
        StringBuffer sb = new StringBuffer();
        Set es = this.parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }

        // 算出摘要
        String enc = this.getCharacterEncoding(this.request, this.response);
        String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();

        String tenpaySign = this.getParameter("sign").toLowerCase();

        // debug信息
        this.setDebugInfo(sb.toString() + " => sign:" + sign + " tenpaySign:" + tenpaySign);

        return tenpaySign.equals(sign);
    }

    // 输出XML
    @SuppressWarnings("rawtypes")
    public String parseXML() {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = this.parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"appkey".equals(k)) {
                sb.append("<" + k + ">" + getParameter(k) + "</" + k + ">\n");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    // 获取编码字符集
    private String getCharacterEncoding(HttpServletRequest request, HttpServletResponse response) {
        if (null == request || null == response) {
            return "gbk";
        }
        String enc = request.getCharacterEncoding();
        if (null == enc || "".equals(enc)) {
            enc = response.getCharacterEncoding();
        }
        if (null == enc || "".equals(enc)) {
            enc = "gbk";
        }
        return enc;
    }

    // 设置debug信息
    protected void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    public String getDebugInfo() {
        return debugInfo;
    }

    public String getKey() {
        return key;
    }

}
