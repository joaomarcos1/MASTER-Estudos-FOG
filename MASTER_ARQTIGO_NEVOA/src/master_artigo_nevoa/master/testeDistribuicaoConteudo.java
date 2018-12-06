/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master_artigo_nevoa.master;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author pasid
 */
public class testeDistribuicaoConteudo {

    String texto = null;

    public static void main(String[] args) throws IOException {
        ArrayList<Node> listaNodes = new ArrayList<>();
        Node node = new Node();
        node.setIp("127.0.0.1");
        node.setPorta("5000");
        Node node1 = new Node();
        node.setIp("127.0.0.1");
        node.setPorta("5001");
        Node node2 = new Node();
        node.setIp("127.0.0.1");
        node.setPorta("5002");
        listaNodes.add(node);
        listaNodes.add(node1);
        listaNodes.add(node2);

        testeDistribuicaoConteudo teste = new testeDistribuicaoConteudo();
        teste.lerArquivo();
        teste.distribuirConteudo(listaNodes);

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

        System.out.println(textox[1124999]);

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
        }catch (RuntimeException e) {
            System.out.println("Erro na Execução");
        }

    }

    private void lerArquivo() {

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

}
