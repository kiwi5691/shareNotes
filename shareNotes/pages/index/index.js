//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    current: 'tab1',
    current_scroll: 'tab1',
  },
  handleChange({ detail }) {
    this.setData({
      current: detail.key
    });
  },

  handleChangeScroll({ detail }) {
    this.setData({
      current_scroll: detail.key
    });
  },
  adddetial: function () {



    wx.navigateTo({

      url: '../adddetial/adddetial',

      success: function (res) { },

      fail: function (res) { },

      complete: function (res) { },

    })

  }
})
