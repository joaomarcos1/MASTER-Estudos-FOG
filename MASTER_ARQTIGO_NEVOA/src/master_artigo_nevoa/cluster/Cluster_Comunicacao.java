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
import java.net.Socket;
import java.util.ArrayList;
import master_artigo_nevoa.master.unzipFile;

/**
 *
 * @author pasid
 */
public class Cluster_Comunicacao {

    public static void main(String[] args) {
        Cluster_Comunicacao cluster = new Cluster_Comunicacao();
        cluster.receberArquivo();
    }

    public void receberArquivo() {

        Socket sockServer = null;
        FileOutputStream fos = null;
        InputStream is = null;

        try {
            // Criando conexão com o servidor
            System.out.println("Conectando com Servidor porta 13267");
            sockServer = new Socket("127.0.0.1", 5000);
            is = sockServer.getInputStream();

            // Cria arquivo local no cliente
            //fos = new FileOutputStream(new File("c:\\temp\\source-copy.zip"));
            fos = new FileOutputStream(new File("Arquivo_Recebido.txt"));
            //System.out.println("Arquivo Local Criado \"C:\\Users\\pasid\\Documents\\zipado.zip");

            // Prepara variaveis para transferencia
            byte[] cbuffer = new byte[1024];
            int bytesRead;

            // Copia conteudo do canal
            System.out.println("Recebendo arquivo...");
            while ((bytesRead = is.read(cbuffer)) != -1) {
                fos.write(cbuffer, 0, bytesRead);
                fos.flush();
            }

            System.out.println("Arquivo recebido!");

            //DESCOMPATAÇÃO DE ARQUIVO
            //String arquivo = ("C:\\Users\\pasid\\Documents\\zipado.zip");
            //String caminho_origem = ("C:\\Users\\pasid\\Music\\zipado.zip");
            //String caminho_saida = ("C:\\Users\\pasid\\Documents\\aa");
            //descompacta(caminho_origem, caminho_saida);
            //String INPUT_ZIP_FILE = ("C:\\Users\\pasid\\Documents\\NetBeansProjects\\MASTER_ARQTIGO_NEVOA\\zipado.zip");
            //String OUTPUT_FOLDER = ("C:\\Users\\pasid\\Documents\\NetBeansProjects\\MASTER_ARQTIGO_NEVOA\\");
            //unzipFile z = new unzipFile();
            //z.unZipIt(INPUT_ZIP_FILE, OUTPUT_FOLDER);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sockServer != null) {
                try {
                    sockServer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

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

    public void contagemPalavras() {
        String texto = null;
        ArrayList<String> palavras = new ArrayList<>();
        ArrayList<String> vezesPalavra = new ArrayList<>();
        File file = new File("ArquivoRecebido.txt");
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
        int total = 0;

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

        //log.GerarLOG(testes);

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
