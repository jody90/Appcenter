<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="content text-center flexbox">
    <a class="flex-valign navi-item-box" href="${pageContext.request.contextPath}/formularmanager/edit">
        <span class="navi-item-text">
            <span class="glyphicon glyphicon-plus navi-item-icon" aria-hidden="true"></span><br>
            Neu
        </span>
    </a>
    <a class="navi-item-box" href="${pageContext.request.contextPath}/formularmanager/list?country=ALL&filter=false">
        <span class="navi-item-text">
            <span class="glyphicon glyphicon-list navi-item-icon" aria-hidden="true"></span><br>
            Übersicht
        </span>
    </a>
    
    <a class="navi-item-box" href="${pageContext.request.contextPath}/formularmanager/list?country=ALL&filter=active">
        <span class="navi-item-text">
            <span class="glyphicon glyphicon-equalizer navi-item-icon" aria-hidden="true"></span><br>
            Aktiv
        </span>
    </a>
</div>

<jsp:include page="/views/${path}/_scripts_styles.jsp"></jsp:include>
