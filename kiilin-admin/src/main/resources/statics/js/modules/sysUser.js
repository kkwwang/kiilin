// 表格显示条目
var columns = [

  {
    label: '用户名',
    data: "username",
    name: "username",
    width: "4%"
  },

  {
    label: '登录名',
    data: "loginName",
    name: "login_name",
    width: "4%"
  },


  {
    label: '电子邮箱',
    data: "email",
    name: "email",
    width: "4%"
  },

  {
    label: '手机号码',
    data: "mobile",
    name: "mobile",
    width: "4%"
  },


  {
    label: '用户类型',
    data: "userType",
    name: "user_type",
    width: "4%"
  },


  {
    label: '状态',
    data: "status",
    name: "status",
    width: "4%"
  },

  // {
  //   label: '所属部门',
  //   data: "deptId",
  //   name: "dept_id",
  //   width: "4%"
  // },


  {
    label: '创建时间',
    data: "createTime",
    name: "create_time",
    width: "4%"
  },

  {
    label: '备注',
    data: "remark",
    name: "remark",
    width: "4%"
  },

  {
    label: '操作',
    orderable: false,
    className: "td_center none_select",
    width: "4%",
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
    showDeptDialog: false,
    filterText: '',
    deptTree: [],
    deptTreeProps: {
      children: 'children',
      label: 'deptName'
    },
    // 提交中的标识。用户控制提交按钮不可点击
    submiting: false,
    // 查询条件，查询时将条件放入该对象即可
    queryData: {},
    userTypeList: [],
    roleList: [],
    // datatables对象
    table: null,
    // 表单数据
    formData: {},
    // 数据校验规则 文档：https://github.com/yiminghe/async-validator
    rules: {

      deptName: [
        {required: true, message: '请选择部门', trigger: 'blur'}
      ],


      username: [
        {required: true, message: '请填写用户名', trigger: 'blur'}
      ],

      loginName: [
        {required: true, message: '请填写登录名', trigger: 'blur'}
      ],


      email: [
        {required: true, message: '请填写电子邮箱', trigger: 'blur'}
      ],

      mobile: [
        {required: true, message: '请填写手机号码', trigger: 'blur'}
      ],


      userType: [
        {required: true, message: '请填写用户类型', trigger: 'blur'}
      ],

      userRoleList: [
        {required: true, message: '请选择用户角色', trigger: 'blur'}
      ],


    }
  },
  // 在 `methods` 对象中定义方法
  methods: {
    // 查询表格
    list: function () {
      vm.table = $("#list").tabletools({
        // 自定义属性
        ajaxUrl: baseUrl + "/sysUser/list",
        // 自定义属性
        ajaxData: vm.queryData,
        columns: columns,
        // 默认排序规则，请修改为具体规则
        order: [[7, 'asc']]
      });
    },

    getUserTypes: function () {
      $.ajax({
        url: baseUrl + "/sysDict/getDictByType",
        data: {type: "user_type"},
        dataType: "json",
        type: "post",
        success: function (_result) {
          if (_result.success) {
            vm.userTypeList = _result.data;
          } else {
            alert(_result.error);
          }
        },
        error: function () {
          alert("查询失败");
        }
      });
    },
    getRoleList: function () {
      $.ajax({
        url: baseUrl + "/sysRole/all",
        dataType: "json",
        type: "post",
        success: function (_result) {
          if (_result.success) {
            vm.roleList = _result.data;
          } else {
            alert(_result.error);
          }
        },
        error: function () {
          alert("查询失败");
        }
      });
    },
    openDeptDialog(){
      vm.showDeptDialog = true;
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.deptName.indexOf(value) !== -1;
    },
    /**
     * 选择部门的回调
     * @param data
     */
    selectDept: function (data, node){
      if(node.disabled){
        return;
      }
      this.$refs.deptTreeRef.setCheckedKeys([data.id]);
    },
    submitDept(){
      var data = this.$refs.deptTreeRef.getCheckedNodes()[0];
      vm.formData.deptName = data.deptName;
      vm.formData.deptIds = data.id;
      this.showDeptDialog = false
    },
    getDeptTree: function () {
      $.ajax({
        url: baseUrl + "/sysDept/selectTree?hasRoot=false",
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
     * 新增
     */
    add: function () {
      // 重置
      vm.formData = {
        username: null,
        loginName: null,
        email: null,
        mobile: null,
        // sex: null,
        userType: null,
        remark: null,
        userRoleList: [],
        deptId: ''
      };
      vm.edit_flag = true;
      vm.submiting = false;
      vm.getUserTypes();
      vm.getRoleList();
      vm.getDeptTree();

    },
    /**
     * 编辑
     * @param id
     */
    edit: function (id) {
      vm.submiting = false;
      vm.getUserTypes();
      vm.getRoleList();
      vm.getDeptTree();

      $.ajax({
        url: baseUrl + "/sysUser/info",
        data: {id: id},
        dataType: "json",
        type: "post",
        success: function (_result) {
          if (_result.success) {
            _result.data.userRoleList = [];
            vm.formData = _result.data;

            // 查询用户关联角色
            vm.getUserRoleList(vm.formData.id);
            vm.getUserDept(vm.formData.id);

            // todo
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
    getUserRoleList: function (userId) {

      $.ajax({
        url: baseUrl + "/sysUser/getUserRoleList",
        data: {userId: userId},
        dataType: "json",
        type: "post",
        success: function (_result) {
          if (_result.success) {
            vm.formData.userRoleList = _result.data;

          } else {
            alert(_result.error);
          }
        },
        error: function () {
          alert("查询失败");
        }
      });
    },
    getUserDept(userId){
      $.ajax({
        url: baseUrl + "/sysUser/getUserDeptList",
        data: {userId: userId},
        dataType: "json",
        type: "post",
        async: false,
        success: function (_result) {
          if (_result.success) {
            if(_result.data.length > 0){
              vm.formData.deptIds = _result.data[0].id;
              vm.formData.deptName = _result.data[0].deptName;
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
      // 密码为空字符串时，置空.不为空时使用md5加密
      if (!vm.formData.password) {
        delete vm.formData.password;
      } else {
        vm.formData.password = $.md5(vm.formData.password);
      }

      if(vm.formData.userRoleList.length > 0){
        vm.formData.roleIds = vm.formData.userRoleList.join();
      }

      $.ajax({
        url: baseUrl + "/sysUser/save",
        data: vm.formData,
        dataType: "json",
        type: "post",
        success: function (_result) {
          vm.submiting = false;
          if (_result.success) {
            alert("保存成功", function () {
              vm.table.ajaxReload(vm.queryData);
              vm.edit_flag = false;
            })
          } else {
            vm.formData.password = null;
            alert(_result.error);
          }
        },
        error: function () {
          vm.formData.password = null;
          vm.submiting = false;
          alert("保存失败");
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
          url: baseUrl + "/sysUser/del",
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
