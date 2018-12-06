/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master_artigo_nevoa.master;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author pasid
 */
public class cliente_envioArquivo {

    public static void main(String[] args) {

        //Criando Classe cliente para receber arquivo
        cliente_envioArquivo cliente = new cliente_envioArquivo();

        //Solicitando arquivo
        cliente.getFileFromServeR();
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
            fos = new FileOutputStream(new File("C:\\Users\\pasid\\Documents\\zipado.zip"));
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
            String INPUT_ZIP_FILE = ("C:\\Users\\pasid\\Documents\\zipado.zip");
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

    public void descompacta(String origem, String destino) throws IOException {
        if (origem == null || destino == null) {
            return;
        }
        FileInputStream fis = new FileInputStream(origem);
        ZipInputStream zis = new ZipInputStream(fis);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ZipEntry ze = null;
        //String name = null;
        while ((ze = zis.getNextEntry()) != null) {
            //name = destino + BARRA_INVERTIDA + ze.getName();
            fos = new FileOutputStream(destino); //montaDiretorio(name);
            bos = new BufferedOutputStream(fos, 512000);
            System.out.println("descompactando: '" + ze.getName() + "'");
            int bytes;
            byte buffer[] = new byte[512000];
            while ((bytes = zis.read(buffer, 0, 512000)) != -1) {
                bos.write(buffer, 0, bytes);
            }
            bos.flush();
            bos.close();
        }
        zis.close();
    }
}
