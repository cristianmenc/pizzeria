/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzeria;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import modelo.Pizza;

/**
 *
 * @author daw
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private TitledPane masa;
    @FXML
    private RadioButton normal;
    @FXML
    private ToggleGroup tipoMasa;
    @FXML
    private RadioButton integral;
    @FXML
    private TitledPane tipoDePizza;
    @FXML
    private ComboBox<String> tipoPizza;
    @FXML
    private TitledPane ingredientesPizza;
    @FXML
    private ListView<String> ingredientesExtra;
    @FXML
    private TitledPane tamaño;
    @FXML
    private ComboBox<String> tamañoPizza;
    @FXML
    private Label precioMasa;
    @FXML
    private Label precioIngredientes;
    @FXML
    private Label precioTamaño;
    @FXML
    private Label precioTotal;
    @FXML
    private Label precioTipoPizza;

    ObservableList<String> listaTipoPizza = FXCollections.observableArrayList("basica", "Cuatro Quesos", "Barbacoa", "Mexicana");
    ObservableList<String> listaIngredientesExtra = FXCollections.observableArrayList("SIN INGREDIENTE EXTRA", "jamon", "queso", "tomate", "cebolla", "olivas");
    ObservableList<String> listaTamaños = FXCollections.observableArrayList("pequeña", "mediana", "familiar");
    @FXML
    private Label hMasa;
    @FXML
    private Label hTipoPizza;
    @FXML
    private Label hIngrediente;
    @FXML
    private Label hTamaño;
    @FXML
    private Accordion pestañas;
    @FXML
    private AnchorPane fondoMasa;
    @FXML
    private AnchorPane fondoPizza;
    @FXML
    private AnchorPane fondoTamaño;
    @FXML
    private Button reset;

    Pizza p1 = new Pizza("", "", "");
    @FXML
    private Button ticket;
    
    private File archivo;
    private DirectoryChooser d1 = new DirectoryChooser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tipoPizza.setItems(listaTipoPizza);
        ingredientesExtra.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ingredientesExtra.setItems(listaIngredientesExtra);
        tamañoPizza.setItems(listaTamaños);

        precioMasa.setText("0");
        precioIngredientes.setText("0");
        precioTipoPizza.setText("0");
        precioTamaño.setText("1");
        precioTotal.setText(String.valueOf(p1.calcularPrecio()));

    }

    @FXML
    private void seleccionarMasa(ActionEvent event) {
        if (normal.isSelected()) {
            p1.setMasa("normal");
            hMasa.setText("normal");
        }
        if (integral.isSelected()) {
            p1.setMasa("integral");
            hMasa.setText("integral");

        }
        precioMasa.setText(String.valueOf(p1.calcularPrecioMasa()));
        precioTotal.setText(String.valueOf(p1.calcularPrecio()));
    }

    @FXML
    private void seleccionarPizza(ActionEvent event) {
        String seleccionado = tipoPizza.getSelectionModel().getSelectedItem();
        hTipoPizza.setText(seleccionado);
        p1.setTipo(seleccionado);

        precioTipoPizza.setText(String.valueOf(p1.calcularPrecioTipo()));
        precioTotal.setText(String.valueOf(p1.calcularPrecio()));
    }

    @FXML
    private void seleccionarIngredientes(MouseEvent event) {
        double total = 0;
        hIngrediente.setText("");
        p1.setPrecioIngredientes(total);

        for (int i = 0; i < ingredientesExtra.getSelectionModel().getSelectedIndices().size(); i++) {
            String seleccionado = ingredientesExtra.getSelectionModel().getSelectedItems().get(i);
            hIngrediente.setText(seleccionado + ", " + hIngrediente.getText());
            p1.setIngredientes(seleccionado);
            precioIngredientes.setText(String.valueOf(p1.calcularPrecioIngredientes()));

        }
        p1.añadirIngredientes();

        precioTotal.setText(String.valueOf(p1.calcularPrecio()));

    }

    @FXML
    private void seleccionarTamaño(ActionEvent event) {
        String seleccionado = tamañoPizza.getSelectionModel().getSelectedItem();
        hTamaño.setText(seleccionado);
        p1.setTamaño(seleccionado);
        precioTamaño.setText(String.valueOf(p1.calcularPrecioTamaño()));
        precioTotal.setText(String.valueOf(p1.calcularPrecio()));
    }

    @FXML
    private void resetearPedido(ActionEvent event) {
        precioMasa.setText("0");
        precioIngredientes.setText("0");
        precioTipoPizza.setText("0");
        precioTamaño.setText("1");
        hMasa.setText("");
        hTipoPizza.setText("");
        hIngrediente.setText("");
        hTamaño.setText("");
        precioTotal.setText("0");
    }

    @FXML
    private void hacerTicket(ActionEvent event) {
        archivo = d1.showDialog(new Stage());
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH-mm-ss");
        LocalDateTime fecha = LocalDateTime.now();
        String fechaFormate = fecha.format(formato);
        Path archi = Paths.get(archivo.getAbsolutePath()+"\\"+fechaFormate+".txt");
        p1.generarTicket(archi.toFile());
    }

}
