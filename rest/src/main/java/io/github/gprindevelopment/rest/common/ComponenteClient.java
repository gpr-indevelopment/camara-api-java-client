package io.github.gprindevelopment.rest.common;

import com.google.gson.reflect.TypeToken;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Type;

public class ComponenteClient {

    protected <T> T parseResultadoChamada(Call call, Type tipoEsperado) throws IOException, CamaraClientStatusException, RespostaNaoEsperadaException {
        try (Response response = call.execute()) {
            if (response.code() != 200) {
                throw new CamaraClientStatusException(response.code(),
                        response.body() != null ? response.body().string() : null);
            }
            ResponseBody body = response.body();
            if (body == null) {
                String mensagem = String.format("A resposta é null e o tipo esperado é %s.", tipoEsperado.getTypeName());
                throw new RespostaNaoEsperadaException(mensagem);
            }
            String json = body.string();
            Type tipoResultado = TypeToken.getParameterized(RespostaCamara.class, tipoEsperado).getType();
            RespostaCamara<T> resposta = GsonSingleton.getInstancia().fromJson(json, tipoResultado);
            return resposta.getDados();
        }
    }
}
