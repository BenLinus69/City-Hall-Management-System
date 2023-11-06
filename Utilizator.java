package org.example;

import java.util.ArrayList;

public abstract class Utilizator {
    String nume;
    int no_exception;
    ArrayList<String> cereriAsteptare =new ArrayList<String>();
    ArrayList<String> cereriSolutionate =new ArrayList<String>();

    String ultimaCerere;
    Utilizator(String nume){
        this.nume=nume;
    }
    abstract public String scriereCerere(String tipCerere,String fisierOut)  throws Exception;
    abstract public void creareCerere(String tip,int prioritate,String data,String fisierOut);
    abstract void retragereCerere(String date);
//    abstract void afisareCereriSolutionate();
    abstract void AfisareCereriAsteptare(String fisierOut);
    abstract void AfisareCereriFinalizate(String fisierOut);

}
