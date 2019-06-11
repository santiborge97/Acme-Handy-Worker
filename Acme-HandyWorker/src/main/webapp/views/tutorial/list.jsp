<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="tutorial.turialList"/>

<input type="button" name="back"
	value="<spring:message code="tutorial.back" />"
	onclick="javascript: relativeRedir('welcome/index.do');" />
<br />
<br />


<display:table pagesize="5" class=displaytag
 name="tutorials" requestURI="${requestURI }">
 	<spring:message code="tutorial.title" var="title" />
	<display:column property="title" title="${title}" sortable="true" />

	<spring:message code="tutorial.moment" var="moment" />
	<display:column property="moment" title="${moment}" sortable="true" />

	<spring:message code="tutorial.summary" var="summary" />
	<display:column property="summary" title="${summary}" sortable="true" />

	<spring:message code="tutorial.pictures" var="pictures" />
	<display:column property="pictures" title="${pictures}"	sortable="true" />
	
	<display:column>
		<a href="handyWorker/unauthenticate/show.do?handyWorkerId=${row.id }"> <spring:message
				code="tutorial.handyWorker" />
		</a>
	</display:column>
	
	<display:column>
		<a href="tutorial/unauthenticate/show.do?tutorialId=${row.id }"> <spring:message
				code="tutorial.view" />
		</a>
	</display:column>
 </display:table>