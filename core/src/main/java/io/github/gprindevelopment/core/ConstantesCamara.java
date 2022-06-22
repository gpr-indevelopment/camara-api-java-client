package io.github.gprindevelopment.core;

public class ConstantesCamara {

    private ConstantesCamara() throws IllegalAccessException {
        throw new IllegalAccessException("Essa classe não deve ser instanciada.");
    }

    public static final String BASE_URL = "https://dadosabertos.camara.leg.br/api/v2";

    public static final String LEGISLATURA_API_URL = BASE_URL + "/legislaturas";
}
