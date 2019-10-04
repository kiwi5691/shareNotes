//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    id:'',
    privateCate: [],
    hiddenAlertPr: true,
    current: 'tab1',
  },
  handleChange({ detail }) {
    this.setData({
      current: detail.key
    });
  },
  getPrivateMain: function () {
    let that = this;
    util.request(api.GetFriendPrivateCategory+fid).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          privateCate: res.data.privateCate,
        });
      } else if (res.errno === 601) {
        that.setData({
          hiddenAlertPr: !that.data.hiddenAlertPr
        })
      }
    });
  },
  onLoad: function (options) {

    this.setData({
      id: options.fid
    });

    wx.setNavigationBarTitle({
      title: options.fname
    })

    getPrivateMain();
    console.log("id is" + options.fid)
  },
  onPullDownRefresh() {
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.getPrivateMain();
    wx.hideNavigationBarLoading() //完成停止加载
    wx.stopPullDownRefresh() //停止下拉刷新
  },


})
