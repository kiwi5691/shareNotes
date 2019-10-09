// pages/post/posts/posts.js
const { $Message } = require('../../../dist/base/index');
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    visibleCom: false,
    switch1: false,
    switch2: false,
    post_id: '',
    del_id: '',
    value5:'',
    del_name: '',
    showRigh2: false,
    visible5: false,
    baseComment: [],
    originalContent: '',
    visits: 0,
    title: '',
    updateTime: '',
    createTime: '',

  },

  getContentAll: function () {
    let that = this;
    util.request(api.GetFriendDetail + this.data.post_id).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          originalContent: res.data.originalContent,
          visits: res.data.visits,
          title: res.data.title,
          switch1: res.data.switch1,
          updateTime: res.data.updateTime,
          createTime: res.data.createTime,
          baseComment: res.data.baseComment,
        });
      } else {
        that.setData({
          failMes: res.errmsg,
          hiddenAlertPu: !that.data.hiddenAlertPu
        })
      }
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onChange(event) {
    const detail = event.detail;
    this.setData({
      'switch2': detail.value
    })

  },
  onLoad: function (options) {
    this.setData({
      post_id: options.post_id
    });
    this.getContentAll();
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

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getContentAll()
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
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