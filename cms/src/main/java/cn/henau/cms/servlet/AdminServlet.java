package cn.henau.cms.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import cn.henau.cms.service.RoleService;
import cn.henau.cms.service.UserService;

@WebServlet(value = {"/admin","/admin/user/*","/admin/welcome","/admin/res"})
public class AdminServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	UserService userService;
	RoleService roleService;
	
	@Override
	public void init() throws ServletException {
		userService=(UserService) getServletContext().getAttribute("UserService");
		roleService=(RoleService) getServletContext().getAttribute("RoleService");
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getRequestURI().equals("/cms/admin")) {
			admin(req,resp);
		}else if(req.getRequestURI().startsWith("/cms/admin/user/")) {
			userManage(req,resp);
		}else if(req.getRequestURI().equals("/cms/admin/welcome")) {
			welcome(req,resp);
		}else if(req.getRequestURI().equals("/cms/admin/res")) {
			res(req,resp);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}

	public void admin(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(d);
		req.setAttribute("date", date);
		req.getRequestDispatcher("/WEB-INF/jsp/admin/admin.jsp").forward(req, resp);
	}

	public void userManage(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		Integer page;
		Integer pageSize;
		//设置page和pageSize的默认值
		if(StringUtils.isBlank(req.getParameter("page"))) {
			page=0;
		}else {
			page=Integer.parseInt(req.getParameter("page"));
		}
		if(StringUtils.isBlank(req.getParameter("pageSize"))) {
			pageSize=10;
		}else {
			pageSize=Integer.parseInt(req.getParameter("pageSize"));
		}
		//根据不同路径将请求转发给不同jsp页面
		if(req.getRequestURI().endsWith("1")) {
			int roleId = 1;
			userService.userPageControl(req, roleId, page, pageSize);
			req.getRequestDispatcher("/WEB-INF/jsp/admin/user.jsp").forward(req, resp);
			return;
		}else if(req.getRequestURI().endsWith("2")) {
			int roleId = 2;
			userService.userPageControl(req, roleId, page, pageSize);
			req.getRequestDispatcher("/WEB-INF/jsp/admin/teacher.jsp").forward(req, resp);
			return;
		}else if(req.getRequestURI().endsWith("3")) {
			int roleId = 3;
			userService.userPageControl(req, roleId, page, pageSize);
			req.getRequestDispatcher("/WEB-INF/jsp/admin/manager.jsp").forward(req, resp);
			return;
		}
	}

	public void res(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/admin/res.jsp").forward(req, resp);
	
	}

	public void welcome(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/admin/welcome.jsp").forward(req, resp);
		
	}
	
	
	
	
	
	
	
}
