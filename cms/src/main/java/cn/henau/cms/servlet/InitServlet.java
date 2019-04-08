package cn.henau.cms.servlet;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import cn.henau.cms.annotation.Component;
import cn.henau.cms.model.Resource;

public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final String[] packagePaths = { "cn/henau/cms/service", "cn/henau/cms/filter" };

	@Override
	public void init() throws ServletException {
		// 初始化ServletContext
		ServletContext sc = getServletContext();
		
		// 遍历所有包路径
		for (String packagePath : packagePaths) {
			// 拿到要进行权限控制的包的路径
			String packageRealPath = this.getClass().getClassLoader().getResource(packagePath).getPath().substring(1);
			File file = new File(packageRealPath);
			// 遍历文件夹下所有文件，得到所有 .class的文件
			String[] classFilesName = file.list(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if (name.endsWith(".class")) {
						return true;
					}
					return false;
				}
			});

			for (String classFileName : classFilesName) {
				try {
					classFileName = classFileName.substring(0, classFileName.indexOf(".class"));

					String packageName = packagePath.replaceAll("/", ".");
					// 拿到纯粹的类的包全名
					String classAllpackageName = packageName + "." + classFileName;
					// 通过反射获取到对应的类的对象
					Class clazz = Class.forName(classAllpackageName);
					// 如果当前类没有AuthClass注解，直接跳出当前循环
					if (!clazz.isAnnotationPresent(Component.class))
						continue;
					else {
						Object newInstance;
						newInstance = clazz.newInstance();
						String className=clazz.getName().substring(clazz.getName().lastIndexOf('.')+1);
						sc.setAttribute(className, newInstance);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
