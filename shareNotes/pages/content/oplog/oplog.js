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
    hasOps:true,
    today:'',
    yesterday: '',
    tdbYesterday: '',
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
          today: res.data.today.createTime,
          yesterday: res.data.yesterday.createTime,
          tdbYesterday: res.data.tdbYesterday.createTime,
          oplogs: res.data.today.logsDTOS,
          oplogs2: res.data.yesterday.logsDTOS,
          oplogs3: res.data.tdbYesterday.logsDTOS
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