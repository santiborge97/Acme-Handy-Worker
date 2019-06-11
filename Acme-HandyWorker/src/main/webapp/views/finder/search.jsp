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

<security:authorize access="hasRole('HANDYWORKER')">
<p><spring:message code="finder.handyworker.search" /></p>

<form:form action="finder/handyworker/search.do" modelAttribute="finder"> 

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="lastUpdate"/>

	
	<form:label path="ticker">
		<spring:message code="fixUpTask.ticker"></spring:message>
	</form:label>
	<form:input path="ticker"/>
	<form:errors cssClass="error" path="ticker"></form:errors>
	<br/>
	
	<form:label path="moment">
		<spring:message code="fixUpTask.moment"></spring:message>
	</form:label>
	<form:input path="moment"/>
	<form:errors cssClass="error" path="moment"></form:errors>
	<br/>
	
	<form:label path="description">
		<spring:message code="fixUpTask.description"></spring:message>
	</form:label>
	<form:input path="description"/>
	<form:errors cssClass="error" path="description"></form:errors>
	<br/>
	
	<form:label path="address">
		<spring:message code="fixUpTask.address"></spring:message>
	</form:label>
	<form:input path="address"/>
	<form:errors cssClass="error" path="address"></form:errors>
	<br/>
	
	
		<form:label path="minimumPrice">
			<spring:message code="fixUpTask.minimumPrice"></spring:message>
		</form:label>
		<form:input path="minimumPrice"/>
	<form:errors cssClass="error" path="minimumPrice"></form:errors>
	<br/>
	
	
	<form:label path="maximumPrice">
			<spring:message code="finder.maximumPrice"></spring:message>
		</form:label>
		<form:input path="maximumPrice"/>
	<form:errors cssClass="error" path="maximumPrice"></form:errors>
	<br/>
	
	<form:label path="startDate">
			<spring:message code="fixUpTask.starDate"></spring:message>
		</form:label>
		<form:input path="startDate" placeholder="<spring:message code=fixUpTask.endDate/>"/>
	<form:errors cssClass="error" path="startDate"></form:errors>
	<br/>
	
	<form:label path="endDate">
			<spring:message code="fixUpTask.endDate"></spring:message>
		</form:label>
		<form:input path="endDate" placeholder="<spring:message code=fixUpTask.endDate/>"/>
	<form:errors cssClass="error" path="endDate"></form:errors>
	<br/>
	
	<form:label path="category">
			<spring:message code="fixUpTask.category"></spring:message>
		</form:label>
		<form:input path="cateogory"/>
	<form:errors cssClass="error" path="category"></form:errors>
	<br/>
	
	<form:label path="warranty">
			<spring:message code="fixUpTask.warranty"></spring:message>
		</form:label>
		<form:input path="warranty"/>
	<form:errors cssClass="error" path="warranty"></form:errors>
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="fixUpTask.search"/>"/>

</form:form>

<%-- Display Table --%>

<display:table name="fixUpTask" id="fixUpTask" 
requestURI="fixUpTask/handyworker&customer/list.do" pagesize="5" class="displaytag">

	<display:column property="ticker" titleKey="<spring:message code="fixUpTask.ticker"/>"/>
	<display:column property="moment" titleKey="<spring:message code="fixUpTask.moment"/>"/>
	<display:column property="description" titleKey="<spring:message code="fixUpTask.description"/>"/>
	<display:column property="address" titleKey="<spring:message code="fixUpTask.address"/>"/>
	<display:column property="maximumPrice" titleKey="<spring:message code="fixUpTask.maximumPrice"/>"/>
	<display:column property="startDate" titleKey="<spring:message code="fixUpTask.ticker"/>"/>
	<display:column property="endDate" titleKey="<spring:message code="fixUpTask.endDate"/>"/>
	<display:column property="category" titleKey="<spring:message code="fixUpTask.category"/>"/>
	<display:column property="warranty" titleKey="<spring:message code="fixUpTask.warranty"/>"/>
	
	<security:authorize access="hasRole('HANDYWORKER')">
	<a href="/customer/handyworker/view.do"><spring:message code="fixUpTask.customer"/></a>
	</security:authorize>
	
	<security:authorize access="hasRole('HANDYWORKER')">
	<a href="application/handyWorker/create.do?fixUpTaskId=${row.id }"><spring:message code="fixUpTask.application"/></a>
	</security:authorize>
	
	
	<security:authorize access="hasRole('HANDYWORKER')">
	<a href="/fixUpTask/handyworker&customer/view.do"><spring:message code="fixUpTask.details"/></a>
	</security:authorize>

	
	<input type="button" name="back" value="<spring:message code="fixUpTask.back"/>"/>
	
</display:table>



</security:authorize>


