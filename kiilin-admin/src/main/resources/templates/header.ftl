<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">


    <#-- jquery v2.1.4 -->
    <#--<script type="text/javascript" src="${request.contextPath}/statics/plugins/jquery-2.1.4/jquery-2.1.4.js"></script>-->
    <script type="text/javascript" src="${request.contextPath}/statics/plugins/jquery-2.1.4/jquery-2.1.4.min.js"></script>

    <#-- layui -->
    <script type="text/javascript" src="${request.contextPath}/statics/plugins/layer/layer.js"></script>

    <#-- datatables 打包 包含DataTables文件夹下除select外所有模块 -->
    <#--<script type="text/javascript" src="${request.contextPath}/statics/plugins/DataTables/datatables.js"></script>-->
    <script type="text/javascript" src="${request.contextPath}/statics/plugins/DataTables/datatables.min.js"></script>
    <#--<link rel="stylesheet" href="${request.contextPath}/statics/plugins/DataTables/datatables.css">-->
    <link rel="stylesheet" href="${request.contextPath}/statics/plugins/DataTables/datatables.min.css">

    <#-- datatables select 模块样式-->
    <#--<link rel="stylesheet" href="${request.contextPath}/statics/plugins/DataTables/Select-1.2.6/css/select.bootstrap4.css">-->


    <#-- vue v2.5.16 -->
    <#--<script type="text/javascript" src="${request.contextPath}/statics/plugins/vue/vue.js"></script>-->
    <script type="text/javascript" src="${request.contextPath}/statics/plugins/vue/vue.min.js"></script>

    <#-- element-ui v2.4.5 -->
    <script type="text/javascript" src="${request.contextPath}/statics/plugins/element-ui/js/index.js"></script>
    <link rel="stylesheet" href="${request.contextPath}/statics/plugins/element-ui/css/index.css">
    <#--<link rel="stylesheet" href="//unpkg.com/element-ui@2.4.5/lib/theme-chalk/index.css">-->

    <script type="text/javascript" src="${request.contextPath}/statics/js/datatableTools.js?_${.now?string["yyyyMMddhhmmSSsss"]}"></script>
    <script type="text/javascript" src="${request.contextPath}/statics/js/commons.js?_${.now?string["yyyyMMddhhmmSSsss"]}"></script>
    <script type="text/javascript" src="${request.contextPath}/statics/js/element.validator.tools.js?_${.now?string["yyyyMMddhhmmSSsss"]}"></script>

    <script type="text/javascript" src="${request.contextPath}/statics/js/template.js?_${.now?string["yyyyMMddhhmmSSsss"]}"></script>



    <#-- baseUrl 供js调用 -->
    <script type="application/javascript">
      var baseUrl = "${request.contextPath}";
      var baseURL = "${request.contextPath}";
    </script>

    <style type="text/css">

      body{
        overflow-x: hidden;
      }

      .el-container {
        padding: 5px;
      }

      .el-header,.el-main,.el-footer,.el-container .el-container{
        padding: 0px;
      }

      #pBottom {
        padding-right: 4px;
      }

      .el-button-group.template .el-button.el-button--text{
        padding: inherit;
        line-height: inherit;
      }

      [class^=el-icon-]{
        margin-right: 3px;
      }

      .el-button-group.template .el-button.el-button--text:not(:first-child){
        margin-left: 5px;
      }

      .table.table-striped.table-bordered .td_center{
        text-align: center;
      }

      .el-row {
        margin-bottom: 10px;
      }

      .el-select:not(.el-select--mini):not(.el-select--small):not(.el-select--medium){
        width: 100%;
      }

      .el-date-editor--daterange:not(.el-range-editor--large):not(.el-range-editor--small):not(.el-range-editor--mini){
        width: 100%;
      }

      .table th, .table td{
        padding-left: 5px;
        padding-right: 5px;
        text-align: center;
        vertical-align: middle;
      }


      input::-webkit-outer-spin-button,
      input::-webkit-inner-spin-button {
        -webkit-appearance: none;
      }
      input[type="number"]{
        -moz-appearance: textfield;
      }
    </style>
