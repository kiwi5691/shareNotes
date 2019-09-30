// pages/post/posts/posts.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    visibleCom: false,
    switch1: true,
    showRigh2: false,
    visible5: false,
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
  handleOpenDel() {
    this.setData({
      visibleCom: true
    });
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
  },
  handleClickDelCo({ detail }) {
    if (detail.index === 0) {
      this.setData({
        visibleCom: false
      });
    } else {
      const action = [...this.data.actions5];
      action[1].loading = true;

      this.setData({
        actions5: action
      });

      setTimeout(() => {
        action[1].loading = false;
        this.setData({
          visibleCom: false,
          actions5: action
        });
        $Message({
          content: '删除成功！',
          type: 'success'
        });
      }, 2000);
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
      url: "/pages/content/editpost/editpost"
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})