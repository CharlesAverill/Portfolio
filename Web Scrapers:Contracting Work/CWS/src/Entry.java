/*
Author: Matthew Averill

DISCLAIMER:
This SOFTWARE PRODUCT is provided by THE PROVIDER "as is" and "with all faults." 
THE PROVIDER makes no representations or warranties of any kind concerning the safety, 
suitability, lack of viruses, inaccuracies, typographical errors, or other harmful components 
of this SOFTWARE PRODUCT. There are inherent dangers in the use of any software, and you are 
solely responsible for determining whether this SOFTWARE PRODUCT is compatible with your 
equipment and other software installed on your equipment. You are also solely responsible 
for the protection of your equipment and backup of your data, and THE PROVIDER will not be 
liable for any damages you may suffer in connection with using, modifying, 
or distributing this SOFTWARE PRODUCT.

NOT FOR REDISTRIBUTION
*/


public class Entry{
	String companyName;
	String contactFirstName;
	String contactLastName;
	String businessPhoneNumber;
	String faxNumber;
	String cellPhoneNumber;
	String URL;
	String email;
	String address1;
	String address2;
	String city;
	String zipCode;
	String PWC;
	String source;
	String comment;
	
	public Entry() {
		companyName = " ";
		contactFirstName = " ";
		contactLastName = " ";
		businessPhoneNumber = " ";
		faxNumber = " ";
		cellPhoneNumber = " ";
		URL = " ";
		email = " ";
		address1 = " ";
		address2 = " ";
		city = " ";
		zipCode = " ";
		PWC = " ";
		source = " ";
		comment = " ";
	}
	
	public Entry(String a, String b, String c, String d, String e, String f, String g, String h, String i, String j, String k, String l, String m, String n, String o) {
		companyName = a;
		contactFirstName = b;
		contactLastName = c;
		businessPhoneNumber = d;
		faxNumber = e;
		cellPhoneNumber = f;
		URL = g;
		email = h;
		address1 = i;
		address2 = j;
		city = k;
		zipCode = l;
		PWC = m;
		source = n;
		comment = o;
	}
	
	public String toString() {
		return "Company Name: " + companyName + "\nContact First Name: " + contactFirstName + 
				"\nContact Last Name: " + contactLastName + "\nBusiness Phone Number: " + businessPhoneNumber + 
				"\nFax Number: " + faxNumber + "\nCell Phone Number: " + cellPhoneNumber + 
				"\nURL: " + URL + "\nEmail: " + email + "\nAddress 1: " + address1 + "\nAddress 2: " + address2 + 
				"\nCity: " + city + "\nZip Code: " + zipCode + "\nPWC: " + PWC + "\nSource: " + source + 
				"\nComments: " + comment;
	}
}
