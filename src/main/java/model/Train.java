/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author asus
 */
public class Train {
    private String trainNumber;
    private String trainName;
    private List<Seat> seats; // Komposisi

    public Train(String trainNumber, String trainName, int totalSeats) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.seats = new ArrayList<>(); // Kursi dibuat saat kereta dibuat
        initializeSeats(totalSeats); // Memanggil method sesuai UML
    }

    /**
     * Method untuk inisialisasi kursi di kereta.
     * Sesuai dengan diagram UML.
     * @param totalSeats Jumlah kursi yang akan dibuat.
     */
    private void initializeSeats(int totalSeats) {
        for (int i = 1; i <= totalSeats; i++) {
            // Contoh format kursi: EKO-01, EKS-01
            String seatNum = "EKO-" + String.format("%02d", i);
            this.seats.add(new Seat(seatNum));
        }
    }

    // Getters (Sesuai UML)
    public String getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public List<Seat> getSeats() {
        return seats;
    }
    
    // Method tambahan (helper) untuk fungsionalitas
    public Seat getSeatByNumber(String seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber().equalsIgnoreCase(seatNumber)) {
                return seat;
            }
        }
        return null;
    }

    // Setters (Enkapsulasi)
    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }
}
