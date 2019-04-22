package cn.henau.cms.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import cn.henau.cms.service.HostHolder;

/**
 * 如果当前用户已经登录，则不能再访问登录有关的所有url
 */
@WebFilter(value = {"/login", "/doLogin", "/reg"})
public class AlreadyLoginFilter implements Filter {
	
	private HostHolder hostHolder;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp=(HttpServletResponse) response;
		if(hostHolder!=null) {
			resp.sendRedirect("/cms/");
		} 
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
