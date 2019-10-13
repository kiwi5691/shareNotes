var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();

Page({
  data: {
    userInfo: {
      nickName: '点击登录',
      avatarUrl: '/static/images/person.png'
    },
    msgNumber:0,
    hasLogin: false
  },
  goMsg() {
    wx.navigateTo({
      url: "/pages/ucenter/msg/msg"
    })
  },
  goHelp() {
    wx.navigateTo({
      url: "/pages/ucenter/help/help"
    })
  },
  goAbout() {
    wx.navigateTo({
      url: "/pages/ucenter/about/about"
    })
  },
  goIssue() {
    wx.navigateTo({
      url: "/pages/ucenter/issue/issue"
    })
  },
  goLeaning() {
    wx.navigateTo({
      url: "/pages/ucenter/leaning/leaning"
    })
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
  },
  onReady: function () {

  },
  onShow: function () {

    //获取用户的登录信息
    if (app.globalData.hasLogin) {
      let userInfo = wx.getStorageSync('userInfo');
      this.setData({
        userInfo: userInfo,
        hasLogin: true
      });
     this.getMsgNumber();
    }

  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭
  },
  getMsgNumber: function () {
    let that = this;
    util.request(api.GetMsgNumber).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          msgNumber: res.data.sysMsgsnum,
        });
        wx.showTabBarRedDot({
          index: 2,

          success: res => { console.log(res) },

          fail: res => { console.error }
        })
      }
    });
  },
  wxLogin: function (e) {
    wx.vibrateShort();
    if (e.detail.userInfo == undefined) {
      app.globalData.hasLogin = false;
      util.showErrorToast('微信登录失败');
      return;
    }

    user.checkLogin().catch(() => {

      user.loginByWeixin(e.detail.userInfo).then(res => {
        app.globalData.hasLogin = true;

        this.onShow();
      }).catch((err) => {
        app.globalData.hasLogin = false;
        util.showErrorToast('微信登录失败');
      });

    });
  },
 
})