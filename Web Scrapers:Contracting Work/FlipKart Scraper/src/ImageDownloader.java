import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class ImageDownloader {
	
	public static void downloadImages(String webUrl) throws IOException, BadLocationException {
		URL url = new URL(webUrl);
		URLConnection connection = url.openConnection();
		InputStream is = connection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		HTMLEditorKit htmlKit = new HTMLEditorKit();
		HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
		htmlKit.read(br, htmlDoc, 0);

		for (HTMLDocument.Iterator iterator = htmlDoc.getIterator(HTML.Tag.A); iterator.isValid(); iterator.next()) {
		    AttributeSet attributes = iterator.getAttributes();
		    String imgSrc = (String) attributes.getAttribute(HTML.Attribute.HREF);

		    System.out.println(imgSrc);
		    if (imgSrc != null && (imgSrc.toLowerCase().endsWith(".jpg") || (imgSrc.endsWith(".png")) || (imgSrc.endsWith(".jpeg")) || (imgSrc.endsWith(".bmp")) || (imgSrc.endsWith(".ico")))) {
		        try {
		            downloadImage(webUrl, imgSrc);
		        } catch (IOException ex) {
		            System.out.println(ex.getMessage());
		        }
		    }
		}
	}
	
	private static void downloadImage(String url, String imgSrc) throws IOException {
	    BufferedImage image = null;
	    try {
	        if (!(imgSrc.startsWith("http"))) {
	            url = url + imgSrc;
	        } else {
	            url = imgSrc;
	        }
	        imgSrc = imgSrc.substring(imgSrc.lastIndexOf("/") + 1);
	        String imageFormat = null;
	        imageFormat = imgSrc.substring(imgSrc.lastIndexOf(".") + 1);
	        String imgPath = null;
	        imgPath = "C:/temp/" + imgSrc + "";
	        URL imageUrl = new URL(url);
	        image = ImageIO.read(imageUrl);
	        if (image != null) {
	            File file = new File(imgPath);
	            ImageIO.write(image, imageFormat, file);
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }

	}
}
