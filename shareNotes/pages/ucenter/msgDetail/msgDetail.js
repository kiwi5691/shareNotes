// pages/ucenter/msg/msg.js
const { $Message } = require('../../../dist/base/index');

var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    msg_id:'',
    avatar:'',
    userName:'',
    title:'',
    cateName:'',
    post_id:'',
    type:'',
    visible5: false,
    actions5: [
      {
        name: '取消'
      },
      {
        name: '删除',
        color: '#ed3f14',
        loading: false
      }
    ]
  },
  handleOpen5() {
    this.setData({
      visible5: true
    });
  },

  delMsg: function (msg_id) {
    let that = this;
    util.request(api.DelMsg, {
      msg_id: msg_id,
    }, 'DELETE').then(function (res) {
      if (res.errno === 0) {
        $Message({
          content: '删除成功！',
          type: 'success'
        });
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
  handleClick5({ detail }) {
    if (detail.index === 0) {
      this.setData({
        visible5: false
      });
    } else {
        this.setData({
          visible5: false,
        });
        var msg_id = this.data.msg_id;
        this.delMsg(msg_id);
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      msg_id: options.msg_id,
    });
    this.getMesgetDetail();
  },
  getMesgetDetail(){
    let that = this;
    util.request(api.GetMsgDetail+this.data.msg_id).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          avatar: res.data.avatar,
          userName: res.data.userName,
          title: res.data.title,
          cateName: res.data.cateName,
          post_id: res.data.post_id,
          type:res.data.type,
          cateid: res.data.cateid,
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

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  goToPost:function(e){
    var id = e.currentTarget.dataset.id;
    var cateid = e.currentTarget.dataset.cateid;
    wx.navigateTo({
        url: "../../../pages/post/posts/posts?post_id=" + id + "&cate_id=" + cateid
    })
  },
})