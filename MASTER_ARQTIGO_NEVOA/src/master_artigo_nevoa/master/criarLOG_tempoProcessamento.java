/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master_artigo_nevoa.master;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author pasid
 */
public class criarLOG_tempoProcessamento {
    
    
     public void GerarLOG_tempoProcessamento(ArrayList<Long> inicio, ArrayList<Long> fim, String IP, String Porta, String arquivos) throws IOException {

        
        int i;

        //System.out.printf("Informe o número para a tabuada:\n");
        //n = ler.nextInt();

        FileWriter arq = new FileWriter("Resultados-No_IP_"+IP+"_Porta_"+Porta+".txt");
        PrintWriter gravarArq = new PrintWriter(arq);
        long diferenca;
        //gravarArq.printf("+--Resultado--+%n");
        for (i = 0; i < inicio.size(); i++) {
            //gravarArq.printf("| %2d X %d = %2d |%n", i, n, (i * n));
            diferenca = fim.get(i) - inicio.get(i);
            //gravarArq.printf(diferenca+" %n");
            gravarArq.append(Long.toString(diferenca)+"%n");
        }
        //gravarArq.printf("+-------------+%n");

        arq.close();
        System.out.println("Arquivo de LOG com tempo de No_IP_"+IP+"_Porta_"+Porta+" Gerado!");
        //System.out.print("\Arquivo Salvo ");

    }
    
    
}
