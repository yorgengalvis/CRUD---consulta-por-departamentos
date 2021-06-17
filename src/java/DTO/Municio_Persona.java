/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author YorgenGalvis
 */
public class Municio_Persona {
    private String municipio;
    private Integer cantidad;

    public Municio_Persona() {
    }

    public Municio_Persona(String municipio, Integer cantidad) {
        this.municipio = municipio;
        this.cantidad = cantidad;
    }

    
    
    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "municipio=" + municipio + ", cantidad=" + cantidad ;
    }
    
    
    
    
}
