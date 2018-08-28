<#-- @ftlroot "./" -->
<!DOCTYPE html>
<html lang="en">
<head>
  <#include "/header.ftl">
  <script type="text/javascript" src="${request.contextPath}/statics/libs/jquery.rotate.min.js"></script>
  <script type="text/javascript" src="${request.contextPath}/statics/libs/jquery.md5.js"></script>

  <meta charset="UTF-8">
  <title>首页</title>
  <style type="text/css">
    html,body{
      height: 100%;
    }

    #app{
      padding: 0px;
    }
    #main{
      font-size: 0px;
      line-height: 0px;
    }
    #header{
      background-color: #ffffff;
    }
    .main-footer{
      height: 60px;
      line-height: 60px;
      text-align: center;
      color: #666666;
      border-top: 1px #dddddd solid;
    }

    .main-footer a{
      color: #666666;
    }

    .main-footer a:hover{
      color: #666666;
      text-decoration: none;
    }

    #iframe-content{
      height: 100%;
      width: 100%;
      padding: 10px;
      background-color: #fff;
    }

    #left_menu{
      background: linear-gradient(to bottom, #082F48, #105A8A);
      height: 100%;
    }

    #left_aside{
      width: auto !important;
    }

    #left_menu:not(.el-menu--collapse){
      width: 300px;
    }

    .menu_collapse_btn{
      line-height: 60px;
      font-size: 36px;
      color: rgba(0,0,0,.5);
      margin-left: 10px;
    }

    .user_photo {
      width: 36px;
      height: 36px;
      border-radius: 50%;
      border: 1px #909399 solid;
      margin-right: 10px;
    }
  </style>

</head>
<body>

<el-container style="height: 100%;" id="app">

  <el-container>
    <#-- 菜单 -->
    <el-aside id="left_aside">
      <el-menu
        id="left_menu"
        :default-active="activeIndex"
        class="el-menu-vertical-demo"
        @open="mainMenuOpen"
        @close="mainMenuClose"
        @select="mainMenuSelect"
        background-color="#082F48"
        text-color="#fff"
        unique-opened
        active-text-color="#ffd04b"
        :collapse="isCollapse">
      <#-- 一级菜单有子菜单 -->
        <el-submenu
          :index="subItem.id"
          v-for="(subItem, index) in menus"
          v-if="subItem.menuLevel == 1 && !subItem.menuHref"
        >
          <template slot="title">
            <i :class="subItem.menuIcon"></i>
            <span slot="title">{{subItem.menuName}}</span>
          </template>
          <el-menu-item
            v-for="(item, index) in menus"
            v-if="item.menuLevel == 2 && item.parentId == subItem.id"
            :index="item.id"
          <#--:data-href="'#' + item.menuHref"-->
          >
            <i :class="item.menuIcon"></i>
            <span slot="title">{{item.menuName}}</span>
          </el-menu-item>
        </el-submenu>
      <#-- 一级菜单无子菜单 -->
        <el-menu-item
          v-for="(item, index) in menus"
          v-if="item.menuLevel == 1 && item.menuHref"
          :index="item.id"
        <#--:data-href="'#' + item.menuHref"-->
        >
          <i :class="item.menuIcon"></i>
          <span slot="title">{{item.menuName}}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container style="background-color: #ecf0f5;">
    <#-- 顶部导航 -->
      <el-header id="header">
        <i class="menu_collapse_btn el-icon-menu" @click="collapse"></i>
        <el-menu
          :default-active="activeIndex"
          style="float: right; "
          class="el-menu-demo"
          mode="horizontal"
          @select="topMenuSelect">
          <#-- 固定用户操作按钮 -->
          <el-submenu index="loginuser">
            <template slot="title">
              <img
                class="user_photo"
                :src="loginUser.photo || 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534400326565&di=12fcface96be98e71100ddd97242d187&imgtype=0&src=http%3A%2F%2Fimg1.touxiang.cn%2Fuploads%2F20140512%2F12-085354_583.jpg'"
              >
              {{loginUser.username}}
            </template>
            <el-menu-item index="logout" @click="logout">安全退出</el-menu-item>
            <el-menu-item index="modify_password" @click="modify_password">修改密码</el-menu-item>
          </el-submenu>
          <#-- 其余按钮 -->
          <el-submenu
            :index="subItem.id"
            v-for="(subItem, index) in top_menus"
            v-if="subItem.menuLevel == 1 && !subItem.menuHref"
          >
            <template slot="title">
              <i :class="subItem.menuIcon"></i>
              <span slot="title">{{subItem.menuName}}</span>
            </template>
            <el-menu-item
              v-for="(item, index) in top_menus"
              v-if="item.menuLevel == 2 && item.parentId == subItem.id"
              :index="item.id"
            <#--:data-href="'#' + item.menuHref"-->
            >
              <i :class="item.menuIcon"></i>
              <span slot="title">{{item.menuName}}</span>
            </el-menu-item>
          </el-submenu>
        <#-- 一级菜单无子菜单 -->
          <el-menu-item
            v-for="(item, index) in top_menus"
            v-if="item.menuLevel == 1 && item.menuHref"
            :index="item.id"
          <#--:data-href="'#' + item.menuHref"-->
          >
            <i :class="item.menuIcon"></i>
            <span slot="title">{{item.menuName}}</span>
          </el-menu-item>

        </el-menu>
      </el-header>

      <el-main id="main">
        <el-breadcrumb separator-class="el-icon-arrow-right" style="margin: 10px;">
          <div style="float: right; width: 5em; text-align: center;" @click="reload">
            <i class="el-icon-refresh"></i>
          </div>
          <el-breadcrumb-item>首页</el-breadcrumb-item>
          <el-breadcrumb-item v-for="name in breadcrumb">{{name}}</el-breadcrumb-item>
        </el-breadcrumb>
        <iframe id="iframe-content" name="iframe-content" scrolling="yes" frameborder="0" :src="main"></iframe>
      </el-main>

      <#-- 页脚 -->
      <el-footer>
        <div class="main-footer">
          Copyright &copy; 2018
          <a href="http://www.kiilin.com" target="_blank">kiilin.com</a>
          All Rights Reserved&nbsp;&nbsp;&nbsp;
          <a href="http://www.miibeian.gov.cn" target="_blank">苏ICP备17015164号-1</a>
        </div>
      </el-footer>
    </el-container>
  </el-container>


</el-container>

<div id="password-app">
  <el-dialog title="修改密码" :visible.sync="dialogFormVisible">
    <el-form :model="form" label-width="120px">
      <el-form-item label="原密码">
        <el-input v-model="form.password" type="password" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="新密码">
        <el-input v-model="form.new_password" type="password" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码">
        <el-input v-model="form.re_new_password" type="password" auto-complete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="pwd_submit">确 定</el-button>
    </div>
  </el-dialog>
</div>



<script type="text/javascript">
  var vm = new Vue({
    el: "#app",
    data: {
      isCollapse: false,
      activeIndex: "",
      menus: {},
      top_menus: {},
      loginUser: {},
      menu_width: "300px",
      main: "main.html",
      breadcrumb: [],
    },
    methods: {
      logout() {
        window.location.href = baseUrl + "/logout"
      },
      modify_password() {
        pwd_vm.dialogFormVisible = true;
      },
      getLoginUser() {
        $.ajax({
          url: baseUrl + "/sysUser/getLoginUser",
          dataType: "json",
          type: "post",
          success: function (_result) {
            if (_result.success) {
              vm.loginUser = _result.data;
            } else {
              alert(_result.error);
            }
          },
          error: function () {
            alert("查询失败");
          }
        });
      },
      collapse: function () {
        this.isCollapse = !this.isCollapse;
        $('.menu_collapse_btn').rotate({
            angle: 0,
            animateTo: 90,
            easing: $.easing.easeInOutExpo
        });
      },
      mainMenuOpen: function (key, keyPath) {
      },
      mainMenuClose: function (key, keyPath) {
      },

      /**
       * 主菜单选择回调
       * @param key
       * @param keyPath
       */
      mainMenuSelect: function (key, keyPath) {
        menuSelect(vm.menus, key, keyPath);
      },
      /**
       * 顶部菜单选择回调
       * @param key
       * @param keyPath
       */
      topMenuSelect: function (key, keyPath) {
        menuSelect(vm.top_menus, key, keyPath);
      },
      reload: function () {
        window.location.reload();
      }
    },
    created: function () {

    }
  });

  vm.getLoginUser();
  initMenu("menus", "pc");
  initMenu("top_menus", "pc_top");


  function menuSelect(menuScope, key, keyPath) {
    // 为了使两组菜单仅选中一个
    vm.activeIndex = key;
    // 拼接地址
    window.location.href = window.location.href.split("#")[0] + "#" + key;
    // 获取菜单
    var _data = menuScope._data[key];
    if (_data) {
      // 跳转及调整面包屑
      vm.main = _data.menuHref;
      var breadcrumb = [];
      $.each(keyPath, function () {
        var name = menuScope._data[this].menuName;
        breadcrumb.push(name);
      });
      vm.breadcrumb = breadcrumb;
    }
  }

  /**
   * 初始化菜单
   * @param menuName
   * @param sysCode
   */
  function initMenu(menuScope, sysCode) {
    var url = window.location.href;
    var index = url.split("#")[1];

    // 初始化菜单
    $.ajax({
      type: "POST",
      url: '${request.contextPath}/sysMenu/getMenuByUser?types=menu,menu_dir',
      data: {sysCode: sysCode},
      success: function (_result) {
        if (_result.success) {
          vm[menuScope] = _result.data;
          // 方便快速取值
          var _data = {};
          $.each(_result.data, function () {
            _data[this.id] = this;
          });
          vm[menuScope]._data = _data;

          // 初始 模拟路由
          if (index) {
            vm.activeIndex = index;
            var _data = vm[menuScope]._data[index];
            if (_data) {
              vm.main = _data.menuHref;
              vm.breadcrumb = [];
              if (_data.menuLevel != 1) {
                vm.breadcrumb.push(vm[menuScope]._data[_data.parentId].menuName);
              }
              vm.breadcrumb.push(_data.menuName);
            }
          }
        } else {
          alert(_result.error)
        }
      }
    });
  }

  function iframeHeight() {
    var mian_height = $("#main").height();
    $("#iframe-content").height(mian_height - 55);
    var window_width = $(window).width();
    if(window_width <= 1024){
      vm.isCollapse = true;
    } else {
      vm.isCollapse = false;
    }
  }

  iframeHeight();
  window.onresize = function () {
    iframeHeight();

  }

  var pwd_vm = new Vue({
    el: "#password-app",
    data: {
      dialogFormVisible: false,
      form: {}
    },
    methods: {
      pwd_submit() {
        var formdata = this.form;

        if (!formdata.password) {
          alert("请输入原密码");
          return;
        }

        if (!formdata.new_password) {
          alert("请输入新密码");
          return;
        }
        if (formdata.new_password !== formdata.re_new_password) {
          alert("两次输入密码不一致，请重新输入");
          return;
        }

        var data = {
          password: $.md5(formdata.password),
          newPassword: $.md5(formdata.new_password),
        }

        $.ajax({
          url: baseUrl + "/sysUser/modifyPassword",
          dataType: "json",
          data: data,
          type: "post",
          success: function (_result) {
            if (_result.success) {
              alert("修改成功", function () {
                window.location.href = baseUrl + "/logout";
              })
            } else {
              alert(_result.error);
            }
          },
          error: function () {
            alert("提交失败");
          }
        });
      }
    }
  });


</script>
</body>
</html>