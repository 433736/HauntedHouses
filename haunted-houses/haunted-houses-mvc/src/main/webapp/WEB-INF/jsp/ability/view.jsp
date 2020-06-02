<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Ability Administration">
<jsp:attribute name="body">

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${ability.id}</td>
            <td><c:out value="${ability.name}"/></td>
            <td><c:out value="${ability.description}"/></td>
        </tr>
        </tbody>
    </table>

    <table class="table" style="table-layout: fixed">
        <tbody>
        <tr>
            <td style="text-align: center;">
                <form method="post" action="${pageContext.request.contextPath}/ability/update/${ability.id}">
                    <button type="submit" class="btn btn-primary">Update</button>
                </form>
            </td>
            <td style="text-align: center;">
                <form method="post" action="${pageContext.request.contextPath}/ability/delete/${ability.id}"
                      onsubmit="return confirm('Are you sure?') ? true : false;">
                    <button type="submit" class="btn btn-primary">Delete</button>
                </form>
            </td>
            <td style="text-align: center;">
                    <my:a href="/ability/list" class="btn btn-primary">
                        Back
                    </my:a>
            </td>
        </tr>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>