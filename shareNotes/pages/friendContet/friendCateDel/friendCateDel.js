// pages/friendContet/friendCateDel/friendCateDel.js
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    cate_id: '',
    failMes: '',
    posts:[],
    title: '',
    hiddenAlertPr: true,
  },
  goPostDetail() {
    wx.navigateTo({
      url: "/pages/friendContet/friendnotes/friendnotes"
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      cate_id: options.id,
      title: options.title,
    });
    wx.setNavigationBarTitle({
      title: options.title
    })

    this.getPostsAll();
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
    this.getPostsAll();
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

  },
   getPostsAll: function () {
    let that = this;
     util.request(api.GetFriendPublicPosts + this.data.cate_id).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          posts: res.data.posts,
        });

      } else if (res.errno === 711) {
        that.setData({
          failMes: res.errmsg,
          hiddenAlertPr: !that.data.hiddenAlertPr
        })
      }
    });
  },
})