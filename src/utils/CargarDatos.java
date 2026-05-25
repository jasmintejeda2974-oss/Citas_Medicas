package utils;

import javax.swing.JComboBox;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CargarDatos {

    // =========================
    // CARGAR SINTOMAS
    // =========================
    public static void cargarSintomas(JComboBox<String> cbSintoma) {
        try {
            String respuesta = ApiCliente.get("http:// 192.168.0.118:8081/sintomas");
            JSONArray array = new JSONArray(respuesta);
            cbSintoma.removeAllItems();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                int id = obj.getInt("id");
                String nombre = obj.getString("nombre");
                cbSintoma.addItem(id + " - " + nombre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // CARGAR ESPECIALIDADES
    // =========================
    public static void cargarEspecialidades(javax.swing.JComboBox<String> cbEspecialidad) {
        try {
            String respuesta = ApiCliente.get(
                    "http:// 192.168.0.118:8081/especialidades"
            );
            org.json.JSONArray array = new org.json.JSONArray(respuesta);
            cbEspecialidad.removeAllItems();
            for (int i = 0; i < array.length(); i++) {
                org.json.JSONObject obj = array.getJSONObject(i);
                int id = obj.getInt("id");
                String nombre = obj.getString("nombre");
                cbEspecialidad.addItem(id + " - " + nombre
                );
            }
        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    // =========================
    // CARGAR DOCTORES
    // =========================
    public static void cargarDoctores(JComboBox<String> cbDoctor) {
        try {
            String respuesta = ApiCliente.get("http:// 192.168.0.118:8081/doctores");
            JSONArray array = new JSONArray(respuesta);
            cbDoctor.removeAllItems();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                int id = obj.getInt("id");
                String nombre = obj.getJSONObject("usuario").getString("nombre");
                cbDoctor.addItem(id + " - " + nombre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // DOCTORES POR ESPECIALIDAD
    // =========================
    public static void cargarDoctoresPorEspecialidad(
            JComboBox<String> cbEspecialidad,
            JComboBox<String> cbDoctor
    ) {

        try {

            if (cbEspecialidad.getSelectedItem() == null) {
                return;
            }

            String seleccion
                    = cbEspecialidad
                            .getSelectedItem()
                            .toString();

            System.out.println("ESPECIALIDAD: " + seleccion);

            String especialidad
                    = seleccion.split(" - ")[1];

            // CAMBIO AQUÍ
            String especialidadCodificada
                    = especialidad.replace(" ", "%20");

            String url
                    = "http:// 192.168.0.118:8081/doctores/especialidad/"
                    + especialidadCodificada;

            System.out.println("URL: " + url);

            String respuesta = ApiCliente.get(url);

            System.out.println("RESPUESTA: " + respuesta);

            JSONArray array = new JSONArray(respuesta);

            cbDoctor.removeAllItems();

            if (array.length() == 0) {
                cbDoctor.addItem("Sin doctores");
                System.out.println("NO HAY DOCTORES");
                return;
            }

            for (int i = 0; i < array.length(); i++) {

                JSONObject obj = array.getJSONObject(i);

                int id = obj.getInt("id");

                String nombre
                        = obj.getJSONObject("usuario")
                                .getString("nombre");

                cbDoctor.addItem(id + " - " + nombre);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // HORAS DISPONIBLES
    // =========================
   // =========================
    // HORAS DISPONIBLES
    // =========================
   // =========================
    // HORAS DISPONIBLES (CORREGIDO)
    // =========================
    public static void cargarHorasDisponibles(JComboBox<String> cbDoctor, JComboBox<String> cbFecha, JComboBox<String> cbHora) {
        try {
            if (cbDoctor.getSelectedItem() == null || cbFecha.getSelectedItem() == null) {
                return;
            }
            
            String doctorSeleccionado = cbDoctor.getSelectedItem().toString();

            if (doctorSeleccionado.equals("Sin doctores")) {
                cbHora.removeAllItems(); 
                return;
            }
            
            int doctorId = Integer.parseInt(doctorSeleccionado.split(" - ")[0]);
            String fecha = cbFecha.getSelectedItem().toString();

            // 🌐 Petición al endpoint que ya tienes mapeado en tu CitaController
            String respuesta = ApiCliente.get("http:// 192.168.0.118:8081/citas/ocupadas/" + doctorId + "/" + fecha);
            JSONArray horasOcupadas = new JSONArray(respuesta);
            
            String[] todasHoras = {
                "09:00:00",
                "10:00:00",
                "11:00:00",
                "12:00:00",
                "13:00:00",
                "14:00:00",
                "15:00:00"
            };

            cbHora.removeAllItems();
            
            for (String hora : todasHoras) {
                boolean ocupada = false;
                
                for (int i = 0; i < horasOcupadas.length(); i++) {
                    String horaBD = horasOcupadas.getString(i); // Viene como "11:00:00.000000" o "11:00"
                    
                    // 🔍 COMPARACIÓN SEGURA: Evaluamos si coincide al menos en los primeros 5 caracteres (HH:mm)
                    // Así "11:00:00.000000" y "11:00:00" se considerarán ocupados para "11:00:00"
                    if (horaBD.startsWith(hora.substring(0, 5))) {
                        ocupada = true;
                        break;
                    }
                }

                if (!ocupada) {
                    cbHora.addItem(hora);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
