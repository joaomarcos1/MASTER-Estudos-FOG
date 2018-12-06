/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master_artigo_nevoa.cluster;



import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;


/**
 *
 * @author nupasd-ufpi
 */
public class Cluster_Local extends JFrame {

    //ArrayList<String> palavrasJSON = new ArrayList<>();
    //ArrayList<String> vezesPalavraJSON = new ArrayList<>();
    //VARIÁVEIS PARA CONTAGEM DE PALAVRAS
    public Cluster_Local() {

    }

    public static int contaPalavras(String palavra, String Texto) {
        int quant = 0;
        String texto = Texto;
        String[] arrayString = texto.split(" ");
        for (int i = 0; i < arrayString.length; i++) {
            if (arrayString[i].equals(palavra)) {
                quant++;
            }
        }
        return quant;
    }

    public static void main(String[] args) throws IOException {

        String texto = null;
        ArrayList<String> palavras = new ArrayList<>();
        ArrayList<String> vezesPalavra = new ArrayList<>();
        File file = new File(args[0]);
        //ArrayList<String> arq = new ArrayList<>();
        //for (int i = 0; i < files.length; i++) {
        //    System.out.println(files[i]);
        //}
        System.out.println("Status do Processamento: Processando Arquivo");
        try {

            int cont = 0;
            System.out.println("Lendo Arquivo!");
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (br.ready()) {
                String linha = br.readLine();
                texto = linha;

                System.out.println(linha + "\n");
                System.out.println("próxima linha");
                cont++;
            }
            br.close();
            //System.out.println(arra.get(0));

            String[] texto1 = texto.split(" ");

            for (int i = 0; i < 10; i++) {
                System.out.println("Palavra: " + texto1[i]);
                palavras.add(texto1[i]);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        //CHAMANDO FUNÇÃO CONTAGEM PALAVRAS ABAIXO
        StringBuilder enviar = new StringBuilder();

        int counter = 0, total = Integer.valueOf(args[1]);

        ArrayList<String> testes = new ArrayList<>();
        
        while (counter < total) {
            //Experimentos
            double inicial = System.currentTimeMillis();
            for (int i = 0; i < palavras.size(); i++) {
                vezesPalavra.add((Integer.toString(contaPalavras(palavras.get(i), texto))));
            }

            double fim = System.currentTimeMillis();

            double tempoProcessamento = fim - inicial;

            testes.add(Double.toString(tempoProcessamento));
            counter++;
        }

        criarLOG log = new criarLOG();
        
        log.GerarLOG(testes);
        
        System.out.println("Status do Processamento: Finalizado");

        //System.out.println("Tempo de Realização da Tarefa: " + tempoProcessamento + "milissegundos");
        //System.out.println(Double.toString(tempoProcessamento));

        StringBuilder resultados = new StringBuilder();
        //for (int i = 0; i < palavras.size(); i++) {
        //    resultados.append("Palavra: " + palavras.get(i) + " - Vezes: " + vezesPalavra.get(i) + "\n");
        //}

        System.out.println(resultados);

    }

}
