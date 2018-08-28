;
/**
 * 扩展RegExp的factory方法
 * @param args name为必须，其他为占位符参数。 占位符使用@标识
 * @version v1.0.1
 * @author wangkai
 * @returns {*}
 */
Object.assign(RegExp, {

  /**
   * 手机号
   */
  mobile: "^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$",


  /**
   * 正则获取
   * @param args
   * @returns {*}
   */
  factory(args) {
    if (typeof args === "string") {
      return new RegExp(this[args]);
    }

    var result = this[args.name];
    if (!result) {
      $.error('RegExp ' + args.name + ' does not exist on RegExp.factory');
    }

    var options = {
      // 占位符前缀
      prefix: "@",
      // 占位符后缀
      suffix: "@",
      // 空值替换
      nullText: ""
    };
    var reg = new RegExp("\\@([^\\[\\]]*?)\\@", 'igm'); //i g m是指分别用于指定区分大小写的匹配、全局匹配和多行匹配。
    var target = result.replace(reg, function (node, key) {
      return args[key] || options.nullText;
    });

    return new RegExp(target);
  }
});

