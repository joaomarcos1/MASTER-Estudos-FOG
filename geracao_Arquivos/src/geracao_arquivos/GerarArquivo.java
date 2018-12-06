/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geracao_arquivos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author pasid
 */
public class GerarArquivo {

    public void GerarArquivo(ArrayList<String> texto) throws IOException {

        Scanner ler = new Scanner(System.in);
        int i, n;

        //System.out.printf("Informe o número para a tabuada:\n");
        //n = ler.nextInt();

        FileWriter arq = new FileWriter("ArquivoGerado.txt");
        PrintWriter gravarArq = new PrintWriter(arq);

        //gravarArq.printf("+--Resultado--+%n");
        for (i = 0; i < texto.size(); i++) {
            //gravarArq.printf("| %2d X %d = %2d |%n", i, n, (i * n));
            gravarArq.printf(texto.get(i));
        }
        //gravarArq.printf("+-------------+%n");

        arq.close();
        System.out.println("Arquivo Gerado!");
        //System.out.print("\Arquivo Salvo ");

    }

}
