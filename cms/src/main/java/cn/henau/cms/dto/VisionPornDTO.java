package cn.henau.cms.dto;

public class VisionPornDTO {
	
	private int ret;
	
	private String msg;
	
	private Object data;

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "VisionPornDTO [ret=" + ret + ", msg=" + msg + ", data=" + data + "]";
	}
	
	

}
