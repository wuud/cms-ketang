package cn.henau.cms.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.henau.cms.constants.Constants;
import cn.henau.cms.vo.ProjectCountVO;

@WebServlet(value = { "/projectCount" })
public class LocalInvokeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getRequestURI().equals("/cms/projectCount")) {
			projectCount(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}

	private void projectCount(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Runtime rt = Runtime.getRuntime();
		Process exec = rt.exec(Constants.CLOC_PATH+" "+Constants.PROJECT_ROOT_PATH);
		InputStream is = exec.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		int count = 0;
		List<ProjectCountVO> VOList=new ArrayList<>();
		while ((line = br.readLine()) != null) {
			count++;
			if (count > 16) {
				line=line.replaceAll("\\s{2,}", "-");
				String[] split = line.split("-");
				if(split.length<=1)
					break;
				VOList.add(set(split));
			}
		}
		req.setAttribute("VOList", VOList);
		req.getRequestDispatcher("/WEB-INF/jsp/projectCount.jsp").forward(req, resp);
	}
	
	private ProjectCountVO set(String[] strs) {
		ProjectCountVO pc=new ProjectCountVO();
		pc.setLanguage(strs[0]);
		pc.setFiles(strs[1]);
		pc.setBlank(strs[2]);
		pc.setComment(strs[3]);
		pc.setCode(strs[4]);
		System.out.println();
		return pc;
	}

}
