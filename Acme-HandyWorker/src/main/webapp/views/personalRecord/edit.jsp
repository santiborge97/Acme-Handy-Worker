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

<form:form action="personalRecord/handyWorker/edit.do"
	modelAttribute="personalRecord" method="post">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<input type="text" hidden="true" value="${curriculumId }"
		name="curriculumId" />


	<form:label path="fullName">
		<spring:message code="personalRecord.fullName"></spring:message>
	</form:label>
	<form:input path="fullName" />
	<form:errors cssClass="error" path="fullName"></form:errors>
	<br />

	<form:label path="email">
		<spring:message code="personalRecord.email"></spring:message>
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email"></form:errors>
	<br />

	<form:label path="photo">
		<spring:message code="personalRecord.photo"></spring:message>
	</form:label>
	<form:input path="photo" />
	<form:errors cssClass="error" path="photo"></form:errors>
	<br />

	<form:label path="phone">
		<spring:message code="customer.phone" />
	</form:label>
	<form:input path="phone" id="phone" onblur="javascript: checkPhone();"/>	
	<form:errors class="error" path="phone" />
	<br />	

	<form:label path="linkedInProfile">
		<spring:message code="personalRecord.linkedInProfile"></spring:message>
	</form:label>
	<form:input path="linkedInProfile" />
	<form:errors cssClass="error" path="linkedInProfile"></form:errors>
	<br />

	<jstl:choose>
		<jstl:when test="${personalRecord.id==0}">
			<input type="submit" name="save1"
				value="<spring:message code="personalRecord.save1"/>" />
		</jstl:when>

		<jstl:otherwise>
			<input type="submit" name="save2"
				value="<spring:message code="personalRecord.save2"/>" />
		</jstl:otherwise>
	</jstl:choose>


	<input type="button" name="cancel" value="<spring:message code="personalRecord.cancel" />" 
	onclick="javascript: relativeRedir('welcome/index.do');" />


</form:form>

<script type="text/javascript">
	function checkPhone() {
		var target = document.getElementById("phone");
		var input = target.value;
		var regExp1 = new RegExp("(^[+]([1-9]{1,3})) ([(][1-9]{1,3}[)]) (\\d{4,}$)");
		var regExp2 = new RegExp("(^[+]([1-9]{1,3})) (\\d{4,}$)");
		var regExp3 = new RegExp("(^\\d{4,}$)");

		if ('${phone}' != input && regExp1.test(input) == false && regExp2.test(input) == false && regExp3.test(input) == false) {
			if (confirm('<spring:message code="actor.phone.wrong" />') == false) {
				return true;
			
			}
		} else if ('${phone}' != input && regExp1.test(input) == false && regExp2.test(input) == false && regExp3.test(input) == true) {
			target.value = '${defaultCountry}' + " " + input;
		}
	}
</script>