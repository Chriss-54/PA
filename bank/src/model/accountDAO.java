package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class accountDAO {
    private account account;
    private final String path = "src/resources/";
    
    public accountDAO() {
        super();
        this.account = new account();
    }
    
    public accountDAO(model.account account) {
        super();
        this.account = account;
    }
    
    // Método para escribir cuenta (corregido)
    public boolean writerAccount() throws IOException {
        FileWriter out = new FileWriter(path + account.getNCuenta() + ".txt", true);
        out.write(account.toString());
        out.close();
        return true;
    }
    
    // Método para leer todas las cuentas
    public List<account> readAllAccounts() throws IOException {
        List<account> accounts = new ArrayList<>();
        // Este método leería todos los archivos de cuentas
        // Implementación simplificada
        return accounts;
    }
    
    // Método para leer una cuenta específica
    public account readAccount(int nCuenta) throws IOException {
        // Implementación para leer una cuenta específica
        return account;
    }
}