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
package mesosphere.client.common;

import feign.Client;
import mesosphere.client.common.ssl.SSLUtils;
import mesosphere.dcos.client.Config;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;

public class HttpClientUtils {

	private HttpClientUtils() {
		// Utility
	}

	public static Client createHttpClient(final Config config) {
		try {
			OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

			httpClientBuilder.followRedirects(true);
			httpClientBuilder.followSslRedirects(true);

			if (config.insecureSkipTlsVerify()) {
				httpClientBuilder.hostnameVerifier((s, sslSession) -> true);
			}

			TrustManager[] trustManagers = SSLUtils.trustManagers(config);

			if (trustManagers != null && trustManagers.length == 1) {
				X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
				try {
					SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
					sslContext.init(null, trustManagers, new SecureRandom());
					httpClientBuilder.sslSocketFactory(sslContext.getSocketFactory(), trustManager);
				} catch (GeneralSecurityException e) {
					throw new IllegalStateException("The system has no TLS.");
				}
			} else {
				throw new IllegalStateException("Unexpected default trust managers: " + Arrays.toString(trustManagers));
			}

			if (config.getTlsVersions() != null && config.getTlsVersions().length > 0) {
				ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
						.tlsVersions(config.getTlsVersions())
						.build();
				httpClientBuilder.connectionSpecs(Arrays.asList(spec, ConnectionSpec.CLEARTEXT));
			}

			return new feign.okhttp.OkHttpClient(httpClientBuilder.build());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
