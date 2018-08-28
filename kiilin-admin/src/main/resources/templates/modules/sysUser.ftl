<#-- @ftlroot ".." -->
<!DOCTYPE html>
<html lang="en">
<head>
  <#include "/header.ftl">
  <script src="${request.contextPath}/statics/libs/jquery.md5.js"></script>

  <meta charset="UTF-8">
  <title>用户管理</title>
</head>
<body>
<el-container id="app">
<#-- 各种按钮 （条件查询等）-->
  <el-header height="40px;">
  <#-- 按钮组 -->
    <el-row v-show="!edit_flag">
      <el-col :span="24">
      <#if shiro.hasPermission("sysUser:save")>
        <el-button type="primary" @click="add"><i class="el-icon-circle-plus"></i>添加</el-button>
      </#if>
      </el-col>
    </el-row>
  </el-header>
<#-- 表格-->
  <el-main>
    <el-row v-show="!edit_flag">
      <el-col :span="24">
        <table id="list"></table>
      </el-col>
    </el-row>

    <el-col :span="18" v-show="edit_flag">
      <el-form :model="formData" :rules="rules" ref="formData" width="70%" label-width="100px">


        <el-form-item label="所属部门" prop="deptName">
          <el-input v-model="formData.deptName" placeholder="请选择所属部门" readonly="readonly"
                    onclick="vm.openDeptDialog()"></el-input>
        </el-form-item>

        <el-form-item label="用户类型" prop="userType">
          <el-select v-model="formData.userType" filterable placeholder="请选择用户类型">
            <el-option
              v-for="item in userTypeList"
              :key="item.value"
              :label="item.label"
              :value="item.label">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="用户名" prop="username">
          <el-input v-model="formData.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        
        <el-form-item label="登录名" prop="loginName">
          <el-input v-model="formData.loginName" placeholder="请输入登录名"></el-input>
        </el-form-item>

        <el-form-item label="初始密码" prop="password">
          <el-input type="password" v-model="formData.password" placeholder="请输入初始密码"></el-input>
        </el-form-item>
        
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        
        <el-form-item label="手机号码" prop="mobile">
          <el-input v-model="formData.mobile" placeholder="请输入手机号码"></el-input>
        </el-form-item>
        
        <#--<el-form-item label="性别" prop="sex">-->
          <#--<el-radio v-model="formData.sex" label="男">男</el-radio>-->
          <#--<el-radio v-model="formData.sex" label="女">女</el-radio>-->
        <#--</el-form-item>-->

        
        <el-form-item label="备注" prop="remark">
            <el-input v-model="formData.remark" placeholder="请输入备注"></el-input>
        </el-form-item>


        <el-form-item label="角色" prop="userRoleList">
          <el-checkbox-group v-model="formData.userRoleList">
            <el-checkbox
              v-for="role in roleList"
              :label="role.id"
              <#--:key="role.id"-->
            >{{role.roleName}}</el-checkbox>
          </el-checkbox-group>
        </el-form-item>




        <el-form-item class="float-right">
          <el-button type="primary" @click="submit('formData')" :disabled="submiting">保存</el-button>
          <el-button @click="resetForm('formData')">重置</el-button>
          <el-button @click="cancel('formData')">取消</el-button>
        </el-form-item>


      </el-form>
    </el-col>

  </el-main>

<#-- 选择所属部门的弹窗 -->
  <el-dialog
    title="请选择所属部门"
    :visible.sync="showDeptDialog"
    width="50%"
  >
    <el-input
      placeholder="输入关键字进行过滤"
      v-model="filterText">
    </el-input>
    <el-tree
      class="filter-tree"
      node-key="id"
      accordion
      show-checkbox
      :default-checked-keys="[formData.deptId]"
      :default-expanded-keys="[formData.deptId]"
      check-strictly
      :filter-node-method="filterNode"
      ref="deptTreeRef"
      :data="deptTree"
      :props="deptTreeProps"
      @node-click="selectDept"
      @check="selectDept"
    >
    </el-tree>
    <div slot="footer" class="dialog-footer">
      <el-button @click="showDeptDialog = false">取 消</el-button>
      <el-button type="primary" @click="submitDept">确 定</el-button>
    </div>
  </el-dialog>

</el-container>


<#-- 操作 template -->
<script type="text/html" id="action_btn">
  <div class="el-button-group template">
  <#if shiro.hasPermission("sysUser:save")>
    <button type="button" class="el-button el-button--text" onclick="vm.edit('[id]')">
      <i class="el-icon-edit"></i>修改
    </button>
  </#if>
  <#if shiro.hasPermission("sysUser:del")>
    <button type="button" class="el-button el-button--text" onclick="vm.del('[id]')">
      <i class="el-icon-delete"></i>删除
    </button>
  </#if>
  </div>
</script>

<script type="text/javascript" src="${request.contextPath}/statics/js/modules/sysUser.js?_${.now?string["yyyyMMddhhmmSSsss"]}"></script>

</body>
</html>