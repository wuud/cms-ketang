package cn.henau.cms.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.henau.cms.model.Course;
import cn.henau.cms.model.Role;
import cn.henau.cms.model.User;
import cn.henau.cms.service.CourseService;
import cn.henau.cms.service.HostHolder;
import cn.henau.cms.service.JoinCourseService;
import cn.henau.cms.service.RoleService;
import cn.henau.cms.service.UserService;

@WebServlet(value = { "/localUser", "/admin/delUser", "/admin/updateUserModal", "/admin/updateUser" })
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private HostHolder hostHolder;
	private UserService userService;
	private JoinCourseService joinCourseService;
	private CourseService courseService;
	private RoleService roleService;

	@Override
	public void init() throws ServletException {
		hostHolder = (HostHolder) getServletContext().getAttribute("HostHolder");
		userService = (UserService) getServletContext().getAttribute("UserService");
		joinCourseService = (JoinCourseService) getServletContext().getAttribute("JoinCourseService");
		courseService = (CourseService) getServletContext().getAttribute("CourseService");
		roleService = (RoleService) getServletContext().getAttribute("RoleService");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getRequestURI().equals("/cms/localUser")) {
			getUserDetail(req, resp);
		} else if (req.getRequestURI().equals("/cms/admin/delUser")) {
			delUser(req, resp);
		} else if (req.getRequestURI().equals("/cms/admin/updateUserModal")) {
			updateUserModal(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getRequestURI().equals("/cms/admin/updateUser")) {
			updateUser(req, resp);
		}
	}

	public void getUserDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int userId = hostHolder.getUser().getId();
		// 我参加的课程
		List<Integer> userCourses = joinCourseService.getUserCourses(userId);
		List<Map<String, Object>> joinCourseList = new ArrayList<>();
		if (userCourses != null) {
			for (Integer i : userCourses) {
				Map<String, Object> map = new HashMap<>();
				Course course = courseService.getCourseById(i);
				System.out.println(course.getUser().getUsername());
				map.put("course", course);
				map.put("teacher", course.getUser().getUsername());
				joinCourseList.add(map);
			}
		}
		req.setAttribute("joinCourseList", joinCourseList);

		if (hostHolder.getUser().getRoleId().getId() >= 2) {
			// 我发布的课程
			List<Course> courseByUser = courseService.getCourseByUser(userId);
			List<Map<String, Object>> myCourseList = new ArrayList<>();
			for (Course c : courseByUser) {
				Map<String, Object> map = new HashMap<>();
				map.put("course", c);
				map.put("count", joinCourseService.countCourses(c.getId()));
				myCourseList.add(map);
			}
			req.setAttribute("myCourseList", myCourseList);
		}
		req.getRequestDispatcher("/WEB-INF/jsp/userDetail.jsp").forward(req, resp);
	}

	private void delUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer userId = Integer.parseInt(req.getParameter("id"));
		User u = userService.getUserById(userId);
		Integer curId = u.getRoleId().getId();
		userService.delUser(userId);
		resp.sendRedirect("/cms/admin/user/" + curId);
	}

	public void updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Integer roleId = Integer.parseInt(req.getParameter("role_Id"));
		Integer userId = Integer.parseInt(req.getParameter("userId"));
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");

		User user = userService.getUserById(userId);
		user.setRoleId(roleService.getRoleById(roleId));
		user.setPassword(password);
		user.setUsername(username);
		user.setEmail(email);
		user.setPhone(phone);
		
		userService.updateUser(user);
		resp.sendRedirect("/cms/admin/user/" + roleId);
	}

	private void updateUserModal(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Integer userId = Integer.parseInt(req.getParameter("id"));
		User u = userService.getUserById(userId);
		List<Role> roleList = roleService.getAllRole();
		String optStr = "";
		for (Role r : roleList) {
			if (u.getRoleId().getId() == r.getId()) {
				optStr = optStr + "<option selected value=\"" + r.getId() + "\">" + r.getRname() + "</option>\r\n";
			} else {
				optStr = optStr + "<option value=\"" + r.getId() + "\">" + r.getRname() + "</option>\r\n";
			}
		}
		String path = req.getContextPath();
		resp.setHeader("Content-type", "text/html;charset=UTF-8");
		resp.getWriter().println("<div class=\"modal-header\">\r\n"
				+ "					<button type=\"button\" class=\"close\" data-dismiss=\"modal\"\r\n"
				+ "						aria-label=\"Close\">\r\n" + "						<span>&times;</span>\r\n"
				+ "					</button>\r\n"
				+ "					<h4 class=\"modal-title\" id=\"myModalLabel\">编辑用户</h4>\r\n"
				+ "				</div>\r\n" + "				<div class=\"modal-body\">\r\n"
				+ "					<form id=\"updateUserForm\"\r\n" + "						action=\"" + path
				+ "/admin/updateUser\"\r\n" + "						method=\"post\">\r\n"
				+ "                       <input type='hidden' name='userId' value='" + u.getId() + "'/>"
				+ "						<div class=\"form-group\">\r\n"
				+ "							<label>用户名：</label> <input type=\"text\" name=\"username\"\r\n"
				+ "								class=\"form-control\" value=\"" + u.getUsername() + "\">\r\n"
				+ "						</div>\r\n" + "						<div class=\"form-group\">\r\n"
				+ "							<label>密码（留空表示不修改）：</label> <input type=\"password\" name=\"password\"\r\n"
				+ "								class=\"form-control\" placeholder=\"密码已加密\">\r\n"
				+ "						</div>\r\n" + "						<div class=\"form-group\">\r\n"
				+ "							<label>手机号：</label> <input type=\"text\" name=\"phone\"\r\n"
				+ "								class=\"form-control\" value=\"" + u.getPhone() + "\">\r\n"
				+ "						</div>\r\n" + "						<div class=\"form-group\">\r\n"
				+ "							<label>邮箱：</label> <input type=\"text\" name=\"email\"\r\n"
				+ "								class=\"form-control\" value=\"" + u.getEmail() + "\">\r\n"
				+ "						</div>\r\n" + "						<div class=\"form-group\">\r\n"
				+ "							<label>用户类型：</label> <select class=\"selectpicker form-control\"\r\n"
				+ "								name=\"role_Id\">\r\n" + optStr
				+ "							</select>\r\n" + "						</div>\r\n"
				+ "					</form>\r\n" + "				</div>\r\n"
				+ "				<div class=\"modal-footer\">\r\n"
				+ "					<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭</button>\r\n"
				+ "					<button onclick=\"updateUserFromSubmit()\" type=\"button\" class=\"btn btn-primary\">完成</button>\r\n"
				+ "				</div>\r\n" + "			</div>\r\n" + "		</div>".getBytes("UTF-8"));
	}
}
