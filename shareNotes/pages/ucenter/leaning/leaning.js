// pages/ucenter/leaning/leaning.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    current: 'tab2',
    name: 'name1'
  },

  handleChange({ detail }) {
    this.setData({
      current: detail.key
    });
  },
  markdownList1:function(){
    const _ts = this;
    let data = app.towxml.toJson(
      '# 我是标题（注意要有空格）',
      'markdown'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      title: data
    });
  },
  markdownList2: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '> 段落引用',
      'markdown'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      rev: data
    });
  },
  markdownList3: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '#### 我是标题（注意要有空格）',
      'markdown'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      bigtitle: data
    });
  },
  markdownList4: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '``` \n postCategories.stream().map(PostCategories::getPostId).collect(Collectors.toList()); \n ```',
      'markdown'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      code: data
    });
  },
  markdownList5: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '**字体加厚**',
      'markdown'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      huge: data
    });
  },
  markdownList6: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '*字体变斜*',
      'markdown'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      wer: data
    });
  },
  markdownList7: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '++字体下划线++',
      'markdown'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      cool: data
    });
  },
  markdownList8: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '~~字体删除~~',
      'markdown'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      dele: data
    });
  },
  markdownList9: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '- ki <br>- wi',
      'markdown'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      sort: data
    });
  },
  markdownList10: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '1. ki <br>2. wi',
      'markdown'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      unsort: data
    });
  },
  htmlList1: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '<h1>我是标题</h1>',
      'html'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      htitle: data
    });
  },
  htmlList2: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '<h6>我是标题</h6>',
      'html'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      ahtitle: data
    });
  },
  htmlList3: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '<p>我是段落</p>',
      'html'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      hp: data
    });
  },
  htmlList4: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '<strong>字体加厚</strong>',
      'html'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      hwid: data
    });
  },
  htmlList5: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '<ul><li>kiki</li><li>wiwi</li></ul>',
      'html'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      hsort: data
    });
  },
  htmlList6: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '<ol><li>kiki</li><li>wiwi</li></ol>',
      'html'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      husort: data
    });
  },
  htmlList7: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '<q>段落引用</q>',
      'html'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      hcw: data
    });
  },
  htmlList8: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '<table ><tr><th>头1</th><th>头2</th></tr><tr><td>行 1</td><td>行 1</td></tr></table>',
      'html'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      htable: data
    });
  },
  htmlList9: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '🍇🍈🍉🍊🍋🍌',
      'html'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      emoji: data
    });
  },
  htmlList10: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '<p style="color: red">我是红色段落</p>',
      'html'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      hred: data
    });
  },
  htmlList11: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '<p style="font - style: oblique;">字体变斜</p>',
      'html'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      oblique: data
    });
  },
  htmlList12: function () {
    const _ts = this;
    let data = app.towxml.toJson(
      '<img src="https://wechat.kiwi1.cn/wx/storage/fetch/g0qc3cny4aedrqer9zc4.jpg" height="50">我是图片</img>',
      'html'
    );
    data = app.towxml.initData(data, {
      app: _ts
    });
    _ts.setData({
      img: data
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.htmlList1();
    this.htmlList2();
    this.htmlList3();
    this.htmlList4();
    this.htmlList5();
    this.htmlList6();
    this.htmlList7();
    this.htmlList8();
    this.htmlList9();
    this.htmlList10();
    this.htmlList11();
    this.htmlList12();
    this.markdownList1();
    this.markdownList2();
    this.markdownList3();
    this.markdownList4();
    this.markdownList5();
    this.markdownList6();
    this.markdownList7();
    this.markdownList8();
    this.markdownList9();
    this.markdownList10();
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
    return {
      title: 'ShareNotes',
      desc: '大家来学习html和markdown呀',
    }
  }
})