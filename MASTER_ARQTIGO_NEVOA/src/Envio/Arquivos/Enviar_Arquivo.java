/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Envio.Arquivos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author pasid
 */
public class Enviar_Arquivo {

    public static void main(String[] argss) throws FileNotFoundException, IOException {

        //Scanner tc = new Scanner(System.in);
        //System.out.println("File:");
        //String fName = tc.nextLine();
        //File f = new File("CAMINHO DE ORIGEM DO FILE/" + fName);
        File f = new File("geracao_Arquivos.jar");
        FileInputStream in = new FileInputStream(f);
        Socket socket = new Socket("127.0.0.1", 5000);
        OutputStream out = socket.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(out);
        BufferedWriter writer = new BufferedWriter(osw);
        writer.write(f.getName() + "\n");
        writer.flush();
        int c;
        int tamanho = 99999999; // buffer de 4KB  
        byte[] buffer = new byte[tamanho];
        int lidos = -1;
        while ((lidos = in.read(buffer, 0, tamanho)) != -1) {
            out.write(buffer, 0, lidos);
        }

    }
}
