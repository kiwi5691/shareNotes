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
    post_id:'',
    cate_id:'',
    del_id:'',
    del_name:'',
    showRigh2: false,
    visible5: false,
    baseComment:[],
    originalContent:'',
    visits:0,
    type:'',
    title:'',
    updateTime:'',
    createTime:'',
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
  handleOpenDel(e) {

    this.setData({
      del_id:e.currentTarget.dataset.id,
      del_name: e.currentTarget.dataset.name,
      visibleCom: true
    });
  },
  toggleRight2() {
    wx.vibrateShort();
    this.setData({
      showRight2: !this.data.showRight2
    });
  },
  handleOpen5() {
    this.setData({
      visible5: true
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
        var postId = this.data.post_id;
        this.delPost(postId);
    }
  },

  upPostsAll: function () {
    var cid = this.data.cate_id;
    let that = this;
    wx.removeStorageSync('postAll' + cid)
    
  },
  delComment: function (commentId){
    util.request(api.DelComment, {
      commentId: commentId,
    }, 'DELETE').then(function (res) {
      if (res.errno === 0) {
        $Message({
          content: '删除评论成功！',
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
  delPost: function (postId) {
    let that=this;
    util.request(api.DelPosts, {
      postId: postId,
    }, 'DELETE').then(function (res) {
      if (res.errno === 0) {
        //全
        that.updateStoragePost();
        //postcategories目录
        that.upPostsAll();
        $Message({
          content: '删除文章成功！',
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
  updateStoragePost: function () {
    let that = this;
    wx.removeStorageSync('postDetail' + that.data.post_id)
  },
  handleClickDelCo({ detail }) {
    if (detail.index === 0) {
      this.setData({
        visibleCom: false
      });
    } else {

        this.setData({
          visibleCom: false,
        });
        var commentId = this.data.del_id;
        this.delComment(commentId);
        this.onShow();

    }
  },
  onChange(event) {
    const detail = event.detail;
    this.setData({
      'switch1': detail.value
    })

  },
  goEditPost() {
    wx.navigateTo({
      url: "/pages/content/editpost/editpost?post_id=" + this.data.post_id + "&cate_id=" + this.data.cate_id
    })
  },
  getComments: function () {
    let that = this;
    util.request(api.getComments + this.data.post_id).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          baseComment: res.data.baseComment,
        });
      } 
    });
  },
  getContentAll: function () {
    let that = this;
    var tempPostsDetail = wx.getStorageSync('postDetail' + that.data.post_id)
    if (tempPostsDetail.length == 0) {
    util.request(api.GetPostsDetail + this.data.post_id).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          originalContent: res.data.originalContent,
          visits: res.data.visits,
          title: res.data.title,
          switch1: res.data.switch1,
          type: res.data.type,
          updateTime: res.data.updateTime,
          createTime: res.data.createTime,
          
        });
        var temptPostsDetail = {
          originalContent : res.data.originalContent,
          visits: res.data.visits,
          title: res.data.title,
          switch1: res.data.switch1,
          type: res.data.type,
          updateTime: res.data.updateTime,
          createTime: res.data.createTime,
        };

        wx.setStorageSync('postDetail' + that.data.post_id, temptPostsDetail)
      } else {
        that.setData({
          failMes: res.errmsg,
          hiddenAlertPu: !that.data.hiddenAlertPu
        })
      }
    }); 
    }else{
        that.setData({
        
      originalContent: tempPostsDetail['originalContent'],
      visits: tempPostsDetail['visits'],
      title: tempPostsDetail['title'],
      switch1: tempPostsDetail['switch1'],
      type: tempPostsDetail['type'],
      updateTime: tempPostsDetail['updateTime'],
      createTime: tempPostsDetail['createTime'],
    });
    }
    that.getComments();
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      post_id: options.post_id,
      cate_id: options.cate_id
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