package cn.cerc.pay.alipay;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import cn.cerc.jbean.core.ServerConfig;
import cn.cerc.jdb.core.IHandle;

public class AlipayJs {
    private static Logger log = Logger.getLogger(AlipayJs.class);
    // 申请支付金额
    private String amount;
    // 申请支付订单号
    private String orderNo;
    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    private String notify_url;
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    private String return_url;
    // 用户付款中途退出返回商户网站的地址。
    private String show_url;
    // 合作身份者ID
    private String partner;
    // 收款支付宝账号
    private String seller_id;
    // 商户的私钥,需要PKCS8格式
    private String private_key;

    public AlipayJs(IHandle handle) {
        ServerConfig config = new ServerConfig();
        partner = config.getProperty("alipay.partner");
        seller_id = config.getProperty("alipay.sellerId");
        private_key = config.getProperty("alipay.privateKey");

    }

    @SuppressWarnings("static-access")
    public String requestPay(AlipayConfig alipayConfig, String subject) {
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", this.partner);
        sParaTemp.put("seller_id", this.seller_id);
        sParaTemp.put("_input_charset", alipayConfig.input_charset);
        sParaTemp.put("payment_type", alipayConfig.payment_type);
        sParaTemp.put("notify_url", this.getNotify_url());
        sParaTemp.put("return_url", this.getReturn_url());
        sParaTemp.put("out_trade_no", orderNo);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", amount);
        sParaTemp.put("show_url", this.getShow_url());
        sParaTemp.put("app_pay", "Y"); // 尝试唤起支付宝客户端进行支付
        // sParaTemp.put("body", body); //商品描述
        // 其他业务参数根据在线开发文档，添加参数.文档地址:https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.2Z6TSk&treeId=60&articleId=103693&docType=1
        // 如sParaTemp.put("参数名","参数值");

        String sHtmlText = buildRequest(sParaTemp, "get", "确认");
        log.info("---------------支付宝提交form--------");
        log.info(sHtmlText);
        return sHtmlText;
    }

    /**
     * 生成签名结果
     * 
     * @param sPara
     *            要签名的数组
     * @return String 签名结果字符串
     */
    private String buildRequestMysign(Map<String, String> sPara) {
        String prestr = AlipayCore.createLinkString(sPara);
        String mysign = RSA.sign(prestr, this.private_key, AlipayConfig.input_charset);
        return mysign;
    }

    /**
     * 生成要请求给支付宝的参数数组
     * 
     * @param sParaTemp
     *            请求前的参数数组
     * @return String 要请求的参数数组
     */
    private Map<String, String> buildRequestPara(Map<String, String> sParaTemp) {
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        String mysign = buildRequestMysign(sPara);
        sPara.put("sign", mysign);
        sPara.put("sign_type", "RSA");
        return sPara;
    }

    /**
     * 建立请求，以表单HTML形式构造（默认）
     * 
     * @param sParaTemp
     *            请求参数数组
     * @param strMethod
     *            提交方式。两个值可选：post、get
     * @param strButtonName
     *            确认按钮显示文字
     * @return String 提交表单HTML文本
     */
    private String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName) {
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        List<String> keys = new ArrayList<String>(sPara.keySet());
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append(
                "<!DOCTYPE html><html lang=\"en\"><head><meta charset=\"UTF-8\"><title>支付宝安全支付</title></head><body>");

        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + AlipayConfig.ALIPAY_GATEWAY_NEW
                + "_input_charset=" + AlipayConfig.input_charset + "\" method=\"" + strMethod + "\">");
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
        sbHtml.append("</body></html>");
        return sbHtml.toString();
    }

    // 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
    public String query_timestamp() throws MalformedURLException, DocumentException, IOException {
        String strUrl = AlipayConfig.ALIPAY_GATEWAY_NEW + "service=query_timestamp&partner=" + this.partner
                + "&_input_charset" + AlipayConfig.input_charset;
        StringBuffer result = new StringBuffer();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new URL(strUrl).openStream());
        List<Node> nodeList = doc.selectNodes("//alipay/*");
        for (Node node : nodeList) {
            if (node.getName().equals("is_success") && node.getText().equals("T")) {
                List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
                for (Node node1 : nodeList1) {
                    result.append(node1.getText());
                }
            }
        }
        return result.toString();
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

    public String getNotify_url() {
        return this.notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getShow_url() {
        return show_url;
    }

    public void setShow_url(String show_url) {
        this.show_url = show_url;
    }
}
