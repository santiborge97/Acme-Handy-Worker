<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<display:table pagesize="5" name="socialProfiles" id="row" 
requestURI="${requestURI }" class="displaytag">

	<display:column property="nick" titleKey="socialProfile.nick"/>
	
	<display:column property="socialName" titleKey="socialProfile.socialName"/>
	
	<display:column property="link" titleKey="socialProfile.link" />
	
	<display:column>
	<a href="socialProfile/administrator,customer,handyWorker,referee,sponsor/edit.do?socialProfileId=${row.id}"><spring:message code="socialProfile.edit"/></a>
	</display:column>
</display:table>

<input type="button" name="create"
		value="<spring:message code="socialProfile.create" />"
		onclick="javascript: relativeRedir('socialProfile/administrator,customer,handyWorker,referee,sponsor/create.do');" />

<input type="button" name="back"
	value="<spring:message code="socialProfile.back" />"
	onclick="javascript: relativeRedir('actor/displayPrincipal.do');" />
<br />
