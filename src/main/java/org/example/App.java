package org.example;

import javax.swing.*;
        import javax.swing.table.DefaultTableModel;
import java.awt.*;
        import java.io.*;
        import javax.imageio.ImageIO;
import java.util.*;

class InventoryManager {

    public JFrame frame;
    public JTable inventoryTable;
    public DefaultTableModel tableModel;
    public Map<String, ItemDetails> itemDetailsMap;

    public InventoryManager() {
        frame = new JFrame("Inventaris Barang");
        tableModel = new DefaultTableModel(new String[]{"Nama", "Deskripsi", "Jumlah"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        inventoryTable = new JTable(tableModel);
        itemDetailsMap = new HashMap<>();

        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setupGUI();
    }

    public void setupGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(45, 52, 54));
        JLabel headerLabel = new JLabel("Inventory Manager");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Daftar Barang"));
        inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(inventoryTable);
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Bottom Panel for input and buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel nameLabel = new JLabel("Nama Item:");
        JTextField nameField = new JTextField();

        JLabel descriptionLabel = new JLabel("Deskripsi:");
        JTextField descriptionField = new JTextField();

        JLabel quantityLabel = new JLabel("Jumlah:");
        JTextField quantityField = new JTextField();

        JLabel imageLabel = new JLabel("Gambar Path:");
        JTextField imageField = new JTextField();
        JButton browseButton = new JButton("Browse...");

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);
        inputPanel.add(imageLabel);

        JPanel imageInputPanel = new JPanel(new BorderLayout());
        imageInputPanel.add(imageField, BorderLayout.CENTER);
        imageInputPanel.add(browseButton, BorderLayout.EAST);
        inputPanel.add(imageInputPanel);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 4, 10, 10));

        JButton addButton = new JButton("Tambah");
        JButton removeButton = new JButton("Hapus");
        JButton updateButton = new JButton("Update");
        JButton readButton = new JButton("Detail");

        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        buttonsPanel.add(updateButton);
        buttonsPanel.add(readButton);

        // Adding panels to bottom panel
        bottomPanel.add(inputPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
        bottomPanel.add(buttonsPanel);

        // Adding panels to frame
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            int selectedRow = inventoryTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Pilih item yang ingin diperbarui!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String oldName = (String) tableModel.getValueAt(selectedRow, 0);
            String name = nameField.getText().trim();
            String description = descriptionField.getText().trim();
            String quantityStr = quantityField.getText().trim();
            String imagePath = imageField.getText().trim();

            if (name.isEmpty() || description.isEmpty() || quantityStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Minimal semua diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityStr);
                ImageIcon icon = new ImageIcon(ImageIO.read(new File(imagePath)));

                // Update HashMap
                itemDetailsMap.remove(oldName);
                itemDetailsMap.put(name, new ItemDetails(name, description, quantity, icon));

                // Update table
                tableModel.setValueAt(name, selectedRow, 0);
                tableModel.setValueAt(description, selectedRow, 1);
                tableModel.setValueAt(quantity, selectedRow, 2);

                JOptionPane.showMessageDialog(frame, "Item berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Jumlah harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Gagal memasukkan gambar!: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        readButton.addActionListener(e -> {
            int selectedRow = inventoryTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Pilih item untuk melihat detailnya!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String name = (String) tableModel.getValueAt(selectedRow, 0);

            // Ambil detail dari HashMap
            ItemDetails details = itemDetailsMap.get(name);
            if (details != null) {
                // Buat dialog baru untuk menampilkan detail
                JDialog detailDialog = new JDialog(frame, "Detail Barang", true);
                detailDialog.setSize(400, 500);
                detailDialog.setLayout(new BorderLayout());

                // Panel untuk teks detail
                JPanel textPanel = new JPanel();
                textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
                textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel detailNameLabel = new JLabel("Nama: " + details.getNama());
                JLabel detailDescriptionLabel = new JLabel("Deskripsi: " + details.getDeskripsi());
                JLabel detailQuantityLabel = new JLabel("Jumlah: " + details.getJumlah());

                textPanel.add(detailNameLabel);
                textPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spasi
                textPanel.add(detailDescriptionLabel);
                textPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spasi
                textPanel.add(detailQuantityLabel);

                // Panel untuk gambar
                JLabel detailImageLabel = new JLabel();
                detailImageLabel.setHorizontalAlignment(JLabel.CENTER);

                // Skalakan gambar agar pas dalam dialog
                ImageIcon scaledIcon = scaleImageIcon(details.getGambar(), 300, 300);
                detailImageLabel.setIcon(scaledIcon);

                // Tambahkan komponen ke dialog
                detailDialog.add(textPanel, BorderLayout.NORTH);
                detailDialog.add(detailImageLabel, BorderLayout.CENTER);

                // Tampilkan dialog
                detailDialog.setLocationRelativeTo(frame);
                detailDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(frame, "Detail tidak ditemukan untuk item ini!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeButton.addActionListener(e -> {
            int selectedRow = inventoryTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Pilih item yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String name = (String) tableModel.getValueAt(selectedRow, 0);

            // Remove from HashMap
            itemDetailsMap.remove(name);

            // Remove from table
            tableModel.removeRow(selectedRow);

            JOptionPane.showMessageDialog(frame, "Item berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        });


        // Browse button action
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imageField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Add button action
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String description = descriptionField.getText().trim();
            String quantityStr = quantityField.getText().trim();
            String imagePath = imageField.getText().trim();

            if (name.isEmpty() || description.isEmpty() || quantityStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Minimal semua diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityStr);
                ImageIcon icon = new ImageIcon(ImageIO.read(new File(imagePath)));

                tableModel.addRow(new Object[]{name, description, quantity});
                itemDetailsMap.put(name, new ItemDetails(name, description, quantity, icon));

                nameField.setText("");
                descriptionField.setText("");
                quantityField.setText("");
                imageField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Jumlah harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Gagal memasukan gambar!: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Remaining actions (removeButton, updateButton, readButton) are similar to the original version...

        frame.setVisible(true);
    }

    private ImageIcon scaleImageIcon(ImageIcon gambar, int i, int i1) {
        return gambar;
    }

    // Remaining methods for showItemDetails and scaleImageIcon are similar to the original version...

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryManager::new);
    }

    class ItemDetails {
        public String nama;
        public String deskripsi;
        public int jumlah;
        public ImageIcon gambar;

        public ItemDetails(String name, String description, int quantity, ImageIcon image) {
            this.nama = name;
            this.deskripsi = description;
            this.jumlah = quantity;
            this.gambar = image;
        }

        public String getNama() {
            return nama;
        }

        public String getDeskripsi() {
            return deskripsi;
        }

        public int getJumlah() {
            return jumlah;
        }

        public ImageIcon getGambar() {
            return gambar;
        }
    }
}
