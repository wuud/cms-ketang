package cn.henau.cms.service;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.junit.runner.Request;

import cn.henau.cms.annotation.Component;
import cn.henau.cms.dao.UserDao;
import cn.henau.cms.model.Role;
import cn.henau.cms.model.User;
import cn.henau.cms.utils.MybatisUtil;

@Component
public class UserService {

	UserDao userDao = MybatisUtil.getClass(UserDao.class);
	RoleService roleService;

	public User getUserById(int id) {
		return userDao.getUserById(id);
	}

	public void insertUser(User u) {
		u.setAddDate(new Date());
		u.setStatus(1);// 1表示正常状态，非1表示账户异常
		userDao.insertUser(u);
	}

	public Integer countAllUser() {
		return userDao.countAllUser();
	}

	public List<User> getUsersByPage(int page, int pageSize) {
		int offsetNum = page * pageSize;
		return userDao.getUsersByPage(pageSize, offsetNum);
	}

	public User getUserByName(String name) {
		return userDao.getUserByName(name);
	}

	public void delUser(int id) {
		SqlSession session = MybatisUtil.getSession();
		UserDao dao = session.getMapper(UserDao.class);
		dao.delUser(id);
		session.commit();
	}

	public void batchDelUsers(int[] ids) {
		for (int i : ids) {
			userDao.delUser(i);
		}
	}

	public List<User> searchUser(String userInfo, int pageSize, int page) {
		int offsetNum = pageSize * page;
		return userDao.searchUser("%" + userInfo + "%", pageSize, offsetNum);
	}

	public List<User> getUsersByRole(int roleId, int pageSize, int page) {
		SqlSession session = MybatisUtil.getSession();
		UserDao dao = session.getMapper(UserDao.class);
		int offsetNum = pageSize * page;
		List<User> usersByRole = dao.getUsersByRole(roleId, pageSize, offsetNum);
		session.commit();
		return usersByRole;
	}

	public void updateUser(User u) {
		SqlSession session = MybatisUtil.getSession();
		UserDao dao = session.getMapper(UserDao.class);
		dao.updateUser(u);
		session.commit();
	}

	public void userPageControl(HttpServletRequest req, int roleId, int page, int pageSize) {
		ServletContext servletContext = req.getServletContext();
		this.roleService = (RoleService) servletContext.getAttribute("RoleService");
		List<Role> roleList = roleService.getAllRole();
		req.setAttribute("roleList", roleList);
		// 将分页查询的到的数据传给前端
		List<User> userList = getUsersByRole(roleId, pageSize, page);
		req.setAttribute("userList", userList);
		// 获得用户总量，在前端显示
		Integer total = userDao.countUserByRole(roleId);
		pageControl(total, req, page, pageSize);
		req.setAttribute("roleId", roleId);
	}

	/**
	 * 进行分页控制
	 * 
	 * @param total
	 * @param req
	 * @param page
	 * @param pageSize
	 */
	public void pageControl(int total, HttpServletRequest req, int page, int pageSize) {
		// 获得总页数
		int totalPages = (int) Math.ceil(((double) total / pageSize));
		// 获得上一页
		int prevPage = 0;
		if (page > 0) {
			prevPage = page - 1;
		}
		// 获得下一页
		int nextPage = 0;
		if (page < totalPages - 1) {
			nextPage = page + 1;
		} else {
			nextPage = totalPages - 1;
		}

		req.setAttribute("total", total);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("page", page);
		req.setAttribute("prevPage", prevPage);
		req.setAttribute("nextPage", nextPage);
	}

}
