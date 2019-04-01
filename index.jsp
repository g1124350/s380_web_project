<!DOCTYPE html>
<html>
    <head>
        <title>Home Page</title>
    </head>
    <body>
        <h2>Course Name: CompS380F</h2>
        <c:choose>
            <c:when test="${fn:length(lectureDatabase) == 0}">
                <i>There are no lecture in the system.</i>
            </c:when>
            <c:otherwise>
               <%-- <c:forEach items="${lectureDatabase}" var="entry">
                    Lecture ${entry.key}:
                    <a href="<c:url value="/lecture/view/${entry.key}" />">
                        <c:out value="${entry.value.subject}" /></a>
                    (customer: <c:out value="${entry.value.customerName}" />)<br />
                </c:forEach>--%>
               <i>There have lecture</i>
            </c:otherwise>
        </c:choose>
    </body>
</html>
