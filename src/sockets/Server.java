package sockets;

// Keeping imports easy to remember.
import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // Port number to bind server to.
        int portNum = 11113;
        ServerSocket listener = new ServerSocket(portNum);
        System.out.println("Servidor corriendo en el puerto: " + portNum);

        while (true) {
            try {
                //Aceptando peticiones
                Socket clientSocket = listener.accept();
                
                //stream de entradas y salida
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                String nombre = (String) in.readObject();
                String response = "Nombre recibido.";
                out.writeObject(response);
                System.out.println("Nombre: "+nombre);
                
                String file = nombre;
                byte[] receivedData;
                int val;
                receivedData = new byte[1024];
                BufferedInputStream auxi = new BufferedInputStream(clientSocket.getInputStream());
                DataInputStream dis=new DataInputStream(clientSocket.getInputStream());

                BufferedOutputStream auxo = new BufferedOutputStream(new FileOutputStream(file));
                while ((val = auxi.read(receivedData)) != -1)
                {
                   auxo.write(receivedData,0,val);
                }
                
                dis.close();
                auxo.close();
                out.close();
                in.close();
                clientSocket.close();
            } 
            finally {
                // Closing Server Socket now.
                //listener.close();
            }
        }
    }

}
