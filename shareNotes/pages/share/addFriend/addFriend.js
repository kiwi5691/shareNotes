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