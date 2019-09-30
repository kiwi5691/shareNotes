//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    current: 'tab1',
  },
  handleChange({ detail }) {
    this.setData({
      current: detail.key
    });
  },


  adddetial: function () {

    wx.navigateTo({

      url: '../content/adddetial/adddetial',

      success: function (res) { },

      fail: function (res) { },

      complete: function (res) { },

    })

  }
})
