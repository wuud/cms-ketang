package cn.henau.cms.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.henau.cms.annotation.Component;
import cn.henau.cms.dao.CourseDao;
import cn.henau.cms.model.Course;
import cn.henau.cms.utils.MybatisUtil;

@Component
public class CourseService {

    CourseDao courseDao = MybatisUtil.getClass(CourseDao.class);

    public void insertCourse(Course c) {
    	SqlSession session = MybatisUtil.getSession();
    	CourseDao dao = session.getMapper(CourseDao.class);
        dao.insertCourse(c);
        session.commit();
    }

    public List<Course> getCourseByPage(int page, int pageSize) {
        int offsetNum = page * pageSize;
        return courseDao.getCourseByPage(pageSize, offsetNum);
    }

    public Course getCourseById(int id) {
        return courseDao.getCourseById(id);

    }

    public Course getCourseByName(String name) {
        return courseDao.getCourseByName(name);
    }

    public List<Course> getCourseByUser(int userId) {
        return courseDao.getCourseByUser(userId);

    }

    public List<Course> getAllCourse() {
        return courseDao.getAllCourse();
    }
    
    public Integer countAllCourse() {
    	return courseDao.countAllCourse();
    }

}
