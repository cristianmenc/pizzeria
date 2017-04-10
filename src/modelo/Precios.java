/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author daw
 */
public class Precios {
    
    private String ingredientes;
    private double precio;
    Map<String, Double> listaIngredientes = new HashMap<>();

    public Precios(String ingredientes) {
        this.ingredientes = ingredientes;
        añadirDatos();
    }
    
    public void añadirDatos() {
        listaIngredientes.put("basica", 3.00);
        listaIngredientes.put("Cuatro Quesos", 5.00);
        listaIngredientes.put("Barbacoa", 7.00);
        listaIngredientes.put("Mexicana", 8.50);

        listaIngredientes.put("SIN INGREDIENTE EXTRA", 0.00);
        listaIngredientes.put("jamon", 0.50);
        listaIngredientes.put("queso", 0.75);
        listaIngredientes.put("tomate", 1.50);
        listaIngredientes.put("cebolla", 2.50);
        listaIngredientes.put("olivas", 1.00);

        listaIngredientes.put("pequeña", 1.00);
        listaIngredientes.put("mediana", 1.15);
        listaIngredientes.put("familiar", 1.30);
    }


    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }
    
    public double calcularPrecio(String ingredientes){
        double precio=0;
        if (listaIngredientes.containsKey(ingredientes)) {
                precio = listaIngredientes.get(ingredientes);
            }
        return precio;
    }
}
