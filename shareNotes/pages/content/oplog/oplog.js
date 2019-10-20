// pages/content/oplog/oplog.js

var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    spinShow: true,
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
          today: res.data.today,
          yesterday: res.data.yesterday,
          tdbYesterday: res.data.tdbYesterday,
          oplogs: res.data.oplogs,
          oplogs2: res.data.oplogs2,
          oplogs3: res.data.oplogs3
        });
        that.setData({
          spinShow:false
        })
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