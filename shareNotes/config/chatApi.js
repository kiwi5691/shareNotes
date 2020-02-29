
// var WxApiRoot = 'https://wechat.kiwi1.cn/msg/wx/';
var WxApiRoot = 'http://localhost:8085/msg/wx/';

module.exports = {
  ListURL: WxApiRoot + 'getUserList', //聊天列表
  GetFMsgs: WxApiRoot + 'getFriendMsgs', //聊天列表
  SelUnReadMsg: WxApiRoot + 'selfUnReadMsg', //聊天列表

};