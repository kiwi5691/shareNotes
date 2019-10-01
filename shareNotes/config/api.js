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
  
  ShowFriendList: WxApiRoot + 'friend/getAll', //获取朋友列表,

  GetPrivateCategory: WxApiRoot + 'category/getAll/'+2, //获取私人目录,
  GetPublicCategory: WxApiRoot + 'category/getAll/'+1, //获取公共目录,
};