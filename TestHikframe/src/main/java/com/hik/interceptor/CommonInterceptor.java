package com.hik.interceptor;

import java.io.PrintWriter;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CommonInterceptor extends HandlerInterceptorAdapter{  
    private final Log log = LogFactory.getLog(CommonInterceptor.class);   
    /**  
     * ��ҵ��������������֮ǰ������  
     * �������false  
     *     �ӵ�ǰ������������ִ��������������afterCompletion(),���˳��������� 
     * �������true  
     *    ִ����һ��������,ֱ�����е���������ִ�����  
     *    ��ִ�б����ص�Controller  
     *    Ȼ�������������,  
     *    �����һ������������ִ�����е�postHandle()  
     *    �����ٴ����һ������������ִ�����е�afterCompletion()  
     */    
    @Override    
    public boolean preHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler) throws Exception {   

        String contextPath = request.getContextPath(); 
        String username =  (String)request.getSession().getAttribute("userId");   

        if(username== null){  
            InetAddress addr = InetAddress.getLocalHost();
            String host=addr.getHostAddress();//��ñ���IP
            int port=request.getLocalPort();
            System.out.println(host+":"+port);
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("window.open ('http://"+host+":"+port+contextPath+"/pages/login.jsp','_top')");  
            out.println("</script>");  
            out.flush();
            out.close();
            return false;  
        }else  
            return true;     
    }    
    /** 
     * ��ҵ��������������ִ����ɺ�,������ͼ֮ǰִ�еĶ���    
     * ����modelAndView�м������ݣ����統ǰʱ�� 
     */  
    @Override    
    public void postHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler,    
            ModelAndView modelAndView) throws Exception {     
//        log.info("==============ִ��˳��: 2��postHandle================");    
        if(modelAndView != null){  //���뵱ǰʱ��    
            modelAndView.addObject("var", "����postHandle");    
        }    
    }    
    /**  
     * ��DispatcherServlet��ȫ����������󱻵���,������������Դ��   
     *   
     * �����������׳��쳣ʱ,��ӵ�ǰ����������ִ�����е���������afterCompletion()  
     */    
    @Override    
    public void afterCompletion(HttpServletRequest request,    
            HttpServletResponse response, Object handler, Exception ex)    
            throws Exception {    
//        log.info("==============ִ��˳��: 3��afterCompletion================");    
    }    
}    