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
    
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Memproses pembayaran Credit Card sebesar Rp" + amount + "...");
        System.out.println("Menggunakan kartu: " + this.cardNumber);
        System.out.println("Pembayaran Berhasil.");
        return true;
    }
}
