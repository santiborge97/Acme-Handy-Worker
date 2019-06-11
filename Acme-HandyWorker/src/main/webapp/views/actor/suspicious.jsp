<%--
 * list.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<display:table pagesize="5" name="actors" id="row" 
requestURI="${requestURI }" class="displaytag" keepStatus="true">

<display:column property="userAccount.username" titleKey="actor.username" />

<display:column titleKey="actor.status" >
	<jstl:choose>
		<jstl:when test="${row.userAccount.isNotBanned == true }">
			<spring:message code="actor.unBanned" var="unBanned"/>
			<jstl:out value="${unBanned}"/>
		</jstl:when>
		<jstl:when test="${row.userAccount.isNotBanned == false }">
			<spring:message code="actor.banned" var="banned"/>
			<jstl:out value="${banned}"/>
		</jstl:when>
	</jstl:choose>	
</display:column>

<display:column>
	<a href="actor/administrator/banActor.do?actorId=${row.id }"><spring:message code="actor.ban"/></a>
</display:column>

</display:table>

<input type="button" name="back" value="<spring:message code="back" />"
	onclick="javascript: relativeRedir('welcome/index.do');" />








