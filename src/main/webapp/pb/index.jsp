<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>Welcome</title>
</head>
<body>
  <p>
  Welcome to Google App Engine for Java! Holy jeez
  </p>
  <p>
  <ul>
    <c:forEach var="message" items="${messages}">
      <li>
        <div><c:out value="${message.text}"/></div>
        <div><a href="index?id=<c:out value="${message.id}"/>">Delete</a></div>
      </li>
    </c:forEach>
  </ul>
  </p>
  <p>
    <form action="index" method="post">
      <input type="text" name="text">
      <input type="submit" value="Create">
    </form>
  </p>
  <h3>Upload form!!$</h3>
  <p>
    <form action="upload?userID=1&fileID=5" method="post" enctype="multipart/form-data">
      <input type="file" name="file">
      <input type="submit" value="UPLOAD">
    </form>
    
    <c:if test="${fn:length(images) > 0}">
      <b>THERE'S IMAGESZ DUDE</b>
      <c:forEach var="image" items="${images}">
        <c:set var="anchorId" value="${image.id}"/>
        <a href="/pb/image?id=<c:out value="${image.id}"/>"><c:out value="${image.name}"/></a>
        <br>
      </c:forEach>
    </c:if>
    
    <c:if test="${fn:length(images) == 0}">
      <b>No images</b>
    </c:if>
  </p>
</body>
</html>
