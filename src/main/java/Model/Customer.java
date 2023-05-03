package Model;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Mariana
 */
public class Customer {
    private final int id;
    private String name;
    private String cpf;
    private Calendar birthdate;
    private boolean active;

//Construtor

    public Customer(int id, String name, String cpf, Calendar birthdate) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.birthdate = birthdate;
    }
    
//Getters and Setters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Calendar getBirthdate() {
        return birthdate;
    }

    public void setDataNasc(Calendar birthdate) {
        this.birthdate = birthdate;
    }
    
    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}