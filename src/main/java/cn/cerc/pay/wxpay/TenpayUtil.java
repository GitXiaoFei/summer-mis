package cn.cerc.pay.wxpay;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TenpayUtil {

    private static Object Server;

    /**
     * 把对象转换为int数值.
     * 
     * @param obj
     *            包含数字的对象.
     * @return int 转换后的数值,对不能转换的对象返回0。
     */
    public static int toInt(Object obj) {
        int a = 0;
        try {
            if (obj != null)
                a = Integer.parseInt(obj.toString());
        } catch (Exception e) {

        }
        return a;
    }

    /**
     * 获取当前时间 yyyyMMddHHmmss
     * 
     * @return String
     */
    public static String getCurrTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);
        return s;
    }

    /**
     * 获取编码字符集
     * 
     * @param request
     *            请求
     * @param response
     *            返回
     * @return String 返回执行结果
     */

    public static String getCharacterEncoding(HttpServletRequest request, HttpServletResponse response) {

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

    public static String URLencode(String content) {

        String URLencode;

        URLencode = replace(Server.equals(content), "+", "%20");

        return URLencode;
    }

    private static String replace(boolean equals, String string, String string2) {

        return null;
    }

    // 获取unix时间，从1970-01-01 00:00:00开始的秒数
    public static long getUnixTime(Date date) {
        if (null == date) {
            return 0;
        }

        return date.getTime() / 1000;
    }

    public static String QRfromGoogle(String chl) {
        int widhtHeight = 300;
        String EC_level = "L";
        int margin = 0;
        String QRfromGoogle;
        chl = URLencode(chl);

        QRfromGoogle = "http://chart.apis.google.com/chart?chs=" + widhtHeight + "x" + widhtHeight + "&cht=qr&chld="
                + EC_level + "|" + margin + "&chl=" + chl;

        return QRfromGoogle;
    }

    // 时间转换成字符串
    public static String date2String(Date date, String formatType) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        return sdf.format(date);
    }

}
