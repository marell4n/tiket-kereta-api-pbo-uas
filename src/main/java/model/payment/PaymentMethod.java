/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.payment;

/**
 *
 * @author asus
 */
public abstract class PaymentMethod {
     // Protected sesuai UML
    protected String paymentName;

    // Sesuai UML
    public String getPaymentName() {
        return paymentName;
    }

    /**
     * Abstract method untuk proses pembayaran.
     * Akan di-override oleh child class (Polymorphism).
     * Sesuai dengan diagram UML.
     * @param amount Jumlah yang harus dibayar.
     * @return true jika pembayaran sukses.
     */
    public abstract boolean pay(double amount);
}
