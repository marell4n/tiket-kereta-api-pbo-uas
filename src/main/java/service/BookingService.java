/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import repository.*;
import model.*;
import model.payment.*;
/**
 *
 * @author asus
 */
public class BookingService {
    private ScheduleRepository scheduleRepo;
    private TicketRepository ticketRepo;

    public BookingService(ScheduleRepository scheduleRepo, TicketRepository ticketRepo) {
        this.scheduleRepo = scheduleRepo;
        this.ticketRepo = ticketRepo;
    }
    
    public List<Schedule> searchAvailableSchedules(Station origin, Station destination, LocalDateTime date) {
        List<Schedule> allSchedules = scheduleRepo.findAll();
        List<Schedule> available = new ArrayList<>();

        for (Schedule s : allSchedules) {
            boolean matchOrigin = s.getRoute().getOriginStation().equals(origin);
            boolean matchDest = s.getRoute().getDestinationStation().equals(destination);
            // Cek apakah tanggalnya sama (mengabaikan jam)
            boolean matchDate = s.getDepartureTime().toLocalDate().equals(date.toLocalDate());

            if (matchOrigin && matchDest && matchDate) {
                available.add(s);
            }
        }
        return available;
    }
    
    public Ticket processBooking(Passenger passenger, Schedule schedule, Seat seat, PaymentMethod paymentMethod) {
        
        System.out.println("Memproses booking untuk " + passenger.getName() + "...");

        // 1. Cek ketersediaan kursi
        if (!seat.isAvailable()) {
            System.err.println("Booking Gagal: Kursi " + seat.getSeatNumber() + " tidak tersedia.");
            return null;
        }

        // 2. Buat objek Booking
        String bookingId = "BK-" + UUID.randomUUID().toString().substring(0, 8);
        Booking booking = new Booking(bookingId, passenger, schedule, seat);
        
        // 3. Set metode pembayaran (Polimorfisme)
        booking.setPaymentMethod(paymentMethod);

        // 4. Lakukan pembayaran
        boolean paymentSuccess = paymentMethod.pay(booking.getTotalAmount());

        if (paymentSuccess) {
            booking.setPaid(true);
            seat.bookSeat(); // Tandai kursi sebagai tidak tersedia
            
            // 5. Buat Tiket
            String ticketId = "TK-" + UUID.randomUUID().toString().substring(0, 8);
            Ticket ticket = new Ticket(ticketId, booking);
            
            ticketRepo.save(ticket);
            
            System.out.println("Booking Berhasil! Tiket telah diterbitkan.");
            return ticket;
        } else {
            System.err.println("Booking Gagal: Pembayaran tidak berhasil.");
            return null;
        }
    }
}
