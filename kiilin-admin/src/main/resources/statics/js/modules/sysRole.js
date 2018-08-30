var columns = [
      

  {
    label: '角色名',
    data: "roleName",
    name: "role_name",
    width: "7%"
  },
      
  {
    label: 'code',
    data: "roleCode",
    name: "role_code",
    width: "7%"
  },
      
  {
    label: '角色类型',
    data: "roleType",
    name: "role_type",
    width: "7%"
  },
      
  {
    label: '数据范围',
    data: "dataScope",
    name: "data_scope",
    width: "7%"
  },

  {
    label: '创建时间',
    data: "createTime",
    name: "create_time",
    width: "7%"
  },
      
  {
    label: '备注',
    data: "remark",
    name: "remark",
    width: "7%"
  },
    
  {
    label: '操作',
    orderable: false,
    className: "td_center none_select",
    width: "7%",
    // 返回自定义操作列
    render: function (data, type, row, meta) {
      return $.template("html", {
        template: $("#action_btn"),
        data: {id: row.id}
      });

    }
  },
];

var vm = new Vue({
  el: '#app',
  data: {
    // 编辑标识，用户切换表单和表格
    edit_flag: false,
    check_strictly: true,
    // 提交中的标识。用户控制提交按钮不可点击
    submiting: false,
    // 查询条件，查询时将条件放入该对象即可
    queryData: {},
    // datatables对象
    table: null,
    // 表单数据
    formData: {},
    menuTree: [],
    userMenus: [],
    defaultProps: {
      children: 'children',
      label: 'menuName'
    },
    // 数据校验规则 文档：https://github.com/yiminghe/async-validator
    rules: {
    
      roleName: [
        {required: true, message: '请填写角色名', trigger: 'blur'}
      ],
    
      roleCode: [
        {required: true, message: '请填写角色 code', trigger: 'blur'}
      ],
    
      roleType: [
        {required: true, message: '请填写角色类型', trigger: 'blur'}
      ],
    
      dataScope: [
        {required: true, message: '请填写数据范围', trigger: 'blur'}
      ],

    }
  },
  // 在 `methods` 对象中定义方法
  methods: {
    // 查询表格
    list: function () {
      vm.table = $("#list").tabletools({
        // 自定义属性
        ajaxUrl: baseUrl + "/sysRole/list",
        // 自定义属性
        ajaxData: vm.queryData,
        columns: columns,
        order: [[4, 'asc']]

      });
    },
    /**
     * 新增
     */
    add: function () {
      // 重置
      vm.formData = {
        roleName: null,
        roleCode: null,
        roleType: null,
        dataScope: null,
        remark: null,
      };

      vm.edit_flag = true;
      vm.submiting = false;
      vm.check_strictly = false;
      vm.userMenus = [];
      vm.setCheckedKeys(vm.userMenus);
      vm.getSysCodeList();


    },
    /**
     * 编辑
     * @param id
     */
    edit: function (id) {
      vm.submiting = false;
      vm.check_strictly = true;

      // 查询角色详情
      $.ajax({
        url: baseUrl + "/sysRole/info",
        data: {id: id},
        dataType: "json",
        type: "post",


        success: function (_result) {
          if (_result.success) {
            vm.formData = _result.data;
            vm.selectRoleMenus(id);
            vm.getSysCodeList();
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

    getSysCodeList: function () {
      vm.menuTree = [];
      $.ajax({
        url: baseUrl + "/sysDict/getDictByType",
        data: {type: "sys_code"},
        dataType: "json",
        type: "post",
        success: function (_result) {
          if (_result.success) {
            $.each(_result.data, function (index, sysCode) {
              vm.selectMenuTree(sysCode);
            });
          } else {
            alert(_result.error);
          }
        },
        error: function () {
          alert("查询失败");
        }
      });
    },

    selectMenuTree(sysCode){
      // 查询
      $.ajax({
        url: baseUrl + "/sysMenu/selectTree?hasRoot=false",
        dataType: "json",
        data: {sysCode: sysCode.value},
        type: "post",
        async: false,
        success: function (_result) {
          if (_result.success) {
            // todo
            vm.menuTree.push({
              menuName: sysCode.label,
              id: "",
              children: _result.data
            })
          } else {
            alert(_result.error);
          }
        },
        error: function () {
          alert("删除失败");
        }
      });
    },
    selectRoleMenus(id) {
      // 查询用户权限集合
      $.ajax({
        url: baseUrl + "/sysRole/selectRoleMenus",
        data: {id: id},
        dataType: "json",
        type: "post",
        success: function (_result) {
          if (_result.success) {
            vm.userMenus = _result.data;
            vm.setCheckedKeys(vm.userMenus);
            vm.$nextTick(function () {
              vm.check_strictly = false;
            })
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
      vm.formData.menuids = vm.getCheckedKeys().join();
      $.ajax({
        url: baseUrl + "/sysRole/save",
        data: vm.formData,
        dataType: "json",
        type: "post",
        success: function (_result) {
          vm.submiting = false;
          if (_result.success) {
            alert("保存成功", function () {
              // vm.table.ajaxReload(vm.queryData);
              // vm.edit_flag = false;
              top.location.reload();
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
      vm.setCheckedKeys([]);
      this.$refs[formName].resetFields();
    },
    /**
     * 删除
     * @param id
     */
    del: function (id) {
      confirm("确认删除该条记录吗？", function () {
        $.ajax({
          url: baseUrl + "/sysRole/del",
          data: {id: id},
          dataType: "json",
          type: "post",
          success: function (_result) {
            if (_result.success) {
              alert("删除成功", function () {
                vm.table.ajaxReload(vm.queryData);
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
    /**
     * 设置选中菜单
     */
    setCheckedKeys(userMenus) {
      this.$refs.tree.setCheckedKeys(userMenus);
    },

    /**
     * 获取选中的树
     */
    getCheckedKeys() {
      return this.$refs.tree.getCheckedKeys();
    },
  },
  // vue创建时执行（钩子）
  created: function () {


  }
});

vm.list();