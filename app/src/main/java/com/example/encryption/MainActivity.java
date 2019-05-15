package com.example.encryption;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    EditText inputText;
    TextView outputText;
    Button encBtn , decBtn;
    String outputString , login="Marcin" , password="Test" ;
    String userKey="";
    String AES = "AES";
    String keyAp = "To jest klucz taki";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = (EditText) findViewById(R.id.inputText);

        outputText =(TextView) findViewById(R.id.outputText);
        encBtn= (Button) findViewById(R.id.encBtn);
        decBtn= (Button) findViewById(R.id.decBtn);


        encBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputString = encrypt(inputText.getText().toString(), userKey.toString());
                    outputText.setText(outputString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputString=decrypt(outputString,userKey.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                outputText.setText(outputString);
            }
        });
    }

    private String decrypt(String outputString, String userKey) throws Exception {
        SecretKeySpec key = generateKey(userKey);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte [] decodedValue = Base64.decode(outputString, Base64.DEFAULT);
        byte [] decValue = c.doFinal(decodedValue);
        String decryptedValue =  new String(decValue);

        return decryptedValue;
    }

    private String encrypt(String Data, String userKey) throws Exception {
        SecretKeySpec key = generateKey(userKey);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte [] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal,Base64.DEFAULT);
        return encryptedValue;
    }
    private  String generateUserKey(String login, String password) {
        String keyApp="";
        String login3 = login.substring(0, 3);
        String password3 = password.substring(password.length() - 3);
        try {
           keyApp= generateKey(keyAp).toString();


        } catch (Exception e) {
            e.printStackTrace();
        }
        String userKey = login3 + password3+keyApp;

        return userKey;
    }
    private SecretKeySpec generateKey(String userKey) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte [] bytes = userKey.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte key[] = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
        return secretKeySpec;

    }

}
