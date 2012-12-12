<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
  <title>Plopbox</title>
  <link rel="stylesheet" href="../stylesheets/bootstrap.min.css" />
  <link rel="stylesheet" href="../stylesheets/bootstrap-editable.css">
  <!-- BEGIN BLUEIMP -->
  <link rel="stylesheet" href="../stylesheets/blueimp/jquery.fileupload-ui.css">
  <!-- END BLUEIMP -->
</head>
<body>
  <div class="container">
    <div class="row">
      <div class="span12">
        <legend>Welcome to Plopbox!</legend>
      </div>
    </div>
    <div class="row">
      <div class="span12">
        <h3>This is your home page. Soak it in.</h3>
      </div>
    </div>
    <div class="row">
      <div class="span12">
        <blockquote>Your name is ${username}. And guess what! Your id # is ${user_id}.<br>That's tight.</blockquote>
      </div>
    </div>
    <form id="fileupload" action="http://localhost:${port}/pb/upload?userId=${user_id}&fileId=${new_file_id}" method="POST" enctype="multipart/form-data">
      <!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
      <div class="row fileupload-buttonbar">
        <div class="span7">
          <!-- The fileinput-button span is used to style the file input field as button -->
          <span class="btn btn-success fileinput-button">
            <i class="icon-plus icon-white"></i>
            <span>Add file...</span>
            <input type="file" name="files[]" multiple>
          </span>
          <button type="submit" class="btn btn-primary start">
            <i class="icon-upload icon-white"></i>
            <span>Start upload</span>
          </button>
          <button type="reset" class="btn btn-warning cancel">
            <i class="icon-ban-circle icon-white"></i>
            <span>Cancel upload</span>
          </button>
          <button type="button" class="btn btn-danger delete">
            <i class="icon-trash icon-white"></i>
            <span>Delete</span>
          </button>
          <input type="checkbox" class="toggle">
        </div>
        <!-- The global progress information -->
        <div class="span5 fileupload-progress fade">
          <!-- The global progress bar -->
          <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
            <div class="bar" style="width:0%;"></div>
          </div>
          <!-- The extended global progress information -->
          <div class="progress-extended">&nbsp;</div>
        </div>
      </div>
      <!-- The loading indicator is shown during file processing -->
      <div class="fileupload-loading"></div>
      <br>
      <!-- The table listing the files available for upload/download -->
      <table role="presentation" class="table table-striped">
        <tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery">
        </tbody>
      </table>
    </form>
    <div class="row">
      <div class="span12">
        <span>Check out </span><a href="/pb/test">this test page</a><span> to see if your cookie info persists!</span>
      </div>
    </div>
  </div>
  <!-- The template to display files available for upload -->
  <script id="template-upload" type="text/x-tmpl">
  {% for (var i=0, file; file=o.files[i]; i++) { %}
      <tr class="template-upload fade">
          <td class="preview"><span class="fade"></span></td>
          <td class="name"><span>{%=file.name%}</span></td>
          <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
          {% if (file.error) { %}
              <td class="error" colspan="2"><span class="label label-important">Error</span> {%=file.error%}</td>
          {% } else if (o.files.valid && !i) { %}
              <td>
                  <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="bar" style="width:0%;"></div></div>
              </td>
              <td class="start">{% if (!o.options.autoUpload) { %}
                  <button class="btn btn-primary">
                      
                      <span>Start</span>
                  </button>
              {% } %}</td>
          {% } else { %}
              <td colspan="2"></td>
          {% } %}
          <td class="cancel">{% if (!i) { %}
              <button class="btn btn-warning">
                  
                  <span>Cancel</span>
              </button>
          {% } %}</td>
      </tr>
  {% } %}
  </script>
  <!-- The template to display files available for download -->
  <script id="template-download" type="text/x-tmpl">
  {% for (var i=0, file; file=o.files[i]; i++) { %}
      <tr class="template-download fade">
          {% if (file.error) { %}
              <td></td>
              <td class="name"><span>{%=file.name%}</span></td>
              <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
              <td class="error" colspan="2"><span class="label label-important">Error</span> {%=file.error%}</td>
          {% } else { %}
              <td class="preview">{% if (file.thumbnail_url) { %}
                  <a href="{%=file.url%}" title="{%=file.name%}" rel="gallery" download="{%=file.name%}"><img src="{%=file.thumbnail_url%}"></a>
              {% } %}</td>
              <td class="name">
                  <a href="{%=file.url%}" title="{%=file.name%}" rel="{%=file.thumbnail_url&&'gallery'%}" download="{%=file.name%}">{%=file.name%}</a>
              </td>
              <td class="size"><span>{%=o.formatFileSize(file.size)%}</span></td>
              <td colspan="2"></td>
          {% } %}
          <td class="delete">
              <button class="btn btn-danger" data-type="{%=file.delete_type%}" data-url="{%=file.delete_url%}">
                  
                  <span>Delete</span>
              </button>
              <input type="checkbox" name="delete" value="1">
          </td>
      </tr>
  {% } %}
  </script>
  <div class="container">
  <div class="row">
    <div class="span12">
      <h2>Your Plopbox files:</h2>
    </div>
  </div>
    <div class="row">
      <div class="span7">
        <table class="table table-bordered table-striped table-hover">
          <thead>
            <tr>
              <th>Filename</th>
              <th>Options</th>
            </tr>
          </thead>
          <c:forEach var="file" items="${user_files}">
            <tr>
              <td>
                <a href="#" id="file-name" data-type="text" data-pk="${file.id}" data-url="/pb/renameFile"><c:out value="${file.name}"/></a>
              </td>
              <td>
                <a style="margin-right:15px;" href="javascript:void(0);" title="Preview file" class="btn btn-primary preview-file" target="_blank" data-url="${file.location}">
                  <i class="icon-eye-open icon-white"></i>
                </a>
                <a style="margin-right:15px;" href="javascript:void(0);" title="Download file" class="btn btn-success download-file" target="_blank" data-url="${file.location}">
                  <i class="icon-download icon-white"></i>
                </a>
                <a style="margin-right:15px;" href="javascript:void(0);" title="Delete file" class="btn btn-danger delete-file" target="_blank" data-url="${file.location}">
                  <i class="icon-trash icon-white"></i>
                </a>
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
      <div class="span5">
        <h4>File preview</h4>
        <div id="preview-cont">
        </div>
      </div>
    </div>
  </div>
  <script src="../js/jquery-1.8.3.min.js"></script>
  <script src="../js/bootstrap.min.js"></script> 
  <script src="../js/bootstrap-editable-inline.min.js"></script>
  <!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
  <!-- The Templates plugin is included to render the upload/download listings -->
  <script src="http://blueimp.github.com/JavaScript-Templates/tmpl.min.js"></script>
  <!-- The Load Image plugin is included for the preview images and image resizing functionality -->
  <script src="http://blueimp.github.com/JavaScript-Load-Image/load-image.min.js"></script>
  <!-- The Canvas to Blob plugin is included for image resizing functionality -->
  <script src="http://blueimp.github.com/JavaScript-Canvas-to-Blob/canvas-to-blob.min.js"></script>
  <!-- Bootstrap JS and Bootstrap Image Gallery are not required, but included for the demo -->
  <script src="http://blueimp.github.com/Bootstrap-Image-Gallery/js/bootstrap-image-gallery.min.js"></script>
  <!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
  <script src="../js/blueimp/jquery.iframe-transport.js"></script>
  <script src="../js/blueimp/vendor/jquery.ui.widget.js"></script>
  <!-- The basic File Upload plugin -->
  <script src="../js/blueimp/jquery.fileupload.js"></script>
  <!-- The File Upload file processing plugin -->
  <script src="../js/blueimp/jquery.fileupload-fp.js"></script>
  <!-- The File Upload user interface plugin -->
  <script src="../js/blueimp/jquery.fileupload-ui.js"></script>
  <!-- The main application script -->
  <script src="../js/blueimp/main.js"></script>
  <script>
    $('document').ready(function(){

      $('a#file-name').editable();

      $('a.preview-file').on('click', function(){
        var self = this;
        var imageUrl = $(self).attr('data-url');
        $('div#preview-cont').fadeOut(function(){
          $('div#preview-cont').html($('<img/>').attr('src', imageUrl));  
          $('div#preview-cont').fadeIn();
        });
      });

    });
  </script
</body>
</html>
