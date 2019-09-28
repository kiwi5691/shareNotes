# 关于接口
参照 WxUserFormId 写法
两个注解，也可以去掉api详细参数注解也就是

```
 @ApiImplicitParams({
            @ApiImplicitParam(name ="userId",value = "用户id",paramType = "path",required = true,dataType = "Integer")
    })
```

但是必须保留
```
    @ApiOperation(value = "通过微信openid登录")

```
# 访问
```
http://localhost:8080/swagger-ui.html
```
来查看接口