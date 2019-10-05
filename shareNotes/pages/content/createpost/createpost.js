// pages/content/adddetial/adddetial.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    text:"最少5字",
    visible5: false,
    switch1: false,
    visible1: false,
    titleName:'',
    context:'',
    min:5,
    max: 500,
    actions5: [
      {
        name: '取消'
      },
      {
        name: '修改',
        color: '#00FF7F',
        loading: false
      }
    ]
  },

  handleOpen5() {
    this.setData({
      visible5: true
    });
  },
  handleClick:function(){
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
        $Message({
          content: '创建成功！',
          type: 'success'
        });
      }, 2000);
    }
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

  },
  onChange(event) {
    const detail = event.detail;
    this.setData({
      'switch1': detail.value
    })

  },
  handleOpen1() {
    this.setData({
      visible1: true
    });
  },

  handleClose1() {
    this.setData({
      visible1: false
    });
  },
  titleNameInput: function (e) {
    this.setData({
      titleName: e.detail.detail.value
    })
  },
  contextInput: function (e) {
    this.setData({
      context: e.detail.value
    })
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
  }

})