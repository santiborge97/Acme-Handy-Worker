<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

<form:form action="category/administrator/edit.do" modelAttribute="category">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="nameEn">
		<spring:message code="category.nameEn" />:
		</form:label>
	<form:input path="nameEn" />
	<form:errors cssClass="error" path="nameEn" />
	<br />
	
	<form:label path="nameSp">
		<spring:message code="category.nameSp" />:
		</form:label>
	<form:input path="nameSp" />
	<form:errors cssClass="error" path="nameSp" />
	<br />
	
	<jstl:if test="${category.id == 0}">
		<form:hidden path="parent" />
	</jstl:if>
	
	<jstl:if test="${category.id != 0}">
		<form:label path="parent">
			<spring:message code="category.parent" />:
		</form:label>
		<form:select path="parent" >	
		<form:options items="${categories}" itemValue="id" itemLabel="nameEn" />
		</form:select>
		<form:errors cssClass="error" path="parent" />
		<br />
	</jstl:if>
	
	<input type="submit" name="save"
		value="<spring:message code="category.save" />" />
		
	<jstl:if test="${category.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="category.delete" />"
			onclick="return confirm('<spring:message code="category.confirm.delete" />')" />
	</jstl:if>	

	<input type="button" name="cancel"
		value="<spring:message code="category.cancel" />"
		onclick="javascript: relativeRedir('category/administrator/list.do');" />
	<br />

</form:form>  

</security:authorize>  