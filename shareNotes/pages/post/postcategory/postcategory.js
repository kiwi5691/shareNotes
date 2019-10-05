// pages/post/posts/posts.js
const { $Message } = require('../../../dist/base/index');


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
    updateTime: '',
    createTime: '',
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
        $Message({
          content: '删除成功！',
          type: 'success'
        });
      }, 2000);
     }
    }
  },
  goPostDetail() {
    wx.navigateTo({
      url: "/pages/post/posts/posts"
    })
  },
  goEditCate() {
    wx.navigateTo({
      url: "/pages/content/editcategory/editcategory"  //todo 后台获取。
    })
  },
  goCreatePost() {
    wx.navigateTo({
      url: "/pages/content/createpost/createpost"
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
      title: options.title
    });

    wx.setNavigationBarTitle({
      title: options.title
    })

    getPostsAll();
  },

  getPostsAll: function () {
    let that = this;
    util.request(api.GetPostsAll + cate_id).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          publicCate: res.data.publicCate,
        });

      } else if (res.errno === 601) {
        that.setData({
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
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})