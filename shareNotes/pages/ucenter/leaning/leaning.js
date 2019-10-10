// pages/ucenter/leaning/leaning.js
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    current: 'tab2',
    name: 'name1'
  },

  handleChange({ detail }) {
    this.setData({
      current: detail.key
    });
  },


  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: (res) => {

    return {
      title: '妹子图片',
      imageUrl: "/static/images/my.png",
      success: (res) => {
        console.log("转发成功", res);
      },
      fail: (res) => {
        console.log("转发失败", res);
      }
    }
  }

})