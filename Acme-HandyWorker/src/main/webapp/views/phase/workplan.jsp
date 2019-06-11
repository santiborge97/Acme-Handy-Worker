<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<security:authorize access="hasRole('HANDYWORKER')">


<display:table pagesize="5" name="phases" id="row" 
requestURI="${requestURI }">
<display:column property="title" titleKey="phase.title">
	<jstl:out value="${row.title}" />
</display:column>
<display:column property="description" titleKey="phase.description">
	<jstl:out value="${row.description}" />
</display:column>
<display:column titleKey="phase.startMoment">
	<fmt:formatDate value="${row.startMoment }" pattern="yyyy/MM/dd" />
</display:column>
<display:column titleKey="phase.endMoment">
	<fmt:formatDate value="${row.endMoment }" pattern="yyyy/MM/dd" />
</display:column>
<display:column titleKey="phase.edit">
	<a href="phase/handyWorker/edit.do?phaseId=${row.id}"><spring:message code="phase.edit"/></a>
</display:column>
</display:table>

<input type="button" name="create"
		value="<spring:message code="phase.create" />" onclick="javascript: relativeRedir('phase/handyWorker/create.do?fixUpTaskId=${param['fixUpTaskId']}');" />

<input type="button" name="back" value="<spring:message code="actor.back" />"
	onclick="javascript: relativeRedir('phase/handyWorker/list.do');" />
</body>
</html>
</security:authorize>