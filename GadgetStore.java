package Class;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GadgetStore {
    private ArrayList<Gadget> gadgets = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean running = true;
        while (running) {
            showMenu();
            System.out.print("Pilih opsi: ");
            int pilihan = -1;
            try {
                pilihan = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Input harus berupa angka.");
                scanner.nextLine();
                continue;
            }

            switch (pilihan) {
                case 1:
                    menuBeli();
                    break;
                case 2:
                    menuJual();
                    break;
                case 3:
                    tampilkanSemuaGadget();
                    break;
                case 4:
                    filterByBrand();
                    break;
                case 5:
                    filterByCondition();
                    break;
                case 6:
                    filterByPriceRange();
                    break;
                case 7:
                    System.out.println("Terima kasih telah menggunakan layanan kami!");
                    running = false;
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n===== MENU UTAMA =====");
        System.out.println("1. Beli Gadget");
        System.out.println("2. Jual Gadget");
        System.out.println("3. Tampilkan Semua Gadget");
        System.out.println("4. Filter Gadget Berdasarkan Merek");
        System.out.println("5. Filter Gadget Berdasarkan Kondisi");
        System.out.println("6. Filter Gadget Berdasarkan Range Harga");
        System.out.println("7. Keluar");
    }

    public void menuBeli() {
        if (gadgets.isEmpty()) {
            System.out.println("Maaf, belum ada gadget tersedia.");
            return;
        }

        System.out.println("\n--- Daftar Gadget yang Tersedia ---");
        for (int i = 0; i < gadgets.size(); i++) {
            System.out.print((i + 1) + ". ");
            gadgets.get(i).displayInfo();
        }

        System.out.print("Masukkan nomor gadget yang ingin dibeli (0 untuk batal): ");
        try {
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            if (pilihan == 0) {
                System.out.println("Pembelian dibatalkan.");
                return;
            }

            if (pilihan < 1 || pilihan > gadgets.size()) {
                System.out.println("Nomor tidak valid.");
                return;
            }

            Gadget dibeli = gadgets.remove(pilihan - 1);
            System.out.println("Anda telah membeli:");
            dibeli.displayInfo();

        } catch (InputMismatchException e) {
            System.out.println("Input harus berupa angka.");
            scanner.nextLine();
        }
    }


    public void menuJual() {
        try {
            System.out.println("\n--- Menu Jual ---");

            System.out.println("Pilih tipe gadget:");
            System.out.println("1. Smartphone");
            System.out.println("2. Laptop");
            System.out.print("Pilihan: ");
            int tipePilihan = scanner.nextInt();
            scanner.nextLine();

            String tipe;
            if (tipePilihan == 1) tipe = "Smartphone";
            else if (tipePilihan == 2) tipe = "Laptop";
            else throw new InvalidGadgetTypeException("Pilihan tipe tidak valid.");

            String[] smartphoneBrands = {"Samsung", "iPhone", "Vivo", "Oppo", "Xiaomi", "Redmi", "Poco", "Infinix", "Tecno", "Nokia"};
            String[] laptopBrands = {"ASUS", "HP", "Lenovo", "Acer", "Razer", "Dell"};

            String[] selectedBrands = tipe.equals("Smartphone") ? smartphoneBrands : laptopBrands;

            System.out.println("Pilih merek:");
            for (int i = 0; i < selectedBrands.length; i++) {
                System.out.println((i + 1) + ". " + selectedBrands[i]);
            }
            System.out.print("Pilihan: ");
            int brandIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            if (brandIndex < 0 || brandIndex >= selectedBrands.length)
                throw new IllegalArgumentException("Pilihan merek tidak valid.");
            String brand = selectedBrands[brandIndex];

            String[] conditions = {"Baru", "Baik Sekali", "Baik", "Kurang Baik", "Buruk"};
            System.out.println("Pilih kondisi:");
            for (int i = 0; i < conditions.length; i++) {
                System.out.println((i + 1) + ". " + conditions[i]);
            }
            System.out.print("Pilihan: ");
            int conditionIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            if (conditionIndex < 0 || conditionIndex >= conditions.length)
                throw new IllegalArgumentException("Pilihan kondisi tidak valid.");
            String condition = conditions[conditionIndex];

            System.out.print("Masukkan harga: ");
            long price = scanner.nextLong();
            scanner.nextLine();
            if (price < 0) throw new IllegalArgumentException("Harga tidak boleh negatif.");

            Gadget gadget;
            if (tipe.equals("Smartphone")) {
                gadget = new Smartphone(brand, condition, price);
            } else {
                gadget = new Laptop(brand, condition, price);
            }

            addGadget(gadget);
            System.out.println("Gadget berhasil ditambahkan.");
        } catch (InputMismatchException e) {
            System.out.println("Input harus berupa angka.");
            scanner.nextLine();
        } catch (InvalidGadgetTypeException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addGadget(Gadget gadget) {
        gadgets.add(gadget);
    }

    public void tampilkanSemuaGadget() {
        if (gadgets.isEmpty()) {
            System.out.println("Belum ada gadget yang terdaftar.");
            return;
        }
        System.out.println("\n--- Semua Gadget ---");
        for (Gadget g : gadgets) {
            g.displayInfo();
        }
    }

    public void filterByBrand() {
        String[] allBrands = {
            "Samsung", "iPhone", "Vivo", "Oppo", "Xiaomi", "Redmi", "Poco", "Infinix", "Tecno", "Nokia",
            "ASUS", "HP", "Lenovo", "Acer", "Razer", "Dell"
        };

        System.out.println("Pilih merek:");
        for (int i = 0; i < allBrands.length; i++) {
            System.out.println((i + 1) + ". " + allBrands[i]);
        }

        try {
            System.out.print("Pilihan: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            if (pilihan < 1 || pilihan > allBrands.length) {
                System.out.println("Pilihan tidak valid.");
                return;
            }

            String selectedBrand = allBrands[pilihan - 1];
            boolean found = false;
            for (Gadget g : gadgets) {
                if (g.getBrand().equalsIgnoreCase(selectedBrand)) {
                    g.displayInfo();
                    found = true;
                }
            }
            if (!found) System.out.println("Tidak ada gadget dengan merek tersebut.");

        } catch (InputMismatchException e) {
            System.out.println("Input harus berupa angka.");
            scanner.nextLine();
        }
    }


    public void filterByCondition() {
        String[] conditions = {"Baru", "Baik Sekali", "Baik", "Kurang Baik", "Buruk"};

        System.out.println("Pilih kondisi:");
        for (int i = 0; i < conditions.length; i++) {
            System.out.println((i + 1) + ". " + conditions[i]);
        }

        try {
            System.out.print("Pilihan: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            if (pilihan < 1 || pilihan > conditions.length) {
                System.out.println("Pilihan tidak valid.");
                return;
            }

            String selectedCondition = conditions[pilihan - 1];
            boolean found = false;
            for (Gadget g : gadgets) {
                if (g.getCondition().equalsIgnoreCase(selectedCondition)) {
                    g.displayInfo();
                    found = true;
                }
            }
            if (!found) System.out.println("Tidak ada gadget dengan kondisi tersebut.");

        } catch (InputMismatchException e) {
            System.out.println("Input harus berupa angka.");
            scanner.nextLine();
        }
    }


    public void filterByPriceRange() {
        try {
            System.out.print("Masukkan harga minimum: ");
            double minPrice = scanner.nextDouble();
            System.out.print("Masukkan harga maksimum: ");
            double maxPrice = scanner.nextDouble();
            scanner.nextLine();

            if (minPrice > maxPrice) {
                System.out.println("Harga minimum tidak boleh lebih besar dari maksimum.");
                return;
            }

            boolean found = false;
            for (Gadget g : gadgets) {
                if (g.getPrice() >= minPrice && g.getPrice() <= maxPrice) {
                    g.displayInfo();
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Tidak ada gadget dalam range harga tersebut.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Input harus berupa angka.");
            scanner.nextLine();
        }
    }
}