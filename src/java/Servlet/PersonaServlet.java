/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import DAO.DepartamentoDAO;
import DAO.MunicipiosDAO;
import DAO.Persona_DAO;
import DTO.Departamento;
import DTO.Municipios;
import DTO.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author YorgenGalvis
 */
public class PersonaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("a");
        if (action == null) {
            seleccionaDepartamento(request, response);
        }
        switch (action) {
            case "step2":
                informacionPersona(request, response);
                break;
            case "validar":
                validar(request, response);
                break;
            case "confirmed_step3":
                confirmar_Step3(request, response);
                break;
            case "error_a1":
                error_a1(request, response);
                break;
            case "consulta": consulta(request,response);
                break;
            case "consultar": consultar(request,response);
            break;
            default:
                seleccionaDepartamento(request, response);
                break;
                
        }
    }
     protected void consultar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre=request.getParameter("nombre");
        Persona p=this.getPersona(nombre);
        
        request.setAttribute("persona", p); 
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/a31.jsp");
        dispatcher.forward(request, response);
    }
    
     private Persona getPersona(String nombre){
         Persona_DAO pd = new Persona_DAO();
        List<Persona> personas=pd.readPersonas();
         for (Persona p:personas) {
             if(p.getNombre().equals(nombre)){
                 return p; 
             }
         }
         return null;
     }
     
    protected void consulta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/a3.jsp");
        dispatcher.forward(request, response);
    }

    protected void confirmar_Step3(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/a13.jsp");
        dispatcher.forward(request, response);
    }

    protected void error_a1(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/Error-a1.jsp");
        dispatcher.forward(request, response);
    }

    protected void validar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer cedula = Integer.parseInt(request.getParameter("cedula"));
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String direccion = request.getParameter("direccion");
        Integer idMunicipio = Integer.parseInt(request.getParameter("municipio"));

        MunicipiosDAO mp = new MunicipiosDAO();
        Persona_DAO pd = new Persona_DAO();
        List<Municipios> municipiosList = mp.readMunicipios();

        Municipios mpo = null;
        for (Municipios m : municipiosList) {
            if (m.getIdMunicipio() == idMunicipio) {
                mpo = m;
            }
        }

        Persona p = new Persona();
        p.setNombre(nombre);
        p.setCedula(cedula);
        p.setEmail(email);
        p.setDireccion(direccion);
        p.setIdMunicipio(mpo);

        try {
            pd.insert(p);
            response.sendRedirect("persona?a=confirmed_step3");
        } catch (Exception ex) {
            response.sendRedirect("persona?a=error_a1");
        }
    }

    protected void informacionPersona(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer idDepartamento = Integer.parseInt(request.getParameter("departamento"));
        MunicipiosDAO mp = new MunicipiosDAO();
        List<Municipios> municipiosList = mp.readMunicipios();
        ArrayList<Municipios> aux = new ArrayList<Municipios>();
        for (Municipios m : municipiosList) {
            if (m.getIdDpto().getIdDpto() == idDepartamento) {
                aux.add(m);
            }
        }
        request.setAttribute("municipiosList", aux);
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/a12.jsp");
        dispatcher.forward(request, response);

    }

    protected void seleccionaDepartamento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DepartamentoDAO dp = new DepartamentoDAO();
        List<Departamento> departamentos = dp.readDepartamento();

        request.setAttribute("departamentos", departamentos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/a1.jsp");
        dispatcher.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
