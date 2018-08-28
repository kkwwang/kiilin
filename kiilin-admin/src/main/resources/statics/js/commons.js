// 重写alert
alert = function (msg, callback) {
  if (callback && typeof(callback) === "function") {
    top.layer.alert(msg, {closeBtn: 0}, function (index) {
      top.layer.close(index);
      callback("ok");
    });
  } else {
    top.layer.msg(msg);
  }
}

// 重写confirm式样框
confirm = function (msg, callback, cancelCallback) {
  top.layer.confirm(msg, {btn: ['确定', '取消'], closeBtn: 0}, function (index) {//确定事件
    if (typeof(callback) === "function") {
      top.layer.close(index);
      callback("ok");
    }
  }, function (index) {
    if (typeof(callback) === "function") {
      top.layer.close(index);
      cancelCallback("ok");
    }
  });
}




