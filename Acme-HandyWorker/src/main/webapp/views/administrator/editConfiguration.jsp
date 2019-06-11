<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

<form:form action="configuration/administrator/edit.do" modelAttribute="configuration">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:errors cssClass="error" path="id" />
	<form:errors cssClass="error" path="version" />

	<form:label path="spamWord">
		<spring:message code="configuration.spamWord" />:
		</form:label>
	<form:textarea path="spamWord" />
	<form:errors cssClass="error" path="spamWord" />
	<br />
	
	<form:label path="creditCardMakes">
		<spring:message code="configuration.creditCardMakes" />:
		</form:label>
	<form:textarea path="creditCardMakes" />
	<form:errors cssClass="error" path="creditCardMakes" />
	<br />
	
	<form:label path="vatTax">
		<spring:message code="configuration.vatTax" />:
	</form:label>
	<form:input path="vatTax" />
	<form:errors cssClass="error" path="vatTax" />
	<br />
	
	<form:label path="countryCode">
		<spring:message code="configuration.countryCode" />:
		</form:label>
	<form:input path="countryCode" />
	<form:errors cssClass="error" path="countryCode" />
	<br />
	
	<form:label path="finderTime">
		<spring:message code="configuration.finderTime" />:
		</form:label>
	<form:input path="finderTime" />
	<form:errors cssClass="error" path="finderTime" />
	<br />
	
	<form:label path="finderResult">
		<spring:message code="configuration.finderResult" />:
		</form:label>
	<form:input path="finderResult" />
	<form:errors cssClass="error" path="finderResult" />
	<br />
	
	<form:label path="banner">
		<spring:message code="configuration.banner" />:
		</form:label>
	<form:input path="banner" />
	<form:errors cssClass="error" path="banner" />
	<br />
	
	<form:label path="welcomeMessage">
		<spring:message code="configuration.welcomeMessage" />:
		</form:label>
	<form:input path="welcomeMessage" />
	<form:errors cssClass="error" path="welcomeMessage" />
	<br />
	
	<form:label path="welcomeMessageEs">
		<spring:message code="configuration.welcomeMessageEs" />:
		</form:label>
	<form:input path="welcomeMessageEs" />
	<form:errors cssClass="error" path="welcomeMessageEs" />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="configuration.save" />" />
		
	<input type="button" name="cancel"
		value="<spring:message code="configuration.cancel" />"
		onclick="javascript: relativeRedir('welcome/index.do');" />
	<br />

</form:form> 

</security:authorize>