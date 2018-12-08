package master_artigo_nevoa.master;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Helbert Monteiro
 */
public class Conexao {

    

    public static void main(String[] args) throws IOException {

        ArrayList<Node> listaNodes = new ArrayList<>();
        
        for (int i = 0; i < args.length; i=+2) {
            Node node = new Node();
            node.setIp(String.valueOf(args[i]));
            node.setPorta(String.valueOf(args[i+1]));
            
            listaNodes.add(node);

        }
        
        new Gerencia().run(9000, listaNodes);

    }
}
