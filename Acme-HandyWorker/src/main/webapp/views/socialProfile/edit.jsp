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

<form:form modelAttribute="socialProfile" action="socialProfile/administrator,customer,handyWorker,referee,sponsor/edit.do">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="actor" />
	
	<form:label path="nick">
		<spring:message code="socialProfile.nick" />:</form:label>
	<form:input path="nick" />
	<form:errors cssClass="error" path="nick" />
	<br />
	
	<form:label path="socialName">
		<spring:message code="socialProfile.socialName" />:</form:label>
	<form:input path="socialName" />
	<form:errors cssClass="error" path="socialName" />
	<br />
	
	<form:label path="link">
		<spring:message code="socialProfile.link" />:</form:label>
	<form:input size="50" path="link" />
	<form:errors cssClass="error" path="link" />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="socialProfile.save" />"/>
	

	<input type="button" name="cancel"
		value="<spring:message code="socialProfile.cancel" />"
		onclick="javascript: relativeRedir('socialProfile/customer,handyWorker,referee,sponsor/list.do');" />
	
</form:form>