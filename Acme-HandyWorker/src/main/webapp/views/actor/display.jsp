<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<security:authorize access="isAuthenticated()">
	<security:authentication property="principal.username" var="user" />
</security:authorize>

<spring:message code="actor.name" var="name" />
<jstl:out value="${name }: " />
<jstl:out value="${actor.name}" />
<br>

<spring:message code="actor.middleName" var="middleName" />
<jstl:out value="${middleName}: " />
<jstl:out value="${actor.middleName}" />
<br>

<spring:message code="actor.surname" var="surname" />
<jstl:out value="${surname}: " />
<jstl:out value="${actor.surname}" />
<br>

<spring:message code="actor.email" var="email" />
<jstl:out value="${email }: " />
<jstl:out value="${actor.email}" />
<br>

<spring:message code="actor.photo" var="photo" />
<jstl:out value="${photo }: " />
<jstl:out value="${actor.photo}" />
<br>

<spring:message code="actor.phone" var="phone" />
<jstl:out value="${phone }: " />
<jstl:out value="${actor.phone}" />
<br>

<spring:message code="actor.address" var="address" />
<jstl:out value="${address }: " />
<jstl:out value="${actor.address}" />
<br>




<security:authorize access="hasRole('HANDYWORKER')">
	<spring:message code="actor.make" var="make" />
	<jstl:out value="${make }: " />
	<jstl:out value="${actor.make}" />
	<br>
</security:authorize>




<input type="button" name="socialProfile"
	value="<spring:message code="actor.socialProfiles" />"
	onclick="javascript: relativeRedir('socialProfile/administrator,customer,handyWorker,referee,sponsor/list.do');" />
<br />

<security:authorize access="isAuthenticated()">
	<jstl:if test="${user == actor.userAccount.username }">
		<input type="button" name="edit"
			value="<spring:message code="actor.edit" />"
			onclick="javascript: relativeRedir('actor/edit.do');" />
	</jstl:if>
</security:authorize>

<input type="button" name="back"
	value="<spring:message code="actor.back" />"
	onclick="javascript: relativeRedir('welcome/index.do');" />
<br />
<br />


	

