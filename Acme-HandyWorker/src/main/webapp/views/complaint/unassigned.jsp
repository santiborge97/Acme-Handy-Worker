<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" name="complaints" id="row" requestURI="">

	<display:column property="ticker" titleKey="complaint.ticker" />
	<display:column property="moment" titleKey="complaint.moment" />
	<display:column property="description" titleKey="complaint.description" />
	<display:column titleKey="complaints.attachments">
		<a href="${row.attachments}"> 
			<jstl:out value="${row.attachments}" />
		</a>
	</display:column>
	<display:column titleKey="workplan.column">
		<a href="complaint/referee/assign.do?complaintId=${row.id}"><spring:message
				code="complaint.assign" /></a>
	</display:column>

</display:table>

<input type="button" name="cancel"
		value="<spring:message code="referee.back" />"
		onclick="javascript: relativeRedir('')" />
