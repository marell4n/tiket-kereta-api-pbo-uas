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
    protected String paymentName;

    public String getPaymentName() {
        return paymentName;
    }

    public abstract boolean pay(double amount);
}
