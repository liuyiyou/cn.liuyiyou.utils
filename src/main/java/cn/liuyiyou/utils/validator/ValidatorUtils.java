package cn.liuyiyou.utils.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * @author: liuyiyou
 * @date: 2018/1/10
 */
public class ValidatorUtils {

    /**
     * 手机号码验证接口
     *
     * @param phoneNumber
     * @return
     */
    public static boolean checkMobileNumber(String phoneNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^(1[34578][0-9]{9})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");//Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(phoneNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 是否为空
     * @param object
     * @return
     */
    public static boolean checkNotNull(Object object) {
        return object == null ? false : true;
    }

}
