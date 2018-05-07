package cn.liuyiyou.utils.date;

import java.util.Set;


public class DaysUtil {

    public static String getMinAndMaxDays(Set<String> days) {
        int min = Integer.parseInt(days.toArray()[0].toString());
        int max = Integer.parseInt(days.toArray()[0].toString());
        for (String day : days) {
            Integer dayInt = Integer.parseInt(day);
            if (dayInt < min) {
                min = dayInt;
            }
            if (dayInt > max) {
                max = dayInt;
            }
        }
        return min + "--" + max;
    }

}
