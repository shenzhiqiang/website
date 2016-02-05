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
  <title>Home Page</title>
  <%--<meta name="description" content="THOMSOON - Portfolio with grid HTML5 and CSS3 modern simplicity style template. Check my portfolio. Download free now with file Photoshop!">--%>
  <%--<meta name="keywords" content="thomsoon, simplicity, theme, html5, download, free psd">--%>
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
      <%--<li><a class="ajax-link" href="${domain}">Home</a></li>--%>
      <%--<li><a class="ajax-link" href="${domain}/products">Projects</a></li>--%>
      <%--<li><a class="ajax-link" href="${domain}/about_us">About us</a></li>--%>
      <%--<li><a class="ajax-link" href="${domain}/contact">Contact</a></li>--%>
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

  <%--<header id="fullscreen">--%>
    <%----%>
      <%--<div class="logo" id="full"><a class="ajax-link" href="${domain}">HOME</a></div>--%>
      <%----%>
        <%--<div class="menu-index" id="button">--%>
        <%--<i class="fa fa-bars"></i>--%>
        <%--</div>--%>

  <%--</header>--%>

  <div class="clear"></div>

  <!--Content-->

  <div class="content" id="ajax-content">


        <div class="text-intro" id="site-type">

          <h1>${indexParam.title}</h1>
          <%--<h1 class="typewrite"><span>Portfolio</span></h1> <!--Change on script.js-->--%>
          <p>${indexParam.subTitle}</p>

        </div>


  <!--Portfolio grid-->

  <ul class="portfolio-grid" id="portfolio-sidebar">

    <c:forEach items="${indexParam.prod_list }" var="item">
    <li class="grid-item">
      <c:if test="${item.extra_info=='outer'}">
        <img src="${item.cover_image_url}">
      </c:if>
      <c:if test="${item.extra_info!='outer'}">
        <img src="${domain}${item.cover_image_url}">
      </c:if>
        <a class="ajax-link" href="${domain}/product/${item.id}">
          <div class="grid-hover">
            <h1>${item.prod_name}</h1>
            <p>${item.prod_introduction}</p>
          </div>
        </a>  
    </li>
    </c:forEach>

  </ul>

  </div>

  <!--Home Sidebar-->

<div id="ajax-sidebar">

  <div class="home-sidebar" style="background-image:url('${domain}/img/home-sidebar.jpg'); position:fixed;" id="hero">
    <%--<div class="home-sidebar" style="background-image:url('${domain}/image/104843149_d5eb40b16c_m.jpg')" id="hero">--%>

    <div class="parallax-option" data-jkit="[parallax:strength=0.8;axis=both]">

      <%--<div class="parallax parallax1"><img src="${domain}/img/business-card-2.png"></div>--%>
      <%--<div class="parallax parallax1"><img src="${domain}/image/home1.jpg"></div>--%>

    </div>

    <div class="parallax-option" data-jkit="[parallax:strength=0.3;axis=both]">

      <%--<div class="parallax parallax1"><img src="${domain}/img/business-card.png"></div>--%>
      <div class="parallax parallax1"><img src="${domain}/image/home1.jpg"></div>

    </div>

  </div>

</div>


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