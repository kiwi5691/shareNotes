var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');
var app = getApp();

Page({
  data: {
    publicCate:[],
    privateCate: [],
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

  },
  getPublicMain: function () {
    let that = this;
    util.request(api.GetPublicCategory).then(function (res) {
      console.log("回调函数:" + JSON.stringify(res.data.publicCate))

      if (res.errno === 0) {
        that.setData({
          publicCate: res.data.publicCate,
        });

    


        console.log("函数:" + that.publicCate)

      
      }
    });
  },
  onLoad: function (options) {

    this.getPublicMain();
    // this.getPrivateMain();

  }
})
