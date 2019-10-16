// pages/ucenter/msg/msg.js
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
    visible5: false,
    hiddenAlertPu:false,
    msgList:[],
    actions5: [
      {
        name: '取消'
      },
      {
        name: '删除',
        color: '#ed3f14',
        loading: false
      }
    ]
  },
  handleOpen5() {
    this.setData({
      visible5: true
    });
  },
  getMsgList: function () {
    let that = this;
    util.request(api.GetMsgList).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          msgList: res.data.msgList,
          hiddenAlertPu:true
        });
      }
    });
  },
  handleClick5({ detail }) {
    if (detail.index === 0) {
      this.setData({
        visible5: false
      });
    } else {
        this.setData({
          visible5: false,
        });
        this.delAllMsg();
    }
  },
  goMsgDetail(e) {
    var id = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: "/pages/ucenter/msgDetail/msgDetail?msg_id=" + id
    })
  },
  delAllMsg: function () {
    let that = this;
    util.request(api.DelMsgAll, {
    }, 'DELETE').then(function (res) {
      if (res.errno === 0) {
        $Message({
          content: '删除成功！',
          type: 'success'
        });
        wx.navigateBack({
          delta: 1
        })
      } else {
        $Message({
          content: res.errmsg,
          type: 'error'
        });
      }
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getMsgList()
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
    this.getMsgList()
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },

 
})