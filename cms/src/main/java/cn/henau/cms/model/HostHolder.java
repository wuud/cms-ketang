package cn.henau.cms.model;

public class HostHolder {

    private ThreadLocal<User> tl = new ThreadLocal<>();

    public void setUser(User u) {
        tl.set(u);
    }

    public User getUser() {
        return tl.get();
    }

    public void clear() {
        tl.remove();
    }

}
