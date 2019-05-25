package cn.henau.cms.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import cn.henau.cms.constants.Constants;
import cn.henau.cms.model.Course;
import cn.henau.cms.model.Role;
import cn.henau.cms.model.User;
import cn.henau.cms.service.CourseService;
import cn.henau.cms.service.RoleService;
import cn.henau.cms.service.UserService;

@WebServlet(value = { "/searchCourse", "/admin/searchUser" })
public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UserService userService;
	private RoleService roleService;
	private CourseService courseService;

	@Override
	public void init() throws ServletException {
		userService = (UserService) getServletContext().getAttribute("UserService");
		roleService = (RoleService) getServletContext().getAttribute("RoleService");
		courseService = (CourseService) getServletContext().getAttribute("CourseService");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getRequestURI().equals("/cms/searchCourse")) {
			searchCourse(req, resp);
		} else if (req.getRequestURI().equals("/cms/admin/searchUser")) {
			searchUser(req, resp);
		}
	}

	/**
	 * 搜索课程
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	public void searchCourse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String courseInfo = req.getParameter("courseInfo");
		List<Course> courseList = courseService.searchCourse(courseInfo);
		int total = courseList.size();
		System.out.println(courseList);
		userService.pageControl(total, req, 0, Constants.COURSE_PAGE_SIZE);
		req.setAttribute("courseList", courseList);
		req.getRequestDispatcher("/WEB-INF/jsp/allCourse.jsp").forward(req, resp);
	}

	/**
	 * 搜索用户
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	public void searchUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userInfo = req.getParameter("userInfo");
		Integer page = 0;
		if (!StringUtils.isBlank(req.getParameter("page"))) {
			page = Integer.parseInt(req.getParameter("page"));
		}
		Integer pageSize = 10;
		if (!StringUtils.isBlank(req.getParameter("pageSize"))) {
			pageSize = Integer.parseInt(req.getParameter("pageSize"));
		}
		List<User> userList = userService.searchUser(userInfo, pageSize, page);
		List<Role> roleList = roleService.getAllRole();
		req.setAttribute("roleList", roleList);
		// 将分页查询的到的数据传给前端
		req.setAttribute("userList", userList);
		// 获得用户总量,在前端显示
		Integer total = userList.size();
		// 获得总页数
		int totalPages = (int) (((double) total / pageSize) + 1);
		// 获得上一页
		int prevPage = 0;
		if (page > 0) {
			prevPage = page - 1;
		}
		// 获得下一页
		int nextPage = totalPages;
		if (page < totalPages) {
			nextPage = page + 1;
		}

		req.setAttribute("total", total);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("page", page);
		req.setAttribute("prevPage", prevPage);
		req.setAttribute("nextPage", nextPage);
		req.getRequestDispatcher("/WEB-INF/jsp/admin/user.jsp").forward(req, resp);
	}

}
