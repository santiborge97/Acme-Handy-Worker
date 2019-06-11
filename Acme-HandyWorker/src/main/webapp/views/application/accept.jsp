<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="application/${authority}/accept.do"
	modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="comment" />
	<form:hidden path="moment" />
	<form:hidden path="status" />
	<form:hidden path="offeredPrice.amount" />
	<form:hidden path="offeredPrice.currency" />
	<form:hidden path="fixUpTask" />
	
	<form:label path="creditCard.holderName">
		<spring:message code="application.creditCard.holderName"></spring:message>
	</form:label>
	<form:input path="creditCard.holderName" />
	<form:errors cssClass="error" path="creditCard.holderName"></form:errors>
	<br />
	
	<form:label path="creditCard.brandName">
		<spring:message code="application.creditCard.brandName"></spring:message>
	</form:label>
	<form:select path="creditCard.brandName" >
		<form:options items="${makes}"/>
	</form:select>
	<form:errors cssClass="error" path="creditCard.brandName"></form:errors>
	<br />
	
	<form:label path="creditCard.number">
		<spring:message code="application.creditCard.number"></spring:message>
	</form:label>
	<form:input path="creditCard.number" />
	<form:errors cssClass="error" path="creditCard.number"></form:errors>
	<br />
	
	<form:label path="creditCard.expMonth">
		<spring:message code="application.creditCard.expMonth"></spring:message>
	</form:label>
	<form:input path="creditCard.expMonth" />
	<form:errors cssClass="error" path="creditCard.expMonth"></form:errors>
	<br />
	
	<form:label path="creditCard.expYear">
		<spring:message code="application.creditCard.expYear"></spring:message>
	</form:label>
	<form:input path="creditCard.expYear" />
	<form:errors cssClass="error" path="creditCard.expYear"></form:errors>
	<br />
	
	<form:label path="creditCard.cvv">
		<spring:message code="application.creditCard.cvv"></spring:message>
	</form:label>
	<form:input path="creditCard.cvv" />
	<form:errors cssClass="error" path="creditCard.cvv"></form:errors>
	<br />

	<input type="submit" name="saveAccept"
		value="<spring:message code="application.save" />" /> 

	<input type="button" name="cancel"
		value="<spring:message code="application.cancel" />"
		onclick="javascript: relativeRedir('application/${authority}/list.do');" />
	<br />

</form:form>