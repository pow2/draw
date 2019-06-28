/**
 * @author Plamen.Prangov
 *
 */
package bg.tusofia.draw.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GF {
	// -------------------------------------------------------------------------------
	public static final String UTF8 = "UTF-8";
	public static final String EMPTY = "";
	public static final int DEF_BUFFER = 1024;
	// -------------------------------------------------------------------------------
	private static Logger log = LoggerFactory.getLogger(GF.class);

	// -------------------------------------------------------------------------------
	public static String genUriPair(String param, String value) {
		StringBuilder sb = new StringBuilder(128);
		sb.append(param);
		sb.append("=");
		sb.append(urlEnc(value));
		return sb.toString();
	}

	// -------------------------------------------------------------------------------
	public static Properties loadProperties(String file) {
		return loadProperties(file, true);
	}

	// -------------------------------------------------------------------------------
	public static String getError(Exception e) {
		StringBuilder result = new StringBuilder(1024);
		result.append("ErrorMessage: ");
		result.append(e.getMessage());
		result.append("\r\nErrorStackTrace: ");
		try {
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			result.append(writer.toString());
		} catch (Exception ex) {
		}
		return result.toString();
	}

	// ---------------------------------------------------------------------------------------
	public static String stackTraceToString(Exception e) {
		String resp;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		resp = sw.toString();
		pw.close();
		return resp;
	}

	// -------------------------------------------------------------------------------
	public static void closeInputStream(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (Exception e) {
			}
		}
	}

	// -------------------------------------------------------------------------------
	public static Properties loadProperties(String resource, boolean isContextPath) {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			if (isContextPath) {
				input = getInputStream(resource);
			} else {
				File f = new File(resource);
				input = new FileInputStream(f);
			}
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			closeInputStream(input);
		}
		return prop;
	}

	// -------------------------------------------------------------------------------
	public static ArrayList<String> fileSearch(String path, String extension) {
		ArrayList<String> lList = new ArrayList<String>();
		fileSearch(path, extension, lList);
		return lList;
	}

	// -------------------------------------------------------------------------------
	private static void fileSearch(String path, String extension, ArrayList<String> files) {
		File root = new File(path);
		File[] list = root.listFiles();
		if (list != null) {
			for (File f : list) {
				if (f.isDirectory()) {
					fileSearch(f.getAbsolutePath(), extension, files);
				} else {
					String fName = f.getAbsolutePath();
					if (!extension.isEmpty()) {
						if (fName.endsWith("." + extension)) {
							files.add(fName);
						}
					} else {
						files.add(fName);
					}
				}
			}
		}
	}

	// -------------------------------------------------------------------------------
	public static short getSystemThreads() {
		return (short) Runtime.getRuntime().availableProcessors();
	}

	// -------------------------------------------------------------------------------
	public static boolean isNullOrEmpty(String string) {
		boolean result = true;
		if (string != null && !string.isEmpty()) {
			result = false;
		}
		return result;
	}

	// -------------------------------------------------------------------------------
	public static boolean eqIC(String string1, String string2) {
		return string2.equalsIgnoreCase(string1);
	}

	// -------------------------------------------------------------------------------
	private static final String TRUE = "true";
	private static final String T = "t";
	private static final String YES = "yes";
	private static final String Y = "y";
	private static final String ONE = "1";

	public static boolean isTrue(String boolStr) {
		boolean result = false;
		if (boolStr == null) {
			return result;
		}
		if (eqIC(boolStr, TRUE) || eqIC(boolStr, T) || eqIC(boolStr, YES) || eqIC(boolStr, Y) || eqIC(boolStr, ONE)) {
			result = true;
		}
		return result;
	}

	// -------------------------------------------------------------------------------
	public static String urlEncode(String plainText, String charset) {
		String resp = EMPTY;
		try {
			resp = URLEncoder.encode(plainText, charset);
		} catch (UnsupportedEncodingException e) {
			resp = EMPTY;
		}
		return resp;
	}

	// -------------------------------------------------------------------------------
	public static String urlDecode(String urlEncoded, String charset) {
		String resp = EMPTY;
		try {
			resp = URLDecoder.decode(urlEncoded, charset);
		} catch (UnsupportedEncodingException e) {
			resp = EMPTY;
		}
		return resp;
	}

	// -------------------------------------------------------------------------------
	public static String base64encode(String plainText, String charset) {
		byte[] encodedBytes;
		String resp = EMPTY;
		try {
			encodedBytes = Base64.getEncoder().encode(plainText.getBytes(charset));
			resp = new String(encodedBytes, charset);
		} catch (UnsupportedEncodingException e) {
			resp = EMPTY;
		}
		return resp;
	}

	// -------------------------------------------------------------------------------
	public static String base64encode(String plainText) {
		byte[] encodedBytes = Base64.getEncoder().encode(plainText.getBytes());
		return new String(encodedBytes);
	}

	// -------------------------------------------------------------------------------
	public static String base64decode(String base64Encoded, String charset) {
		byte[] decodedBytes = Base64.getDecoder().decode(base64Encoded);
		return new String(decodedBytes, Charset.forName(charset));
	}

	// -------------------------------------------------------------------------------
	public static String base64decode(String base64Encoded) {
		return base64decode(base64Encoded, UTF8);
	}

	// ------------------------------------------------------------------------------
	public static String readFile(String path, String charset, boolean isContextPath) {
		String response = EMPTY;
		if (isContextPath) {
			StringBuilder sb = new StringBuilder();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(getInputStream(path), UTF8))) {
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				response = sb.toString();
			} catch (Exception ex) {
				response = EMPTY;
			}
		} else {
			try {
				byte[] encoded = Files.readAllBytes(Paths.get(path));
				response = new String(encoded, Charset.forName(charset));
			} catch (Exception ex) {
				response = EMPTY;
			}
		}
		return response;
	}

	// -------------------------------------------------------------------------------
	public static String readFile(String path, String charset) {
		return readFile(path, charset, true);
	}

	// -------------------------------------------------------------------------------
	public static String readFile(String path) {
		return readFile(path, UTF8, true);
	}

	// -------------------------------------------------------------------------------
	private static String appPath = null;

	// ------------------------------------------------------------------------------
	private static synchronized void setAppPath(String path) {
		if (appPath == null) {
			appPath = path;
		}
	}

	// -------------------------------------------------------------------------------
	public static String getResourcePath() {
		String path = EMPTY;
		if (appPath == null) {
			try {
				File file = new File(GF.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
				path = file.getParentFile().getParentFile().getAbsolutePath();
				path = path + "/WEB-INF/resources/";
				setAppPath(path);
			} catch (Exception e) {
				path = e.getMessage();
			}
		} else {
			path = appPath;
		}
		return path;

	}

	// -------------------------------------------------------------------------------
	public static InputStream getInputStream(String resource) {
		InputStream response = null;
		try {
			response = GF.class.getClassLoader().getResourceAsStream(resource);
		} catch (Exception e) {
			e.getMessage();
		}
		return response;
	}

	// -------------------------------------------------------------------------------
	public static String getSha256(String str) {
		MessageDigest digest;
		String resp;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
			resp = toHex(hash);// Base64.getEncoder().encodeToString(hash);
		} catch (Exception e) {
			resp = EMPTY;
		}

		return resp;
	}

	// -------------------------------------------------------------------------------
	public static String urlEnc(String str) {
		String response;
		try {
			response = URLEncoder.encode(str, UTF8);
		} catch (UnsupportedEncodingException e) {
			response = EMPTY;
		}
		return response;
	}

	// -------------------------------------------------------------------------------
	public static String urlDec(String str) {
		String response;
		try {
			response = URLDecoder.decode(str, UTF8);
		} catch (UnsupportedEncodingException e) {
			response = EMPTY;
		}
		return response;
	}
	// -------------------------------------------------------------------------------
	public static Long tryParseLong(String str) {
		Long result = -1l;
		if (GF.isNullOrEmpty(str)){
			return result;
		}
		try {
			result = Long.parseLong(str);
		} catch (Exception e) {
			result = -1l;
		}
		return result;
	}
	// -------------------------------------------------------------------------------
	public static String toHex(byte[] data) {
		int len = data.length;
		StringBuilder sb = new StringBuilder(len * 2);
		for (int i = 0; i < len; i++) {
			sb.append(String.format("%02X", data[i] & 0xFF));
		}
		return sb.toString();
	}

	// -------------------------------------------------------------------------------
	@SuppressWarnings("unused")
	private static int getRandomInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	// -------------------------------------------------------------------------------
	public static long getRandomLong(long min, long max) {
		return ThreadLocalRandom.current().nextLong(min, max + 1);
	}

	// -------------------------------------------------------------------------------
	public static String addLeadingZero(long number, int length) {
		return String.format("%0" + length + "d", number);
	}
	// ------------------------------------------------------------------------------

	// -------------------------------------------------------------------------------
	public static String objToXMLString(Object obj) {
		String s = "";
		try {
			java.io.StringWriter sw = new java.io.StringWriter();
			javax.xml.bind.JAXB.marshal(obj, sw);
			s = sw.toString();
		} catch (Exception e) {
			log.error("GF.objToXMLString() failed: " + e.getMessage());
		}
		return s;
	}

	// -------------------------------------------------------------------------------
	public static String encodeWithGZip(String str, String encoding) {
		String resp = null;
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				GZIPOutputStream gzipOutputStream = new GZIPOutputStream(out)) {
			gzipOutputStream.write(str.getBytes(encoding));
			gzipOutputStream.close();
			resp = new String(Base64.getEncoder().encode(out.toByteArray()), encoding);
			gzipOutputStream.close();
		} catch (IOException e) {
			log.error("GF.encodeWithGZip() failed: " + e.getMessage());
			resp = EMPTY;
		}
		return resp;
	}

	// -------------------------------------------------------------------------------
	public static String decodeWithGZip(String str, String encoding) {
		String resp = null;
		try (GZIPInputStream gs = new GZIPInputStream(
				new ByteArrayInputStream(Base64.getDecoder().decode(str.getBytes(encoding))))) {
			byte[] buffer = new byte[DEF_BUFFER];
			ByteArrayOutputStream baous = new ByteArrayOutputStream(2560);
			int len = 0;
			while ((len = gs.read(buffer, 0, buffer.length)) != -1) {
				baous.write(buffer, 0, len);
			}
			byte[] arr2 = baous.toByteArray();
			resp = new String(arr2, encoding);
		} catch (IOException e) {
			log.error("GF.decodeWithGZip() failed: " + e.getMessage());
			resp = EMPTY;
		}
		return resp;
	}
	// -------------------------------------------------------------------------------
}
