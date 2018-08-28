<#-- @ftlroot ".." -->
<!DOCTYPE html>
<html lang="en">
<head>
  <#include "/header.ftl">
  <meta charset="UTF-8">
  <title>数据字典类型管理</title>
  <style type="text/css">
    #dictEditDialog{
      height: 100%;
      width: 100%;
    }

    #dictEditDialog_div .el-dialog__header{
      border-bottom: 1px solid #cccccc;
    }
    #dictEditDialog_div .el-dialog__footer{
      border-top: 1px solid #cccccc;
    }
    #dictEditDialog_div .el-dialog__body{
      padding: 5px;
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
      <#--<#if shiro.hasPermission("sysDictType:save")>-->
        <el-button type="primary" @click="add"><i class="el-icon-circle-plus"></i>添加</el-button>
      <#--</#if>-->
      </el-col>
    </el-row>
  </el-header>
<#-- 表格-->
  <el-main>
    <el-row v-show="!edit_flag">
      <el-col :span="24">
        <table class="is-center" id="list"></table>
      </el-col>
    </el-row>

    <el-col :span="18" v-show="edit_flag">
      <el-form :model="formData" :rules="rules" ref="formData" width="70%" label-width="100px">
        <el-form-item label="类型值" prop="typeValue">
          <el-input v-model="formData.typeValue"></el-input>
        </el-form-item>

        <el-form-item label="类型名称" prop="typeLabel">
          <el-input v-model="formData.typeLabel"></el-input>
        </el-form-item>

        <el-form-item label="排序" prop="sort">
          <el-input v-model="formData.sort"></el-input>
        </el-form-item>


        <el-form-item class="float-right">
          <el-button type="primary" @click="submit('formData')" :disabled="submiting">保存</el-button>
          <el-button @click="resetForm('formData')">重置</el-button>
          <el-button @click="cancel('formData')">取消</el-button>
        </el-form-item>


      </el-form>
    </el-col>

  </el-main>

  <el-dialog
    id="dictEditDialog_div"
    :title="'管理数据字典（' + editTypeName + '）'"
    :visible.sync="editTypeFlag"
    fullscreen
  >
    <iframe id="dictEditDialog" scrolling="yes" frameborder="0" :src="'${request.contextPath}/modules/sysDict.html?type=' + editTypeId"></iframe>
  </el-dialog>

</el-container>


<#-- 操作 template -->
<script type="text/html" id="action_btn">
  <div class="el-button-group template">
    <button type="button" class="el-button el-button--text" onclick="vm.editDict('[typeValue]', '[typeLabel]')">
      <i class="el-icon-edit-outline"></i>管理
    </button>
  <#--<#if shiro.hasPermission("sysDictType:save")>-->
    <button type="button" class="el-button el-button--text" onclick="vm.edit('[id]')">
      <i class="el-icon-edit"></i>修改
    </button>
  <#--</#if>-->
  <#--<#if shiro.hasPermission("sysDictType:del")>-->
    <button type="button" class="el-button el-button--text" onclick="vm.del('[id]')">
      <i class="el-icon-delete"></i>删除
    </button>
  <#--</#if>-->
  </div>
</script>

<script>
  var has_sys_edit = false;
  <#if shiro.hasPermission("sysDictType:sysData:edit")>
    has_sys_edit = true;
  </#if>
</script>
<script type="text/javascript" src="${request.contextPath}/statics/js/modules/sysDictType.js?_${.now?string["yyyyMMddhhmmSSsss"]}"></script>

</body>
</html>