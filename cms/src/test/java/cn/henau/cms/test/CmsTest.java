package cn.henau.cms.test;

import java.util.Date;

import org.junit.Test;

import cn.henau.cms.model.Role;
import cn.henau.cms.model.User;
import cn.henau.cms.service.UserService;

public class CmsTest {
	
	@Test
	public void test() {
		UserService userService=new UserService();
		userService.insertUser(new User("user"+1, "we", "sf", "1243", 0, new Date(), new Role()));
	}

}
