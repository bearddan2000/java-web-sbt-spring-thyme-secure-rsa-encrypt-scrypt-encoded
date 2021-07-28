package example.security;

import java.security.KeyPair;
import javax.xml.bind.DatatypeConverter;

import org.springframework.security.crypto.password.PasswordEncoder;

public class RSAPasswordEncoder extends org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
  implements PasswordEncoder {

    KeyPair keypair = null;

    public RSAPasswordEncoder() throws Exception {
      super();
      try {
        this.keypair = RSAUtils.generateRSAKeyPair();
      } catch(Exception e) {}
    }


    @Override
    public java.lang.String encode(java.lang.CharSequence rawPassword)
    {
      try {
        String plainText = rawPassword.toString();
        byte[] rsaText = RSAUtils.encrypt(plainText, this.keypair);
        return super.encode(DatatypeConverter.printHexBinary(rsaText));
      } catch(Exception e) {}
      return super.encode(rawPassword);
    }

    @Override
    public boolean matches(java.lang.CharSequence rawPassword, java.lang.String encodedPassword)
    {
     try {
        String plainText = rawPassword.toString();
        byte[] rsaText = RSAUtils.encrypt(plainText, this.keypair);
        plainText = DatatypeConverter.printHexBinary(rsaText);
       return super.matches(plainText, encodedPassword);
     } catch(Exception e) {}
     return false;
    }
}
