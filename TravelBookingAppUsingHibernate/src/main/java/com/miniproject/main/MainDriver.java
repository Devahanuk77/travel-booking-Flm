package com.miniproject.main;

import java.io.FileNotFoundException;

import com.miniproject.service.TravelFunctions;
import com.miniproject.utils.Constants;

public class MainDriver {
	public static void main(String[] args) throws FileNotFoundException {
		TravelFunctions travelObject = TravelFunctions.getInstanceUsingSingleton();
		try {
			TravelFunctions.printLogoUsingFile(Constants.FILEPATH);
			travelObject.menuOptions();
			
		} catch (Exception exception) {
			TravelFunctions.fileNotFoundCustomException();
		}
	}

}
