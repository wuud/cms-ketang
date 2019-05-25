package cn.henau.cms.vo;

/**
 *代码统计软件输出传输对象
 */
public class ProjectCountVO {
	
	private String language;
	
	private String files;
	
	private String blank;
	
	private String comment;
	
	private String code;

	public String getLanguage() {
		return language;
	}

	@Override
	public String toString() {
		return "ProjectCountVO [language=" + language + ", files=" + files + ", blank=" + blank + ", comment=" + comment
				+ ", code=" + code + "]";
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}

	public String getBlank() {
		return blank;
	}

	public void setBlank(String blank) {
		this.blank = blank;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

}
