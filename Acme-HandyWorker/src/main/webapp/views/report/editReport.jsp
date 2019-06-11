<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="report/referee/edit.do"
	modelAttribute="report">

	<form:hidden path="id" />
	<form:errors cssClass="error" path="id" />
	<form:hidden path="version" />
	<form:errors cssClass="error" path="version" />
	<form:hidden path="moment" />
	<form:errors cssClass="error" path="moment" />
	<form:hidden path="complaint" />
	<form:errors cssClass="error" path="complaint" />
	<form:hidden path="referee" />
	<form:errors cssClass="error" path="referee" />
	<form:hidden path="notes" />
	<form:errors cssClass="error" path="notes" />

	<form:label path="description">
		<spring:message code="report.description" />:
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	<br />
	
	<form:label path="attachments">
		<spring:message code="report.attachments" />:
	</form:label>
	<form:input size="100" path="attachments" pattern="^http(s*)://(?:[a-zA-Z0-9-]+[\\.\\:]{0,1})+([a-zA-Z/]+)*(,http(s*)://(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]+)*$"/>
	<form:errors cssClass="error" path="attachments" />
	<%-- --%>
	<br />
	<br />
	
	<form:select path="finalMode">
        <form:option value="true" label="Final" />
        <form:option value="false" label="No final" />
    </form:select>
    <br />
	<br />

	<input type="submit" name="save" value="<spring:message code="report.save" />" />&nbsp;

	<input type="button" name="cancel" value="<spring:message code="report.cancel" />" onclick="javascript: relativeRedir('report/referee/list.do');" />
	
	<jstl:if test="${report.id != 0}">
        <input type="submit" name="delete"
            value="<spring:message code="report.delete" />"
            onclick="return confirm('<spring:message code="report.confirm.delete" />')" />
    </jstl:if>
	<br />

</form:form>


