package cn.henau.cms.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.henau.cms.model.Course;
import cn.henau.cms.service.CourseService;

@WebServlet(value = {"/index",""})
public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    CourseService courseService = null;

    @Override
    public void init() throws ServletException {
    	ServletContext context = getServletContext();
        courseService = (CourseService) context.getAttribute("CourseService");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Course> courseList = courseService.getAllCourse();

        req.setAttribute("courseList", courseList);

        req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
    }


}
