/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Negocio.DANE;

/**
 *
 * @author madar
 */
public class Test_Persona {
    public static void main(String[] args) {
        //Servlets del controlador
        DANE dane=new DANE();
        System.out.println(dane.getListadoPersonas());
        
        
        
    }
}
