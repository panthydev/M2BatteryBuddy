package com.panthydev.m2batteryapp.BackendClient;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class BackendHttpClient {

    // https://battery-buddy-backend-834594360876.europe-west1.run.app

    private static final int CONNECT_TIMEOUT_MS = 15_000;
    private static final int READ_TIMEOUT_MS = 30_000;

    private BackendHttpClient() {}

    public static BackendHttpResponse postJson(String endpointUrl, JSONObject payload) throws IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(endpointUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
            connection.setReadTimeout(READ_TIMEOUT_MS);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Accept", "application/json");

            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8))) {
                writer.write(payload.toString());
                writer.flush();
            }

            int responseCode = connection.getResponseCode();
            String responseBody = readResponse(connection, responseCode);
            return new BackendHttpResponse(responseCode, responseBody);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String readResponse(HttpURLConnection connection, int responseCode) throws IOException {
        BufferedReader reader = null;
        try {
            if (responseCode >= 200 && responseCode < 400) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            } else {
                if (connection.getErrorStream() == null) {
                    return "";
                }
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static final class BackendHttpResponse {
        public final int httpCode;
        public final String body;

        public BackendHttpResponse(int httpCode, String body) {
            this.httpCode = httpCode;
            this.body = body;
        }
    }
}


