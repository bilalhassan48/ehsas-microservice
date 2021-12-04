package com.zingpay.ehsas.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.http.HttpMethod;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Bilal Hassan on 20-Nov-21
 * @project ehsas-integration-microservice
 */

public class Utils {

	private static ObjectMapper json = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	
	//private static BCryptPasswordEncoder passwordEncoder;

	public static DecimalFormat ONE_DECIMAL_POINT_FORMAT = new DecimalFormat("#.#");
	public static DecimalFormat TWO_DECIMAL_POINT_FORMAT = new DecimalFormat("#.##");

	final static int size = 8;

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static DateFormat dateFormatWithoutTime = new SimpleDateFormat("yyyy-MM-dd");
	private static Calendar cal = null;

	public Utils() {
		this.json.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static boolean isCollectionEmpty(Collection<?> es){
		if (es != null && !es.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	
	
	public static boolean isStringEmpty(String str){
		if (str != null && !str.isEmpty()){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isStringNotEmpty(String str){
		if (str != null && !str.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean validate (Object obj){
		boolean flag = Boolean.TRUE;
		
		/*
		 * TODO Input validation of JSON fields
		 * */

		return flag;
	}


	private static TrustManager[ ] get_trust_mgr() {
	     TrustManager[ ] certs = new TrustManager[ ] {
	        new X509TrustManager() {
	           public X509Certificate[ ] getAcceptedIssuers() { return null; }
	           public void checkClientTrusted(X509Certificate[ ] certs, String t) { }
	           public void checkServerTrusted(X509Certificate[ ] certs, String t) { }
	         }
	      };
	      return certs;
	  }
	
	
	public static String executeURL(String urlString, String urlParameters, String method, String token,String auth) {
		StringBuffer response = new StringBuffer();
		String exceptionMsg= "";
		String msg[];
		
		
		try {
			
			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(method);
			
			if (!Utils.isStringEmpty(token)){
				conn.setRequestProperty("Authorization", auth + " "+token);
			}
			//conn.setRequestProperty("Content-Type", "application/json");
			
			
			if (method.equalsIgnoreCase(HttpMethod.POST.toString())){
			
				DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
			}
			
			/*if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}*/

			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);
			
			System.out.println(conn.getResponseMessage());
		
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//print result
			logger.info(response.toString());
			
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			exceptionMsg = e.getMessage();
			msg = exceptionMsg.split("URL:");
			//response.append(msg[1]);
			
			logger.error("",e);
			
			//e.printStackTrace();
		}catch (Exception e) {
		}
		
		return response.toString();
		
		
	}
	
		
	private static void print_https_cert(HttpsURLConnection con){
	     if(con!=null){
				
	     try {
					
		System.out.println("Response Code : " + con.getResponseCode());
		System.out.println("Cipher Suite : " + con.getCipherSuite());
		System.out.println("\n");
					
		Certificate[] certs = con.getServerCertificates();
		for(Certificate cert : certs){
		  System.out.println("Cert Type : " + cert.getType());
		  System.out.println("Cert Hash Code : " + cert.hashCode());
		  System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
		  System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
		  System.out.println("\n");
		}
					
					
	     } catch (SSLPeerUnverifiedException e) {
		  e.printStackTrace();
	     } catch (IOException e){
		  e.printStackTrace();
	     }	   
	   }		
	  }
		
	  private static void print_content(HttpsURLConnection con){
	    if(con!=null){
				
	    try {
			
		System.out.println("****** Content of the URL ********");
					
		BufferedReader br = 
			new BufferedReader(
				new InputStreamReader(con.getInputStream()));
					
		String input;
					
		while ((input = br.readLine()) != null){
		   System.out.println(input);
		}
		br.close();
					
	     } catch (IOException e) {
		e.printStackTrace();
	     }		
	   }
	  }


	public static Date getUTCTime(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		return cal.getTime();
	}
	
	public static Date getTime(){
		cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	public static int getCurrentYear() {
		int year = 0;
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		
		//cal.setTime(getUTCTime());
		
		year = cal.get(Calendar.YEAR);
		
		
		return year;
	}


	public static String getFormattedDate(long date) {
		
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		return dateFormat.format(date);
	}
	
	public static Date getFormattedDate(String date){
		
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date dateValue = null;
		try {
			dateValue = dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dateValue;
	}

	public static Date getFormattedDateWithoutTime(String date){

		dateFormatWithoutTime.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date dateValue = null;
		try {
			dateValue = dateFormatWithoutTime.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dateValue;
	}

	public static String getFormattedDate(Date date){

		return dateFormat.format(date);
	}

	public static String getFormattedUTCDate(Date date){
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatLocal.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormatLocal.format(date);
	}
	
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyNotNullBeanProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

	/*public static BufferedImage decodeToImage(String imageString) {

		BufferedImage image = null;
		byte[] imageByte;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			imageByte = decoder.decodeBuffer(imageString);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}*/

	public static String generateFileName(String originalFileName, String append, String ext){
		String extension = null;
		String fileName=null;

		if(originalFileName.indexOf(".") > -1){
			extension = originalFileName.substring(originalFileName.indexOf("."), originalFileName.length()); //included . DOT
		}

		if(null == extension && null == ext){
			ext=".png";
		}


		try {
			fileName = originalFileName + (ext == null ? extension : ext.trim());
		}catch (Exception e){
			logger.error("", e);
			fileName = originalFileName + ".png";
		}

		return (append == null ? fileName : fileName+"_"+append.trim());

	}

	/*public static String createNumericKey(int size) {
		RandomNumeric randomString = new RandomNumeric(size);

		return randomString.nextString();
	}*/

	public static int getHoursFromDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinutesFromDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	private static final String key = "cvcwq9M8Ogh7pDYX";
	private static final String initVector = "lTvO2vUTmzs5c7xH";

	/*public static String encrypt(String value) {
		if(value == null || value.isEmpty()){
			return null;
		}
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			return DatatypeConverter.printHexBinary(encrypted);
			//return new String(encrypted);
			//return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}*/

	/*public static String decrypt(String encrypted) {
		if(encrypted == null || encrypted.isEmpty()){
			return null;
		}
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			//byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
			byte[] original = cipher.doFinal(DatatypeConverter.parseHexBinary(encrypted));

			return new String(original);
		} catch (Exception ex) {
			//ex.printStackTrace();
			//logger.error("error while decryption. May b plain pin/password provided.", ex);
			logger.error("error while decryption. May b plain pin/password provided.");
		}

		return null;
	}*/

	public static Date getDayStartOrEndTime(Date dateTime, boolean startTimeRequired){
		cal = Calendar.getInstance();
		cal.setTime(dateTime);
		if(startTimeRequired){
			//cal.set(Calendar.HOUR_OF_DAY,0);
			cal.set(Calendar.MINUTE,0);
			cal.set(Calendar.SECOND,0);
		}else{
			//cal.set(Calendar.HOUR_OF_DAY,23);
			cal.set(Calendar.MINUTE,59);
			cal.set(Calendar.SECOND,59);
		}

		return cal.getTime();
	}

	public static boolean checkInRange(Date startDate, Date endDate, Date dateToCheck){
		return (dateToCheck.getTime() >= startDate.getTime() &&
				dateToCheck.getTime() <= endDate.getTime()
				/*|| (startDate.getTime() >= dateToCheck.getTime() &&
				endDate.getTime() <= dateToCheck.getTime()*/);
	}



	public static Date addDaysToDate(Date date, int noOfDaysToAdd){
		cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, noOfDaysToAdd);
		return cal.getTime();
	}

	public static Date addMinToDate(Date date, int noOfMinToAdd){
		cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, noOfMinToAdd);
		return cal.getTime();
	}

	public static String formatDateWithoutTime(Date date){
		return dateFormatWithoutTime.format(date);
	}

	public static <T> List<T> parseJsonToList(String jsonToParse,
											  Class<T> classOnWhichArrayIsDefined)
			throws JsonProcessingException, ClassNotFoundException {
		Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + classOnWhichArrayIsDefined.getName() + ";");
		T[] objects = json.readValue(jsonToParse, arrayClass);
		return Arrays.asList(objects);
	}

	public static class CommonFunctions {
		static Cipher cipher;

		public static boolean validateToken(String token, ObjectFactory<HttpSession> httpSessionFactory) {
			/* Boolean validToken = false;
			 HttpSession httpSession = httpSessionFactory.getObject();
			 if(token !=null && httpSession.getAttribute("token") !=null) {
				 if(token.equalsIgnoreCase(httpSession.getAttribute("token").toString())){
					 validToken = true;
					}else {
						 validToken = false;
					}
			 }else {
				 validToken = false;
			 }*/
			return true;
		}

		public static String encrypt(String plainText)
				throws Exception {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128); // block size is 128bits
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] plainTextByte = plainText.getBytes();
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedByte = cipher.doFinal(plainTextByte);
			Base64.Encoder encoder = Base64.getEncoder();
			String encryptedText = encoder.encodeToString(encryptedByte);
			return encryptedText;
		}

		public static String decrypt(String encryptedText)
				throws Exception {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128); // block size is 128bits
			SecretKey secretKey = keyGenerator.generateKey();
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] encryptedTextByte = decoder.decode(encryptedText);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
			String decryptedText = new String(decryptedByte);
			return decryptedText;
		}
	}

	public static int generateFourDigitPin(){
		return ThreadLocalRandom.current().nextInt(1111, 9998 + 1);
	}

	public static String encodePassword(String password) {
		return Base64.getEncoder().encodeToString(password.getBytes());
	}

	public static String decodePassword(String password) {
		return new String(Base64.getDecoder().decode(password));
	}

	public static <T> T parseToObject(String jsonString, Class<T> type) throws JsonProcessingException {
		return json.readValue(jsonString, type);
	}

	public static long convertStringDateToLong(String date) throws ParseException {
		return dateFormatWithoutTime.parse(date).getTime();
	}

	public static String parseObjectToJson(Object obj) throws JsonProcessingException {
		return json.writeValueAsString(obj);
	}
}


