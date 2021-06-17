/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Departamento;
import Persistencia.DepartamentoJpaController;
import java.util.List;

/**
 *
 * @author YorgenGalvis
 */
public class DepartamentoDAO {
    DepartamentoJpaController depar;
    
    public DepartamentoDAO() {
        
        Conexion con=Conexion.getConexion();
        this.depar=new DepartamentoJpaController(con.getBd());
        
    }
    
    
    public List<Departamento> readDepartamento()
    {
        return this.depar.findDepartamentoEntities();
    }
}
