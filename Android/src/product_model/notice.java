package product_model;

public class notice {

	
		private String notice_select, notice_title, notice_detail, notice_day;

	
	public notice(){}
	public notice(String notice_select, String notice_title,String notice_detail, String notice_day) {
		this.notice_select = notice_select;
		this.notice_title = notice_title;
		this.notice_detail = notice_detail;
		this.notice_day= notice_day;
	}
	
	public String getnotice_select() {
		return notice_select;
	}
	
	public void setnotice_select(String notice_select) {
		this.notice_select = notice_select;
	}
	public String getnotice_title() {
		return notice_title;
	}
	
	public void setnotice_title(String notice_title) {
		this.notice_title = notice_title;
	}
	
	public String getnotice_detail() {
		return notice_detail;
	}
	
	public void setnotice_detail(String notice_detail) {
		this.notice_detail = notice_detail;
	}
	
	public String getnotice_day() {
		return notice_day;
	}
	
	public void setnotice_day(String notice_day) {
		this.notice_day = notice_day;
	}
}
