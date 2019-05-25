package cn.henau.cms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.henau.cms.service.HostHolder;

/**
 * 有些url是用户登录后才有权限访问的，
 * 如果用户未登录，强行跳转到登录页面
 */
@WebFilter(value = {"/course/*","/addCourse","/admin/*","/admin/**","/course/**","/localUser"})
public class F1_RequireLoginFilter implements Filter {
	
	private HostHolder hostHolder;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp=(HttpServletResponse) response;
		hostHolder=(HostHolder) request.getServletContext().getAttribute("HostHolder");
		if(hostHolder.getUser()==null) {
			resp.sendRedirect("/cms/login");
		}else {
			chain.doFilter(request, response);
		}
		
		
	}

	@Override
	public void destroy() {
		
	}

}
