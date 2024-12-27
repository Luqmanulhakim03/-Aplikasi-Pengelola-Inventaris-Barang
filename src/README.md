# Inventory Manager

## Description
Inventory Manager adalah aplikasi berbasis Java Swing untuk mengelola inventaris barang. Aplikasi ini menyediakan antarmuka grafis yang sederhana untuk menambahkan, menghapus, memperbarui, dan melihat detail barang, termasuk gambar.

1. Tambah Barang:

- Pengguna dapat memasukkan nama, deskripsi, jumlah, dan gambar barang.
- Input validasi dilakukan untuk memastikan semua kolom terisi dan jumlah berupa angka.

2. Hapus Barang:
- Pengguna dapat memilih item dari tabel dan menghapusnya dari inventaris.

3. Perbarui Barang:
- Pengguna dapat memilih item dari tabel, mengedit detailnya, dan menyimpan perubahan.

4. Detail Barang:
- Pengguna dapat melihat detail lengkap barang, termasuk gambar yang ditampilkan dalam ukuran skala.

5. Penyimpanan Data Barang:
- Data barang disimpan dalam memori menggunakan struktur HashMap untuk pencarian cepat.

6. Penanganan Gambar:
- Gambar barang dimuat dari path yang diberikan pengguna dan ditampilkan menggunakan skala yang disesuaikan.

## Struktur Kode
1. Kelas Utama (InventoryManager):

    - Mengatur GUI dan logika utama aplikasi.
    - Menggunakan JTable untuk menampilkan daftar barang.

2. Kelas ItemDetails:
    - Menyimpan detail barang seperti:
    - Nama
    - Deskripsi
    - Jumlah
    - Gambar (dalam bentuk ImageIcon).

3. GUI Components:
    - Tabel Barang
      Menampilkan daftar barang dalam tabel non-editable.
    - Form Input
      Berisi input untuk nama, deskripsi, jumlah, dan path gambar barang.
    - Panel Tombol
      Tombol untuk berbagai aksi seperti Tambah, Hapus, Update, dan Detail.
   
4. Penyimpanan Data
    - Menggunakan HashMap<String, ItemDetails> untuk menyimpan data barang.
    - Kunci berupa nama barang dan nilai berupa objek ItemDetails.


## Cara menjalankan
1. Jalankan Program:
    - Kompilasi dan jalankan file InventoryManager.java.

2. Tambah Barang:

    - Isi kolom input:
    - Nama Item
    - Deskripsi
    - Jumlah (angka)
    - Path Gambar (klik tombol "Browse..." untuk memilih file gambar).
    - Klik tombol Tambah.

3. Hapus Barang:
    - Pilih item dari tabel.
    - Klik tombol "Hapus Barang".

4. Perbarui Barang:
    - Pilih item dari tabel.
    - Edit informasi di kolom input.
    - Klik tombol "Update Barang".

5. Lihat Detail Barang:
    - Pilih item dari tabel.
    - Klik tombol "Detail Barang" untuk melihat informasi lengkap dan gambar.

## Output
1. Form Input dan Daftar Barang
   Aplikasi memiliki antarmuka dengan header, tabel daftar barang, form input, dan panel tombol.

    Form Input:

    - Kolom untuk Nama Item, Deskripsi, Jumlah, dan Path Gambar.
    - Tombol Browse untuk memilih gambar.

    Tabel Daftar Barang:
    - Menampilkan data barang dalam format tabel.

    Tombol:
    - Tambah: Menambahkan barang baru.
    - Hapus: Menghapus barang yang dipilih. 
    - Update: Memperbarui detail barang.
    - Detail: Melihat informasi lengkap barang.
   
2. Tampilan Pop-up Detail Barang
   Setelah klik tombol Detail, muncul jendela pop-up dengan informasi barang dan gambar.

