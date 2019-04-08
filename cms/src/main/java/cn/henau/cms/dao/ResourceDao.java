package cn.henau.cms.dao;

import java.util.List;

import cn.henau.cms.model.Resource;

public interface ResourceDao {

    void insertResource(Resource res);

    Integer countResourceByPath(String path);

    void updateResource(Resource res);

    List<Resource> getAllResource();

}
