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
		<spring:message code="warranty.error" var="error" />
		<jstl:out value="${error }: " />
		<br />
		<br />
		
		<input type="button" name="back"
		value="<spring:message code="report.back" />"
		onclick="javascript: relativeRedir('welcome/index.do');" />
		
</jstl:if>


<jstl:if test="${noReport != 'error'}">

<spring:message code="warranty.title" var="title" />
<jstl:out value="${title }: " />
<jstl:out value="${warranty.title}" />
<br>

<spring:message code="warranty.terms" var="terms" />
<jstl:out value="${terms }: " />
<jstl:out value="${warranty.terms}" />
<br>

<spring:message code="warranty.law" var="law" />
<jstl:out value="${law }: " />
<jstl:out value="${warranty.law}" />
<br>

<input type="button" name="back"
	value="<spring:message code="report.back" />"
	onclick="javascript: relativeRedir('warranty/administrator/list.do');" />
<br />
<br />

</jstl:if>