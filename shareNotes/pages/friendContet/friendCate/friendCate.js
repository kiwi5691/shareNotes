
const { $Message } = require('../../../dist/base/index');
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();

Page({
  data: {
    id:'',
    failMes: '',
    fname:'',
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
    ],
    publicCate: [],
    hiddenAlertPr: true,
    current: 'tab1',
  },
  handleChange({ detail }) {
    this.setData({
      current: detail.key
    });
  },
  getPublicMain: function () {
    let that = this;
    util.request(api.GetFriendPublicCategory + this.data.id).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          publicCate: res.data.publicCate,
        });
      } else if (res.errno === 721) {
        that.setData({
          failMes: res.errmsg,
          hiddenAlertPr: !that.data.hiddenAlertPr
        })
      }
    });
  },
  onLoad: function (options) {

    this.setData({
      id: options.fid,
      fname: options.fname
    });

    wx.setNavigationBarTitle({
      title: options.fname
    })

    this.getPublicMain();
  },
  onPullDownRefresh() {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getPublicMain();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },
  delFriend: function (fid) {
    let that = this;
    util.request(api.DelFriend, {
      fid: fid,
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
  onChange(event) {
    this.setData({
      visible5: true
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
      setTimeout(() => {
        action[1].loading = false;
        this.setData({
          visible5: false,
          actions5: action
        });
        var fid = this.data.id;
        this.delFriend(fid);
      }, 1000);
    }
  }
})
