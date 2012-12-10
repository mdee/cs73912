<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>IMAGE VIEW!!</title>
</head>
<body>

    
    <c:if test="${fn:length(images) > 0}">
      <b>THERE'Z IMAGES</b>
      <c:forEach var="image" items="${images}">
        <c:set var="anchorId" value="${image.id}"/>
        <a href="/pb/file?id=<c:out value="${image.id}"/>"><c:out value="${image.name}"/></a>
      </c:forEach>
    </c:if>
    
    <c:if test="${fn:length(images) == 0}">
      <b>No images</b>
    </c:if>
  </p>
</body>
</html>
