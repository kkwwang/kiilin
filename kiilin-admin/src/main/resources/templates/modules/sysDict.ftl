<#-- @ftlroot "../" -->
<!DOCTYPE html>
<html lang="en">
<head>
  <#include "/header.ftl">
  <meta charset="UTF-8">
  <title>数据字典管理</title>
</head>
<body>
<el-container id="app">
<#-- 各种按钮 （条件查询等）-->
  <el-header height="40px;">
  <#-- 按钮组 -->
    <el-row v-show="!edit_flag">
      <el-col :span="24">
      <#if shiro.hasPermission("sysDict:save")>
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
      <el-form  :model="formData" :rules="rules" ref="formData" width="70%" label-width="100px">
                
        <#--<el-form-item label="类型值" prop="type">-->
          <#--<el-input v-model="formData.type" placeholder="请输入类型值"></el-input>-->
        <#--</el-form-item>-->
        
        <el-form-item label="字典名称" prop="label">
          <el-input v-model="formData.label" placeholder="请输入字典名称"></el-input>
        </el-form-item>
        
        <el-form-item label="字典值" prop="value">
          <el-input v-model="formData.value" placeholder="请输入字典值"></el-input>
        </el-form-item>
        
        <el-form-item label="排序" prop="sort">
          <el-input v-model="formData.sort" placeholder="请输入排序"></el-input>
        </el-form-item>
        
        <el-form-item label="备注" prop="remark">
            <el-input v-model="formData.remark"></el-input>
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
  <#if shiro.hasPermission("sysDict:save")>
    <button type="button" class="el-button el-button--text" onclick="vm.edit('[id]')">
      <i class="el-icon-edit"></i>修改
    </button>
  </#if>
  <#if shiro.hasPermission("sysDict:del")>
    <button type="button" class="el-button el-button--text" onclick="vm.del('[id]')">
      <i class="el-icon-delete"></i>删除
    </button>
  </#if>
  </div>
</script>

<script type="text/javascript">
  var type = '${type}' == '' ? null : '${type}';
</script>
<script>
  var has_sys_edit = false;
  <#if shiro.hasPermission("sysDict:sysData:edit")>
  has_sys_edit = true;
  </#if>
</script>
<script type="text/javascript" src="${request.contextPath}/statics/js/modules/sysDict.js?_${.now?string["yyyyMMddhhmmSSsss"]}"></script>

</body>
</html>