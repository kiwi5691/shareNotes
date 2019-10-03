var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();


Page({

  /** * 页面的初始数据 */

  data: {

    isActive: null,
    listMain: '',
    fixedTitle: null,
    toView: 'inTo0',

    oHeight: [],

    scroolHeight: 0,

    fixedTop: 0
  },
  getListMain:function(){
    let that = this;
    util.request(api.ShowFriendList).then(function (res) {
      console.log("回调函数:" + res.data.listMain)

      if (res.errno === 0) {
        that.setData({
          listMain: JSON.parse(res.data.listMain),
        });
 

        // var temp = res.data.friendList
        // for (var i in temp) {
        //   listMain.push(temp[i]);

        // }
    
        // that.setData({
        //   checkedAllStatus: that.isCheckedAll()
        // });
      }

      console.log("函数:" + this.listMain);

    });
  },

  
  //   util.request(api.ShowFriendList, {
  //     showType: that.data.showType,
  //   }).then(function (res) {
  //     if (res.errno === 0) {
  //       console.log(res.data);
  //       that.setData({
  //         listMain: that.data.groupDtoMap.concat(res.data.list),
  //       });
  //     }
  //   });
  // },
  

  //点击右侧字母导航定位触发

  scrollToViewFn: function (e) {

    var that = this; var _id = e.target.dataset.id;

    for (var i = 0; i < that.data.listMain.length; ++i) {

      if (that.data.listMain[i].id === _id) {

        that.setData({
          isActive: _id, toView: 'inTo' + _id, fixedTitle: that.data.listMain[i].region

        })

        break;

      }

    }
  },

  // 页面滑动时触发

  onPageScroll: function (e) {

    this.setData({ scroolHeight: e.detail.scrollTop });

    for (let i in this.data.oHeight) {

      if (e.detail.scrollTop < this.data.oHeight[i].height) {

        this.setData({

          isActive: this.data.oHeight[i].key,

          fixedTitle: this.data.oHeight[i].nickname

        });

        return false;

      }
    }
  },

  // 处理数据格式，及获取分组高度

  getBrands: function () {

    var that = this; var number = 0

    //计算分组高度,wx.createSelectotQuery()获取节点信息

    for (let i = 0; i < that.data.listMain.length; ++i) {

      wx.createSelectorQuery().select('#inTo' + that.data.listMain[i].id).boundingClientRect(function (rect) {
        number = rect.height + number; var newArry = [{ 'height': number, 'key': rect.dataset.id, "nickname": that.data.listMain[i].region }]

        that.setData({

          oHeight: that.data.oHeight.concat(newArry)

        })

      }).exec();
    };
  },

  onLoad: function (options) {

    this.getListMain();  

    var that = this;

    that.getBrands();

  },
  goFriendCate() {
    wx.navigateTo({
      url: "/pages/friendContet/friendCate/friendCate"
    })
  },
})