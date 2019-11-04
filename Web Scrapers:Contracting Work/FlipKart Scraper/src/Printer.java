import java.io.*;

public class Printer implements Runnable{
	
	SearchPage sp;
	SearchResult sr;
	String mode;
	
	int n;
	
	Thread t;
	
	int imageCount;

	public Printer(SearchPage s, SearchResult r, String m, int q, Thread k) {
		sp = s;
		sr = r;
		mode = m;
		imageCount = 1;
		n = q;
		t = k;
	}
	
	public void setResult(SearchResult r) {
		sr = r;
	}
	
	@Override
	public void run() {
		if(mode.equals("html")) {
			
			sr.product.replace("N", "" + n);
			File html = new File(sp.folder.getPath() + "/html/" + sp.search + n + ".html");
			PrintWriter h;
			try {
				h = new PrintWriter(html);
				h.println(sr.getXML());
				h.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			File text = new File(sp.folder.getPath() + "/text/" + sp.search + n + ".txt");
			try {
				h = new PrintWriter(text);
				h.println(sr.getText());
				h.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			sp.pw.println(sr.product.toString());
		}
	}
}
