<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Update house">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/house/update"
               modelAttribute="houseUpdate" cssClass="form-horizontal">

        <div class="form-group ${id_error?'has-error':''}">
            <form:label path="id" cssClass="col-sm-2 control-label">ID</form:label>
            <div class="col-sm-10">
                <form:input path="id" cssClass="form-control" readonly="true"/>
                <form:errors path="id" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
            <div class="col-sm-10">
                <form:input path="name" cssClass="form-control"/>
                <form:errors path="name" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${address_error?'has-error':''}">
            <form:label path="address" cssClass="col-sm-2 control-label">Address</form:label>
            <div class="col-sm-10">
                <form:input path="address" cssClass="form-control"/>
                <form:errors path="address" cssClass="help-block"/>
                <c:if test="${true == duplicate_address}">
                    <p style="color:darkred">House with this address already exists!</p>
                </c:if>
            </div>
        </div>

        <div class="form-group ${hauntedSince_error?'has-error':''}">
            <form:label path="hauntedSince" cssClass="col-sm-2 control-label">Haunted since</form:label>
            <div class="col-sm-10">
                <form:input path="hauntedSince" type="date" placeholder="yyyy-MM-dd" class="input-sm form-control"></form:input>
                <form:errors path="hauntedSince" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${history_error?'has-error':''}">
            <form:label path="history" cssClass="col-sm-2 control-label">History</form:label>
            <div class="col-sm-10">
                <form:input path="history" cssClass="form-control"/>
                <form:errors path="history" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${clue_error?'has-error':''}">
            <form:label path="clue" cssClass="col-sm-2 control-label">Clue</form:label>
            <div class="col-sm-10">
                <form:input path="clue" cssClass="form-control"/>
                <form:errors path="clue" cssClass="help-block"/>
            </div>
        </div>

        <table class="table" style="table-layout: fixed">
            <tbody>
            <tr>
                <td style="text-align: center;">
                    <button class="btn btn-primary" type="submit">Update</button>
                </td>
                <td style="text-align: center;">
                </td>
                <td style="text-align: center;">
                    <my:a href="/house/list" class="btn btn-primary">
                        Back
                    </my:a>
                </td>
            </tr>
            </tbody>
        </table>

    </form:form>


</jsp:attribute>
</my:pagetemplate>