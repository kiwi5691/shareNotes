
const { $Message } = require('../../../dist/base/index');
var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();
Page({


  data: {

    searchPageNum:1,

    searchKeyword: '',  //需要搜索的字符

    searchSongList: [], //放置返回数据的数组

    searchPageNum: 1,   // 设置加载的第几次，默认是第一次

    hasMoreData: true,

    callbackcount: 15,      //返回数据的个数

    searchLoading: false, //"上拉加载"的变量，默认false，隐藏

    searchLoadingComplete: false  //“没有数据”的变量，默认false，隐藏

  },

  //输入框事件，每输入一个字符，就会触发一次  

  bindKeywordInput: function (e) {

    console.log("输入框事件");

    this.setData({

      searchKeyword: e.detail.value

    })

  },



  //搜索，访问网络  

  fetchSearchList: function () {

    let that = this;

    let searchKeyword = that.data.searchKeyword,//输入框字符串作为参数  

      searchPageNum = that.data.searchPageNum,//把第几次加载次数作为参数  

      callbackcount = that.data.callbackcount; //返回数据的个数  

    //访问网络  
    util.request(api.Search, {
      page: that.data.searchPageNum,
      pageSize: 5,
      setSearchKeyword: that.data.searchKeyword
    }, 'POST').then(function (res) {
      if (res.errno === 0) {
        console.log("left:",res.data.status);
        if (res.data.status != 0) {
          let searchList = [];
          searchList = res.data.data;
          if(that.data.searchPageNum==0){
            that.setData({
              searchSongList: searchList, //获取数据数组
              // searchLoading: true   //把"上拉加载"的变量设为false，显示
            });
          }else {
            let tempData = that.data.searchSongList;
            that.setData({
              searchSongList: tempData.concat(searchList), //获取数据数组
              // searchLoading: true   //把"上拉加载"的变量设为false，显示
            });
          }
        }
       else {
         //没有数据
          //没有数据了，把“没有数据”显示，把“上拉加载”隐藏

        that.setData({
          searchLoadingComplete: true, //把“没有数据”设为true，显示
          searchLoading: false,  //把"上拉加载"的变量设为false，隐藏
          hasMoreData:false
        });
      }
    } else {
        $Message({
          content: '请入搜索条件！',
          type: 'error'
        });
      }
    });

  },



  //点击搜索按钮，触发事件  

  keywordSearch: function (e) {

    if(this.data.searchKeyword!=""){
      this.setData({

        searchPageNum: 0,   //第一次加载，设置1

        searchSongList: [],  //放置返回数据的数组,设为空

        searchLoading: true,  //把"上拉加载"的变量设为true，显示

        searchLoadingComplete: false //把“没有数据”设为false，隐藏

      })
    }else {
      $Message({
        content: '请入搜索条件！',
        type: 'error'
      });
    }


    this.fetchSearchList();

  },



  requestForPage:function(id){
    var checkId=id;
    let that = this;
    util.request(api.RequestForPage + checkId).then(function (res) {

      if (res.errno === 0) {
        if(res.data.status=="fid"){
          wx.navigateTo({
            url: "/pages/friendContet/friendnotes/friendnotes?post_id=" + id
          })
        }if(res.data.status=="own"){
          wx.navigateTo({
            url: "/pages/post/posts/posts?post_id=" + id + "&cate_id=" + res.data.cateId
          })
        }

      } else {
        $Message({
          content: res.errmsg,
          type: 'error'
        });
      }

    });
  },

  goPostDetail(e) {
    var id = e.currentTarget.dataset.id;
    this.requestForPage(id);

  },
  //滚动到底部触发事件

  onReachBottom: function () {

    // console.log("im in");
    let that = this;

    if (that.data.searchLoading && !that.data.searchLoadingComplete) {

      that.setData({

        searchPageNum: that.data.searchPageNum + 1,  //每次触发上拉事件，把searchPageNum+1


      });

      that.fetchSearchList();

    }

  },

})