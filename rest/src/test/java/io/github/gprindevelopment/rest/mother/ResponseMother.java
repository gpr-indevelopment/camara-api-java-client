package io.github.gprindevelopment.rest.mother;

import io.github.gprindevelopment.core.common.ConstantesCamara;
import okhttp3.*;

public class ResponseMother {

    public static Response gerarResponse(String corpo, int statusCode) {
        Request requisicao = new Request
                .Builder()
                .url(ConstantesCamara.BASE_URL)
                .build();
        Response resposta = new Response
                .Builder()
                .body(corpo != null
                        ? ResponseBody.create(corpo, MediaType.get("application/json"))
                        : null)
                .request(requisicao)
                .code(statusCode)
                .protocol(Protocol.HTTP_1_1)
                .message("Some message")
                .build();
        return resposta;
    }
}
