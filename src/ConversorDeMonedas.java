import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversorDeMonedas {
    private static final String API_KEY = "d1f38f35af4168234e4c190b";

    public double convertir(String from, String to, double cantidad) {
        String url = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%f", API_KEY, from, to, cantidad);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body();
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            return jsonObject.get("conversion_result").getAsDouble();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al consultar la API", e);
        }
    }
}
