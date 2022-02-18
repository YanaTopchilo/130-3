/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileReceiver;

/**
 *
 * @author kosti
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Класс предназначен для получения произвольного файла по сети с использованием
 * стека протоколов TCP/IP.
 *
 * @author (C)Y.D.Zakovryashin, 30.11.2020
 */
public class FileReceiver {

   
    public static final int SERVER_PORT = 1855;
  
    public static final int BUFFER_SIZE = 6144;
 
    public static void main(String[] args) {
        System.out.println("File receiver started...");
        new FileReceiver().run();
        System.out.println("File receiver finished.");
      
    }

    private void run() {
        try (ServerSocket ss = new ServerSocket(SERVER_PORT);
                Socket s = ss.accept();
                InputStream in = s.getInputStream();
                OutputStream out = s.getOutputStream()) {
            byte[] buf = new byte[BUFFER_SIZE];
            int n = in.read(buf);
            File file = createFile(new String(buf, 0, n));
            try (FileOutputStream fos = new FileOutputStream(file)) {
                while (true) {
                    n = in.read(buf);
                    if (n < 0) {
                        break;
                    }
                    fos.write(buf, 0, n);
                }
            }
            out.write("Transfer file finished.".getBytes());
        } catch (IOException e) {
            System.err.println("Error #1: " + e.getMessage());
        }
    }

    private File createFile(String fileName) throws IOException {
        fileName = fileName.trim();
        if (fileName.isEmpty()) {
            fileName = "default_name";
        } 
        System.out.println(fileName);
        return new File(fileName);
        
    }
}

