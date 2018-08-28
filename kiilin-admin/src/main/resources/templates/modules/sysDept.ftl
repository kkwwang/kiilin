<#-- @ftlroot ".." -->
<!DOCTYPE html>
<html lang="en">
<head>
  <#include "/header.ftl">

  <link rel="stylesheet" href="${request.contextPath}/statics/plugins/bootstrap-3.3.7/css/bootstrap.min.css">
  <link rel="stylesheet" href="${request.contextPath}/statics/plugins/bootstrap-table/bootstrap-table.min.css">
  <link rel="stylesheet" href="${request.contextPath}/statics/plugins/treegrid/jquery.treegrid.css">
  <script src="${request.contextPath}/statics/plugins/bootstrap-table/bootstrap-table.min.js"></script>
  <script src="${request.contextPath}/statics/plugins/treegrid/jquery.treegrid.min.js"></script>
  <script src="${request.contextPath}/statics/plugins/treegrid/jquery.treegrid.bootstrap3.js"></script>
  <script src="${request.contextPath}/statics/plugins/treegrid/jquery.treegrid.extension.js"></script>
  <script src="${request.contextPath}/statics/plugins/treegrid/tree.table.js"></script>
  <meta charset="UTF-8">
  <title>部门管理</title>
  <style type="text/css">
    #list td:first-child{
      text-align: left;
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
      <#if shiro.hasPermission("sysDept:save")>
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

        <el-form-item label="部门类型" prop="deptType">
          <el-select v-model="formData.deptType" filterable placeholder="请选择部门类型">
            <el-option
              v-for="item in deptTypeList"
              :key="item.value"
              :label="item.label"
              :value="item.label">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="上级部门" prop="parentId">
          <el-input v-model="formData.parentName" placeholder="请选择上级部门" readonly="readonly"
                    onclick="vm.openParentDeptDialog()"></el-input>
        </el-form-item>

        <el-form-item label="部门名称" prop="deptName">
          <el-input v-model="formData.deptName" placeholder="请输入部门名称"></el-input>
        </el-form-item>

        <el-form-item label="部门代号" prop="deptCode">
          <el-input v-model="formData.deptCode" placeholder="请输入部门代号"></el-input>
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


<#-- 选择父节点的弹窗 -->
  <el-dialog
    title="请选择父部门"
    :visible.sync="showParentDialog"
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
      :default-checked-keys="[formData.parentId]"
      :default-expanded-keys="[formData.parentId]"
      check-strictly
      :filter-node-method="filterNode"
      ref="deptTreeRef"
      :data="deptTree"
      :props="deptTreeProps"
      @node-click="selectParentDept"
      @check="selectParentDept"
    >
    </el-tree>
    <div slot="footer" class="dialog-footer">
      <el-button @click="showParentDialog = false">取 消</el-button>
      <el-button type="primary" @click="submitParentDept">确 定</el-button>
    </div>
  </el-dialog>
</el-container>


<#-- 操作 template -->
<script type="text/html" id="action_btn">
  <div class="el-button-group template">
  <#if shiro.hasPermission("sysDept:save")>
    <button type="button" class="el-button el-button--text" onclick="vm.edit('[id]')">
      <i class="el-icon-edit"></i>修改
    </button>
  </#if>
  <#if shiro.hasPermission("sysDept:del")>
    <button type="button" class="el-button el-button--text" onclick="vm.del('[id]')">
      <i class="el-icon-delete"></i>删除
    </button>
  </#if>
  </div>
</script>

<script type="text/javascript"
        src="${request.contextPath}/statics/js/modules/sysDept.js?_${.now?string["yyyyMMddhhmmSSsss"]}"></script>

</body>
</html>