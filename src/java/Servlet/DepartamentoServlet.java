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
import DTO.Municio_Persona;
import DTO.Municipios;
import DTO.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author YorgenGalvis
 */
public class DepartamentoServlet extends HttpServlet {

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
        String action = request.getParameter("a");
        if (action == null) {
            obtieneDepartamento(request, response);
        } else {
            switch (action) {
                case "consultar":
                    validar(request, response);
                    break;
                    
            }
        }

    }

    protected void validar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer iddepartamento = Integer.parseInt(request.getParameter("departamento"));
        MunicipiosDAO mp = new MunicipiosDAO();
        List<Municipios> municipiosList = mp.readMunicipios();
        Persona_DAO pdo=new Persona_DAO();
        ArrayList<Municio_Persona> auxEnvia=new ArrayList<Municio_Persona>(); 
        
        List<Persona> personas=pdo.readPersonas();
       ArrayList<Municipios> aux=new ArrayList<Municipios>(); 
        for (Municipios m : municipiosList) {
            if (m.getIdDpto().getIdDpto() == iddepartamento) {
             aux.add(m);
                
            }
        }
        request.setAttribute("municipios", aux);
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/a21.jsp");
        dispatcher.forward(request, response);
    }

    protected void obtieneDepartamento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DepartamentoDAO dp = new DepartamentoDAO();
        List<Departamento> departamentos = dp.readDepartamento();

        request.setAttribute("departamentos", departamentos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/a2.jsp");
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
