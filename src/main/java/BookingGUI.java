/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.time.LocalDateTime;
import javax.swing.*; // Import library GUI
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.*;
import model.payment.*;
import service.*;
import util.*;
import repository.*;

// import untuk tema
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.util.List;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author asus
 */
public class BookingGUI extends JFrame {
     // Ini adalah referensi ke LOGIKA kita
    private BookingService service;
    private ScheduleRepository repo;

    // Komponen GUI
    // --- JTextArea (ticketDisplayArea) DIHAPUS DARI SINI ---
    private JButton bookButton;
    
    // --- KOMPONEN BARU UNTUK INPUT ---
    private JTextField nameField;
    private JComboBox<Schedule> scheduleComboBox;
    private JComboBox<Seat> seatComboBox;
    private JLabel priceLabel; // Label harga yang bisa di-update
    
    // Komponen radio button (tetap)
    private JRadioButton ccRadio; // Tombol radio Kartu Kredit
    private JRadioButton btRadio; // Tombol radio Bank Transfer
    private ButtonGroup paymentGroup; // Grup agar hanya 1 yang bisa dipilih

    public BookingGUI() {
        // 1. Setup data (panggil helper setupData)
        this.repo = setupData();
        this.service = new BookingService(repo);
        
        // 3. Setup tampilan JFrame
        setTitle("Sistem Booking Tiket Kereta Api");
        // Kita bisa buat sedikit lebih pendek karena text area hilang
        setSize(550, 650); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Menengahkan jendela
        
        // --- Panel Utama (Biar ada padding) ---
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(255, 245, 248)); // Latar belakang pink pucat
        setContentPane(mainPanel);


        // --- Panel Info (Bagian Atas) ---
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 8, 8)); // 0 baris, 1 kolom, gap 8px
        infoPanel.setOpaque(false); // Transparan, ikut warna mainPanel
        
        // Judul Panel
        JLabel titleLabel = new JLabel("Detail Pemesanan");
        titleLabel.setFont(titleLabel.getFont().deriveFont(24f).deriveFont(java.awt.Font.BOLD));
        titleLabel.setForeground(new Color(210, 45, 105)); // Warna pink tua
        infoPanel.add(titleLabel);
        infoPanel.add(new JSeparator());
        
        // --- Input Nama Penumpang ---
        infoPanel.add(new JLabel("Nama Penumpang:"));
        nameField = new JTextField("Marellan"); // Nama default
        nameField.setFont(nameField.getFont().deriveFont(14f));
        infoPanel.add(nameField);
        
        // --- Pilihan Jadwal ---
        infoPanel.add(new JLabel("Pilih Jadwal:"));
        scheduleComboBox = new JComboBox<>();
        scheduleComboBox.setFont(scheduleComboBox.getFont().deriveFont(14f));
        // Isi dropdown jadwal
        List<Schedule> allSchedules = repo.findAll();
        for (Schedule s : allSchedules) {
            scheduleComboBox.addItem(s);
        }
        infoPanel.add(scheduleComboBox);
        
        // --- Pilihan Kursi ---
        infoPanel.add(new JLabel("Pilih Kursi:"));
        seatComboBox = new JComboBox<>();
        seatComboBox.setFont(seatComboBox.getFont().deriveFont(14f));
        infoPanel.add(seatComboBox);

        // --- Label Harga Dinamis ---
        priceLabel = new JLabel("Harga: Rp 0.0");
        priceLabel.setFont(priceLabel.getFont().deriveFont(16f).deriveFont(java.awt.Font.BOLD));
        infoPanel.add(priceLabel);
        
        
        // --- TAMBAHKAN PILIHAN PEMBAYARAN ---
        infoPanel.add(new JSeparator()); // Garis pemisah
        JLabel paymentLabel = new JLabel("Pilih Metode Pembayaran:");
        paymentLabel.setFont(paymentLabel.getFont().deriveFont(16f).deriveFont(java.awt.Font.BOLD));
        infoPanel.add(paymentLabel);
        
        ccRadio = new JRadioButton("Credit Card (1234-...)");
        ccRadio.setSelected(true); // Default pilih credit card
        ccRadio.setFont(ccRadio.getFont().deriveFont(14f));
        ccRadio.setOpaque(false);
        
        btRadio = new JRadioButton("Bank Transfer (VA 8808-...)");
        btRadio.setFont(btRadio.getFont().deriveFont(14f));
        btRadio.setOpaque(false);
        
        // Masukkan ke ButtonGroup
        paymentGroup = new ButtonGroup();
        paymentGroup.add(ccRadio);
        paymentGroup.add(btRadio);
        
        // Panel kecil untuk radio button
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setOpaque(false);
        radioPanel.add(ccRadio);
        radioPanel.add(btRadio);
        infoPanel.add(radioPanel);

        // --- PERUBAHAN LAYOUT ---
        // Kita letakkan panel form di TENGAH (CENTER)
        mainPanel.add(infoPanel, BorderLayout.CENTER);

        // --- Text Area DIHAPUS dari sini ---
        // mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- Tombol untuk book ---
        bookButton = new JButton("Bayar dan Pesan Tiket");
        // Style Tombol (accent button)
        bookButton.putClientProperty("JButton.buttonType", "roundRect");
        bookButton.putClientProperty("JComponent.outline", "accent");
        bookButton.setFont(bookButton.getFont().deriveFont(16f).deriveFont(java.awt.Font.BOLD));
        bookButton.setBackground(new Color(230, 60, 125)); // Warna pink utama
        bookButton.setForeground(Color.WHITE);
        bookButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        bookButton.setPreferredSize(new Dimension(bookButton.getPreferredSize().width, 50));

        JPanel buttonPanel = new JPanel(new BorderLayout()); // Panel agar tombol bisa full-width
        buttonPanel.setOpaque(false);
        buttonPanel.add(bookButton, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        
        // --- LOGIKA PENTING (Baru) ---
        // 1. Saat jadwal di-klik, update daftar kursi dan harga
        scheduleComboBox.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateSeatAndPrice();
            }
        });
        
        // 2. Update kursi saat pertama kali GUI dibuka
        updateSeatAndPrice();

        // 3. Logika Tombol "Book" (Di-update)
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // --- AMBIL SEMUA DATA INPUT DARI USER ---
                
                // 1. Ambil Nama
                String namaPenumpang = nameField.getText();
                if (namaPenumpang.isBlank()) {
                    JOptionPane.showMessageDialog(BookingGUI.this, 
                            "Nama penumpang tidak boleh kosong!", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // 2. Buat objek Passenger (Email & Pass DUMMY)
                Passenger passenger = new Passenger("P-Manual", "dummyPass", namaPenumpang, "dummy@email.com");
                
                // 3. Ambil Jadwal
                Schedule selectedSchedule = (Schedule) scheduleComboBox.getSelectedItem();
                
                // 4. Ambil Kursi
                Seat selectedSeat = (Seat) seatComboBox.getSelectedItem();
                if (selectedSeat == null) {
                    JOptionPane.showMessageDialog(BookingGUI.this, 
                            "Tidak ada kursi tersedia di jadwal ini.", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // 5. Ambil Metode Pembayaran
                PaymentMethod paymentMethod; 
                if (ccRadio.isSelected()) {
                    paymentMethod = new CreditCardPayment("1234-5678-9012-3456");
                } else {
                    paymentMethod = new BankTransferPayment("8808123456789");
                }
                
                // --- PANGGIL SERVICE (LOGIKA) ---
                Ticket ticket = service.processBooking(passenger, selectedSchedule, selectedSeat, paymentMethod);
                
                // 6. Tampilkan hasil ke GUI
                if (ticket != null) {
                    // --- INI PERUBAHAN UTAMANYA ---
                    // Buat JTextArea untuk ditaruh di dalam pop-up
                    JTextArea receiptArea = new JTextArea(ticket.getFormattedTicket());
                    receiptArea.setEditable(false);
                    receiptArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
                    receiptArea.setOpaque(false); // transparan
                    
                    // Buat pop-up "Panel Sendiri"
                    JOptionPane.showMessageDialog(BookingGUI.this, 
                            new JScrollPane(receiptArea), // Masukkan area teks ke scroll
                            "Booking Berhasil!", // Judul pop-up
                            JOptionPane.INFORMATION_MESSAGE);
                    
                    // Reset form untuk booking selanjutnya
                    nameField.setText("");
                    
                } else {
                    // --- Tampilkan error di pop-up juga ---
                    JOptionPane.showMessageDialog(BookingGUI.this, 
                            "BOOKING GAGAL.\nKursi mungkin sudah diambil orang lain.", 
                            "Booking Gagal", 
                            JOptionPane.ERROR_MESSAGE);
                }
                
                // 7. Update daftar kursi lagi (karena 1 kursi sudah diambil)
                updateSeatAndPrice();
            }
        });
    }
    
    /**
     * Method helper BARU
     * Untuk meng-update dropdown kursi dan label harga
     * setiap kali jadwal diubah.
     */
    private void updateSeatAndPrice() {
        // 1. Ambil jadwal yang dipilih
        Schedule selectedSchedule = (Schedule) scheduleComboBox.getSelectedItem();
        if (selectedSchedule == null) return;
        
        // 2. Update label harga
        priceLabel.setText("Harga: Rp " + selectedSchedule.getPrice());
        
        // 3. Hapus semua kursi lama dari dropdown
        seatComboBox.removeAllItems();
        
        // 4. Masukkan kursi yang TERSEDIA saja
        List<Seat> seats = selectedSchedule.getTrain().getSeats();
        for (Seat seat : seats) {
            if (seat.isAvailable()) {
                seatComboBox.addItem(seat);
            }
        }
    }

    /**
     * Helper method untuk inisialisasi data dummy.
     * --- SEKARANG DENGAN LEBIH BANYAK DATA ---
     */
    private static ScheduleRepository setupData() {
        // 1. Buat Stasiun
        Station gambir = new Station("GMR", "Stasiun Gambir");
        Station bandung = new Station("BD", "Stasiun Bandung");
        
        // 2. Buat Kereta
        Train argoParahyangan = new Train("K1", "Argo Parahyangan", 5); // 5 kursi EKO
        Train lodaya = new Train("K2", "Lodaya Pagi", 3); // 3 kursi EKS
        
        // 3. Buat Rute
        Route ruteGMR_BD = new Route("R01", gambir, bandung);
        Route ruteBD_GMR = new Route("R02", bandung, gambir); // Rute Balik
        
        // 4. Buat Jadwal
        Schedule jadwalPagi = new Schedule(
            "S01", 
            ruteGMR_BD, 
            argoParahyangan, // Pakai kereta K1
            LocalDateTime.of(2025, 12, 25, 8, 0, 0), 
            150000.0
        );
        Schedule jadwalSiang = new Schedule(
            "S02", 
            ruteGMR_BD, 
            lodaya, // Pakai kereta K2
            LocalDateTime.of(2025, 12, 25, 10, 0, 0), 
            210000.0
        );
        Schedule jadwalBalik = new Schedule(
            "S03", 
            ruteBD_GMR, // Rute balik
            argoParahyangan, // Pakai kereta K1
            LocalDateTime.of(2025, 12, 26, 14, 0, 0), 
            155000.0
        );
        
        // 5. Setup Repository
        ScheduleRepository repo = new ScheduleRepository();
        
        // Masukkan cache (sesuai UML)
        repo.addStationToCache(gambir);
        repo.addStationToCache(bandung);
        repo.addTrainToCache(argoParahyangan);
        repo.addTrainToCache(lodaya);
        
        // Masukkan data ke DB simulasi
        repo.save(jadwalPagi);
        repo.save(jadwalSiang); // <-- Data baru
        repo.save(jadwalBalik); // <-- Data baru
        
        return repo;
    }

    // Main method untuk menjalankan GUI
    public static void main(String[] args) {
        // Ini memastikan GUI berjalan di thread yang benar
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                // --- KODE BARU UNTUK TEMA ---
                try {
                    // Set FlatLaf Light Look and Feel
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    
                    // --- TAMBAHAN KODE PINK ---
                    // Mengubah warna aksen default (biru) menjadi pink
                    // Ini akan mengubah warna radio button, focus ring, dll.
                    UIManager.put("Component.accentColor", new Color(230, 60, 125));
                    
                } catch (Exception ex) {
                    System.err.println("Gagal menginisialisasi tema (LaF).");
                }
                // --- BATAS KODE BARU ---
                
                new BookingGUI().setVisible(true);
            }
        });
    }
}
