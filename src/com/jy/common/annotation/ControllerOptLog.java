package com.jy.common.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解类
 * 请填上日志描述:desc
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public @interface ControllerOptLog {
	/**
	 * 日志描述
	 * @return
	 */
	String desc() default "";  
}