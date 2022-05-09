package com.example.demo.aop;

import java.sql.Time;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.example.demo.custom_annotations.LoggingTrack;
/**
 * Base class to perform functionality for user logging activity.
 * @author milindjaid
 *
 */
@Aspect
@Component
public class LogAspect {

	@Around(value = "@annotation(loggingTrack)")
	public Object trackLoggingActivity(ProceedingJoinPoint pjp, LoggingTrack loggingTrack) {
		Object object = null;
		try {
			System.out.println("Before logging at time " + new Date().getDate());
			object = pjp.proceed();
			System.out.println("After logging at time " + new Date().getDate());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
}
