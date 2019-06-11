<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="application/handyWorker/edit.do"
	modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="fixUpTask"/>
	<form:hidden path="moment"/>
	<form:hidden path="status"/>
	<form:hidden path="offeredPrice.currency" value = "euros"/>
	
	
	<form:label path="offeredPrice.amount">
		<spring:message code="application.offeredPrice.amount" />:
	</form:label>
	<form:input path="offeredPrice.amount" />
	<form:errors cssClass="error" path="offeredPrice.amount" />
	<br />
	
	<form:label path="comment">
		<spring:message code="application.comment" />:
		</form:label>
	<form:input path="comment" />
	<form:errors cssClass="error" path="comment" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="application.save" />" />&nbsp; 

	<input type="button" name="cancel"
		value="<spring:message code="application.cancel" />"
		onclick="javascript: relativeRedir('fixUpTask/handyWorker/list.do');" />
	<br />

</form:form>


