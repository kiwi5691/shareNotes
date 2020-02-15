// pages/chat/chat.js
const app = getApp();
var api = require('../../../config/chatApi.js');
var websocket = require('../../../utils/websocket.js');
var utils = require('../../../utils/util.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    scrollHeight:0,
    isMsgs: true,
    newslist: [],
    toView: '' ,
    userInfo: {},
    scrollTop: 0,
    increase: false,//图片添加区域隐藏
    aniStyle: true,//动画效果
    message: "",
    previewImgList: [],
    nickName:'',
    fid:'',
    userId:'',
    avatar:''
  },
  /**
   * 生命周期函数--监听页面加载
   */

  getMsgs: function (userId,fid) {
    let that = this;
    utils.request(api.GetFMsgs, {
      userId: userId,
      fid: fid,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
     
        that.setData({
          newslist: res.data.userMsgs,
          toView: 'msg-' + (res.data.userMsgs.length - 1)
         });
        wx.setStorageSync('userMsgs:fid' + fid+":uid:"+userId, res.data.userMsgs);
      }else{
        that.setData({
          isMsgs: !that.data.isMsgs
        })
      }
    });
  
  },
  onShow: function () {
    this.getMsgs(this.data.userId, this.data.fid);
    wx.connectSocket({
      url: 'ws://localhost:8088/ws',
      header: { 'content-type': 'application/json' },
      success: function () {
        console.log('信道连接成功~');
      },
      fail: function () {
        console.log('信道连接失败~')
      }
    })
    wx.onSocketOpen(() => {
      wx.showToast({
        title: '信道已开通~',
        icon: "success",
        duration: 2000
      })
      //心跳
      var that = this;
      //将计时器赋值给setInter
      // var keepalive =  setInterval(function () {
      //todo 心跳包发送成功，但是需要设置定时器
      var dataContent = websocket.DataContent(app.globalData.KEEPALIVE, null, null);
        console.log("keepalive:"+JSON.stringify(dataContent));
        websocket.send(JSON.stringify(dataContent));
      // }, 10000);
      //接受服务器消息
    });
    wx.onSocketMessage(function (res) {
      if(res.data!=null){
        console.log(JSON.parse(res.data));
        var list = [];
        list = wx.getStorageSync('userMsgs:fid' + that.data.fid+":uid:"+that.data.userId);
        temp =  JSON.parse(res.data);
        var weChatMsg ={
          id:temp.chatMsg.msgId,
          sendId : temp.chatMsg.senderId,
          acceptId : temp.chatMsg.receiverId,
          msg : temp.chatMsg.msg,
          type: temp.extand,
        };
        list.push(weChatMsg);
        that.setData({
          newslist: list,
          toView: 'msg-' + (list.length - 1)
        })
      }
    });//func回调可以拿到服务器返回的数据
    wx.onSocketError(function (res) {
      wx.showToast({
        title: '信道连接失败，请检查！',
        icon: "none",
        duration: 2000
      })
    })
    },

  onLoad: function (options) {
    var that = this;
    that.setData({
      userId: options.userId,
      fid: options.fid,
      nickName: options.nickName,
      avatar:options.avatar,
      userInfo: wx.getStorageSync('userInfo')
    });
    wx.setNavigationBarTitle({
      title: options.nickName
    })
    that.getMsgs(that.data.userId,that.data.fid);

  },
  // 页面卸载
  onUnload() {
    wx.closeSocket();
    wx.showToast({
      title: '连接已断开~',
      icon: "none",
      duration: 2000
    })
  },
  //事件处理函数
  send: function () {
    var flag = this
    if (this.data.message.trim() == "") {
      wx.showToast({
        title: '消息不能为空哦~',
        icon: "none",
        duration: 2000
      })
    } else {
      setTimeout(function () {
        flag.setData({
          increase: false
        })
      }, 500)
      websocket.send('{ "content": "' + this.data.message + '", "date": "' + utils.formatTime(new Date()) + '","type":"text", "nickName": "' + this.data.userInfo.nickName + '", "avatarUrl": "' + this.data.userInfo.avatarUrl + '" }')
      this.bottom()
    }
  },
  //监听input值的改变
  bindChange(res) {
    this.setData({
      message: res.detail.value
    })
  },
  cleanInput() {
    //button会自动清空，所以不能再次清空而是应该给他设置目前的input值
    this.setData({
      message: this.data.message
    })
  },
  increase() {
    this.setData({
      increase: true,
      aniStyle: true
    })
  },
  //点击空白隐藏message下选框
  outbtn() {
    this.setData({
      increase: false,
      aniStyle: true
    })
  },
  chooseImage() {
    var that = this
    wx.chooseImage({
      count: 1, // 默认9
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths
        // console.log(tempFilePaths)
        wx.uploadFile({
          url: 'http://192.168.137.91/index/index/upload', //仅为示例，非真实的接口地址
          filePath: tempFilePaths[0],
          name: 'file',
          headers: {
            'Content-Type': 'form-data'
          },
          success: function (res) {
            if (res.data) {
              that.setData({
                increase: false
              })
              websocket.send('{"images":"' + res.data + '","date":"' + utils.formatTime(new Date()) + '","type":"image","nickName":"' + that.data.userInfo.nickName + '","avatarUrl":"' + that.data.userInfo.avatarUrl + '"}')
              that.bottom()
            }
          }
        })

      }
    })
  },
  //图片预览
  previewImg(e) {
    var that = this
    //必须给对应的wxml的image标签设置data-set=“图片路径”，否则接收不到
    var res = e.target.dataset.src
    var list = this.data.previewImgList //页面的图片集合数组

    //判断res在数组中是否存在，不存在则push到数组中, -1表示res不存在
    if (list.indexOf(res) == -1) {
      this.data.previewImgList.push(res)
    }
    wx.previewImage({
      current: res, // 当前显示图片的http链接
      urls: that.data.previewImgList // 需要预览的图片http链接列表
    })
    
  },
  //聊天消息始终显示最底端
  bottom: function () {
    var that = this;
    var query = wx.createSelectorQuery();
    query.select('#scrollMsg').boundingClientRect(function (rect) {
      console.log(rect.height)
      that.setData({
        scrollTop: rect.height + 'px'
      });
    }).exec(); 

    // var query = wx.createSelectorQuery()
    // query.select('#flag').boundingClientRect()
    // query.selectViewport().scrollOffset()
    // query.exec(function (res) {
    //   wx.pageScrollTo({
    //     scrollTop: res[0].bottom  // #the-id节点的下边界坐标  
    //   })
    //   res[1].scrollTop // 显示区域的竖直滚动位置  
    // })
  },  
})