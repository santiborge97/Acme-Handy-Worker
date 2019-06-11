<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${banner}" alt="Acme HandyWorker Co., Inc." width="600" height="300"/></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="configuration/administrator/edit.do"><spring:message code="master.page.configuration" /></a></li>
					<li><a href="box/actor/list.do"><spring:message code="master.page.messaging" /></a></li>
					<li><a href="category/administrator/list.do"><spring:message code="master.page.categories" /></a></li>
					<li><a href="warranty/administrator/list.do"><spring:message code="master.page.warranty" /></a></li>
					<li><a href="actor/administrator/listSuspiciousActors.do"><spring:message code="master.page.suspicious" /></a></li>
					<li><a href="dashboard/administrator/display.do"><spring:message code="master.page.dashboard" /></a></li>
					<li><a href="administrator/create.do"><spring:message code="master.page.signUpAdmin" /></a></li>
					<li><a href="administrator/createReferee.do"><spring:message code="master.page.signUpReferee" /></a></li>
					<li><a href="broadcast/administrator/create.do"><spring:message code="master.page.broadcast" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="box/actor/list.do"><spring:message code="master.page.messaging" /></a></li>
					<li><a href="fixUpTask/customer/list.do"><spring:message code="master.page.fixUpTask" /></a></li>		
					<li><a href="complaint/customer/list.do"><spring:message code="master.page.complaint" /></a></li>
					<li><a href="application/customer/list.do"><spring:message code="master.page.applications" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('HANDYWORKER')">
			<li><a class="fNiv"><spring:message	code="master.page.handyWorker" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="box/actor/list.do"><spring:message code="master.page.messaging" /></a></li>
					<li><a href="curriculum/handyWorker/aux.do"><spring:message code="master.page.curriculum" /></a></li>
					<li><a href="fixUpTask/handyWorker/list.do"><spring:message code="master.page.fixUpTask" /></a></li>
					<li><a href="finderFixUpTask/handyworker/find.do"><spring:message code="master.page.finder" /></a></li>			
					<li><a href="complaint/handyWorker/list.do"><spring:message code="master.page.complaint" /></a></li>	
					<li><a href="phase/handyWorker/list.do"><spring:message code="master.page.workplans" /></a></li>
					<li><a href="application/handyWorker/list.do"><spring:message code="master.page.applications" /></a></li>			
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('REFEREE')">
			<li><a class="fNiv"><spring:message	code="master.page.referee" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="box/actor/list.do"><spring:message code="master.page.messaging" /></a></li>
					<li><a href="report/referee/list.do"><spring:message code="master.page.report" /></a></li>	
					<li><a href="complaint/referee/list.do"><spring:message code="master.page.complaintsReferee" /></a></li>
					<li><a href="complaint/referee/unassigned.do"><spring:message code="master.page.complaintsUnassigned" /></a></li>			
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="register/createCustomer.do"><spring:message code="security.signup.customer" /></a></li>
			<li><a class="fNiv" href="register/createHandyWorker.do"><spring:message code="security.signup.handyWorker" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li><a href="actor/displayPrincipal.do"><spring:message code="master.page.profile" /></a></li>				
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>