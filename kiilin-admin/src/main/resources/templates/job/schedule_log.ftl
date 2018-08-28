<#-- @ftlroot ".." -->
<!DOCTYPE html>
<html>
<head>
<title>定时任务日志</title>
<#include "/header.ftl">
</head>
<body>
<div id="rrapp">
  <table id="jobLogs"></table>
</div>

<script type="text/javascript">
  var ajaxData = null;
  if('${jobId}' != ""){
    var ajaxData = {jobId: '=${jobId}'};
	}
</script>
<script src="${request.contextPath}/statics/js/job/schedule_log.js?_${.now?string["yyyyMMddhhmmSSsss"]}"></script>
</body>
</html>