<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>




<form:form action="application/${authority}/reject.do"
	modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="comment" />
	<form:hidden path="moment" />
	<form:hidden path="status" />
	<form:hidden path="fixUpTask" />
	<form:hidden path="offeredPrice.amount" />
	<form:hidden path="offeredPrice.currency" />
	
	<form:label path="commentReject">
		<spring:message code="application.commentReject"></spring:message>
	</form:label>
	<form:input path="commentReject" />
	<form:errors cssClass="error" path="comment"></form:errors>
	<br />	

	<input type="submit" name="saveReject"
		value="<spring:message code="application.save" />" /> 

	<input type="button" name="cancel"
		value="<spring:message code="application.cancel" />"
		onclick="javascript: relativeRedir('application/${authority}/list.do');" />
	<br />

</form:form>