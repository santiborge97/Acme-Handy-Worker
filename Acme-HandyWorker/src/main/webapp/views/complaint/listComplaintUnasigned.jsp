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
	<display:column property="description" titleKey="complaint.description" />
	<display:column titleKey="complaint.attachments">
		<a href="${row.attachment}"> 
			<jstl:out value="${row.attachment}" />
		</a>
	</display:column>
	<security:authorize access="hasRole('REFEREE')">
		<display:column>
			<a href="">
				<spring:message	code="announcement.asign" />
			</a>
		</display:column>		
	</security:authorize>

</display:table>
    