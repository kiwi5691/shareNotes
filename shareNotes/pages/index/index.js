var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');
var app = getApp();

Page({
  data: {
    publicCate:[],
    privateCate: [],
    current: 'tab1',
    visible2: false,
    hiddenAlertPr:true,
    hasLogin: false,
    hiddenAlertPu: true
  },
  handleChange({ detail }) {
    this.setData({
      current: detail.key
    });
  },
  toLogin:function(){
    this.setData({
      visible2: false
    });
    wx.switchTab({

      url: "/pages/ucenter/index/index",

      success: function (res) { },

      fail: function (res) { },

      complete: function (res) { },

    })
  },
  adddetial: function () {

    if (this.data.hasLogin) {
      try {
        wx.setStorageSync('tab', 0);
      } catch (e) {

      }
      wx.navigateTo({
        url: '../content/adddetial/adddetial',

      });
    } else {

      this.setData({
        visible2: true
      });
    }
  },
  handleClose2() {
    this.setData({
      visible2: false
    });
  },

  getPublicMain: function () {
    let that = this;
    util.request(api.GetPublicCategory).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          publicCate: res.data.publicCate,
        });
      
      } else if(res.errno === 601){
        that.setData({
          hiddenAlertPu: !that.data.hiddenAlertPu
        })
      }
    });
  },
  getPrivateMain: function () {
    let that = this;
    util.request(api.GetPrivateCategory).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          privateCate: res.data.privateCate,
        });
      } else if(res.errno === 601){
        that.setData({
          hiddenAlertPr: !that.data.hiddenAlertPr
        })
      }
    });
  },
  onLoad: function (options) {

    this.getPublicMain();
    this.getPrivateMain();

  },
  onPullDownRefresh() {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getPublicMain();
    this.getPrivateMain();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },
  onShow: function () {
    if (app.globalData.hasLogin) {
      let userInfo = wx.getStorageSync('userInfo');
      this.setData({
        userInfo: userInfo,
        hasLogin: true
      });
    }

    this.getPublicMain();
    this.getPrivateMain();

  },
})

