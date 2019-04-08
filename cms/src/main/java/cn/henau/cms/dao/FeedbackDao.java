package cn.henau.cms.dao;

import java.util.List;

import cn.henau.cms.model.Feedback;


public interface FeedbackDao {

    void addFeedback(Feedback feedback);

    List<Feedback> getAllFeedback();

}
