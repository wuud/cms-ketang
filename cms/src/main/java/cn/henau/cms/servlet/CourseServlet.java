package cn.henau.cms.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import cn.henau.cms.constants.PageConstant;
import cn.henau.cms.model.Course;
import cn.henau.cms.model.Video;
import cn.henau.cms.service.CourseService;
import cn.henau.cms.service.HostHolder;
import cn.henau.cms.service.JoinCourseService;
import cn.henau.cms.service.UserService;
import cn.henau.cms.service.VideoService;

@WebServlet(value = { "/allCourse", "/course/*", "/addCourse", "/course/download/*" })
public class CourseServlet extends HttpServlet {

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
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getRequestURI().equals("/cms/allCourse")) {
			allCourse(req, resp);
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
		hostHolder = (HostHolder) getServletContext().getAttribute("HostHolder");
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

		List<Course> courseList = courseService.getCourseByPage(page, PageConstant.COURSE_PAGE_SIZE);
		Integer courseNum = courseService.countAllCourse();

		userService.pageControl(courseNum, req, page, PageConstant.COURSE_PAGE_SIZE);

		req.setAttribute("courseList", courseList);
		req.getRequestDispatcher("/WEB-INF/jsp/allCourse.jsp").forward(req, resp);
	}

	/**
	 * 课程上传
	 * 
	 * @param picture     课程封面
	 * @param file        课程资料
	 * @param videos      课程视频
	 * @param userName    课程发布者
	 * @param courseName  课程名称
	 * @param courseIntro 课程介绍
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public void addCourse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String basePath = "E:/sts-bundle/newWorkSpace/cms/src/main/webapp";

		InputStream in = null;
		FileOutputStream out = null;

		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			if (!ServletFileUpload.isMultipartContent(req)) {
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
					String relativePath = "/upload" + "/" + courseName + "/" + fieldName + "/" + fileName;
					String path = basePath + relativePath;
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
			// 得到所有数据后，先插入课程，再插入视频
			Course c = new Course(courseName, hostHolder.getUser(), courseIntro, coursePicture, courseFile);
			courseService.insertCourse(c);
			Course course = courseService.getCourseByName(courseName);
			for (Video v : videoList) {
				v.setCourse(course);
				videoService.addVideo(v);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
		resp.sendRedirect("/cms/index");
	}

	public void addCoursePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/addCourse.jsp").forward(req, resp);
	}

	/**
	 * 文件下载
	 */
//	public ResponseEntity<byte[]> downCourseFile(@PathVariable("courseId") int courseId, HttpServletRequest request)
//			throws IOException {
//		// 要下载的文件所在目录的绝对路径
//		Course course = courseService.getCourseById(courseId);
//		String realPath = request.getRealPath("");
//		String filePath = realPath + course.getCourseFile();
//		File file = new File(filePath);
//		HttpHeaders hh = new HttpHeaders();
//		// 解决中文乱码问题
//		String downloadFilename = new String(course.getCourseFile().getBytes("utf-8"), "iso-8859-1");
//		// 通知浏览器下载方式为attachment
//		hh.setContentDispositionFormData("attachment", downloadFilename);
//		// application/octet-stream二进制流数据下载
//		hh.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), hh, HttpStatus.CREATED);
//	}

}
