package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class Pensionar extends Utilizator{
    Pensionar(String nume ){
        super(nume);
    }

    public String scriereCerere(String tipCerere,String fisierOut) throws Exception {
        String aux=TipCerere.inlocuire_buletin.name();
        String auxf=aux.replace("_"," ");
        String aux2=TipCerere.inlocuire_carnet_de_sofer.name();
        String aux2f=aux2.replace("_"," ");
        String aux3=TipCerere.inregistrare_cupoane_de_pensie.name();
        String aux3f=aux3.replace("_"," ");

        if((tipCerere.compareTo(auxf))==0 )
        {
            return "Subsemnatul " + this.nume + ", va rog sa-mi aprobati urmatoarea solicitare: "+ auxf;
        }
        else if((tipCerere.compareTo(aux2f))==0)
        {
            return "Subsemnatul " + this.nume + ", va rog sa-mi aprobati urmatoarea solicitare: " +aux2f;
        }
        else if((tipCerere.compareTo(aux3f))==0)
        {
            return "Subsemnatul " + this.nume + ", va rog sa-mi aprobati urmatoarea solicitare: " +aux3f;
        }
        else {
            try{
                FileWriter fw = new FileWriter(fisierOut, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);
                out.println("Utilizatorul de tip pensionar nu poate inainta o cerere de tip "+ tipCerere);
                bw.close();
            }
            catch (Exception e){
                System.out.println("NU MERGE EROAREA");
            }
            throw new Exception("eroare");
        }
    }
    public void creareCerere(String tip,int prioritate,String data,String fisierOut)
    {
        try {

            //System.out.println(cereriAsteptare);
            //System.out.println(data);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
            LocalDateTime date = LocalDateTime.parse(data, formatter);
            //System.out.println(date);

            if (cereriAsteptare.isEmpty()) {
                cereriAsteptare.add(data + " - " + scriereCerere(tip,fisierOut)+ " ; " + prioritate);
                this.ultimaCerere=data + " - " + scriereCerere(tip,fisierOut)+ " ; " + prioritate;
                this.no_exception=1;
            } else {
                int ok=0;
                for (int i = 0; i < cereriAsteptare.size(); i++) {
                    String aux = cereriAsteptare.get(i);
                    String[] auxarr=aux.split(" - ");
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
                    LocalDateTime date1 = LocalDateTime.parse(auxarr[0], formatter);
                    if(date1.isAfter(date))
                    {
                        cereriAsteptare.add(i,data + " - " + scriereCerere(tip,fisierOut)+ " ; " + prioritate);
                        this.ultimaCerere=data + " - " + scriereCerere(tip,fisierOut)+ " ; " + prioritate;
                        this.no_exception=1;
                        ok=1;
                        break;
                    }
                }
                if(ok==0)
                {
                    cereriAsteptare.add(cereriAsteptare.size(),data + " - " + scriereCerere(tip,fisierOut)+ " ; " + prioritate);
                    this.ultimaCerere=data + " - " + scriereCerere(tip,fisierOut)+ " ; " + prioritate;
                    this.no_exception=1;
                }


            }

        }
        catch (Exception e){
            this.no_exception=0;
            System.out.println("nasol moment");
        }
    }
    public void AfisareCereriAsteptare(String fisierOut)
    {
        if (cereriAsteptare.isEmpty()==true)
            return;

        try {
            FileWriter fw = new FileWriter(fisierOut, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(this.nume+ " - cereri in asteptare:");
            {
                for(int i=0;i<cereriAsteptare.size();i++)
                {

                    String[] arr= cereriAsteptare.get(i).split(" ; " );
                    out.println(arr[0]);

                }
            }
            bw.close();
        }
        catch (Exception e){
            System.out.println("nu se poate scrie");
        }
    }
    public void AfisareCereriFinalizate(String fisierOut)
    {
        if (cereriSolutionate.isEmpty()==true)
            return;

        try {
            FileWriter fw = new FileWriter(fisierOut, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(this.nume+ " - cereri in finalizate:");
            {
                for(int i=0;i<cereriSolutionate.size();i++)
                {
                    String[] arr= cereriSolutionate.get(i).split(" ; " );
                    out.println(arr[0]);
                }
            }
            bw.close();
        }
        catch (Exception e){
            System.out.println("nu se poate scrie");
        }
    }
    public void retragereCerere(String date)
    {
        for(int i=0;i<cereriAsteptare.size();i++)
        {
            String aux = cereriAsteptare.get(i);
            String[] auxarr=aux.split(" - ");
            if((auxarr[0].compareTo(date))==0)
            {
                cereriAsteptare.remove(i);
                break;
            }
        }
    }
}
