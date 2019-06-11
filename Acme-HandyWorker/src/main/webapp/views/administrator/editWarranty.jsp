<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

<form:form action="warranty/administrator/edit.do"
	modelAttribute="warranty">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="title">
		<spring:message code="warranty.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	<br />
	
	<form:label path="terms">
		<spring:message code="warranty.terms" />:
	</form:label>
	<form:textarea path="terms" />
	<form:errors cssClass="error" path="terms" />
	<br />
	<br />
	
	<form:label path="law">
		<spring:message code="warranty.law" />:
	</form:label>
	<form:textarea path="law" />
	<form:errors cssClass="error" path="law" />
	<br />
	<br />
	
	<form:select path="finalMode">
        <form:option value="true" label="Final" />
        <form:option value="false" label="No final" />
    </form:select>
    <br />
	<br />

	<input type="submit" name="save" value="<spring:message code="warranty.save" />" />&nbsp;

	<input type="button" name="cancel" value="<spring:message code="warranty.cancel" />" onclick="javascript: relativeRedir('warranty/administrator/list.do');" />
	
	<jstl:if test="${warranty.id != 0}">
        <input type="submit" name="delete"
            value="<spring:message code="warranty.delete" />"
            onclick="return confirm('<spring:message code="warranty.confirm.delete" />')" />
    </jstl:if>
	<br />

</form:form>

</security:authorize>
