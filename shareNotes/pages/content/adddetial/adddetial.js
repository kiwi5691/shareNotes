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
      const action = [...this.data.actions5];
      action[1].loading = true;

      this.setData({
        actions5: action
      }); 

      console.log(this.data.cateName);
      console.log(this.data.switch1);
      console.log(this.data.current);
      if (this.data.cateName == ""){
        this.setData({
          visible5: false,
        });
        $Message({
          content: '目录名不能空！',
          type: 'error'
        });
      }else{
      setTimeout(() => {
        action[1].loading = false;
        this.setData({
          visible5: false,
          actions5: action
        });
        util.request(api.AddCategory, {
          name: this.data.cateName,
          isPcOrPr: this.data.switch1,
          iconSelected: this.data.current
        }, 'POST').then(function (res) {
          if (res.errno === 0) {
            $Message({
              content: '创建成功！',
              type: 'success'
            });
        }});
        }, 1000);
      }
    }
  },
  onChange(event) {
    const detail = event.detail;
    this.setData({
      'switch1': detail.value
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