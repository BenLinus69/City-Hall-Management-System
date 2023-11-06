package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class Birou <T extends Utilizator> {
    ArrayList<String> cereriBirou = new ArrayList<String>();
    ArrayList<FunctionarPublic> functionari=new ArrayList<>();

    public void adaugaCerere(T user) {
        String cerere_aux = user.ultimaCerere;
        String[] cerere_arr = cerere_aux.split(" ; ");

        if (cereriBirou.isEmpty()) {//daca lista e goala, o adaug
            cereriBirou.add(cerere_arr[1]+" - "+cerere_arr[0]);
        }
        else{
            int ok=0;
            int prioritate_adaug=Integer.parseInt(cerere_arr[1]);//retin prioritatea cererii
            String[] aux_data=cerere_aux.split(" - ");
            String data_adaug=aux_data[0];//retin data cererii

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
            LocalDateTime date = LocalDateTime.parse(data_adaug, formatter);//convertesc data in formatul potrivit

            for (int i = 0; i < cereriBirou.size(); i++)
            {
                String aux=cereriBirou.get(i);
                String[] cerere_old=aux.split(" - ");
                int prioritate_vechi=Integer.parseInt(cerere_old[0]);
                LocalDateTime date1 = LocalDateTime.parse(cerere_old[1], formatter);//convertesc data veche
                if(prioritate_adaug>prioritate_vechi)
                {
                    cereriBirou.add(i,cerere_arr[1]+" - "+cerere_arr[0]);
                    ok=1;
                    break;
                }
                if(prioritate_adaug==prioritate_vechi)
                {
                    if(date1.isAfter(date))
                    {
                        cereriBirou.add(i,cerere_arr[1]+" - "+cerere_arr[0]);
                        ok=1;
                        break;
                    }
                    else
                    {
                        for(int j=i+1;j<cereriBirou.size();j++)
                        {
                            String aux1=cereriBirou.get(j);
                            String[] cerere_old1=aux1.split(" - ");
                            int prioritate_vechi1=Integer.parseInt(cerere_old1[0]);
                            LocalDateTime date2 = LocalDateTime.parse(cerere_old1[1], formatter);//convertesc data veche
                            if(prioritate_adaug!=prioritate_vechi1)
                            {
                                cereriBirou.add(j,cerere_arr[1]+" - "+cerere_arr[0]);
                                ok=1;
                                break;
                            }
                            if(date2.isAfter(date))
                            {
                                cereriBirou.add(j,cerere_arr[1]+" - "+cerere_arr[0]);
                                ok=1;
                                break;
                            }
                        }
                        break;
                    }

                }
            }
            if(ok==0)
            {
                cereriBirou.add(cereriBirou.size(),cerere_arr[1]+" - "+cerere_arr[0]);
            }



        }

    }
    public void AfisareCereri(String fisierOut,String mesaj)
    {
        if (cereriBirou.isEmpty()==true)
            return;
        try {
            FileWriter fw = new FileWriter(fisierOut, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(mesaj);
            {
                for(int i=0;i<cereriBirou.size();i++)
                {
                    out.println(cereriBirou.get(i));
                }
            }
            bw.close();
        }
        catch (Exception e){
            System.out.println("nu se poate scrie");
        }
    }

}
