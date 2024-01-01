package org.example.ch4;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ThroughputHttpServer {
    private static final String INPUT_FILE = "throughput/war_and_peace.txt";
    private static final int NUMBER_OF_THREADS = 4;

    public static void main(String[] args) throws IOException {
        Path filePath;
        InputStream resourceStream = ThroughputHttpServer.class.getClassLoader().getResourceAsStream(INPUT_FILE);
        if (resourceStream == null) {
            throw new IOException("file path가 잘못되었다");
        } else {
            // InputStream(바이트 스트림)으로부터 데이터를 읽어와서 문자열(String)로 변환하는 과정
            String text = new BufferedReader(new InputStreamReader(resourceStream)).lines().collect(Collectors.joining("\n"));
            startServer(text);
        }
    }

    public static void startServer(String text) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8100), 0);
        server.createContext("/search", new WordCountHandler(text));

        Executor executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        server.setExecutor(executor);

        server.start();
    }

    public static class WordCountHandler implements HttpHandler {
        private final String text;

        public WordCountHandler(String text) {
            this.text = text;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println(exchange.getRequestURI());
            String query = exchange.getRequestURI().getQuery();
            String[] keyValues = query.split("=");
            String key = keyValues[0];
            String value = keyValues[1];
            if (!key.equals("word")) {
                exchange.sendResponseHeaders(400, 0);
                return;
            }

            // 비즈니스 로직: 특정 단어 개수 카운트
            long count = countWord(value);

            // 응답 http구성
            byte[] response = Long.toString(count).getBytes();
            exchange.sendResponseHeaders(200, response.length);
            OutputStream outputResponse = exchange.getResponseBody();
            outputResponse.write(response);
            outputResponse.close();
        }

        private long countWord(String word) {
            long count = 0;
            int index = 0;
            while (index >= 0) {
                index = text.indexOf(word, index);

                if (index >= 0) {
                    count++;
                    index++;
                }
            }

            return count;
        }
    }
}
