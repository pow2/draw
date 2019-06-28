package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bg.tusofia.draw.controllers.AccountController;
import bg.tusofia.draw.controllers.ImageController;
import bg.tusofia.draw.controllers.OfferController;
import bg.tusofia.draw.model.SiteAccount;
import bg.tusofia.draw.model.SiteImage;
import bg.tusofia.draw.model.SiteOffer;
import bg.tusofia.draw.model.SiteTagsDAO;
import bg.tusofia.draw.utils.JDBCCtrl;


@SuppressWarnings("deprecation")
public class TestSuite {
	
	@Before
	public void setMockConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection testCon = DriverManager.getConnection("jdbc:mysql://localhost:11240/Kerrigan", "XXX", "XXX");
			JDBCCtrl.setTestConnection(testCon);
		} catch (Exception e) {
		}
	}
	
	@After
	public void resetMockConnection(){
		JDBCCtrl.setTestConnection(null);
	}

	@Test
	public void fetchImage() {
		List<SiteImage> list = ImageController.getNewest(mockHttpRequest());
		System.out.println(list.size());
		System.out.println();
	}
	
	@Test
	public void fetchOffer() {
		List<SiteOffer> list = OfferController.getNewest(mockHttpRequest());
		System.out.println(list.size());
		System.out.println();
	}

	@Test(expected = NullPointerException.class)
	public void verifyAccount(){
		SiteAccount acc = AccountController.verifyAccount("plamenprangov@gmail.com", "123456");
		System.out.println(acc.getEmail());
		System.out.println();
	}
	
	@Test
	public void verifyHashing() throws Exception {
		String hashed = AccountController.getHashedPassword("Moonlight123@");
		System.out.println(hashed);
		if (!hashed.equals("d8d91e709bc5058c72814d94248c5497")){
			throw new Exception();
		}
		System.out.println();
	}
	
	@Test (expected = NullPointerException.class)
	public void fetchAccount(){
		SiteAccount acc = AccountController.getSiteAccountInfo(11);
		System.out.println(acc.getEmail());
		System.out.println();
	}
	
	@Test
	public void cacheTags() throws Exception{
		long ts1 = System.currentTimeMillis();
		SiteTagsDAO.getJSArrayTags();
		long ts2 = System.currentTimeMillis();
		SiteTagsDAO.getJSArrayTags();
		long ts3 = System.currentTimeMillis();
		System.out.println((ts2 - ts1));
		System.out.println((ts3 - ts2));
		System.out.println();
		if ( (ts2 - ts1) < (ts3 - ts2) ){
			throw new Exception();
		}
	}
	
	private HttpServletRequest mockHttpRequest(){
		HttpServletRequest request = new HttpServletRequest() {
			
			@Override
			public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1) throws IllegalStateException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public AsyncContext startAsync() throws IllegalStateException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setAttribute(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeAttribute(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isSecure() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isAsyncSupported() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isAsyncStarted() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public ServletContext getServletContext() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getServerPort() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getServerName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getScheme() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public RequestDispatcher getRequestDispatcher(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getRemotePort() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getRemoteHost() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRemoteAddr() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRealPath(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public BufferedReader getReader() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getProtocol() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String[] getParameterValues(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getParameterNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String[]> getParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getParameter(String arg0) {
				if (arg0 != null && arg0.equals("start")){
					return "0";
				} else if (arg0 != null && arg0.equals("start")){
					return "anime";
				} else if (arg0 != null && arg0.equals("userid")){
					return "11";
				} else {
					return "";
				}
			}
			
			@Override
			public Enumeration<Locale> getLocales() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Locale getLocale() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getLocalPort() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getLocalName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getLocalAddr() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ServletInputStream getInputStream() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public DispatcherType getDispatcherType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContentType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getContentLengthLong() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getContentLength() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getCharacterEncoding() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getAttribute(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public AsyncContext getAsyncContext() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <T extends HttpUpgradeHandler> T upgrade(Class<T> arg0) throws IOException, ServletException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void logout() throws ServletException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void login(String arg0, String arg1) throws ServletException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isUserInRole(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isRequestedSessionIdValid() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isRequestedSessionIdFromUrl() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isRequestedSessionIdFromURL() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isRequestedSessionIdFromCookie() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Principal getUserPrincipal() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public HttpSession getSession(boolean arg0) {
				// TODO Auto-generated method stub
				return new HttpSession() {
					
					@Override
					public void setMaxInactiveInterval(int arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void setAttribute(String arg0, Object arg1) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void removeValue(String arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void removeAttribute(String arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void putValue(String arg0, Object arg1) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public boolean isNew() {
						// TODO Auto-generated method stub
						return false;
					}
					
					@Override
					public void invalidate() {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public String[] getValueNames() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public Object getValue(String arg0) {
						// TODO Auto-generated method stub
						return null;
					}
					
					@SuppressWarnings("deprecation")
					@Override
					public HttpSessionContext getSessionContext() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public ServletContext getServletContext() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public int getMaxInactiveInterval() {
						// TODO Auto-generated method stub
						return 0;
					}
					
					@Override
					public long getLastAccessedTime() {
						// TODO Auto-generated method stub
						return 0;
					}
					
					@Override
					public String getId() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public long getCreationTime() {
						// TODO Auto-generated method stub
						return 0;
					}
					
					@Override
					public Enumeration<String> getAttributeNames() {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public Object getAttribute(String arg0) {
						// TODO Auto-generated method stub
						return null;
					}
				};
			}
			
			@Override
			public HttpSession getSession() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getServletPath() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRequestedSessionId() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public StringBuffer getRequestURL() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRequestURI() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRemoteUser() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getQueryString() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getPathTranslated() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getPathInfo() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<Part> getParts() throws IOException, ServletException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Part getPart(String arg0) throws IOException, ServletException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getMethod() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getIntHeader(String arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Enumeration<String> getHeaders(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getHeaderNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getHeader(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getDateHeader(String arg0) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Cookie[] getCookies() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContextPath() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getAuthType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String changeSessionId() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean authenticate(HttpServletResponse arg0) throws IOException, ServletException {
				// TODO Auto-generated method stub
				return false;
			}
		};

		return request;
	}
}

