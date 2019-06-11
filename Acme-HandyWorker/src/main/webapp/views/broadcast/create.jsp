<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="broadcast/administrator/create.do" modelAttribute="message">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="recipient" />
	<form:hidden path="spam" />
	<form:hidden path="moment" />
	<form:hidden path="sender" />
	<form:hidden path="boxes" />
	<form:hidden path="priority" />
	
	
	<form:label path="subject">
		<spring:message code="message.subject" />:
	</form:label>
	<form:input path="subject" />
	<form:errors cssClass="error" path="subject" />
	<br />

	<form:label path="body">
		<spring:message code="message.body" />:
	</form:label>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br />
	
	<form:label path="tags">
		<spring:message code="message.tags"></spring:message>
	</form:label>
	<form:textarea path="tags" />
	<form:errors cssClass="error" path="tags"></form:errors>
	<br />


	
	<input type="submit" name="save" value='<spring:message code="message.save"/>' />&nbsp;
	
	<input type="button" name="cancel"
		value='<spring:message code="message.cancel"/>'
		onclick="javascript: relativeRedir('/welcome/index.do');" />
	<br />
	
</form:form>