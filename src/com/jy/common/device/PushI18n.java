package com.jy.common.device;

import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PushI18n {
	
    private static ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"i18n/locale.xml"});

	public static String getI18nString(String title , Locale locale )
    {
		if( locale==null ){
			locale = new Locale("en", "US" );//默认
		}
		String message = null;
		try{
			message = context.getMessage(title, new Object[] { }, locale);
		}catch(Exception e){

		}
    	return message;
    }
	
	public static String getI18nString(String title , String locale )
    {
	
		Locale locale2 ;//默认
		
		if( locale==null || locale.length() !=5 ){
			locale2 = new Locale("en", "US" );//默认
		}else{
			locale2 = new Locale( (String) locale.subSequence(0,2) , 	(String) locale.subSequence(3,5));
		}
		String message = null;
		try{
			message = context.getMessage(title, new Object[] { }, locale2);
		}catch(Exception e){

		}
    	return message;
    }

}
