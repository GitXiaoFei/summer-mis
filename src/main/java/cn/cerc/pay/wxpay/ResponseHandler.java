package cn.cerc.pay.wxpay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 微信支付服务器签名支付请求应答类 api说明： getKey()/setKey(),获取/设置密钥
 * getParameter()/setParameter(),获取/设置参数值 getAllParameters(),获取所有参数
 * isTenpaySign(),是否财付通签名,true:是 false:否 getDebugInfo(),获取debug信息
 */
public class ResponseHandler {

    /** 密钥 */
    private String key;

    /** 应答的参数 */
    private SortedMap<String, String> parameters;

    /** debug信息 */
    private String debugInfo;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private String uriEncoding;

    private SortedMap<String, String> smap;

    public SortedMap<String, String> getSmap() {
        return smap;
    }

    // 构造函数
    @SuppressWarnings("rawtypes")
    public ResponseHandler(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.smap = new TreeMap<String, String>();
        this.key = "";
        this.parameters = new TreeMap<String, String>();
        this.debugInfo = "";
        this.uriEncoding = "";

        Map m = this.request.getParameterMap();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String k = (String) it.next();
            String v = ((String[]) m.get(k))[0];
            this.setParameter(k, v);
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            if (!"".equals(sb.toString())) {
                Document doc = DocumentHelper.parseText(sb.toString());
                Element root = doc.getRootElement();
                for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
                    Element e = (Element) iterator.next();
                    smap.put(e.getName(), e.getText());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 是否微信V3签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
    @SuppressWarnings("rawtypes")
    public boolean isWechatSign() {
        StringBuffer sb = new StringBuffer();
        Set es = this.smap.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);
        // 算出摘要
        String enc = TenpayUtil.getCharacterEncoding(this.request, this.response);
        String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();
        String ValidSign = ((String) this.smap.get("sign")).toLowerCase();

        // debug信息
        System.out.println(sb.toString() + " => sign:" + sign + " ValidSign:" + ValidSign);

        return ValidSign.equals(sign);
    }

    // 获取密钥
    public String getKey() {
        return key;
    }

    // 设置密钥
    public void setKey(String key) {
        this.key = key;
    }

    // 获取参数值
    public String getParameter(String parameter) {
        String s = (String) this.parameters.get(parameter);
        return (null == s) ? "" : s;
    }

    // 设置参数值
    public void setParameter(String parameter, String parameterValue) {
        String v = "";
        if (null != parameterValue) {
            v = parameterValue.trim();
        }
        this.parameters.put(parameter, v);
    }

    // 返回所有的参数
    public SortedMap<String, String> getAllParameters() {
        return this.parameters;
    }

    // 返回处理结果给财付通服务器。
    public void sendToCFT(String msg) throws IOException {
        String strHtml = msg;
        PrintWriter out = this.getHttpServletResponse().getWriter();
        out.println(strHtml);
        out.flush();
        out.close();
    }

    // 获取uri编码
    public String getUriEncoding() {
        return uriEncoding;
    }

    // 获取debug信息
    public String getDebugInfo() {
        return debugInfo;
    }

    // 获取debug信息
    protected void setDebugInfo(String debugInfo) {
        this.debugInfo = debugInfo;
    }

    protected HttpServletRequest getHttpServletRequest() {
        return this.request;
    }

    protected HttpServletResponse getHttpServletResponse() {
        return this.response;
    }

}
