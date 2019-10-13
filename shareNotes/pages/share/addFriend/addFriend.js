// pages/addFriend/addFriend.js
const { $Message } = require('../../../dist/base/index');

var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    hasPicture:false,
    text1: '',
    text2: '',
    visible6: false,
    imgFormat:'fuckfuckfuck',
    files: [],
    imgsrc:'/static/images/addpic.png',
    visible3: false,
    actions3: [
      {
        name: '复制mark',
        color: '#2d8cf0',
      },
      {
        name: '复制html',
        color: '#19be6b'
      },
      {
        name: '取消'
      }
    ],
  },
  handleOpen3() {
    this.setData({
      visible3: true
    });
  },

  handleClick3({ detail }) {
    const index = detail.index;

    if (index === 0) {
      if (this.data.hasPicture){
        wx.setClipboardData({
          data: this.data.imgFormat,
          success: function (res) {
            wx.getClipboardData({            
            })
          }
        })
      }else{
        $Message({
          content: '尚未上传图片',
          type: 'success'
        });
      }
    } else if (index === 1) {
      if (this.data.hasPicture) {
        wx.setClipboardData({
          data: this.data.imgFormat,
          success: function (res) {
            wx.getClipboardData({
              success: function (res) {
                console.log(res.data) // data
              }
            })
          }
        })
      } else {
        $Message({
          content: '尚未上传图片',
          type: 'success'
        });
      }
    } else if (index === 2){
    
    this.setData({
      visible3: false
    });
    }
  },
  handleOpen1() {
    this.setData({
      visible6: true
    });
  },

  handleClose1() {
    this.setData({
      visible6: false
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  addFriend: function (id) {
    var friendId = id;
    let that = this;
    util.request(api.AddFriend + friendId).then(function (res) {

      if (res.errno === 0) {
        that.setData({
          text1: "添加好友中",
          text2: "正在加速处理~"
        })
        wx.switchTab({
          url: "../../../pages/share/index/index"
        })
      } else  {
        that.setData({
          text1: "您尚未登录",
          text2: "请先登录"
        })
        wx.switchTab({
          url: "../../../pages/share/index/index"
        })
      }
    });
  },
  onLoad: function (options) {
    var id = options.id;
    this.addFriend(id);
  },
  //上传
  uploadFileTap: function (e) {
    var that = this;
    wx.chooseImage({
      count: 1, 
      sizeType: ['compressed'], 
      sourceType: ['album', 'camera'], 
      success: function (res) {
        that.setData({
          files: that.data.files.concat(res.tempFilePaths)
        });
        that.upload(res);
      }
    })
  },
  upload: function (res) {
    var that = this;
    const uploadTask = wx.uploadFile({
      url: api.StorageUpload,
      filePath: res.tempFilePaths[0],
      name: 'file',
      success: function (res) {
        var _res = JSON.parse(res.data);
        if (_res.errno === 0) {
          that.setData({
            hasPicture: true,
            imgsrc: _res.data.url
          })
        } else {
          $Message({
            content: _res.errmsg ,
            type: 'error'
          });
        }
        
      },
      fail: function (e) {
        wx.showModal({
          title: '错误',
          content: '上传失败',
          showCancel: false
        })
      },
    })
    uploadTask.onProgressUpdate((res) => {
      console.log('上传进度', res.progress)
      console.log('已经上传的数据长度', res.totalBytesSent)
      console.log('预期需要上传的数据总长度', res.totalBytesExpectedToSend)
    })

  },
  uploadPic() {
    this.setData({
      visible6: true
    });
  },
  handleClick4() {
    this.setData({
      visible6: false
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

  }

})