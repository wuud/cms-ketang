package cn.henau.cms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.henau.cms.service.CourseService;
import cn.henau.cms.service.HostHolder;
import cn.henau.cms.service.JoinCourseService;

@WebServlet(value = {"/joinCourse","/unJoinCourse"})
public class JoinCourseServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private CourseService courseService;
	private JoinCourseService joinCourseService;
	private HostHolder hostHolder;

	@Override
	public void init() throws ServletException {
		courseService=(CourseService) getServletContext().getAttribute("CourseService");
		joinCourseService=(JoinCourseService) getServletContext().getAttribute("JoinCourseService");
		hostHolder=(HostHolder) getServletContext().getAttribute("HostHolder");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getRequestURI().equals("/cms/joinCourse")) {
			joinCourse(req,resp);
		}else if(req.getRequestURI().equals("/cms/unJoinCourse")) {
			unJoinCourse(req,resp);
		}
	}
	
	public void joinCourse(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		Integer courseId=Integer.parseInt(req.getParameter("courseId"));
		if(hostHolder.getUser()==null) {
			resp.sendRedirect("/cms/login");
		}
		int userId=hostHolder.getUser().getId();
		joinCourseService.joinCourse(courseId, userId);
		resp.sendRedirect("/cms/course/"+courseId);
	}
	
	public void unJoinCourse(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		Integer courseId=Integer.parseInt(req.getParameter("courseId"));
		if(hostHolder.getUser()==null) {
			resp.sendRedirect("/cms/login");
		}
		int userId=hostHolder.getUser().getId();
		joinCourseService.unJoinCourse(courseId, userId);
		resp.sendRedirect("/cms/course/"+courseId);
	}


}
