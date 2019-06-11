<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form action="actor/${authority}/edit.do"
	modelAttribute="${authority}">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	
	<form:hidden path="userAccount"/>
	<form:hidden path="userAccount.isNotBanned" />
	<form:hidden path="suspicious" />
	
	
	<security:authorize access="isAnonymous()">

		<form:label path="userAccount.username">
			<spring:message code="actor.username" />
		</form:label>
		<form:input path="userAccount.username" />
		<form:errors class="error" path="userAccount.username" />
		<br />

		<form:label path="userAccount.password">
			<spring:message code="actor.password" />
		</form:label>
		<form:input path="userAccount.password" type="password" value=""/>
		<form:errors class="error" path="userAccount.password" />
		<br />
	</security:authorize>
	
	<form:label path="name">
		<spring:message code="actor.name"></spring:message>
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name"></form:errors>
	<br />
	
	<form:label path="middleName">
		<spring:message code="actor.middleName"></spring:message>
	</form:label>
	<form:input path="middleName" />
	<form:errors cssClass="error" path="middleName"></form:errors>
	<br />
	
	<form:label path="surname">
		<spring:message code="actor.surname"></spring:message>
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname"></form:errors>
	<br />
	
	<form:label path="photo">
		<spring:message code="actor.photo"></spring:message>
	</form:label>
	<form:input path="photo" />
	<form:errors cssClass="error" path="photo"></form:errors>
	<br />
	
	<form:label path="email">
		<spring:message code="customer.email" />
	</form:label>
	<form:input path="email" pattern="^[\\w]+@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]+|(([\\w]\\s)*[\\w])+<\\w+@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z0-9]+>$"/>	
	<form:errors class="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="customer.phone" />
	</form:label>
	<form:input path="phone" id="phone" onblur="javascript: checkPhone();"/>
	<form:errors class="error" path="phone" />
	<br />	
	
	<form:label path="address">
		<spring:message code="actor.address"></spring:message>
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address"></form:errors>
	<br />
	
	<!--<jstl:if test="${handyWorker.id==0}">
		<form:label path="make">
			<spring:message code="actor.make"></spring:message>
		</form:label>
		<form:input path="make" />
		<form:errors cssClass="error" path="make"></form:errors>
		<br />
		
	</jstl:if>-->
	
	<security:authorize access="hasRole('HANDYWORKER')">
		<form:label path="make">
			<spring:message code="actor.make"></spring:message>
		</form:label>
		<form:input path="make" />
		<form:errors cssClass="error" path="make"></form:errors>
		<br />
	</security:authorize>
	
	
	<security:authorize access="hasRole('CUSTOMER')">
		<form:hidden path="complaints" />
		<form:hidden path="fixUpTasks" />
	</security:authorize>
	
	
	<input type="submit" name="save"
			value="<spring:message code="actor.save" />" />
	
	<jstl:if test="${customer.id!=0 && handyWorker.id!=0}">
		<input type="button" name="cancel"
			value="<spring:message code="actor.cancel" />"
			onclick="javascript: relativeRedir('actor/displayPrincipal.do');" />
	</jstl:if>
	<jstl:if test="${customer.id==0 || handyWorker.id==0}">
		<input type="button" name="cancel"
			value="<spring:message code="actor.cancel" />"
			onclick="javascript: relativeRedir('welcome/index.do');" />
	</jstl:if>
	
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
