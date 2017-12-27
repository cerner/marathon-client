/**
 * Copyright (C) 2015 Red Hat, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mesosphere.client.common.ssl;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import mesosphere.dcos.client.Config;
import okio.ByteString;

public final class SSLUtils {

	private SSLUtils() {
		//Utility
	}

	public static TrustManager[] trustManagers(Config config) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
		return trustManagers(config.getCaCertData(), config.getCaCertFile(), config.insecureSkipTlsVerify());
	}

	public static TrustManager[] trustManagers(String certData, String certFile, boolean insecureSkipTlsVerify) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		KeyStore trustStore = null;
		if (insecureSkipTlsVerify) {
			return new TrustManager[]{
					new X509TrustManager() {
						public void checkClientTrusted(X509Certificate[] chain, String s) {
						}

						public void checkServerTrusted(X509Certificate[] chain, String s) {
						}

						public X509Certificate[] getAcceptedIssuers() {
							return new X509Certificate[0];
						}
					}
			};
		} else if (!isNullOrEmpty(certData) || !isNullOrEmpty(certFile)) {
			trustStore = createTrustStore(certData, certFile);
		}
		tmf.init(trustStore);
		return tmf.getTrustManagers();
	}

	private static InputStream getInputStreamFromDataOrFile(String data, String file) throws FileNotFoundException {
		if (data != null) {
			byte[] bytes;
			ByteString decoded = ByteString.decodeBase64(data);
			if (decoded != null) {
				bytes = decoded.toByteArray();
			} else {
				bytes = data.getBytes();
			}

			return new ByteArrayInputStream(bytes);
		}
		if (file != null) {
			return new FileInputStream(file);
		}
		return null;
	}

	private static KeyStore createTrustStore(String caCertData, String caCertFile) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
		try (InputStream pemInputStream = getInputStreamFromDataOrFile(caCertData, caCertFile)) {
			return createTrustStore(pemInputStream);
		}
	}

	private static KeyStore createTrustStore(InputStream pemInputStream) throws IOException, CertificateException, KeyStoreException, NoSuchAlgorithmException {
		KeyStore trustStore = KeyStore.getInstance("JKS");
		trustStore.load(null);

		while (pemInputStream.available() > 0) {
			CertificateFactory certFactory = CertificateFactory.getInstance("X509");
			X509Certificate cert = (X509Certificate) certFactory.generateCertificate(pemInputStream);

			String alias = cert.getSubjectX500Principal().getName();
			trustStore.setCertificateEntry(alias, cert);
		}
		return trustStore;
	}

	private static boolean isNullOrEmpty(final String string) {
		return string == null || string.isEmpty();
	}
}
