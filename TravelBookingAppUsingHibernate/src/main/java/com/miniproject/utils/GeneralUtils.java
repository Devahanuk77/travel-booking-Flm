package com.miniproject.utils;

public class GeneralUtils {
	
	private static String travelDate;
	private static int numberOfPassengers;

	private static boolean loginInStatus = false;
	private static double initialPrice;
	private static double weekdayPrice;
	private static double weekendPrice;
	private static double amount;
	public static String getTravelDate() {
		return travelDate;
	}

	public static void setTravelDate(String travelDate) {
		GeneralUtils.travelDate = travelDate;
	}

	public static int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public static void setNumberOfPassengers(int numberOfPassengers) {
		GeneralUtils.numberOfPassengers = numberOfPassengers;
	}

	public static boolean isLoginInStatus() {
		return loginInStatus;
	}

	public static void setLoginInStatus(boolean loginInStatus) {
		GeneralUtils.loginInStatus = loginInStatus;
	}

	public static double getInitialPrice() {
		return initialPrice;
	}

	public static void setInitialPrice(double initialPrice) {
		GeneralUtils.initialPrice = initialPrice;
	}

	public static double getWeekdayPrice() {
		return weekdayPrice;
	}

	public static void setWeekdayPrice(double weekdayPrice) {
		GeneralUtils.weekdayPrice = weekdayPrice;
	}

	public static double getWeekendPrice() {
		return weekendPrice;
	}

	public static void setWeekendPrice(double weekendPrice) {
		GeneralUtils.weekendPrice = weekendPrice;
	}

	public static double getAmount() {
		return amount;
	}

	public static void setAmount(double amount) {
		GeneralUtils.amount = amount;
	}


}
