package um.edu.uy.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.opencsv.bean.AbstractBeanField;

public class TituloColeccionJson extends AbstractBeanField<String, String> {
    @Override
    protected String convert(String value) {
        if (value.trim().isEmpty()) {
            return null;
        }
        String json = value.replace('\'', '"');
        JsonElement element = JsonParser.parseString(json);
        JsonElement nameElement = element.getAsJsonObject().get("name");
        return nameElement != null && !nameElement.isJsonNull() ? nameElement.getAsString() : null;
    }
}
