// pages/content/oplog/oplog.js

var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    scrollTop: 0,
    oplogs:[],
    oplogs2: [],
    oplogs3: [],
    oplogs4: [],
    oplogs5: [],
    hasOps:true,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      this.getOplogs();
  },

  getOplogs: function () {
    let that = this;
    util.request(api.GetOplogs).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          oplogs: res.data.oplogs,
          oplogs2: res.data.oplogs2,
          oplogs3: res.data.oplogs3,
          oplogs4: res.data.oplogs4,
          oplogs5: res.data.oplogs5,
        });
      } else {
        that.setData({
          hasOps: false,
        });
      }
    });
  },
  onPageScroll(event) {
    this.setData({
      scrollTop: event.scrollTop
    })
  },
  
})