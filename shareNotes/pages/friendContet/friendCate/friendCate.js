//index.js
//获取应用实例
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();

Page({
  data: {
    id:'',
    failMes: '',
    publicCate: [],
    hiddenAlertPr: true,
    current: 'tab1',
  },
  handleChange({ detail }) {
    this.setData({
      current: detail.key
    });
  },
  getPublicMain: function () {
    let that = this;
    util.request(api.GetFriendPublicCategory + this.data.id).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          publicCate: res.data.publicCate,
        });
      } else if (res.errno === 601) {
        that.setData({
          failMes: res.errmsg,
          hiddenAlertPr: !that.data.hiddenAlertPr
        })
      }
    });
  },
  onLoad: function (options) {

    this.setData({
      id: options.fid
    });

    wx.setNavigationBarTitle({
      title: options.fname
    })

    this.getPublicMain();
  },
  onPullDownRefresh() {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getPublicMain();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },


})
