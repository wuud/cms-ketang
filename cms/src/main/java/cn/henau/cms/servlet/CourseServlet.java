package cn.henau.cms.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

import cn.henau.cms.constants.Constants;
import cn.henau.cms.dao.CourseDao;
import cn.henau.cms.dao.VideoDao;
import cn.henau.cms.dto.TagDTO;
import cn.henau.cms.model.Course;
import cn.henau.cms.model.Video;
import cn.henau.cms.service.CourseService;
import cn.henau.cms.service.HostHolder;
import cn.henau.cms.service.JoinCourseService;
import cn.henau.cms.service.SensitiveWordService;
import cn.henau.cms.service.TencentAiService;
import cn.henau.cms.service.UserService;
import cn.henau.cms.service.VideoService;
import cn.henau.cms.utils.JsonUtil;
import cn.henau.cms.utils.MybatisUtil;

@WebServlet(value = { "/allCourse", "/course/*", "/addCourse", "/course/download/*" })
public class CourseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private CourseService courseService;
	private UserService userService;
	private HostHolder hostHolder;
	private JoinCourseService joinCourseService;
	private VideoService videoService;
	private SensitiveWordService sensitiveWordService;
	private TencentAiService tencentAiService;

	@Override
	public void init() throws ServletException {
		courseService = (CourseService) getServletContext().getAttribute("CourseService");
		userService = (UserService) getServletContext().getAttribute("UserService");
		videoService = (VideoService) getServletContext().getAttribute("VideoService");
		joinCourseService = (JoinCourseService) getServletContext().getAttribute("JoinCourseService");
		hostHolder = (HostHolder) getServletContext().getAttribute("HostHolder");
		sensitiveWordService = (SensitiveWordService) getServletContext().getAttribute("SensitiveWordService");
		tencentAiService=(TencentAiService) getServletContext().getAttribute("TencentAiService");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getRequestURI().equals("/cms/allCourse")) {
			allCourse(req, resp);
		} else if (req.getRequestURI().startsWith("/cms/course/download")) {
			String uri = req.getRequestURI();
			System.out.println(uri);
			int courseId = Integer.parseInt(uri.substring(uri.lastIndexOf('/') + 1));
			downCourseFile(courseId, req, resp);
		} else if (req.getRequestURI().startsWith("/cms/course/")) {
			String uri = req.getRequestURI();
			int courseId = Integer.parseInt(uri.substring(uri.lastIndexOf('/') + 1));
			courseDetail(req, resp, courseId);
		} else if (req.getRequestURI().equals("/cms/addCourse")) {
			addCoursePage(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getRequestURI().equals("/cms/addCourse")) {
			addCourse(req, resp);

		}
	}

	/**
	 * 课程详情页
	 */
	public void courseDetail(HttpServletRequest req, HttpServletResponse resp, int courseId)
			throws ServletException, IOException {
		int userId = hostHolder.getUser().getId();
		Course course = courseService.getCourseById(courseId);
		req.setAttribute("course", course);
		boolean isJoin = joinCourseService.isJoin(courseId, userId);
		req.setAttribute("isJoin", isJoin);
		req.getRequestDispatcher("/WEB-INF/jsp/courseDetail.jsp").forward(req, resp);
	}

	/**
	 * 所有课程页面
	 */
	public void allCourse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer page = 0;
		if (!StringUtils.isBlank(req.getParameter("page"))) {
			page = Integer.parseInt(req.getParameter("page"));
		}

		List<Course> courseList = courseService.getCourseByPage(page, Constants.COURSE_PAGE_SIZE);
		Integer courseNum = courseService.countAllCourse();

		userService.pageControl(courseNum, req, page, Constants.COURSE_PAGE_SIZE);

		req.setAttribute("courseList", courseList);
		req.getRequestDispatcher("/WEB-INF/jsp/allCourse.jsp").forward(req, resp);
	}

	/**
	 * 接收上传的文件，存储到本地
	 * 
	 * @param req
	 * @param resp
	 */
	public void addCourse(HttpServletRequest req, HttpServletResponse resp) {

		ServletOutputStream outputStream = null;
		InputStream in = null;
		FileOutputStream out = null;
		DiskFileItemFactory factory = null;
		ServletFileUpload upload = null;

		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("content-type", "text/html;charset=UTF-8");
		try {
			outputStream=resp.getOutputStream();
			factory = new DiskFileItemFactory();
			upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			if (!ServletFileUpload.isMultipartContent(req)) {
				outputStream.write("请求有误！".getBytes("UTF-8"));
				return;
			}
			String courseName = "";
			String courseIntro = "";
			String coursePicture = "";
			String courseFile = "";
			List<Video> videoList = new ArrayList<>();
			List<FileItem> list = upload.parseRequest(req);
			for (FileItem item : list) {
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					switch (name) {
					case "courseName":
						courseName = value;
						break;

					case "courseIntro":
						courseIntro = value;
						break;
					}
				} else {
					String fileName = item.getName();
					String fieldName = item.getFieldName();

					if (StringUtils.isBlank(fileName)) {
						continue;
					}
					String relativePath = "/"+courseName + "/" + fieldName + "/" + fileName;
					String path = Constants.UPLOAD_FILE_PATH + relativePath;
					File file = new File(path);
					if (!file.exists()) {
						file.getParentFile().mkdirs();
					}
					out = new FileOutputStream(file);

					in = item.getInputStream();
					byte buffer[] = new byte[1024];
					int len = 0;
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					switch (fieldName) {
					case "coursePicture":
						coursePicture = relativePath;
						break;
					case "courseFile":
						courseFile = relativePath;
						break;
					case "courseVideo":
						long videoSize = item.getSize();
						Double videoSizeM = videoSize / (1024 * 1024.0);
						videoList.add(new Video(fileName, null, relativePath, videoSizeM));
						break;
					}

				}
			}
			// 对课程名字和课程封面进行校验
			Map<String, String> filter = sensitiveWordService.filter(courseName + courseIntro);
			if (filter.containsKey("error")) {
				outputStream.write(JsonUtil.getJSONString(400, filter.get("error")).getBytes("UTF-8"));
				return;
			}
			
			// 调用腾讯SDK，校验图片
			List<TagDTO> tagList = tencentAiService.visionPornImg(Constants.UPLOAD_FILE_PATH+coursePicture);
			String data = tencentAiService.parseData(tagList);
			if(!data.equals("正常")) {
				outputStream.write(JsonUtil.getJSONString(401, data).getBytes());
				return;
			}

			insertCourse(courseName, courseIntro, coursePicture, courseFile, videoList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			outputStream.write("success".getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将课程视频信息插入数据库
	 * 
	 * @param picture     课程封面
	 * @param file        课程资料
	 * @param videos      课程视频
	 * @param userName    课程发布者
	 * @param courseName  课程名称
	 * @param courseIntro 课程介绍
	 */
	public void insertCourse(String courseName, String courseIntro, String coursePicture, String courseFile,
			List<Video> videoList) {
		try {
			// 开启事务
			SqlSession session = MybatisUtil.getSession();
			CourseDao courseDao = session.getMapper(CourseDao.class);
			VideoDao videoDao = session.getMapper(VideoDao.class);
			// 得到所有数据后，先插入课程，再插入视频
			Course c = new Course(courseName, hostHolder.getUser(), courseIntro, coursePicture, courseFile);
			courseDao.insertCourse(c);
			for (Video v : videoList) {
				v.setCourse(c);
				videoDao.addVideo(v);
			}
			// 事务提交
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCoursePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/addCourse.jsp").forward(req, resp);
	}

	/**
	 * 文件下载
	 */
	public void downCourseFile(Integer courseId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String contentType = "application/octet-stream";
		String enc = "UTF-8";
//		String filePath = "E:/sts-bundle/newWorkSpace/cms/src/main/webapp"
//				+ courseService.getCourseById(courseId).getCourseFile();
		String filePath = "E:\\sts-bundle\\newWorkSpace\\cms\\src\\main\\webapp\\upload\\Spring\\courseFile\\test.txt";
		System.out.println(filePath);
		String fileName = req.getParameter("fileName");

		ServletOutputStream servletOutputStream = null;
		BufferedInputStream bufferedInputStream = null;
		System.out.println("this is downCourseFile");
		try {
			File downloadFile = new File(filePath);
			System.out.println(downloadFile.exists());
			if (downloadFile.exists()) {
				resp.setContentType(contentType);
				Long length = downloadFile.length();
				resp.setContentLength(length.intValue());
				fileName = URLEncoder.encode(downloadFile.getName(), enc);
				resp.addHeader("Content-Disposition", "attachment; filename=" + fileName);

				servletOutputStream = resp.getOutputStream();
				FileInputStream fileInputStream = new FileInputStream(downloadFile);
				bufferedInputStream = new BufferedInputStream(fileInputStream);
				int size = 0;
				byte[] b = new byte[4096];
				while ((size = bufferedInputStream.read(b)) != -1) {
					servletOutputStream.write(b, 0, size);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (servletOutputStream != null) {
				servletOutputStream.flush();
				servletOutputStream.close();
			}
			if (bufferedInputStream != null) {
				bufferedInputStream.close();
			}
		}

	}
}
