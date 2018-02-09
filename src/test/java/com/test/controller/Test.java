package com.test.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test {

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getTime());
		
		cal.add(Calendar.YEAR, 1); // 날짜 계산: 1년 더하기
		cal.add(Calendar.MONTH, -2); // 2개월 전으로 월 빼기
		cal.add(Calendar.DATE, 3); // 3일 후로 날짜 더하기
		System.out.println(cal.getTime());
		
		System.out.println(cal.get(Calendar.MONTH)+1); // cal의 월 구하기, 1을 더해야함
		
		// 원하는 날짜 포맷으로 나타내기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(sdf.format(cal.getTime()));
	}
}
