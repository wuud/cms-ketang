package cn.henau.cms.dao;

import cn.henau.cms.model.Ticket;

public interface TicketDao {

    void insertTicket(Ticket ticket);

    Ticket getTicketById(int id);

    Ticket getTicketByTicket(String Ticket);

    void updateTicket(Ticket ticket);

}

