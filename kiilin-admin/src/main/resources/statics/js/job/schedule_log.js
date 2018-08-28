// 表格显示条目
var columns = [

  {
    label: 'bean名称',
    data: "beanName",
    name: "bean_name",
    width: "9%"
  },
  {
    label: '方法名称',
    data: "methodName",
    name: "method_name",
    width: "9%"
  },
  {
    label: '参数',
    data: "params",
    name: "params",
    width: "9%"
  },
  {
    label: '状态',
    data: "status",
    name: "status",
    className: "td_center none_select",
    width: "9%",
    render(data, type, row, meta) {
      if (data === 0) {
        return '<span class="el-tag el-tag--success">成功</span>';
      } else {
        return '<span class="el-tag el-tag--danger" title="查看失败原因" onclick="vm.showError(\'' + row.logId + '\')">失败</span>'
        }
    }
  },
  {
    label: '耗时(单位：毫秒)',
    data: "times",
    name: "times",
    width: "9%"
  },
  {
    label: '执行时间',
    data: "createTime",
    name: "create_time",
    width: "9%"
  },
]

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			jobId: null
		}
	},
	methods: {
    list() {
      this.table = $("#jobLogs").tabletools({
        ajaxUrl: baseURL + '/sys/scheduleLog/list',
        ajaxData: ajaxData,
        dataId: "logId",
        order: [[5, 'desc']],
        columns: columns
      });
    },
		showError: function(logId) {
      $.get(baseURL + "/sys/scheduleLog/info/" + logId, function (r) {
        top.layer.open({
				  title:'失败信息',
				  closeBtn:0,
          content: r.data.error
				});
			});
		},
  },

});

vm.list();

