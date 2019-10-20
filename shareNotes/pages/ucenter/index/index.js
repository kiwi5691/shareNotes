const { $Message } = require('../../../dist/base/index');
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
    actions5: [
      {
        name: '取消'
      },
      {
        name: '确定',
        color: '#ed3f14'
      }
    ],
    visible5: false,
    hasLogin: false
  },
  goMsg() {
    wx.navigateTo({
      url: "/pages/ucenter/msg/msg"
    })
  },
  goMy() {
    wx.navigateTo({
      url: "/pages/ucenter/my/my"
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
  handleOpen5() {
    this.setData({
      visible5: true
    });
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
  exitLogin({ detail }) {
    if (detail.index === 0) {
      this.setData({
        visible5: false
      });
    } else {
        this.setData({
          visible5: false
        });
      util.request(api.AuthLogout, {}, 'POST');
      app.globalData.hasLogin = false;
      wx.removeStorageSync('token');
      wx.removeStorageSync('userInfo');
      $Message({
        content: '登出成功！',
        type: 'success'
      });
      wx.reLaunch({
        url: '../../../pages/index/index'
      });
        
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
        if (res.data.sysMsgsnum){
        wx.showTabBarRedDot({
          index: 2,
        })
      }
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