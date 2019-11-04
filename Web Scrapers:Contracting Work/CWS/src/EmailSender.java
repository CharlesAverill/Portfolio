import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


import javax.activation.*;

public class EmailSender {
	
	String[] toEmail;
	String fromEmail;
	String subject;
	String message;
	
	public EmailSender(String from, String[] to) {
		toEmail = to;
		fromEmail = from;
	}
	
	public void setSubject(String s) {
		subject = s;
	}
	
	public void setMessage(String m) {
		message = m;
	}
	
	public void sendMessage() throws UnknownHostException, IOException {
		final String password = "ThePassw0rdF0rThisB0t3mailIsL0ng3n0ughThatItSh0uldBeVeryStr0ng";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(fromEmail, password);
		    }
		});

		try {

			Message mes = new MimeMessage(session);
			mes.setFrom(new InternetAddress(fromEmail));
			for(String str : toEmail) {
				mes.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(str));
					
				mes.setSubject(subject);
				mes.setText(message);

				Transport.send(mes);
			}

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void sendFile(File file) throws UnknownHostException, IOException {
		String filename = file.getPath();
		final String password = "ThePassw0rdF0rThisB0t3mailIsL0ng3n0ughThatItSh0uldBeVeryStr0ng";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(fromEmail, password);
		    }
		});

		try {
			for(String str : toEmail) {
		         Message mes = new MimeMessage(session);
		         
		         mes.setFrom(new InternetAddress(fromEmail));
		         
		         mes.setRecipients(Message.RecipientType.TO, InternetAddress.parse(str));
		         
		         mes.setSubject(subject);
		         
		         BodyPart messageBodyPart = new MimeBodyPart();
		         
		         messageBodyPart.setText(message);
		         
		         Multipart multipart = new MimeMultipart();
		         
		         multipart.addBodyPart(messageBodyPart);

		         
		         messageBodyPart = new MimeBodyPart();
		         
		         DataSource source = new FileDataSource(filename);
		         
		         messageBodyPart.setDataHandler(new DataHandler(source));
		         
		         messageBodyPart.setFileName(filename);
		         
		         multipart.addBodyPart(messageBodyPart);

		         mes.setContent(multipart);

		         Transport.send(mes);
		         
		         System.out.println("Email sent.");
			}

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
