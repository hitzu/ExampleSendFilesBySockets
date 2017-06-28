package sockets;

import java.io.*;
import java.net.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author darren
 */
public class Client {

    public static void main(String arg[]) throws IOException, ClassNotFoundException {

        int portNum = 11113;
        Socket socket = new Socket("localhost", portNum);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        // Integer Object to send to Server.
        //Integer num = 20;
        //out.writeObject(num);
        //String response = (String) in.readObject();
        //System.out.println("Mensaje de servidor : " + response);
        
        try
        {
            JFileChooser buscaarchivo = new JFileChooser();
            buscaarchivo.setCurrentDirectory(new java.io.File("."));
            buscaarchivo.setDialogTitle("Archivo a abrir");
            //FileNameExtensionFilter("Archivos de texto","txt");
            int status = buscaarchivo.showOpenDialog(null);// Da un entero
            // Si apretamos en aceptar ocurrirá esto
            if (status == JFileChooser.APPROVE_OPTION) 
            {
                File archivo = buscaarchivo.getSelectedFile();
                //JOptionPane.showMessageDialog(null,"el archivo seleccionado es"+archivo.getName());
                out.writeObject(archivo.getName());
                String response = (String) in.readObject();
                System.out.println("Mensaje de servidor: " + response);
                BufferedInputStream auxArchivo = new BufferedInputStream(new FileInputStream(archivo)); 
                BufferedOutputStream auxSalida = new BufferedOutputStream(socket.getOutputStream());
                int val;
                byte[] byteArray;
                byteArray = new byte[8192];
                while ((val = auxArchivo.read(byteArray)) != -1)
                {
                    auxSalida.write(byteArray, 0, val);
                }
                JOptionPane.showMessageDialog(null,"El archivo se transfirio correctamente :D");
            } 
        
            socket.close();        
        }
        catch (IOError e)
        {
            JOptionPane.showMessageDialog(null,"Error al cargar el archivo: " + e.toString());
        }
    }
}
