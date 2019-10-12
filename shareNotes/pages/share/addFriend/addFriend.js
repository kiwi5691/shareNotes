// pages/addFriend/addFriend.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    text1: '',
    text2: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  addFriend: function (id) {
    var friendId = id;
    let that = this;
    util.request(api.AddFriend + friendId).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          text1: "添加好友中",
          text2: "正在加速处理~"
        })
        wx.switchTab({
          url: "../../../pages/share/index/index"
        })
      } else  {
        that.setData({
          text1: "您尚未登录",
          text2: "请先登录"
        })
        wx.switchTab({
          url: "../../../pages/share/index/index"
        })
      }
    });
  },
  onLoad: function (options) {
    var id = options.id;
    this.addFriend(id);
  },
  //上传
  uploadFileTap: function () {
    var _this = this;
    wx.chooseImage({
      count: 1, // 默认9  
      sizeType: ['original'], // 可以指定是原图还是压缩图，默认二者都有  
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有  
      success: function (res) {
        console.log(res);
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths;
        _this.setData({
          paths: tempFilePaths
        })
        console.log(_this)
      },
      fail: function (res) {
        // fail
      },
      complete: function (res) {
        // complete
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  }

})