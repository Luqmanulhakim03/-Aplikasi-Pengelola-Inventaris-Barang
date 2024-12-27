package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


class InventoryManagerTest {

    private InventoryManager inventoryManager;

    @BeforeEach
    void setUp() {
        inventoryManager = new InventoryManager();
    }

    @Test
    void testAddItem() {
        String name = "Laptop";
        String description = "Laptop Gaming";
        int quantity = 10;
        ImageIcon image = new ImageIcon(); // Simulasi ikon gambar kosong

        InventoryManager.ItemDetails item = inventoryManager.new ItemDetails(name, description, quantity, image);
        inventoryManager.itemDetailsMap.put(name, item);

        // Validasi bahwa item berhasil ditambahkan ke peta
        assertTrue(inventoryManager.itemDetailsMap.containsKey(name));
        assertEquals(description, inventoryManager.itemDetailsMap.get(name).getDeskripsi());
        assertEquals(quantity, inventoryManager.itemDetailsMap.get(name).getJumlah());
    }

    @Test
    void testRemoveItem() {
        String name = "Mouse";
        String description = "Mouse Wireless";
        int quantity = 15;
        ImageIcon image = new ImageIcon();

        InventoryManager.ItemDetails item = inventoryManager.new ItemDetails(name, description, quantity, image);
        inventoryManager.itemDetailsMap.put(name, item);

        // Hapus item
        inventoryManager.itemDetailsMap.remove(name);

        // Validasi bahwa item berhasil dihapus
        assertFalse(inventoryManager.itemDetailsMap.containsKey(name));
    }

    @Test
    void testUpdateItem() {
        String name = "Keyboard";
        String description = "Mechanical Keyboard";
        int quantity = 20;
        ImageIcon image = new ImageIcon();

        InventoryManager.ItemDetails item = inventoryManager.new ItemDetails(name, description, quantity, image);
        inventoryManager.itemDetailsMap.put(name, item);

        // Perbarui deskripsi dan jumlah
        String newDescription = "RGB Mechanical Keyboard";
        int newQuantity = 25;

        inventoryManager.itemDetailsMap.get(name).deskripsi = newDescription;
        inventoryManager.itemDetailsMap.get(name).jumlah = newQuantity;

        // Validasi pembaruan
        assertEquals(newDescription, inventoryManager.itemDetailsMap.get(name).getDeskripsi());
        assertEquals(newQuantity, inventoryManager.itemDetailsMap.get(name).getJumlah());
    }

    @Test
    void testAddItemWithInvalidQuantity() {
        String name = "Monitor";
        String description = "LED Monitor";
        String invalidQuantity = "abc"; // Kuantitas tidak valid
        ImageIcon image = new ImageIcon();

        // Uji dengan menangkap exception
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            int quantity = Integer.parseInt(invalidQuantity);
            inventoryManager.itemDetailsMap.put(name, inventoryManager.new ItemDetails(name, description, quantity, image));
        });

        assertTrue(exception.getMessage().contains("For input string"));
    }
}
