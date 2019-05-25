package cn.henau.cms.dao;

import java.util.List;

import cn.henau.cms.model.Video;

public interface VideoDao {

    Video getVideoById(int id);

    List<Video> getVideoByCourse(String courseName);
    
    List<Video> getAllVideo();

    void addVideo(Video video);


}
