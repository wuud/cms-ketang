package cn.henau.cms.model;

import java.util.Date;

public class Ticket {

    @Override
	public String toString() {
		return "Ticket [id=" + id + ", userId=" + userId + ", status=" + status + ", ticket=" + ticket + ", expired="
				+ expired + "]";
	}

	private int id;
    private int userId;

    private int status;
    private String ticket;
    private Date expired;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStaus() {
        return status;
    }

    public void setStaus(int staus) {
        this.status = staus;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

}
