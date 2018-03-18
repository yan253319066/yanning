本包主要功能
1.Mapper，Service，controller，entity基类
2.封装分页功能
3.net.sf.json日期转换封装
4.驼峰命名转换类 HumpConvert
5.request参数进行封装工具类 RequestParamUtil
6.字符串工具类StringUtils
7.验证码生成
8.公用springmvc跳转页面
9.session国际化
10.增加请求可以为.html
11.shiro. 及权限控制
12.后台国际化调用方法messageResource
13.数据字典
14.登录
15.日志管理
16.excel操作
17.LogInterceptor记录日志每次请求
18.LogAnnotation注解方式记录日志
19.后台通用保存文件上传，其他地方调用attachmentService.save将数据保存到base_attachment表中
20.修改密码
21.修改头像
22.加解密工具类 DESUtils
23.properties文件解密 PropertiesEncryptFactoryBean
24.动态数据源 DataSourceHolder
	示例：DataSourceHolder.setDataSource(DataSourceType.ACTIVITI.getId());
25.系统基础配置管理
26.ZIP操作utils

0.0.4版本
加入访问webservice路径不需要登录
0.0.5版本
增加自定义异常,修改logaspect异常保存
0.0.6版本
LogAspect切面由BaseControllerImpl exceptionHandler代替保存异常日志
