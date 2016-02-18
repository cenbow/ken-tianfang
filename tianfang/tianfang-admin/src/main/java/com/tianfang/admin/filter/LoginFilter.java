package com.tianfang.admin.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tianfang.admin.controller.Const;

/**
 * 后台管理登陆拦截器
 * 
 * @author xiang_wang
 *
 * 2015年10月22日下午1:06:13
 */
public class LoginFilter implements HandlerInterceptor{
	protected static final Log logger = LogFactory.getLog(LoginFilter.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		/*logger.debug("login validate --- ");
		logger.debug("requestURL : " + request.getRequestURL().toString());
		logger.debug("context : " + request.getContextPath());
		logger.debug("parameterMap : " + request.getParameterMap());
		logger.debug("uri :" + uri);*/
		if("/admin/login_toLogin.do".equals(uri) || "/admin/login_login.do".equals(uri)|| "/admin/logout.do".equals(uri)){
			return true;
		}
	
        Object context= request.getSession().getAttribute(Const.SESSION_USER);   
        
        if(null == context){  
			response.sendRedirect("/admin/login_toLogin.do");

			return false;
        }
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
