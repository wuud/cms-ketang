package cn.henau.cms.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.henau.cms.service.LoginService;

/**
 * <li>login 既可以GET方式，也可以POST方式
 * <li>reg只能POST
 * <li>logout只能GET
 *
 */
@WebServlet(value = {"/login", "/doLogin", "/reg", "/logout"})
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    LoginService loginService;

    @Override
    public void init() throws ServletException {
        loginService = new LoginService();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().equals("/cms/login")) {
            login(req, resp);
        } else if (req.getRequestURI().equals("/cms/logout")) {
            logout(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().equals("/cms/doLogin")) {
            doLogin(req, resp);
        } else if (req.getRequestURI().equals("/cms/reg")) {
            reg(req, resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String number = req.getParameter("number");
        String password = req.getParameter("password");
        String rememberme = req.getParameter("rememberme");

        Map<String, String> map = loginService.login(req,number, password, rememberme);

        if (map.containsKey("error")) {
            req.setAttribute("error", map.get("error"));
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
        if (map.containsKey("ticket")) {
            String ticket = map.get("ticket");
            Cookie cookie = new Cookie("ticket", ticket);
            cookie.setPath("/");
            resp.addCookie(cookie);
            resp.sendRedirect("/cms/index");
        } else {
            req.setAttribute("error", "登录时发生异常！");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }

    }

    private void reg(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");

        Map<String, String> map = loginService.reg(username, password, phone, email);
        if (map.containsKey("error")) {
            req.setAttribute("error", map.get("error"));
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
        if (map.containsKey("ticket")) {
            String ticket = map.get("ticket");
            Cookie cookie = new Cookie("ticket", ticket);
            cookie.setPath("/");
            resp.addCookie(cookie);
        } else {
            req.setAttribute("error", "登录时发生异常！");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }
        resp.sendRedirect("/cms/index");
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("ticket")) {
                loginService.logout(cookie.getValue());
            }
        }
        resp.sendRedirect("/cms/");
    }

}
