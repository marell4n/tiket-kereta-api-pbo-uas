/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import util.*;
import model.payment.*;

/**
 *
 * @author asus
 */
public class Ticket {
    private String ticketId;
    private Booking bookingDetails;

    public Ticket(String ticketId, Booking bookingDetails) {
        this.ticketId = ticketId;
        this.bookingDetails = bookingDetails;
    }

    /**
     * Method untuk menampilkan detail tiket yang diformat.
     * Menggunakan (depend on) DateUtil.
     * Sesuai dengan diagram UML.
     * @return String detail tiket.
     */
    public String getFormattedTicket() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- TIKET KERETA API ---\n");
        sb.append("ID Tiket: ").append(ticketId).append("\n");
        sb.append("ID Booking: ").append(bookingDetails.getBookingId()).append("\n");
        sb.append("------------------------\n");
        sb.append("Nama Penumpang: ").append(bookingDetails.getPassenger().getName()).append("\n");
        sb.append("Kereta: ").append(bookingDetails.getSchedule().getTrain().getTrainName()).append("\n");
        sb.append("No. Kursi: ").append(bookingDetails.getSelectedSeat().getSeatNumber()).append("\n");
        sb.append("Rute: ").append(bookingDetails.getSchedule().getRoute().getOriginStation().getStationName())
          .append(" -> ")
          .append(bookingDetails.getSchedule().getRoute().getDestinationStation().getStationName()).append("\n");
        
        // Dependency ke DateUtil
        sb.append("Berangkat: ").append(DateUtil.formatLDT(bookingDetails.getSchedule().getDepartureTime())).append("\n");
        
        sb.append("Harga: Rp").append(bookingDetails.getTotalAmount()).append("\n");
        sb.append("Status: ").append(bookingDetails.isPaid() ? "LUNAS" : "BELUM LUNAS").append("\n");
        sb.append("------------------------\n");
        sb.append("--- RESI PEMBAYARAN ---\n");
        
        PaymentMethod payment = bookingDetails.getPaymentMethod();
        if (payment != null) {
            sb.append("Metode Bayar: ").append(payment.getPaymentName()).append("\n");
            
            // Cek tipe payment method (Polimorfisme)
            if (payment instanceof CreditCardPayment) {
                CreditCardPayment cc = (CreditCardPayment) payment;
                // Kita sensor nomor kartunya
                String cardNumber = cc.getCardNumber();
                String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);
                sb.append("Nomor Kartu: XXXX-XXXX-XXXX-").append(lastFourDigits).append("\n");
                
            } else if (payment instanceof BankTransferPayment) {
                BankTransferPayment bt = (BankTransferPayment) payment;
                sb.append("Virtual Account: ").append(bt.getVirtualAccount()).append("\n");
            }
        }
        // Kita pakai Booking ID sebagai ID Transaksi
        sb.append("ID Transaksi: ").append(bookingDetails.getBookingId()).append("\n"); 
        sb.append("Jumlah Bayar: Rp").append(bookingDetails.getTotalAmount()).append("\n");
        sb.append("------------------------\n");
        
        return sb.toString();
    }

    // Getters (Sesuai UML)
    public String getTicketId() {
        return ticketId;
    }

    public Booking getBookingDetails() {
        return bookingDetails;
    }

    // Setters (Enkapsulasi)
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void setBookingDetails(Booking bookingDetails) {
        this.bookingDetails = bookingDetails;
    }
}
