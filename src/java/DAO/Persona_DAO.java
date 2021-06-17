/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Persona;
import Persistencia.PersonaJpaController;
import java.util.List;

/**
 * Los métodos del CRUD
 * @author madarme
 */
public class Persona_DAO {

    private PersonaJpaController per;
    public Persona_DAO() {
        
        Conexion con=Conexion.getConexion();
        this.per=new PersonaJpaController(con.getBd());
        
    }
    
    
    public List<Persona> readPersonas()
    {
        return this.per.findPersonaEntities();
    }
    
    
    public void insert(Persona p) throws Exception{
        this.per.create(p);
    }
    
    
}
