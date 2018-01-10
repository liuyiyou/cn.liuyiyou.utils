package cn.liuyiyou.utils.date;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * 
 * @author: liuyiyou@daojia.com
 * @date: 2016年10月21日
 * @version: V1.0
 * @Copyright: 2015 www.daojia.com Inc. All rights reserved.
 */
public class DaysUtil {

	@Test
	public void getMinAndMaxDays() {
		Set<String> daySet = new HashSet<String>();
		daySet.add("20160103");
		daySet.add("20160101");
		daySet.add("20160223");
		daySet.add("20170223");
		

		int min = Integer.parseInt(daySet.toArray()[0].toString());
		int max = Integer.parseInt(daySet.toArray()[0].toString());
		for (String day : daySet) {
			Integer dayInt = Integer.parseInt(day);
			if (dayInt < min) {
				min = dayInt;
			}
			if (dayInt > max) {
				max = dayInt;
			}
		}

		System.out.println(min+"--"+max);
	}

}
