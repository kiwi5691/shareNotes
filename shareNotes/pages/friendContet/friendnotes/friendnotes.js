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
    type:'',
    del_name: '',
    showRigh2: false,
    visible5: false,
    baseComment: [],
    originalContent: '',
    visits: 0,
    title: '',
    updateTime: '',
    createTime: '',
    actions5: [
      {
        name: '取消'
      },
      {
        name: '创建',
        color: '#00FF7F',
        loading: false
      }
    ]

  },
  handleOpen5() {
    this.setData({
      visible5: true
    });
  },
  handleClick5({ detail }) {
    this.data.categoryId;
    if (detail.index === 0) {
      this.setData({
        visible5: false
      });
    } else {
      if (this.data.value5 == "") {
        this.setData({
          visible5: false,
        });
        $Message({
          content: '评论不能为空！',
          type: 'error'
        });
      } else {
          this.setData({
            visible5: false,
          });
          var postId = this.data.post_id;
          var content = this.data.value5;
          var isanonymous = this.data.switch2;
          this.createComment(postId, content, isanonymous);
          this.setData({
            value5: ''
          });
          this.onShow();
      }
    }
  },
  createComment: function (postId, content, isanonymous){
    util.request(api.AddComment, {
      postId: postId,
      content: content,
      isanonymous: isanonymous,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        $Message({
          content: '创建成功！',
          type: 'success'
        });
        
      } else {
        $Message({
          content: res.errmsg,
          type: 'error'
        });
      }
    });
  },
  getContentAll: function () {
    const _ts = this;
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
          type: res.data.type,
          baseComment: res.data.baseComment,
        });
        let data = app.towxml.toJson(
          that.data.originalContent,
          that.data.type
        );
        data = app.towxml.initData(data, {
          app: _ts
        });
        _ts.setData({
          article: data
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
    this.getContentAll();
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },
  commentInput: function (e) {
    this.setData({
      value5: e.detail.detail.value
    })
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
    var titleName = this.data.title
    return {
      title: titleName,
      desc: '大家一起分享笔记呀',
    }
  }
})