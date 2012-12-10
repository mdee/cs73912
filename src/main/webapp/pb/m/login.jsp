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
        <form class="form-horizontal" action="/pb/login" method="post">
          <fieldset>
            <legend>Enter your Plopbox</legend>
          </fieldset>
          <div class="control-group">
            <label class="control-label" for="username">Username</label>
            <div class="controls">
              <input type="text" id="username" name="username" placeholder="Username">
            </div>
          </div>
          <div class="control-group">
            <label class="control-label" for="password">Password</label>
            <div class="controls">
              <input type="password" id="password" name="password" placeholder="Password">
            </div>
          </div>
          <div class="control-group">
            <div class="controls">
              <button type="submit" class="btn">Go!</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <script src="../js/jquery-1.8.3.min.js"></script>
  <script src="../js/bootstrap.min.js"></script>
</body>
</html>
