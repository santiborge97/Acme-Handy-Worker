<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="tutorial/handyWorker/create.do"
	modelAttribute="tutorial">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />

	<form:label path="title">
		<spring:message code="tutorial.title" />:
		</form:label>
	<form:textarea path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	<br />
	
	<form:label path="summary">
		<spring:message code="tutorial.summary" />:
		</form:label>
	<form:textarea path="summary" />
	<form:errors cssClass="error" path="summary" />
	<br />
	<br />
	
	<form:label path="pictures">
		<spring:message code="tutorial.pictures" />:
		</form:label>
	<form:select id="pictures" path="pictures">
		<form:options items="${pictures}" />
	</form:select>
	<form:errors cssClass="error" path="pictures" />
	<br />
	<br />
	
	<form:label path="sections">
		<spring:message code="tutorial.sections" />:
		</form:label>
	<form:select id="sections" path="sections">
		<form:options items="${sections}" itemValue="id" itemLabel="title" />
	</form:select>
	<form:errors cssClass="error" path="sections" />
	<br />

	<input type="submit" name="save"
		value="save" /> 
		
	<jstl:if test="${tutorial.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="tutorial.delete" />"
			onclick="return confirm('<spring:message code="tutorial.confirm.delete" />')" />&nbsp;
	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="tutorial.cancel" />"
		onclick="javascript: relativeRedir('tutorial/handyWorker/list.do');" />
	<br />

</form:form>