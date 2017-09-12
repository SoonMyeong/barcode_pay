package product_model;

public class user_review {
	
	private String user_name, user_review, created,
	user_number, check_user, barcode_num;
	private int user_score;

	
	public user_review(){}
	public user_review(String user_name, String user_review, 
			String created, String user_number, String check_user, String barcode_num, int user_score) {
		this.user_name = user_name;
		this.user_review = user_review;
		this.created = created;
		this.user_number = user_number;
		this.check_user = check_user;
		this.barcode_num = barcode_num;
		this.user_score = user_score;
	}
	
	public String getuser_name() {
		return user_name;
	}
	
	public void setuser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getuser_review() {
		return user_review;
	}
	
	public void setuser_review(String user_review) {
		this.user_review =user_review;
	}
	
	public String getcreate() {
		return created;
	}
	
	public void setcreate(String created) {
		this.created = created;
	}	
	
	public String getuser_number() {
		return user_number;
	}
	public void setuser_number(String user_number) {
		this.user_number = user_number;
	}
	public String getcheck_user() {
		return check_user;
	}
	public void setcheck_user(String check_user) {
		this.check_user = check_user;
	}
	public String getbarcode_num() {
		return barcode_num;
	}
	public void setbarcode_num(String barcode_num) {
		this.barcode_num = barcode_num;
	}
	public int getuser_score() {
		return user_score;
	}
	public void setuser_score(int user_score) {
		this.user_score = user_score;
	}
	
}
