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
import cn.henau.cms.dao.TicketDao;
import cn.henau.cms.dao.UserDao;
import cn.henau.cms.model.Ticket;
import cn.henau.cms.model.User;
import cn.henau.cms.service.HostHolder;
import cn.henau.cms.utils.MybatisUtil;

@WebFilter(value = "/*")
@Component
public class LoginFilter implements Filter {
	
	TicketDao ticketDao;
	UserDao userDao;
	HostHolder hostHolder;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	ticketDao=MybatisUtil.getClass(TicketDao.class);
    	userDao=MybatisUtil.getClass(UserDao.class);
    	
    	//拿到提前放到ServletContext中的hostHolder对象
    	ServletContext context=filterConfig.getServletContext();
    	hostHolder=(HostHolder) context.getAttribute("hostHolder");

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	Ticket ticket = null;
    	HttpServletRequest req=(HttpServletRequest) request;
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("ticket")) {
					ticket = ticketDao.getTicketByTicket(c.getValue());
				}
			}
		}
		//如果cookie中不含有ticket或ticket已过期直接返回
		if (ticket == null || ticket.getExpired().before(new Date()) || ticket.getStaus() != 0) {
			chain.doFilter(request, response);
			return;
		}
		//如果拿到ticket，则说明用户已登录，将user对象添加到ServletContext
		User user = userDao.getUserById(ticket.getUserId());
		hostHolder.setUser(user);
		request.getServletContext().setAttribute("user", user);
        
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {


    }

}
