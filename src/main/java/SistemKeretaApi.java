/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import model.*;
import model.payment.*;
import service.*;
import java.time.format.DateTimeFormatter;
import repository.*;

/**
 *
 * @author asus
 */
public class SistemKeretaApi {
    private static ScheduleRepository scheduleRepo;
    private static TicketRepository ticketRepo;
    private static BookingService service;
    private static Passenger passenger;
    private static Scanner sc;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    
    public static void main(String[] args) {
        System.out.println("--- SELAMAT DATANG DI SISTEM BOOKING TIKET KERETA API ---");
        
        // 1. Inisialisasi (Hanya sekali di awal)
        initSystem();
        
        boolean running = true;
        while (running) {
            System.out.println("\n=================================");
            System.out.println("Halo, " + passenger.getName());
            System.out.println("=================================");
            System.out.println("1. Pesan Tiket Baru");
            System.out.println("2. Lihat Riwayat Tiket Saya");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu (1-3): ");
            
            int choice = sc.nextInt();
            sc.nextLine(); // Bersihkan buffer newline
            
            switch (choice) {
                case 1:
                    menuPesanTiket();
                    break;
                case 2:
                    menuLihatRiwayat();
                    break;
                case 3:
                    running = false;
                    System.out.println("Terima kasih telah menggunakan layanan kami!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
        
        sc.close();
    }
// --- MENU 1: PESAN TIKET ---
    private static void menuPesanTiket() {
        System.out.println("\n--- MENU PESAN TIKET ---");
        
        Station gambir = scheduleRepo.getStation("GMR");
        Station bandung = scheduleRepo.getStation("BD");
        LocalDateTime searchDate = LocalDateTime.of(2025, 12, 25, 0, 0);

        System.out.println("Mencari jadwal: " + gambir.getStationName() + " -> " + bandung.getStationName());
        
        List<Schedule> schedules = service.searchAvailableSchedules(gambir, bandung, searchDate);
        
        if (schedules.isEmpty()) {
            System.out.println("Maaf, jadwal tidak ditemukan.");
            return;
        }
        
        // Tampilkan Jadwal
        System.out.println("\nJadwal Tersedia:");
        for (int i = 0; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            System.out.println((i + 1) + ". " + s.getTrain().getTrainName() + 
                               " (" + s.getDepartureTime().format(FORMATTER) + ")" +
                               " - Rp" + s.getPrice());
        }
        
        System.out.print("Pilih nomor jadwal (0 untuk batal): ");
        int scheduleChoice = sc.nextInt();
        if (scheduleChoice < 1 || scheduleChoice > schedules.size()) {
            System.out.println("Batal memilih jadwal.");
            return;
        }
        Schedule selectedSchedule = schedules.get(scheduleChoice - 1);

        // Pilih Kursi
        System.out.println("\nMemeriksa ketersediaan kursi...");
        List<Seat> seats = selectedSchedule.getTrain().getSeats();
        Seat selectedSeat = null;
        
        // Tampilkan kursi yang available saja
        int count = 0;
        for(Seat s : seats) {
            if(s.isAvailable()) {
                if (selectedSeat == null) selectedSeat = s; // Pilih otomatis yang pertama
                System.out.print("[" + s.getSeatNumber() + "] ");
                count++;
            }
            if (count >= 5) break; 
        }
        System.out.println("...");
        
        if (selectedSeat == null) {
            System.out.println("Maaf, semua kursi penuh!");
            return;
        }
        System.out.println("Sistem otomatis memilihkan kursi: " + selectedSeat.getSeatNumber());

        // Pembayaran
        System.out.println("\nPilih Metode Pembayaran:");
        System.out.println("1. Credit Card");
        System.out.println("2. Bank Transfer");
        System.out.print("Pilihan: ");
        int paymentChoice = sc.nextInt();
        sc.nextLine();
        
        PaymentMethod paymentMethod;
        if (paymentChoice == 1) {
            String nomorKartu = "";

            while (nomorKartu.isBlank()) {
                System.out.print("Masukkan Nomor Credit Card: ");
                nomorKartu = sc.nextLine().trim(); // trim() menghapus spasi depan/belakang
                
                if (nomorKartu.isBlank()) {
                    System.out.println(">> Error: Nomor kartu tidak boleh kosong! Silakan input ulang.");
                }
            }
            paymentMethod = new CreditCardPayment(nomorKartu);
        } else {
            paymentMethod = new BankTransferPayment("8808123456789");
        }

        // Proses
        Ticket ticket = service.processBooking(passenger, selectedSchedule, selectedSeat, paymentMethod);
        
        if (ticket != null) {
            System.out.println("\n>>> BOOKING SUKSES! <<<");
            System.out.println(ticket.getFormattedTicket());
        } else {
            System.out.println("\n>>> BOOKING GAGAL <<<");
        }
    }
    
    // --- MENU 2: LIHAT RIWAYAT ---
    private static void menuLihatRiwayat() {
        System.out.println("\n--- RIWAYAT TIKET SAYA ---");
        
        // Mengambil semua tiket dari TicketRepository
        List<Ticket> myTickets = ticketRepo.findAll();
        
        if (myTickets.isEmpty()) {
            System.out.println("Belum ada tiket yang dipesan.");
        } else {
            for (int i = 0; i < myTickets.size(); i++) {
                Ticket t = myTickets.get(i);
                System.out.println("Tiket #" + (i+1) + " (ID: " + t.getTicketId() + ")");
                System.out.println("Rute: " + t.getBookingDetails().getSchedule().getRoute().getOriginStation().getStationName() + 
                                   " -> " + t.getBookingDetails().getSchedule().getRoute().getDestinationStation().getStationName());
                System.out.println("Status: " + (t.getBookingDetails().isPaid() ? "LUNAS" : "BELUM LUNAS"));
                System.out.println("-------------------------------");
            }
        }
        // Pause biar user bisa baca
        System.out.println("Tekan Enter untuk kembali ke menu...");
        try { System.in.read(); } catch (Exception e) {}
    }

    // --- SETUP SYSTEM ---
    private static void initSystem() {
        scheduleRepo = setupData();
        ticketRepo = new TicketRepository();
        service = new BookingService(scheduleRepo, ticketRepo);
        passenger = new Passenger("P001", "pass123", "Budi Santoso", "budi@email.com");
        sc = new Scanner(System.in);
    }
    
    private static ScheduleRepository setupData() {
        Station gambir = new Station("GMR", "Stasiun Gambir");
        Station bandung = new Station("BD", "Stasiun Bandung");
        
        Train argoParahyangan = new Train("K1", "Argo Parahyangan", 5);
        Train lodaya = new Train("K2", "Lodaya Pagi", 3);
        
        Route ruteGMR_BD = new Route("R01", gambir, bandung);
        Route ruteBD_GMR = new Route("R02", bandung, gambir);
        
        Schedule jadwalPagi = new Schedule("S01", ruteGMR_BD, argoParahyangan, LocalDateTime.of(2025, 12, 25, 8, 0, 0), 150000.0);
        Schedule jadwalSiang = new Schedule("S02", ruteGMR_BD, lodaya, LocalDateTime.of(2025, 12, 25, 10, 0, 0), 210000.0);
        Schedule jadwalBalik = new Schedule("S03", ruteBD_GMR, argoParahyangan, LocalDateTime.of(2025, 12, 26, 14, 0, 0), 155000.0);
        
        ScheduleRepository repo = new ScheduleRepository();
        repo.addStationToCache(gambir);
        repo.addStationToCache(bandung);
        repo.addTrainToCache(argoParahyangan);
        repo.addTrainToCache(lodaya);
        repo.save(jadwalPagi);
        repo.save(jadwalSiang);
        repo.save(jadwalBalik);
        
        return repo;
    }
}
