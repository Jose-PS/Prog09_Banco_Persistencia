package Principal;

import Banco.Banco;
import Banco.ContaAforro;
import Banco.ContaBancaria;
import Banco.ContaEmpresa;
import Banco.ContaPersoal;
import Banco.Persoa;
import Utils.BancoException;
import Utils.CancelException;
import Utils.Error;
import Utils.Inputs;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 *
 * @author DAW
 */
public class Principal {

    private static Banco banco = new Banco();
    private String tipoConta;
    Scanner lec = new Scanner(System.in);

    void run()  {
        char opt = 0;
        String iban;
        String lista[] = null;
        double cant;
        boolean estado = false;
        double saldo;
        Menu m = new Menu("Banco do Hio, escolle unha opcion.", new String[]{"(A)brir nova conta.", "(V)er listado de contas.","(L)istar datos en arquivo txt","(O)bter datos dunha conta.", "(I)ngresar efectivo en conta.", "(R)etirar efectivo de conta.", "(C)onsultar saldo en conta.","(E)liminar conta.", "(S)air."}, "avloirces");
        try {
            load();
        do {
            try {
                opt = m.getOpcion();
                switch (opt) {
                    case 'a' ->
                        abreConta();
                    case 'v' -> {
                        {
                            try {
                                lista = banco.listadoContas();
                            } catch (BancoException ex) {
                                System.out.println(ex.getCodigo());
                            }
                        }
                        for (String lista1 : lista) {
                            System.out.println(lista1);
                        }
                    }
                    case 'l'->{
                        banco.listadoClientestxt();
                    }
                    case 'o' -> {
                        System.out.println("Introduce o IBAN: ");
                        System.out.println(banco.informacionConta(new Scanner(System.in).nextLine()));
                    }
                    case 'i' -> {
                        try {
                            System.out.println("Introduce o IBAN: ");
                            iban = lec.nextLine();
                            System.out.println("Introduce a cantidade: ");
                            cant = Double.parseDouble(lec.nextLine());
                            estado = banco.ingresoConta(iban, cant);
                            if (estado) {
                                System.out.println("Operacion realizada con exito!");
                            } else {
                                System.out.println("Houbo un erro na operacion.");
                            }
                        } catch (NumberFormatException n) {
                            throw new BancoException(Error.NONVALIDO);
                        }
                    }
                    case 'r' -> {
                        try {
                            System.out.println("Introduce o IBAN: ");
                            iban = lec.nextLine();
                            System.out.println("Introduce a cantidade a retirar: ");
                            cant = Double.parseDouble(lec.nextLine());
                            estado = banco.retiradaConta(iban, cant);
                            if (estado) {
                                System.out.println("Operacion realizada con exito!");
                            } else {
                                System.out.println("Houbo un erro na operacion.");
                            }
                        } catch (NumberFormatException n) {
                            throw new BancoException(Error.NONVALIDO);
                        }
                    }
                    case 'c' -> {
                        System.out.println("Introduce o IBAN da conta: ");
                        saldo = banco.obterSaldo(lec.nextLine());
                        if (saldo < 0) {
                            throw new BancoException(Error.NONEXISTE);
                        }
                        System.out.println("O saldo da conta e: " + saldo);
                    }
                    case 'e'->{
                        System.out.println("Introduce o iban da conta a eliminar.");
                        iban=lec.nextLine();
                        if(banco.buscaConta(iban)==null) throw new BancoException(Error.NONEXISTE);
                        banco.eliminaConta(banco.buscaConta(iban));
                    }
                    case 's' -> {
                        break;
                    }
                    }
            } catch (BancoException ex) {
                System.out.println(ex.getCodigo());
            } catch (CancelException ex) {
                System.out.println("Operacion cancelada polo usuario.");
            } catch (IOException ex) {
                System.out.println("Fallo na lectura/escritura do arquivo");
            }
        } while (opt != 's');
        }finally {
            save();
        }
        
    }

    private Persoa novaPersoa() throws CancelException {
        String dni = null;
        String nome = null;
        String apelidos = null;
        Persoa p = null;
        boolean sigue = false;
        do {
            try {
                System.out.println("Introduce os datos do cliente, pulsa * pra cancelar.");
                nome = Inputs.getString("Nome: ");
                apelidos = Inputs.getString("Apelidos: ");
                dni = Inputs.getString("DNI: ");
                p = new Persoa(nome, apelidos, dni);
                sigue=true;
            } catch (BancoException ex) {
                System.out.println(ex.getCodigo());
            }
        } while (!sigue);
        return p;
    }

    private void abreConta() throws CancelException {
        ContaBancaria conta = null;
        Persoa p = null;
        int[] opts;
        String[] datos = new String[7];
        boolean abrirConta = false;
        String[] opcions = new String[]{"Saldo.", "Numero de conta.", "Tipo de interes.", "Comision de mantemento.", "Maximo descuberto permitido.", "Tipo de interes por descuberto.", "Comision fija por descuberto.", "Pulsa * pra cancelar."};
        p = novaPersoa();
        do {
            try {
                opts = getTipo();
                System.out.println("Introduce os datos da conta, pulsa * pra sair.");
                for (int i = 0; i < opts.length - 1; i++) {
                    System.out.println(opcions[opts[i]]);
                    datos[opts[i]] = lec.nextLine();
                    if (datos[opts[i]].charAt(0) == '*') {
                        throw new CancelException();
                    }
                }
                switch (tipoConta.charAt(0)) {
                    case 'e' ->
                        conta = new ContaEmpresa(p, datos[opts[0]], datos[opts[1]], datos[opts[2]], datos[opts[3]], datos[opts[4]]);
                    case 'p' ->
                        conta = new ContaPersoal(p, datos[opts[0]], datos[opts[1]], datos[opts[2]]);
                    case 'a' ->
                        conta = new ContaAforro(p, datos[opts[0]], datos[opts[1]], datos[opts[2]]);
                    default -> {
                    }
                }
                abrirConta = banco.abrirConta(conta);
                if (!abrirConta) {
                    throw new BancoException(Error.NONVALIDO);
                }
            } catch (BancoException ex) {
                System.out.println(ex.getMessage());
            }
        } while (!abrirConta);
    }

    public int[] getTipo() throws CancelException {
        char escolle;
        int[] opts = null;
        Menu m = new Menu("Que tipo de conta desexas crear?", new String[]{"Conta de (E)mpresa", "Conta (P)ersoal", "Conta de (A)forro", "(S)air"}, "epas", Menu.Direccion.HORIZONTAL);
        escolle = m.getOpcion();
        switch (escolle) {
            case 'e' -> {
                tipoConta = "empresa";
                opts = new int[]{0, 1, 4, 5, 6, 7
                };
            }
            case 'p' -> {
                tipoConta = "persoal";
                opts = new int[]{0, 1, 3, 7};
            }
            case 'a' -> {
                tipoConta = "aforro";
                opts = new int[]{0, 1, 2, 7};
            }
            case 's' -> {
                throw new CancelException();
            }
            default -> {
            }
        }
        return opts;
    }
    
    
    public static void save()  {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("DatosContasBancarias.dat"))) {
            out.writeObject(banco);
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
    }

    public static void load()   {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("DatosContasBancarias.dat"))) {
            banco=(Banco) in.readObject();
        } catch (FileNotFoundException ex) {
        } catch (IOException | ClassNotFoundException ex) {
        }
    }

    public static void main(String[] args) {
        Principal main = new Principal();
        main.run();
    }

}
