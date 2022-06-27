package io.github.gprindevelopment.json;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Arrays;

public class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String dataHoraStr = json.getAsString();
        String data = dataHoraStr.split("T")[0];
        String hora = dataHoraStr.split("T")[1];

        int[] dataSplit = Arrays.stream(data.split("-")).mapToInt(Integer::parseInt).toArray();
        int[] horaSplit = Arrays.stream(hora.split(":")).mapToInt(Integer::parseInt).toArray();
        return LocalDateTime.of(dataSplit[0], dataSplit[1], dataSplit[2], horaSplit[0], horaSplit[1]);
    }
}
