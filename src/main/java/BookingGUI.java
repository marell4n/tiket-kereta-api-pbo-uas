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

/**
 *
 * @author asus
 */
public class BookingGUI extends JFrame {
     // Ini adalah referensi ke LOGIKA kita
    private BookingService service;
    private ScheduleRepository repo;

    // Data dummy (sama seperti di console)
    private Passenger passenger;
    private Schedule selectedSchedule;
    private Seat selectedSeat;

    // Komponen GUI
    private JTextArea ticketDisplayArea;
    private JButton bookButton;
    private JLabel infoLabel;
    
    // --- KOMPONEN BARU ---
    private JRadioButton ccRadio; // Tombol radio Kartu Kredit
    private JRadioButton btRadio; // Tombol radio Bank Transfer
    private ButtonGroup paymentGroup; // Grup agar hanya 1 yang bisa dipilih

    public BookingGUI() {
        // 1. Setup data (panggil helper setupData)
        this.repo = setupData();
        this.service = new BookingService(repo);
        
        // 2. Siapkan data dummy untuk booking
        this.passenger = new Passenger("P001", "pass123", "Budi Santoso", "budi@email.com");
        this.selectedSchedule = repo.findById("S01"); // Ambil jadwal S01
        this.selectedSeat = selectedSchedule.getTrain().getSeatByNumber("EKO-03"); // Ambil kursi EKO-03
        
        // 3. Setup tampilan JFrame
        setTitle("Sistem Booking Tiket GUI");
        setSize(500, 700); // Kita perbesar sedikit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Beri jarak

        // Panel untuk info
        // Kita ubah layoutnya agar bisa menampung lebih banyak
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5)); // 0 baris, 1 kolom
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Beri padding
        
        infoLabel = new JLabel("Booking untuk: " + passenger.getName());
        JLabel scheduleLabel = new JLabel("Jadwal: " + selectedSchedule.getTrain().getTrainName() + " (" + DateUtil.formatLDT(selectedSchedule.getDepartureTime()) + ")");
        JLabel seatLabel = new JLabel("Kursi: " + selectedSeat.getSeatNumber());
        JLabel priceLabel = new JLabel("Harga: Rp" + selectedSchedule.getPrice());
        
        infoPanel.add(infoLabel);
        infoPanel.add(scheduleLabel);
        infoPanel.add(seatLabel);
        infoPanel.add(priceLabel);
        
        // --- TAMBAHKAN PILIHAN PEMBAYARAN ---
        infoPanel.add(new JSeparator()); // Garis pemisah
        infoPanel.add(new JLabel("Pilih Metode Pembayaran:"));
        
        ccRadio = new JRadioButton("Credit Card (1234-...)");
        ccRadio.setSelected(true); // Default pilih credit card
        btRadio = new JRadioButton("Bank Transfer (VA 8808-...)");
        
        // Masukkan ke ButtonGroup
        paymentGroup = new ButtonGroup();
        paymentGroup.add(ccRadio);
        paymentGroup.add(btRadio);
        
        infoPanel.add(ccRadio);
        infoPanel.add(btRadio);

        add(infoPanel, BorderLayout.NORTH);

        // Text Area untuk hasil tiket
        ticketDisplayArea = new JTextArea();
        ticketDisplayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ticketDisplayArea);
        add(scrollPane, BorderLayout.CENTER);

        // Tombol untuk book (teksnya kita buat umum)
        bookButton = new JButton("Book Tiket Sekarang");
        JPanel buttonPanel = new JPanel(); // Panel agar tombol tidak terlalu lebar
        buttonPanel.add(bookButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // 4. Tambah Aksi (Ini adalah PENGHUBUNG-nya)
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Saat tombol diklik, PANGGIL LOGIKA dari BookingService
                
                PaymentMethod paymentMethod; // Deklarasi dulu
                
                // --- INI DIA LOGIKA POLIMORFISME-NYA ---
                // Cek radio button mana yang dipilih
                if (ccRadio.isSelected()) {
                    // Jika pilih CC, buat objek CreditCardPayment
                    paymentMethod = new CreditCardPayment("1234-5678-9012-3456");
                } else {
                    // Jika pilih BT, buat objek BankTransferPayment
                    paymentMethod = new BankTransferPayment("8808123456789");
                }
                
                // PANGGIL SERVICE (LOGIKA)
                // Service tidak peduli isinya CC atau BT,
                // yang penting adalah objek 'PaymentMethod'.
                // Inilah Polimorfisme!
                Ticket ticket = service.processBooking(passenger, selectedSchedule, selectedSeat, paymentMethod);
                
                // 5. Tampilkan hasil ke GUI
                if (ticket != null) {
                    ticketDisplayArea.setText(ticket.getFormattedTicket());
                    bookButton.setEnabled(false);
                    bookButton.setText("Booking Berhasil!");
                } else {
                    ticketDisplayArea.setText("BOOKING GAGAL.\nSilakan cek di console error.");
                }
            }
        });
    }

    /**
     * Helper method untuk inisialisasi data dummy (sama seperti di KeretaApi.java).
     */
    private static ScheduleRepository setupData() {
        // 1. Buat Stasiun
        Station gambir = new Station("GMR", "Stasiun Gambir");
        Station bandung = new Station("BD", "Stasiun Bandung");
        
        // 2. Buat Kereta (Komposisi: Kursi dibuat di dalam)
        Train argoParahyangan = new Train("K1", "Argo Parahyangan", 5); // 5 kursi
        
        // 3. Buat Rute (Agregasi)
        Route ruteGMR_BD = new Route("R01", gambir, bandung);
        
        // 4. Buat Jadwal (Agregasi)
        Schedule jadwalPagi = new Schedule(
            "S01", 
            ruteGMR_BD, 
            argoParahyangan, 
            LocalDateTime.of(2025, 12, 25, 8, 0, 0), 
            150000.0
        );
        
        // 5. Setup Repository
        ScheduleRepository repo = new ScheduleRepository();
        repo.addStationToCache(gambir);
        repo.addStationToCache(bandung);
        repo.addTrainToCache(argoParahyangan);
        repo.save(jadwalPagi);
        
        return repo;
    }

    // Main method untuk menjalankan GUI
    public static void main(String[] args) {
        // Ini memastikan GUI berjalan di thread yang benar
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BookingGUI().setVisible(true);
            }
        });
    }
}
