/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Banco;

import java.io.Serializable;

/**
 *
 * @author DAW
 */
public abstract class ContaBancaria implements Imprimible, Serializable {
    public enum TipoConta {AFORRO, EMPRESA, PERSOAL}
    protected Persoa titular;
    protected double saldo;
    protected String iban;
    protected TipoConta tipoConta;

    public ContaBancaria(Persoa titular, double saldo, String iban, TipoConta tipoConta) {
        this.titular = titular;
        this.saldo = saldo;
        this.iban = iban;
        this.tipoConta = tipoConta;
    }

    public String getIban() {
        return iban;
    }

    public double getSaldo() {
        return saldo;
    }
    
    

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    
    
    

    

}
