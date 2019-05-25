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
import cn.henau.cms.service.JoinCourseService;

/**
 * 用来检查，用户是否参加了当前课程，如果没有参加则跳转至课程详情页
 */
@WebFilter(value = { "/course/videoList/*", "/course/video/*" })
public class F2_RequireJoinCourseFilter implements Filter {
	
	private HostHolder hostHolder;
	private JoinCourseService joinCourseService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		joinCourseService=new JoinCourseService();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse resp=(HttpServletResponse) response;
		String requestURI = req.getRequestURI();
		Integer courseId=null;
		// 先拿到当前课程id
		if(requestURI.startsWith("/cms/course/videoList/")) {
			courseId = Integer.parseInt(requestURI.substring(requestURI.lastIndexOf('/') + 1));
		}else if(requestURI.startsWith("/cms/course/video/")) {
			String iid = requestURI.substring(requestURI.lastIndexOf('/') + 1);
			String[] ids = iid.split("_");
			courseId = Integer.parseInt(ids[0]);
		}
		// 检查当前用户是否参加了这个课程
		hostHolder=(HostHolder) request.getServletContext().getAttribute("HostHolder");
		boolean isJoin = joinCourseService.isJoin(courseId, hostHolder.getUser().getId());
		if(!isJoin) {
			resp.sendRedirect("/cms/course/"+courseId);
		}else {
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void destroy() {

	}

}
