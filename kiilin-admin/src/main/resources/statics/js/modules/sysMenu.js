var tags = ['el-tag--success', 'el-tag--warning', 'el-tag--info', 'el-tag--danger', ''];
var types = {length: 0};
var columns = [
  {title: '菜单名称', field: 'menuName', sortable: false, width: '180px'},
  {title: '菜单级别', field: 'menuLevel', sortable: false, width: '100px'},
  {
    title: '图标',
    field: 'icon',
    sortable: false,
    width: '80px',
    formatter: function (item, index) {
      return item.icon == null ? '' : '<i class="' + item.icon + '"></i>';
    }
  },
  {
    title: '菜单类型',
    field: 'menuType',
    sortable: false,
    width: '100px',
    formatter: function (item, index) {

      if(!item.menuType){
        return '';
      }

      if(types[item.menuType]){
        return types[item.menuType];
      } else {
        var tagClass = tags[types.length];
        var html = '<span class="el-tag ' + tagClass + '">' + item.menuType + '</span>';
        types.length += 1;
        types[item.menuType] = html;
        return html;
      }
    }
  },
  {title: '排序', field: 'sort', sortable: false, width: '100px'},
  {
    title: '是否显示', field: 'sort', sortable: false, width: '100px', formatter(item, index) {
      if(!item.menuType){
        return '';
      }
      return item.showFlag ? "显示" : "不显示";
    }
  },
  {title: '链接', field: 'menuHref', sortable: false, width: '160px'},
  {title: '权限标识', field: 'permission', sortable: true},
  {title: '归属系统', field: 'sysCode', width: '80px',sortable: true},
  {
    title: '操作',
    field: '',
    sortable: false,
    width: '180px',
    formatter: function (item, index) {
      if(!item.menuType){
        return '';
      }
      return $.template("html", {
        template: $("#action_btn"),
        data: {id: item.id}
      });
    }
  },
];


var vm = new Vue({
  el: '#app',
  data: {
    // 编辑标识，用户切换表单和表格
    edit_flag: false,
    // 提交中的标识。用户控制提交按钮不可点击
    submiting: false,
    showParentDialog: false,
    filterText: "",
    expandAll: true,
    table: null,
    menuTypeList: [],
    sysCodeList: [],
    menuTreeProps: {
      children: 'children',
      label: 'menuName'
    },
    menuTree: [],
    // 查询条件，查询时将条件放入该对象即可
    queryData: {
      // parentId: "0"
    },
    // datatables对象
    table: null,
    // 表单数据
    formData: {},
    openData: {},
    // 数据校验规则 文档：https://github.com/yiminghe/async-validator
    rules: {

      parentId: [
        {required: true, message: '请选择父菜单', trigger: 'blur'}
      ],

      menuName: [
        {required: true, message: '请填写菜单名称', trigger: 'blur'}
      ],

      menuLevel: [
        {required: true, message: '请填写菜单级别', trigger: 'blur'}
      ],

      menuType: [
        {required: true, message: '请填写菜单类型', trigger: 'blur'}
      ],

      sysCode: [
        {required: true, message: '请填写归属系统', trigger: 'blur'}
      ],

    }
  },
  // 在 `methods` 对象中定义方法
  methods: {
    // 查询表格
    list: function () {
      var table = new TreeTable("menuTable", baseUrl + "/sysMenu/list", columns);
      table.setExpandColumn(0);
      table.setIdField("id");
      table.setCodeField("id");
      table.setParentCodeField("parentId");
      table.setRootCodeValue("0");
      table.setExpandAll(vm.expandAll);
      table.init();

      vm.table = table;
    },
    expandAllFn(){
      vm.expandAll = !vm.expandAll;
      vm.table.toggleExpandAll(vm.expandAll);
    },
    getMenuTree: function (sysCode) {
      $.ajax({
        url: baseUrl + "/sysMenu/selectTreeNoneAction?hasRoot=true",
        data: {sysCode: sysCode},
        dataType: "json",
        type: "post",
        success: function (_result) {
          if (_result.success) {
            vm.menuTree = _result.data;
          } else {
            alert(_result.error);
          }
        },
        error: function () {
          alert("查询失败");
        }
      });
    },
    getMenuTypeList: function () {
      $.ajax({
        url: baseUrl + "/sysDict/getDictByType",
        data: {type: "menu_type"},
        dataType: "json",
        type: "post",
        success: function (_result) {
          if (_result.success) {
            vm.menuTypeList = _result.data;
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
      $.ajax({
        url: baseUrl + "/sysDict/getDictByType",
        data: {type: "sys_code"},
        dataType: "json",
        type: "post",
        success: function (_result) {
          if (_result.success) {
            vm.sysCodeList = _result.data;
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

        parentId: null,
        parentName: null,

        parentIds: null,

        menuName: null,

        menuLevel: null,

        menuType: "菜单",

        menuHref: null,

        menuTarget: 'main',

        menuIcon: null,

        permission: null,

        showFlag: true,

        sort: null,

        sysCode: '',

        remark: null
      };
      vm.edit_flag = true;
      vm.submiting = false;
      vm.menuTree = [];
      // vm.getMenuTree();
      vm.getMenuTypeList();
      vm.getSysCodeList();

    },
    /**
     * 编辑
     * @param id
     */
    edit: function (id) {
      vm.submiting = false;
      vm.menuTree = [];

      // vm.getMenuTree();
      vm.getMenuTypeList();
      vm.getSysCodeList();

      $.ajax({
        url: baseUrl + "/sysMenu/info",
        data: {id: id},
        dataType: "json",
        type: "post",
        success: function (_result) {
          if (_result.success) {
            vm.formData = _result.data;
            vm.sysCodeChange();
            vm.edit_flag = true;
            // 父菜单名
            if (vm.formData.parentId == '0') {
              vm.formData.parentName = '根目录';
            } else {
              $.each(vm.tableData, function (index, item) {
                if (vm.formData.parentId == item.id) {
                  vm.formData.parentName = item.menuName;
                }
              });
            }
          } else {
            alert(_result.error);
          }
        },
        error: function () {
          alert("查询失败");
        }
      });
    },
    menuTypeChange: function (item) {
      vm.menuType = item.value;
    },
    sysCodeChange(){
      var sysCode = vm.formData.sysCode;
      vm.getMenuTree(sysCode);
    },
    openParentMenuDialog: function () {
      if(!vm.formData.sysCode){
        alert("请先选中所属系统");
        return;
      }
      vm.showParentDialog = true;
    },
    /**
     * 选择父菜单的回调
     * @param data
     */
    selectParentMenu: function (data, node){
      if(node.disabled){
        return;
      }
      this.$refs.menuTreeRef.setCheckedKeys([data.id]);

    },
    /**
     * 提交父菜单
     */
    submitParentMenu: function () {
      var data = this.$refs.menuTreeRef.getCheckedNodes()[0];
      vm.formData.parentName = data.menuName;
      vm.formData.parentId = data.id;
      vm.formData.parentIds = (data.parentIds || "") + (data.id || "0") + ",";
      // 菜单级别
      vm.formData.menuLevel = data.menuLevel + 1;
      this.showParentDialog = false
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
        url: baseUrl + "/sysMenu/save",
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
          url: baseUrl + "/sysMenu/del",
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
      return data.menuName.indexOf(value) !== -1;
    }
  },
  // vue创建时执行（钩子）
  created: function () {

  },
  watch: {
    filterText(val) {
      this.$refs.menuTreeRef.filter(val);
    }
  },
});

vm.list();
