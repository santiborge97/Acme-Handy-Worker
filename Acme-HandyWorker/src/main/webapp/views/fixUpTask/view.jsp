<%--
 * view.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<div><spring:message code="fixUpTask.ticker"/>: <jstl:out value="${fixUpTask.ticker}"/></div>
<div><spring:message code="fixUpTask.moment"/>: <fmt:formatDate value="${fixUpTask.moment}" pattern="yyyy/MM/dd"/></div>
<div><spring:message code="fixUpTask.description"/>: <jstl:out value="${fixUpTask.description}"/></div>
<div><spring:message code="fixUpTask.address"/>: <jstl:out value="${fixUpTask.address}"/></div>
<div><spring:message code="fixUpTask.maximumPrice"/>: <jstl:out value="${fixUpTask.maximumPrice.amount}"/></div>
<div><spring:message code="fixUpTask.startDate"/>: <fmt:formatDate value="${fixUpTask.startDate}" pattern="yyyy/MM/dd"/></div>
<div><spring:message code="fixUpTask.endDate"/>: <fmt:formatDate value="${fixUpTask.endDate}" pattern="yyyy/MM/dd"/></div>
<jstl:if test="${language == 'es'}">
	<div><spring:message code="fixUpTask.category"/>: <jstl:out value="${fixUpTask.category.nameSp}"/></div>	
</jstl:if>
<jstl:if test="${language == 'en'}">
	<div><spring:message code="fixUpTask.category"/>: <jstl:out value="${fixUpTask.category.nameEn}"/></div>	
</jstl:if>

<div><spring:message code="fixUpTask.warranty"/>: <jstl:out value="${fixUpTask.warranty.title}"/></div>


<jstl:if test="${notHasApplicationAccepted}">
<security:authorize access="hasRole('HANDYWORKER')">
<a href="application/handyWorker/create.do?fixUpTaskId=${fixUpTask.id }"><spring:message code="fixUpTask.apply"/></a>
</security:authorize>
</jstl:if>

<jstl:if test="${isNotStarted && notHasApplicationAccepted}">
<security:authorize access="hasRole('CUSTOMER')">
<a href="fixUpTask/customer/edit.do?fixUpTaskId=${fixUpTask.id}"><spring:message code="fixUpTask.edit"/></a>
</security:authorize>
</jstl:if>

<security:authorize access="hasRole('HANDYWORKER')">
<input type="button" name="back" value="<spring:message code="fixUpTask.back" />"
	onclick="javascript: relativeRedir('fixUpTask/handyWorker/list.do');" />
</security:authorize>

<security:authorize access="hasRole('CUSTOMER')">
<input type="button" name="back" value="<spring:message code="fixUpTask.back" />"
	onclick="javascript: relativeRedir('fixUpTask/customer/list.do');" />
</security:authorize>


			


