<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

<display:table name="warranties" id="row" requestURI="${requestURI}" pagesize="5">
	
	<spring:message code = "warranty.title" var = "title"/>
	<display:column property="title" title = "${title}"/>
	<spring:message code = "warranty.terms" var = "terms"/>
	<display:column property="terms" title = "${terms}"/>
	<spring:message code = "warranty.law" var = "law"/>
	<display:column property="law" title = "${law}"/>
	<spring:message code = "warranty.finalMode" var = "finalMode"/>
	<display:column>
		<jstl:if test="${row.finalMode == false}">
			<a href="warranty/administrator/edit.do?warrantyId=${row.id}">
				<spring:message code = "warranty.edit"></spring:message>
			</a>
		</jstl:if>
	</display:column>
	<display:column>
		<a href="warranty/administrator/display.do?warrantyId=${row.id}">
			<spring:message code = "warranty.display"></spring:message>
		</a>
	</display:column>
</display:table>

<a href="warranty/administrator/create.do">
	<spring:message	code="warranty.create" />
</a>
<br />
<input type="button" name="cancel"
		value="<spring:message code="warranty.back" />"
		onclick="javascript: relativeRedir('welcome/index.do');" />
<br />

</security:authorize>