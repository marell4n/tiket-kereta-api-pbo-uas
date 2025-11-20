/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author asus
 */
public class Seat {
    private String seatNumber;
    private boolean isAvailable;

    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
        this.isAvailable = true; // Default kursi tersedia saat dibuat
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void bookSeat() {
        if (isAvailable) {
            this.isAvailable = false;
        } else {
            System.out.println("Gagal booking, kursi " + seatNumber + " sudah terisi.");
        }
    }

    public void cancelBooking() {
        this.isAvailable = true;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
