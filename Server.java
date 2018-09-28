package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Server
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket s1= new ServerSocket(6666);
        Socket s3= s1.accept();
        DataInputStream din = new DataInputStream(s3.getInputStream());
        DataOutputStream dout = new DataOutputStream(s3.getOutputStream());
        Scanner sc= new Scanner(System.in);
        Certificate c=new Certificate(000000,"rth5",1,1,"tuo3");
        String a = null;
        OTPCipher o = new OTPCipher();
        String s="hello";       
        String k=null;
        String message = null,key=null,text=null;
        if(c!=null){
            a="Certificate exists";
            dout.writeUTF(a);
            System.out.println("Enter the message to be sent");
            while(!"bye".equals(text))
            {
                s = sc.nextLine();
                k= o.RandomAlpha(s.length());
                s = o.encrypt(s, k);
                dout.writeUTF(s);
                dout.writeUTF(k);
                message = din.readUTF();
                key = din.readUTF();
                text=o.decrypt(message,key);
                System.out.println(text);   
            }
        }
        else{
            a="Certificate doesnt exist";
            dout.writeUTF(a);
            System.out.println("Enter the message to be sent");
            while(!"bye".equals(s))
            {
                s= sc.nextLine();
                dout.writeUTF(s);
                System.out.println(din.readUTF());
            }
        }
    }
}
class Certificate
{
    double serial;
    String issuer;
    int valid;
    int key;
    String subject;
    Certificate(double serial, String issuer,int valid,int key,String subject){
        this.serial = serial;
        this.issuer = issuer;
        this.key= key;
        this.subject=subject;
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
