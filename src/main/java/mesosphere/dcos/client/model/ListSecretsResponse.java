package mesosphere.dcos.client.model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import mesosphere.client.common.ModelUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListSecretsResponse {
    private List<String> secrets;

    public void setSecrets(final List<String> secrets) {
        this.secrets = secrets;
    }

    public List<String> getSecrets() {
        return secrets;
    }

    public static class ListSecretsResponseAdapter implements JsonDeserializer<ListSecretsResponse>, JsonSerializer<ListSecretsResponse> {
        @Override
        public ListSecretsResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            ListSecretsResponse response = new ListSecretsResponse();
            response.setSecrets(jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject().get("array").getAsJsonArray(), new TypeToken<List<String>>() {
            }.getType()));
            return response;
        }

        @Override
        public JsonElement serialize(ListSecretsResponse response, Type type, JsonSerializationContext jsonSerializationContext) {
            Map<String, List<String>> map = new HashMap<>(2);
            map.put("array", response.getSecrets());
            return jsonSerializationContext.serialize(map);
        }
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}
