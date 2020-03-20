<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>

<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="messages" />

<html lang="${param.lang}">
<head>
    <title>crudApp - Main</title>
</head>
<body>

<ul>
    <li><a href="?lang=en"><fmt:message key="label.lang.en" /></a></li>
    <li><a href="?lang=ru"><fmt:message key="label.lang.ru" /></a></li>
</ul>

<h1><fmt:message key="header.message" /></h1>
<span><fmt:message key="description.message" /></span>

<h2><fmt:message key="tech_header.message" /></h2>
<span><fmt:message key="tech_desc.message" /></span>

<h2><fmt:message key="prg_struct_head.message" /></h2>

<h3><fmt:message key="model_header.message" /></h3>
<span><fmt:message key="model_desc.message" /></span>

<h3><fmt:message key="repo_header.message" /></h3>
<span><fmt:message key="repo_desc.message" /></span>

<h3><fmt:message key="service_header.message" /></h3>
<span><fmt:message key="service_desc.message" /></span>

<h3><fmt:message key="rest_header.message" /></h3>
<span><fmt:message key="rest_desc.message" /></span>

</body>
</html>