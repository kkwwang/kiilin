
;(function ($) {
  /**
   * 自定义
   * @param method
   * @returns {*}
   */
  $.fn.tabletools = function (method) {
    //你自己的插件代码
    if (methods[method]) {
      return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
    } else if (typeof method === 'object' || !method) {
      return methods.init.apply(this, arguments);
    } else {
      $.error('Method ' + method + ' does not exist on jQuery.tabletools');
    }
  };


  /**
   * 函数
   * @type {{init: function(*=): *, getDefData: function(*): {lengthChange: boolean, searching: boolean, ordering: boolean, info: boolean, autoWidth: boolean, serverSide: boolean, processing: boolean, ajax: {url: *, dataSrc: string, type: string, contentType: string, data: function(*=): string}, dom: string, language: {lengthMenu: string, zeroRecords: string, info: string, infoEmpty: string, infoFiltered: string, processing: string, search: string, paginate: {first: string, previous: string, next: string, last: string}}, lengthMenu: number[]}, createHeader: createHeader}}
   */
  var methods = {
    init: function (options) {
      var _this = this;
      // 补充class
      _this.addClass('table table-striped table-bordered');

      // 创建表头
      methods.createHeader(_this, options);
      // 获取默认值
      var defoptions = methods.getDefData(options);
      // 合并配置
      options = $.extend(defoptions, options);
      // 创建
      var table = _this.DataTable(options);


      /**
       * 选中效果
       */
      if (options.select) {
        _this.find('tbody').on('click', 'td', function () {
          if ($(this).hasClass("none_select")) {
            return;
          }
          var _parent = $(this).parent()[0];
          // dom的id
          var id = _parent.id;
          var index = $.inArray(id, defoptions.selected);
          if (index === -1) {
            defoptions.selected.push(id);
          } else {
            defoptions.selected.splice(index, 1);
          }

          $(_parent).toggleClass('selected');
        });
      }



      /**
       * 获取选中的数据(全部)
       * @returns {*}
       */
      table.getSelectDatas = function () {
        return table.rows('.selected').data();
      }


      /**
       * 获取选中的数据
       * @returns {*}
       */
      table.getSelectData = function () {
        return table.row('.selected').data();
      }
      /**
       * 获取选中数据的ids
       * @returns {Array}
       */
      table.getSelectDataIds = function () {
        var sDatas = table.rows('.selected').data();

        var ids = [];
        $.each(sDatas, function (index, _sData) {
          ids.push(_sData[options.dataId || "id"]);
        });
        return ids.length == 0 ? null : ids;
      }

      /**
       * 获取选中数据的id
       * @returns {*}
       */
      table.getSelectDataId = function () {
        var sData = table.row('.selected').data();
        if(sData){
          return sData[options.dataId || "id"];
        }
      }


      /**
       * 刷新数据表
       * @param queryData
       */
      table.ajaxReload = function (queryData) {
        table.settings()[0].ajax.data = function (_data) {
          return JSON.stringify($.extend(_data, queryData));
        };
        table.ajax.reload();
      }

      return table;
    },

    getDefData: function (options) {
      /**
       * 默认值
       * @type {{lengthChange: boolean, searching: boolean, ordering: boolean, info: boolean, autoWidth: boolean, serverSide: boolean, processing: boolean, ajax: {url: *, dataSrc: string, type: string, contentType: string, data: function(*=): string}, dom: string, language: {lengthMenu: string, zeroRecords: string, info: string, infoEmpty: string, infoFiltered: string, processing: string, search: string, paginate: {first: string, previous: string, next: string, last: string}}, lengthMenu: number[]}}
       */
      var defoptions = {
        lengthChange: true,//是否允许用户自定义显示数量
        searching: false,//本地搜索
        ordering: true, //排序功能
        info: true,//页脚信息
        autoWidth: true,//自动宽度
        select: true,//开启选择
        // 开启服务器模式
        serverSide: true,
        // 数据id
        dataId: "id",
        // 保存选择
        selected: [],
        // 是否显示处理状态(排序的时候，数据很多耗费时间长的话，也会显示这个)
        processing: true,
        rowId: "dt_",
        // 异步请求
        ajax: {
          url: options.ajaxUrl,
          dataSrc: "data",
          // post方式
          type: "POST",
          // 请求头
          contentType: "application/json",
          // 请求数据格式转换
          data: function (_data) {
            return JSON.stringify($.extend(_data, options.ajaxData));
          }
        },

        dom: 'rt<"#pBottom"p>',
        //汉化
        language: {
          lengthMenu: "每页显示 _MENU_ 条记录",
          zeroRecords: "暂无数据",
          info: "当前显示 _START_ 到 _END_ 条，共 _TOTAL_条记录",
          infoEmpty: "找不到相关数据",
          infoFiltered: "数据表中共为 _MAX_ 条记录)",
          processing: "正在加载中...",
          search: "搜索",
          paginate: {
            first: "第一页",
            previous: " 上一页 ",
            next: " 下一页 ",
            last: " 最后一页 ",

          },
        },
        lengthMenu: [10, 25, 50, 100],
        createdRow: function (row, data, displayNum, setting) {
          var id = "dt_" + data[defoptions.dataId];
          $(row).attr("id", "dt_" + data[defoptions.dataId]);
          if ($.inArray(id, defoptions.selected) !== -1) {
            $(row).addClass('selected');
          }
        }
      };

      return defoptions;
    },

    /**
     * 创建表头
     * @param _this
     * @param options
     */
    createHeader: function (_this, options) {

      var $table = _this;
      var $thead = $table.find("thead");
      if ($thead.length == 0) {
        $table.append("<thead><tr></tr></thead>");
        $thead = $table.find("thead");
      }

      var $tr = $thead.find("tr");
      var $tds = $tr.find("td");
      // 创建表头
      $.each(options.columns, function (index, colum) {
        if (colum.label) {
          colum.class = colum.class || "";
          var td = $tds.get(index);
          var $td;
          if (td) {
            $td = $(td);
            $td.html(colum.label);
            $td.addClass(colum.class);
          } else {
            $td = $("<td class='" + colum.class + "'>" + colum.label + "</td>")
            $tr.append($td);
          }
        }
      });
    }
  }

}($));

