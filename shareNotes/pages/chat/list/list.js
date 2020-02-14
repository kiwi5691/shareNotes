// pages/content/adddetial/adddetial.js
const { $Message } = require('../../../dist/base/index');

var util = require('../../../utils/util.js');
var api = require('../../../config/chatApi.js');
var app = getApp();

// pages/chat/list/list.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    hiddenAlertPu: true,
    groupList: [],
    hasLogin: false,
    userIdT:0,
  },
  toLogin: function () {
        wx.vibrateShort();
        wx.switchTab({
          url: "/pages/ucenter/index/index",
          success: function (res) { },
          fail: function (res) { },
          complete: function (res) { },

        })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (this.data.hasLogin) {
      this.setData({
        userIdT:  wx.getStorageSync('userId')
      });

      console.log("id" + this.data.userIdT);
      this.getGroupList(this.data.userIdT);
    }
  },

  getGroupList: function (id) {
    let that = this;
    util.request(api.ListURL, {
      userId: id,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        that.setData({
          groupList: res.data.groupList,
        });
      }else{
        that.setData({
          hiddenAlertPu: !that.data.hiddenAlertPu
        })
      }
    });
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

    if (app.globalData.hasLogin) {
      this.setData({
        userIdT:  wx.getStorageSync('userId')
      });
      console.log("id" + this.data.userIdT);
      this.getGroupList(this.data.userIdT);
    }
    this.setData({
      hasLogin: app.globalData.hasLogin
    });
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

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})