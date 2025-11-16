/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

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

    public Schedule(String scheduleId, Route route, Train train, LocalDateTime departureTime, double price) {
        this.scheduleId = scheduleId;
        this.route = route;
        this.train = train;
        this.departureTime = departureTime;
        this.price = price;
    }

    // Getters (Sesuai UML)
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

    // Setters (Enkapsulasi)
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
}
