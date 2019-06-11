<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:message code="fixUpTask.keyWord" var="keyWord" />
<jstl:out value="${keyWord }: " />
<jstl:out value="${finder.keyWord}" />

<jstl:out value=" | " />

<spring:message code="fixUpTask.category" var="category" />
<jstl:out value="${category }: " />
<jstl:out value="${finder.category}" />

<jstl:out value=" | " />

<spring:message code="fixUpTask.warranty" var="warranty" />
<jstl:out value="${warranty }: " />
<jstl:out value="${finder.warranty}" />

<jstl:out value=" | " />
<br>

<spring:message code="fixUpTask.minimumPrice" var="minPrice" />
<jstl:out value="${minPrice }: " />
<jstl:out value="${finder.minPrice}" />

<jstl:out value=" | " />

<spring:message code="fixUpTask.maximumPrice" var="maxPrice" />
<jstl:out value="${maxPrice }: " />
<jstl:out value="${finder.maxPrice}" />

<jstl:out value=" | " />

<spring:message code="fixUpTask.minDate" var="minDate" />
<jstl:out value="${minDate }: " />
<jstl:out value="${finder.minDate}"/>

<jstl:out value=" | " />

<spring:message code="fixUpTask.maxDate" var="maxDate" />
<jstl:out value="${maxDate }: " />
<jstl:out value="${finder.maxDate}"/>

<jstl:out value=" | " />

<a href="fixUpTask/handyWorker/list.do"><spring:message code="finder.reset"/></a>

<br>

<display:table name="fixUpTasks" id="row" requestURI="" pagesize="${fixUpTasks.size() }">

<display:column property="ticker" titleKey="fixUpTask.ticker">
	<jstl:out value="${row.ticker}" />
	</display:column>
	
	<display:column property="moment" titleKey="fixUpTask.moment">
	<jstl:out value="${row.moment}" />
	</display:column>
	
	<display:column property="description" titleKey="fixUpTask.description">
	<jstl:out value="${row.description}" />
	</display:column>
	
	<display:column property="address" titleKey="fixUpTask.address">
	<jstl:out value="${row.address}" />
	</display:column>
	
	<display:column titleKey="fixUpTask.maximumPrice">
	<jstl:out value="${row.maximumPrice.amount }"/>
	<jstl:out value="${row.maximumPrice.currency }"/>
	</display:column>
	
	<display:column property="startDate" titleKey="fixUpTask.startDate">
	<jstl:out value="${row.startDate}" />
	</display:column>
	
	<display:column property="endDate" titleKey="fixUpTask.endDate">
	<jstl:out value="${row.endDate}" />
	</display:column>
	
	<jstl:if test="${language == 'es'}">
		<display:column property="category.nameSp" titleKey="fixUpTask.category" />
	</jstl:if>
	
	<jstl:if test="${language == 'en'}">
		<display:column property="category.nameEn" titleKey="fixUpTask.category" />
	</jstl:if>
	
	<display:column property="warranty.title" titleKey="fixUpTask.warranty">
	<jstl:out value="${row.warranty.title}" />
	</display:column>

	
	
	<display:column>
	<a href="actor/handyworker/displayCustomer.do?fixUpTaskId=${row.id }"><spring:message code="fixUpTask.customer"/></a>
	</display:column>
	
	
	<display:column>
	<a href="fixUpTask/customer,handyWorker/view.do?fixUpTaskId=${row.id }"><spring:message code="fixUpTask.details"/></a>
	</display:column>

</display:table>



