<%@page import="com.web.core.common.*"%>
<%@ page import="com.web.core.common.ProductsParam" %>
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
  <title>Products</title>
  <%--<meta name="description" content="THOMSOON - Portfolio Masonry boxed layout. Clean and minimalist portfolio and photography.">--%>
  <%--<meta name="keywords" content="thomsoon, simplicity, theme, html5, download, masonry, showroom, creative ">--%>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <%--<meta name="author" content="thomsoon.com">--%>
  <link rel="icon" type="image/png" href="${domain}/img/icon.png" />

  <!--Style-->

  <link rel="stylesheet" href="${domain}/css/reset.css">
  <link rel="stylesheet" href="${domain}/css/style.css">
  <link rel="stylesheet" href="${domain}/css/style-responsive.css">
    <link href="${domain}/css/bootstrap.min.css" rel="stylesheet" media="screen">

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

<div class="navbar navbar-inverse navbar-fixed-top">
  <!-- 定义个内部框架表现的基调,位置大小背景等 -->
  <div class="navbar-inner">
    <!-- 定义实现块的css,具体内容都包含在container中 -->
    <div class="container">
      <!-- 定义响应时用的按钮元素,界面大小改变时会用到 -->
      <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      <!-- 定义品牌链接导航 -->
      <!-- 不知道为什么这么定义 -->
      <a class="brand" href="${domain}">Home</a>
      <!-- 定义了个响应触发点 -->
      <div class="nav-collapse collapse">
        <!-- 定义导航列表 -->
        <ul class="nav">
          <!-- 首页链接高亮 -->
          <li class=""> <a href="${domain}/products">Projects</a> </li>
          <li class=""> <a href="${domain}/about_us">About me</a> </li>

          <c:if test="${username==null}">
            <li><a href="${domain}/login">Login</a></li>
          </c:if>
          <c:if test="${username!=null}">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">${username} <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li class="divider"></li>
                <li class="nav-header">Admin</li>
                <li><a href="${domain}/admin/passwd">Passwd</a></li>
                <li><a href="${domain}/admin/products">Projects</a></li>
                <li><a href="${domain}/admin/add">Add One</a></li>
                <li><a href="${domain}/logout">Logout</a></li>

              </ul>
            </li>
          </c:if>
        </ul>
      </div>

      <form class="navbar-search pull-left" action="${domain}/search">
        <input type="text" class="span2 search-query" name="searchinfo" id="searchinfo_nav" data-jkit="[validate:required=true;min=1;error=Please write your search info]">
      </form>

    </div>
  </div>
</div>

  <!--Header-->
<header >

  <%--<div class="header-margin">--%>

    <%--<div class="logo"><a class="ajax-link" href="${domain}">HOME</a></div>--%>

    <%--<ul class="header-nav">--%>
      <%--<li><a href="${domain}/products">Products</a>--%>
        <%--&lt;%&ndash;<ul>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<li><a class="ajax-link" href="${domain}/projects">Projects</a></li>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<li><a class="ajax-link" href="single.html">Single project</a></li>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<li><a class="ajax-link" href="single-full.html">Single project fullscreen</a></li>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</ul>&ndash;%&gt;--%>

      <%--</li>--%>

      <%--<li><a href="${domain}/about_us">About me</a></li>--%>
      <%--<c:if test="${username==null}">--%>
        <%--<li><a href="${domain}/login">Login</a></li>--%>
      <%--</c:if>--%>
      <%--<c:if test="${username!=null}">--%>
        <%--<li><a href="${domain}">${username}</a></li>--%>
        <%--<ul>--%>
          <%--<li><a href="${domain}/logout">Logout</a></li>--%>
        <%--</ul>--%>
      <%--</c:if>--%>
    <%--</ul>--%>

  <%--</div>--%>

</header>

  <div class="clear"></div>

  <!--Content-->

  <div class="content" id="ajax-content">


        <div class="text-intro">

          <h1>${productsParam.title}</h1>
          <%--<p>${productsParam.subTitle}</p>--%>
          <%--<form method="get" action="${domain}/search" class="form-search">--%>
            <%--<div class="input-append">--%>
              <%--<p>--%>
                <%--<input class="span2 search-query" name="searchinfo" id="searchinfo" data-jkit="[validate:required=true;min=1;error=Please write your search info]">--%>
                <%--<input class="btn" type="submit" value="SEARCH" />--%>
              <%--</p>--%>
            <%--</div>--%>
          <%--</form>--%>

        </div>


  <!--Portfolio grid-->

  <ul class="portfolio-grid">

    <c:forEach items="${productsParam.prod_list}" var="item">
      <li class="grid-item" data-jkit="[show:delay=500;speed=500;animation=fade]">
        <c:if test="${item.extra_info!='outer'}">
          <img class="img-item-fix" src="${domain}${item.cover_image_url}">
        </c:if>
        <c:if test="${item.extra_info=='outer'}">
          <img class="img-item-fix" src="${item.cover_image_url}">
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

    <div class="text-intro">
      <div class="pagination">
        <ul>
          <%

            ProductsParam pageParam = (ProductsParam)request.getAttribute("productsParam");
            int currPage = pageParam.getCurrPage();
            int totalPage = pageParam.getTotalPage();
            for(int i = 1; i <= totalPage; i ++){
              if(i == currPage){
          %><li class="disabled"><a><%=currPage %>  </a></li><%
        }else{
        %><li><a href="${domain}/search/<%=i %>?searchinfo=<%=pageParam.getSubTitle() %>"><%=i %>  </a></li><%
            }
          }
        %>
        </ul>
      </div>
    </div>
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
<script src="${domain}/js/bootstrap.min.js"></script>

<script>
    $('#button, #buttons').on('click', function() {
      $( ".opacity-nav" ).fadeToggle( "slow", "linear" );
    // Animation complete.
    });
  </script>


</body>
</html>