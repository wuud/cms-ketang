package cn.henau.cms.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.henau.cms.model.Course;
import cn.henau.cms.service.CourseService;

@WebServlet(value = "/test")
public class TestServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext context = req.getServletContext();
		Enumeration<String> ss = context.getAttributeNames();
		while(ss.hasMoreElements()) {
			System.out.println(ss.nextElement());
		}
		CourseService courseService = (CourseService) context.getAttribute("CourseService");
		List<Course> list = courseService.getAllCourse();
		System.out.println(list);
//		req.getRequestDispatcher("/WEB-INF/jsp/test.jsp").forward(req, resp);
	}

}
