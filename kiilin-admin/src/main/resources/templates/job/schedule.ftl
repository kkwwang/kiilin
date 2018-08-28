<#-- @ftlroot ".." -->
<!DOCTYPE html>
<html>
<head>
<title>定时任务</title>
<#include "/header.ftl">
	<style type="text/css">
    #showLog_frame{
      height: 100%;
      width: 100%;
    }

    #showLog_div .el-dialog__header{
      border-bottom: 1px solid #cccccc;
    }
    #showLog_div .el-dialog__footer{
      border-top: 1px solid #cccccc;
    }
    #showLog_div .el-dialog__body{
      padding: 5px;
    }
	</style>
</head>
<body>
<div id="rrapp" v-cloak>
	<div v-show="showList">
    <el-row :gutter="10">
      <el-col :span="6">
        <input type="text" class="form-control" v-model="q.beanName" @keyup.enter="query" placeholder="bean名称">
      </el-col>
      <el-col :span="18">
        <el-button @click="query">查询</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
				<#if shiro.hasPermission("test")>
					test
				</#if>
				<#if shiro.hasPermission("sys:schedule:save")>
					<el-button type="primary" @click="add"><i class="el-icon-plus"></i>&nbsp;新增</el-button>
				</#if>
				<#if shiro.hasPermission("sys:schedule:update")>
					<el-button type="primary" @click="update"><i class="el-icon-edit"></i>&nbsp;修改</el-button>
				</#if>
				<#if shiro.hasPermission("sys:schedule:delete")>
					<el-button type="primary" @click="del"><i class="el-icon-delete"></i>&nbsp;删除</el-button>
				</#if>
				<#if shiro.hasPermission("sys:schedule:pause")>
					<el-button type="primary" @click="pause"><i class="el-icon-circle-close"></i>&nbsp;暂停</el-button>
				</#if>
				<#if shiro.hasPermission("sys:schedule:resume")>
					<el-button type="primary" @click="resume"><i class="el-icon-success"></i>&nbsp;恢复</el-button>
				</#if>
				<#if shiro.hasPermission("sys:schedule:run")>
					<el-button type="primary" @click="runOnce"><i class="el-icon-d-arrow-right"></i>&nbsp;立即执行</el-button>
				</#if>

      </el-col>
    </el-row>
    <table id="table"></table>
	</div>
	
	<div v-show="!showList" class="panel panel-default">
		<div class="panel-heading">{{title}}</div>
		<form class="form-horizontal">
			<div class="form-group">
			   	<div class="col-sm-2 control-label">bean名称</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="schedule.beanName" placeholder="spring bean名称，如：testTask"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">方法名称</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="schedule.methodName" placeholder="方法名称"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">参数</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="schedule.params" placeholder="参数"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">cron表达式</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="schedule.cronExpression" placeholder="如：0 0 12 * * ?"/>
			    </div>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">备注</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="schedule.remark" placeholder="备注"/>
			    </div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label"></div> 
				<input type="button" class="btn btn-primary" @click="saveOrUpdate" value="确定"/>
				&nbsp;&nbsp;<input type="button" class="btn btn-warning" @click="reload" value="返回"/>
			</div>
		</form>
	</div>

	<#-- log_page_dialog -->
  <el-dialog
    id="showLog_div"
    :title="'查看日志 -- ' + show.beanName + '#' + show.methodName + '(' + (show.params || '') + ')'"
    :visible.sync="showLogFlag"
    fullscreen
  >
    <iframe id="showLog_frame" scrolling="yes" frameborder="0" :src="'schedule_log.html?_' + show.now + '&jobId=' + show.jobId"></iframe>
  </el-dialog>
</div>

<script type="text/javascript">
	var log_flag = false;
	<#if shiro.hasPermission("sys:schedule:log")>
	log_flag = true;
	</#if>
</script>
<script src="${request.contextPath}/statics/js/job/schedule.js?_${.now?string["yyyyMMddhhmmSSsss"]}"></script>
</body>
</html>