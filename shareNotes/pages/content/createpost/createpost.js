
const { $Message } = require('../../../dist/base/index');

var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    text:"最少10字",
    visible5: false,
    switch1: false,
    switch2: true,
    visible6:false,
    categoryId:'',
    visible1: false,
    titleName:'',
    mdDisplay: false,
    htmlDisplay:true,
    context:'',
    current: 'homepage',
    min:10,
    max: 500,
    showRight1: false,
    actions4: [
      {
        name: '按钮1'
      },
      {
        name: '按钮2',
        color: '#ff9900'
      },
      {
        name: '按钮3',
        icon: 'search'
      }
    ],
    actions5: [
      {
        name: '取消'
      },
      {
        name: '创建',
        color: '#00FF7F',
        loading: false
      }
    ]
  },
  uploadPic(){
    this.setData({
      visible6: true
    });
  },
  handleOpen5() {
    this.setData({
      visible5: true
    });
  },
  handleClick:function(){
  },
  handleClick5({ detail }) {
    this.data.categoryId;
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
      if (this.data.titleName == "") {
        this.setData({
          visible5: false,
        });
        $Message({
          content: '目录名不能空！',
          type: 'error'
        });
      } else {
      setTimeout(() => {
        action[1].loading = false;
        this.setData({
          visible5: false,
          actions5: action
        });
        var categoryId = this.data.categoryId;
        var title = this.data.titleName;
        var type = this.data.switch1;
        var allowComment = this.data.switch2;

        var originalContent = this.data.context.split('&hc').join('<br>')
        this.createPost(categoryId, title, type, originalContent, allowComment);
      },  1000);
      }
    }
  },
  handleChange({ detail }) {
    this.setData({
      current: detail.key
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      categoryId: options.cate_id,
    });
    console.log(this.data.categoryId);
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
 
  toggleRight1() {
    this.setData({
      showRight1: !this.data.showRight1
    });
  },
 
  onChange2(event){
    const detail = event.detail;
    this.setData({
      'switch2': detail.value
    })
  },
  onChange(event) {
    const detail = event.detail;
    this.setData({
      'switch1': detail.value
    })
    if (this.data.switch1 === true){
      this.setData({
        'mdDisplay': true,
        'htmlDisplay':false
      })
    }else{
      this.setData({
        'mdDisplay': false,
        'htmlDisplay': true
      })
    }
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
        texts: "最低十个字"
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
  createPost: function (categoryId, title, type, originalContent, allowComment){
   
    util.request(api.AddPost, {
      categoryId: categoryId,
      title: title,
      type: type,
      originalContent: originalContent,
      allowComment: allowComment
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        $Message({
          content: '创建成功！',
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
  handleClick4() {
    this.setData({
      visible6: false
    });
  },

  // 工具添加事件
  addTitle(){
    if (this.data.switch1){
    var text = "<h2> 标题</h2>";
    var realtext = this.data.context + text
    this.setData({
      context: realtext
    });
    }else{
      var text = "## 标题";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
    }
  },
  addBored() {
    if (this.data.switch1) {
      var text = "<strong> 字体加厚</strong>";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
    } else {
      var text = "** 字体加厚**";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
    }
  },
  addOblique() {
    if (this.data.switch1) {
      var text = "<p style='font-style: oblique;'>字体变斜</p>";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
    } else {
      var text = "*字体变斜*";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
    }
  },
  addUnSortList() {
    if (this.data.switch1) {
      var text = "<ul>\n<li>无序</li>\n<li>列表</li>\n</ul>";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
    } else {
      var text = "- 无序 \n -列表";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
    }
  },
  addSortList() {
    if (this.data.switch1) {
      var text = "<ol>\n<li>有序</li>\n<li>列表</li>\n</ol>";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
    } else {
      var text = "1. 有序 \n 2.列表";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
    }
  },
  addTable() {
    if (this.data.switch1) {
      var text = "<table>\n<tr>\n<th>头1</th>\n<th>头2</th>\n</tr>\n<tr>\n<td>行 1</td>\n<td>行 1</td>\n</tr>\n</table>";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
    } else {
      var text = "|头1|头2|头3|\n|-|-|-|\n|行1|行2|行3|";
      var realtext = this.data.context + text
      this.setData({
        context: realtext
      });
    }
  },
})