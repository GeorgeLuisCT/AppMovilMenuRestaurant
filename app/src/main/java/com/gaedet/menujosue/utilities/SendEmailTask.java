package com.gaedet.menujosue.utilities;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.content.Context;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.sun.mail.smtp.SMTPTransport;

public class SendEmailTask extends AsyncTask<Void, Void, Void> {

    private String toEmail;
    private String subject;
    private String body;
    private String senderEmail;
    private String senderPassword;
    private Uri selectedImageUri;
    private Context context;
    private EmailTaskListener emailTaskListener;

    public SendEmailTask(Context context, String toEmail, String subject, String body, String senderEmail, String senderPassword, Uri selectedImageUri) {
        this.context = context;
        this.toEmail = toEmail;
        this.subject = subject;
        this.body = body;
        this.senderEmail = senderEmail;
        this.senderPassword = senderPassword;
        this.selectedImageUri = selectedImageUri;
    }

    @Override
    protected Void doInBackground(Void... params) {
        sendEmail();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (emailTaskListener != null) {
            emailTaskListener.onEmailTaskCompleted();
        }
    }


    private void sendEmail() {
        try {
            Security.addProvider(new JSSEProvider());

            Properties props = new Properties();
            props.put("mail.smtps.host", "smtp.gmail.com");
            props.put("mail.smtps.auth", "true");

            Session session = Session.getInstance(props, null);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            message.setSubject(subject);

            // Configurar el cuerpo del mensaje
            MimeMultipart multipart = new MimeMultipart();

            // Parte del cuerpo del mensaje
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html; charset=utf-8");
            multipart.addBodyPart(messageBodyPart);

            // Parte de la imagen adjunta
            if (selectedImageUri != null) {
                MimeBodyPart imageBodyPart = new MimeBodyPart();
                InputStream imageInputStream = context.getContentResolver().openInputStream(selectedImageUri);
                imageBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(imageInputStream, "image/jpeg")));
                imageBodyPart.setFileName("attached_image.jpg");
                multipart.addBodyPart(imageBodyPart);
            }

            // Configurar el mensaje
            message.setContent(multipart);

            try (SMTPTransport t = (SMTPTransport) session.getTransport("smtps")) {
                t.connect("smtp.gmail.com", senderEmail, senderPassword);
                t.sendMessage(message, message.getAllRecipients());
            }

        } catch (MessagingException | IOException e) {
            Log.e("EmailError", "Error sending email", e);
        }
    }
    public void setEmailTaskListener(EmailTaskListener listener) {
        this.emailTaskListener = listener;
    }

    private static class JSSEProvider extends java.security.Provider {
        private static final long serialVersionUID = 1L;

        public JSSEProvider() {
            super("HarmonyJSSE", 1.0, "Harmony JSSE Provider");
            put("SSLContext.TLS", "org.apache.harmony.xnet.provider.jsse.SSLContextImpl");
            put("Alg.Alias.SSLContext.TLSv1", "TLS");
            put("KeyManagerFactory.X509", "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl");
            put("TrustManagerFactory.X509", "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl");
            put("Cipher.ECDHE", "org.apache.harmony.xnet.provider.jsse.CipherSuiteImpl");
            put("Cipher.DHE", "org.apache.harmony.xnet.provider.jsse.CipherSuiteImpl");
            put("Cipher.ECDSA", "org.apache.harmony.xnet.provider.jsse.CipherSuiteImpl");
            put("Engine.UpdateManager", "com.sun.mail.util.logging.PlatformLogger$1");
            put("Engine.X500", "org.apache.harmony.xnet.provider.jsse.X500KeyManager");
        }
    }



    public interface EmailTaskListener {
        void onEmailTaskCompleted();
        void onEmailTaskFailed(String errorMessage);
    }

}
