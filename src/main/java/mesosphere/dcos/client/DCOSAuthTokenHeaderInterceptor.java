package mesosphere.dcos.client;

import static java.util.Objects.requireNonNull;

import com.google.common.base.Strings;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import mesosphere.dcos.client.model.DCOSAuthCredentials;
import mesosphere.dcos.client.model.DCOSAuthToken;

public class DCOSAuthTokenHeaderInterceptor implements RequestInterceptor {
    private final DCOS dcosClient;
    private final DCOSAuthCredentials authCredentials;
    private DCOSAuthToken dcosAuthToken;

    DCOSAuthTokenHeaderInterceptor(final DCOSAuthCredentials authCredentials, final DCOS dcosClient) {
        requireNonNull(authCredentials, "authCredentials:null");
        requireNonNull(dcosClient, "dcosClient:null");

        this.dcosClient = dcosClient;
        this.authCredentials = authCredentials;
    }

    @Override
    public void apply(RequestTemplate template) {
        if (dcosAuthToken == null || dcosAuthToken.requiresRefresh()) {
            dcosAuthToken = dcosClient.authenticate(authCredentials).toDCOSAuthToken();
        }

        template.header("Authorization", "token=" + dcosAuthToken.getToken());
    }

    public boolean hasToken() {
        return dcosAuthToken != null;
    }

    public void clearToken() {
        dcosAuthToken = null;
    }
}
