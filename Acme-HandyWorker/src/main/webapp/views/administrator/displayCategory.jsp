<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jstl:if test="${noReport == 'error'}">
		<spring:message code="category.error" var="error" />
		<jstl:out value="${error }: " />
		<br />
		<br />
		
		<input type="button" name="back"
		value="<spring:message code="report.back" />"
		onclick="javascript: relativeRedir('category/administrator/list.do');" />
		
</jstl:if>


<jstl:if test="${noReport != 'error'}">

<spring:message code="category.nameEn" var="nameEn" />
<jstl:out value="${nameEn }: " />
<jstl:out value="${category.nameEn}" />
<br>

<spring:message code="category.nameSp" var="nameSp" />
<jstl:out value="${nameSp }: " />
<jstl:out value="${category.nameSp}" />
<br>

<spring:message code="category.parentEn" var="parentEn" />
<jstl:out value="${parentEn }: " />
<jstl:out value="${category.parent.nameEn}" />
<br>

<spring:message code="category.parentSp" var="parentSp" />
<jstl:out value="${parentSp }: " />
<jstl:out value="${category.parent.nameSp}" />
<br>

<input type="button" name="back"
	value="<spring:message code="report.back" />"
	onclick="javascript: relativeRedir('category/administrator/list.do');" />
<br />
<br />

</jstl:if>