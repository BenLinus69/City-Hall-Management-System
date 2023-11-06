package org.example;

import java.io.*;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
public class ManagementPrimarie {

    public static void main(String[] args) throws IOException , ParseException {
        String antetInput = "src/main/resources/input/";
        String antetOut="src/main/resources/output/";
        ArrayList <Utilizator> users = new ArrayList<>();
       // ArrayList <Birou> birouri = new ArrayList<>();
        Birou<Utilizator> birou_angajati= new Birou<>();
        Birou<Utilizator> birou_elevi= new Birou<>();
        Birou<Utilizator> birou_entitati_juridice= new Birou<>();
        Birou<Utilizator> birou_pensionari= new Birou<>();
        Birou<Utilizator> birou_persoane= new Birou<>();
       // birouri.add(new Angajat())



        //Citire fisier
        File outputfolder=new File(antetOut);
        FileUtils.cleanDirectory(outputfolder);
       try {
        BufferedReader br = new BufferedReader(new FileReader((antetInput+args[0])));
        String line;
            while((line = br.readLine()) != null)
            {
                String[] arr = line.split("; ");


                if((arr[0].compareTo("adauga_utilizator"))==0)
                {
                    //System.out.print("ADAUG");

                    switch(arr[1])
                    {
                        case "angajat":
                        {
                            users.add(new Angajat(arr[2],arr[3]));
                            break;
                        }
                        case "pensionar":
                        {
                            users.add(new Pensionar(arr[2]));
                            break;
                        }
                        case "elev":
                        {
                            users.add(new Elev(arr[2],arr[3]));
                            break;
                        }
                        case "persoana":
                        {
                            users.add(new Persoana(arr[2]));
                            break;
                        }
                        case "entitate juridica":
                        {
                            users.add(new EntitateJuridica(arr[2],arr[3]));
                            break;
                        }
                        default:
                            System.out.println("UPSS");
                    }
                }
                else if ((arr[0].compareTo("cerere_noua"))==0)
                {
                    int ok=0;
                    int i;
                    for( i=0;i<users.size();i++)//verific daca userul exista in sistem
                    {
                        Utilizator aux=users.get(i);
                        if((aux.nume.compareTo(arr[1]))==0)
                        {
                            ok=1;
                            break;
                        }
                    }
                    if(ok==1)
                    {
                        Utilizator orice = users.get(i);
                        int prioritate = Integer.parseInt(arr[4]);
                        orice.creareCerere(arr[2], prioritate, arr[3],(antetOut+args[0]));//creez cererea
                        if(orice instanceof Angajat && orice.no_exception!=0) {
                            birou_angajati.adaugaCerere(orice);
                        }
                        else if(orice instanceof Elev && orice.no_exception!=0) {
                            birou_elevi.adaugaCerere(orice);
                        }
                        else if(orice instanceof EntitateJuridica && orice.no_exception!=0) {
                            birou_entitati_juridice.adaugaCerere(orice);
                        }
                        else if(orice instanceof Pensionar && orice.no_exception!=0) {
                            birou_pensionari.adaugaCerere(orice);
                        }
                        else if(orice instanceof Persoana && orice.no_exception!=0) {
                            birou_persoane.adaugaCerere(orice);
                        }
                    }


                }
                else if((arr[0].compareTo("afiseaza_cereri_in_asteptare"))==0)
                {

                    int i;
                    int ok=1;
                    for( i=0;i<users.size();i++)//caut userul caruia trebuie sa ii afisez cererile
                    {
                        Utilizator aux=users.get(i);
                        if((aux.nume.compareTo(arr[1]))==0)
                        {
                            ok=1;
                            break;
                        }
                    }
                    if(ok==1)
                    {
                        Utilizator afisator = users.get(i);
                        afisator.AfisareCereriAsteptare(antetOut + args[0]);
                    }
                }
                else if((arr[0].compareTo("retrage_cerere"))==0)
                {
                    int i;
                    int ok=1;
                    for( i=0;i<users.size();i++)//caut userul caruia trebuie sa ii afisez cererile
                    {
                        Utilizator aux=users.get(i);
                        if((aux.nume.compareTo(arr[1]))==0)
                        {
                            ok=1;
                            break;
                        }
                    }
                    if(ok==1)
                    {
                        Utilizator stergator = users.get(i);
                        stergator.retragereCerere(arr[2]);
                        if(stergator instanceof Angajat) {
                            for(int j=0;j<birou_angajati.cereriBirou.size();j++)
                            {
                                String[] test=birou_angajati.cereriBirou.get(j).split(" - ");
                                if((test[1].compareTo(arr[2]))==0)
                                {
                                    birou_angajati.cereriBirou.remove(j);
                                }
                            }
                        }
                        else if(stergator instanceof Elev) {
                            for(int j=0;j<birou_elevi.cereriBirou.size();j++)
                            {
                                String[] test=birou_elevi.cereriBirou.get(j).split(" - ");
                                if((test[1].compareTo(arr[2]))==0)
                                {
                                    birou_elevi.cereriBirou.remove(j);
                                }
                            }
                        }
                        else if(stergator instanceof EntitateJuridica) {
                            for(int j=0;j<birou_entitati_juridice.cereriBirou.size();j++)
                            {
                                String[] test=birou_entitati_juridice.cereriBirou.get(j).split(" - ");
                                if((test[1].compareTo(arr[2]))==0)
                                {
                                    birou_entitati_juridice.cereriBirou.remove(j);
                                }
                            }
                        }
                        else if(stergator instanceof Pensionar) {
                            for(int j=0;j<birou_pensionari.cereriBirou.size();j++)
                            {
                                String[] test=birou_pensionari.cereriBirou.get(j).split(" - ");
                                if((test[1].compareTo(arr[2]))==0)
                                {
                                    birou_pensionari.cereriBirou.remove(j);
                                }
                            }
                        }
                        else if(stergator instanceof Persoana) {
                            for(int j=0;j<birou_persoane.cereriBirou.size();j++)
                            {
                                String[] test=birou_persoane.cereriBirou.get(j).split(" - ");
                                if((test[1].compareTo(arr[2]))==0)
                                {
                                    birou_persoane.cereriBirou.remove(j);
                                }
                            }
                        }
                    }
                }
                else if ((arr[0].compareTo("afiseaza_cereri"))==0)
                {
                    switch(arr[1])
                    {
                        case "angajat":
                        {
                            String mesaj="angajat - cereri in birou:";
                            birou_angajati.AfisareCereri(antetOut+args[0],mesaj);
                            break;
                        }
                        case "pensionar":
                        {
                            String mesaj="pensionar - cereri in birou:";
                            birou_pensionari.AfisareCereri(antetOut+args[0],mesaj);
                            break;
                        }
                        case "elev":
                        {
                            String mesaj="elev - cereri in birou:";
                            birou_elevi.AfisareCereri(antetOut+args[0],mesaj);
                            break;
                        }
                        case "persoana":
                        {
                            String mesaj="persoana - cereri in birou:";
                            birou_persoane.AfisareCereri(antetOut+args[0],mesaj);
                            break;
                        }
                        case "entitate juridica":
                        {
                            String mesaj="entitate juridica - cereri in birou:";
                            birou_entitati_juridice.AfisareCereri(antetOut+args[0],mesaj);
                            break;
                        }
                        default:
                            System.out.println("UPSS");
                    }
                }
                else if((arr[0].compareTo("adauga_functionar"))==0)
                {
                    switch(arr[1])
                    {
                        case "angajat":
                        {
                            birou_angajati.functionari.add(new FunctionarPublic(arr[2]));
                            break;
                        }
                        case "pensionar":
                        {
                            birou_pensionari.functionari.add(new FunctionarPublic(arr[2]));
                            break;
                        }
                        case "elev":
                        {
                            birou_elevi.functionari.add(new FunctionarPublic(arr[2]));
                            break;
                        }
                        case "persoana":
                        {
                            birou_persoane.functionari.add(new FunctionarPublic(arr[2]));
                            break;
                        }
                        case "entitate juridica":
                        {
                            birou_entitati_juridice.functionari.add(new FunctionarPublic(arr[2]));
                            break;
                        }
                        default:
                            System.out.println("UPSS");
                    }
                }
                else if((arr[0].compareTo("rezolva_cerere"))==0)
                {
                    switch(arr[1])
                    {
                        case "angajat":
                        {
                            int ok=0;
                            for(int i=0;i<birou_angajati.functionari.size();i++)
                            {
                                if((birou_angajati.functionari.get(i).nume.compareTo(arr[2]))==0)
                                {
                                    ok=1;
                                    break;
                                }
                            }
                            if(ok==1)
                            {
                                String cerere_rezolvata=birou_angajati.cereriBirou.get(0);
                                birou_angajati.cereriBirou.remove(0);
                                String[] arr_data=cerere_rezolvata.split(" - ");
                                String data =arr_data[1];
                                String[] arr_nume=cerere_rezolvata.split(",");
                                String[] arr_nume1=arr_nume[0].split(" ");
                                String nume=arr_nume1[6]+" "+arr_nume1[7];
                                try{//scrie fisierele functionarilor
                                    FileWriter fw = new FileWriter(antetOut+"functionar_"+arr[2]+".txt", true);
                                    BufferedWriter bw = new BufferedWriter(fw);
                                    PrintWriter out = new PrintWriter(bw);
                                    out.println(data + " - "+ nume);
                                    bw.close();
                                }
                                catch (Exception e){
                                    System.out.println("eroare");
                                }
                                int i;
                                int ok1=1;
                                for( i=0;i<users.size();i++)//caut userul caruia trebuie sa ii sterg cererea
                                {
                                    Utilizator aux=users.get(i);
                                    if((aux.nume.compareTo(nume))==0)
                                    {
                                        ok1=1;
                                        break;
                                    }
                                }
                                if(ok1==1)
                                {
                                    Utilizator sterg=users.get(i);
                                    for(int k=0;k<sterg.cereriAsteptare.size();k++)
                                    {
                                        String cerere_aux=sterg.cereriAsteptare.get(k);
                                        String[] data_aux=cerere_aux.split(" - ");
                                        if((data_aux[0].compareTo(data))==0)
                                        {
                                            sterg.cereriAsteptare.remove(k);
                                            sterg.cereriSolutionate.add(cerere_aux);
                                        }
                                    }

                                }


                            }

                            break;
                        }
                        case "pensionar":
                        {
                            int ok=0;
                            for(int i=0;i<birou_pensionari.functionari.size();i++)
                            {
                                if((birou_pensionari.functionari.get(i).nume.compareTo(arr[2]))==0)
                                {
                                    ok=1;
                                    break;
                                }
                            }
                            if(ok==1)
                            {
                                String cerere_rezolvata=birou_pensionari.cereriBirou.get(0);
                                birou_pensionari.cereriBirou.remove(0);
                                String[] arr_data=cerere_rezolvata.split(" - ");
                                String data =arr_data[1];
                                String[] arr_nume=cerere_rezolvata.split(",");
                                String[] arr_nume1=arr_nume[0].split(" ");
                                String nume=arr_nume1[6]+" "+arr_nume1[7];
                                try{//scrie fisierele functionarilor
                                    FileWriter fw = new FileWriter(antetOut+"functionar_"+arr[2]+".txt", true);
                                    BufferedWriter bw = new BufferedWriter(fw);
                                    PrintWriter out = new PrintWriter(bw);
                                    out.println(data + " - "+ nume);
                                    bw.close();
                                }
                                catch (Exception e){
                                    System.out.println("eroare");
                                }
                                int i;
                                int ok1=1;
                                for( i=0;i<users.size();i++)//caut userul caruia trebuie sa ii sterg cererea
                                {
                                    Utilizator aux=users.get(i);
                                    if((aux.nume.compareTo(nume))==0)
                                    {
                                        ok1=1;
                                        break;
                                    }
                                }
                                if(ok1==1)
                                {
                                    Utilizator sterg=users.get(i);
                                    for(int k=0;k<sterg.cereriAsteptare.size();k++)
                                    {
                                        String cerere_aux=sterg.cereriAsteptare.get(k);
                                        String[] data_aux=cerere_aux.split(" - ");
                                        if((data_aux[0].compareTo(data))==0)
                                        {
                                            sterg.cereriAsteptare.remove(k);
                                            sterg.cereriSolutionate.add(cerere_aux);
                                        }
                                    }

                                }


                            }

                            break;
                        }
                        case "elev":
                        {
                            int ok=0;
                            for(int i=0;i<birou_elevi.functionari.size();i++)
                            {
                                if((birou_elevi.functionari.get(i).nume.compareTo(arr[2]))==0)
                                {
                                    ok=1;
                                    break;
                                }
                            }
                            if(ok==1)
                            {
                                String cerere_rezolvata=birou_elevi.cereriBirou.get(0);
                                birou_elevi.cereriBirou.remove(0);
                                String[] arr_data=cerere_rezolvata.split(" - ");
                                String data =arr_data[1];
                                String[] arr_nume=cerere_rezolvata.split(",");
                                String[] arr_nume1=arr_nume[0].split(" ");
                                String nume=arr_nume1[6]+" "+arr_nume1[7];
                                try{//scrie fisierele functionarilor
                                    FileWriter fw = new FileWriter(antetOut+"functionar_"+arr[2]+".txt", true);
                                    BufferedWriter bw = new BufferedWriter(fw);
                                    PrintWriter out = new PrintWriter(bw);
                                    out.println(data + " - "+ nume);
                                    bw.close();
                                }
                                catch (Exception e){
                                    System.out.println("eroare");
                                }
                                int i;
                                int ok1=1;
                                for( i=0;i<users.size();i++)//caut userul caruia trebuie sa ii sterg cererea
                                {
                                    Utilizator aux=users.get(i);
                                    if((aux.nume.compareTo(nume))==0)
                                    {
                                        ok1=1;
                                        break;
                                    }
                                }
                                if(ok1==1)
                                {
                                    Utilizator sterg=users.get(i);
                                    for(int k=0;k<sterg.cereriAsteptare.size();k++)
                                    {
                                        String cerere_aux=sterg.cereriAsteptare.get(k);
                                        String[] data_aux=cerere_aux.split(" - ");
                                        if((data_aux[0].compareTo(data))==0)
                                        {
                                            sterg.cereriAsteptare.remove(k);
                                            sterg.cereriSolutionate.add(cerere_aux);
                                        }
                                    }

                                }


                            }

                            break;
                        }
                        case "persoana":
                        {
                            int ok=0;
                            for(int i=0;i<birou_persoane.functionari.size();i++)
                            {
                                if((birou_persoane.functionari.get(i).nume.compareTo(arr[2]))==0)
                                {
                                    ok=1;
                                    break;
                                }
                            }
                            if(ok==1)
                            {
                                String cerere_rezolvata=birou_persoane.cereriBirou.get(0);
                                birou_persoane.cereriBirou.remove(0);
                                String[] arr_data=cerere_rezolvata.split(" - ");
                                String data =arr_data[1];
                                String[] arr_nume=cerere_rezolvata.split(",");
                                String[] arr_nume1=arr_nume[0].split(" ");
                                String nume=arr_nume1[6]+" "+arr_nume1[7];
                                try{//scrie fisierele functionarilor
                                    FileWriter fw = new FileWriter(antetOut+"functionar_"+arr[2]+".txt", true);
                                    BufferedWriter bw = new BufferedWriter(fw);
                                    PrintWriter out = new PrintWriter(bw);
                                    out.println(data + " - "+ nume);
                                    bw.close();
                                }
                                catch (Exception e){
                                    System.out.println("eroare");
                                }
                                int i;
                                int ok1=1;
                                for( i=0;i<users.size();i++)//caut userul caruia trebuie sa ii sterg cererea
                                {
                                    Utilizator aux=users.get(i);
                                    if((aux.nume.compareTo(nume))==0)
                                    {
                                        ok1=1;
                                        break;
                                    }
                                }
                                if(ok1==1)
                                {
                                    Utilizator sterg=users.get(i);
                                    for(int k=0;k<sterg.cereriAsteptare.size();k++)
                                    {
                                        String cerere_aux=sterg.cereriAsteptare.get(k);
                                        String[] data_aux=cerere_aux.split(" - ");
                                        if((data_aux[0].compareTo(data))==0)
                                        {
                                            sterg.cereriAsteptare.remove(k);
                                            sterg.cereriSolutionate.add(cerere_aux);
                                        }
                                    }

                                }


                            }

                            break;
                        }
                        case "entitate juridica":
                        {
                            int ok=0;
                            for(int i=0;i<birou_entitati_juridice.functionari.size();i++)
                            {
                                if((birou_entitati_juridice.functionari.get(i).nume.compareTo(arr[2]))==0)
                                {
                                    ok=1;
                                    break;
                                }
                            }
                            if(ok==1)
                            {
                                String cerere_rezolvata=birou_entitati_juridice.cereriBirou.get(0);
                                birou_entitati_juridice.cereriBirou.remove(0);
                                String[] arr_data=cerere_rezolvata.split(" - ");
                                String data =arr_data[1];
                                String[] arr_nume=cerere_rezolvata.split(",");
                                String[] arr_nume1=arr_nume[1].split(" ");
                                String nume=arr_nume1[5]+" "+arr_nume1[6];
                                try{//scrie fisierele functionarilor
                                    FileWriter fw = new FileWriter(antetOut+"functionar_"+arr[2]+".txt", true);
                                    BufferedWriter bw = new BufferedWriter(fw);
                                    PrintWriter out = new PrintWriter(bw);
                                    out.println(data + " - "+ nume);
                                    bw.close();
                                }
                                catch (Exception e){
                                    System.out.println("eroare");
                                }
                                int i;
                                int ok1=0;
                                for( i=0;i<users.size();i++)//caut userul caruia trebuie sa ii sterg cererea
                                {
                                    Utilizator aux=users.get(i);
                                    if((aux.nume.compareTo(nume))==0)
                                    {
                                        ok1=1;
                                        break;
                                    }
                                }
                                if(ok1==1)
                                {
                                    Utilizator sterg=users.get(i);
                                    for(int k=0;k<sterg.cereriAsteptare.size();k++)
                                    {
                                        String cerere_aux=sterg.cereriAsteptare.get(k);
                                        String[] data_aux=cerere_aux.split(" - ");
                                        if((data_aux[0].compareTo(data))==0)
                                        {
                                            sterg.cereriAsteptare.remove(k);
                                            sterg.cereriSolutionate.add(cerere_aux);
                                        }
                                    }

                                }


                            }

                            break;
                        }
                        default:
                            System.out.println("UPSS");
                    }
                }
                else if((arr[0].compareTo("afiseaza_cereri_finalizate"))==0)
                {
                    int i;
                    int ok=1;
                    for( i=0;i<users.size();i++)//caut userul caruia trebuie sa ii afisez cererile
                    {
                        Utilizator aux=users.get(i);
                        if((aux.nume.compareTo(arr[1]))==0)
                        {
                            ok=1;
                            break;
                        }
                    }
                    if(ok==1)
                    {
                        Utilizator afisator = users.get(i);
                        afisator.AfisareCereriFinalizate(antetOut + args[0]);
                    }
                }
            }

        br.close();
      } catch (IOException e) {
            System.out.println("Eroare");
       }
    }
}