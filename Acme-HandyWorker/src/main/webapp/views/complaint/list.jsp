<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table pagesize="5" name="complaints" id="row" requestURI="${RequestURI}">

	<display:column property="ticker" titleKey="complaint.ticker" />
	<display:column property="moment" titleKey="complaint.moment" />
	<display:column property="description" titleKey="complaint.description" />
	<display:column titleKey="complaints.attachments">
		<a href="${row.attachments}"> 
			<jstl:out value="${row.attachments}" />
		</a>
	</display:column>
	
	<security:authorize access="hasRole('HANDYWORKER')">
	<display:column>
		<a href="complaint/handyWorker/display.do?complaintId=${row.id}">
			<spring:message code = "complaint.display"></spring:message>
		</a>
	</display:column>
	<display:column>
		<jstl:if test="${row.report.finalMode}">
			<a href="report/handyWorker/display.do?complaintId=${row.id}">
				<spring:message code="complaint.displayReport"/>
			</a>	
		</jstl:if>
	</display:column>
	</security:authorize>

	<security:authorize access="hasRole('CUSTOMER')">
	<display:column>
		<a href="complaint/customer/display.do?complaintId=${row.id}">
			<spring:message code = "complaint.display"></spring:message>
		</a>
	</display:column>
	<display:column>
		<jstl:if test="${row.report.finalMode}">
			<a href="report/customer/display.do?complaintId=${row.id}"><spring:message code="complaint.displayReport"/></a>	
		</jstl:if>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('REFEREE')">
	<display:column>
		<a href="complaint/referee/display.do?complaintId=${row.id}">
			<spring:message code = "complaint.display"></spring:message>
		</a>
	</display:column>
	<display:column>
		<jstl:if test="${empty row.report}">
			<a href="report/referee/create.do?complaintId=${row.id}"><spring:message code="complaint.createReport"/></a>	
		</jstl:if>	
	</display:column>
	
	<display:column>
		<jstl:if test="${not row.report.finalMode and not empty row.report}">
			<a href="report/referee/edit.do?reportId=${row.report.id}"><spring:message code="complaint.editReport"/></a>	
		</jstl:if>
	</display:column>
	
	<display:column>
		<jstl:if test="${row.report.finalMode}">
			<a href="report/referee/display.do?complaintId=${row.id}"><spring:message code="complaint.displayReport"/></a>	
		</jstl:if>
	</display:column>
	</security:authorize>
	


</display:table>

<input type="button" name="cancel"
		value="<spring:message code="referee.back" />"
		onclick="javascript: relativeRedir('')" />
