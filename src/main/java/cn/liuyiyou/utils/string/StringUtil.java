package cn.liuyiyou.utils.string;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {


    /**
     * 是否是中文字符
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        return Pattern.compile("[\\u4e00-\\u9fa5]").matcher(c + "").find();
    }


    public static Boolean isEmptyJson(final String jsonValue) {
        return (null == jsonValue || "" == jsonValue || "[]" == jsonValue);
    }

    public static Boolean isEmptyString(final String value) {
        return (null == value || "".equals(value.trim()));
    }

    public static Boolean isNotEmptyString(final String value) {
        return !isEmptyString(value);
    }


    public static String combineString(String... inputs) {
        StringBuilder tmp = new StringBuilder();
        for (String string : inputs) {
            tmp.append(string);
        }
        return tmp.toString();
    }

    /**
     * 生成指定长度字符串，不足部分补齐指定字符串
     *
     * @param val    原始字符串
     * @param pad    补齐不足部分使用字符串
     * @param length 最终要生成的字符串长度
     * @return
     */
    public static String lpad(String val, String pad, Integer length) {
        if (null != val && val.length() >= length) {
            return val;
        }
        // 生成追加部分字符串
        StringBuilder sb = new StringBuilder(pad);
        int orgLength = null == val ? 0 : val.length();
        int padLength = length - orgLength;
        while (sb.length() < padLength) {
            sb.append(pad);
        }
        // 截取多余部分
        StringBuilder result = new StringBuilder(sb.substring(sb.length() - padLength));
        if (null != val) {
            result.append(val);
        }
        return result.toString();
    }

    /**
     * 右补全
     *
     * @param val
     * @param pad
     * @param length
     * @return
     */
    public static String rpad(String val, String pad, Integer length) {
        if (null != val && val.length() >= length) {
            return val;
        }
        // 生成追加部分字符串
        StringBuilder sb = new StringBuilder(val);
        while (sb.length() < length) {
            sb.append(pad);
        }
        return sb.toString().substring(0, length);
    }

    public static String getNoBlankStr(String input) {
        if (isEmptyString(input)) {
            return null;
        }
        return input;
    }

    public static String getNoNullStr(String input) {
        return (input == null ? "" : input.trim());
    }

    public static String getMatch(String input, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        if (m.find()) {
            return m.group();
        }
        return null;
    }

    public static String captureMatch(String input, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    /**
     * url字符串增加参数
     *
     * @param url
     * @param paramName
     * @param paramVal
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String addParam2Url(String url, String paramName, String paramVal, String encode)
            throws UnsupportedEncodingException {
        StringBuilder tmp = new StringBuilder(url);
        if (url.contains("?") || url.contains("&")) {
            tmp.append("&");
        } else {
            tmp.append("?");
        }
        tmp.append(paramName).append("=").append(URLEncoder.encode(paramVal, encode));
        return tmp.toString();
    }

    /**
     * url字符串增加参数
     *
     * @param url
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String addParams2Url(String url, Map<String, String> params, String encode)
            throws UnsupportedEncodingException {
        Iterator<String> keyIter = params.keySet().iterator();
        StringBuilder tmp = new StringBuilder(url);
        if (keyIter.hasNext()) {
            if (url.contains("?") || url.contains("&")) {
                tmp.append("&");
            } else {
                tmp.append("?");
            }
            String key = keyIter.next();
            tmp.append(key).append("=").append(URLEncoder.encode(params.get(key), encode));
        }
        while (keyIter.hasNext()) {
            String key = keyIter.next();
            tmp.append("&").append(key).append("=").append(URLEncoder.encode(params.get(key), encode));
        }
        return tmp.toString();
    }

    public static String addParams2UrlSorted(String url, Map<String, String> params, String encode)
            throws UnsupportedEncodingException {
        List<String> listkey = new ArrayList<String>(params.keySet());
        StringBuilder tmp = new StringBuilder(url);
        Collections.sort(listkey);
        String key = null;
        String val = null;
        for (int i = 0; i < listkey.size(); i++) {
            key = listkey.get(i);
            val = params.get(key);
            if (i == 0) {
                if (url.contains("?") || url.contains("&")) {
                } else {
                    tmp.append("?");
                }
            } else {
                tmp.append("&");
            }
            tmp.append(key).append("=").append(URLEncoder.encode(val, encode));
        }
        return tmp.toString();
    }

    public static String createLinkString(Map<String, String> params) {
        if (params == null || params.size() == 0) {
            return "";
        }

        List<String> keys = new ArrayList<String>(params.keySet());
        //Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        Collections.sort(keys, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (isNotEmptyString(value)) {
                if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                    prestr = prestr + key + "=" + value;
                } else {
                    prestr = prestr + key + "=" + value + "&";
                }
            }
        }
        return prestr;
    }


    /**
     * 功能：前台交易构造HTTP POST自动提交表单<br>
     *
     * @param reqUrl  表单提交地址<br>
     * @param hiddens 以MAP形式存储的表单键值<br>
     * @param method  POST或者GET提交<br>
     * @return 构造好的HTTP POST或者GET交易表单<br>
     */
    public static String createAutoFormHtml(String reqUrl, Map<String, String> hiddens, String method, String acceptCharset, String onSubmit, boolean newWindow) {
        StringBuffer sf = new StringBuffer();
        String otherParams = (isNotEmptyString(acceptCharset) ? "  accept-charset=\"" + acceptCharset + "\"" : "")
                + (isNotEmptyString(onSubmit) ? "  onsubmit=\"" + onSubmit + "\"" : "");
        sf.append("<form id = \"pay_form\" action=\"" + reqUrl + "\" target=\"" + (newWindow ? "_blank" : "_self") + "\"  method=\"" + method + "\"" + otherParams + ">");
        if (null != hiddens && 0 != hiddens.size()) {
            Set<Entry<String, String>> set = hiddens.entrySet();
            Iterator<Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Entry<String, String> ey = it.next();
                String key = ey.getKey();
                String value = ey.getValue();
                if (isNotEmptyString(value)) {
                    sf.append("<input type=\"hidden\" name=\"" + key + "\" value=\"" + value + "\"/>");
                }

            }
        }
        sf.append("</form>");
        return sf.toString();
    }

    public static String convertToJsonString(String src, String splitChar, String secondSliptChar) {
        if (isEmptyString(src) || isEmptyString(splitChar)) {
            return null;
        }
        String[] strs = src.split(splitChar);
        if (null != strs && strs.length > 0) {
            JSONObject jsonObj = new JSONObject();
            String key, value;
            int index = 0;
            for (String str : strs) {
                index = str.indexOf(secondSliptChar);
                key = str.substring(0, index);
                value = str.substring(index + 1);
                if (StringUtil.isNotEmptyString(value)) {
                    jsonObj.put(key, value);
                }

            }
            return jsonObj.toJSONString();
        }

        return null;
    }

    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * isValidBankCardNo: 是否是正确的银行卡卡号， TODO:目前只是使用正则判断，以后改成用Luhn算法判断
     *
     * @param cardNo        银联卡号
     * @param bankId        银行机构id
     * @param acctChildType 账户子类型：1-储蓄卡；2-信用卡
     * @return
     */
    public static boolean isValidBankCardNo(String cardNo, Short bankId, Byte acctChildType) {
        if (cardNo == null || "".equals(cardNo.trim())) {
            return false;
        }

        Pattern p = Pattern.compile("^[0-9]+");
        Matcher m = p.matcher(cardNo);
        if (!m.matches()) {
            return false;
        }

        return true;
    }

    /**
     * parseNumber: 将文字转成Number， 不抛出错误 <br/>
     * 此方法和apache-commons里面的NumberUtils的toXX方法类似
     *
     * @param numberString 字符串
     * @param numberClass  支持Integer.class Long.class Short.class
     * @param defaultValue 如果转换失败, 返回的默认值
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T parseNumber(String numberString, Class<T> numberClass, T defaultValue) {
        try {
            if (numberClass.equals(Integer.class)) {
                return (T) Integer.valueOf(numberString);
            } else if (numberClass.equals(Long.class)) {
                return (T) Long.valueOf(numberString);
            } else if (numberClass.equals(Short.class)) {
                return (T) Short.valueOf(numberString);
            }
        } catch (NumberFormatException ex) {
            return defaultValue;
        } catch (NullPointerException ex) {
            return defaultValue;
        } catch (Exception ex) {
            return defaultValue;
        }
        return null;
    }

    /**
     * parseIntegerDfZero: 将文字转成Integer， 不抛出错误 <br/>
     *
     * @param intString    字符串
     * @param defaultValue 如果转换失败, 返回的默认值
     * @return 如果转换出错， 返回defaultValue
     */
    public static Integer parseInteger(String intString, Integer defaultValue) {
        return parseNumber(intString, Integer.class, defaultValue);
    }

    public static Byte parseByte(String byteString, Byte defaultVal) {
        if (isEmptyString(byteString)) {
            return defaultVal;
        } else {
            return Byte.decode(byteString);
        }
    }

    public static String implode(Collection<?> cols, String glue) {
        if (null == cols || cols.size() == 0) {
            return "";
        }
        StringBuilder tmp = new StringBuilder();
        Iterator<?> objIter = cols.iterator();
        while (objIter.hasNext()) {
            Object obj = objIter.next();
            tmp.append(obj.toString()).append(glue);
        }
        if (tmp.length() > 0) {
            return tmp.substring(0, tmp.length() - 1);
        }
        return "";
    }





    /**
     * @return返回隐藏中间的号码变成*号的手机号
     */
    public static String getMaskAccountName(String str) {
        if (str == null || str.length() <= 10) {
            return str;
        } else {
            String result = str.substring(0, str.length() - (str.substring(3)).length()) + "****" + str.substring(7);
            return result;
        }
    }

    /**
     * @return返回隐藏中间的号码变成*号身份证号码
     */
    public static String getMaskIdCardNum(String str) {
        if (str == null || str.length() < 15 || str.length() > 18) {
            return str;
        } else {
            int strLen = str.length();
            int maskLen = strLen - 8;
            String maskStr = "";
            for (int i = 0; i < maskLen; i++) {
                maskStr += "*";
            }
            String result = str.substring(0, 4) + maskStr + str.substring(strLen - 4, strLen);
            return result;
        }
    }


    /**
     * 属性json串，拼接成key串：71_11,72_4
     *
     * @param skuJson
     * @param isSort  是否排序
     * @return
     */
    public static String joinSkuJsonKey(String skuJson, boolean isSort) {
        if (StringUtil.isEmptyString(skuJson)) {
            return "";
        }
        String rst = "";
        JSONArray skuArry = JSONArray.parseArray(skuJson);

		/*[
		    {
		        "attrid-name": "71-颜色",
		        "valid-name": "11-红色"
		    },
		    {
		        "attrid-name": "72-规格",
		        "valid-name": "4-4G"
		    }
		]*/
        if (!skuArry.isEmpty()) {
            List strList = new ArrayList();
            for (int i = 0; i < skuArry.size(); i++) {

                JSONObject json = (JSONObject) skuArry.get(i);
                String attridName = json.getString("attrid-name");
                String validName = json.getString("valid-name");
                int aEndIndex = attridName.indexOf("-") > -1 ? attridName.indexOf("-") : attridName.length();
                int vEndIndex = validName.indexOf("-") > -1 ? validName.indexOf("-") : validName.length();
                String attr_valid = attridName.substring(0, aEndIndex) + "_" + validName.substring(0, vEndIndex);
                if (StringUtil.isNotEmptyString(attr_valid)) {
                    strList.add(attr_valid);
                }
            }
            if (isSort) {
                Collections.sort(strList);
            }
            rst = StringUtils.join(strList.iterator(), ",");
        }
        return rst;

    }

    /**
     * 属性json串，拼接成名称串：4G,颜色
     *
     * @param skuJson
     * @return
     */
    public static String joinSkuJsonValName(String skuJson) {
        if (StringUtil.isEmptyString(skuJson)) {
            return "";
        }
        String rst = "";
        JSONArray skuArry = JSONArray.parseArray(skuJson);

		/*[
		    {
		        "attrid-name": "71-颜色",
		        "valid-name": "11-红色"
		    },
		    {
		        "attrid-name": "72-规格",
		        "valid-name": "4-4G"
		    }
		]*/
        if (!skuArry.isEmpty()) {
            List strList = new ArrayList();
            for (int i = 0; i < skuArry.size(); i++) {

                JSONObject json = (JSONObject) skuArry.get(i);
                String validName = json.getString("valid-name");
                int vEndIndex = validName.indexOf("-") > -1 ? validName.indexOf("-") : validName.length();
                String valName = validName.substring(vEndIndex + 1);
                if (StringUtil.isNotEmptyString(valName)) {
                    strList.add(valName);
                }
            }
            Collections.sort(strList);
            rst = StringUtils.join(strList.iterator(), ",");
        }
        return rst;
    }

    public static void main(String[] args) {
//		System.out.println(getMaskAccountName(null));
//		System.out.println(getMaskAccountName("1231"));
//		System.out.println(getMaskAccountName("13188882222"));
//		List<Integer> tmp = new ArrayList<Integer>();
//		tmp.add(1);
//		tmp.add(3);
//		tmp.add(4);
//		System.out.println(implode(tmp, ","));
//
//		String id = "1231";
//		String[] rdstrAr = genRanCode(id, 5);
//		System.out.println(rdstrAr[0]);

        String id = "130424198144256";
        System.out.println(getMaskIdCardNum(id));

    }

    /**
     * 为数字字符串左填充0
     *
     * @param str       原字符串
     * @param strLength 需要填充的总长度
     * @return
     */
    public static String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append("0").append(str);
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    public static String appendLeft(String str, String addStr, int strLength) {
        int strLen = str.length();
        StringBuffer sb = null;
        while (strLen < strLength) {
            sb = new StringBuffer();
            sb.append(addStr).append(str);
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }




    public static String appendUrl(String src, String tmp) {

        if (StringUtil.isEmptyString(tmp)) {
            return src;
        }
        StringBuilder sb = new StringBuilder(src);
        if (!src.endsWith("/") && !tmp.startsWith("/")) {
            sb.append("/");
        }
        sb.append(tmp);
        return sb.toString();
    }





    /**
     * 属性json串，拼接成键值串：颜色：红色/规格：4G
     *
     * @param skuJson
     * @return
     */
    public static String joinAttrNameAndValNames(String skuJson) {
        if (StringUtil.isEmptyString(skuJson)) {
            return "";
        }
        String rst = "";
        JSONArray skuArry = JSONArray.parseArray(skuJson);
        if (!skuArry.isEmpty()) {
            List strList = new ArrayList();
            for (int i = 0; i < skuArry.size(); i++) {
                JSONObject json = (JSONObject) skuArry.get(i);
                String attridName = json.getString("attrid-name");
                String validName = json.getString("valid-name");
                int aEndIndex = attridName.indexOf("-") > -1 ? attridName.indexOf("-") : attridName.length();
                int vEndIndex = validName.indexOf("-") > -1 ? validName.indexOf("-") : validName.length();
                String attrName = attridName.substring(aEndIndex + 1);
                String valName = validName.substring(vEndIndex + 1);
                if (StringUtil.isNotEmptyString(attrName)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(attrName).append(":").append(valName);
                    strList.add(sb.toString().trim());
                }
            }
            Collections.sort(strList);
            rst = StringUtils.join(strList.iterator(), "/");
        }
        return rst;
    }
}
