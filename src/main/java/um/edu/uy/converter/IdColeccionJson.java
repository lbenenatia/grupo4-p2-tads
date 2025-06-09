package um.edu.uy.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.opencsv.bean.AbstractBeanField;

public class IdColeccionJson extends AbstractBeanField<Integer, String> {
    @Override
    protected Integer convert(String value) {
        if (value.trim().isEmpty()) {
            return 0;
        }
        String json = value.replace('\'', '"');
        JsonElement element = JsonParser.parseString(json);
        JsonElement idElement = element.getAsJsonObject().get("id");
        return idElement != null && !idElement.isJsonNull() ? idElement.getAsInt() : 0;
    }

}
