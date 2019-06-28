package bg.tusofia.draw.utils;

import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Enc {
  //-------------------------------------------------------------------------------
    private static final String ALGO = "AES";
    private static final String UTF8 = "UTF-8";
  //-------------------------------------------------------------------------------
    public Enc(){

    }
  //-------------------------------------------------------------------------------
    public static String encStr(String data, byte[] keyValue) {
        String res = data;
        try {
            if(data != null) {
                Key key = getKey(keyValue);
                Cipher cipher = Cipher.getInstance(ALGO);
                cipher.init(Cipher.ENCRYPT_MODE, key); 
                byte[] ph1 = cipher.doFinal(data.getBytes(UTF8));
                byte[] ph2 = Base64.getEncoder().encode(ph1);
                res = new String(ph2, UTF8);
                        
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
  //-------------------------------------------------------------------------------
    public static String decStr(String data, byte[] keyValue) {
        String res = data;
        try {
            if(data != null) {
                Key key = getKey(keyValue);
                Cipher cipher = Cipher.getInstance(ALGO);
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] ph1 = Base64.getDecoder().decode(data.getBytes(UTF8));
                byte[] ph2 = cipher.doFinal(ph1);
                res = new String(ph2, UTF8);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
  //-------------------------------------------------------------------------------
    private static Key getKey(byte[] keyValue) throws Exception {
        return new SecretKeySpec(keyValue, ALGO);
    }
  //-------------------------------------------------------------------------------
}
