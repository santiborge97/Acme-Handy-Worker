<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="notes" id="row" requestURI="${requestURI}" pagesize="5">
	<spring:message code="note.momment" var="momentHeader"/>
	<display:column property="moment" title="${momentHeader}"/>
	<spring:message code="note.commentCustomer" var="commentCustomerHeader"/>	
	<display:column property="commentCustomer" title="${commentCustomerHeader}"/>
	<spring:message code="note.commentHandyWorker" var="commentHandyWorkerHeader"/>	
	<display:column property="commentHandyWorker" title="${commentHandyWorkerHeader}"/>
	<spring:message code="note.commentReferee" var="commentRefereeHeader"/>	
	<display:column property="commentReferee" title="${commentRefereeHeader}"/>
	<display:column>
	<security:authorize access="hasRole('CUSTOMER')">
		<jstl:if test="${empty row.commentCustomer}">
			<a href="note/customer,handyWorker,referee/writeComment.do?noteId=${row.id}">
				<spring:message	code="note.writeComment" />
			</a>
		</jstl:if>
	</security:authorize>
	<security:authorize access="hasRole('HANDYWORKER')">
		<jstl:if test="${empty row.commentHandyWorker}">
			<a href="note/customer,handyWorker,referee/writeComment.do?noteId=${row.id}">
				<spring:message	code="note.writeComment" />
			</a>
		</jstl:if>
	</security:authorize>
	<security:authorize access="hasRole('REFEREE')">
		<jstl:if test="${empty row.commentReferee}">
			<a href="note/customer,handyWorker,referee/writeComment.do?noteId=${row.id}">
				<spring:message	code="note.writeComment" />
			</a>
		</jstl:if>
	</security:authorize>
		
	</display:column>
	
		
</display:table>

<input type="button" name="cancel"
		value="<spring:message code="note.cancel" />"
		onclick="javascript: relativeRedir('report/${autoridad}/display.do?complaintId=${complaintId}');" />
