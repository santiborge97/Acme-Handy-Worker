<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
	<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h3><spring:message code="actor.customers" /></h3>
	
	<display:table name="customers" id="row" requestURI="${requestURI}" pagesize="5">
		
		<spring:message code = "actor.name" var = "name"/>
		<display:column property="name" title = "${name}"/>
		<spring:message code = "actor.surname" var = "surname"/>
		<display:column property="surname" title = "${surname}"/>
		
		
		<display:column>
		<div>
			<a href="message/actor/create.do?actorId=${row.id}"> <spring:message
 				code="actor.contact" />
			</a>
		</div>
		</display:column>
		
	</display:table>
	
<h3><spring:message code="actor.handyWorkers" /></h3>
	
	<display:table name="handyWorkers" id="row" requestURI="${requestURI}" pagesize="10">
		
		<spring:message code = "actor.name" var = "name"/>
		<display:column property="name" title = "${name}"/>
		<spring:message code = "actor.surname" var = "surname"/>
		<display:column property="surname" title = "${surname}"/>
		
		
		<display:column>
		<div>
			<a href="message/actor/create.do?actorId=${row.id}"> <spring:message
 				code="actor.contact" />
			</a>
		</div>
		</display:column>
		
	</display:table>
	
<h3><spring:message code="actor.administrators" /></h3>
	
	<display:table name="administrators" id="row" requestURI="${requestURI}" pagesize="5">
		
		<spring:message code = "actor.name" var = "name"/>
		<display:column property="name" title = "${name}"/>
		<spring:message code = "actor.surname" var = "surname"/>
		<display:column property="surname" title = "${surname}"/>
		
		
		<display:column>
		<div>
			<a href="message/actor/create.do?actorId=${row.id}"> <spring:message
 				code="actor.contact" />
			</a>
		</div>
		</display:column>
		
	</display:table>
	
<h3><spring:message code="actor.referees" /></h3>
	
	<display:table name="referees" id="row" requestURI="${requestURI}" pagesize="5">
		
		<spring:message code = "actor.name" var = "name"/>
		<display:column property="name" title = "${name}"/>
		<spring:message code = "actor.surname" var = "surname"/>
		<display:column property="surname" title = "${surname}"/>
		
		
		<display:column>
		<div>
			<a href="message/actor/create.do?actorId=${row.id}"> <spring:message
 				code="actor.contact" />
			</a>
		</div>
		</display:column>
		
	</display:table>
	
<h3><spring:message code="actor.sponsors" /></h3>
	
	<display:table name="sponsors" id="row" requestURI="${requestURI}" pagesize="5">
		
		<spring:message code = "actor.name" var = "name"/>
		<display:column property="name" title = "${name}"/>
		<spring:message code = "actor.surname" var = "surname"/>
		<display:column property="surname" title = "${surname}"/>
		
		
		<display:column>
		<div>
			<a href="message/actor/create.do?actorId=${row.id}"> <spring:message
 				code="actor.contact" />
			</a>
		</div>
		</display:column>
		
	</display:table>

	<input type="button" name="back" value="<spring:message code="actor.back" />"
	onclick="javascript: relativeRedir('box/actor/list.do');" />
