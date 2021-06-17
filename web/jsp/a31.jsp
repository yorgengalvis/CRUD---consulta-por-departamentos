<%-- 
    Document   : a1
    Created on : 13/06/2021, 11:29:56 p. m.
    Author     : Andrey R
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    </head>
    <body><br><br><br>
        <div class="container row justify-content-center align-items-center">
            <div class="card text-center" style="width: 30rem;">
                <h1>DANE</h1>
                <form class="row g-3" action="persona?a=consultar" method="POST">
                    <h4>Digite el nombre persona a consultar</h4>
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">Email</th>
                                <th scope="col">Direccion</th>
                                <th scope="col">Nombre Municipio</th>
                                <th scope="col">Nombre Departamento</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th scope="row"><c:out value="${persona.email}"></c:out></th>
                                <td><c:out value="${persona.direccion}"></c:out></td>
                                <td><c:out value="${persona.getIdMunicipio().getNombre()}"></c:out></td>
                                <td><c:out value="${persona.getIdMunicipio().getIdDpto().getNombre()}"></c:out></td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js" integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT" crossorigin="anonymous"></script>
    </body>
</html>
