<#-- @ftlroot "./" -->
<!DOCTYPE html>
<html lang="en" class="no-js">

<head>
  <#include "/header.ftl">

  <meta charset="utf-8">
  <title>登录</title>

  <script type="text/javascript">
    if (self != top) {
      top.location.href = self.location.href;
    }
  </script>


  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <!-- CSS -->
  <link rel="stylesheet" href="${request.contextPath}/statics/css/reset.css">
  <link rel="stylesheet" href="${request.contextPath}/statics/css/supersized.css">
  <link rel="stylesheet" href="${request.contextPath}/statics/css/style.css">

  <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
  <![endif]-->

</head>

<body oncontextmenu="return false">

<div class="page-container">
  <h1>Login</h1>
  <form action="" method="post">
    <div>
      <input type="text" name="loginName" class="username" placeholder="用户名" autocomplete="off"/>
    </div>
    <div>
      <input type="password" name="password" class="password" placeholder="密码"  oncontextmenu="return false"
             onpaste="return false"/>
    </div>
    <button id="submit" type="button">登录</button>
  </form>
  <div class="connect">
    <p>If we can only encounter each other rather than stay with each other,then I wish we had never encountered.</p>
    <p style="margin-top:20px;">如果只是遇见，不能停留，不如不遇见。</p>
  </div>
</div>
<div class="alert" style="display:none">
  <h2>消息</h2>
  <div class="alert_con">
    <p id="ts"></p>
    <p style="line-height:70px"><a class="btn">确定</a></p>
  </div>
</div>

<!-- Javascript -->
<script src="http://apps.bdimg.com/libs/jquery/1.6.4/jquery.min.js" type="text/javascript"></script>
<script src="${request.contextPath}/statics/js/supersized.3.2.7.min.js"></script>
<script src="${request.contextPath}/statics/js/supersized-init.js"></script>
<script src="${request.contextPath}/statics/libs/jquery.md5.js"></script>
<script>
  $(".btn").click(function () {
    is_hide();
  })

  $('input[name=password]').bind('keyup', function(event) {
    if (event.keyCode == "13") {
      //回车执行查询
      login();
    }
  });


  $("#submit").live('click', function () {
    login();
  });

  function login(){
    var u = $("input[name=loginName]");
    var p = $("input[name=password]");

    var data = {
      loginName: u.val(),
      password: p.val(),
    }

    if (data.loginName == '' || data.password == '') {
      $("#ts").html("用户名或密码不能为空~");
      is_show();
      return false;
    } else {
      var reg = /^[0-9A-Za-z]+$/;
      if (!reg.exec(data.loginName)) {
        $("#ts").html("用户名错误");
        is_show();
        return false;
      }
    }

    data.password = $.md5(data.password);

    // 登录
    $.ajax({
      type: "POST",
      url: '${request.contextPath}/login',
      data: data,
      success: function (_result) {
        if(_result.success){
          window.location.href = "${request.contextPath}/";
        } else {
          alert(_result.error)
          data.password = null;
        }
      },
      error: function () {
        data.password = null;
        alert("登录失败");
      }
    });
  }

  window.onload = function () {
    $(".connect p").eq(0).animate({"left": "0%"}, 600);
    $(".connect p").eq(1).animate({"left": "0%"}, 400);
  }

  function is_hide() {
    $(".alert").animate({"top": "-40%"}, 300)
  }

  function is_show() {
    $(".alert").show().animate({"top": "45%"}, 300)
  }
</script>
</body>

</html>

