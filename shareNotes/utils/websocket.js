// 聊天室
var url = 'ws://localhost:8088/ws';
// var utils = require('./util.js');
var app = getApp();

//ChatMsg 聊天模型对象
function ChatMsg(senderId, receiverId, msg, msgId){
  var ChatMsg={
    senderId : senderId,
    receiverId : receiverId,
     msg : msg,
    msgId : msgId
  };
  return ChatMsg;
}

function DataContent(action, chatMsg, extand){
  var DataContent={
    // dataContent:{
    action : action,
    chatMsg : chatMsg,
    extand : extand
    // }
  };
  return DataContent;
}
function keepalive(){
  var dataContent = DataContent(app.globalData.KEEPALIVE, null, null);
  console.log("keepalive");
  send(dataContent);

}
//todo 每次重连后获取服务器的未签收消息
function fetchUnReadMsg(){

}
function connect( func) {
  // wx.connectSocket({
  //   url: url,
  //   header: { 'content-type': 'application/json' },
  //   success: function () {
  //     console.log('信道连接成功~');
  //   },
  //   fail: function () {
  //     console.log('信道连接失败~')
  //   }
  // })
  // wx.onSocketOpen(function (res) {
  //   wx.showToast({
  //     title: '信道已开通~',
  //     icon: "success",
  //     duration: 2000
  //   })
  //   //心跳
  //   setInterval("keepalive()", 10000);
  //   //接受服务器消息
  //
  // });
  // wx.onSocketMessage(func);//func回调可以拿到服务器返回的数据
  // wx.onSocketError(function (res) {
  //   wx.showToast({
  //     title: '信道连接失败，请检查！',
  //     icon: "none",
  //     duration: 2000
  //   })
  // })
}
//发送消息
function send(dataContent) {
  wx.sendSocketMessage({
    data: dataContent,
  });
}
module.exports = {
  connect: connect,
  send: send,
  keepalive:keepalive,
  DataContent: DataContent,
  ChatMsg:ChatMsg,
  url:url,
  fetchUnReadMsg:fetchUnReadMsg,
}