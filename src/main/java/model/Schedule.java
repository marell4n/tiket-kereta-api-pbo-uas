/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author asus
 */
public class Schedule {
    private String scheduleId;
    private Route route; // Agregasi
    private Train train; // Agregasi
    private LocalDateTime departureTime;
    private double price;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Schedule(String scheduleId, Route route, Train train, LocalDateTime departureTime, double price) {
        this.scheduleId = scheduleId;
        this.route = route;
        this.train = train;
        this.departureTime = departureTime;
        this.price = price;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public Route getRoute() {
        return route;
    }

    public Train getTrain() {
        return train;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public double getPrice() {
        return price;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString() {
        // Cek null untuk keamanan
        if (train == null || route == null) {
            return "Jadwal " + scheduleId;
        }
        // Format: Argo Parahyangan (GMR -> BD) - 08:00
        return train.getTrainName() + " (" + 
               route.getOriginStation().getStationCode() + " -> " + 
               route.getDestinationStation().getStationCode() + ") - " +
               departureTime.format(FORMATTER);
    }
}
