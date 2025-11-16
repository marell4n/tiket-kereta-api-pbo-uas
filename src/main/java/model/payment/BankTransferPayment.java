/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.payment;

/**
 *
 * @author asus
 */
public class BankTransferPayment extends PaymentMethod{
    private String virtualAccount;
    
    public BankTransferPayment(String virtualAccount) {
        this.paymentName = "Bank Transfer";
        this.virtualAccount = virtualAccount;
    }
    
    // Getter (Sesuai UML)
    public String getVirtualAccount() {
        return virtualAccount;
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
        System.out.println("Memproses pembayaran Bank Transfer sebesar Rp" + amount + "...");
        System.out.println("Silakan transfer ke Virtual Account: " + this.virtualAccount);
        // Simulasi menunggu pembayaran...
        System.out.println("Pembayaran Berhasil.");
        return true;
    }
}
