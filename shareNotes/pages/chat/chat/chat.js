// pages/chat/chat.js
const app = getApp();
const { $Message } = require('../../../dist/base/index');
var api = require('../../../config/chatApi.js');
var BaseApi = require('../../../config/api.js');
var websocket = require('../../../utils/websocket.js');
var utils = require('../../../utils/util.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    scrollHeight:0,
    noeMsg: true,
    newslist: [],
    toView: '' ,
    files: [],
    userInfo: {},
    scrollTop: 0,
    increase: false,//图片添加区域隐藏
    aniStyle: true,//动画效果
    message: "",
    previewImgList: [],
    nickName:'',
    fid:'',
    userId:'',
    avatar:'',
    keepalive:null,//keepalive定时器
    selfUnReadMsg:null,//selfUnReadMsg定时器
    fetchReadMsg:null,//fetchReadMsg定时器
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
          toView: 'msg-' + (res.data.userMsgs.length - 1),
          noeMsg: true
        });
        wx.setStorageSync('userMsgs:fid' + fid+":uid:"+userId, res.data.userMsgs);
      }else{
        that.setData({
          noeMsg: false
        })
      }
    });
  
  },
  onShow: function () {
    this.getMsgs(this.data.userId, this.data.fid);
    var fid =this.data.fid;
    var userId =this.data.userId;
    wx.connectSocket({
      url: 'ws://localhost:8088/ws',
      header: { 'content-type': 'application/json' },
      success: function () {
        // console.log('信道连接成功~');
      },
      fail: function () {
        console.log('信道连接失败，请联系开发者')
      }
    })
    wx.onSocketOpen(() => {
      wx.showToast({
        title: '信道已开通~',
        icon: "success",
        duration: 2000
      })
      // 构建ChatMsg
      var chatMsg = new websocket.ChatMsg(this.data.userId, null, null, null);
      // 构建DataContent
      var contentDC = websocket.DataContent(app.globalData.CONNECT, chatMsg, null);
      // 发送websocket
      websocket.send(JSON.stringify(contentDC));
      var _this =this;
      _this.data.fetchReadMsg = setInterval(function () {
        var list = [];
        list = wx.getStorageSync('userMsgs:fid' + _this.data.fid+":uid:"+_this.data.userId);

        var thisFid =_this.data.fid;
        var unSignId =[];
        var flag=0;
        for (var i = 0 ; i < list.length ; i ++) {
          if(list[i].sendId == thisFid&&list[i].isSign=='0'){
            unSignId[flag]=list[i].id;
            list[i].isSign=1;
            flag++;
          }
        }
        // console.log("unSignId"+JSON.stringify(unSignId)+"flag"+flag);
        if(flag!=0&&unSignId[0]!=''){
          //这里全部发送已读过去
          var unSignIdArray=unSignId.join(",");
          websocket.signMsgList(unSignIdArray);
        }
        _this.setData({
          newslist: list,
          noeMsg: true,
          toView: 'msg-' + (list.length - 1)
        });
        wx.setStorageSync('userMsgs:fid' + _this.data.fid+":uid:"+_this.data.userId, list);
      },10000);

      var _this =this;
      _this.data.selfUnReadMsg = setInterval(function () {
        var list = [];
        list = wx.getStorageSync('userMsgs:fid' + _this.data.fid+":uid:"+_this.data.userId);

        var thisUserId =_this.data.userId;
        var flag=0;
        for (var i = 0 ; i < list.length ; i ++) {
          if(list[i].sendId == thisUserId&&list[i].isSign=='0'){
            flag=1;
            break;
          }
        }
        if(flag==1){
        utils.request(api.GetFMsgs, {
          userId: _this.data.userId,
          fid: _this.data.fid,
        }, 'POST').then(function (res) {
          if (res.errno === 0) {

            _this.setData({
              newslist: res.data.userMsgs,
            });
            wx.setStorageSync('userMsgs:fid' + _this.data.fid+":uid:"+_this.data.userId, res.data.userMsgs);
          }

        });
        }
      },10000);
      //心跳
      //将计时器赋值给setInter
      var _this=this;
      _this.data.keepalive =  setInterval(function () {
      var dataContent = websocket.DataContent(app.globalData.KEEPALIVE, null, null);
        websocket.send(JSON.stringify(dataContent));
      }, 10000);
      //接受服务器消息
    });
    var _this = this;
    wx.onSocketMessage(function (res) {

      if(res.data!=null){
        var list = [];

        list = wx.getStorageSync('userMsgs:fid' + fid+":uid:"+userId);
        var temp =  JSON.parse(res.data);
        var weChatMsg ={
          id:temp.chatMsg.msgId,
          sendId : temp.chatMsg.senderId,
          acceptId : temp.chatMsg.receiverId,
          msg : temp.chatMsg.msg,
          type: temp.extand,
          isSign: 1,
        };
        list.push(weChatMsg);

        _this.setData({
          newslist: list,
          toView: 'msg-' + (list.length - 1)
        });
        wx.setStorageSync('userMsgs:fid' + fid+":uid:"+userId, _this.data.newslist);

        // 构建ChatMsg
        // var chatMsg = new websocket.ChatMsg(weChatMsg.senderId, weChatMsg.receiverId, weChatMsg.msg, weChatMsg.id);
        // 构建DataContent
        var signedDC = websocket.DataContent(app.globalData.SIGNED, null, weChatMsg.id);
        // 发送websocket
        websocket.send(JSON.stringify(signedDC));

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

    //卸载定时器
    //todo 没卸载掉，明天测试
    clearInterval(this.data.keepalive);
    clearInterval(this.data.selfUnReadMsg);
    clearInterval(this.data.fetchReadMsg);
    wx.closeSocket();

    wx.showToast({
      title: '连接已断开~',
      icon: "none",
      duration: 2000
    })
  },
  startSetInter: function(){
    var that = this;
    //将计时器赋值给setInter
    that.data.setInter = setInterval(
        function () {
          var numVal = that.data.num + 1;
          that.setData({ num: numVal });
          console.log('setInterval==' + that.data.num);
        }
        , 2000);
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
      //构造消息
      var chatMsg = new websocket.ChatMsg(this.data.userId, this.data.fid, this.data.message, null);
      var dataContent = new websocket.DataContent(app.globalData.CHAT, chatMsg, null);
      // console.log("dataContent"+JSON.stringify(dataContent));
      //发送
      websocket.send(JSON.stringify(dataContent));
      //重新构造数组
      var list = [];
      list = wx.getStorageSync('userMsgs:fid' + this.data.fid+":uid:"+this.data.userId);
      var weChatMsg ={
        sendId : this.data.userId,
        acceptId : this.data.fid,
        msg : this.data.message,
        isSign : 0,
        type:'text'
      };
      list.push(weChatMsg);
      this.setData({
        newslist: list,
        noeMsg: true,
        toView: 'msg-' + (list.length - 1)
      });
      wx.setStorageSync('userMsgs:fid' + this.data.fid+":uid:"+this.data.userId, this.data.newslist);

      // this.bottom();
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
      sizeType: ['original', 'compressed'],
      sourceType: ['album', 'camera'],
      success: function (res) {
        that.setData({
          files: that.data.files.concat(res.tempFilePaths)
        });
        that.upload(res);
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
      // console.log(rect.height)
      that.setData({
        scrollTop: rect.height + 'px'
      });
    }).exec();
  },
  upload: function (res) {
    var that = this;
    const uploadTask = wx.uploadFile({
      url: BaseApi.StorageUpload,
      filePath: res.tempFilePaths[0],
      name: 'file',
      success: function (res) {
        var _res = JSON.parse(res.data);
        if (_res.errno === 0) {
            that.setData({
              increase: false
            })
          console.log("_res.data.url"+_res.data.url);
          var chatMsg = new websocket.ChatMsg(that.data.userId, that.data.fid, _res.data.url, null);
          var dataContent = new websocket.DataContent(app.globalData.CHAT, chatMsg, "images");
          // console.log("dataContent"+JSON.stringify(dataContent));
          //发送
          websocket.send(JSON.stringify(dataContent));
          //重新构造数组
          var list = [];
          list = wx.getStorageSync('userMsgs:fid' + that.data.fid+":uid:"+that.data.userId);
          var weChatMsg ={
            sendId : that.data.userId,
            acceptId : that.data.fid,
            msg : _res.data.url,
            isSign : 0,
            type:'images'
          };
          list.push(weChatMsg);
          that.setData({
            newslist: list,
            noeMsg: true,
            toView: 'msg-' + (list.length - 1)
          });
          wx.setStorageSync('userMsgs:fid' + that.data.fid+":uid:"+that.data.userId, that.data.newslist);

        } else {
          $Message({
            content: _res.errmsg,
            type: 'error'
          });
        }

      },
      fail: function (e) {
        wx.showModal({
          title: '错误',
          content: '请联系开发者',
          showCancel: false
        })
      },
    })
    uploadTask.onProgressUpdate((res) => {
      console.log('上传进度', res.progress)
      console.log('已经上传的数据长度', res.totalBytesSent)
      console.log('预期需要上传的数据总长度', res.totalBytesExpectedToSend)
    })

  },
})