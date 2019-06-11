<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form:form action="note/customer,handyWorker,referee/writeComment.do" modelAttribute="note">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="report" />
	<form:hidden path="moment"/>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<form:label path="commentCustomer">
			<spring:message code="note.comment" />
		</form:label>
		<form:textarea path="commentCustomer" />
		<form:errors cssClass="error" path="commentCustomer" />
		<br />
		
		<form:hidden path="commentHandyWorker"/>
		<form:hidden path="commentReferee"/>
	</security:authorize>
	
	<security:authorize access="hasRole('HANDYWORKER')">
		<form:label path="commentHandyWorker">
			<spring:message code="note.comment" />
		</form:label>
		<form:textarea path="commentHandyWorker" />
		<form:errors cssClass="error" path="commentHandyWorker" />
		<br />
		
		<form:hidden path="commentCustomer"/>
		<form:hidden path="commentReferee"/>
	</security:authorize>	
	
	<security:authorize access="hasRole('REFEREE')">
		<form:label path="commentReferee">
			<spring:message code="note.comment" />
		</form:label>
		<form:textarea path="commentReferee" />
		<form:errors cssClass="error" path="commentReferee" />
		<br />
		
		<form:hidden path="commentCustomer"/>
		<form:hidden path="commentHandyWorker"/>
	</security:authorize>
	
	
		
	<input type="submit" name="save"
			value="<spring:message code="category.save" />"
			onclick="return confirm('<spring:message code="note.confirm.save" />')" />	

	<input type="button" name="cancel"
		value="<spring:message code="note.cancel" />"
		onclick="javascript: relativeRedir('note/customer,handyWorker,referee/list.do?reportId=${reportId}');" />

</form:form>    