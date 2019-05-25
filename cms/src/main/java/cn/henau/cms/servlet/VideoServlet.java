package cn.henau.cms.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.henau.cms.model.Course;
import cn.henau.cms.model.Video;
import cn.henau.cms.service.CourseService;
import cn.henau.cms.service.HostHolder;
import cn.henau.cms.service.JoinCourseService;
import cn.henau.cms.service.UserService;
import cn.henau.cms.service.VideoService;

@WebServlet(value = { "/course/videoList/*", "/course/video/*" })
public class VideoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private CourseService courseService;
	private UserService userService;
	private HostHolder hostHolder;
	private JoinCourseService joinCourseService;
	private VideoService videoService;

	@Override
	public void init() throws ServletException {
		courseService = (CourseService) getServletContext().getAttribute("CourseService");
		userService = (UserService) getServletContext().getAttribute("UserService");
		videoService = (VideoService) getServletContext().getAttribute("VideoService");
		joinCourseService = (JoinCourseService) getServletContext().getAttribute("JoinCourseService");
		hostHolder = (HostHolder) getServletContext().getAttribute("HostHolder");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getRequestURI().startsWith("/cms/course/videoList/")) {
			String uri = req.getRequestURI();
			int courseId = Integer.parseInt(uri.substring(uri.lastIndexOf('/') + 1));
			videoList(req, resp, courseId);
		} else if (req.getRequestURI().startsWith("/cms/course/video/")) {
			String uri = req.getRequestURI();
			String iid = uri.substring(uri.lastIndexOf('/') + 1);
			String[] ids = iid.split("_");
			Integer courseId = Integer.parseInt(ids[0]);
			Integer videoId = Integer.parseInt(ids[1]);
			coursevideo(req, resp, courseId, videoId);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}

	public void coursevideo(HttpServletRequest req, HttpServletResponse resp, Integer courseId, Integer videoId)
			throws ServletException, IOException {
		Course course = courseService.getCourseById(courseId);
		Video video = videoService.getVideoById(videoId);
		course.setPicture(course.getPicture().replace("\\", "/"));
		req.setAttribute("course", course);
		req.setAttribute("video", video);
		req.getRequestDispatcher("/WEB-INF/jsp/video.jsp").forward(req, resp);
	}

	public void videoList(HttpServletRequest req, HttpServletResponse resp, Integer courseId)
			throws ServletException, IOException {
		Course course=courseService.getCourseById(courseId);
		List<Video> videoByCourse = videoService.getVideoByCourse(course.getCname());
		req.setAttribute("videoList", videoByCourse);
		req.setAttribute("courseId", courseId);
		req.getRequestDispatcher("/WEB-INF/jsp/videoList.jsp").forward(req, resp);
	}

}
