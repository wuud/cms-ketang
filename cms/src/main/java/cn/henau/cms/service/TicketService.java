package cn.henau.cms.service;

import org.apache.ibatis.session.SqlSession;

import cn.henau.cms.annotation.Component;
import cn.henau.cms.dao.TicketDao;
import cn.henau.cms.model.Ticket;
import cn.henau.cms.utils.MybatisUtil;

@Component
public class TicketService {
	
	public void insertTicket(Ticket ticket) {
		SqlSession session=MybatisUtil.getSession();
		TicketDao dao = session.getMapper(TicketDao.class);
		dao.insertTicket(ticket);
		session.commit();
		session.close();
	}

    public Ticket getTicketById(int id) {
    	SqlSession session=MybatisUtil.getSession();
		TicketDao dao = session.getMapper(TicketDao.class);
		Ticket ticket = dao.getTicketById(id);
		session.close();
		return ticket;
    }

    public Ticket getTicketByTicket(String Ticket) {
    	SqlSession session=MybatisUtil.getSession();
		TicketDao dao = session.getMapper(TicketDao.class);
		Ticket ticket = dao.getTicketByTicket(Ticket);
		session.close();
		return ticket;
    }

    public void updateTicket(Ticket ticket) {
    	SqlSession session=MybatisUtil.getSession();
		TicketDao dao = session.getMapper(TicketDao.class);
		dao.updateTicket(ticket);
		session.commit();
    }

}
