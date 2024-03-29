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
    switch1: false,
    cateName: '',
    cate_id:'',
    category:[],
    actions5: [
      {
        name: '取消'
      },
      {
        name: '修改',
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

  categoryNameInput: function (e) {
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
   
      if (this.data.cateName == "") {
        this.setData({
          visible5: false,
        });
        $Message({
          content: '目录名不能空！',
          type: 'error'
        });
      } else {
          this.setData({
            visible5: false,
          });
          var name = this.data.cateName;
          var isPcOrPr = this.data.switch1;
          var iconSelected = this.data.current;
          var cateId= this.data.cate_id;
          this.updateCategory(cateId,name, isPcOrPr, iconSelected);
      }
    }
  },
  onChange(event) {
    const detail = event.detail;
    this.setData({
      'switch1': detail.value
    })

  },
  updateCategory: function (cateId,name, isPcOrPr, iconSelected) {
    let that = this;
    util.request(api.UpdateCategory, {
      cateId: cateId,
      name: name,
      isPcOrPr: isPcOrPr,
      iconSelected: iconSelected
    }, 'PUT').then(function (res) {
      if (res.errno === 0) {
        $Message({
          content: '修改成功！',
          type: 'success'
        });
        if (isPcOrPr) {
          // 公共目录
          that.getPublicMain();
        } else {
          that.getPrivateMain();
        }

        wx.setStorageSync('titleName', name)
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

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      cate_id: options.cate_id,
    });
    console.log(this.data.cate_id);
    this.getCateDetail();
  },

  getPublicMain: function () {
    let that = this;
    wx.removeStorageSync('publicCate')

    util.request(api.GetPublicCategory).then(function (res) {

      if (res.errno === 0) {
        wx.setStorageSync('publicCate', res.data.publicCate)
        console.log(res.data.publicCate)
        console.log("public")
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
  getCateDetail:function(){
    let that = this;
    util.request(api.GetCategoryDetail+this.data.cate_id).then(function (res) {

      if (res.errno === 0) {

        that.setData({
          cateName: res.data.cateName,
          current: res.data.current,
          switch1: res.data.switch1,
        });
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

})