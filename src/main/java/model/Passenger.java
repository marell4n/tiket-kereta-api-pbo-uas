package model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author asus
 */
public class Passenger {
    private String passengerId;
    private String password;
    private String name;
    private String email;

    public Passenger(String passengerId, String password, String name, String email) {
        this.passengerId = passengerId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    /**
     * Method simulasi login.
     * Sesuai dengan diagram UML.
     * @param email Email yang dimasukkan
     * @param password Password yang dimasukkan
     * @return true jika login berhasil, false jika gagal.
     */
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    // Getters (Sesuai UML)
    public String getPassengerId() {
        return passengerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Setters (Enkapsulasi)
    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
