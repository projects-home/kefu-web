### 简介


QIQIIM 提供简单快捷的IM方案，快速打造在线IM，可用于公司内网、外网通讯，客服系统等，实现了socket,websocket，能和安卓、IOS应用结合使用，可用于任何商业、个人作品中，请保留作者信息，如果项目帮到了您请加个星，文档简陋，后续会不断完善，如有不明白的可以加群

项目使用springmvc mybatis netty4

数据库  mysql

开发环境 eclipse  tomcat7   jdk1.7

Java后端和js消息统一采用Google Protobuf传输

项目可以直接生成后台代码、页面及js文件，大大节省开发时间

目前实现了单聊、群聊、机器人回复、上下线提醒、离线消息拉取、聊天记录、断线重连等功能，项目已经结合Mysql数据库，聊天的信息会保存到数据库表中，已经跟layim结合完成，如果有更好的需求或想法可以联系我 

###  :question:  **SQL文件找不到、protobuf文件怎么生成，手机访问连接不上的问题，请先查看项目webapp下的help.doc文档** 


![输入图片说明](https://gitee.com/uploads/images/2017/1212/180526_2b8c7f29_1644780.png "1513073046197.png")
QQ群：532696201

###  更新日志

  **版本号v0.4.3** 
 - 修改多用户链接后，导致之前登录的用户掉线问题
 - 修改同一用户多开页面导致消息不能接收的问题 

  **版本号v0.4** 
 - 兼容ie6以上浏览器，使用dwr推送解决websocket在低版本ie下不能访问的问题
 - 更新单聊界面
 - 修复一些小BUG
 - 更新idea下编译找不到xml的问题
 


###  **项目运行图** 


![首页](https://gitee.com/uploads/images/2017/1129/135448_76529268_1644780.png "QQ图片20171129135331.png")
![单聊](https://gitee.com/uploads/images/2017/1212/172531_4921fcf5_1644780.png "单聊.png")
![单聊](https://gitee.com/uploads/images/2017/1124/131757_0d40833f_1644780.png "图片2.png")
![群聊](https://gitee.com/uploads/images/2017/1124/131808_e55369c4_1644780.png "图片3.png")
![机器人](https://gitee.com/uploads/images/2017/1124/131822_f8984f96_1644780.png "图片4.png")
![用户管理](https://gitee.com/uploads/images/2017/1124/131832_59945c72_1644780.png "图片5.png")
![消息管理](https://gitee.com/uploads/images/2017/1124/131851_3ed48ae6_1644780.png "图片6.png")
![代码生成](https://gitee.com/uploads/images/2017/1124/131901_05fbcb63_1644780.png "图片7.png")

 **以下为结合Layim实现的功能截图** 

![输入图片说明](https://gitee.com/uploads/images/2017/1129/135512_76c8644f_1644780.png "QQ图片20171129134102.png")
![输入图片说明](https://gitee.com/uploads/images/2017/1129/135546_8cfed41d_1644780.png "QQ图片20171129134130.png")
![输入图片说明](https://gitee.com/uploads/images/2017/1129/135557_e901c42f_1644780.png "QQ图片20171129134145.png")
![输入图片说明](https://gitee.com/uploads/images/2017/1129/135610_5d6d8a4e_1644780.png "QQ图片20171129134217.png")
![输入图片说明](https://gitee.com/uploads/images/2017/1129/135620_01b1ee66_1644780.png "QQ图片20171129134235.png")
![输入图片说明](https://gitee.com/uploads/images/2017/1129/135628_63b9ce67_1644780.png "QQ图片20171129134351.png")
![输入图片说明](https://gitee.com/uploads/images/2017/1129/135636_58543ad4_1644780.png "QQ图片20171129134502.png")
![输入图片说明](https://gitee.com/uploads/images/2017/1129/135646_202d82d6_1644780.png "QQ图片20171129134512.png")



###  **支持** 

感谢@雷子 100元捐赠



 **QIQIIM参考了多个开源项目，感谢以下开源项目及作者** 


[LayUi](https://gitee.com/sentsin/layui)

[renren代码生成](https://gitee.com/babaio/renren-generator)

[Tcp-gateway](https://github.com/linkedkeeper/tcp-gateway)

[CIM](https://gitee.com/farsunset/cim)

