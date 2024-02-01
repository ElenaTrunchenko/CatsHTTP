import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.example.Cat;

import java.io.IOException;
import java.util.List;

public class Main {
    //адрес REMOTE_SERVICE_URI контсанта
    public static final String REMOTE_SERVICE_URI =
            "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        // создаем клиента для отправки запроса
        CloseableHttpClient httpClient = HttpClientBuilder.create() // паттерн билдер HttpClientBuilder который собирает объект из разных параметров.
                // создаем билдер с помощью статического метода create
                // указываем настроки клиета которые мы хотим чтоб у него были. дейстия и заголовки по умолчанию
                // .setUserAgent("My Test Program")
                .setDefaultRequestConfig(RequestConfig.custom() // конфиг по умолчанию
                        .setConnectTimeout(5000)// если мы не смогли подключиться к СЕРВЕРУ за 5 мсек, то вернем ошибку
                        .setSocketTimeout(30000)//время жизни запроса
                        .setRedirectsEnabled(false)
                        .build())//метод build собрал конфиг
                .build(); //создал объект клиента
        HttpGet request = new HttpGet(REMOTE_SERVICE_URI);
        CloseableHttpResponse response = httpClient.execute(request);

        List<Cat> cats = mapper.readValue(
                response.getEntity().getContent(),
                new TypeReference<>() {
                });
        cats.stream().filter(value -> value.getUpvotes() != null && Integer.parseInt(value.getUpvotes()) > 0)
                .forEach(System.out::println);

        response.close();
        httpClient.close();
    }
}