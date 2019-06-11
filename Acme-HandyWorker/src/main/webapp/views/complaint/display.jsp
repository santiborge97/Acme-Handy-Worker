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
		<spring:message code="complaint.error" var="error" />
		<jstl:out value="${error }: " />
		<br />
		<br />
		
		<input type="button" name="back"
		value="<spring:message code="report.back" />"
		onclick="javascript: relativeRedir('complaint/${autoridad}/list.do');" />
		
</jstl:if>


<jstl:if test="${noReport != 'error'}">

<spring:message code="complaint.ticker" var="ticker" />
<jstl:out value="${ticker }: " />
<jstl:out value="${complaint.ticker}" />
<br>

<spring:message code="complaint.moment" var="moment" />
<jstl:out value="${moment }: " />
<jstl:out value="${complaint.moment}" />
<br>

<spring:message code="complaint.description" var="description" />
<jstl:out value="${description }: " />
<jstl:out value="${complaint.description}" />
<br>

<spring:message code="complaint.attachments" var="attachments" />
<jstl:out value="${attachments }: " />
<jstl:out value="${complaint.attachments}" />
<br>

<spring:message code="complaint.referee" var="referee" />
<jstl:out value="${referee }: " />
<jstl:out value="${complaint.referee.userAccount.username}" />
<br>

<spring:message code="complaint.fixUpTask" var="fixUpTask" />
<jstl:out value="${fixUpTask }: " />
<jstl:out value="${complaint.fixUpTask.ticker}" />
<br>

<input type="button" name="back"
	value="<spring:message code="report.back" />"
	onclick="javascript: relativeRedir('complaint/${autoridad}/list.do');" />
<br />
<br />

</jstl:if>