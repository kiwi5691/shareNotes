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
    wx.vibrateShort();
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
   
      if (this.data.titleName == "") {
        this.setData({
          visible5: false,
        });
        $Message({
          content: '姓名不能为空！',
          type: 'error'
        });
      } else {
          this.setData({
            visible5: false,
          });
          var titleName = this.data.titleName;
          var context = this.data.context;
          this.submitIssue(titleName, context);
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

  
})