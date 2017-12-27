package mesosphere.dcos.client;

import mesosphere.dcos.client.model.DCOSAuthCredentials;
import okhttp3.TlsVersion;

/**
 * Configuration to use when creating {@link DCOS} instances.
 */
public class Config {

	private final DCOSAuthCredentials credentials;
	private final boolean insecureSkipTlsVerify;
	private final String caCertData;
	private final String caCertFile;
	private final TlsVersion[] tlsVersions;

	private Config(Builder configBuilder) {
		this.credentials = configBuilder.credentials;
		this.insecureSkipTlsVerify = configBuilder.insecureSkipTlsVerify;
		this.caCertData = configBuilder.caCertData;
		this.caCertFile = configBuilder.caCertFile;
		this.tlsVersions = configBuilder.tlsVersions;
	}

	/**
	 * @return the configured authentication credentials (may be null).
	 */
	public DCOSAuthCredentials getCredentials() {
		return credentials;
	}

	/**
	 * @return true if TLS verification should be skipped, false otherwise.
	 */
	public boolean insecureSkipTlsVerify() {
		return insecureSkipTlsVerify;
	}

	/**
	 * @return the CA certificate data (Base64 encoded) to add to the trust store.
	 */
	public String getCaCertData() {
		return caCertData;
	}

	/**
	 * @return path to the CA certificate to be added to the trust store.
	 */
	public String getCaCertFile() {
		return caCertFile;
	}

	/**
	 * @return the TLS versions used
	 */
	public TlsVersion[] getTlsVersions() {
		return tlsVersions;
	}

	/**
	 * @return Non-null builder instance.
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * @param config Config to copy from
	 * @return Non-null initialized builder instance.
	 */
	public static Builder builder(final Config config) {
		return new Builder(config);
	}

	public static class Builder {
		private DCOSAuthCredentials credentials;
		private boolean insecureSkipTlsVerify;
		private String caCertData;
		private String caCertFile;
		private TlsVersion[] tlsVersions;

		/**
		 * Default Constructor
		 */
		public Builder() {
		}

		/**
		 * Constructor
		 * @param config Config to copy from
		 */
		public Builder(final Config config) {
			this.credentials = config.getCredentials();
			this.insecureSkipTlsVerify = config.insecureSkipTlsVerify();
			this.caCertData = config.caCertData;
			this.caCertFile = config.caCertFile;
		}

		/**
		 * skips TLS verification
		 * @return the builder
		 */
		public Builder insecureSkipTlsVerify() {
			this.insecureSkipTlsVerify = true;
			return this;
		}

		/**
		 * @param insecureSkipTlsVerify whether TLS verification should be skipped
		 * @return the builder
		 */
		public Builder withInsecureSkipTlsVerify(boolean insecureSkipTlsVerify) {
			this.insecureSkipTlsVerify = insecureSkipTlsVerify;
			return this;
		}

		/**
		 * @param credentials the authentication credentials
		 * @return the builder
		 */
		public Builder withCredentials(final DCOSAuthCredentials credentials) {
			this.credentials = credentials;
			return this;
		}

		/**
		 * @param caCertData the CA certificate data (Base64 encoded) to add to the trust store. This should NOT include the
		 * -----BEGIN CERTIFICATE----- or -----END CERTIFICATE----- markers
		 * @return the builder
		 */
		public Builder withCaCertData(final String caCertData) {
			this.caCertData = caCertData;
			return this;
		}

		/**
		 * @param caCertFile path to the CA certificate to be added to the trust store
		 * @return the builder
		 */
		public Builder withCaCertFile(final String caCertFile) {
			this.caCertFile = caCertFile;
			return this;
		}

		/**
		 * @param tlsVersions an array of TLS versions to use
		 * @return the builder
		 */
		public Builder withTlsVersions(final TlsVersion... tlsVersions) {
			this.tlsVersions = tlsVersions;
			return this;
		}

		/**
		 * @return an instance of {@link Config} using the current builder state
		 */
		public Config build() {
			return new Config(this);
		}
	}
}
