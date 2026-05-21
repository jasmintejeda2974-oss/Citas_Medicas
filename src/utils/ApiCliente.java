/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiCliente {

    public static String post(String endpoint, String jsonInput) {

        try {

            URL url = new URL(endpoint);

            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

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

            if (conn.getResponseCode() >= 200 &&
                    conn.getResponseCode() < 300) {

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
}
