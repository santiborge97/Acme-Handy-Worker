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
		<spring:message code="report.error" var="error" />
		<jstl:out value="${error }: " />
		<br />
		<br />
		
		<input type="button" name="back"
		value="<spring:message code="report.back" />"
		onclick="javascript: relativeRedir('complaint/${autoridad}/list.do');" />
		
</jstl:if>


<jstl:if test="${noReport != 'error'}">

<spring:message code="report.moment" var="moment" />
<jstl:out value="${moment }: " />
<jstl:out value="${report.moment}" />
<br>

<spring:message code="report.description" var="description" />
<jstl:out value="${description }: " />
<jstl:out value="${report.description}" />
<br>

<spring:message code="report.attachments" var="attachments" />
<jstl:out value="${attachments }: " />
<jstl:out value="${report.attachments}" />
<br>

<spring:message code="report.complaint" var="complaint" />
<jstl:out value="${complaint }: " />
<jstl:out value="${report.complaint.ticker}" />
<br>

<input type="button" name="back"
	value="<spring:message code="report.back" />"
	onclick="javascript: relativeRedir('complaint/${autoridad}/list.do');" />
<br />
<br />

<form:form modelAttribute="report">
	<form:hidden path="id" />
	<form:hidden path="complaint.id" />
	
	<a href="note/customer,handyWorker,referee/list.do?reportId=${report.id}">
		<spring:message code = "report.note"></spring:message>
	</a>
	<br />
	<br />
	<a href="note/customer,handyWorker,referee/writeNew.do?reportId=${report.id }"> 
		<spring:message code="note.writeNew" />
	</a>
</form:form>

</jstl:if>