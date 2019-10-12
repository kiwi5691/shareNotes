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
    showRigh2: false,
    visible5: false,
    cate_id: 0,
    confirm:'',
    title: '',
    menu_id:'',
    hiddenAlertPu: true,
    posts:[],
    updateTime: '',
    createTime: '',
    failMes:'',
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
  toggleRight2() {
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
      const action = [...this.data.actions5];
      action[1].loading = true;

      this.setData({
        actions5: action
      });
      

      if (this.data.confirm != this.data.title) {
        this.setData({
          visible5: false,
        });
        $Message({
          content: '目录名输入错误！',
          type: 'error'
        });
      } else {
      setTimeout(() => {
        action[1].loading = false;
        this.setData({
          visible5: false,
          actions5: action
        });
        var cateId = this.data.cate_id;
        var menu_id = this.data.menu_id;
        this.delCategory(cateId, menu_id);
      }, 1000);
     }
    }
  },
  delCategory: function (cateId, menu_id) {
    let that = this;
    util.request(api.DelCategory, {
      cateId: cateId,
      menu_id: menu_id,
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
  goPostDetail(e) {
    var id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: "/pages/post/posts/posts?post_id=" + id + "&cate_id=" + this.data.cate_id
    })
  },
  goEditCate() {
    wx.navigateTo({
      url: "/pages/content/editcategory/editcategory?cate_id="+this.data.cate_id  
    })
  },
  goCreatePost() {
    wx.navigateTo({
      url: "/pages/content/createpost/createpost?cate_id=" + this.data.cate_id 
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    this.setData({
      createTime: options.creTime,
      updateTime: options.upTime,
      cate_id: options.id,
      title: options.title,
      menu_id: options.menu_id
    });
    wx.setNavigationBarTitle({
      title: options.title
    })

    this.getPostsAll();
  },

  getPostsAll: function () {
    let that = this;
    util.request(api.GetPostsAll + this.data.cate_id).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          posts: res.data.posts,
        });

      } else if (res.errno === 801) {
        that.setData({
          failMes: res.errmsg,
          hiddenAlertPu: !that.data.hiddenAlertPu
        })
      }
    });
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
      this.getPostsAll()
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },
  confirmDel: function (e) {
    this.setData({
      confirm: e.detail.detail.value
    })
  },

})