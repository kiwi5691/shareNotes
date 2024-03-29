const { $Message } = require('../../../dist/base/index');

var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    text: " ",
    visible5: false,
    switch2: true,
    switch1: false,
    visible1: false,
    visibleReview:false,
    titleName: '',
    contextTemp: '',
    type:'',
    hasPicture: false,
    files: [],
    imgsrc: '/static/images/addpic.png',
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
    visible3: false,
    cate_id:'',
    mdDisplay: false,
    htmlDisplay: true,
    context: '',
    post_id:'',
    min: 10,
    max: 500,
    showRight1: false,
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
    wx.vibrateShort();
    this.setData({
      visible5: true,
      visibleReview:true
    });
  },
  handleClick: function () {
  },
  onChange2(event) {
    const detail = event.detail;
    this.setData({
      'switch2': detail.value
    })
  },
  updateMarkdown: function (context) {
    const _ts = this;
    let data = app.towxml.toJson(
      context,
      'markdown'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      article1: data
    });
  },
  updatehtml: function (context) {
    const _ts = this;
    let data = app.towxml.toJson(
      context,
      'html'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      article2: data
    });
  },
  handleClick5({ detail }) {
    if (detail.index === 0) {
      this.setData({
        visible5: false,
        visibleReview: false
      });
    } else {
      if (this.data.titleName == "") {
        this.setData({
          visible5: false,
        });
        $Message({
          content: '文章名不能空！',
          type: 'error'
        });
      } else {
          this.setData({
            visible5: false,
          });
          var postId = this.data.post_id;
          var title = this.data.titleName;
          var type = this.data.switch1;
          var originalContent = this.data.context.split('&hc').join('<br>')
          var categoryId = this.data.cate_id;
          var allowComment = this.data.switch2;
          this.updatePosts(postId, title, type, originalContent, categoryId, allowComment);
        
    }
  }
  },
  updatePosts: function (postId, title, type, originalContent, categoryId, allowComment){
    let that = this;
    util.request(api.UpdatePosts, {
      postId: postId,
      title: title,
      type: type,
      originalContent: originalContent,
      allowComment: allowComment,
      categoryId: categoryId
    }, 'PUT').then(function (res) {
      if (res.errno === 0) {
       
        //postcategories目录
        that.upPostsAll();
        //全
        that.updateStoragePost();
        $Message({
          content: '修改成功！',
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
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      post_id: options.post_id,
      cate_id: options.cate_id
    });
    this.getContentAll();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  handleOpen3() {
    this.setData({
      visible3: true
    });
  },
  handleClick3({ detail }) {
    const index = detail.index;

    if (index === 0) {
      if (this.data.hasPicture) {
        wx.setClipboardData({
          data: "![图片](" + this.data.imgsrc + ")",
          success: function (res) {
            wx.getClipboardData({
            })
          }
        })
      } else {
        $Message({
          content: '尚未上传图片',
          type: 'success'
        });
      }
    } else if (index === 1) {
      if (this.data.hasPicture) {
        wx.setClipboardData({
          data: "<img src='" + this.data.imgsrc + "' height='10'/>",
          success: function (res) {
            wx.getClipboardData({
              success: function (res) {
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
    } else if (index === 2) {
      this.setData({
        visible3: false
      });
    }
  },
  getContentAll: function () {
    let that = this;

    var tempPostsDetail = wx.getStorageSync('postDetail' + that.data.post_id)
    if (tempPostsDetail['type']=='markdown'){
      that.setData({ 
        switch1: false,
      });
    }else{
      that.setData({
        switch1: true,
      });
    }
    that.setData({
      context: tempPostsDetail['originalContent'],
      titleName: tempPostsDetail['title'],
      //评论
      switch2: tempPostsDetail['switch1'],
      contextTemp: tempPostsDetail['originalContent'].split('\n').join('<br>')
    });
    that.updateTextArea(that.data.context)
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
            content: _res.errmsg,
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
  toggleRight1() {
    this.setData({
      showRight1: !this.data.showRight1,
      visibleReview: !this.data.visibleReview
    });
  },
  onChange(event) {
    const detail = event.detail;
    this.setData({
      'switch1': detail.value
    })
    if (this.data.switch1 === true) {
      this.setData({
        'mdDisplay': true,
        'htmlDisplay': false
      })
    } else {
      this.setData({
        'mdDisplay': false,
        'htmlDisplay': true
      })
    }
  },
  handleOpen1() {
    this.setData({
      visible1: true,
      visibleReview: true
    });
    this.updatehtml(this.data.context);
    this.updateMarkdown(this.data.context);
  },

  handleClose1() {
    this.setData({
      visible1: false,
      visibleReview: false
    });
  },
  titleNameInput: function (e) {
    this.setData({
      titleName: e.detail.detail.value
    })
  },
  contextInput: function (e) {
    this.setData({
      context: e.detail.value,
      contextTemp: e.detail.value.split('\n').join('<br>')
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
        texts: " "
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
  updateStoragePost:function(){

    let that = this;
    wx.removeStorageSync('postDetail' + that.data.post_id)
    util.request(api.GetPostsDetail + this.data.post_id).then(function (res) {
      if (res.errno === 0) {
        var temptPostsDetail = {
          originalContent: res.data.originalContent,
          visits: res.data.visits,
          title: res.data.title,
          switch1: res.data.switch1,
          type: res.data.type,
          updateTime: res.data.updateTime,
          createTime: res.data.createTime,
        };
        wx.setStorageSync('postDetail' + that.data.post_id, temptPostsDetail)
      }else{
        $Message({
          content: "服务器出小差了",
          type: 'error'
        });
      } });
 },
  upPostsAll: function () {
    let that = this;
    var cid = that.data.cate_id;
    wx.removeStorageSync('postAll' + cid)
  },
  updateTextArea(context) {
    var value = context;
    this.setData({
      contextTemp: value.split('\n').join('<br>')
    })
    // 获取输入框内容的长度
    var len = parseInt(value.length);

    //最少字数限制
    if (len <= this.data.min)
      this.setData({
        texts: " "
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
  addCode(){
    var text = "```\n hello world\n```";
    var realtext = this.data.context + text
    this.setData({
      context: realtext
    });
    this.updateTextArea(this.data.context)
  },
  // 工具添加事件
  addTitle() {
    if (this.data.switch1) {
      var text = "<h2> 标题</h2>";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
      this.updateTextArea(this.data.context)
    } else {
      var text = "## 标题";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
      this.updateTextArea(this.data.context)
    }
  },
  addBored() {
    if (this.data.switch1) {
      var text = "<strong> 字体加厚</strong>";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
      this.updateTextArea(this.data.context)
    } else {
      var text = "** 字体加厚**";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
      this.updateTextArea(this.data.context)
    }
  },
  addOblique() {
    if (this.data.switch1) {
      var text = "<p style='font-style: oblique;'>字体变斜</p>";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
      this.updateTextArea(this.data.context)
    } else {
      var text = "*字体变斜*";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
      this.updateTextArea(this.data.context)
    }
  },
  addUnSortList() {
    if (this.data.switch1) {
      var text = "<ul>\n<li>无序</li>\n<li>列表</li>\n</ul>";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
      this.updateTextArea(this.data.context)
    } else {
      var text = "- 无序 \n -列表";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
      this.updateTextArea(this.data.context)
    }
  },
  addSortList() {
    if (this.data.switch1) {
      var text = "<ol>\n<li>有序</li>\n<li>列表</li>\n</ol>";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
      this.updateTextArea(this.data.context)
    } else {
      var text = "1. 有序 \n 2.列表";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
      this.updateTextArea(this.data.context)
    }
  },
  addTable() {
    if (this.data.switch1) {
      var text = "<table>\n<tr>\n<th>头1</th>\n<th>头2</th>\n</tr>\n<tr>\n<td>行 1</td>\n<td>行 1</td>\n</tr>\n</table>";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
      this.updateTextArea(this.data.context)
    } else {
      var text = "|头1|头2|头3|\n|-|-|-|\n|行1|行2|行3|";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
      this.updateTextArea(this.data.context)
    }
  },

})