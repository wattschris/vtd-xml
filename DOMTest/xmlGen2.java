/*
 * Created on Nov 22, 2004
 *
 * This file generates random structured XML
 */
package DOMTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

/**
 * @author jimmy zhang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


public class xmlGen2 {
	public final static String testDir1 = 
		"d://ximple-dev//testcases//VTDNav//";
	public final static String readme = testDir1 + "readme.txt";
	public final static String enc_ascii = "<?xml version='1.0' encoding=\"us-ascii\"?>";
	public final static String enc_8859 = "<?xml version='1.0' encoding=\"iso-8859-1\"?>";
	public final static String enc_utf_8 = "<?xml version='1.0' encoding=\"utf-8\"?>";
	public final static String enc_utf_16le = "<?xml version='1.0' encoding=\"utf-16le\"?>";
	public final static String enc_utf_16be = "<?xml version='1.0' encoding=\"utf-16be\"?>";
	public static File rf,f0,f1,f2,f3,f4;
	public static FileWriter fw;
	public static FileOutputStream fos0, fos1, fos2, fos3, fos4;
    public static String s;
	public static void composeXML2(String xml1, int i, String msg) throws Exception {
		byte[] ba0 = null, ba1 = null, ba2 = null, ba3 = null, ba4 = null;
		//ba0 = (enc_ascii+"<!--"+ msg+"-->" + xml1).getBytes("ascii");
		//ba1 = (enc_8859 +"<!--"+ msg+"-->" + xml1).getBytes("iso-8859-1");
		ba2 = (enc_utf_8+"<!--"+ msg+"-->" + xml1).getBytes("utf-8");
		ba3 = (enc_utf_16le+"<!--"+ msg+"-->" + xml1).getBytes("utf-16le");
		ba4 = (enc_utf_16be+"<!--"+ msg+"-->" + xml1).getBytes("utf-16be");

		
		fw.write("nav_" + i
				+ "_*.xml "+"<!--"+ msg+"-->"+"\n");
		fw.flush();
		//f0 = new File(testDir1 + "nt_" + i + "_0.xml");
		//f1 = new File(testDir1 + "nt_" + i + "_1.xml");
		f2 = new File(testDir1 + "nav_" + i + "_2.xml");
		f3 = new File(testDir1 + "nav_" + i + "_3.xml");
		f4 = new File(testDir1 + "nav_" + i + "_4.xml");

		//fos0 = new FileOutputStream(f0);
		//fos1 = new FileOutputStream(f1);
		fos2 = new FileOutputStream(f2);
		fos3 = new FileOutputStream(f3);
		fos4 = new FileOutputStream(f4);

		//fos0.write(ba0);
		//fos1.write(ba1);
		fos2.write(ba2);
		fos3.write(ba3);
		fos4.write(ba4);

		//fos0.close();
		//fos1.close();
		fos2.close();
		fos3.close();
		fos4.close();		
	}

	public static void composeXML1(String xml1, int i, String msg) throws Exception {
		byte[] ba0 = null, ba1 = null, ba2 = null, ba3 = null, ba4 = null;
		ba0 = (enc_ascii+"<!--"+ msg+"-->" + xml1).getBytes("ascii");
		ba1 = (enc_8859 +"<!--"+ msg+"-->" + xml1).getBytes("iso-8859-1");
		ba2 = (enc_utf_8+"<!--"+ msg+"-->" + xml1).getBytes("utf-8");
		ba3 = (enc_utf_16le+"<!--"+ msg+"-->" + xml1).getBytes("utf-16le");
		ba4 = (enc_utf_16be+"<!--"+ msg+"-->" + xml1).getBytes("utf-16be");

	   // test id
		fw.write("nt_" + i
				+ "_*.xml "+"<!--"+ msg+"-->"+"\n");
		fw.flush();
		f0 = new File(testDir1 + "nav_" + i + "_0.xml");
		f1 = new File(testDir1 + "nav_" + i + "_1.xml");
		f2 = new File(testDir1 + "nav_" + i + "_2.xml");
		f3 = new File(testDir1 + "nav_" + i + "_3.xml");
		f4 = new File(testDir1 + "nav_" + i + "_4.xml");

		fos0 = new FileOutputStream(f0);
		fos1 = new FileOutputStream(f1);
		fos2 = new FileOutputStream(f2);
		fos3 = new FileOutputStream(f3);
		fos4 = new FileOutputStream(f4);

		fos0.write(ba0);
		fos1.write(ba1);
		fos2.write(ba2);
		fos3.write(ba3);
		fos4.write(ba4);

		fos0.close();
		fos1.close();
		fos2.close();
		fos3.close();
		fos4.close();
	}  
	
	public static String randomXML(int depth){
		String s = "<top" + compose(1,depth)+"</top>";
		return s; 
	}
	
	public static String compose(int i, int depth){
		int k = (int)(Math.random()*10);
		String s=" attr='"+k+"'>\n";
		if (i==depth){
			return " attr ='0'>\n";
		}
		else {
			for(int z=0;z<k;z++){
				s = s+"<level"+i+compose(i+1,depth)+"</level"+i+">\n";
			}
		}
		return s;
	}

	public static void main(String[] args) {
		String[] s1={};
		try {
			// open readme file
			rf = new File(readme);
			fw = new FileWriter(rf);
		    for (int i=0;i<40;i++){
		    	composeXML1(randomXML(5),i,"random structure of XML");
		    }
		    System.out.println("finished!");
		}
	    catch (Exception e) {
	    	e.printStackTrace();
	    	System.out.println("exception type" + e);
	    }
	}
}
