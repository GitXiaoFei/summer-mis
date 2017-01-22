package cn.cerc.pay.alipay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import cn.cerc.jbean.core.ServerConfig;
import cn.cerc.jdb.core.IHandle;

public class AlipayNotify {
    // 合作身份者ID
    private String partner;
    // 支付宝的公钥
    private String alipay_public_key;

    public AlipayNotify(IHandle handle) {
        ServerConfig config = new ServerConfig();
        partner = config.getProperty("alipay.partner");
        alipay_public_key = config.getProperty("alipay.alipayPublicKey");
    }

    /**
     * 验证消息是否是支付宝发出的合法消息
     * 
     * @param params
     *            通知返回来的参数数组
     * @return 验证结果
     */
    public boolean verify(Map<String, String> params) {
        String responseTxt = "false";
        if (params.get("notify_id") != null) {
            String notify_id = params.get("notify_id");
            responseTxt = verifyResponse(notify_id);
        }
        String sign = "";
        if (params.get("sign") != null) {
            sign = params.get("sign");
        }
        boolean isSign = getSignVeryfy(params, sign);
        if (isSign && responseTxt.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据反馈回来的信息，生成签名结果
     * 
     * @param Params
     *            通知返回来的参数数组
     * @param sign
     *            比对的签名结果
     * @return 生成的签名结果
     */
    private boolean getSignVeryfy(Map<String, String> Params, String sign) {
        Map<String, String> sParaNew = AlipayCore.paraFilter(Params);
        String preSignStr = AlipayCore.createLinkString(sParaNew);
        boolean isSign = RSA.verify(preSignStr, sign, this.alipay_public_key, AlipayConfig.input_charset);
        return isSign;
    }

    /**
     * 获取远程服务器ATN结果,验证返回URL
     * 
     * @param notify_id
     *            通知校验ID
     * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
     *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
    private String verifyResponse(String notify_id) {
        String partner = this.partner;
        String veryfy_url = AlipayConfig.HTTPS_VERIFY_URL + "partner=" + partner + "&notify_id=" + notify_id;
        return checkUrl(veryfy_url);
    }

    /**
     * 获取远程服务器ATN结果
     * 
     * @param urlvalue
     *            指定URL路径地址
     * @return 服务器ATN结果 验证结果集： invalid命令参数不对 出现这个错误，请检测返回处理中partner和key是否为空 true
     *         返回正确信息 false 请检查防火墙或者是服务器阻止端口问题以及验证时间是否超过一分钟
     */
    private static String checkUrl(String urlvalue) {
        String inputLine = "";
        try {
            URL url = new URL(urlvalue);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            inputLine = in.readLine().toString();
        } catch (Exception e) {
            e.printStackTrace();
            inputLine = "";
        }
        return inputLine;
    }
}
