package cn.henau.cms.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.henau.cms.annotation.Component;
import cn.henau.cms.dao.VideoDao;
import cn.henau.cms.model.Video;
import cn.henau.cms.utils.MybatisUtil;

@Component
public class VideoService {
	
	VideoDao videoDao=MybatisUtil.getClass(VideoDao.class);
	
	public Video getVideoById(int id) {
		return videoDao.getVideoById(id);
	}
	public List<Video> getVideoByCourse(int courseId){
		return videoDao.getVideoByCourse(courseId);
	}
	
	public void addVideo(Video video) {
		SqlSession session = MybatisUtil.getSession();
		VideoDao dao = session.getMapper(VideoDao.class);
		dao.addVideo(video);
		session.commit();
	}

}
