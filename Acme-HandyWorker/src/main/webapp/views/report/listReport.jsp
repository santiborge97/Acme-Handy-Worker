<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
	<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
	
	<display:table name="reports" id="row" requestURI="${requestURI}" pagesize="5">
		
		<spring:message code = "report.moment" var = "moment"/>
		<display:column property="moment" title = "${moment}"/>
		
		<spring:message code = "report.description" var = "description"/>
		<display:column property="description" title = "${description}"/>
		
		<spring:message code = "report.attachments" var = "attachments"/>
		<display:column property="attachments" title = "${attachments}"/>
		
		<spring:message code = "report.complaint" var = "complaint"/>
		<display:column property="complaint.ticker" title = "${complaint}"/>
		
		<spring:message code = "report.finalMode" var = "finalMode"/>
		<display:column>
		
		<jstl:if test="${row.finalMode == false}">
		
			<a href="report/referee/edit.do?reportId=${row.id}">
			<spring:message code = "report.edit"></spring:message>
			</a>
		
		</jstl:if>
		</display:column>
		
	</display:table>
