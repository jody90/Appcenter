<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>




<div class="content">
    <h1>Dashboard</h1>
    
    <p>
    	Persönlicher Bereich im Sortimo App Center
    <p>
    
    <p>
    	Beispielsweise Notizen, Statistiken, Server Monitoring,  etc.
   	</p>
		
		<c:if test='${helper.isAuthorized("new_form")}'>
			<h1>
				Darf Formular anlegen
			</h1>
		</c:if>
    	
</div>