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
    titleName: '',
    context: '',
    min: 5,
    max: 500,
    actions5: [
      {
        name: '取消'
      },
      {
        name: '提交',
        color: '#00FF7F',
        loading: false
      }
    ],
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
  categoryNameInput: function (e) {
    this.setData({
      titleName: e.detail.detail.value
    })
  },
  contextInput: function (e) {
    this.setData({
      context: e.detail.value
    })
  },
  handleOpen5() {
    this.setData({
      visible5: true
    });
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

      console.log(this.data.titleName);
      console.log(this.data.context);
      if (this.data.titleName == "") {
        this.setData({
          visible5: false,
        });
        $Message({
          content: '姓名不能为空！',
          type: 'error'
        });
      } else {
        setTimeout(() => {
          action[1].loading = false;
          this.setData({
            visible5: false,
            actions5: action
          });
          var titleName = this.data.titleName;
          var context = this.data.context;
          this.submitIssue(titleName, context);
        }, 1000);
      }
    }
  },
  submitIssue: function (titleName, context) {
    let that = this;
    util.request(api.SubmitIssue, {
      titleName: titleName,
      context: context,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        $Message({
          content: '提交成功！',
          type: 'success'
        });
      } else if (res.errno === 102) {
        $Message({
          content: res.errmsg,
          type: 'error'
        });
      } 
    });
  },
  inputs: function (e) {
    // 获取输入框的内容
    var value = e.detail.value;
    // 获取输入框内容的长度
    var len = parseInt(value.length);

    //最少字数限制
    if (len <= this.data.min)
      this.setData({
        texts: "最低五个字"
      })
    else if (len > this.data.min)
      this.setData({
        texts: " "
      })

    if (len > this.data.max) return;
    this.setData({
      currentWordNumber: len
    });
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