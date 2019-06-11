<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table name="applications" id="row" requestURI="${requestURI }" pagesize="5" class="displaytag">

	<display:column property="moment" titleKey="application.moment"/>
	<display:column property="status" titleKey="application.status"/>
	<display:column titleKey="application.offeredPrice">
		<jstl:out value="${row.offeredPrice.amount }"/>
		<jstl:out value="${row.offeredPrice.currency }"/>
	</display:column>
	<display:column property="comment" titleKey="application.comment"/>
	<display:column property="commentReject" titleKey="application.commentReject"/>
	<display:column property="fixUpTask.ticker" titleKey="application.fixUpTask"/>
	
	<display:column>
		<a href="application/${autoridad}/display.do?applicationId=${row.id}"><spring:message code = "application.display"></spring:message></a>
	</display:column>
	
	<security:authorize access="hasRole('CUSTOMER')">
	
	<display:column property="creditCard.number" titleKey="application.creditCard"/>
	
	<display:column titleKey="application.options">
		<jstl:if test="${row.status == 'PENDING'}">	
				<input type="button" name="accept"
					value="<spring:message code = "application.accept" />"
					onclick="javascript: window.location.replace('application/customer/accept.do?applicationId=${row.id}')" />
					
				<input type="button" name="reject"
					value="<spring:message code = "application.reject" />"
					onclick="javascript: window.location.replace('application/customer/reject.do?applicationId=${row.id}')" />
		</jstl:if>
	</display:column>
	</security:authorize>
	

</display:table>

<input type="button" name="back"
	value="<spring:message code="application.back" />"
	onclick="javascript: relativeRedir('welcome/index.do');" />
<br />
<br />
<script type="text/javascript">
	var trTags = document.getElementsByTagName("tr");
	for (var i = 0; i < trTags.length; i++) {
	  var tdStatus = trTags[i].children[1];
	  if (tdStatus.innerText == "REJECTED") {
		  trTags[i].style.backgroundColor = "orange";
	  } else if (tdStatus.innerText == "ACCEPTED") {
		  trTags[i].style.backgroundColor = "green";
	  } else if (tdStatus.innerText == "PENDING" && ('${daysFixUpTask - daysNow > 0}')) {
		  trTags[i].style.backgroundColor = "grey";
	  } else{
		  trTags[i].style.backgroundColor = "white";
	  }
	}
</script>