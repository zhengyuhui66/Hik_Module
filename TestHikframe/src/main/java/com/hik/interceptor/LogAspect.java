package com.hik.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;

import com.hik.framework.service.ILogService;
import com.hik.framework.utils.SysContent;

@Aspect //该注解标示该类为切面类 
@Component //注入依赖
public class LogAspect {
	private static String NOUSER="无用户信息";
	@Autowired
	private ILogService ilogService;
	
	@Pointcut("execution(public * *(..))")
	private void anyMethod(){}
	
    @AfterReturning("anyMethod() && @annotation(rl)")  
    public void addLogSuccess(JoinPoint jp, HikLog rl){
//    	  HttpServletRequest request = SysContent.getRequest();  
//          HttpServletResponse response = SysContent.getResponse();  
    	jp.getClass();
//    	System.out.println(jp.getClass()+"=============!"+jp+"===>jp.getKind():"+jp.getKind()
//    	+"===>jp.getThis():"+jp.getThis().toString()
//    	+"===>jp.getTarget():"+jp.getTarget().toString()
//    	+"===>jp.ADVICE_EXECUTION():"+jp.ADVICE_EXECUTION
//    	+"===>jp.CONSTRUCTOR_CALL():"+jp.CONSTRUCTOR_CALL
//    	+"===>jp.CONSTRUCTOR_EXECUTION():"+jp.CONSTRUCTOR_EXECUTION
//    	+"===>jp.EXCEPTION_HANDLER():"+jp.EXCEPTION_HANDLER
//    	+"===>jp.FIELD_GET():"+jp.FIELD_GET
//    	+"===>jp.FIELD_SET():"+jp.FIELD_SET
//    	+"===>jp.INITIALIZATION():"+jp.INITIALIZATION
//    	+"===>jp.METHOD_CALL():"+jp.METHOD_CALL
//    	+"===>jp.METHOD_EXECUTION():"+jp.METHOD_EXECUTION
//    	+"===>jp.PREINITIALIZATION():"+jp.PREINITIALIZATION
//    	+"===>jp.STATICINITIALIZATION():"+jp.STATICINITIALIZATION
//    	+"===>jp.SYNCHRONIZATION_LOCK():"+jp.SYNCHRONIZATION_LOCK
//    	+"===>jp.SYNCHRONIZATION_UNLOCK():"+jp.SYNCHRONIZATION_UNLOCK
//    	+"===>jp.getStaticPart():"+jp.getStaticPart().toString());
          HttpSession session = SysContent.getSession();  
          HttpServletRequest request = SysContent.getRequest();
          Enumeration enu=request.getParameterNames();
          String aopMethod=jp.toString();
          while(enu.hasMoreElements()){
	          String paraName=(String)enu.nextElement();  
	          aopMethod+=" "+paraName;
//          System.out.println(paraName+": "+request.getParameter(paraName));  
          }
          
//          System.out.println("THreadNBame:"+Thread.currentThread().getName()+"    id:"+Thread.currentThread().getId());
//          System.out.println("session:"+session.getAttribute("userId")+"================="+session.getAttribute("userName"));
          Object obj=session.getAttribute("userName");
          String userid=NOUSER;
          if(obj!=null){
        	  userid=obj.toString();
          }
          ilogService.insertLogInfo(userid, rl.content(), rl.curd(),aopMethod);
//    	System.out.println("==========================*************address:"+rl.content()+"***"+rl.curd()+"   ))))"+rl.user());
    }
}