package cn.henau.cms.test;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import cn.henau.cms.dao.TicketDao;
import cn.henau.cms.model.Ticket;
import cn.henau.cms.utils.MybatisUtil;

public class CmsTest {
	
	@Test
	public void test() {
		Ticket ticket=new Ticket();
		ticket.setUserId(23);
		ticket.setStaus(0);
		ticket.setTicket("asdfsfdsf");
		ticket.setExpired(new Date());
		SqlSession session = MybatisUtil.getSession();
		TicketDao ticketDao = session.getMapper(TicketDao.class);
		ticketDao.insertTicket(ticket);
		session.commit();
	}

}
