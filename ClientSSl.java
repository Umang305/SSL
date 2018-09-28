package ClientSSl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;


public class ClientSSl
{
    public static void main(String[] args) throws IOException 
    {
        Socket s2 = new Socket("localhost",6666);
        System.out.println(s2.getPort());
        System.out.println(s2.getLocalPort());
        DataInputStream din = new DataInputStream(s2.getInputStream());
        DataOutputStream dout = new DataOutputStream(s2.getOutputStream());
        String s="Hello";
        OTPCipher o =new OTPCipher();
        String me=din.readUTF();
        System.out.println();
        String message = null,key=null,text=null,k=null;
        Scanner sc= new Scanner(System.in);
        if(me.equals("Certificate exists")){
            while(!"bye".equals(text))
            {
                System.out.println(me);
                message = din.readUTF();
                key = din.readUTF();
                text=o.decrypt(message,key);
                System.out.println(message);
                System.out.println(text);
                s = sc.nextLine();
                k= o.RandomAlpha(s.length());
                s = o.encrypt(s, k);
                dout.writeUTF(s);
                dout.writeUTF(k);
            }
        }
        else{
            while(!"bye".equals(s))
            {
                System.out.println(me);
                System.out.println(din.readUTF());
                s = sc.nextLine();
                dout.writeUTF(s);
            }
        }
    }
}   
class OTPCipher{
    String encrypt(String text,String key)
    {
      String alphaU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      String alphaL = "abcdefghijklmnopqrstuvwxyz";
      
      int len = text.length();
      
      String sb = "";
      for(int x=0;x<len;x++){
         char get = text.charAt(x);
         char keyget = key.charAt(x);
         if(Character.isUpperCase(get)){
            int index = alphaU.indexOf(get);
            int keydex = alphaU.indexOf(Character.toUpperCase(keyget));
            
            int total = (index + keydex) % 26;
            
            sb = sb+ alphaU.charAt(total);
         }
         else if(Character.isLowerCase(get)){
            int index = alphaL.indexOf(get);
            int keydex = alphaU.indexOf(Character.toLowerCase(keyget));
            
            int total = (index + keydex) % 26;
            
            sb = sb+ alphaL.charAt(total);
         }
         else{
            sb = sb + get;
         }
      }
      
      return sb;

    }
     public static String RandomAlpha(int len){
      Random r = new Random();
      String key = "";
      for(int x=0;x<len;x++)
         key = key + (char) (r.nextInt(26) + 'A');
      return key;
    }
    public static String decrypt(String text,String key){
      String alphaU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      String alphaL = "abcdefghijklmnopqrstuvwxyz";
      
      int len = text.length();
      
      String sb = "";
      for(int x=0;x<len;x++){
         char get = text.charAt(x);
         char keyget = key.charAt(x);
         if(Character.isUpperCase(get)){
            int index = alphaU.indexOf(get);
            int keydex = alphaU.indexOf(Character.toUpperCase(keyget));

            int total = (index - keydex) % 26;
            total = (total<0)? total + 26 : total;
            
            sb = sb+ alphaU.charAt(total);
         }
         else if(Character.isLowerCase(get)){
            int index = alphaL.indexOf(get);
            int keydex = alphaU.indexOf(Character.toLowerCase(keyget));
            
            int total = (index - keydex) % 26;
            total = (total<0)? total + 26 : total;
            
            sb = sb+ alphaL.charAt(total);
         }
         else{
            sb = sb + get;
         }
      }
      
      return sb;
   }
}