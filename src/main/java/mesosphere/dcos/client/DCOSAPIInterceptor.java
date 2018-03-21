package mesosphere.dcos.client;

import java.util.Collection;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import mesosphere.client.common.HeaderUtils;

public class DCOSAPIInterceptor implements RequestInterceptor {
    private final String marathonPath;
    private final String metronomePath;

    DCOSAPIInterceptor(String marathonPath, String metronomePath) {
        this.marathonPath = marathonPath;
        this.metronomePath = metronomePath;
    }

    @Override
    public void apply(RequestTemplate template) {
        Collection<String> apiSources = template.headers().get(HeaderUtils.API_SOURCE_HEADER);

        if (apiSources == null || apiSources.isEmpty()) {
            return;
        }

        if (apiSources.iterator().next().equals(HeaderUtils.MARATHON_API_SOURCE)) {
            template.insert(0, marathonPath);
        } else if (apiSources.iterator().next().equals(HeaderUtils.METRONOME_API_SOURCE)) {
            template.insert(0, metronomePath);
        }

        template.header(HeaderUtils.API_SOURCE_HEADER);
    }
}
