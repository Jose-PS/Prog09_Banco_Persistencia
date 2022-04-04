/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Banco;

import Utils.BancoException;
import Utils.Error;
import Utils.Valida;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author DAW
 */
public class Banco implements Serializable {

    private HashMap<String, ContaBancaria> contas=new HashMap<>();
    private int pos;

    /**
     * Inserta unha conta bancaria no banco.Maximo 100 contas.
     *
     * @param cb
     * @return Devolve true ou false en funcion de se tivo exito.
     * @throws Utils.BancoException
     */
    public boolean abrirConta(ContaBancaria cb) throws BancoException {
        return contas.putIfAbsent(cb.getIban(), cb) == null;
    }

    /**
     * Recibe un IBAN e busca unha conta.
     *
     * @param iban
     * @return Devolve un obxeto ContaBancaria no caso de atopala ou null se non
     * a atopa.
     * @throws BancoException
     *
     */
    public ContaBancaria buscaConta(String iban) throws BancoException {
        return contas.get(iban);
    }

    /**
     * Recibe un obxeto contaBancaria e a sobreescribe no banco
     *
     * @param cv
     * @return Devolve true ou false dependendo de se saiu ben ou non.
     */
    public boolean actualizaConta(ContaBancaria cv) {
        return contas.put(cv.iban, cv)==null;
    }

    /**
     * Lista as contas que hai no banco
     *
     * @return Devolve un array coa informacion de cada conta
     * @throws Utils.BancoException
     */
    public String[] listadoContas() throws BancoException {
        String[] lista = new String[contas.size()];
        int i = -1;
        for (ContaBancaria c : contas.values()) {
            lista[++i] = c.devolverInfoString();
        }
        return lista;
    }

    /**
     * Recibe un iban pra dar a informacion da conta.
     *
     * @param iban
     * @return Devolve un String coa informacion da conta ou null se non existe.
     * @throws Utils.BancoException
     */
    public String informacionConta(String iban) throws BancoException {
        Valida.validaIban(iban);
        if (buscaConta(iban)==null) throw new BancoException(Error.NONEXISTE);
        return contas.get(iban).devolverInfoString();
    }

    /**
     * Introducindo o iban e a cantidade, fai o ingreso na conta indicada.
     *
     * @param iban
     * @param cant
     * @return devolve true ou false en funcion de como saiu a operacion.
     * @throws Utils.BancoException
     */
    public boolean ingresoConta(String iban, double cant) throws BancoException {
        Valida.validaIban(iban);
        ContaBancaria cv;
        if (cant < 0) throw new BancoException(Error.NONVALIDO, "Introduce un valor positivo");
        cv = buscaConta(iban);
        if (cv==null) throw new BancoException(Error.NONEXISTE);
        cv.setSaldo(cv.getSaldo() + cant);
        return actualizaConta(cv);
    }

    /**
     * Introducindo o iban e a cantidade, retira os cartos da conta indicada.
     *
     * @param iban
     * @param cant
     * @return Devolve true ou false en funcion de como saiu a operacion.
     * @throws Utils.BancoException
     */
    public boolean retiradaConta(String iban, double cant) throws BancoException {
        Valida.validaIban(iban);
        ContaBancaria cv;
        if (cant < 0) throw new BancoException(Error.NONVALIDO, "Introduce un valor positivo");
        cv = buscaConta(iban);
        if (cv==null) throw new BancoException(Error.NONEXISTE);
        cv.setSaldo(cv.getSaldo() - cant);
        if (cv.getSaldo() <= 0) return false;
        return actualizaConta(cv);
    }

    /**
     * Recibe un iban e mostra o saldo da conta
     *
     * @param iban
     * @return devolve un double co saldo da conta ou -1 se non a atopa.
     * @throws Utils.BancoException
     */
    public double obterSaldo(String iban) throws BancoException {
        Valida.validaIban(iban);
        if (buscaConta(iban)==null) throw new BancoException(Error.NONEXISTE);
        return buscaConta(iban).getSaldo();
    }
    
    /**
     * Elimina a conta do banco
     *
     * @param cb un obxeto conta bancaria
     * @return true ou false dependendo de se tivo exito
     * @throws BancoException
     */
    public boolean eliminaConta(ContaBancaria cb) throws BancoException { 
        return contas.remove(cb.getIban(), cb);
    }

    public void listadoClientestxt() throws IOException{
        try (RandomAccessFile lista=new RandomAccessFile("ListadoClientesCCC.txt", "rw")){
            for(ContaBancaria c:contas.values()){
                lista.writeUTF(c.getIban());
                lista.writeUTF(c.titular.devolverInfoString()+"\n");             
            }
            lista.writeUTF("Contas no banco: ");
            lista.writeUTF(Integer.toString(contas.size()));
        }
    }
    
    
    

}
