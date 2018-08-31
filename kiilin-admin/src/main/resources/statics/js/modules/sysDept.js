// 表格显示条目
var columns = [
  {
    title: '部门名称',
    field: "deptName",
    name: "dept_name",
    width: "10%"
  },
  {
    title: '部门代号',
    field: "deptCode",
    name: "dept_code",
    width: "10%"
  },
  {
    title: '部门类型',
    field: "deptType",
    name: "dept_type",
    width: "10%"
  },
  {
    title: '创建时间',
    field: "createTime",
    name: "create_time",
    width: "10%"
  },
  {
    title: '备注',
    field: "remark",
    name: "remark",
    width: "10%"
  },
  {
    title: '操作',
    field: "",
    width: "10%",
    // 返回自定义操作列
    formatter: function (item, index) {
      return $.template("html", {
        template: $("#action_btn"),
        data: item
      });
    }
  },
];

var vm = new Vue({
  el: '#app',
  data: {
    // 编辑标识，用户切换表单和表格
    edit_flag: false,
    showParentDialog: false,
    deptTree: [],
    deptTypeList: [],
    filterText: "",
    expandAll: true,
    deptTreeProps: {
      children: 'children',
      label: 'deptName'
    },
    // 提交中的标识。用户控制提交按钮不可点击
    submiting: false,
    // 查询条件，查询时将条件放入该对象即可
    queryData: {},
    // datatables对象
    table: null,
    // 表单数据
    formData: {},
    // 数据校验规则 文档：https://github.com/yiminghe/async-validator
    rules: {
      parentId: [
        {required: true, message: '请选择上级部门', trigger: 'change'}
      ],
      deptName: [
        {required: true, message: '请填写部门名称', trigger: 'blur'}
      ],
      deptCode: [
        {required: true, message: '请填写部门代号', trigger: 'blur'}
      ],
      deptType: [
        {required: true, message: '请填写部门类型', trigger: 'blur'}
      ],

    }
  },
  // 在 `methods` 对象中定义方法
  methods: {
    // 查询表格
    list: function () {
      var table = new TreeTable("list", baseUrl + "/sysDept/list", columns);
      table.setExpandColumn(0);
      table.setIdField("id");
      table.setCodeField("id");
      table.setParentCodeField("parentId");
      table.setExpandAll(vm.expandAll);
      table.init();

      vm.table = table;
    },
    expandAllFn(){
      vm.expandAll = !vm.expandAll;
      vm.table.toggleExpandAll(vm.expandAll);
    },
    /**
     * 查询部门类型
     */
    getDeptTypeList() {
      $.ajax({
        url: baseUrl + "/sysDict/getDictByType",
        data: {type: "dept_type"},
        dataType: "json",
        type: "post",
        success: function (_result) {
          if (_result.success) {
            vm.deptTypeList = _result.data;
          } else {
            alert(_result.error);
          }
        },
        error: function () {
          alert("查询失败");
        }
      });
    },
    /**
     * 新增
     */
    add: function () {
      // 重置
      vm.formData = {
        deptName: null,
        deptCode: null,
        deptType: null,
        remark: null,
        parentId: ''
      };
      vm.edit_flag = true;
      vm.submiting = false;
      vm.getDeptTree();
      vm.getDeptTypeList();

    },
    /**
     * 编辑
     * @param id
     */
    edit: function (id) {
      vm.submiting = false;
      vm.getDeptTree();
      vm.getDeptTypeList();

      $.ajax({
        url: baseUrl + "/sysDept/info",
        data: {id: id},
        dataType: "json",
        type: "post",
        success: function (_result) {
          if (_result.success) {
            vm.formData = _result.data;
            // 父菜单名
            if (vm.formData.parentId == '0') {
              vm.formData.parentName = '根目录';
            } else {
              $.each(vm.tableData, function (index, item) {
                if (vm.formData.parentId == item.id) {
                  vm.formData.parentName = item.deptName;
                }
              });
            }

            vm.edit_flag = true;
          } else {
            alert(_result.error);
          }
        },
        error: function () {
          alert("查询失败");
        }
      });
    },
    openParentDeptDialog() {
      vm.showParentDialog = true;
    },
    submitParentDept() {
      var data = this.$refs.deptTreeRef.getCheckedNodes()[0];
      vm.formData.parentName = data.deptName;
      vm.formData.parentId = data.id;
      vm.formData.parentIds = (data.parentIds || "") + (data.id || "0") + ",";
      // 级别
      vm.formData.deptLevel = data.deptLevel + 1;
      this.showParentDialog = false
    },
    /**
     * 选择父部门的回调
     * @param data
     */
    selectParentDept: function (data, node){
      if(node.disabled){
        return;
      }
      this.$refs.deptTreeRef.setCheckedKeys([data.id]);
    },
    getDeptTree: function () {
      $.ajax({
        url: baseUrl + "/sysDept/selectTree?hasRoot=true",
        dataType: "json",
        type: "post",
        success: function (_result) {
          if (_result.success) {
            vm.deptTree = _result.data;
          } else {
            alert(_result.error);
          }
        },
        error: function () {
          alert("查询失败");
        }
      });
    },
    /**
     * 提交
     * @param formName
     */
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // 校验通过，调用保存
          vm.submiting = true;
          vm.save();
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    /**
     * 保存
     */
    save: function () {
      $.ajax({
        url: baseUrl + "/sysDept/save",
        data: vm.formData,
        dataType: "json",
        type: "post",
        success: function (_result) {
          vm.submiting = false;
          if (_result.success) {
            alert("保存成功", function () {
              vm.table.refresh();
              vm.edit_flag = false;
            })
          } else {
            alert(_result.error);
          }
        },
        error: function () {
          alert("保存失败");
          vm.submiting = false;
        }
      });
    },
    /**
     * 重置表单
     * @param formName
     */
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    /**
     * 删除
     * @param id
     */
    del: function (id) {
      confirm("确认删除该条记录吗？", function () {
        $.ajax({
          url: baseUrl + "/sysDept/del",
          data: {id: id},
          dataType: "json",
          type: "post",
          success: function (_result) {
            if (_result.success) {
              alert("删除成功", function () {
                vm.table.refresh();
              })
            } else {
              alert(_result.error);
            }
          },
          error: function () {
            alert("删除失败");
          }
        });
      });
    },
    // 取消
    cancel: function (formName) {
      vm.resetForm(formName);
      vm.edit_flag = false;
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.deptName.indexOf(value) !== -1;
    }
  },
  // vue创建时执行（钩子）
  created: function () {

  },
  watch: {
    filterText(val) {
      this.$refs.deptTreeRef.filter(val);
    }
  },
});

vm.list();
