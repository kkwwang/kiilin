/**
 * 利用模板及正则解决js拼接html代码问题
 * @version 1.0.1
 * @author wangkai
 *
 */
;(function ($) {

  /**
   * 自定义
   * @param method
   * @returns {*}
   */
  $.template = function (method) {
    //你自己的插件代码
    if (methods[method]) {
      return methods[method].apply(this, Array.prototype.slice.call(arguments, 1));
    } else if (typeof method === 'object' || !method) {
      return methods.init.apply(this, arguments);
    } else {
      $.error('Method ' + method + ' does not exist on jQuery.template');
    }
  };

  /**
   * 默认值
   * @type {{template: null, prefix: string, suffix: string, data: null, nullText: string}}
   */
  var defStep = {
    // 模板（jq对象）
    template: null,
    // 占位符前缀
    prefix: "[",
    // 占位符后缀
    suffix: "]",
    // 替换数据
    data: null,
    // 空值替换
    nullText: ""
  };

  /**
   * 函数
   * @type {{init: init, html: function(*=): *}}
   */
  var methods = {
    init: function (options) {

      // 合并配置
      options = $.extend(defStep, options);

      // 定义正则
      var reg = new RegExp("\\" + options.prefix + "([^\\[\\]]*?)\\" + options.suffix, 'igm'); //i g m是指分别用于指定区分大小写的匹配、全局匹配和多行匹配。

      // 拼接代码缓存
      var source = "";
      // 获取模板代码
      var html = options.template.html();
      // 遍历数据
      $.each(options.data, function () {
        // 当前遍历对象
        var _this = this;
        // 替换对象
        source += html.replace(reg, function (node, key) {
          return _this[key] || options.nullText;
        });
      });

      // 绘制表格
      options.target.html(source);
    },

    /**
     * 获取html
     * @param options
     * @returns {*}
     */
    html: function (options) {

      // 合并配置
      options = $.extend(defStep, options);

      // 定义正则
      var reg = new RegExp("\\" + options.prefix + "([^\\[\\]]*?)\\" + options.suffix, 'igm'); //i g m是指分别用于指定区分大小写的匹配、全局匹配和多行匹配。

      // 获取模板代码
      var html = options.template.html();

      var source = html.replace(reg, function (node, key) {
        return options.data[key] || options.nullText;
      });

      return source;

    }
  }


}($));