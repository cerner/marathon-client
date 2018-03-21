package mesosphere.dcos.client;

import feign.Feign;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import mesosphere.client.common.HttpClientUtils;
import mesosphere.client.common.ModelUtils;
import mesosphere.dcos.client.model.DCOSAuthCredentials;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

public class DCOSClient {
    public static DCOS getInstance(String endpoint) {
        return getInstance(endpoint, Config.builder().build());
    }

    public static DCOS getInstance(String endpoint, final DCOSAuthCredentials authCredentials) {
        return getInstance(endpoint, Config.builder().withCredentials(authCredentials).build());
    }

    public static DCOS getInstance(String endpoint, final Config config) {
        GsonDecoder decoder = new GsonDecoder(ModelUtils.GSON);
        GsonEncoder encoder = new GsonEncoder(ModelUtils.GSON);

        Feign.Builder builder = Feign.builder()
                .client(HttpClientUtils.createHttpClient(config))
                .encoder(encoder)
                .decoder(decoder);

        if (config.getCredentials() != null) {
            // Have to create a DCOS instance without authentication credentials that will be responsible for refreshing the authentication token.
            final Config noAuthConfig = Config.builder(config).withCredentials(null).build();
            final DCOSAuthTokenHeaderInterceptor dcosAuthTokenHeaderInterceptor = new DCOSAuthTokenHeaderInterceptor(config.getCredentials(), getInstance(endpoint, noAuthConfig));

            builder.requestInterceptor(dcosAuthTokenHeaderInterceptor);
            builder.errorDecoder(new DCOSErrorDecoder(dcosAuthTokenHeaderInterceptor));
        } else {
            builder.errorDecoder(new DCOSErrorDecoder());
        }

        builder.requestInterceptor(new DCOSAPIInterceptor(config.getMarathonPath(), config.getMetronomePath()));

        return builder.target(DCOS.class, endpoint);
    }

    private static class DCOSErrorDecoder implements ErrorDecoder {
        private final DCOSAuthTokenHeaderInterceptor authTokenHeaderInterceptor;

        public DCOSErrorDecoder() {
            this.authTokenHeaderInterceptor = null;
        }

        public DCOSErrorDecoder(DCOSAuthTokenHeaderInterceptor authTokenHeaderInterceptor) {
            this.authTokenHeaderInterceptor = authTokenHeaderInterceptor;
        }

        @Override
        public Exception decode(String methodKey, Response response) {

            String details;
            try {
                details = IOUtils.toString(response.body().asInputStream(), "UTF-8");
            } catch (NullPointerException | IOException e) {
                details = "Unable to read response body";
            }

            DCOSException e = new DCOSException(response.status(), response.reason(), methodKey, details);

            // WARNING: THIS IF STATEMENT IS NOT THREAD SAFE
            // TODO: Fix the thread safety of this if statement or token refresh/clear in general
            if (authTokenHeaderInterceptor != null && response.status() == 401 && authTokenHeaderInterceptor.hasToken()) {
                authTokenHeaderInterceptor.clearToken();
                return new RetryableException(response.reason(), e, null);
            }

            return e;
        }
    }
}
