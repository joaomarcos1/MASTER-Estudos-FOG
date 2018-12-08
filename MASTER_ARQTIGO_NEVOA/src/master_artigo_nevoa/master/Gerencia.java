package master_artigo_nevoa.master;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Helbert Monteiro
 */
public class Gerencia {

    private ServerSocket socketServidor;
    private Socket dispositivoCliente;

    private Scanner scanner;
    private String json, subTexto;
    private int index, codigoCliente, codigoNode = 0;
    private String[] palavras;

    private Gson gson;
    private Modelo modelo, modeloRetorno;
    private NodePalavra nodePalavra;
    private ArrayList<NodePalavra> listaNodePalavra;
    private Node cliente;

    //NOVA METODOLOGIA PARA LEITURA DE ARQUIVO
    ArrayList<String> palavrasTEXTO = new ArrayList<>();
    ArrayList<String> vezesPalavra = new ArrayList<>();

    String texto = null;

    ServerSocket server;

    

    public void run(int porta, ArrayList<Node> listaNodes) throws IOException {

        while (true) {

            receber_Arquivo();

            lerArquivo();

            if (listaNodes.size() > 0) {
                distribuirConteudo(listaNodes); //FUNÇÃO PARA LER O ARQUIVO RECEBIDO DISTRIBUIR SEU CONTEÚDO PARA A QUANTIDADE DE NÓS CADASDTRADOS
                for (int i = 0; i < listaNodes.size(); i++) {
                    Transmissor trans = new Transmissor();
                    //new Transmissor().envio(listaNodes.get(i).getIp(), listaNodes.get(i).getPorta(), "RASP0" + i + ".txt");
                    trans.envio(listaNodes.get(i).getIp(), listaNodes.get(i).getPorta(), "RASP0" + i + ".txt");
                    trans.recebeResposta(listaNodes.get(i).getIp(), listaNodes.get(i).getPorta(), "RASP0" + i + ".txt");
                
                }
            }
            listaNodes.clear();
        }

    }

    private void recebe() {
        Socket sockServer = null;
        FileOutputStream fos = null;
        InputStream is = null;

        try {
            // Criando conexão com o servidor
            System.out.println("Conectando com Servidor porta 13267");
            sockServer = new Socket("127.0.0.1", 13267);
            is = sockServer.getInputStream();

            // Cria arquivo local no cliente
            //fos = new FileOutputStream(new File("c:\\temp\\source-copy.zip"));
            fos = new FileOutputStream(new File("C:\\Users\\pasid\\Documents\\zipado.zip"));
            System.out.println("Arquivo Local Criado \"C:\\Users\\pasid\\Documents\\zipado.zip");

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
            String INPUT_ZIP_FILE = ("C:\\Users\\pasid\\Music\\zipado.zip");
            String OUTPUT_FOLDER = ("C:\\Users\\pasid\\Documents\\");
            unzipFile z = new unzipFile();

            z.unZipIt(INPUT_ZIP_FILE, OUTPUT_FOLDER);

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

    private void distribuir(Modelo modelo, ArrayList<Node> listaNodes) {
        palavras = texto.split(" ");
        listaNodePalavra = new ArrayList<>();
        index = 0;

        for (int i = 0; i < listaNodes.size(); i++) {
            nodePalavra = new NodePalavra(i);
            listaNodePalavra.add(nodePalavra);
        }

        for (int i = 0; i < palavras.length; i++) {
            if (index < listaNodes.size()) {
                listaNodePalavra.get(index).addPalavra(palavras[i]);
                index++;
            } else {
                index = 0;
                listaNodePalavra.get(index).addPalavra(palavras[i]);
                index++;
            }
        }

        System.out.println("Texto Inteiro: " + texto);
        subTexto = "";

        for (int i = 0; i < listaNodePalavra.size(); i++) {
            for (int j = 0; j < listaNodePalavra.get(i).getListaPalavras().size(); j++) {
                subTexto = subTexto + listaNodePalavra.get(i).getListaPalavras().get(j) + " ";
            }
            System.out.println("Palavras separadas para Node " + i + ": " + subTexto);

            modelo = new Modelo(modelo.getCodigo(), modelo.getPalavras(), subTexto);

            new Transmissor().enviar(modelo, listaNodes.get(i).getIp(), Integer.parseInt(listaNodes.get(i).getPorta()), false);

            subTexto = "";
        }
    }

    private void reduzir(Modelo modelo, Node cliente, ArrayList<Node> listaNodes) {
        for (int j = 0; j < modeloRetorno.getVezesPalavras().size(); j++) {
            modeloRetorno.acrescentaValorVez(j, modelo.getValorVez(j));
        }
        codigoNode++;
        if (codigoNode == listaNodes.size()) {
            modelo = new Modelo(modelo.getPalavras(), modeloRetorno.getVezesPalavras());
            new Transmissor().enviar(modelo, cliente.getIp(), Integer.parseInt(cliente.getPorta()), true);
            codigoCliente = 0;
            codigoNode = 0;
        }
    }

    private void getFileFromServeR() {
        Socket sockServer = null;
        FileOutputStream fos = null;
        InputStream is = null;

        try {
            // Criando conexão com o servidor
            System.out.println("Conectando com Servidor porta 13267");
            sockServer = new Socket("127.0.0.1", 13267);
            is = sockServer.getInputStream();

            // Cria arquivo local no cliente
            //fos = new FileOutputStream(new File("c:\\temp\\source-copy.zip"));
            fos = new FileOutputStream(new File("zipado.zip"));
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
            String INPUT_ZIP_FILE = ("C:\\Users\\pasid\\Documents\\NetBeansProjects\\MASTER_ARQTIGO_NEVOA\\zipado.zip");
            String OUTPUT_FOLDER = ("C:\\Users\\pasid\\Documents\\NetBeansProjects\\MASTER_ARQTIGO_NEVOA\\");
            unzipFile z = new unzipFile();

            z.unZipIt(INPUT_ZIP_FILE, OUTPUT_FOLDER);

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

    private void distribuirConteudo(ArrayList<Node> listaNodes) throws IOException {

        int i;

        String textox[] = texto.split(" ");

        int resto = textox.length % 2;
        int total = 0;//PARA DEFINIR QUANTAS PALAVRAS PARA CADA NÓ
        //DIVISÃO DE CONTEÚDO POR QUANTIDAADE DE NÓS DISPONÍVEIS
        //SE A QUANTIDADE DE PALAVRAS FOR PAR
        System.out.println("Quantidade de Palavras: " + textox.length);
        total = textox.length / listaNodes.size();
        System.out.println("Tamanho: " + total);
        int indexPalavra = 0;
        boolean confere = false;

        //System.out.println(textox[1124999]);
        try {
            for (int a = 0; a < listaNodes.size(); a++) {
                System.out.println("Indice Palavra: " + indexPalavra);
                System.out.println("Total: " + total);
                FileWriter arq = new FileWriter("RASP0" + a + ".txt");
                PrintWriter gravarArq = new PrintWriter(arq);

                //gravarArq.printf("+--Resultado--+%n");
                for (i = (indexPalavra); i < (total); i++) {
                    //gravarArq.printf("| %2d X %d = %2d |%n", i, n, (i * n));
                    gravarArq.printf(textox[i] + " ");
                    indexPalavra = i - 1;
                }
                //gravarArq.printf("+-------------+%n");

                arq.close();
                System.out.println("Arquivo RASP0" + a + " Gerado!");
                total += total;
                //System.out.print("\Arquivo Salvo ");
            }
        } catch (RuntimeException e) {
            System.out.println("Erro na Execução");
        }

    }

    private void lerArquivo() throws IOException {

        String INPUT_ZIP_FILE = ("zipado.zip");
        String OUTPUT_FOLDER = ("C:\\Users\\pasid\\Documents\\NetBeansProjects\\MASTER_ARQTIGO_NEVOA\\dist");
        //File arquivo = new File("");
        //String OUTPUT_FOLDER = arquivo.getPath();
        unzipFile z = new unzipFile();

        z.unZipIt(INPUT_ZIP_FILE, OUTPUT_FOLDER);

        System.out.println("Arquivo Descompactado!");

        File file = new File("ArquivoGerado.txt");

        try {

            int cont = 0;
            System.out.println("Lendo Arquivo!");
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (br.ready()) {
                String linha = br.readLine();
                texto = linha;

                System.out.println(linha + "\n");
                System.out.println("Leitura do Arquivo Finalizada");
                cont++;
            }
            br.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void enviarArquivosParaNos() {

    }

    private void receber_Arquivo() throws IOException {
        try {
            ServerSocket server = new ServerSocket(9000);
            System.out.println("Esperando Receber Arquivo...");
            Socket clSocket = server.accept();

            InputStream in = clSocket.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(isr);
            String fName = reader.readLine();
            System.out.println(fName);
            //File f1 = new File("C:\\Users\\pasid\\Music\\" + fName);
            File f1 = new File(fName);
            FileOutputStream out = new FileOutputStream(f1);
            int tamanho = 99999999;
            byte[] buffer = new byte[tamanho];
            int lidos;
            //while ((lidos = in.read(buffer, 0, tamanho)) != -1) {
            while ((lidos = in.read(buffer)) != -1) {
                //System.out.println(lidos);
                out.write(buffer, 0, lidos);
                out.flush();
                return;

            }

            server.close();
            clSocket.close();

            System.out.println("Arquivo Recebido!");

        } catch (IOException e) {
            System.out.println("MASTER: Erro no recebimento do Arquivo");
        }
    }

}
