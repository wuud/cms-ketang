package cn.henau.cms.service;

import java.util.List;

import cn.henau.cms.annotation.Component;
import cn.henau.cms.dao.RoleDao;
import cn.henau.cms.model.Role;
import cn.henau.cms.utils.MybatisUtil;


@Component
public class RoleService {
	
	RoleDao roleDao=MybatisUtil.getClass(RoleDao.class);
	
	public List<Role> getAllRole(){
		return roleDao.getAllRole();
	}
	public Role getRoleById(int id) {
		return roleDao.getRoleById(id);
	}
	public Role getRoleByName(String name) {
		return roleDao.getRoleByName(name);
	}

}
