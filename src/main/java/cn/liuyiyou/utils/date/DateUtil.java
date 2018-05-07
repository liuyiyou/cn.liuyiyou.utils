package cn.liuyiyou.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.liuyiyou.utils.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具类
 */
public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String YYYY_MMDD_HHMMSS = "yyyyMMddHHmmss";
	private static final String YYYY_MM_DD = "yyyy-MM-dd";
	private static final String YYYY_MMDD = "yyyyMMdd";

	/**
	 * 格式常用的日期格式
	 * 
	 * @param dt
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dt) throws ParseException {
		try {
			SimpleDateFormat df = new SimpleDateFormat(YYYY_MMDD_HHMMSS);
			return df.parse(dt);
		} catch (ParseException pe) {
			logger.error(pe.getMessage(), pe);
			throw pe;
		}
	}

	public static Date parseShortDate(String dt) throws Exception {
		if (StringUtil.isEmptyString(dt))
			return null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
			return df.parse(dt);
		} catch (Exception pe) {
			logger.error(pe.getMessage(), pe);
			throw pe;
		}
	}



	public static Date parseStatisticsDate(String dt) throws Exception {
		if (StringUtil.isEmptyString(dt))
			return null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(YYYY_MMDD);
			return df.parse(dt);
		} catch (Exception pe) {
			logger.error(pe.getMessage(), pe);
			throw pe;
		}
	}

	public static String formatDate(Date dt) {
		if (dt == null)
			return "0000-00-00 00:00:00";
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MMDD_HHMMSS);
		return df.format(dt);
	}

	public static String formatStatisticsDate(Date dt) {
		if (dt == null)
			return "00000000";
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MMDD);
		return df.format(dt);
	}

	/**
	 * 生成baidu报表格式的默认开始统计日期
	 * 
	 * @return
	 */
	public static String formatBaiDuDefaultStartDate() {
		Date yesterdayDate = getYesterday();
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		String date = df.format(yesterdayDate);
		date += "T00:00:00.000";

		return date;
	}

	public static String format1HourAgoBaiduStartDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR_OF_DAY, -1);
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		String result = df.format(c.getTime());
		return result + "T00:00:00.000";
	}

	public static String formatBaiDuStartDate(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		return df.format(date) + "T00:00:00.000";
	}

	/**
	 * 格式化成统计日期使用的前一天日期
	 * 
	 * @return
	 */
	public static String formatYesterdayStatisticsDate() {
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MMDD);
		Calendar cal = Calendar.getInstance();
		// 日期减一
		cal.add(Calendar.DATE, -1);

		return df.format(cal.getTime());
	}

	public static String formatAddStatisticsDate(String date, int days) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MMDD);
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseStatisticsDate(date));
		// 日期减一
		cal.add(Calendar.DATE, days);

		return df.format(cal.getTime());
	}

	/**
	 * 格式化成n天前的日期
	 * 
	 * @param days
	 * @return
	 */
	public static String formatDaysAgoStatisticsDate(int days) {
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MMDD);
		Calendar cal = Calendar.getInstance();
		// 日期减days
		cal.add(Calendar.DATE, -days);

		return df.format(cal.getTime());
	}

	/**
	 * 格式化成通用日期格式的前一天日期
	 * 
	 * @return
	 */
	public static String formatYesterdayStatisticsDateCommon() {
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		Calendar cal = Calendar.getInstance();
		// 日期减一
		cal.add(Calendar.DATE, -1);

		return df.format(cal.getTime());
	}

	/**
	 * 日期减1
	 * 
	 * @return
	 */
	public static Date getYesterday() {
		Calendar cal = Calendar.getInstance();
		// 日期减一
		cal.add(Calendar.DATE, -1);

		return cal.getTime();
	}

	public static String getMonthfirstDay(String dateStr) throws Exception {
		Date date = parseStatisticsDate(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return formatStatisticsDate(cal.getTime());
	}

	public static String getDayAgoMonth(String dateStr, int month) throws Exception {
		Date date = parseStatisticsDate(dateStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -month);
		return formatStatisticsDate(cal.getTime());
	}

	/**
	 * 前 length 天
	 * 
	 * @param length
	 * @return
	 */
	public static Date getBefore(int length) {
		Calendar cal = Calendar.getInstance();
		// 日期减length
		cal.add(Calendar.DATE, -length);

		return cal.getTime();
	}

	/**
	 * 判断是否有效的运行日期参数
	 * 
	 * @param workDate
	 * @param endDate
	 * @return
	 * @throws Exception 
	 */
	public static boolean validWorkDate(String workDate, String endDate) throws Exception {
		// 格式统一为：yyyyMMdd 直接转成数字做简单的范围计算
		int work = Integer.parseInt(workDate);
		int end = Integer.parseInt(endDate);
		// 不能大于当前时间
		int now = Integer.parseInt(formatStatisticsDate(new Date()));
		if (work > now || end > now) {
			throw new Exception("输入时间非法：不能大于当前时间");
		}
		return work <= end;
	}

	public static String getYesterday(String dateStr) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyyMMdd").parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		String beforeDay = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
		return beforeDay;
	}

	public static String formatShortDate(Date dt) {
		if (dt == null)
			return "0000-00-00";
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		return df.format(dt);
	}

	/**
	 * 格式化时间到年月显示
	 * 
	 * @param dt
	 * @return
	 */
	public static String formatDate2YearMonth(Date dt) {
		if (dt == null)
			return "0000-00";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		return df.format(dt);
	}



	/**
	 * 格式化广告的开始投放时间: 两部分值简单相加
	 * @param startDate
	 * @param startTime
	 * @return
	 */
	public static long toAdStartDate(String startDate, String startTime) {
		if (startDate == null || startTime == null)
			return -1;
		try {
			SimpleDateFormat df = new SimpleDateFormat(YYYY_MMDD_HHMMSS);
			return df.parse(new StringBuilder(startDate).append(' ').append(startTime).toString()).getTime();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return -1;
	}

	/**
	 * 格式化广告的结束投放时间
	 * @param endDate
	 * @param endTime
	 * @return
	 */
	public static long toAdEndDate(String endDate, String endTime) {
		if (endDate == null || endTime == null)
			return -1;
		try {
			SimpleDateFormat df = new SimpleDateFormat(YYYY_MMDD_HHMMSS);
			return df.parse(new StringBuilder(endDate).append(' ').append(endTime).toString()).getTime();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return -1;
	}

	/**
	 * 得到查询通用的结束时间: 今天的0点
	 * 
	 * @return
	 */
	public static Date toQueryDefaultStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 得到查询通用的结束时间: 今天的24点
	 * 
	 * @return
	 */
	public static Date toQueryDefaultEndDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 格式化表名用的日期
	 * 
	 * @param dt
	 * @return
	 */
	public static String formatShortTableDate(Date dt) {
		if (dt == null)
			return "0000_00_00";
		// 不是线程安全的类
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");
		return df.format(dt);
	}

	/**
	 * 判定某个年份是否是闰年
	 * @param year
	 * @return
	 */
	private static boolean isLeapYear(int year) {
		return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0));
	}

	/**
	 * 计算两个日期的天数间隔
	 * 
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	public static int calDaysInterval(Date sDate, Date eDate) {
		// 时间间隔，初始为0
		int interval = 0;
		Calendar sCalendar = Calendar.getInstance();
		sCalendar.setTime(sDate);
		int sYears = sCalendar.get(Calendar.YEAR);
		int sDays = sCalendar.get(Calendar.DAY_OF_YEAR);

		Calendar eCalendar = Calendar.getInstance();
		eCalendar.setTime(eDate);
		int eYears = eCalendar.get(Calendar.YEAR);
		int eDays = eCalendar.get(Calendar.DAY_OF_YEAR);

		interval = 365 * (eYears - sYears);
		interval += (eDays - sDays);
		// 除去闰年天数
		while (sYears < eYears) {
			if (isLeapYear(sYears)) {
				--interval;
			}
			++sYears;
		}

		return interval;
	}

	/**
	 * 判断日期是否属于今天
	 * 
	 * @param sDate
	 * @return
	 */
	public static boolean isToday(Date sDate) {
		if (sDate == null)
			return false;

		Calendar sCalendar = Calendar.getInstance();
		sCalendar.setTime(sDate);
		int sDays = sCalendar.get(Calendar.DAY_OF_YEAR);

		Calendar nowCal = Calendar.getInstance();
		int nowDays = nowCal.get(Calendar.DAY_OF_YEAR);

		return sDays == nowDays ? true : false;
	}

	/**
	 * 格式化表名：如果不是今天的日期就加上后缀
	 * 
	 * @param tableName
	 * @param dayStr
	 * @return
	 */
	public static String formatDayTableName(String tableName, String dayStr) {
		return tableName + "_" + dayStr;
	}

	public static String formatDayTableName(String tableName, Date date) {
		return tableName + "_" + formatShortTableDate(date);
	}

	public static String formatToDayTableName(String tableName) {
		return tableName + "_" + formatShortTableDate(new Date());
	}

	public static String formatToDayTableName(String tableName, String day) {
		return tableName + "_" + day;
	}

	public static String formartDayWithShort(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
		String dateStr = df.format(date);
		return dateStr;
	}

	public static String formartDayWithShort2(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(YYYY_MMDD);
		String dateStr = df.format(date);
		return dateStr;
	}

	/**
	 * 处理日任务起止时间
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static Map<String, String> treatDate4DayStatistic(String begin, String end) {
		String preBegin = "";
		Date now = new Date();

		if (StringUtils.isBlank(begin) && StringUtils.isBlank(end)) {// 设置默认起始时间

			// 取前一天
			begin = DateFormatUtils.format(DateUtils.truncate(DateUtils.addDays(now, -1), Calendar.DATE), "yyyyMMdd");

			end = DateFormatUtils.format(DateUtils.truncate(now, Calendar.DATE), "yyyyMMdd");

			preBegin = DateFormatUtils
					.format(DateUtils.truncate(DateUtils.addDays(now, -1), Calendar.DATE), "yyyyMMdd");

		} else if (!StringUtils.isBlank(begin) && StringUtils.isBlank(end)) {// 设置默认结束时间
			end = DateFormatUtils.format(DateUtils.truncate(now, Calendar.DATE), "yyyyMMdd");

		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("begin", begin);
		map.put("end", end);
		map.put("preBegin", preBegin);
		return map;

	}

	/**
	 * 处理周任务起止时间
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static Map<String, String> treatDate4WeekStatistic(String begin, String end) {

		Date now = new Date();

		if (StringUtils.isBlank(begin) && StringUtils.isBlank(end)) {// 设置默认起始时间,做前一天的统计操作
			// 判断当前是否星期一,每周一凌晨统计上一周数据
			Calendar c = Calendar.getInstance();
			c.setTime(now);
			int w = c.get(Calendar.DAY_OF_WEEK) - 1;
			if (w != 1) {// 不是周一，则做本周一到当前的周数据

				begin = DateFormatUtils.format(DateUtils.truncate(DateUtils.addDays(now, -w + 1), Calendar.DATE),
						"yyyyMMdd");

				end = DateFormatUtils.format(now, "yyyyMMdd");

			} else {

				begin = DateFormatUtils.format(DateUtils.truncate(DateUtils.addDays(now, -7), Calendar.DATE),
						"yyyyMMdd");
				end = DateFormatUtils.format(DateUtils.truncate(DateUtils.addDays(now, -1), Calendar.DATE), "yyyyMMdd");
			}

		} else if (!StringUtils.isBlank(begin) && !StringUtils.isBlank(end)) {// 设置默认结束时间
			// 根据历史数据构造周报表，含[begin,end]内的第一个周一，到最后一个周日的日历史数据构造周统计数据
			try {
				Date date0 = DateUtils.parseDate(begin, "yyyyMMdd");
				Calendar c0 = Calendar.getInstance();
				c0.setTime(date0);
				int w = c0.get(Calendar.DAY_OF_WEEK) - 1;
				if (w == 0) {
					begin = DateFormatUtils.format(DateUtils.truncate(DateUtils.addDays(date0, 1), Calendar.DATE),
							"yyyyMMdd");// 取到下一周周一
				} else if (w > 1) {

					begin = DateFormatUtils.format(
							DateUtils.truncate(DateUtils.addDays(date0, 7 - w + 1), Calendar.DATE), "yyyyMMdd");// 取到下一周周一
				}

				Date date1 = DateUtils.parseDate(end, "yyyyMMdd");
				Calendar c1 = Calendar.getInstance();
				c1.setTime(date1);
				w = c1.get(Calendar.DAY_OF_WEEK) - 1;
				if (w != 0) {
					end = DateFormatUtils.format(DateUtils.truncate(DateUtils.addDays(date1, -w + 1), Calendar.DATE),
							"yyyyMMdd");// 取到上周周日
				}
				// 判断begin 和 end 是否合法
				Date bg = DateUtils.parseDate(begin, "yyyyMMdd");
				Date ed = DateUtils.parseDate(end, "yyyyMMdd");

				if (ed.getTime() > now.getTime()) {// 结束时间大于当前时间，取结束时间为上周日

					end = DateFormatUtils.format(DateUtils.truncate(DateUtils.addDays(date1, -w + 1), Calendar.DATE),
							"yyyyMMdd");// 取到上周周日
				}
				if (bg.getTime() > now.getTime() || bg.getTime() > ed.getTime()) {// 时间不合法

					return null;
				}
			} catch (Exception e) {
				return null;
			}

		} else if (!StringUtils.isBlank(begin) && StringUtils.isBlank(end)) {// 仅设置开始时间，结束时间默认为前一周的星期日

			try {
				Date date0 = DateUtils.parseDate(begin, "yyyyMMdd");
				Calendar c0 = Calendar.getInstance();
				c0.setTime(date0);
				int w = c0.get(Calendar.DAY_OF_WEEK) - 1;
				if (w != 1) {
					begin = DateFormatUtils.format(
							DateUtils.truncate(DateUtils.addDays(date0, 7 - w + 1), Calendar.DATE), "yyyyMMdd");// 取到下一周周一
				}

				Calendar c1 = Calendar.getInstance();
				c1.setTime(new Date());
				w = c1.get(Calendar.DAY_OF_WEEK) - 1;
				if (w != 0) {// 判断是否周日
					end = DateFormatUtils.format(DateUtils.truncate(DateUtils.addDays(now, -w), Calendar.DATE),
							"yyyyMMdd");// 取到上周周日
				} else {
					end = DateFormatUtils.format(DateUtils.truncate(now, Calendar.DATE), "yyyyMMdd");//
				}

			} catch (Exception e) {

				return null;
			}
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("begin", begin);
		map.put("end", end);
		return map;

	}

	/**
	 * 处理月任务起止时间
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static Map<String, String> treatDate4MonthStatistic(String begin, String end) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int w = cal.get(Calendar.DAY_OF_MONTH);

		Date firstdayOfLastMonth = DateUtils.addDays(DateUtils.addMonths(new Date(), -1), -w + 1);// 当前时间上一个月1号

		Date LastdayOfLastMonth = DateUtils.addDays(new Date(), -w);// 当前时间上一个月最后一天

		if (StringUtils.isBlank(begin) && StringUtils.isBlank(end)) {// 设置默认起始时间,做上一个月的统计操作
			// 判断当前是否是1号
			if (w != 1) {// 当前日不是1号
				return null;
			}

			begin = DateFormatUtils.format(DateUtils.truncate(firstdayOfLastMonth, Calendar.DATE), "yyyyMMdd");// 取上一个月1号

			end = DateFormatUtils.format(DateUtils.truncate(LastdayOfLastMonth, Calendar.DATE), "yyyyMMdd");

		} else if (!StringUtils.isBlank(begin) && !StringUtils.isBlank(end)) {// 设置默认结束时间
			// 根据历史数据构造月报表，含[begin,end]内的第一个月1号，到最后一个月末的日历史数据构造月统计数据
			try {
				Date date0 = DateUtils.parseDate(begin, "yyyyMMdd");
				cal.setTime(date0);
				w = cal.get(Calendar.DAY_OF_MONTH);
				if (w != 1) {// 开始时间非1号，从下月1号开始统计
					Date date = DateUtils.addMonths(date0, 1);
					begin = DateFormatUtils.format(DateUtils.truncate(DateUtils.addDays(date, -w + 1), Calendar.DATE),
							"yyyyMMdd");// 取到下月1号
				}

				// 判断begin 和 end 是否合法
				Date bg = DateUtils.parseDate(begin, "yyyyMMdd");
				Date ed = DateUtils.parseDate(end, "yyyyMMdd");

				Date current = new Date();
				if (ed.getTime() > current.getTime()) {// 结束时间大于当前时间，取上月最后一天

					end = DateFormatUtils.format(DateUtils.truncate(LastdayOfLastMonth, Calendar.DATE), "yyyyMMdd");// 取上月最后一天

				}
				if (bg.getTime() > current.getTime() || bg.getTime() > ed.getTime()) {// 时间不合法

					return null;
				}

			} catch (Exception e) {
				return null;
			}

		} else if (!StringUtils.isBlank(begin) && StringUtils.isBlank(end)) {// 仅设置开始时间，结束时间默认为上月末

			try {

				Date date0 = DateUtils.parseDate(begin, "yyyyMMdd");

				cal.setTime(date0);
				w = cal.get(Calendar.DAY_OF_MONTH);
				if (w != 1) {// 开始时间非1号，从下个月1号开始统计
					Date date = DateUtils.addMonths(date0, 1);

					begin = DateFormatUtils.format(DateUtils.truncate(DateUtils.addDays(date, -w + 1), Calendar.DATE),
							"yyyyMMdd");// 取到开始时间的下个月1号
				}

				end = DateFormatUtils.format(DateUtils.truncate(LastdayOfLastMonth, Calendar.DATE), "yyyyMMdd");// 取当前时间上月最后一天

			} catch (Exception e) {

				return null;
			}
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("begin", begin);
		map.put("end", end);
		return map;
	}

	/**
	 * get last day of month
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastdayOfmonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		// 设置时间,当前时间不用设置
		calendar.setTime(date);
		// 设置日期为本月最大日期
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

		return calendar.getTime();

	}

}
