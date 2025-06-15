package Class;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GadgetStore {
    private List<Gadget> gadgets = new ArrayList<>();

    public Gadget addGadget(String type, String brand, String condition, long price) throws InvalidGadgetTypeException {
        Gadget gadget = createGadget(type, brand, condition, price);
        gadgets.add(gadget);
        return gadget;
    }

    public Gadget createGadget(String type, String brand, String condition, long price) throws InvalidGadgetTypeException {
        if (type.equalsIgnoreCase("Smartphone")) {
            return new Smartphone(brand, condition, price);
        } else if (type.equalsIgnoreCase("Laptop")) {
            return new Laptop(brand, condition, price);
        } else {
            throw new InvalidGadgetTypeException("Tipe gadget tidak valid: " + type);
        }
    }

    public Gadget buyGadget(int index) {
        if (index >= 0 && index < gadgets.size()) {
            return gadgets.remove(index);
        }
        return null;
    }

    public List<Gadget> getAllGadgets() {
        return new ArrayList<>(gadgets);
    }

    public List<Gadget> filterByPrice(long min, long max) {
        List<Gadget> filtered = new ArrayList<>();
        for (Gadget g : gadgets) {
            if (g.getPrice() >= min && g.getPrice() <= max) {
                filtered.add(g);
            }
        }
        return filtered;
    }

    public List<Gadget> filterByCondition(Component parent) {
        String[] kondisiOptions = {"Baru", "Baik Sekali", "Baik", "Kurang Baik", "Buruk"};
        String condition = (String) JOptionPane.showInputDialog(parent, "Pilih kondisi:",
                "Filter Kondisi", JOptionPane.QUESTION_MESSAGE, null, kondisiOptions, kondisiOptions[0]);

        if (condition == null) return gadgets;

        List<Gadget> filtered = new ArrayList<>();
        for (Gadget g : gadgets) {
            if (g.getCondition().equalsIgnoreCase(condition)) {
                filtered.add(g);
            }
        }
        return filtered;
    }

    public List<String> getAllBrands() {
        List<String> brands = new ArrayList<>();
        for (Gadget g : gadgets) {
            String brand = g.getBrand();
            if (!brands.contains(brand)) {
                brands.add(brand);
            }
        }
        return brands;
    }

    public List<Gadget> filterByBrand(String brand) {
        List<Gadget> filtered = new ArrayList<>();
        for (Gadget g : gadgets) {
            if (g.getBrand().equalsIgnoreCase(brand)) {
                filtered.add(g);
            }
        }
        return filtered;
    }

    public String askBrandByType(Component parent, String type) {
        String[] smartphoneBrands = {"Samsung", "Oppo", "Xiaomi", "Apple", "Realme", "Vivo"};
        String[] laptopBrands = {"Asus", "Acer", "Lenovo", "HP", "Dell", "MSI"};
        String[] brandOptions;

        if (type.equalsIgnoreCase("Smartphone")) {
            brandOptions = smartphoneBrands;
        } else {
            brandOptions = laptopBrands;
        }

        JComboBox<String> brandComboBox = new JComboBox<>(brandOptions);
        int result = JOptionPane.showConfirmDialog(parent, brandComboBox, "Pilih Merek", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) return null;
        return (String) brandComboBox.getSelectedItem();
    }

    public String askCondition(Component parent) {
        String[] kondisiOptions = {"Baru", "Baik Sekali", "Baik", "Kurang Baik", "Buruk"};
        return (String) JOptionPane.showInputDialog(parent, "Pilih kondisi:", "Kondisi",
                JOptionPane.QUESTION_MESSAGE, null, kondisiOptions, kondisiOptions[0]);
    }
}
