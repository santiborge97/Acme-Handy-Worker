<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form modelAttribute="complaint" action="complaint/customer/edit.do" >

	
	<form:hidden path="ticker" />
	<form:errors cssClass="error" path="ticker"></form:errors>
	<form:hidden path="moment" />
	<form:errors cssClass="error" path="moment"></form:errors>
	<form:hidden path="fixUpTask" />
	<form:errors cssClass="error" path="fixUpTask"></form:errors>
	
	<form:label path="description">
		<spring:message code="complaint.description" />:
	</form:label>
	<form:input path="description" />	
	<form:errors class="error" path="description" />
	<br/>
	
	<form:label path="attachments">
		<spring:message code="complaint.attachments" />:
	</form:label>
	<form:input size="100" path="attachments" pattern="^http(s*)://(?:[a-zA-Z0-9-]+[\\.\\:]{0,1})+([a-zA-Z/]+)*(,http(s*)://(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]+)*$"/>
	<form:errors cssClass="error" path="attachments" />
	<br />
	<br />
	
	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="customer.complaint.failed" />
		</div>
	</jstl:if>
	
	<input type="submit" name="save" value="<spring:message code="customer.save"/>" onclick="return confirm('<spring:message code="customer.saveConfirm" />')"/>
	
	<security:authorize access="hasRole('CUSTOMER')">
	<input type="button" name="cancel" 
	value="<spring:message code="customer.cancel" />" 
	onclick="javascript: relativeRedir('complaint/customer/list.do');" />
	</security:authorize>
	
	<security:authorize access="hasRole('HANDYWORKER')">
	<input type="button" name="cancel" 
	value="<spring:message code="back" />" 
	onclick="javascript: relativeRedir('complaint/handyWorker/list.do');" />
	</security:authorize>
	
</form:form>