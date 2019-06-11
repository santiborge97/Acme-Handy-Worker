<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="message/actor/edit.do"
	modelAttribute="message1">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="moment" />
	<form:hidden path="subject" />
	<form:hidden path="body" />
	<form:hidden path="priority" />
	<form:hidden path="tags" />
	<form:hidden path="spam" />
	<form:hidden path="sender" />
	<form:hidden path="recipient" />
	<form:hidden path="boxes" />

<p>
	<span style="font-weight: bold;"> <spring:message
			code="message.sender" var="senderHeader" /> <jstl:out
			value="${senderHeader }" />:
	</span>
	<jstl:out value="${message1.sender.name}" />
</p>


<p>
	<span style="font-weight: bold;"> <spring:message
			code="message.subject" var="subjectHeader" /> <jstl:out
			value="${subjectHeader }" />:
	</span>
	<jstl:out value="${message1.subject }" />
</p>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="message.body" var="bodyHeader" /> <jstl:out
			value="${bodyHeader }" />:
	</span>
	<jstl:out value="${message1.body }" />
</p>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="message.priority" var="priorityHeader" /> <jstl:out
			value="${priorityHeader }" />:
	</span>
	<jstl:out value="${message1.priority }" />
</p>


<p>
	<span style="font-weight: bold;"> <spring:message
			code="message.tags" var="tagsHeader" /> <jstl:out
			value="${tagsHeader }" />:
	</span>
	<jstl:out value="${message1.tags }" />
</p>


	
	

<input type="button" name="back" value="<spring:message code="message.back" />"
	onclick="javascript: relativeRedir('box/actor/list.do');" />
	
<input type="submit" name="delete"
            value="<spring:message code="message.delete" />"
            onclick="return confirm('<spring:message code="message.confirm.delete" />')" />


</form:form>	

