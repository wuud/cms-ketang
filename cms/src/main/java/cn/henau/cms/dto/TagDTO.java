package cn.henau.cms.dto;


public class TagDTO {
    private String tag_name;

    private int tag_confidence;

    private double tag_confidence_f;
    

	@Override
	public String toString() {
		return "TagDTO [tag_name=" + tag_name + ", tag_confidence=" + tag_confidence + ", tag_confidence_f="
				+ tag_confidence_f + "]";
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public int getTag_confidence() {
		return tag_confidence;
	}

	public void setTag_confidence(int tag_confidence) {
		this.tag_confidence = tag_confidence;
	}

	public double getTag_confidence_f() {
		return tag_confidence_f;
	}

	public void setTag_confidence_f(double tag_confidence_f) {
		this.tag_confidence_f = tag_confidence_f;
	}
    
}
