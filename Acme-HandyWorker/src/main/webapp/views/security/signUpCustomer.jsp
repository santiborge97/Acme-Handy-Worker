<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="register/editCustomer.do" modelAttribute="customer">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="userAccount.authorities" />
	<form:hidden path="userAccount.isNotBanned" />
	
	<form:hidden path="fixUpTasks" />
	<form:hidden path="complaints" />
	
	<form:label path="name">
		<spring:message code="customer.name" />
	</form:label>
	<form:input path="name" />	
	<form:errors class="error" path="name" />
	<br />
	
	<form:label path="middleName">
		<spring:message code="customer.middleName" />
	</form:label>
	<form:input path="middleName" />	
	<form:errors class="error" path="middleName" />
	<br />
	
	<form:label path="surname">
		<spring:message code="customer.surname" />
	</form:label>
	<form:input path="surname" />	
	<form:errors class="error" path="surname" />
	<br />	

	<form:label path="photo">
		<spring:message code="customer.photo" />
	</form:label>
	<form:input path="photo" />	
	<form:errors class="error" path="photo" />
	<br />
	
	<form:label path="email">
		<spring:message code="customer.email" />
	</form:label>
	<form:input path="email" pattern="^[\\w]+@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+|(([\\w]\\s)*[\\w])+<\\w+@(?:[a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+>$"/>	
	<form:errors class="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="customer.phone" />
	</form:label>
	<form:input path="phone" id="phone" onblur="javascript: checkPhone();"/>
	<form:errors class="error" path="phone" />
	<br />	
	
	<form:label path="address">
		<spring:message code="customer.address" />
	</form:label>
	<form:input path="address" />	
	<form:errors class="error" path="address" />
	<br />	
	
	<form:hidden path="suspicious"/>	
	<form:errors class="error" path="suspicious" />
	
	<form:label path="userAccount.username">
		<spring:message code="customer.username" />
	</form:label>
	<form:input path="userAccount.username" />	
	<form:errors class="error" path="userAccount.username" />
	<br />		
	
	<form:label path="userAccount.password">
		<spring:message code="customer.password" />
	</form:label>
	<form:password path="userAccount.password" />	
	<form:errors class="error" path="userAccount.password" />
	<br />		
	
	<jstl:if test="${showError == true}">
		<div class="error">
			<spring:message code="customer.signUp.failed" />
		</div>
	</jstl:if>
	
	<input type="submit" name="save" value="<spring:message code="customer.save" />" />
	
	<input type="button" name="cancel" value="<spring:message code="customer.cancel" />" 
	onclick="javascript: relativeRedir('redirect:welcome/index.do');" />
	
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
