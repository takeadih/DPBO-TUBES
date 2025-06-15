package Class;

public abstract class Gadget implements IGadget {
	 private String brand;
	    private String condition;
	    private long price;

	    public Gadget(String brand, String condition, long price) {
	        this.brand = brand;
	        this.condition = condition;
	        this.price = price;
	    }

	    public String getBrand() {
	        return brand;
	    }

	    public String getCondition() {
	        return condition;
	    }

	    public long getPrice() {
	        return price;
	    }

	    public void setBrand(String brand) {
	        this.brand = brand;
	    }

	    public void setCondition(String condition) {
	        this.condition = condition;
	    }

	    public void setPrice(long price) {
	        this.price = price;
	    }

	    public abstract void displayInfo();	
}
