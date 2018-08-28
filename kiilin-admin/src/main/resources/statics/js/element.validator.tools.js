/**
 * element 自定义正则校验
 * @param rule
 * @param value
 * @param callback
 */
var validator = {
  /**
   * 正则校验
   * @param rule
   * @param value
   * @param callback
   */
  regex: (rule, value, callback) => {
    if (!rule.regex.test(value)) {
      callback(new Error());
    } else {
      callback();
    }
  },
  phone: (rule, value, callback) => {
    rule.regex = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
    this.regex(rule, value, callback);
  },
}