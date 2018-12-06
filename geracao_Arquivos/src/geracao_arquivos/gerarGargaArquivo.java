/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geracao_arquivos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

import geracao_arquivos.GerarArquivo;

/**
 *
 * @author pasid
 */
public class gerarGargaArquivo {

    public static void main(String[] args) throws IOException {

        Scanner ler = new Scanner(System.in);
        String frase = null;
        int i, quantidade;

        //System.out.println("Inserir quantidade de palavras:");
        //quantidade = ler.nextInt();

        //String article[] = {"the", "a", "one", "some", "any"};
        //String noun[] = {"boy", "the", "dog", "town", "car"};
        //String verb[] = {"drove", "jumped", "ran", "walked", "skipped"};
        //String preposition[] = {"to", "from", "over", "under", "on"};

        String article[] = {"the"};
        String noun[] = {"the"};
        String verb[] = {"drove"};
        String preposition[] = {"over"};
        
        
        
        String conteudo;
        ArrayList<String> texto = new ArrayList<>();

        //GERANDO VALORES ALEATÓRIOS APENAS UMA VEZ PARA CADA PALAVRA
        //SERÃO GERADOS APENAS AS MEMAS PALAVRAS NO ARQUIVO
        
        int articleLength = article.length;
        int nounLength = noun.length;
        int verbLength = verb.length;
        int prepositionLength = preposition.length;

        int randArticle = (int) (Math.random() * articleLength);
        int randNoun = (int) (Math.random() * nounLength);
        int randVerb = (int) (Math.random() * verbLength);
        int randPreposition = (int) (Math.random() * prepositionLength);
        int randArticle2 = (int) (Math.random() * articleLength);
        int randNoun2 = (int) (Math.random() * nounLength);
        
        int randArticle10 = (int) (Math.random() * articleLength);
        int randNoun10 = (int) (Math.random() * nounLength);
        int randVerb10 = (int) (Math.random() * verbLength);
        int randPreposition10 = (int) (Math.random() * prepositionLength);

        
        int qt_megas = Integer.valueOf(args[0]);
        
        
        for (i = 0; i < ((qt_megas * 225000)/ 10); i++) {
            //tamanhos totais palavras de cada tipo 

            frase = article[randArticle] + " " + noun[randNoun] + " " + verb[randVerb] + " " + preposition[randPreposition] + " " + article[randArticle] + " " + noun[randNoun2] + " " + article[randArticle10] + " " + noun[randNoun10] + " " + verb[randVerb10] + " " + preposition[randPreposition10] + " ";
      
            texto.add(frase);

        }

        System.out.println("Texto Gerado!");

        //Enviando varga de text para os arquivos
        GerarArquivo gerar = new GerarArquivo();

        gerar.GerarArquivo(texto);

    }

   
}
