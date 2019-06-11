<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<security:authorize access="hasRole('HANDYWORKER')">


<display:table pagesize="5" name="fixUpTasks" id="row" 
requestURI="${requestURI }" class="card">
<display:column property="ticker" titleKey="fixUpTask.ticker">
	<jstl:out value="${row.ticker}" />
</display:column>
<display:column property="description" titleKey="fixUpTask.description">
	<jstl:out value="${row.description}" />
</display:column>
<display:column titleKey="fixUpTask.startDate">
	<fmt:formatDate value="${row.startDate}" pattern="yyyy/MM/dd" />
</display:column>
<display:column titleKey="fixUpTask.endDate">
	<fmt:formatDate value="${row.endDate}" pattern="yyyy/MM/dd" />
</display:column>
<display:column titleKey="workplan.column">
	<a href="phase/handyWorker/workplan.do?fixUpTaskId=${row.id}"><spring:message code="workplan"/></a>
</display:column>
</display:table>

<input type="button" name="back" value="<spring:message code="actor.back" />"
	onclick="javascript: relativeRedir('welcome/index.do');" />
</security:authorize>