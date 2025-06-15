package Class;

public class Smartphone extends Gadget {

    public Smartphone(String brand, String condition, long price) {
        super(brand, condition, price);
    }

    @Override
    public void displayInfo() {
        System.out.println("Smartphone | Merek: " + getBrand() + " | Kondisi: " + getCondition() + " | Harga: " + getPrice());
    }
}