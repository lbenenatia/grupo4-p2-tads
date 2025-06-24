package um.edu.uy.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.opencsv.bean.AbstractBeanField;
import um.edu.uy.entities.Director;

import java.util.Map;

public class DirectorJson extends AbstractBeanField<String, String> {
    private Map<Integer, Director> directoresExistentes;

    public DirectorJson(Map<Integer, Director> directoresExistentes) {
        this.directoresExistentes = directoresExistentes;
    }

    @Override
    public Director convert(String value) {
        if (value.trim().isEmpty()) {
            return null;
        }
        // Reemplazo estructural seguro de comillas simples por dobles
        String json = value
                .replaceAll("([\\{,]\\s*)'(\\w+)'\\s*:", "$1\"$2\":") // claves
                .replaceAll(":\\s*'([^']*)'", ":\"$1\"");             // valores
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
                JsonElement id = e.getAsJsonObject().get("id");
                if (name != null && !name.isJsonNull() && id != null && !id.isJsonNull()) {
                    int directorId = id.getAsInt();
                    if (directoresExistentes.containsKey(directorId)) {
                        return directoresExistentes.get(directorId);
                    }
                    else {
                        String nombre = name.getAsString();
                        Director directorNuevo = new Director(directorId, nombre);
                        directoresExistentes.put(directorId, directorNuevo);
                        return directorNuevo;
                    }
                }
            }
        }
        return null;
    }
}
