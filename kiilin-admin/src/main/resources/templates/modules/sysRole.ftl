<#-- @ftlroot "../" -->
<!DOCTYPE html>
<html lang="en">
<head>
  <#include "/header.ftl">
  <meta charset="UTF-8">
  <title>角色管理</title>
  <style type="text/css">
    #menu_tree .el-checkbox{
      margin-bottom: 0px;
    }
  </style>
</head>
<body>
<el-container id="app">
<#-- 各种按钮 （条件查询等）-->
  <el-header height="40px;">
  <#-- 按钮组 -->
    <el-row v-show="!edit_flag">
      <el-col :span="24">
      <#--<#if shiro.hasPermission("sysRole:save")>-->
        <el-button type="primary" @click="add"><i class="el-icon-circle-plus"></i>添加</el-button>
      <#--</#if>-->
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
                
        <el-form-item label="角色名" prop="roleName">
          <el-input v-model="formData.roleName"></el-input>
        </el-form-item>
        
        <el-form-item label="code" prop="roleCode">
          <el-input v-model="formData.roleCode"></el-input>
        </el-form-item>
        
        <el-form-item label="角色类型" prop="roleType">
          <el-input v-model="formData.roleType"></el-input>
        </el-form-item>
        
        <el-form-item label="数据范围" prop="dataScope">
          <el-input v-model="formData.dataScope"></el-input>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
            <el-input v-model="formData.remark"></el-input>
        </el-form-item>

        <el-form-item label="操作权限">
          <el-tree
            id="menu_tree"
            :data="menuTree"
            show-checkbox
            default-expand-all
            node-key="id"
            ref="tree"
            :check-strictly="check_strictly"

            highlight-current
            :props="defaultProps">
          </el-tree>
        </el-form-item>

        <el-form-item class="float-right">
          <el-button type="primary" @click="submit('formData')" :disabled="submiting">保存</el-button>
          <el-button @click="resetForm('formData')">重置</el-button>
          <el-button @click="cancel('formData')">取消</el-button>
        </el-form-item>


      </el-form>
    </el-col>

  </el-main>
</el-container>


<#-- 操作 template -->
<script type="text/html" id="action_btn">
  <div class="el-button-group template">
  <#--<#if shiro.hasPermission("sysRole:save")>-->
    <button type="button" class="el-button el-button--text" onclick="vm.edit('[id]')">
      <i class="el-icon-edit"></i>修改
    </button>
  <#--</#if>-->
  <#--<#if shiro.hasPermission("sysRole:del")>-->
    <button type="button" class="el-button el-button--text" onclick="vm.del('[id]')">
      <i class="el-icon-delete"></i>删除
    </button>
  <#--</#if>-->
  </div>
</script>

<script type="text/javascript" src="${request.contextPath}/statics/js/modules/sysRole.js?_${.now?string["yyyyMMddhhmmSSsss"]}"></script>

</body>
</html>