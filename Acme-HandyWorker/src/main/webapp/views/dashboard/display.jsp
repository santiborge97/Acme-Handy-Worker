<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h3><spring:message code="dashboard.fixUpTaskPerCustomer" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${statsOfFixUpTasksPerCustomer[0][0]}</li>
	<li><spring:message code="dashboard.minimum" />: ${statsOfFixUpTasksPerCustomer[0][1]}</li>
	<li><spring:message code="dashboard.maximum" />: ${statsOfFixUpTasksPerCustomer[0][2]}</li>
	<li><spring:message code="dashboard.deviation" />: ${statsOfFixUpTasksPerCustomer[0][3]}</li>
	</ul>
	
<h3><spring:message code="dashboard.applicationsPerFixUpTask" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${statsOfApplicationsPerFixUpTask[0][0]}</li>
	<li><spring:message code="dashboard.minimum" />: ${statsOfApplicationsPerFixUpTask[0][1]}</li>
	<li><spring:message code="dashboard.maximum" />: ${statsOfApplicationsPerFixUpTask[0][2]}</li>
	<li><spring:message code="dashboard.deviation" />: ${statsOfApplicationsPerFixUpTask[0][3]}</li>
	</ul>
	
<h3><spring:message code="dashboard.maximumPricePerFixUpTask" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${statsOfMaximumPricePerFixUpTask[0][0]}</li>
	<li><spring:message code="dashboard.minimum" />: ${statsOfMaximumPricePerFixUpTask[0][1]}</li>
	<li><spring:message code="dashboard.maximum" />: ${statsOfMaximumPricePerFixUpTask[0][2]}</li>
	<li><spring:message code="dashboard.deviation" />: ${statsOfMaximumPricePerFixUpTask[0][3]}</li>
	</ul>
	
<h3><spring:message code="dashboard.offeredPricePerApplication" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${statsOfOfferedPricePerApplication[0][0]}</li>
	<li><spring:message code="dashboard.minimum" />: ${statsOfOfferedPricePerApplication[0][1]}</li>
	<li><spring:message code="dashboard.maximum" />: ${statsOfOfferedPricePerApplication[0][2]}</li>
	<li><spring:message code="dashboard.deviation" />: ${statsOfOfferedPricePerApplication[0][3]}</li>
	</ul>
	
<h3><spring:message code="dashboard.ratioOfApplications" /></h3>

	<ul>
	<li><spring:message code="dashboard.pending" />: ${ratioOfApplicationsPending} </li>
	<li><spring:message code="dashboard.accepted" />: ${ratioOfApplicationsAccepted} </li>
	<li><spring:message code="dashboard.rejected" />: ${ratioOfApplicationsRejected} </li>
	<li><spring:message code="dashboard.pendingElapsed" />: ${ratioOfApplicationsPendingElapsedPeriod} </li>
	</ul>
	
<h3><spring:message code="dashboard.customersTenPerCentMore" /></h3>
<fieldset>

   <jstl:forEach items="${customers}" var="c">
    <jstl:out value="${c}" />
    <br />
   </jstl:forEach>

</fieldset>

<h3><spring:message code="dashboard.handyWorkersTenPerCentMore" /></h3>
<fieldset>

   <jstl:forEach items="${handyWorkers}" var="h">
    <jstl:out value="${h}" />
    <br />
   </jstl:forEach>

</fieldset>

<h3><spring:message code="dashboard.complaintsPerFixUpTask" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${statsOfComplaintsPerFixUpTask[0][0]}</li>
	<li><spring:message code="dashboard.minimum" />: ${statsOfComplaintsPerFixUpTask[0][1]}</li>
	<li><spring:message code="dashboard.maximum" />: ${statsOfComplaintsPerFixUpTask[0][2]}</li>
	<li><spring:message code="dashboard.deviation" />: ${statsOfComplaintsPerFixUpTask[0][3]}</li>
	</ul>
	
<h3><spring:message code="dashboard.notesPerReport" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${statsOfNotesPerReport[0][0]}</li>
	<li><spring:message code="dashboard.minimum" />: ${statsOfNotesPerReport[0][1]}</li>
	<li><spring:message code="dashboard.maximum" />: ${statsOfNotesPerReport[0][2]}</li>
	<li><spring:message code="dashboard.deviation" />: ${statsOfNotesPerReport[0][3]}</li>
	</ul>
	
<h3><spring:message code="dashboard.ratioOfFixUpTasksWithComplaint" /></h3>

	<ul>
	<li><spring:message code="dashboard.ratio" />: ${ratioOfFixUpTasksWithComplaint} </li>
	</ul>
	
<h3><spring:message code="dashboard.top3customers" /></h3>
<fieldset>

   <jstl:forEach items="${top3customers}" var="c">
    <jstl:out value="${c}" />
    <br />
   </jstl:forEach>

</fieldset>

<h3><spring:message code="dashboard.top3handyWorkers" /></h3>
<fieldset>

   <jstl:forEach items="${top3handyWorkers}" var="h">
    <jstl:out value="${h}" />
    <br />
   </jstl:forEach>

</fieldset>