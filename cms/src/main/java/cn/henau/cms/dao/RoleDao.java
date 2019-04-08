package cn.henau.cms.dao;

import java.util.List;

import cn.henau.cms.model.Role;

public interface RoleDao {

    public List<Role> getAllRole();

    public Role getRoleById(int id);

    public Role getRoleByName(String name);
}
