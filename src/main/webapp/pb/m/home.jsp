<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
  <title>Plopbox</title>
  <link type="text/css" rel="stylesheet" href="../stylesheets/bootstrap.min.css" />
</head>
<body>
  <div class="container">
    <div class="row">
      <div class="span12">
        <legend>Welcome to Plopbox!</legend>
      </div>
      <div class="span12">
        <blockquote>Your name is ${username}. And guess what! Your id # is ${user_id}.</blockquote>
        <blockquote>That's tight.</blockquote>
      </div>
      <div class="span12">
        <span>Check out </span><a href="/pb/test">this test page</a><span> to see if your cookie info persists!</span>
      </div>
    </div>
  </div>
  <script src="../js/jquery-1.8.3.min.js"></script>
  <script src="../js/bootstrap.min.js"></script>
</body>
</html>
