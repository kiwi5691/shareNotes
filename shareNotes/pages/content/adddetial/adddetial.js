// pages/content/adddetial/adddetial.js
const { $Message } = require('../../../dist/base/index');

var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    visible5: false,    
    switch1: true,
    cateName:'',
    actions5: [
      {
        name: '取消'
      },
      {
        name: '创建',
        color: '#00FF7F',
        loading: false
      }
    ],
    categories: [{
      id: 1,
      name: '活动',
    }, {
      id: 2,
      name: '手记'
    }, {
      id: 3,
      name: '内容'
    }],
    current: '活动',
  },
  handleCategoryChange({ detail = {} }) {
    this.setData({
      current: detail.value
    });
  },
 
  handleOpen5() {
    wx.vibrateShort();
    this.setData({
      visible5: true
    });
  },

  categoryNameInput:function(e){
      this.setData({
        cateName: e.detail.detail.value
      })
  },
  handleClick5({ detail }) {
    if (detail.index === 0) {
      this.setData({
        visible5: false
      });
    } else {

      if (this.data.cateName == ""){
        this.setData({
          visible5: false,
        });
        $Message({
          content: '目录名不能空！',
          type: 'error'
        });
      }else{
        this.setData({
          visible5: false,
        });
        var name = this.data.cateName;
        var isPcOrPr = this.data.switch1;
        var iconSelected = this.data.current;
        this.addCategory(name, isPcOrPr, iconSelected);
      }
    }
  },
  onChange(event) {
    const detail = event.detail;
    this.setData({
      'switch1': detail.value
    })

  },
  addCategory: function (name, isPcOrPr, iconSelected){
    let that = this;
    util.request(api.AddCategory, {
      name: name,
      isPcOrPr: isPcOrPr,
      iconSelected: iconSelected
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        $Message({
          content: '创建成功！',
          type: 'success'
        });
        if(isPcOrPr){
          // 公共目录
          that.getPublicMain();
        }else{
          that.getPrivateMain();
        }
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
  getPublicMain: function () {
    let that = this;
    wx.removeStorageSync('publicCate')
   
      util.request(api.GetPublicCategory).then(function (res) {

        if (res.errno === 0) {
          wx.setStorageSync('publicCate', res.data.publicCate)
        } else {
          $Message({
            content: '服务器出小差啦',
            type: 'error'
          });
        }
      });
    },
  getPrivateMain: function () {
    let that = this;
    wx.removeStorageSync('privateCate')
      util.request(api.GetPrivateCategory).then(function (res) {
        if (res.errno === 0) {
          wx.setStorageSync('privateCate', res.data.privateCate)
        } else {
          $Message({
            content: '服务器出小差啦',
            type: 'error'
          });
        }
      });  
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

})