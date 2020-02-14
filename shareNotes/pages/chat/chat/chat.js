Page({

  data: {
    list: [],
    title: '',
    nickName:'',
    fid:'',
    userId:''
  },

  onLoad: function (options) {
    this.setData({
      userId: options.userId,
      fid: options.fid,
      nickName: options.nickName
    });
    wx.setNavigationBarTitle({
      title: options.nickName
    })

    this.getPostsAll();
  },
  onShow: function (options) {
    var _this = this;

    /**
     * 启动WebSocket
     */
    wx.connectSocket({
      url: 'ws://192.168.168.82:8888/websocket'
    })

    /**
     * 监听连接成功事件
     */
    wx.onSocketOpen(function (res) {

      _this.data.list.push("WebSocket连接成功");
      _this.setData({ list: _this.data.list })

    })

    /**
     * 监听发送事件
     */
    wx.onSocketMessage(function (res) {

      _this.data.list.push(res.data);
      _this.setData({ list: _this.data.list })

    })

    /**
     * 监听连接关闭事件
     */
    wx.onSocketClose(function (res) {

      _this.data.list.push("WebSocket连接关闭");
      _this.setData({ list: _this.data.list })

    })
  },

  /**
   * 绑定参数
   */
  inputTitle: function (e) {
    var _this = this;
    _this.setData({ title: e.detail.value })
  },

  /**
   * 发送信息
   */
  send: function () {
    var _this = this;
    wx.sendSocketMessage({ data: _this.data.title })
  },

  /**
   * 关闭连接
   */
  closeWebSocket: function () {
    wx.closeSocket({})
  }
})