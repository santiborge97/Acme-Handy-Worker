<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="curriculum.ticker" var="tickerTitle" />
<jstl:out value="${tickerTitle }: " />
<jstl:out value="${curriculum.ticker }" />
<br>

<fieldset>
<spring:message code="curriculum.personalRecord"
	var="personalRecordTitle" />
<h3>
	<jstl:out value="${personalRecordTitle }: " />
</h3>
<div>
	<img
		src="${personalRecord.photo }"
		alt="photo" height="200" width="200">

	<spring:message code="personalRecord.fullName" var="fullName" />
	<p>
		<jstl:out value="${fullName}: ${curriculum.personalRecord.fullName}"></jstl:out>
	</p>

	<spring:message code="personalRecord.email" var="email" />
	<p>
		<jstl:out value="${email}: ${curriculum.personalRecord.email}"></jstl:out>
	</p>

	<spring:message code="personalRecord.phone" var="phone" />
	<p>
		<jstl:out value="${phone}: ${curriculum.personalRecord.phone}"></jstl:out>
	</p>

	<spring:message code="personalRecord.linkedInProfile" var="linkedInProfile" />
	<jstl:out value="${linkedInProfile}: "></jstl:out>
	<a href="${curriculum.personalRecord.linkedInProfile}">${curriculum.personalRecord.linkedInProfile}</a>

</div>
</fieldset>

<fieldset>
<spring:message code="curriculum.educationRecord"
	var="educationRecordTitle" />
<h3>
	<jstl:out value="${educationRecordTitle }: " />
</h3>
<div>
	<display:table name="curriculum.educationRecords" id="row"
		requestURI="${requestURI }" pagesize="3" class="displaytag">

		<spring:message code="educationRecord.title"
			var="title" />
		<display:column property="title" title="${title}"
			sortable="false" />

		<spring:message code="educationRecord.institution" var="institution" />
		<display:column property="institution" title="${institution}"
			sortable="false" />

		<spring:message code="educationRecord.attachment" var="attachment" />
		<display:column title="${attachment}">
			<a href="${row.attachment}">${row.attachment}</a>
		</display:column>

		<spring:message code="educationRecord.comments" var="comments" />
		<display:column property="comments" title="${comments}"
			sortable="false" />

		<spring:message code="educationRecord.period"
			var="period" />
		<display:column title="${period}">
			<jstl:out value="${row.period}"></jstl:out>
		</display:column>
		
		<display:column>
			<a href="educationRecord/handyWorker/edit.do?educationRecordId=${row.id}">
			<spring:message code = "educationRecord.edit"></spring:message>
			</a>
		</display:column>

	</display:table>
	
	<a href="educationRecord/handyWorker/create.do">
		<spring:message	code="educationRecord.create" />
	</a>

</div>
</fieldset>


<fieldset>
<spring:message code="curriculum.professionalRecord"
	var="professionalRecordTitle" />
<h3>
	<jstl:out value="${professionalRecordTitle }: " />
</h3>
<div>
	<display:table name="curriculum.professionalRecords" id="row"
		requestURI="${requestURI }" pagesize="3" class="displaytag">

		<spring:message code="professionalRecord.companyName"
			var="companyName" />
		<display:column property="companyName" title="${companyName}"
			sortable="false" />

		<spring:message code="professionalRecord.role" var="role" />
		<display:column property="role" title="${role}" sortable="false" />

		<spring:message code="professionalRecord.attachment" var="attachment" />
		<display:column title="${attachment}">
			<a href="${row.attachment}">${row.attachment}</a>
		</display:column>

		<spring:message code="professionalRecord.comments" var="comments" />
		<display:column property="comments" title="${comments}"
			sortable="comments" />

		<spring:message code="professionalRecord.period"
			var="period" />
		<display:column title="${period}">
			<jstl:out value="${row.period}"></jstl:out>
		</display:column>	
		
		<display:column>
			<a href="professionalRecord/handyWorker/edit.do?professionalRecordId=${row.id}">
			<spring:message code = "professionalRecord.edit"></spring:message>
			</a>
		</display:column>
		
	</display:table>
	
	<a href="professionalRecord/handyWorker/create.do">
		<spring:message	code="professionalRecord.create" />
	</a>	
</div>
</fieldset>


<fieldset>
<spring:message code="curriculum.endorserRecord"
	var="endorserRecordTitle" />
<h3>
	<jstl:out value="${endorserRecordTitle }: " />
</h3>
<div>
	<display:table name="curriculum.endorserRecords" id="row"
		requestURI="${requestURI }" pagesize="3" class="displaytag">

		<spring:message code="endorserRecord.fullName" var="fullName" />
		<display:column property="fullName" title="${fullName}" sortable="false" />

		<spring:message code="endorserRecord.email" var="email" />
		<display:column property="email" title="${email}" sortable="false" />

		<spring:message code="endorserRecord.phone" var="phone" />
		<display:column property="phone" title="${phone}" sortable="false" />

		<spring:message code="endorserRecord.linkedInProfile" var="linkedInProfile" />
		<display:column title="${linkedInProfile}">
			<a href="${row.linkedInProfile}">${row.linkedInProfile}</a>
		</display:column>

		<spring:message code="endorserRecord.comments" var="comments" />
		<display:column property="comments" title="${comments}"
			sortable="false" />
			
			<display:column>
			<a href="endorserRecord/handyWorker/edit.do?endorserRecordId=${row.id}">
			<spring:message code = "endorserRecord.edit"></spring:message>
			</a>
		</display:column>
			
	</display:table>
	
	<a href="endorserRecord/handyWorker/create.do">
		<spring:message	code="endorserRecord.create" />
	</a>
	
</div>
</fieldset>


<fieldset>
<spring:message code="curriculum.miscellaneousRecord"
	var="miscellaneousRecordTitle" />
<h3>
	<jstl:out value="${miscellaneousRecordTitle }: " />
</h3>
<div>
	<display:table name="curriculum.miscellaneousRecords" id="row"
		requestURI="${requestURI }" pagesize="3" class="displaytag">

		<spring:message code="miscellaneousRecord.title" var="title" />
		<display:column property="title" title="${title}" sortable="false" />

		<spring:message code="miscellaneousRecord.attachment" var="attachment" />
		<display:column title="${attachment}">
			<a href="${row.attachment}">${row.attachment}</a>
		</display:column>

		<spring:message code="miscellaneousRecord.comments" var="comments" />
		<display:column property="comments" title="${comments}"
			sortable="false" />
			
			<display:column>
			<a href="miscellaneousRecord/handyWorker/edit.do?miscellaneousRecordId=${row.id}">
			<spring:message code = "miscellaneousRecord.edit"></spring:message>
			</a>
		</display:column>
			
	</display:table>
	
	<a href="miscellaneousRecord/handyWorker/create.do">
		<spring:message	code="miscellaneousRecord.create" />
	</a>
	
</div>
</fieldset>