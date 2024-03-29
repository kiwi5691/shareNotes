
const { $Message } = require('../../../dist/base/index');
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();

Page({
  data: {
    spinShow: true,
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
    showRigh2: false,
    userIdT:'',
    fid:'',
    nickName:'',
    avatar:'',
  },
  toggleRight2() {
    wx.vibrateShort();
    this.setData({
      showRight2: !this.data.showRight2
    });
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
          spinShow:false
        });
      } else if (res.errno === 721) {
        that.setData({
          failMes: res.errmsg,
          hiddenAlertPr: !that.data.hiddenAlertPr
        })
      }

      that.setData({
        spinShow: false
      });
  
    });
  },
  toggleLeft1() {
    this.setData({
      showRight2: !this.data.showRight2
    });
  },
  onLoad: function (options) {

    this.setData({
      id: options.fid,
      fname: options.fname,
      userIdT:wx.getStorageSync('userId'),
      fid: options.fid,
      nickName:options.fname,
      avatar:options.avatar,
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

  onChat: function () {
      wx.navigateTo({
        url: '/pages/chat/chat/chat?userId='+this.data.userIdT+'&fid='+this.data.fid+'&nickName='+this.data.nickName+'&avatar='+this.data.avatar,
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
        var fid = this.data.id;
        this.delFriend(fid);
    }
  }
})
