<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="box/actor/edit.do"
	modelAttribute="box">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="actor"/>
	<form:hidden path="byDefault"/>
	
	<form:label path="name">
		<spring:message code="box.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />
	<br />

	<input type="submit" name="save" value="<spring:message code="box.save" />" />&nbsp;

	<input type="button" name="cancel" value="<spring:message code="box.cancel" />" onclick="javascript: relativeRedir('box/actor/list.do');" />
	
	<jstl:if test="${box.id != 0}">
        <input type="submit" name="delete"
            value="<spring:message code="box.delete" />"
            onclick="return confirm('<spring:message code="box.confirm.delete" />')" />
    </jstl:if>
	<br />
			
</form:form> 	    