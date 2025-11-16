/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author asus
 */
public class Route {
     private String routeId;
    private Station originStation;
    private Station destinationStation;

    public Route(String routeId, Station originStation, Station destinationStation) {
        this.routeId = routeId;
        this.originStation = originStation;
        this.destinationStation = destinationStation;
    }

    // Getters (Sesuai UML)
    public String getRouteId() {
        return routeId;
    }

    public Station getOriginStation() {
        return originStation;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    // Setters (Enkapsulasi)
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setOriginStation(Station originStation) {
        this.originStation = originStation;
    }

    public void setDestinationStation(Station destinationStation) {
        this.destinationStation = destinationStation;
    }
}
