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

    public String getVirtualAccount() {
        return virtualAccount;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("Memproses pembayaran Bank Transfer sebesar Rp" + amount + "...");
        System.out.println("Silakan transfer ke Virtual Account: " + this.virtualAccount);
        System.out.println("Pembayaran Berhasil.");
        return true;
    }
}
