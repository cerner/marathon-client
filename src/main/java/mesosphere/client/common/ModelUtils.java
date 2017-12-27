package mesosphere.client.common;

import mesosphere.dcos.client.model.ListSecretsResponse;
import mesosphere.marathon.client.model.v2.Volume;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ModelUtils {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
            .disableHtmlEscaping().registerTypeAdapter(Volume.class, new Volume.VolumeAdapter()).registerTypeAdapter(ListSecretsResponse.class, new ListSecretsResponse.ListSecretsResponseAdapter()).create();

    public static String toString(Object o) {
        return GSON.toJson(o);
    }

}
