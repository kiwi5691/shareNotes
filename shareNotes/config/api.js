// 本机开发时使用
var WxApiRoot = 'http://localhost:8080/wx/';

// 云平台部署时使用
// var WxApiRoot = 'http://3.14.127.134:8080/wx/';
// 云平台上线时使用
// var WxApiRoot = 'https://www.kiwi1.cn/wx/';

module.exports = {
  IndexUrl: WxApiRoot + 'home/index', //首页数据接口

  AuthLoginByWeixin: WxApiRoot + 'auth/login_by_weixin', //微信登录
  AuthRegister: WxApiRoot + 'auth/register', //账号注册
  AuthReset: WxApiRoot + 'auth/reset', //账号密码重置
  AuthRegisterCaptcha: WxApiRoot + 'auth/regCaptcha', //验证码
  AuthBindPhone: WxApiRoot + 'auth/bindPhone', //绑定微信手机号

  StorageUpload: WxApiRoot + 'storage/upload', //图片上传,

  UserIndex: WxApiRoot + 'user/index', //个人页面用户相关信息
  SubmitIssue: WxApiRoot + 'user/submitIssue', //提交issue
  ShowFriendList: WxApiRoot + 'friend/getAll', //获取朋友列表,

  GetPrivateCategory: WxApiRoot + 'category/getAll/' + 2, //获取私人目录,
  GetPublicCategory: WxApiRoot + 'category/getAll/' + 1, //获取公共目录,
  GetPostsAll: WxApiRoot + 'posts/getAll/', //获取所有posts,
  GetPostsDetail: WxApiRoot + 'posts/getDetail/', //获取posts详细,


  GetFriendPublicCategory: WxApiRoot + 'friend/getAll/menu/', //获取朋友共有目录,
  GetFriendPublicPosts: WxApiRoot + 'friend/getPost/', //获取朋友所有posts,
  GetFriendDetail: WxApiRoot + 'friend/getDetail/', //获取朋友posts详细,

  
  AddCategory: WxApiRoot + 'category/add', //添加目录,
  UpdateCategory: WxApiRoot + 'category/update', //修改目录,
  DelCategory: WxApiRoot + 'category/delete', //删除目录,
  GetCategoryDetail: WxApiRoot + 'category/detail/', //获取目录详细,

  AddPost: WxApiRoot + 'posts/add', //添加文章,

};