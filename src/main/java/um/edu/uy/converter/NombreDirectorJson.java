package um.edu.uy.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.opencsv.bean.AbstractBeanField;

public class NombreDirectorJson extends AbstractBeanField<String, String> {
    @Override
    protected String convert(String value) {
        if (value.trim().isEmpty()) {
            return null;
        }
        String json = value.replace('\'', '"');
        JsonElement element = JsonParser.parseString(json);
        if (!element.isJsonArray()) {
            return null;
        }
        for (JsonElement e : element.getAsJsonArray()) {
            if (!e.isJsonObject()) {
                continue;
            }
            JsonElement job = e.getAsJsonObject().get("job");
            if (job != null && "Director".equals(job.getAsString())) {
                JsonElement name = e.getAsJsonObject().get("name");
                if (name != null && !name.isJsonNull()) {
                    return name.getAsString();
                }
            }
        }
        return null;
    }
}
