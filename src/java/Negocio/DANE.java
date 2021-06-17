/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.Persona_DAO;
import DTO.Persona;
import java.util.List;

/**
 *
 * @author madar
 */
public class DANE {

    public DANE() {
    }

    /**
     * Reglas del negocio
     *
     * @return un listado de las personas en la BD
     */
    public String getListadoPersonas() {

        Persona_DAO per = new Persona_DAO();
        String msg = "\n<table>";
        msg += "\n<tr>"
                + "\n<td> Nombre </td>"
                + "\n<td> Cedula</td>"
                + "\n<td> E-mail</td>"
                + "\n<td> Dirección</td>"
                + "\n<td> Municipio</td>"
                + "\n<td> Departamento</td>"
                + "\n</tr>";
        List<Persona> myLista = per.readPersonas();
        for (Persona p : myLista) {

            msg += "\n<tr>"
                    + "\n<td>" + p.getNombre() + "</td>"
                    + "\n<td>" + p.getCedula() + "</td>"
                    + "\n<td>" + p.getEmail() + "</td>"
                    + "\n<td>" + p.getDireccion() + "</td>"
                    + "\n<td>" + p.getIdMunicipio().getNombre() + "</td>"
                    + "\n<td>" + p.getIdMunicipio().getIdDpto().getNombre() + "</td>"
                    + "\n</tr>";

        }
        return msg + "\n</table>";
    }
}
