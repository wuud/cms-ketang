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
 * 权限控制Filter
 */
@WebFilter(value = {"/addCourse","/admin/*","/admin/**"})
public class F2_AuthorityControlFilter implements Filter{
	
	private HostHolder hostHolder;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;
		hostHolder=(HostHolder) req.getServletContext().getAttribute("HostHolder");
		
		// 需要超级管理员权限
		if(req.getRequestURI().equals("/cms/admin/user/3")) {
			if(hostHolder.getUser().getRoleId().getId()>3) {
				chain.doFilter(request, response);
			}else {
				req.getRequestDispatcher("/WEB-INF/jsp/authority.jsp").forward(req, resp);
			}
		// 需要管理员权限
		}else if(req.getRequestURI().startsWith("/cms/admin")) {
			if(hostHolder.getUser().getRoleId().getId()>2) {
				chain.doFilter(request, response);
			}else {
				req.getRequestDispatcher("/WEB-INF/jsp/authority.jsp").forward(req, resp);
			}
		// 需要教师用户权限
		}else if(req.getRequestURI().equals("/cms/addCourse")) {
			if(hostHolder.getUser().getRoleId().getId()>1) {
				chain.doFilter(request, response);
			}else {
				req.getRequestDispatcher("/WEB-INF/jsp/authority.jsp").forward(req, resp);
			}
		}
	}

	@Override
	public void destroy() {
		
	}
	

}
