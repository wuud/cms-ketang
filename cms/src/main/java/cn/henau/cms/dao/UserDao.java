package cn.henau.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.henau.cms.model.User;

public interface UserDao {

    User getUserById(int id);

    User getUserByPhone(String phone);

    User getUserByEmail(String email);

    User getUserByName(String name);

    void insertUser(User u);

    void delUser(int id);

    void updateUser(User u);

    Integer countAllUser();

    Integer countUserByRole(int roleId);


    List<User> searchUser(@Param("userInfo") String userInfo, @Param("pageSize") int pageSize, @Param("offsetNum") int offsetNum);

    List<User> getUsersByPage(@Param("pageSize") int pageSize, @Param("offsetNum") int offsetNum);

    List<User> getUsersByRole(@Param("roleId") int roleId, @Param("pageSize") int pageSize, @Param("offsetNum") int offsetNum);


}
