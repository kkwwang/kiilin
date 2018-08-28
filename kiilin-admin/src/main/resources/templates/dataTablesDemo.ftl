<#-- @ftlroot "./" -->
<!DOCTYPE html>
<html lang="en">
<head>
<#include "/header.ftl">


</head>
<body>

<table id="table">
  <thead>
  <tr>
    <th>type</th>
    <th>value</th>
    <th>label</th>
    <th>sort</th>
  </tr>
  </thead>
  <tbody></tbody>
</table>

<#if shiro.hasPermission("system:manager")>
system:manager
</#if>


<#if shiro.hasPermission("system:test")>
system:test
</#if>

<script>
  var dataTable = $("#table").tabletools(
    {
      processing: true,
      // 开启服务器模式
      serverSide: true,
      // 自定义属性
      ajaxUrl: baseUrl + "/demos/test",
      // 自定义属性
      ajaxData: {type: "menu_ty", sort: 1, id: 1},
      autoWidth: false,
      columns: [
        {
          data: "type",
          name: "type",
          width: "3%"
        },
        {
          data: "value",
          name: "value",
          width: "10%"
        },
        {
          data: "label",
          name: "label",
          width: "10%"
        },
        {
          data: "sort",
          name: "sort",
          width: "10%"
        }
      ]
    });

</script>

</body>
</html>