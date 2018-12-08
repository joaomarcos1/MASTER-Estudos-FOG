package master_artigo_nevoa.master;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Transmissor {

    private OutputStream osSaida;
    private PrintWriter pwSaida;
    private Socket dispositivoCliente;


    private String json;

    ArrayList<Long> tempoInicio = new ArrayList<>();
    ArrayList<Long> tempoFim = new ArrayList<>();
    ArrayList<Long> diferenca = new ArrayList<>();

    public void enviar(Modelo modelo, String ip, int porta, boolean teste) {
    
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

    public void envio(String IP, String Porta, String arquivo) throws FileNotFoundException, IOException {

        Thread tsensor3;
        tsensor3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File f = new File(arquivo);
                    FileInputStream in = new FileInputStream(f);
                    Socket socket = new Socket(IP, Integer.parseInt(Porta));
                    OutputStream out = socket.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(out);
                    BufferedWriter writer = new BufferedWriter(osw);
                    writer.write(f.getName() + "\n");
                    writer.flush();
                    int c;
                    int tamanho = 9999999;
                    byte[] buffer = new byte[tamanho];
                    int lidos = -1;
                    tempoInicio.add(System.currentTimeMillis());
                    while ((lidos = in.read(buffer, 0, tamanho)) != -1) {
                        out.write(buffer, 0, lidos);
                    }
                } catch (IOException | NumberFormatException a) {
                    System.out.println("MASTER: ERRO NO ENVIO DO ARQUIVO PARA NÓ IP: " + IP + "/ Porta: " + Porta + " - ERRO: " + a);
                }
            }
        }
        );
        tsensor3.start();

    }

    public void recebeResposta(String IP, String Porta, String arquivos) throws IOException {
        ServerSocket server = new ServerSocket(Integer.parseInt(Porta)+1000); // PEGANDO O VALOR DA PORTA DO SOCKET CRIADO
        Socket clSocket = server.accept();

        Scanner entrada = new Scanner(clSocket.getInputStream());

        long info = entrada.nextLong();

        tempoFim.add(info);
        
        criarLOG_tempoProcessamento tempo = new criarLOG_tempoProcessamento();
        tempo.GerarLOG_tempoProcessamento(tempoInicio, tempoFim, IP, Porta, arquivos);

    }

}
