package bg.tusofia.draw.utils;
import java.io.*;
import java.security.MessageDigest;

public class MD5Checksum {

   private static byte[] createChecksumFromFile(String filename) throws Exception {
       InputStream fis =  new FileInputStream(filename);

       byte[] buffer = new byte[1024];
       MessageDigest complete = MessageDigest.getInstance("MD5");
       int numRead;

       do {
           numRead = fis.read(buffer);
           if (numRead > 0) {
               complete.update(buffer, 0, numRead);
           }
       } while (numRead != -1);

       fis.close();
       return complete.digest();
   }

   // see this How-to for a faster way to convert
   // a byte array to a HEX string
   public static String getMD5ChecksumFromFile(String filename) throws Exception {
       byte[] b = createChecksumFromFile(filename);
       StringBuilder sb = new StringBuilder(128);
       for (int i=0; i < b.length; i++) {
           sb.append( Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 ) );
       }
       return sb.toString();
   }
   
   public static String getMD5Checksum(String str) {
	   StringBuilder sb = new StringBuilder(128);
	   try {
		   MessageDigest md = MessageDigest.getInstance("MD5");
	       md.update(str.getBytes());
	       byte byteData[] = md.digest();
	       for (int i = 0; i < byteData.length; i++) {
	        sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	       }
	   } catch (Exception e){
	   }
       return sb.toString();
   }
  
}