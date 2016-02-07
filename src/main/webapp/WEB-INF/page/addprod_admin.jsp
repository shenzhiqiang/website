<%@page import="com.web.core.common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
  request.setAttribute("domain", basePath);
%>
<!doctype html>

<html lang="en">
<head>

  <%--<meta charset="utf-8">--%>
  <title>Add</title>
  <%--<meta name="description" content="Contact page with contact form and simplicty information with e-mail and address. Contact form with check input and textarea. ">--%>
  <%--<meta name="keywords" content="thomsoon, simplicity, theme, html5, contact, form">--%>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <%--<meta name="author" content="thomsoon.com">--%>
    <link rel="icon" type="image/png" href="${domain}/img/icon.png" />

    <!--Style-->

    <link rel="stylesheet" href="${domain}/css/reset.css">
    <link rel="stylesheet" href="${domain}/css/style.css">
    <link rel="stylesheet" href="${domain}/css/style-responsive.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
  
  <!--[if lt IE 9]>
  <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
  <![endif]-->
  
</head>

<body>

<!--Preloader-->

<%--<div class="preloader" id="preloader">--%>
    <%--<div class="item">--%>
      <%--<div class="spinner">--%>
      <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>

<%--<div class="opacity-nav">--%>

        <%--<div class="menu-index" id="buttons" style="z-index:999999">--%>
        <%--<i class="fa  fa-close"></i>--%>
        <%--</div>--%>

    <%--<ul class="menu-fullscreen">--%>
      <%--<li><a class="ajax-link" href="index.html">Home</a></li>--%>
      <%--<li><a class="ajax-link" href="projects.html">Projects</a></li>--%>
      <%--<li><a class="ajax-link" href="about-us.html">About us</a></li>--%>
      <%--<li><a class="ajax-link" href="contact.html">Contact</a></li>--%>
    <%--</ul>--%>

<%--</div>--%>


  <!--Header-->
<header class="boxed" id="header-white">

  <div class="header-margin">

    <div class="logo"><a class="ajax-link" href="${domain}">HOME</a></div>

    <ul class="header-nav">
      <li><a href="${domain}/products">Products</a>
        <%--<ul>--%>
        <%--<li><a class="ajax-link" href="${domain}/projects">Projects</a></li>--%>
        <%--<li><a class="ajax-link" href="single.html">Single project</a></li>--%>
        <%--<li><a class="ajax-link" href="single-full.html">Single project fullscreen</a></li>--%>
        <%--</ul>--%>

      </li>

      <li><a href="${domain}/about_us">About me</a></li>
      <c:if test="${username==null}">
        <li><a href="${domain}/login">Login</a></li>
      </c:if>
      <c:if test="${username!=null}">
        <li><a href="${domain}">${username}</a></li>
        <ul>
          <li><a href="${domain}/logout">Logout</a></li>
        </ul>
      </c:if>
    </ul>

  </div>

</header>

  <div class="clear"></div>

  <!--Content-->

  <div class="content"  id="ajax-content">

        <div class="text-intro">

          <h1>ADD</h1>
          <c:if test="${error!=null}">
            <p>${error}</p>
          </c:if>

          <%--<form method="post" action="${domin}/login" data-jkit="[form:validateonly=true]">--%>
          <%----%>
          <%--<div class="contact-one">--%>
            <%--<p>--%>
              <%--<label for="username">Username:</label><br />--%>
              <%--<input name="username" id="username" data-jkit="[validate:required=true;min=3;max=10;error=Please enter your username (3-10 characters)]">--%>
            <%--</p>--%>
          <%--</div>--%>

          <%--<div class="contact-two">--%>
            <%--<p>--%>
              <%--<label for="passwd">Password:</label><br />--%>
              <%--<input type="password" name="passwd" id="passwd" data-jkit="[validate:required=true;error=Please write your password]">--%>
              <%--<input class="button-submit" type="submit" value="SUBMIT" />--%>
            <%--</p>--%>
          <%--</div>  --%>
          <%----%>

          <%--</form>--%>
          <div class="contact-one">
          <form action="${domain}/admin/add" method="post" enctype="multipart/form-data">
            <label>Prod name: </label><input type="text" name="prod_name"/><br/>
            <label>Prod introduction: </label><input type="text" name="prod_introduction"/><br/>
            <label>img01: </label><input type="file" name="imgs"/><br/>
            <label>img02: </label><input type="file" name="imgs"/><br/>
            <label>img03: </label><input type="file" name="imgs"/><br/>
            <input type="submit" value="ADD"/>
          </form>
          </div>

        </div>
        
	<br/><br/><br/><br/><br/><br/><br/><br/>      
  </div>




  <!--Home Sidebar-->

<div id="ajax-sidebar"></div>



  <!--Footer-->
  <footer>

    <div class="footer-margin">
      <%--<div class="social-footer">--%>
        <%--<a href="https://www.facebook.com/thomsooncom"><i class="fa fa-facebook"></i></a>--%>
        <%--<a href="https://www.behance.net/TomaszMazurczak"><i class="fa fa-behance"></i></a>--%>
      <%----%>
      <%--</div>     --%>
      <%--<div class="copyright">© Copyright 2015 Thomsoon.com. All Rights Reserved.</div>--%>
      

    
    </div>


  </footer>


<!--Scripts-->

  <script src="${domain}/js/jquery.min.js"></script>
  <script src="${domain}/js/jquery.easing.min.js"></script>
  <script src="${domain}/js/modernizr.custom.42534.js" type="text/javascript"></script>
  <script src="${domain}/js/jquery.waitforimages.js" type="text/javascript"></script>
  <script src="${domain}/js/typed.js" type="text/javascript"></script>
  <script src="${domain}/js/masonry.pkgd.min.js" type="text/javascript"></script>
  <script src="${domain}/js/imagesloaded.pkgd.min.js" type="text/javascript"></script>
  <script src="${domain}/js/jquery.jkit.1.2.16.min.js"></script>

  <script src="${domain}/js/script.js" type="text/javascript"></script>

  <script>
    $('#button, #buttons').on('click', function() {
      $( ".opacity-nav" ).fadeToggle( "slow", "linear" );
    // Animation complete.
    });
  </script>


</body>
</html>