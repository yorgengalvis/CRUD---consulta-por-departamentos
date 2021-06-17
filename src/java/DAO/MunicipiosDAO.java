/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Municipios;
import Persistencia.MunicipiosJpaController;
import java.util.List;

/**
 *
 * @author YorgenGalvis
 */
public class MunicipiosDAO {
  MunicipiosJpaController muni;
    
    public MunicipiosDAO() {
        
        Conexion con=Conexion.getConexion();
        this.muni=new MunicipiosJpaController(con.getBd());
        
    }
    
    
    public List<Municipios> readMunicipios()
    {
        return this.muni.findMunicipiosEntities();
    }
    
    
}
