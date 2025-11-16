/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import model.*;
import model.payment.*;
import service.*;
import util.*;
import repository.*;

/**
 *
 * @author asus
 */
public class SistemKeretaApi {
    public static void main(String[] args) {
        System.out.println("--- SELAMAT DATANG DI SISTEM BOOKING TIKET KERETA API ---");
        
        // 1. Inisialisasi Database (Repository dan Data Dummy)
        ScheduleRepository repo = setupData();
        
        // 2. Inisialisasi Booking Service
        BookingService service = new BookingService(repo);
        
        // 3. Data Penumpang (Dummy)
        Passenger passenger = new Passenger("P001", "pass123", "Budi Santoso", "budi@email.com");
        
        // 4. Simulasi Skenario Console
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\nLogin sebagai: " + passenger.getName());
        
        // Ambil data stasiun dari repo
        Station gambir = repo.getStation("GMR");
        Station bandung = repo.getStation("BD");
        LocalDateTime searchDate = LocalDateTime.of(2025, 12, 25, 0, 0);

        System.out.println("\nMencari jadwal untuk: " + gambir.getStationName() + " -> " + bandung.getStationName() + " pada " + DateUtil.formatLDT(searchDate));
        
        // 5. Cari Jadwal
        List<Schedule> schedules = service.searchAvailableSchedules(gambir, bandung, searchDate);
        
        if (schedules.isEmpty()) {
            System.out.println("Jadwal tidak ditemukan.");
            return;
        }
        
        // Tampilkan jadwal
        Schedule selectedSchedule = schedules.get(0); // Ambil jadwal pertama
        System.out.println("Jadwal Ditemukan:");
        System.out.println("Kereta: " + selectedSchedule.getTrain().getTrainName());
        System.out.println("Waktu: " + DateUtil.formatLDT(selectedSchedule.getDepartureTime()));
        System.out.println("Harga: Rp" + selectedSchedule.getPrice());

        // 6. Pilih Kursi
        Seat selectedSeat = selectedSchedule.getTrain().getSeatByNumber("EKO-03");
        System.out.println("\nMemilih kursi: " + selectedSeat.getSeatNumber());

        // 7. Pilih Metode Pembayaran (Polimorfisme)
        System.out.println("\nPilih Metode Pembayaran:");
        System.out.println("1. Credit Card");
        System.out.println("2. Bank Transfer");
        System.out.print("Pilihan: ");
        int paymentChoice = sc.nextInt();
        
        PaymentMethod paymentMethod;
        if (paymentChoice == 1) {
            paymentMethod = new CreditCardPayment("1234-5678-9012-3456");
        } else {
            paymentMethod = new BankTransferPayment("8808123456789");
        }

        // 8. Proses Booking
        System.out.println("\n--- MEMPROSES PEMBAYARAN & BOOKING ---");
        Ticket ticket = service.processBooking(passenger, selectedSchedule, selectedSeat, paymentMethod);
        
        // 9. Cetak Tiket
        if (ticket != null) {
            System.out.println("\n--- CETAK TIKET ---");
            System.out.println(ticket.getFormattedTicket());
        } else {
            System.out.println("Gagal mencetak tiket.");
        }
        
        sc.close();
    }
    
    /**
     * Helper method untuk inisialisasi data dummy.
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
        
        // Masukkan cache (sesuai UML)
        repo.addStationToCache(gambir);
        repo.addStationToCache(bandung);
        repo.addTrainToCache(argoParahyangan);
        
        // Masukkan data ke DB simulasi
        repo.save(jadwalPagi);
        
        return repo;
    }
}
