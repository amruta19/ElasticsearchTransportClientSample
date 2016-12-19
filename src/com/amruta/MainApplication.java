package com.amruta;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApplication {
	public static void main(String[] args) {
	
		//Integer userUid = Integer.parseInt(args[0]);
		long startTime = System.currentTimeMillis();
		ApplicationContext context = new ClassPathXmlApplicationContext("com/amruta/resources/applicationContext.xml");
		ApplicationService appService =(ApplicationService)context.getBean("applicationService");
		//for all
		int status = appService.addAllEmployeesToES();
		if(status==0){
			System.out.println("Process stoped in between");
		}
		else{
			System.out.println("Completed successfully");
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total time : " + (endTime - startTime));
	}
}
