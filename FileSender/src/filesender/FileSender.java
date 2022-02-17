/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesender;

/**
 *
 * @author kosti
 */
import java.io.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kosti
 */
public class FileSender {
    
   
    public static final int SERVER_PORT = 1855;
  
    public static final int BUFFER_SIZE = 6144;
    
     public static File file = new File("C:\\test\\база zyf201.png");   
  
    public void sendFile (File file){
        
        try(Socket socket = new Socket("127.0.0.1", SERVER_PORT)){
            OutputStream os = socket.getOutputStream();
            String str = newFileName(file);                        
            byte[]buffName = str.getBytes();  
            os.write(buffName );                                //отправляем имя будущего файла;
            FileInputStream fis = new FileInputStream(file);    
            int i; 
             byte[]buff = new byte[BUFFER_SIZE];
            while((i=fis.read(buff))>-1){
                os.write(buff, 0, i);
                os.flush();
            }
        }catch(IOException io){}
    }
   
     private String newFileName (File file){    // метод создаёт имя будущего файла, добавляя перед расширением "-copy"
        String filename = file.getPath();
        char[] chArray = filename.toCharArray();
        List<Character> list1 = new ArrayList<Character>();
        List<Character> list2 = new ArrayList<Character>();

        for (int i = 0; i<chArray.length; i++)
        {  list1.add(chArray[i]);
           list2.add(chArray[i]);
        }

        for (int i = list1.size()-1; i>0; i--)
        {
            if (list1.get(i) != 46){
             list1.remove(i);}
            else 
            {
                list1.remove(i);
                break;
            }
        }
        for (int i = 0; i<=list1.size(); i++)
        {
              list2.remove(0);  
        }

      for (int i = 0; i<list1.size(); i++)
        {
          if (chArray[i] == 92){
         list1.add((i+1), chArray[i]);}
        }

        for (int i = (list1.size()-1); i<list2.size(); i++)
        {
          if (chArray[i] == 92){
         list2.add((i+1), chArray[i]);}    
        }   

      StringBuilder sb1 = new StringBuilder();
        for (Character ch: list1) {
            sb1.append(ch);
        }
      StringBuilder sb2 = new StringBuilder();
        for (Character ch: list2) {
            sb2.append(ch);
        }   
       String newFileName = (sb1.toString() + "-copy" + "." + sb2.toString() + " ");
       // System.out.println(newFileName);                            
          return newFileName;
}

    public static void main(String[] args) {
        FileSender client = new FileSender();
        client.sendFile(file);
    }
}

