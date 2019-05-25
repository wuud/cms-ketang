package cn.henau.cms.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import cn.henau.cms.annotation.Component;
import cn.henau.cms.dao.UserDao;
import cn.henau.cms.model.Ticket;
import cn.henau.cms.model.User;
import cn.henau.cms.service.HostHolder;
import cn.henau.cms.service.TicketService;
import cn.henau.cms.utils.MybatisUtil;

/**
 * 由于WebFilter无法控制Filter类执行顺序，而是使用文件名排序的顺序执行， 所以修改文件名，让LoginFilter第一个执行
 */
@WebFilter(value = "/*")
@Component
public class F0_LoginFilter implements Filter {

	TicketService ticketService;
	UserDao userDao;
	HostHolder hostHolder;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		userDao = MybatisUtil.getClass(UserDao.class);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ServletContext context = request.getServletContext();
		ticketService = (TicketService) context.getAttribute("TicketService");
		hostHolder = (HostHolder) context.getAttribute("HostHolder");
		Ticket ticket = null;
		HttpServletRequest req = (HttpServletRequest) request;
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("ticket")) {
					ticket = ticketService.getTicketByTicket(c.getValue());
				}
			}
		}
		// 如果cookie中不含有ticket或ticket已过期直接返回
		if (ticket == null || ticket.getExpired().before(new Date()) || ticket.getStaus() != 0) {
			if (hostHolder.getUser() != null)
				hostHolder.setUser(null);
			chain.doFilter(request, response);
			return;
		}
		// 如果拿到ticket，则说明用户已登录，将user对象添加到ServletContext
		User user = userDao.getUserById(ticket.getUserId());
		hostHolder.setUser(user);
		req.setAttribute("user", user);

		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {

	}

}
