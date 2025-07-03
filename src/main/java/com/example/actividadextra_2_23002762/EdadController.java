package com.example.actividadextra_2_23002762;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EdadController {

    @FXML
    private Button buttonDias;

    @FXML
    private Button buttonEdadAños;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ImageView imageView;

    @FXML
    private Label labelDias;

    @FXML
    private Label lablEdad;

    // mensaje de alerte por si dan click si asignar una fecha
    // lo creamos como metodo y objeto para ser usado en varias lineas de codigo
    // y no repetir codigo
    private void mostrarError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // crearemos nuestros metodos antes de lo eventos de click
    // metodo  para calcular la edad dada una fecha de nacimiento y la fecha actual
    private int calcularEdad(LocalDate fechaNacimiento, LocalDate fechaActual) {
        // Se obtienen los años, meses y días de ambas fechas
        int years = fechaActual.getYear() - fechaNacimiento.getYear();
        int months = fechaActual.getMonthValue() - fechaNacimiento.getMonthValue();
        int days = fechaActual.getDayOfMonth() - fechaNacimiento.getDayOfMonth();
        // Se ajusta la edad en caso de que no se haya cumplido el año completo
        if (months < 0 || (months == 0 && days < 0)) {
            years--;
        }
        // Se devuelve la edad calculada
        return years;
    }
    // Método para calcular la diferencia en días entre dos fechas
    private long calcularDiferenciaEnDias(LocalDate fechaInicio, LocalDate fechaFin) {
        return ChronoUnit.DAYS.between(fechaInicio, fechaFin);
    }

    private long calcularDiferenciaEnDiasDesdeHoy(LocalDate fechaInicio) {
        LocalDate fechaFin = LocalDate.now();
        return calcularDiferenciaEnDias(fechaInicio, fechaFin);
    }


    // Método para actualizar la imagen en el ImageView según la edad
    private void actualizarImagenSegunEdad(int edad) {
        LocalDate fechaNacimiento = datePicker.getValue();
        if (fechaNacimiento == null) {
            //  no se ha seleccionado una fecha de nacimiento
            mostrarError("Error", "Seleccione una fecha de nacimiento.");
            return;
        }

        LocalDate fechaActual = LocalDate.now();
        int diaNacimiento = fechaNacimiento.getDayOfMonth();
        int mesNacimiento = fechaNacimiento.getMonthValue();
        int diaActual = fechaActual.getDayOfMonth();
        int mesActual = fechaActual.getMonthValue();

        //  si ya pasó el cumpleaños
        if ((mesActual > mesNacimiento) || (mesActual == mesNacimiento && diaActual >= diaNacimiento)) {
            // Ya pasó el cumpleaños
            imageView.setImage(new Image(getClass().getResource("/com/example/actividadextra_2_23002762/img/ya paso su cumpleaño.jpeg").toExternalForm()));
        } else if (mesActual == mesNacimiento && diaActual == diaNacimiento) {
            // hoy el día del cumpleaños
            imageView.setImage(new Image(getClass().getResource("/com/example/actividadextra_2_23002762/img/es su cumpleaño.jpeg").toExternalForm()));
        } else {
            // falta para el cumpleaños
            imageView.setImage(new Image(getClass().getResource("/com/example/actividadextra_2_23002762/img/esperando su cumpleaño.jpeg").toExternalForm()));
        }
    }


    // metodo para actualizar la imagen en el ImageView según la cantidad de días
    // utilizaremos un try catch para manejar exepciones o por si no nuestra la  imagen
    // tambien estoy utilizando getClass().getResource() sin el prefijo "/" porque
    // el path debe ser relativo al paquete de la clase actual.
    // También estoy usando .toExternalForm() para obtener la URL externa del recurso.
    private void actualizarImagenSegunDias(long dias) {
        try {
            if (dias < 100) {
                imageView.setImage(new Image(getClass().getResource("/com/example/actividadextra_2_23002762/img/99.jpeg").toExternalForm()));
            } else if (dias < 1000) {
                imageView.setImage(new Image(getClass().getResource("/com/example/actividadextra_2_23002762/img/999.jpeg").toExternalForm()));
            } else {
                imageView.setImage(new Image(getClass().getResource("/com/example/actividadextra_2_23002762/img/1000.jpeg").toExternalForm()));
            }
        } catch (Exception e) {
            // mensaje de error
            e.printStackTrace();
            mostrarError("Error de carga de imagen", "No se pudo cargar la imagen.");
        }
    }


    // click boton dias
    @FXML
    void clickDias(ActionEvent event) {
        LocalDate fechaSeleccionada = datePicker.getValue();
        if (fechaSeleccionada == null) {
            mostrarError("Error", "Seleccione una fecha antes de calcular los días.");
            return;
        }
        LocalDate fechaActual = LocalDate.now();
        long diasDiferencia = calcularDiferenciaEnDias(fechaSeleccionada, fechaActual);
        labelDias.setText("Su Edad En días es: " + diasDiferencia);
        actualizarImagenSegunDias(diasDiferencia);
    }
    // click boton años
    @FXML
    void clickEdadAños(ActionEvent event) {
        LocalDate fechaSeleccionada = datePicker.getValue();
        if (fechaSeleccionada == null) {
            mostrarError("Error", "Seleccione una fecha antes de calcular la edad.");
            return;
        }
        LocalDate fechaActual = LocalDate.now();
        int edad = calcularEdad(fechaSeleccionada, fechaActual);
        lablEdad.setText("Su Edad es: " + edad);
        actualizarImagenSegunEdad(edad);
    }

    public void dateUsuario(ActionEvent actionEvent) {
    }
}



