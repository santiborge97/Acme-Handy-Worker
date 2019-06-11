<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>   

<display:table pagesize="5" name="boxes" id="row" requestURI="${requestURI }" class="displaytag">

	
	<display:column property="name" titleKey="box.name">
	<jstl:out value="${row.name}" />
	</display:column>
	
	<display:column>
		<jstl:if test="${row.byDefault == false}">
			<a href="box/actor/edit.do?boxId=${row.id}">
			<spring:message code = "box.edit"></spring:message>
			</a>
		</jstl:if>
	</display:column>
		
	
	<display:column>
	<a href="message/actor/list.do?boxId=${row.id}"><spring:message code="box.messages"/></a>
	</display:column>
	
</display:table>    

	<input type="button" name="back" value="<spring:message code="box.back" />"
	onclick="javascript: relativeRedir('welcome/index.do');" />
	
	<security:authorize access="isAuthenticated()">
	<a href="box/actor/create.do"><spring:message code="box.create"/></a>
	</security:authorize>
	
	<security:authorize access="isAuthenticated()">
	<a href="actor/list.do"><spring:message code="box.listActor"/></a>
	</security:authorize>


	