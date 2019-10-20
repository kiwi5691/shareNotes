// pages/ucenter/my/my.js
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
    userInfo: {
      nickName: '您尚未登录',
      avatarUrl: '/static/images/person.png'
    },
    tname:'',
    visible5: false,
    actions4: [
      {
        name: '取消'
      },
      {
        name: '确定',
        color: '#19be6b',
      }
    ],
    actions5: [
      {
        name: '取消'
      },
      {
        name: '确定',
        color: '#19be6b',
      }
    ],
    hasLogin:false
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
      
        this.setData({
          visible5: false
        });

      var nickename = this.data.tname;
      this.updateNickName(nickename);

    }
  },
  nameInput: function (e) {
    let nickname = this.data.userInfo.nickName
    this.setData({
      // 'userInfo.nickName': e.detail.detail.value,
      tname: e.detail.detail.value
    })
  },
  updateOnWechat:function(e){
    if (e.detail.userInfo == undefined) {
      
      util.showErrorToast('同步失败');
      return;
    }
    

    user.updateInfoWechat(e.detail.userInfo).then(res => {

      $Message({
        content: '同步成功',
        type: 'success'
      });   
    }).catch((err) => {
      $Message({
        content: '同步失败',
        type: 'error'
      });   
    });
 
  },
  updateNickName: function (nickname){
    let that = this;
    if(nickname==that.data.userInfo.nickName){
      $Message({
        content: '名字不能和之前相同哦',
        type: 'warning'
      });
    } else if (nickname.length==0){
      $Message({
        content: '名字不能为空哦',
        type: 'warning'
      });
    }else{
      util.request(api.AuthUpdate, {
      nickname: nickname,
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        $Message({
          content: '更新成功！',
          type: 'success'
        });
        
        that.setData({
          'userInfo.nickName': nickname
        })
        wx.removeStorageSync('userInfo');
        wx.setStorageSync('userInfo', that.data.userInfo);  
      } else {
        $Message({
          content: res.errmsg,
          type: 'error'
        });
      }
    });
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    //获取用户的登录信息
    if (app.globalData.hasLogin) {
      let userInfo = wx.getStorageSync('userInfo');
      this.setData({
        userInfo: userInfo,
        hasLogin: true
      });
      this.setData({
        tname: this.data.userInfo.nickName
      })
    }
  },

  
})