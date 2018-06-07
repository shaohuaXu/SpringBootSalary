package com.iqb.salary.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: CalculateUtil 
 * @Description: 计算工具类
 * @author: liujun
 * @date: 2016年8月11日 上午9:09:02
 */
public final class CalculateUtil {

	/**
	 * 计算相加结果(按照a、b最大精度)
	 * 
	 * @param one
	 *            数字1
	 * @param two
	 *            数字2
	 * @return
	 */
	public static BigDecimal add(BigDecimal one, BigDecimal two) {
		return one.add(two);
	}
	
	/**
	 *  多数值 计算相加结果
	 * @param objs
	 * @return
	 */
	public static BigDecimal add(BigDecimal... objs) {
		BigDecimal result = BigDecimal.ZERO;
		for(BigDecimal obj:objs){
			result = result.add(obj);
		}
		return result;
	}

	/**
	 * 两数字相加，结果取四舍五入精度
	 * 
	 * @param one
	 *            数字1
	 * @param two
	 *            数字2
	 * @param scale
	 *            精度
	 * @return
	 */
	public static BigDecimal addForRoundHalfUp(BigDecimal one, BigDecimal two, int scale) {
		return setScaleForRoundHalfUp(add(one, two), scale);
	}

	/**
	 * 计算相减结果(按照a、b最大精度)
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal one, BigDecimal two) {
		return one.subtract(two);
	}

	/**
	 * 两数字相减，结果取四舍五入精度
	 * 
	 * @param one
	 *            数字1
	 * @param two
	 *            数字2
	 * @param scale
	 *            精度
	 * @return
	 */
	public static BigDecimal subtractForRoundHalfUp(BigDecimal one, BigDecimal two, int scale) {
		return setScaleForRoundHalfUp(subtract(one, two), scale);
	}

	/**
	 * 计算相乘结果(按照a、b最大精度)
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @return
	 */
	public static BigDecimal zmultiply(BigDecimal one, BigDecimal two) {
		return one.multiply(two);
	}
	
	/**
	 *  多数值 计算相乘结果
	 * @param objs
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal... objs) {
		BigDecimal result = BigDecimal.ONE;
		for(BigDecimal obj:objs){
			result = result.multiply(obj);
		}
		return result;
	}

	/**
	 * 计算相乘结果(按照a、b最大精度)
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @return
	 */
	public static BigDecimal multiplyForRoundHalfUp(BigDecimal one, BigDecimal two, int scale) {
		return setScaleForRoundHalfUp(multiply(one, two), scale);
	}

	/**
	 * 计算相乘结果(按照a、b最大精度)
	 * @param one 比较数字1
	 * @param two 比较数字2
	 * @param scale 精度
	 * @return
	 */
	public static BigDecimal multiplyForRoundUp(BigDecimal one, BigDecimal two, int scale) {
		return setScaleForRoundUp(multiply(one, two), scale);
	}

	/**
	 * 向下取整
	 * @param one
	 * @param two
	 * @param scale
	 * @return
	 */
	public static BigDecimal multiplyForRoundDown(BigDecimal one, BigDecimal two, int scale) {
		return setDownHScale(multiply(one, two), scale);
	}

	/**
	 * 计算相除结果,默认精确32位（超过部分使用银行家算法舍入）
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @return
	 */
	public static BigDecimal division(BigDecimal one, BigDecimal two) {
		BigDecimal result = division(one, two, 32);
		return result;
	}

	/**
	 * 计算相除结果(按照指定精度参数)
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @param scale
	 *            精度，超过部分使用银行家算法舍入
	 * @return
	 */
	public static BigDecimal division(BigDecimal one, BigDecimal two, int scale) {
		return one.divide(two, scale, RoundingMode.HALF_EVEN);
	}

	/**
	 * 除法取整
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @return
	 */
	public static int divisionFloor(BigDecimal one, BigDecimal two) {
		BigDecimal result = division(one, two);
		result.setScale(0, RoundingMode.DOWN);
		return result.intValue();
	}

	/**
	 * 判断a是否大于b a > b 返回 TRUE a < b 返回 FALSE a = b 返回 FALSE
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @return
	 */
	public static Boolean isLargeThan(BigDecimal one, BigDecimal two) {
		if (one == null || two == null) {
			return null;
		}
		if (one.compareTo(two) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断a是否大于等于b<br>
	 * a > b 返回 TRUE<br>
	 * a = b 返回 TRUE<br>
	 * a < b 返回 FALSE<br>
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @return
	 */
	public static Boolean isLargeOrEqualThan(BigDecimal one, BigDecimal two) {
		if (one == null || two == null) {
			return null;
		}
		int result = one.compareTo(two);
		if (result > 0) {
			return true;
		} else if (result == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断a是否小于b a < b 返回 TRUE a > b 返回 FALSE a = b 返回 FALSE
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @return
	 */
	public static Boolean isLessThan(BigDecimal one, BigDecimal two) {
		if (one == null || two == null) {
			return null;
		}
		if (one.compareTo(two) < 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断a是否小于等于b<br>
	 * a < b 返回 TRUE <br>
	 * a = b 返回 TRUE a > b 返回 FALSE <br>
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @return
	 */
	public static Boolean isLessOrEqualThan(BigDecimal one, BigDecimal two) {
		if (one == null || two == null) {
			return null;
		}
		int result = one.compareTo(two);
		if (result < 0) {
			return true;
		} else if (result == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断a是否约等于b，精度取到小数点后三位 a = b 返回 TRUE a > b 返回 FALSE a < b 返回 FALSE
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @param scale
	 *            精度，超过部分直接截取
	 * @param errorRate
	 *            允许误差范围
	 * @return
	 */
	public static Boolean isApproximatelyEquals(BigDecimal one, BigDecimal two, int scale, BigDecimal errorRate) {
		if (one == null || two == null) {
			return null;
		}
		BigDecimal min = CalculateUtil.add(one, errorRate);
		BigDecimal max = CalculateUtil.subtract(one, errorRate);
		min = min.setScale(scale, RoundingMode.DOWN);
		max = max.setScale(scale, RoundingMode.DOWN);
		two = two.setScale(scale, RoundingMode.DOWN);
		if ((min.compareTo(two) == 0) || (max.compareTo(two) == 0) || (min.compareTo(two) == 1 && max.compareTo(two) == -1)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 比较两个债权百分比是否相同(直接截取保留16位精度判断，内误差值为零)
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @param scale
	 * @return
	 */
	public static Boolean isApproximatelyEqualsForRate(BigDecimal one, BigDecimal two) {
		return isApproximatelyEquals(one, two, 16, BigDecimal.ZERO);
	}

	/**
	 * 比较两个金额是否相同(直接截取保留6位精度判断，内误差值为零)
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @return
	 */
	public static Boolean isApproximatelyEqualsForAmount(BigDecimal one, BigDecimal two) {
		return isApproximatelyEquals(one, two, 6, BigDecimal.ZERO);
	}
	
	/**
	 * 比较两个值是否相等
	 * @param one
	 * @param two
	 * @return
	 */
	public static Boolean isEquals(BigDecimal one, BigDecimal two){
		return one.compareTo(two)==0;
	}

	/**
	 * 返回最小的数值
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @return
	 */
	public static BigDecimal min(BigDecimal one, BigDecimal two) {
		if (isLargeThan(one, two)) {
			return two;
		} else {
			return one;
		}
	}

	/**
	 * 返回最大的数值
	 * 
	 * @param one
	 *            比较数字1
	 * @param two
	 *            比较数字2
	 * @return
	 */
	public static BigDecimal max(BigDecimal one, BigDecimal two) {
		if (isLargeThan(one, two)) {
			return one;
		} else {
			return two;
		}
	}
	/**
	 * 银行家取舍
	 * @param input
	 * @param scale
	 * @return
	 */
	public static BigDecimal setScale(BigDecimal input,int scale){
		return input.setScale(scale, RoundingMode.HALF_EVEN);
	}
	
	/**
	 * @methodName:setScale     
	 * @describe:不做四舍五入 
	 * @parm:
	 * @author:liujun
	 * @date:2016年4月20日上午10:07:03
	 */
	public static BigDecimal setDownHScale(BigDecimal input,int scale){
		return input.setScale(scale, RoundingMode.DOWN);
	}






	public static BigDecimal setUpHScale(BigDecimal input,int scale){
		return input.setScale(scale, RoundingMode.HALF_UP);
	}




	public static BigDecimal setUp4DownHScale(BigDecimal input,int scale){
		return input.setScale(scale, RoundingMode.HALF_UP);
	}
	
	/**
	 * 设置债权持有比率精度（直接截取保留16位精度），新交易系统设计的表持有比率最小保留小数点后16位
	 * 
	 * @param input
	 *            待转换对象
	 * @return
	 */
	public static BigDecimal setScaleForBizRate(BigDecimal input) {
		return input.setScale(16, RoundingMode.DOWN);
	}

	/**
	 * 设置金额精度（直接截取保留6位精度），用于保存新交易系统的表，新交易系统设计的表金额最小保留小数点后6位
	 * 
	 * @param input
	 *            待转换对象
	 * @return
	 */
	public static BigDecimal setScaleForBizAmount(BigDecimal input) {
		return input.setScale(6, RoundingMode.DOWN);
	}

	/**
	 * 根据设置的精度，四舍五入转换精度
	 * 
	 * @param input
	 *            待转换对象
	 * @param scale
	 *            保留精度
	 * @return
	 */
	public static BigDecimal setScaleForRoundHalfUp(BigDecimal input, int scale) {
		return new BigDecimal(format(input, scale));
	}



	/**
	 * 根据设置的精度，四舍五入转换精度
	 *
	 * @param input
	 *            待转换对象
	 * @param scale
	 *            保留精度
	 * @return
	 */
	public static BigDecimal setScaleForRoundUp(BigDecimal input, int scale){
		return new BigDecimal(roundUpformat(input,scale));
	}

	/**
	 * 根据精度 向上获取精度
	 * @param input
	 *            待转换对象
	 * @param scale
	 *            精度
	 * @return
	 */
	public static String roundUpformat(BigDecimal input, int scale) {
		return input.setScale(scale, BigDecimal.ROUND_UP).toString();
	}

	/**
	 * 根据设置的精度，四舍五入转换精度,一般用于用户交互显示，一般建议金额保留小数后面2位
	 * 
	 * @param input
	 *            待转换对象
	 * @param scale
	 *            精度
	 * @return
	 */
	public static String format(BigDecimal input, int scale) {
		return input.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 判断是否小于0
	 * 
	 * @param input
	 * @return
	 */
	public static Boolean isLessThanZero(BigDecimal input) {
		return isLessThan(input, BigDecimal.ZERO);
	}

	/**
	 * 判断是否大于0
	 * 
	 * @param input
	 * @return
	 */
	public static Boolean isLargeThanZero(BigDecimal input) {
		return isLargeThan(input, BigDecimal.ZERO);
	}
	
	/**
	 * @methodName:isNumeric     
	 * @describe:验证是否是数字
	 * @author:liujun
	 * @date:2016年4月27日上午11:30:17
	 */
	public static boolean isNumeric(String str){ 
	   Pattern pattern = Pattern.compile("[0-9]*"); 
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
	}
	
	/**
	 * @methodName:compareTo     
	 * @describe:比较数字大小
	 * @author:liujun
	 * null无法进行比较   1 大于 -1 小于 0 等于
	 * @date:2016年5月23日上午11:08:11
	 */
	public static Integer compareTo(String one,String two){
		if(StringUtils.isEmpty(one)|| StringUtils.isEmpty(two)){
			return null;
		}
		BigDecimal oneb = BigDecimal.valueOf(Double.valueOf(one));
		BigDecimal twob = BigDecimal.valueOf(Double.valueOf(two));
		return oneb.compareTo(twob);
	}
	
	/**
	 * @Title: compareTo 
	 * @Description: 两数据比大小
	 * @param one
	 * @param two
	 * @return: Integer
	 */
	public static Integer compareTo(BigDecimal one,BigDecimal two){
		return one.compareTo(two);
	}
	
	/**
	 * @methodName:judgeNumber
	 * @describe:返回整数小数
	 * @author:liujun
	 * @date:2016年5月23日下午4:15:59
	 */
	public static boolean judgeisDecimals(String input) {
		Pattern integerPattern = Pattern.compile("^\\d+$|-\\d+$"); // 就是判断是否为整数
		Matcher integerNum = integerPattern.matcher(input);
		if(integerNum.matches()){
			return true;
		}
		Pattern decimalPattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");//判断是否为小数
		Matcher decimalNum = decimalPattern.matcher(input);
		if(decimalNum.matches()){
			return false;
		}
		return false;
	}
	
	
	/**
	 * @Title: getMonthByDay 
	 * @Description: 天数转换 计算月数
	 * 例子 :  (Math.floor(daynum/30))+(daynum%30)/30 
	 * @param daynum
	 * @author: liujun 
	 * @date: 2016年8月26日 下午2:59:18
	 * @return: Double
	 */
	public static BigDecimal getMonthByDay(int daynum){
		BigDecimal threeDay = new BigDecimal("30");
		int sortMonth = daynum % 30 ;
		BigDecimal month = CalculateUtil.division(new BigDecimal(sortMonth),threeDay,2);
		int intMonth = daynum/30;
		return CalculateUtil.addForRoundHalfUp(new BigDecimal(intMonth),month,1);
	}

	/**
	 * 取得大写形式的字符串
	 *
	 * @return
	 */
	public static String getCnString(String original) {
		String[] digit =    {"零","壹","貳","叁","肆","伍","陆","柒","扒","玖"};
		String[] unit =     {"整","圆","拾","百","仟","万","拾","百","仟","亿",
				"拾","百","仟","万"};
		// 整数部分
		String integerPart = "";
		// 小数部分
		String floatPart = "";

		if (original.contains(".")) {
			// 如果包含小数点
			int dotIndex = original.indexOf(".");
			integerPart = original.substring(0, dotIndex);
			floatPart = original.substring(dotIndex + 1);
			if("00".equals(floatPart))
				floatPart = "";
		} else {
			// 不包含小数点
			integerPart = original;
		}
		// 因为是累加所以用StringBuffer
		StringBuffer sb = new StringBuffer();
		//整数部分
		char[] chArr = integerPart.toCharArray();
		for(int i = 0;i < chArr.length;i++){
			String s = digit[Integer.parseInt(String.valueOf(chArr[i]))];
			String s2 = unit[chArr.length-i];
			sb.append(s).append(s2);
		}
		if("".equals(floatPart))
			sb.append(unit[0]);
		// 小数部分处理
		if (floatPart.length() > 0) {
			sb.append("点");
			for (int i = 0; i < floatPart.length(); i++) {
				int number = getNumber(floatPart.charAt(i));
				sb.append(digit[number]);
			}
		}
		String str = sb.toString();
		str = change(str);
		return str;
	}

	/**
	 * 将字符形式的数字转化为整形数字
	 * 因为所有实例都要用到所以用静态修饰
	 *
	 * @param c
	 * @return
	 */
	private static int getNumber(char c) {
		String str = String.valueOf(c);
		return Integer.parseInt(str);
	}

	private static String change(String str) {
		String s = str.replaceAll("零[仟百拾]", "零");
		s = s.replaceAll("零+", "零").replaceAll("零亿", "亿").replaceAll("零万", "万");
		s = s.replaceAll("零圆", "圆").replace("亿万", "亿");
		return s;
	}


	/**
	 *
	 * @return
	 */
	public static BigDecimal setPHPScaleFloat(BigDecimal input,int upScale,int downScale){

		if( upScale <= downScale){
			throw new RuntimeException("upScale must be than downScale ");
		}
		BigDecimal upScALE = CalculateUtil.setUpHScale(input, upScale);

		input = CalculateUtil.setDownHScale(upScALE,downScale);
		return input;
	}


}
