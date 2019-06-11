<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jstl:if test="${noReport == 'error'}">
		<spring:message code="application.error.notFound" var="error" />
		<jstl:out value="${error }: " />
		<br />
		<br />
		
		<input type="button" name="back"
		value="<spring:message code="application.back" />"
		onclick="javascript: relativeRedir('application/${autoridad}/list.do');" />
		
</jstl:if>


<jstl:if test="${noReport != 'error'}">

<spring:message code="application.moment" var="moment" />
<jstl:out value="${moment }: " />
<jstl:out value="${application.moment}" />
<br>

<spring:message code="application.status" var="status" />
<jstl:out value="${status }: " />
<jstl:out value="${application.status}" />
<br>

<spring:message code="application.offeredPrice.amount" var="amount" />
<jstl:out value="${amount }: " />
<jstl:out value="${application.offeredPrice.amount}" />
<jstl:out value="${application.offeredPrice.currency}" />
<br>

<spring:message code="application.comment" var="comment" />
<jstl:out value="${comment }: " />
<jstl:out value="${application.comment}" />
<br>

<spring:message code="application.commentReject" var="commentReject" />
<jstl:out value="${commentReject }: " />
<jstl:out value="${application.commentReject}" />
<br>

<spring:message code="application.fixUpTask" var="fixUpTask" />
<jstl:out value="${fixUpTask }: " />
<jstl:out value="${application.fixUpTask.ticker}" />
<br>

<security:authorize access="hasRole('CUSTOMER')">

	<spring:message code="application.creditCard.holderName" var="holderName" />
	<jstl:out value="${holderName }: " />
	<jstl:out value="${application.creditCard.holderName}" />
	<br>
	
	<spring:message code="application.creditCard.brandName" var="brandName" />
	<jstl:out value="${brandName }: " />
	<jstl:out value="${application.creditCard.brandName}" />
	<br>
	
	<spring:message code="application.creditCard.number" var="number" />
	<jstl:out value="${number }: " />
	<jstl:out value="${application.creditCard.number}" />
	<br>
	
	<spring:message code="application.creditCard.expMonth" var="expMonth" />
	<jstl:out value="${expMonth }: " />
	<jstl:out value="${application.creditCard.expMonth}" />
	<br>
	
	<spring:message code="application.creditCard.expYear" var="expYear" />
	<jstl:out value="${expYear }: " />
	<jstl:out value="${application.creditCard.expYear}" />
	<br>

</security:authorize>



<input type="button" name="back"
	value="<spring:message code="report.back" />"
	onclick="javascript: relativeRedir('application/${autoridad}/list.do');" />
<br />
<br />

</jstl:if>