package product_model;

public class user_product {
	
	private String food_name, food_image, barcode_image, barcode_number;
	private int food_price;

	
	public user_product(){}
	public user_product(String food_name, String food_image,String food_barcode_image, String food_barcode_number, int food_price) {
		this.food_name = food_name;
		this.food_image = food_image;
		this.barcode_image = food_barcode_image;
		this.barcode_number= food_barcode_number;
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
	
	public String getbarcode_image() {
		return barcode_image;
	}
	
	public void setbarcode_image(String food_barcode_image) {
		this.barcode_image = food_barcode_image;
	}
	
	public String getbarcode_number() {
		return barcode_number;
	}
	
	public void setbarcode_number(String food_barcode_number) {
		this.barcode_number = food_barcode_number;
	}
	
	public int getfood_price() {
		return food_price;
	}
	
	public void setfood_price(int price) {
		this.food_price = price;
	}	
}
