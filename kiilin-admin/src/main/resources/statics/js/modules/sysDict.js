// 表格显示条目
var columns = [
      
  {
    label: '类型值',
    data: "type",
    name: "type",
    width: "9%"
  },
      
  {
    label: '字典名称',
    data: "label",
    name: "label",
    width: "9%"
  },
      
  {
    label: '字典值',
    data: "value",
    name: "value",
    width: "9%"
  },
      
  {
    label: '排序',
    data: "sort",
    name: "sort",
    width: "9%"
  },
    
            
  {
    label: '创建时间',
    data: "createTime",
    name: "create_time",
    width: "9%"
  },
                  
  {
    label: '备注',
    data: "remark",
    name: "remark",
    width: "9%"
  },
    
  {
    label: '操作',
    orderable: false,
    className: "td_center none_select",
    width: "9%",
    // 返回自定义操作列
    render: function (data, type, row, meta) {
      if(row.sysFlag && !has_sys_edit){
        return "";
      }
      return $.template("html", {
        template: $("#action_btn"),
        data: row
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
    // 查询条件，查询时将条件放入该对象即可
    queryData: {
      type: type ? '=' + type : null
    },
    // datatables对象
    table: null,
    // 表单数据
    formData: {},
    // 数据校验规则 文档：https://github.com/yiminghe/async-validator
    rules: {
    
      type: [
        {required: true, message: '请填写类型值', trigger: 'blur'}
      ],
    
      label: [
        {required: true, message: '请填写字典名称', trigger: 'blur'}
      ],
    
      value: [
        {required: true, message: '请填写字典值', trigger: 'blur'}
      ],
    
      sort: [
        {required: true, message: '请填写排序', trigger: 'blur'}
      ],
    
    }
  },
  // 在 `methods` 对象中定义方法
  methods: {
    // 查询表格
    list: function () {
      vm.table = $("#list").tabletools({
        // 自定义属性
        ajaxUrl: baseUrl + "/sysDict/list",
        // 自定义属性
        ajaxData: vm.queryData,
        columns: columns,
        order: [[3, 'asc']]
      });
    },
    /**
     * 新增
     */
    add: function () {
      // 重置
      vm.formData = {
      
        type: type,
      
        label: null,
      
        value: null,
      
        sort: null,
      
        remark: null
      };
      vm.edit_flag = true;
      vm.submiting = false;

    },
    /**
     * 编辑
     * @param id
     */
    edit: function (id) {
      vm.submiting = false;

      $.ajax({
        url: baseUrl + "/sysDict/info",
        data: {id: id},
        dataType: "json",
        type: "post",
        success: function (_result) {
          if (_result.success) {
            vm.formData = _result.data;
            if(vm.formData.sysFlag){
              confirm("系统数据，需谨慎修改。", function () {
                vm.edit_flag = true;
              })
            } else {
              vm.edit_flag = true;
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
    /**
     * 提交
     * @param formName
     */
    submit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (vm.formData.sysFlag) {
            confirm("您当前修改的为系统数据，请确认后再提交", function () {
              // 校验通过，调用保存
              vm.submiting = true;
              vm.save();
            }, function () {
              return;
            })
          } else {
            // 校验通过，调用保存
            vm.submiting = true;
            vm.save();
          }
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
        url: baseUrl + "/sysDict/save",
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
          url: baseUrl + "/sysDict/del",
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

  }
});

vm.list();