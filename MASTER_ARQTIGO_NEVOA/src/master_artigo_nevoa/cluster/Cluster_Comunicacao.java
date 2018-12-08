/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master_artigo_nevoa.cluster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import master_artigo_nevoa.master.unzipFile;

/**
 *
 * @author pasid
 */
public class Cluster_Comunicacao {

    String caminho = "C:\\Users\\pasid\\Music\\";
    String Arquivo = null;

    public static void main(String[] args) throws IOException {
        Cluster_Comunicacao cluster = new Cluster_Comunicacao();
        cluster.receberArquivo(args[0]);
    }

    public void receberArquivo(String quantidade) throws IOException {

        try {
            while (true) {
                ServerSocket server = new ServerSocket(5000);
                System.out.println("Esperando Receber Arquivo...");
                Socket clSocket = server.accept();

                InputStream in = clSocket.getInputStream();
                InputStreamReader isr = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(isr);
                String fName = reader.readLine();
                System.out.println(fName);
                //File f1 = new File("C:\\Users\\pasid\\Music\\" + fName);
                File f1 = new File(caminho + fName);
                Arquivo = caminho + fName;
                //File f1 = new File("ArquivoRecebido.txt");
                FileOutputStream out = new FileOutputStream(f1);
                int tamanho = 99999999;
                byte[] buffer = new byte[tamanho];
                int lidos;
                //while ((lidos = in.read(buffer, 0, tamanho)) != -1) {
                /*
            while ((lidos = in.read(buffer)) != -1) {
                //System.out.println(lidos);
                out.write(buffer, 0, lidos);
                out.flush();
            }
                 */
                while ((lidos = in.read(buffer)) != -1) {
                    //System.out.println(lidos);
                    out.write(buffer, 0, lidos);
                    out.flush();
                    //
                    contagemPalavras(quantidade);
                    return;
                }

                server.close();
                clSocket.close();

                System.out.println("Arquivo Recebido!");
                return;
            }
        } catch (IOException e) {
            System.out.println("CLUSTER: Erro no recebimento do Arquivo - ERRO: " + e);
        }

    }

    public int contaPalavras(String palavra, String Texto) {
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

    public void contagemPalavras(String quantidade) throws IOException {
        String texto = null;
        ArrayList<String> palavras = new ArrayList<>();
        ArrayList<String> vezesPalavra = new ArrayList<>();
        File file = new File(Arquivo);
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

        int counter = 0;
        int total = Integer.valueOf(quantidade);

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
