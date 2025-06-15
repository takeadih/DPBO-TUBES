package Class;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GadgetStoreGUI extends JFrame {
    private GadgetStore store = new GadgetStore();
    private DefaultListModel<String> gadgetListModel = new DefaultListModel<>();
    private JList<String> gadgetList = new JList<>(gadgetListModel);

    public GadgetStoreGUI() {
        setTitle("Toko Gadget Bekas");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel untuk judul di tengah atas
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Toko Gadget Bekas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        // Panel untuk tombol Keluar di bagian atas
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnKeluar = new JButton("Keluar");
        topPanel.add(btnKeluar);
        add(topPanel, BorderLayout.NORTH);

        add(new JScrollPane(gadgetList), BorderLayout.CENTER);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnTambah = new JButton("Jual Gadget");
        JButton btnBeli = new JButton("Beli Gadget");
        JButton btnFilterHarga = new JButton("Filter Harga");
        JButton btnFilterBrand = new JButton("Filter Merek");
        JButton btnFilterKondisi = new JButton("Filter Kondisi");
        JButton btnReset = new JButton("Reset Filter");

        panel.add(btnTambah);
        panel.add(btnBeli);
        panel.add(btnFilterHarga);
        panel.add(btnFilterBrand);
        panel.add(btnFilterKondisi);
        panel.add(btnReset);
        add(panel, BorderLayout.SOUTH);

        // Listener klasik (kompatibel Java 8)
        btnTambah.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tambahGadget();
            }
        });
        btnBeli.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                beliGadget();
            }
        });
        btnFilterHarga.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (store.getAllGadgets().isEmpty()) {
                    JOptionPane.showMessageDialog(GadgetStoreGUI.this, "Belum ada gadget untuk difilter.");
                    return;
                }
                filterByPrice();
            }
        });
        btnFilterBrand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (store.getAllGadgets().isEmpty()) {
                    JOptionPane.showMessageDialog(GadgetStoreGUI.this, "Belum ada gadget untuk difilter.");
                    return;
                }
                filterByBrandDropdown();
            }
        });
        btnFilterKondisi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (store.getAllGadgets().isEmpty()) {
                    JOptionPane.showMessageDialog(GadgetStoreGUI.this, "Belum ada gadget untuk difilter.");
                    return;
                }
                filterByCondition();
            }
        });
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tampilkanSemuaGadget();
            }
        });
        btnKeluar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                keluarDenganKonfirmasi();
            }
        });

        tampilkanSemuaGadget();
    }

    private void tambahGadget() {
        String[] tipeOptions = {"Smartphone", "Laptop"};
        String tipe = (String) JOptionPane.showInputDialog(this, "Pilih tipe gadget:", "Tipe",
                JOptionPane.QUESTION_MESSAGE, null, tipeOptions, tipeOptions[0]);
        if (tipe == null) return;

        String brand = store.askBrandByType(this, tipe);
        if (brand == null) return;

        String condition = store.askCondition(this);
        if (condition == null) return;

        String hargaStr = JOptionPane.showInputDialog(this, "Masukkan harga:");
        if (hargaStr == null) return;

        try {
            long price = Long.parseLong(hargaStr);
            Gadget gadget = store.addGadget(tipe, brand, condition, price);
            gadgetListModel.addElement(gadgetDisplay(gadget));
            JOptionPane.showMessageDialog(this, "Gadget berhasil ditambahkan!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void beliGadget() {
        int index = gadgetList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Pilih gadget yang ingin dibeli.");
            return;
        }
        Gadget gadget = store.buyGadget(index);
        if (gadget != null) {
            gadgetListModel.remove(index);
            JOptionPane.showMessageDialog(this, "Anda membeli:\n" + gadgetDisplay(gadget));
        } else {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat membeli gadget.");
        }
    }

    private void filterByPrice() {
        String minStr = JOptionPane.showInputDialog(this, "Masukkan harga minimum:");
        if (minStr == null) return;

        String maxStr = JOptionPane.showInputDialog(this, "Masukkan harga maksimum:");
        if (maxStr == null) return;

        try {
            long min = Long.parseLong(minStr);
            long max = Long.parseLong(maxStr);
            List<Gadget> filtered = store.filterByPrice(min, max);
            showFilteredResult(filtered);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input harga harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterByBrandDropdown() {
        List<String> allBrands = store.getAllBrands();
        if (allBrands.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Belum ada gadget dengan merek tersedia.");
            return;
        }

        JComboBox<String> brandBox = new JComboBox<>(allBrands.toArray(new String[0]));
        int result = JOptionPane.showConfirmDialog(this, brandBox, "Pilih Merek", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String selectedBrand = (String) brandBox.getSelectedItem();
            List<Gadget> filtered = store.filterByBrand(selectedBrand);
            showFilteredResult(filtered);
        }
    }

    private void filterByCondition() {
        List<Gadget> filtered = store.filterByCondition(this);
        showFilteredResult(filtered);
    }

    private void tampilkanSemuaGadget() {
        List<Gadget> all = store.getAllGadgets();
        gadgetListModel.clear();
        for (Gadget g : all) {
            gadgetListModel.addElement(gadgetDisplay(g));
        }
    }

    private void showFilteredResult(List<Gadget> filtered) {
        gadgetListModel.clear();
        for (Gadget g : filtered) {
            gadgetListModel.addElement(gadgetDisplay(g));
        }
    }

    private String gadgetDisplay(Gadget g) {
        return (g instanceof Smartphone ? "Smartphone" : "Laptop") +
                " | Merek: " + g.getBrand() +
                " | Kondisi: " + g.getCondition() +
                " | Harga: " + g.getPrice();
    }

    private void showExitMessage() {
        JOptionPane.showMessageDialog(this,
                "Terima kasih telah menggunakan Toko Gadget Bekas!\nSampai jumpa lagi.",
                "Keluar", JOptionPane.INFORMATION_MESSAGE);
    }

    private void keluarDenganKonfirmasi() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin keluar?",
                "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            showExitMessage();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GadgetStoreGUI().setVisible(true);
            }
        });
    }
}
