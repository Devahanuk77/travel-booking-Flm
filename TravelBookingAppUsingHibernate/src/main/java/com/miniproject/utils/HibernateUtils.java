package com.miniproject.utils;

import com.miniproject.entity.LoginUser;
import com.miniproject.entity.TravelDetails;
import com.miniproject.entity.User;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	
	public static SessionFactory sessionFactory = null;
	
	private HibernateUtils() {
		
	}
	
	public static SessionFactory buildSessionFactoryObject() {
		
		if(sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
				configuration.configure("hibernate.cfg.xml");
				configuration.addAnnotatedClass(User.class);
				configuration.addAnnotatedClass(LoginUser.class);
				configuration.addAnnotatedClass(TravelDetails.class);
				
				sessionFactory = configuration.buildSessionFactory();
			}
			catch(Exception e) {
				System.out.println("Something wrong with the sessionFactory object !");
			}
		}
		return sessionFactory;
		
	}
	
}

