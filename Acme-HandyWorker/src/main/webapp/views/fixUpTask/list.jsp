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




<%-- Search Fix-up Task --%>

<security:authorize access="hasRole('HANDYWORKER')">
<form:form action="${requestAction }" modelAttribute="finder"> 

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="lastUpdate"/>
	<form:hidden path="handyWorker"/>

	
	<form:label path="keyWord">
		<spring:message code="fixUpTask.keyWord"></spring:message>
	</form:label>
	<form:input path="keyWord"/>
	<form:errors cssClass="error" path="keyWord"></form:errors>
	
	<form:label path="category">
		<spring:message code="fixUpTask.category"></spring:message>
	</form:label>
	<form:input path="category"/>
	<form:errors cssClass="error" path="category"></form:errors>
	
	<form:label path="warranty">
		<spring:message code="fixUpTask.warranty"></spring:message>
	</form:label>
	<form:input path="warranty"/>
	<form:errors cssClass="error" path="warranty"></form:errors>
	
	<form:label path="minPrice">
		<spring:message code="fixUpTask.minimumPrice"></spring:message>
	</form:label>
	<form:input path="minPrice"/>
	<form:errors cssClass="error" path="minPrice"></form:errors>
	<br>
	
	<form:label path="maxPrice">
			<spring:message code="fixUpTask.maximumPrice"></spring:message>
		</form:label>
		<form:input path="maxPrice"/>
	<form:errors cssClass="error" path="maxPrice"></form:errors>
	
	
	<form:label path="minDate">
			<spring:message code="fixUpTask.minDate"></spring:message>
		</form:label>
		<form:input path="minDate" placeholder="yyyy/MM/dd"/>
	<form:errors cssClass="error" path="minDate"></form:errors>
	
	
	<form:label path="maxDate">
			<spring:message code="fixUpTask.maxDate"></spring:message>
		</form:label>
		<form:input path="maxDate" placeholder="yyyy/MM/dd"/>
	<form:errors cssClass="error" path="maxDate"></form:errors>
	
	<jstl:choose>
		<jstl:when test="${requestAction == 'finderFixUpTask/handyworker/find.do' }">
		<input type="submit" name="find" value="<spring:message code="fixUpTask.find"/>"/>
		</jstl:when>
		<jstl:when test="${requestAction == 'finderFixUpTask/handyworker/filter.do' }">
		<input type="submit" name="filter" value="<spring:message code="fixUpTask.filter"/>"/>
		</jstl:when>
	</jstl:choose>
	
</form:form> 
</security:authorize>
	

<%-- Display Table --%>

<display:table pagesize="${pagesize }" name="fixUpTasks" id="row" 
requestURI="${requestURI }" class="displaytag" >

	<display:column property="ticker" titleKey="fixUpTask.ticker">
	<jstl:out value="${row.ticker }"/>
	</display:column>
	
	<display:column titleKey="fixUpTask.moment"> 
	<fmt:formatDate value="${row.moment }" pattern="yyyy/MM/dd" />
	</display:column>
	
	<display:column property="description" titleKey="fixUpTask.description"/>
	
	<display:column property="address" titleKey="fixUpTask.address"/>
	
	<display:column  titleKey="fixUpTask.maximumPrice">
	<jstl:out value="${row.maximumPrice.amount }"/>
	(<jstl:out value="${row.maximumPrice.amount * (1+vatTax)}"/>)
	<jstl:out value="${row.maximumPrice.currency }"/>
	</display:column>
	
	<display:column titleKey="fixUpTask.startDate"> 
	<fmt:formatDate value="${row.startDate }" pattern="yyyy/MM/dd" />
	</display:column>
	
	<display:column titleKey="fixUpTask.endDate">
	<fmt:formatDate value="${row.endDate }" pattern="yyyy/MM/dd" />
	</display:column>
	
	<jstl:if test="${language == 'es'}">
		<display:column property="category.nameSp" titleKey="fixUpTask.category" />
	</jstl:if>
	
	<jstl:if test="${language == 'en'}">
		<display:column property="category.nameEn" titleKey="fixUpTask.category" />
	</jstl:if>
	
	
	<display:column property="warranty.title" titleKey="fixUpTask.warranty" />

	
	<security:authorize access="hasRole('HANDYWORKER')">
	<display:column paramProperty="ticker">
	<a href="actor/handyworker/displayCustomer.do?fixUpTaskId=${row.id }"><spring:message code="fixUpTask.customer"/></a>
	</display:column>
	</security:authorize>
	
	<%--<display:column>
	<a href="application/handyWorker/create.do?fixUpTaskId=${row.id }"><spring:message code="fixUpTask.application"/></a>
	</display:column>
	</security:authorize>--%>
	
	<display:column paramProperty="ticker">
	<a href="fixUpTask/customer,handyWorker/view.do?fixUpTaskId=${row.id }"><spring:message code="fixUpTask.details"/></a>
	</display:column>
	
	<security:authorize access="hasRole('CUSTOMER')">
	<display:column>
	<a href="complaint/customer/create.do?fixUpTaskId=${row.id}"><spring:message code="complaint.create"/></a>
	</display:column>
	</security:authorize>
	
</display:table>
	
	
	<input type="button" name="back" value="<spring:message code="fixUpTask.back" />"
	onclick="javascript: relativeRedir('welcome/index.do');" />
	
	<security:authorize access="hasRole('CUSTOMER')">
	<a href="fixUpTask/customer/create.do"><spring:message code="fixUpTask.create"/></a>
	</security:authorize>




