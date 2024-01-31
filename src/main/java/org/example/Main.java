//package org.example;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.http.HttpHeaders;
//import org.apache.http.client.config.RequestConfig;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.entity.ContentType;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//
//import java.io.IOException;
//import java.util.List;
//
//public class Main {
//    public static final String REMOTE_SERVICE_URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
//    public static ObjectMapper mapper = new ObjectMapper();
//
//    public static void main(String[] args) throws IOException {
//        CloseableHttpClient httpClient = HttpClientBuilder.create()
//                .setDefaultRequestConfig(RequestConfig.custom()
//                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
//                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
//                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
//                        .build())
//                .build();
//        HttpGet request = new HttpGet(REMOTE_SERVICE_URL);
//        request.setHeader(HttpHeaders.ACCEPT, String.valueOf(ContentType.APPLICATION_JSON.getCharset()));
////        отправка запроса
//        CloseableHttpResponse response = httpClient.execute(request);
//
//        List<Cat> cats = mapper.readValue(response.getEntity().getContent(),                 // методе readValue производит дисеренизацию
//                 // указать откуда читать и какой тип читать
//                new TypeReference<>() {});
//        cats.stream().filter(value -> value.getUpvotes() != null && Integer.parseInt(value.getUpvotes()) > 0)
//                .forEach(System.out::println);
//
//        response.close();
//        httpClient.close();
//    }
//}
//
//

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
    public static final String REMOTE_SERVICE_URI =
            "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        HttpGet request = new HttpGet(REMOTE_SERVICE_URI);
        CloseableHttpResponse response = httpClient.execute(request);

        List<Cat> cats = mapper.readValue(
                response.getEntity().getContent(),
                new TypeReference<>() {
                });
        cats.stream().filter(value -> value.getUpvotes() != null && Integer.parseInt(value.getUpvotes()) > 0)
                .forEach(System.out::println);
    }
}