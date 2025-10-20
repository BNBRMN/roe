import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.PushPromiseHandler;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;

import org.junit.jupiter.api.Test;

public class ConversorDeMonedasTest {

    @Test
    void convertir_retornaResultadoCuandoRespuestaEsValida() {
        HttpResponse<String> response = new StubHttpResponse(200, "{\"conversion_result\": 123.45}");
        ConversorDeMonedas conversor = new ConversorDeMonedas(new StubHttpClient(response));

        double resultado = conversor.convertir("USD", "ARS", 10);

        assertEquals(123.45, resultado);
    }

    @Test
    void convertir_lanzaExcepcionCuandoCodigoNoEs200() {
        HttpResponse<String> response = new StubHttpResponse(500, "{\"conversion_result\": 0}");
        ConversorDeMonedas conversor = new ConversorDeMonedas(new StubHttpClient(response));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> conversor.convertir("USD", "ARS", 10));

        assertEquals("Error en respuesta de la API: código 500", exception.getMessage());
    }

    @Test
    void convertir_lanzaExcepcionCuandoBodyEsInvalido() {
        HttpResponse<String> response = new StubHttpResponse(200, "{\"otra_clave\": 1}");
        ConversorDeMonedas conversor = new ConversorDeMonedas(new StubHttpClient(response));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> conversor.convertir("USD", "ARS", 10));

        assertEquals("Respuesta inválida de la API", exception.getMessage());
    }

    private static class StubHttpClient extends HttpClient {
        private final HttpResponse<String> response;
        private final IOException ioException;
        private final InterruptedException interruptedException;

        StubHttpClient(HttpResponse<String> response) {
            this(response, null, null);
        }

        StubHttpClient(IOException ioException) {
            this(null, ioException, null);
        }

        StubHttpClient(InterruptedException interruptedException) {
            this(null, null, interruptedException);
        }

        private StubHttpClient(HttpResponse<String> response, IOException ioException,
                InterruptedException interruptedException) {
            this.response = response;
            this.ioException = ioException;
            this.interruptedException = interruptedException;
        }

        @Override
        public Optional<CookieHandler> cookieHandler() {
            return Optional.empty();
        }

        @Override
        public Optional<Duration> connectTimeout() {
            return Optional.empty();
        }

        @Override
        public Redirect followRedirects() {
            return Redirect.NEVER;
        }

        @Override
        public Optional<ProxySelector> proxy() {
            return Optional.empty();
        }

        @Override
        public SSLContext sslContext() {
            try {
                return SSLContext.getDefault();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public SSLParameters sslParameters() {
            return new SSLParameters();
        }

        @Override
        public Optional<Authenticator> authenticator() {
            return Optional.empty();
        }

        @Override
        public Version version() {
            return Version.HTTP_1_1;
        }

        @Override
        public Optional<Executor> executor() {
            return Optional.empty();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> HttpResponse<T> send(HttpRequest request, BodyHandler<T> responseBodyHandler)
                throws IOException, InterruptedException {
            if (ioException != null) {
                throw ioException;
            }
            if (interruptedException != null) {
                throw interruptedException;
            }
            return (HttpResponse<T>) response;
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request,
                BodyHandler<T> responseBodyHandler) {
            throw new UnsupportedOperationException("Método no soportado en StubHttpClient");
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request,
                BodyHandler<T> responseBodyHandler, PushPromiseHandler<T> pushPromiseHandler) {
            throw new UnsupportedOperationException("Método no soportado en StubHttpClient");
        }
    }

    private static class StubHttpResponse implements HttpResponse<String> {
        private final int statusCode;
        private final String body;

        private StubHttpResponse(int statusCode, String body) {
            this.statusCode = statusCode;
            this.body = body;
        }

        @Override
        public int statusCode() {
            return statusCode;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            return HttpHeaders.of(Map.of(), (s, s2) -> true);
        }

        @Override
        public String body() {
            return body;
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return URI.create("https://example.com");
        }

        @Override
        public Version version() {
            return Version.HTTP_1_1;
        }

        @Override
        public Optional<Long> previousResponseBodyLength() {
            return Optional.empty();
        }

        @Override
        public Optional<HttpHeaders> trailers() {
            return Optional.empty();
        }
    }
}
