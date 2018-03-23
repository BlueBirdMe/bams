package com.pinhuba.common.util;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.security.SecureRandom;

import org.apache.commons.lang.StringUtils;

/**
 * 本类是字符串处理的工具类，封装项目中所有与字符串处理相关的操作.
 * 
 * <p>
 * <b>Company：</b>ShangHai SoftWare CO.,LTD.
 * </p>
 * <p>
 * <b>Author：</b>
 * </p>
 * <p>
 * <b>Date：</b>
 * </p>
 * 
 * @see StringToolTest
 * @see StringToolTests
 */

public class StringTool {
	public static final String[] zodiacArr = { "猴", "鸡", "狗", "猪", "鼠", "牛",
			"虎", "兔", "龙", "蛇", "马", "羊" };

	public static final String[] constellationArr = { "水瓶座", "双鱼座", "白羊座",
			"金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座" };

	public static final int[] constellationEdgeDay = { 20, 19, 21, 21, 21, 22,
			23, 23, 23, 23, 22, 22 };

	/**
	 * 根据日期获取生肖
	 * 
	 * @return
	 */
	public static String getZodica(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return zodiacArr[cal.get(Calendar.YEAR) % 12];
	}

	/**
	 * 根据日期获取星座
	 * 
	 * @param time
	 * @return
	 */
	public static String getConstellation(Date date) {
		if (date == null) {
			return "";
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		if (day < constellationEdgeDay[month]) {
			month = month - 1;
		}
		if (month >= 0) {
			return constellationArr[month];
		}
		// default to return 魔羯
		return constellationArr[11];
	}

	/**
	 * 检查指定的字符串是否全部由数字组成
	 * 
	 * @param meta
	 *            待检测的字符串
	 * @return true：此字符串全部由数字组成；false：反之
	 */
	public static boolean isDigitAll(String meta) {
		if (meta == null) {
			return false;
		}

		// 使用正则表达式检查字符串内字符是否在'0'到'9'（包括边界）之间。
		// 在，返回true；不在，返回false。
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(meta);
		boolean result = m.matches();

		return result;
	}

	public static String getStringFromTime(Date date) {
		return getStringFromDate(date, "yyyy-MM-dd HH:mm:SS");
	}

	/**
	 * 检查指定的字符串是否全部由字母组成
	 * 
	 * @param meta
	 *            待检测的字符串
	 * @return true：此字符全部由字母组成；false：反之
	 */
	public static boolean isLetterAll(String meta) {
		if (meta == null) {
			return false;
		}
		// 使用正则表达示检查字符串内字符是否在'a'到'z'（包括边界）之间或在'A'到'Z'（包括边界）之间。
		// 在，返回true；不在，返回false。
		Pattern p = Pattern.compile("[a-zA-Z]+");
		Matcher m = p.matcher(meta);
		boolean result = m.matches();

		return result;
	}

	/**
	 * 检查指定的字符串是否全部由字母或数字组成
	 * 
	 * @param meta
	 *            待检测的字符串
	 * @return true：此字符串全部由字母或数字组成；false：反之
	 */
	public static boolean isDigitAndLetterAll(String meta) {
		if (meta == null) {
			return false;
		}
		// 使用正则表达式检查字符串内字符是否在'a'到'z'（包括边界）之间
		// 或在'A'到'Z'（包括边界）之间或在'0'到'9'（包括边界）之间。
		// 在，返回true；不在，返回false。
		Pattern p = Pattern.compile("[0-9a-zA-Z]+");
		Matcher m = p.matcher(meta);
		boolean result = m.matches();

		return result;
	}

	/**
	 * 检查指定的字符串是否由数字或数字和逗号组成，由数字和逗号组成时，逗号不允许在字符串两边且不能连续
	 * 
	 * @param meta
	 *            待检测的字符串
	 * @return true：此字符串由数字和逗号组成，并且逗号不在字符串两边且不连续；false：反之
	 */
	public static boolean isCommaAndDigit(String meta) {
		if (meta == null) {
			return false;
		}

		// 使用正则表达示检查字符串内字符是否在'0'到'9'（包括边界）之间或为','，
		// 以','隔开，不能以','为开头和结尾，也不能有连续的','。
		// 满足条件，返回true；不满足，返回false。
		Pattern p = Pattern.compile("(\\d+,?)*\\d");
		Matcher m = p.matcher(meta);
		boolean result = m.matches();

		return result;
	}

	/**
	 * 检查指定的字符串是否为正确的Email格式
	 * 
	 * @param meta
	 *            待检测的字符串
	 * @return true：此字符串为正确的Email格式；false：反之
	 */
	public static boolean isEmailFormat(String meta) {
		if (meta == null) {
			return false;
		}

		// 使用正则表达示判断字符串是否为Email格式
		Pattern p = Pattern.compile("[^@\\.]+@\\w+\\.[a-zA-Z]+(\\.[a-zA-Z]+)*");
		Matcher m = p.matcher(meta);
		boolean result = m.matches();

		return result;
	}

	/**
	 * 将double形数值转换为大写人民币格式的字符串
	 * 
	 * @param d
	 *            需要被转换为人民币大写格式的double形数值
	 * @return 非空字符串：被转换为人民币格式的字符串；null：整数部分长度超过12位时
	 */
	public static String toUpperRMB(double d) {
		if (d == 0) {
			return "零元整";
		}

		// 表示符号,用以区别正负
		String strSign = "";
		if (d < 0) {
			strSign = "负";
			d = -d;
		}

		// 格式化参数
		DecimalFormat df = new DecimalFormat("########0.00");
		String str = df.format(d);

		// 按小数点将格式化后的字符串拆分
		String[] strLr = str.split("\\.");

		// 对小数点左边的字符串进行转换
		String strLeft = leftToUpper(strLr[0]);

		if (strLeft == null) {
			return null;
		}

		// 对小数点右边的字符串进行转换
		String strRight = rightToUpper(strLr[1]);

		String result = strSign + strLeft + strRight;

		return result;
	}

	/**
	 * 将所给字符串转换为大写人民币格式的字符串
	 * 
	 * @param meta
	 *            需要转换的字符串
	 * @return 非空字符串：被转换为人民币格式的字符串；null：发生异常
	 */
	public static String toUpperRMB(String meta) {
		if (meta == null) {
			return null;
		}

		// 判断是否能转换为double形
		double d = 0;
		try {
			d = Double.parseDouble(meta);
		} catch (Exception e) {
			return null;
		}

		String result = toUpperRMB(d);

		return result;
	}

	/**
	 * 将日期对象转换为字符串,格式为"yyyy-MM-dd"
	 * 
	 * @param date
	 *            待转换的日期对象
	 * @return 非空字符串：被转换后的字符串；null：出现异常
	 */
	public static String getStringFromDate(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String result = sdf.format(date);

		return result;
	}

	/**
	 * 按指定的格式将日期对象转换为字符串
	 * 
	 * @param date
	 *            待转换的日期对象
	 * @param pattern
	 *            指定的格式
	 *            <p>
	 *            <b>常用格式为:</b>
	 *            </p>
	 *            <p>
	 *            年:yyyy; 月:MM; 日:dd; 时:HH; 分:mm; 秒:SS
	 *            </p>
	 * @return 非空字符串：被转换后的字符串；null：出现异常
	 */
	public static String getStringFromDate(Date date, String pattern) {
		if (date == null || pattern == null) {
			return null;
		}

		SimpleDateFormat sdf = null;
		sdf = new SimpleDateFormat(pattern);
		String result = sdf.format(date);

		return result;
	}

	/**
	 * 产生一个指定位数的数字随机数字符串
	 * 
	 * @param num
	 *            随机数的位数
	 * @return 一个4位的数字随机数字符串
	 */
	public static String getRandom(int num) {
		if (num <= 0) {
			return null;
		}

		StringBuffer random = new StringBuffer();
		SecureRandom secrand = new SecureRandom();
		int i = 0;
		while (i <= num) {
			long lTemp = Math.abs(secrand.nextLong());
			i = i + String.valueOf(lTemp).length();
			random.append(lTemp);
		}
		String result = random.substring(0, num);

		return result;
	}

	/**
	 * 将数值字符转换为汉字数字大写
	 * 
	 * @param c:需要被转换的数值（字符型）
	 * @return 返回转换后的汉字数字大写
	 */
	private static char toUpperCnChar(char c) {
		int iCur = Character.digit(c, 10);
		char[] upperNums = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };

		return upperNums[iCur];
	}

	/**
	 * 将数字字符串转换为元形式的人民币大写格式字符串
	 * 
	 * @param str:转换为字符串后小数点左边的数值
	 * @return 非空字符串：被转换成人民币元大写格式的字符串；无内容字符串：参数为"0"；null：出现异常
	 */
	private static String leftToUpper(String str) {
		if (str == null) {
			return null;
		}

		if (str.length() > 12) {
			return null;
		}

		// 当字符串内容为"0"时返回空字符串
		if (str.equals("0")) {
			return "";
		}

		String[] signA = { "仟", "佰", "拾", "" };
		String[] signB = { "亿", "万", "" };

		// 将参数字符串按四位一组拆成字符串数组
		String[] strArray = splitString(str, 4);

		// 用于存放转换结果的StringBuffer对象
		StringBuffer result = new StringBuffer();

		// 使用二层循环定位参数字符串的各字符的位置
		for (int i = 0; i < strArray.length; i++) {
			// 存放字符串中'0'字符的个数
			int zeroCount = 0;
			for (int j = 0; j < strArray[i].length(); j++) {
				if (strArray[i].charAt(j) == '0') {
					zeroCount++;
				} else {
					if (zeroCount > 0) {
						zeroCount = 0;
						result.append("零");
					}
					char upperChar = ' ';
					upperChar = toUpperCnChar(strArray[i].charAt(j));
					result.append(upperChar);
					result
							.append(signA[signA.length - strArray[i].length()
									+ j]);
				}
			}
			if (zeroCount < 4) {
				result.append(signB[signB.length - strArray.length + i]);
			}
		}
		result.append("元");

		return result.toString();
	}

	/**
	 * 将数字字符串转换为角分形式的人民币大写格式字符串
	 * 
	 * @param str
	 *            转换为字符串后小数点右边的数值
	 * @return 非空字符串：被转换成人民币角分大写格式的字符串；null：出现异常
	 */
	private static String rightToUpper(String str) {
		if (str == null) {
			return null;
		}

		// 当字符串内容为"00"时返回"整"
		if (str.equals("00")) {
			return "整";
		}

		// 用于存放转换结果的StringBuffer对象
		StringBuffer result = new StringBuffer();
		String[] sign = { "角", "分" };
		// 遍历参数字符串，定位小数部分
		for (int i = 0; i < str.length(); i++) {
			// 若当前字符不为'0'，则将当前字符及与其匹配的单位字符附加至结果中
			if (str.charAt(i) != '0') {
				char upperChar = ' ';
				upperChar = toUpperCnChar(str.charAt(i));
				result.append(upperChar);
				result.append(sign[sign.length - str.length() + i]);
			}
		}

		return result.toString();
	}

	/**
	 * 将字符串按照一定的长度进行拆分
	 * 
	 * @param meta
	 *            待拆分的字符串
	 * @param num
	 *            子字符串的最大长度
	 * @return String[]：拆分后的字符串数组；null：所给参数字符串为null或num为0
	 */
	private static String[] splitString(String meta, int num) {
		if (meta == null || num == 0) {
			return null;
		}

		// 计算待返回的字符串数组的长度
		int splitLength = (meta.length() + num - 1) / num;

		// 表示待返回的字符串数组
		String[] result = new String[splitLength];

		for (int i = splitLength - 1; i >= 0; i--) {
			// 计算拆分的起始点
			int start = meta.length() - num;
			if (start < 0) {
				start = 0;
			}

			result[i] = meta.substring(start);

			// 剪掉已被拆分的部分
			meta = meta.substring(0, start);
		}

		return result;
	}

	public static String spiltByLine(String objectName) {
		String tmp = "";
		for (int i = 0; i < objectName.length(); i++) {
			char c = objectName.charAt(i);
			if (i > 0 && c >= 'A' && c <= 'Z') {
				tmp += "_";
			}
			tmp += c;
		}
		return tmp;
	}

	public static boolean getFlag() {
		Date d = new Date();
		if (d.after(DateTimeTool.getDateFromString("2008-08-08"))) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String getLeftString(String str,int len){
		if(str==null||str.length()==0||str.length()<=len){
			return str;
		}else{
			return str.substring(0,str.length()-len);			
		}
	}
	
	public static boolean isChinese(String strName) {
		boolean flag = false;
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
//	CJK_UNIFIED_IDEOGRAPHS 中日韩统一表意文字
//	CJK_COMPATIBILITY_IDEOGRAPHS 中日韩兼容字符
//	CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A 中日韩统一表意文字扩充A
//	GENERAL_PUNCTUATION  一般标点符号, 判断中文的“号 
//	CJK_SYMBOLS_AND_PUNCTUATION 符号和标点, 判断中文的。号
//	HALFWIDTH_AND_FULLWIDTH_FORMS 半角及全角字符, 判断中文的，号
	
    public static boolean isChinese(char c) {  
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
            return true;  
        }  
        return false;  
    }
    
    /**
	 * 剪切文本。如果进行了剪切，则在文本后加上"..."
	 * 
	 * @param s
	 *            剪切对象。
	 * @param len
	 *            编码小于256的作为一个字符，大于256的作为两个字符。
	 * @return
	 */
	public static String textCut(String s, int len, String append) {
		if (s == null) {
			return null;
		}
		int slen = s.length();
		if (slen <= len) {
			return s;
		}
		// 最大计数（如果全是英文）
		int maxCount = len * 2;
		int count = 0;
		int i = 0;
		for (; count < maxCount && i < slen; i++) {
			if (s.codePointAt(i) < 256) {
				count++;
			} else {
				count += 2;
			}
		}
		if (i < slen) {
			if (count > maxCount) {
				i--;
			}
			if (!StringUtils.isBlank(append)) {
				if (s.codePointAt(i - 1) < 256) {
					i -= 2;
				} else {
					i--;
				}
				return s.substring(0, i) + append;
			} else {
				return s.substring(0, i);
			}
		} else {
			return s;
		}
	}
	
	/**
	 * 通过URL获取页面名称
	 * @param url
	 * @return
	 */
	public static String getPageName(String url){
		return url.substring(url.lastIndexOf("/")+1,url.length());
	}
}
