package com.miniproject.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.miniproject.entity.LoginUser;
import com.miniproject.entity.TravelDetails;
import com.miniproject.entity.User;
import com.miniproject.utils.Constants;
import com.miniproject.utils.GeneralUtils;
import com.miniproject.utils.HibernateUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class TravelFunctions {

	static Scanner input = new Scanner(System.in);
	String charCombination =null;
	String validationVariable =null;
	static int option;
	static int failedCount;
	String finalDay = null;

	TravelDetails  travelDetails = new TravelDetails();
	LoginUser loginUser = new LoginUser();
	User userObject = new User();
	
	public static  TravelFunctions travelObject;
	
	private TravelFunctions() {
	}

	public static TravelFunctions getInstanceUsingSingleton() {
		if (travelObject == null) {
			travelObject = new TravelFunctions();
		}
		return travelObject;
	}
	
	private static FileInputStream fileInput = null;
	
	
	public static void printLogoUsingFile(String filePath) throws IOException {

		File fileObject = new File(filePath);
		try {
			
			fileInput = new FileInputStream(fileObject);
			int tempVariable;
			while ((tempVariable = fileInput.read()) != Constants.FILEREAD) {
				System.out.print((char) tempVariable);
			}
		} catch (Exception exception) {
			fileNotFoundCustomException();
		}
	}
	
	public void menuOptions() {
		printMessage("\n Welcome to Menu page... Please select options : \n");
		printMessage("Option-1 : User login ");
		printMessage("Option-2 : New User registration ");
		printMessage("Option-3 : Plan Journey ");
		printMessage("Option-4 : Edit travel date ");
		printMessage("Option-5 : User Exit ");
		printMessage("Please select option 1 ");
		option = optionVariable();
		if(option == Constants.OPTION) {
			printMessage("Valid option selection !");
			menuOptionSwitchCase();
		}else {
			printMessage("User must login first then you will go for remaining options : \n");
			exitUser();
		}
		
	}

	private void menuOptionSwitchCase() {
		printMessage("\n Please select one option : \n");
		option = optionVariable();
		switch (option) {
			case 1:
				userLogin();
				break;
			case 2:
				userRegistration();
				break;
			case 3:
				planJourney();
				break;
			case 4:
				printMessage("\n Edit travel date  page : \n");
				break;
			case 5:
				exitUser();
				break;
			default:
				printMessage("Invalid Option selection ");
				break;
		}
	}
	
	public void userLogin() {
		try {
			printMessage("\n Welcome to User login page : \n");
			printMessage(" Please enter user EmailId : ");
			String userId = input.next();
			printMessage("please enter user password : ");
			String password = input.next();
			SessionFactory sessionFactory = HibernateUtils.buildSessionFactoryObject();
			if(sessionFactory != null) {
				Session session = sessionFactory.openSession();
				Long count = getLoginUserObjectFromDb(userId, password, session);
				if(count > Constants.COUNT) {
					printMessage(" Valid user ");
					GeneralUtils.setLoginInStatus(Constants.TRUE_CONDITION);
					printMessage(" User login successfully...");
					printMessage("\n Please select either option 1 or 2 for further process !\n ");
					userLoginSwitchCase();
				}
				else {
					printMessage("No user found with given credentials ... \n");
					GeneralUtils.setLoginInStatus(Constants.FALSE_CONDITION);
					failedCount++;
					printMessage("number of login status : " + failedCount);
					lockUserAccount();
					if(failedCount == Constants.LOCKCOUNT_CONDITION) {
						exitUser();
					}else {
						userLoginElseBlockSwitch();
					}
				}
			}
				else {
					printMessage("sessionFactory is null");
				}
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	private  void userLoginSwitchCase() {
		printMessage("option-1: Go to plan Journey page :");
		printMessage("option-2 : Edit travel date page :");
		printMessage("option-3 : Exit user :");
		option = optionVariable();
		switch (option) {
			case 1:
				planJourney();
				break;
			case 2:
				editTravelDate();
				break;
			case 3:
				exitUser();
				break;
			default:
				printMessage("Invalid option selection :");
		}
	}
	
	private  void userLoginElseBlockSwitch() {
		printMessage("\n please select options either 1 or 2 :");
		printMessage("\n Option-1 : Again go to user Login ");
		printMessage("\n Option-2 : Go to newUser registration page :");
		option = optionVariable();
		switch (option) {
			case 1:
				userLogin();
				break;
			case 2:
				userRegistration();
				break;
			default:
				printMessage(" Invalid option selection ");
				break;
		}
	}
	
	@SuppressWarnings("deprecation")
	public  void userRegistration() {
		printMessage("\n Welcome to New user registration page : \n");
		
		printMessage("Please  Enter the first name !");
		userObject.setFirstName(input.next());
		firstNameValidation();
		
		
		printMessage("Please  Enter the Last name !");
		userObject.setLastName(input.next());
		lastNameValidation();
		
		

		mobileNumberValidation();
		if(userObject.getMobileNumber().length() ==10) {
			printMessage("\n Mobile number is Valid");
		}else {
			mobileNumberValidation();	
		}
		
		genderValidation();
		if((userObject.getGender().equalsIgnoreCase("male")) || (userObject.getGender().equalsIgnoreCase("female")) 
		|| (userObject.getGender().equalsIgnoreCase("others"))){
			printMessage("\n Gender is Valid");
		}else {
			genderValidation();	
		}
		
		suggestedUserNameGenerator();
		printMessage("\n Enter EmailId : ");
		userObject.setEmailId(input.next());
		emailValidation();
		
		
		suggestedPasswordGenerator();
		printMessage("\n Enter 8 digits user Password : ");
		userObject.setPassword(input.next());
		passwordValidation();	
		
	
		try {
		SessionFactory sessionFactory = HibernateUtils.buildSessionFactoryObject();
		if(sessionFactory != null) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String query = insertUserRowInDb();
		session.createNativeQuery(query);
		session.persist(userObject);
		loginUser.setEmail(userObject.getEmailId());
		loginUser.setUserPassword(userObject.getPassword());
		session.persist(loginUser);
		session.getTransaction().commit();
		printMessage("\n User registered successfully! please login with your credentials \n ");
		}else {
			printMessage("sessionFactory is null");
			printMessage("Row is not inserted");
		}
		userLogin();
		}
		catch (Exception exception) {

			System.out.println(exception);
		}
	}
	
	public  void firstNameValidation() {
		validationVariable = userObject.getFirstName() != null ? Constants.VALID : Constants.INVALID;
		printMessage("\n First name is "+validationVariable);
	}
	

	private void lastNameValidation() {
		validationVariable = userObject.getLastName() != null ? Constants.VALID : Constants.INVALID;
		printMessage("\n Last name is "+validationVariable);
	}

	private void mobileNumberValidation() {
			printMessage("Please Enter 10 digit Mobile number !");
			userObject.setMobileNumber(input.next());
	}

	private void genderValidation() {
		printMessage("Enter user Gender : ");
		userObject.setGender(input.next()); 
	}
	
	public void emailValidation() {
		if(userObject.getEmailId().startsWith(".") && userObject.getEmailId().endsWith(".") && userObject.getEmailId().startsWith("@")
				&& userObject.getEmailId().endsWith("@") ) {
			printMessage("Email is Invalid");
		}
		else {
			printMessage("Email is Valid");
		}
	}
	public void passwordValidation() {
		validationVariable = userObject.getPassword().length() ==8 ? Constants.VALID : Constants.INVALID;
		printMessage("\n Password is "+validationVariable);
	}
	
	public static void lockUserAccount() {
		if (failedCount > Constants.LOCKCOUNT) {

			printMessage("User account is locked... please try again after 24 hours");
		} else {
			printMessage("User account is not locked try again");
			
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void bookingTickets() {

		try {
			SessionFactory sessionFactory = HibernateUtils.buildSessionFactoryObject();
			if(sessionFactory != null) {
			Session session = sessionFactory.openSession();
			Long count = getCountOfTravelObjectFromDb(session);
			if(count > Constants.COUNT) {
				printMessage("\n Given source & destination are exist in database");
				printMessage("\n Buses are availble for these locations...Please complete the ticket booking\n");
				Query query1 = getTravelDetailsObjectFromDb(session);
				List<Object[]> list = query1.list();
				for (Object[] object : list) {
					printMessage(object[0]+"  "+object[1]+"   "+object[2]);
					GeneralUtils.setInitialPrice((Double) object[2]);
				}
				if (finalDay.equals(Constants.WEEKEND_DAY1) || finalDay.equals(Constants.WEEKEND_DAY2)) {
					weekdayPriceCalculation();
					weekendPriceCalculation();
					printMessage("\n Your ticket booking day is " + finalDay
							+ " it is weekend so we additionaly add price 200 & GST is 5% of total fare :");
					printMessage("\n GST in rupees on total travel fare is : " + (0.05 * GeneralUtils.getWeekdayPrice()));
					printMessage("\n total price for booking tickets : " + GeneralUtils.getWeekendPrice());
					ticketPayment();
				} else {
					weekdayPriceCalculation();
					printMessage("\n Your ticket booking day is Weekday so ticket price is normal :");
					printMessage("\ntotal price for booking tickets : " + GeneralUtils.getWeekdayPrice());
					ticketPayment();
				}
				bookingTicketsSwitchCase();
			}
		 else {
			printMessage("\n Given source & destination are not exist in database\n");
			printMessage("\n Buses are not availble for these locations...Please change the location details ");
			planJourney();
		}
	}
			} catch (Exception exception) {
		System.out.println(exception);
		printMessage("Wrong details selection... please again go to planJourney page ");
		planJourney();
	}
}
	public void ticketPayment() {
		printMessage(" Option-1 : Go to payment gateway to complete the payment process !");
		printMessage("Option-2 : Go to planJourney page ! ");
		printMessage("Option-3 : Exit user !");
		option = optionVariable();
		switch(option) {
			case 1:
				printMessage("Welcome to payment gateway");
				payment();
				break;
			case 2:
				planJourney();
				break;
				case 3:
					exitUser();
					break;
					default:
						printMessage("Invalid option selection !");
		}
	}
	public void payment() {
		printMessage("Please enter the amount for ticket booking !");
		GeneralUtils.setAmount(input.nextDouble());
		if(GeneralUtils.getWeekdayPrice() == GeneralUtils.getAmount() || GeneralUtils.getWeekendPrice() == GeneralUtils.getAmount()) {
			printMessage("Payment process is completed !");
			printMessage("\n Your tickets booking was confirmed...");
		}else {
			printMessage("payment is not sufficient for booking tickets !");
		}	
	}
	private void bookingTicketsSwitchCase() {
		printMessage("\n Please select option for further process :");
		printMessage("\n Option-1 : Again go for Plan journey page :");
		printMessage("\n Option-2: Edit travel date page :");
		printMessage("\n option-3 : Exit user");
		printMessage("\n Please select one option :");
		option = optionVariable();
		switch (option) {
			case 1:
				planJourney();
				break;
			case 2:
				editTravelDate();
				break;
			case 3:
				exitUser();
				break;
			default:
				printMessage("Invalid option selection");
				break;
		}
	}
	public void editTravelDate() {
		getValidateDateAndDay();
		bookingTickets();
	}
	
	public void suggestedPasswordGenerator() {
		charCombination = Constants.ALPHA_SPECIAL_COMBINATION;
		printMessage("\n Suggested password ");
		randomStringFunction();
	}
	
	public void suggestedUserNameGenerator() {
		charCombination =Constants.ALPHABET_COMBINATION;
		printMessage("\n Suggested username ");
		randomStringFunction();
	}
		public void planJourney() {
		getValidateDateAndDay();
		printMessage("\n Welcome to Plan journey page : \n");
		printMessage("\nPlease enter source location :");
		travelDetails.setSource(input.next());
		printMessage("\nPlease enter destination location :");
		travelDetails.setDestination(input.next());
		printMessage("\nPlease enter number of persons for booking tickets : ");
		GeneralUtils.setNumberOfPassengers(input.nextInt());
		bookingTickets();
	}
			public void getValidateDateAndDay() {
			printMessage("\nPlease enter the travel date in the form of (DD/MM/YYYY) : ");
			GeneralUtils.setTravelDate(input.next());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			simpleDateFormat.setLenient(Constants.FALSE_CONDITION);
			try {
				simpleDateFormat.parse(GeneralUtils.getTravelDate());
				printMessage("\n" +GeneralUtils.getTravelDate() + " is a valid Date");
				Date dateObject = simpleDateFormat.parse(GeneralUtils.getTravelDate());
				DateFormat format = new SimpleDateFormat(Constants.DATESTANDARDFORMAT);
				finalDay = format.format(dateObject);
				printMessage("\n Day of the given travel date : " + finalDay);
			} catch (Exception exception) {
				printMessage(GeneralUtils.getTravelDate() + " is not a valid Date...plz re enter the data again :");
				getValidateDateAndDay();
			}
		}
	private void randomStringFunction() {
		Random randomMethod = randomObject();
		char [] passwordArray = new char[Constants.STRING_LENGTH];
		for (int temp = Constants.COUNT; temp < Constants.STRING_LENGTH; temp++) {
			passwordArray[temp] = charCombination.charAt(randomMethod.nextInt(charCombination.length()));	
		}
		System.out.println(passwordArray);
	}
	
	private Random randomObject() {
		Random randomMethod = new Random();
		return randomMethod;
	}
	
	public static void fileNotFoundCustomException() throws FileNotFoundException {
		throw new FileNotFoundException("Logo file is not found... please check the file path \n");
	}
	
	private static int optionVariable() {
		return input.nextInt();
	}
	
	public static void exitUser() {
		printMessage("\n Exit...Thank you for using this application \n");
	}
	
	public static void printMessage(String message) {
		System.out.println(message);
	}
	
	private void weekendPriceCalculation() {
		GeneralUtils.setWeekendPrice(GeneralUtils.getWeekdayPrice()+200+(0.05* GeneralUtils.getWeekdayPrice()));
	}

	private void weekdayPriceCalculation() {
		GeneralUtils.setWeekdayPrice(GeneralUtils.getInitialPrice() *  GeneralUtils.getNumberOfPassengers());
	}
	
	private String insertUserRowInDb() {
		return "insert into new_user_registration values('" + userObject.getFirstName() + "', '" + userObject.getLastName() + "', '"
				+ userObject.getMobileNumber() + "', '" + userObject.getGender() + "', '" + userObject.getEmailId() + "', '" + userObject.getPassword() + "' )";
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	private Query getTravelDetailsObjectFromDb(Session session) {
		return session.createQuery("select source, destination, ticketPrice from TravelDetails where source='"+travelDetails.getSource()+"' and destination = '"+travelDetails.getDestination()+"' ");
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	private Long getCountOfTravelObjectFromDb(Session session) {
		Query query =session.createQuery("select count(*) from TravelDetails where source='"+travelDetails.getSource()+"' and destination = '"+travelDetails.getDestination()+"' ");
		return (Long)query.uniqueResult();
	}
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	private Long getLoginUserObjectFromDb(String userId, String password, Session session) {
		Query query =session.createQuery("select count(*) from LoginUser where email='"+userId+"' and UserPassword = '"+password+"' ");
		return (Long)query.uniqueResult();
	}
	

}
