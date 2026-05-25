package utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiCliente {

    public static String post(String endpoint, String jsonInput) {
        try {

            URL url = new URL(endpoint);

            HttpURLConnection conn
                    = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");

            conn.setRequestProperty(
                    "Content-Type",
                    "application/json"
            );

            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();
            os.close();

            BufferedReader br;

            if (conn.getResponseCode() >= 200
                    && conn.getResponseCode() < 300) {

                br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

            } else {

                br = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream())
                );
            }

            String output;
            StringBuilder response = new StringBuilder();

            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            conn.disconnect();

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 🔥 AQUÍ MISMO VA EL GET (ABAJO DEL POST)
    public static String get(String endpoint) {

        try {

            URL url = new URL(endpoint);

            HttpURLConnection conn
                    = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String output;
            StringBuilder response = new StringBuilder();

            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            conn.disconnect();

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String put(String urlString) throws Exception {

        URL url = new URL(urlString);

        HttpURLConnection conexion
                = (HttpURLConnection) url.openConnection();

        conexion.setRequestMethod("PUT");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        conexion.getInputStream()
                )
        );

        String inputLine;

        StringBuilder respuesta
                = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            respuesta.append(inputLine);
        }

        in.close();

        return respuesta.toString();
    }
    
}
