<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

<display:table name="categories" id="row" requestURI="${requestURI}" pagesize="5">
	<spring:message code="category.nameEn" var="nameEnHeader"/>
	<display:column property="nameEn" title="${nameEnHeader}"/>
	<spring:message code="category.nameSp" var="nameSpHeader"/>	
	<display:column property="nameSp" title="${nameSpHeader}"/>
	<spring:message code="category.parentEn" var="parentEnHeader"/>
	<display:column property="parent.nameEn" title="${parentEnHeader}"/>
	<spring:message code="category.parentSp" var="parentSpHeader"/>
	<display:column property="parent.nameSp" title="${parentSpHeader}"/>
	
	<display:column>
		<jstl:if test="${not empty  row.parent}">
			<a href="category/administrator/edit.do?categoryId=${row.id}">
				<spring:message	code="category.edit" />
			</a>
		</jstl:if>
	</display:column>
	
	<display:column>
		<a href="category/administrator/display.do?categoryId=${row.id}">
			<spring:message code = "category.display"></spring:message>
		</a>
	</display:column>
</display:table>

	<div>
		<a href="category/administrator/create.do"> 
			<spring:message code="category.create" />
		</a>
	</div>
	<br />
	<input type="button" name="cancel"
		value="<spring:message code="category.back" />"
		onclick="javascript: relativeRedir('welcome/index.do');" />
	<br />
	
</security:authorize>	