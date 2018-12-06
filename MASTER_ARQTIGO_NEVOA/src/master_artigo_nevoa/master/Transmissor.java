package master_artigo_nevoa.master;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.Gson;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Transmissor {

    private OutputStream osSaida;
    private PrintWriter pwSaida;
    private Socket dispositivoCliente;
    private Gson gson;
    private GeradorGrafico geradorGrafico;

    private String json;

    public void enviar(Modelo modelo, String ip, int porta, boolean teste) {
        geradorGrafico = new GeradorGrafico();
        try {
            //gson = new Gson();
            //json = gson.toJson(modelo);
            System.out.println("Preparando para enviar mensagem...");
            dispositivoCliente = new Socket(ip, porta);
            osSaida = dispositivoCliente.getOutputStream();
            pwSaida = new PrintWriter(osSaida);
            pwSaida.print(json);
            System.out.println("Parametros enviados" + json);
            System.out.println("Mensagem enviada!!!\n");
            osSaida.flush();
            pwSaida.flush();

            osSaida.close();
            pwSaida.close();
        } catch (IOException erro) {
            System.out.println("Erro no envio da mensagem: " + erro.getMessage());
        }
        /*if(teste){
            for(int i = 0; i < modelo.getPalavras().size(); i++){
                geradorGrafico.addValor(Double.parseDouble(modelo.getVezesPalavras().get(i)), modelo.getPalavras().get(i), modelo.getPalavras().get(i), i);
            }
            geradorGrafico.exibeGrafico();
        }*/
    }

    public void envio(int Porta, String arquivo) {

        // Checa se a transferencia foi completada com sucesso
        OutputStream socketOut = null;
        ServerSocket servsock = null;
        FileInputStream fileIn = null;

        try {
            // Abrindo porta para conexao de clients
            servsock = new ServerSocket(Porta);
            System.out.println("Porta de conexao aberta "+Porta);

            // Cliente conectado
            Socket sock = servsock.accept();
            //System.out.println("Conexao recebida pelo cliente");

            // Criando tamanho de leitura
            byte[] cbuffer = new byte[1024];
            int bytesRead;

            // Criando arquivo que sera transferido pelo servidor
            //C:\Users\pasid\Music
            //File file = new File("C:\\Users\\pasid\\Music\\zipado.zip");
            File file = new File(arquivo);
            fileIn = new FileInputStream(file);
            System.out.println("Lendo arquivo...");

            // Criando canal de transferencia
            socketOut = sock.getOutputStream();

            // Lendo arquivo criado e enviado para o canal de transferencia
            System.out.println("Enviando Arquivo...");
            while ((bytesRead = fileIn.read(cbuffer)) != -1) {
                socketOut.write(cbuffer, 0, bytesRead);
                socketOut.flush();
            }

            System.out.println("Arquivo Enviado!");
        } catch (Exception e) {
            // Mostra erro no console
            e.printStackTrace();
        } finally {
            if (socketOut != null) {
                try {
                    socketOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (servsock != null) {
                try {
                    servsock.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fileIn != null) {
                try {
                    fileIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
