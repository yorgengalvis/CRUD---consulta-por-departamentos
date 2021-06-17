<%-- 
    Document   : a12
    Created on : 13/06/2021, 11:30:05 p. m.
    Author     : Andrey R
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    </head>
    <body>
        <div class="container row justify-content-center align-items-center m-5">
            <div class="card">
                <div class="card-header">Registro de Persona</div>
                <form action="persona?a=validar" method="POST">
                <div class="card-body">
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">Cedula:</span>
                        <input type="text" class="form-control" name="cedula" placeholder="" >
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">Nombre:</span>
                        <input type="text" class="form-control" name="nombre" placeholder="" >
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">Email:</span>
                        <input type="text" class="form-control"name="email" placeholder="" >
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text" id="basic-addon1">Direccion:</span>
                        <input type="text" class="form-control" name="direccion" placeholder="" >
                    </div>
                    <div class="input-group mb-3">
                        <span class="input-group-text">Municipio:</span>
                        <select class="form-select form-control" name="municipio" aria-label="Default select example">
                            <c:forEach var="municipio" items="${municipiosList}">
                                <option value="${municipio.idMunicipio}"><c:out value="${municipio.nombre}"></c:out></option>
                            </c:forEach>
                        </select>
                    </div> 
                    <button type="submit" class="btn btn-success">Registrar Persona</button>
                </form>
                </div>
            </div>
        </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js" integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT" crossorigin="anonymous"></script>
 </body>
</html>
