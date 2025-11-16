/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import model.payment.*;

/**
 *
 * @author asus
 */
public class Booking {
     private String bookingId;
    private Passenger passenger;
    private Schedule schedule;
    private Seat selectedSeat;
    private PaymentMethod paymentMethod; // Polimorfisme di sini
    private double totalAmount;
    private boolean isPaid;

    /**
     * Constructor sesuai UML.
     */
    public Booking(String bookingId, Passenger passenger, Schedule schedule, Seat selectedSeat) {
        this.bookingId = bookingId;
        this.passenger = passenger;
        this.schedule = schedule;
        this.selectedSeat = selectedSeat;
        this.totalAmount = schedule.getPrice(); // Ambil harga dari schedule
        this.isPaid = false;
    }

    // Getters (Sesuai UML)
    public String getBookingId() {
        return bookingId;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Seat getSelectedSeat() {
        return selectedSeat;
    }
    
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public boolean isPaid() {
        return isPaid;
    }

    /**
     * Method untuk set payment method.
     * Sesuai dengan diagram UML.
     * @param paymentMethod Bisa CreditCardPayment atau BankTransferPayment.
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    // Setters (Enkapsulasi)
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setSelectedSeat(Seat selectedSeat) {
        this.selectedSeat = selectedSeat;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
}
