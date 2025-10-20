import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversorDeMonedas {
    private static final String API_KEY = "d1f38f35af4168234e4c190b";

    private final HttpClient httpClient;

    public ConversorDeMonedas() {
        this(HttpClient.newHttpClient());
    }

    public ConversorDeMonedas(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public double convertir(String from, String to, double cantidad) {
        String url = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%f", API_KEY, from, to, cantidad);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Error en respuesta de la API: código " + response.statusCode());
            }
            return parseConversionResult(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al consultar la API", e);
        }
    }

    private double parseConversionResult(String jsonResponse) {
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            if (!jsonObject.has("conversion_result")) {
                throw new IllegalStateException("Campo conversion_result ausente");
            }
            return jsonObject.get("conversion_result").getAsDouble();
        } catch (RuntimeException e) {
            throw new RuntimeException("Respuesta inválida de la API", e);
        }
    }
}
