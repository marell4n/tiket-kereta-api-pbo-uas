/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.payment;

/**
 *
 * @author asus
 */
public class CreditCardPayment extends PaymentMethod{
    private String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.paymentName = "Credit Card"; // Set nama pembayaran
        this.cardNumber = cardNumber;
    }
    
    // Getter (Sesuai UML)
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Implementasi (Override) method abstract dari parent.
     * Ini adalah contoh Polymorphism.
     * Sesuai dengan diagram UML.
     * @param amount Jumlah yang harus dibayar.
     * @return true (simulasi selalu sukses).
     */
    @Override
    public boolean pay(double amount) {
        System.out.println("Memproses pembayaran Credit Card sebesar Rp" + amount + "...");
        System.out.println("Menggunakan kartu: " + this.cardNumber);
        // Simulasi validasi kartu...
        System.out.println("Pembayaran Berhasil.");
        return true;
    }
}
