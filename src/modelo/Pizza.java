/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import modelo.Precios;

public class Pizza {

    private String masa;
    private String tipo;
    private String ingredientes;
    private String tamaño;
    private double precioMasa;
    private double precioTipo;
    private double precioIngredientes;
    private double precioTamaño;
    List<String> listaIngredientes = new ArrayList<>();
    Precios precio1 = new Precios("");
    DecimalFormat formato = new DecimalFormat("0.##");

    public Pizza(String masa, String tipo, String ingredientes) {
        this.masa = masa;
        this.tipo = tipo;
        this.ingredientes = ingredientes;
    }

    public String getMasa() {
        return masa;
    }

    public void setMasa(String masa) {
        this.masa = masa;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public double getPrecioIngredientes() {
        return precioIngredientes;
    }

    public void setPrecioIngredientes(double precioIngredientes) {
        this.precioIngredientes = precioIngredientes;
    }

    public double calcularPrecioMasa() {
        if (getMasa().equalsIgnoreCase("normal")) {
            this.precioMasa = 9.0;
        }
        if (getMasa().equalsIgnoreCase("integral")) {
            this.precioMasa = 9.5;
        }
        return this.precioMasa;
    }

    public double calcularPrecioTipo() {

        this.precioTipo = precio1.calcularPrecio(this.getTipo());
        return this.precioTipo;
    }

    public double calcularPrecioIngredientes() {
        if(precio1.listaIngredientes.containsKey(this.getIngredientes())){
            this.precioIngredientes = this.precioIngredientes + precio1.calcularPrecio(this.getIngredientes());
        }

        return this.precioIngredientes;
    }

    public double calcularPrecioTamaño() {

        this.precioTamaño = precio1.calcularPrecio(this.tamaño);
        return this.precioTamaño;
    }

    public void añadirIngredientes() {
        if (listaIngredientes.isEmpty()) {
            listaIngredientes.add(this.getIngredientes());
        } else if (listaIngredientes.contains(getIngredientes())) {
            listaIngredientes.remove(this.getIngredientes());
        } else {
            listaIngredientes.add(this.getIngredientes());
        }

    }
    
    public String pedido(){
        String resultado="";
        String listaIn="";
        for (int i = 0; i < listaIngredientes.size(); i++) {
            listaIn=listaIn+","+listaIngredientes.get(i);
        }
        resultado = "Masa:"+this.masa+": "+this.precioMasa+" €"+"\r\nTipo Pizza:"+this.tipo+": "+precio1.calcularPrecio(this.tipo)+" €"+"\r\nIngredientes:"+listaIn+": "+this.precioIngredientes+" €"+"\r\n"+"Tamaño:"+this.tamaño+": "+precio1.calcularPrecio(this.tamaño)+" %"+"\r\n"+"Total:"+calcularPrecio()+" €";
        return resultado;
    }

    public double calcularPrecio() {
        double precio = 0;
        String precioFormateado = "";
        if (this.precioTamaño == 0) {
            precio = (this.precioMasa + this.precioTipo + this.precioIngredientes);
            precioFormateado = formato.format(precio);
        } else {
            precio = (this.precioMasa + this.precioTipo + this.precioIngredientes) * this.precioTamaño;
            precioFormateado = formato.format(precio);
        }
        return precio;
    }

    public String generarTicket(File archivo) {
        String devuelve = "";
        try(BufferedWriter out = Files.newBufferedWriter(archivo.toPath(), StandardOpenOption.CREATE_NEW)){
            out.write(pedido());
        }catch (IOException e) {
            System.out.println("Error al abrir");
        }

        return devuelve;
    }
}

