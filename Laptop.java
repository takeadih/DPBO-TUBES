package Class;

public class Laptop extends Gadget {

    public Laptop(String brand, String condition, long price) {
        super(brand, condition, price);
    }

    @Override
    public void displayInfo() {
        System.out.println("Laptop | Merek: " + getBrand() + " | Kondisi: " + getCondition() + " | Harga: " + getPrice());
    }
}

