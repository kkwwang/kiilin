var table;

$(function () {

  table = $("#table").tabletools({

    ajaxUrl: baseUrl + "/sys/schedule/list",
    autoWidth: false,
    dataId: "jobId",
    order: [[4, 'asc']],

    columns: [
      {
        label: 'bean名称',
        data: "beanName",
        name: "bean_name",
        width: "3%"
      },
      {
        label: '方法名称',
        data: "methodName",
        name: "method_name",
        width: "10%"
      },
      {
        label: '参数',
        data: "params",
        name: "params",
        width: "10%"
      },
      {
        label: '运行状态',
        data: "status",
        name: "status",
        width: "10%",
        render(data, type, row, meta){
          if(data === 0){
            return '<span class="el-tag el-tag--success">正常</span>';
          }else if(data === 1){
            return '<span class="el-tag el-tag--warning">暂停</span>'
          }
        }
      },
      {
        label: 'cron表达式 ',
        data: "cronExpression",
        name: "cron_expression",
        width: "10%"
      },
      {
        label: '备注 ',
        data: "remark",
        name: "remark",
        width: "10%"
      },
      {
        label: '创建时间 ',
        visible: false,
        data: "createTime",
        name: "create_time",
        width: "10%"
      },
      {
        label: '查看日志',
        data: "jobId",
        name: "jobId",
        className: "td_center none_select",
        width: "10%",
        render(data, type, row, meta){
          if (log_flag) {
            return '<button type="button" class="el-button el-button--text none_select" onclick="vm.showLog(this)"><i class="el-icon-search"></i><span>日志列表</span></button>';
          }
          return '';
        }
      },
    ]
  });

});

var vm = new Vue({
  el:'#rrapp',
  data:{
    q:{
      beanName: null
    },
    showList: true,
    showLogFlag: false,
    show: {},
    title: null,
    schedule: {}
  },
  methods: {
    query: function () {
      vm.reload();
    },
    showLog(item){
      var data = table.row($(item).parents("tr")).data();
      if (data) {
        vm.show = data;
        vm.show.now = new Date().getTime();
        vm.showLogFlag = true;
        vm.$nextTick(function () {
          var mian_height = $("#showLog_div").height();
          var header_height = $("#showLog_div .el-dialog__header").outerHeight();
          $("#showLog_frame").height(mian_height - header_height - 15);
        })
      }
      return false;
    },
    add: function(){
      vm.showList = false;
      vm.title = "新增";
      vm.schedule = {};
    },
    update: function () {
      var jobId = table.getSelectDataId();
      if(jobId == null){
        return ;
      }

      $.get(baseURL + "/sys/schedule/info/" + jobId, function (_result) {
        vm.showList = false;
        vm.title = "修改";
        vm.schedule = _result.data;
      });
    },
    saveOrUpdate: function (event) {
      var url = vm.schedule.jobId == null ? "/sys/schedule/save" : "/sys/schedule/update";
      $.ajax({
        type: "POST",
        url: baseURL + url,
        contentType: "application/json",
        data: JSON.stringify(vm.schedule),
        success: function (_result) {
          if (_result.success) {
            alert('操作成功', function(index){
              vm.reload();
            });
          }else{
            alert(_result.error);
          }
        }
      });
    },
    del: function (event) {
      var jobIds = table.getSelectDataIds();
      // var jobIds = getSelectedRows();
      if(jobIds == null){
        return ;
      }

      confirm('确定要删除选中的记录？', function(){
        $.ajax({
          type: "POST",
          url: baseURL + "/sys/schedule/delete",
          contentType: "application/json",
          data: JSON.stringify(jobIds),
          success: function (_result) {
            if (_result.success) {
              alert('操作成功', function(index){
                vm.reload();
              });
            }else{
              alert(_result.error);
            }
          }
        });
      });
    },
    pause: function (event) {
      var jobIds = table.getSelectDataIds();
      if(jobIds == null){
        return ;
      }

      confirm('确定要暂停选中的记录？', function(){
        $.ajax({
          type: "POST",
          url: baseURL + "/sys/schedule/pause",
          contentType: "application/json",
          data: JSON.stringify(jobIds),
          success: function (_result) {
            if (_result.success) {
              alert('操作成功', function(index){
                vm.reload();
              });
            }else{
              alert(_result.error);
            }
          }
        });
      });
    },
    resume: function (event) {
      var jobIds = table.getSelectDataIds();
      if(jobIds == null){
        return ;
      }

      confirm('确定要恢复选中的记录？', function(){
        $.ajax({
          type: "POST",
          url: baseURL + "/sys/schedule/resume",
          contentType: "application/json",
          data: JSON.stringify(jobIds),
          success: function (_result) {
            if (_result.success) {
              alert('操作成功', function(index){
                vm.reload();
              });
            }else{
              alert(_result.error);
            }
          }
        });
      });
    },
    runOnce: function (event) {
      var jobIds = table.getSelectDataIds();
      if(jobIds == null){
        return ;
      }

      confirm('确定要立即执行选中的记录？', function(){
        $.ajax({
          type: "POST",
          url: baseURL + "/sys/schedule/run",
          contentType: "application/json",
          data: JSON.stringify(jobIds),
          success: function (_result) {
            if (_result.success) {
              alert('提交成功，请稍后查看执行结果', function(index){
                vm.reload();
              });
            }else{
              alert(_result.error);
            }
          }
        });
      });
    },
    reload: function (event) {
      vm.showList = true;

      table.ajaxReload({'beanName': vm.q.beanName});

    }
  },
  created: function () {

  }
});