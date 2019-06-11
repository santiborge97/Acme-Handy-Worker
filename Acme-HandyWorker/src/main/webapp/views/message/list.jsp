<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>   

<display:table pagesize="5" name="messages" id="row" requestURI="${requestURI }" class="displaytag">

	<display:column property="sender.name" titleKey="message.sender">

	</display:column>
	
	<display:column property="subject" titleKey="message.subject">
	</display:column>
	
	<display:column property="body" titleKey="message.body">
	</display:column>
	
	<display:column property="priority" titleKey="message.priority">
	</display:column>
	
	<display:column property="tags" titleKey="message.tags">
	</display:column>
	
	<display:column>
			<a href="message/actor/display.do?messageId=${row.id}&boxId=${boxId}">
			<spring:message code = "message.display"></spring:message>
			</a>	
	</display:column>

</display:table>    

<input type="button" name="back" value="<spring:message code="message.back" />"
	onclick="javascript: relativeRedir('box/actor/list.do');" />