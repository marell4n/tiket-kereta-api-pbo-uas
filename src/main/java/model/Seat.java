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

    // Sesuai UML
    public String getSeatNumber() {
        return seatNumber;
    }

    // Sesuai UML
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Method untuk mem-booking kursi.
     * Sesuai dengan diagram UML.
     */
    public void bookSeat() {
        if (isAvailable) {
            this.isAvailable = false;
        } else {
            System.out.println("Gagal booking, kursi " + seatNumber + " sudah terisi.");
        }
    }

    /**
     * Method untuk membatalkan booking kursi.
     * Sesuai dengan diagram UML.
     */
    public void cancelBooking() {
        this.isAvailable = true;
    }

    // Setter (Enkapsulasi tambahan)
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
