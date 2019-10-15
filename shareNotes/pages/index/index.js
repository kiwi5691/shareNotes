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
    hiddenAlertPu: true,
    showRigh2: false,
    msg:'',
  },
  handleChange({ detail }) {
    this.setData({
      current: detail.key
    });
  },
  toLogin:function(){
    wx.vibrateShort();
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
      wx.navigateTo({
        url: '../content/adddetial/adddetial',
      });
    } else {
      this.setData({
        visible2: true
      });
    }
  },
  goOplog: function () {
   
      wx.navigateTo({
        url: '../content/oplog/oplog',
      });
    
  },
  toggleRight2() {
    wx.vibrateShort();
    this.setData({
      showRight2: !this.data.showRight2
    });
  },
  handleClose2() {
    this.setData({
      visible2: false
    });
  },
  getPublicMain: function () {
    let that = this;
    var tempPublic = wx.getStorageSync('publicCate')
    that.setData({
      publicCate: tempPublic,
    });
    if (this.data.publicCate.length==0){
    util.request(api.GetPublicCategory).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          publicCate: res.data.publicCate,
        });
        wx.setStorageSync('publicCate', res.data.publicCate)
      } else{
        that.setData({
          msg:res.errmsg,
          hiddenAlertPu: !that.data.hiddenAlertPu
        })
      }
    });
    }
  },
  getPrivateMain: function () {
    let that = this;
    var tempPrivate= wx.getStorageSync('privateCate')
    that.setData({
      privateCate: tempPrivate,
    });
    if (this.data.privateCate.length == 0) {
    util.request(api.GetPrivateCategory).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          privateCate: res.data.privateCate,
        });
        wx.setStorageSync('privateCate', res.data.privateCate)
      } else{
        that.setData({
          msg: res.errmsg,
          hiddenAlertPr: !that.data.hiddenAlertPr
        })
      }
    });
    }
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
    this.setData({
      showRight2: false
    });

    this.getPublicMain();
    this.getPrivateMain();
  },
})

