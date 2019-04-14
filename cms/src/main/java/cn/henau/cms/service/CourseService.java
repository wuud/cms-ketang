package cn.henau.cms.service;

import java.util.List;

import cn.henau.cms.dao.CourseDao;
import cn.henau.cms.model.Course;
import cn.henau.cms.utils.MybatisUtil;

public class CourseService {

    CourseDao courseDao = MybatisUtil.getSession(CourseDao.class);


    public void insertCourse(Course c) {
        courseDao.insertCourse(c);
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

}
