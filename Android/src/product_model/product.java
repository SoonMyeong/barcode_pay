package product_model;

public class product {
	
	private String food_name, food_image;
	private int food_price;

	
	public product(){}
	public product(String food_name, String food_image, int food_price) {
		this.food_name = food_name;
		this.food_image = food_image;
		this.food_price = food_price;
	}
	
	public String getfood_name() {
		return food_name;
	}
	
	public void setfood_name(String food_name) {
		this.food_name = food_name;
	}
	public String getfood_image() {
		return food_image;
	}
	
	public void setfood_image(String food_image) {
		this.food_image = food_image;
	}
	
	public int getfood_price() {
		return food_price;
	}
	
	public void setfood_price(int price) {
		this.food_price = price;
	}	
}
