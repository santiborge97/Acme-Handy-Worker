<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form modelAttribute="fixUpTask" action="fixUpTask/customer/edit.do">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />

	<form:label path="address">
		<spring:message code="fixUpTask.address" />:</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />

	<form:label path="maximumPrice.amount">
		<spring:message code="fixUpTask.maximumPrice" />(<jstl:out value="${fixUpTask
			.maximumPrice.currency }"/>):</form:label>
	<form:input path="maximumPrice.amount" placeholder="00.0"/>
	<form:errors cssClass="error" path="maximumPrice.amount" />
	<br />

	<form:label path="startDate">
		<spring:message code="fixUpTask.startDate" />:</form:label>
	<form:input path="startDate"
		placeholder="yyyy/mm/dd" />
	<form:errors cssClass="error" path="startDate" />
	<br />


	<form:label path="endDate">
		<spring:message code="fixUpTask.endDate" />:</form:label>
	<form:input path="endDate"
		placeholder="yyyy/mm/dd" />
	<form:errors cssClass="error" path="endDate" />
	<br />

	<form:label path="description">
		<spring:message code="fixUpTask.description" />:</form:label>
	<form:textarea path="description" />
	<form:errors cssClass="error" path="description" />
	<br />

	<form:label path="warranty">
		<spring:message code="fixUpTask.warranty" />:</form:label>
	<form:select path="warranty">
		<form:option label="${warranty }" value="${warranty }"/>
		<form:options items="${warranties }" itemLabel="title"
			itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="warranty" />
	<br />
	
	<jstl:if test="${language == 'es'}">
	<form:label path="category">
		<spring:message code="fixUpTask.category" />:</form:label>
	<form:select path="category">
		<form:option label="${category }" value="${category }"/>
		<form:options items="${categories }" itemLabel="nameSp" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="category" />
	<br />
	</jstl:if>
	
	<jstl:if test="${language == 'en'}">
	<form:label path="category">
		<spring:message code="fixUpTask.category" />:</form:label>
	<form:select path="category">
		<form:option label="${category }" value="${category }"/>
		<form:options items="${categories }" itemLabel="nameEn" itemValue="id" />
	</form:select>
	<form:errors cssClass="error" path="category" />
	<br />
	</jstl:if>
	
	
	<form:hidden path="applications" />
	<form:hidden path="complaints" />
	<form:hidden path="moment" />
	<form:hidden path="maximumPrice.currency"/>
	

	<input type="submit" name="save"
		value="<spring:message code="fixUpTask.save" />"/>
	

	<jstl:if test="${fixUpTask.id!=0 && !hasApplications}">
		<input type="submit" name="delete"
			value="<spring:message code="fixUpTask.delete" />" />
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="fixUpTask.cancel" />"
		onclick="javascript: relativeRedir('fixUpTask/customer/list.do');" />
		
	
</form:form>











