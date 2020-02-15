
// var WxApiRoot = 'https://wechat.kiwi1.cn/wx/';
var WxApiRoot = 'http://localhost:8080/wx/';

module.exports = {
  ListURL: WxApiRoot + 'getUserList', //聊天列表
  GetFMsgs: WxApiRoot + 'getFriendMsgs', //聊天列表

};