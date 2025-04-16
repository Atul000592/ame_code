package nic.ame.app.sms;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

public class SSLUtil {
	
	public static boolean disableCertificateCheck(HttpsURLConnection connection) {

		try {

			//Disable Trust Manager
			TrustManager[] disabledTrustManager = new TrustManager[]{new  X509TrustManager() {

				@Override
				public X509Certificate[] getAcceptedIssuers() {

					return null;
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				}
			}};

			SSLContext relaxedSSLContext = SSLContext.getInstance("SSL");
			relaxedSSLContext.init(null, disabledTrustManager, new java.security.SecureRandom());
			SSLSocketFactory relaxedSSLSocketFactory = relaxedSSLContext.getSocketFactory();

			connection.setSSLSocketFactory(relaxedSSLSocketFactory);

			//Disable Host Name Verifier
			HostnameVerifier disabledHostNameVerifier = new HostnameVerifier() {

				@Override
				public boolean verify(String hostname, SSLSession session) {

					return true;
				}
			};

			connection.setHostnameVerifier(disabledHostNameVerifier);

			return true;
		}
		catch (Exception e) {

			e.printStackTrace();
		}

		return false;
	}

}
