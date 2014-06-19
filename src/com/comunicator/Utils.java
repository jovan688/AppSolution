package com.comunicator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.kobjects.base64.Base64;


public class Utils {

	
	static String query = "86a4a45eb4f4c04c622d6f59634a6728";
	String salt=new String();
	String url_principal = new String();
	
	
	public  String CreateSalt(int size)
	{
		String value =new String();
		try{
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			//byte[] bytes = new byte[size];
			byte[] bytes = messageDigest.digest(new byte[size]);
			//synchronized (random) { random.nextBytes(bytes); }
			value=bytes.toString();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return value;
	}
	/*
	public  String Parse(String address)
	{
		String result = new  String(); 
		try {
		    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		    byte[] myaddress = messageDigest.digest(address.getBytes());
		    result= myaddress.toString();
	   }
		catch (Exception e) {
			e.printStackTrace();
	    }
		return result;
	}
	*/
	public  String Parse(String address) 
	{
		String result = new  String(); 
		try {
		    MessageDigest md = MessageDigest.getInstance("MD5");
		    md.update(address.getBytes());
		
		    byte byteData[] = md.digest();
		
		    //convert the byte to hex format method 1
		    StringBuffer sb = new StringBuffer();
		    for (int i = 0; i < byteData.length; i++) {
		     sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		    }
		
		    /*System.out.println("Digest(in hex format):: " + sb.toString());*/
		
		    //convert the byte to hex format method 2
		    StringBuffer hexString = new StringBuffer();
			for (int i=0;i<byteData.length;i++) {
				String hex=Integer.toHexString(0xff & byteData[i]);
			     	if(hex.length()==1) hexString.append('0');
			     	hexString.append(hex);
			}
			/*System.out.println("Digest(in hex format):: " + hexString.toString());*/
			result= hexString.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
	    }
		return result;
	}
	public boolean Build(String address)
	{
		Boolean allow = false;
		//get the subString
		url_principal = address.substring(0, 22);
		/*
		//Create 5 numbers ramdom
		salt= CreateSalt(5);
		String url_enconde=new String();		
		url_enconde=Parse(url_principal);
		String enconde_val=new String();
		enconde_val=Parse(query.concat(salt));
		String enconde_val2=new String();
		enconde_val2=Parse(url_enconde.concat(salt));
		
		if(enconde_val.equals(enconde_val2))
		{
			allow=true;
		}
		*/
		
		String url_enconde=Parse(url_principal);
		salt= CreateSalt(5);
		String encripter1= Parse(query.concat(salt));
		String encripter2= Parse(url_enconde.concat(salt));
		
		if(encripter1.equals(encripter2))
		{
			allow=true;
		}
		
		return allow;
	}

	
}
