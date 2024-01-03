# 模式切换

* 演出模式
  ALT + V -> 选择Enter Presentation Mode -> ALT + V -> 选择Exit Presentation Mode
* 全屏模式
  ALT + V -> 选择Enter Full Screen -> ALT + V -> 选择Exit Full Screen

# 常用快捷键

* 搜索

| 快捷键                 | 介绍                         |
| :--------------------- | :--------------------------- |
| Ctrl + F               | 在当前文件进行文本查找       |
| Ctrl + R               | 在当前文件进行文本替换       |
| Shift + Ctrl + F       | 在项目进行文本查找           |
| Shift + Ctrl + R       | 在项目进行文本替换           |
| Shift + Shift          | 快速搜索                     |
| Ctrl + N               | 查找class                    |
| Ctrl + Shift + N       | 查找文件                     |
| Ctrl + Shift + Alt + N | 查找symbol（查找某个方法名） |

* 跳转

| 快捷键           | 介绍                  |
| :--------------- | :-------------------- |
| Ctrl + E         | 最近文件              |
| Ctrl + Tab       | 切换文件              |
| Ctrl + Alt + ←/→ | 跳转历史光标所在处    |
| Alt + ←/→ 方向键 | 切换子tab             |
| Ctrl + G         | go to（跳转指定行号） |
| CTRL + ALT + B   | 跳转实现类            |

* 编码

| 快捷键                      | 介绍                                                         |
| :-------------------------- | :----------------------------------------------------------- |
| Ctrl + W                    | 智能的扩大选中代码范围                                       |
| (Shift + Ctrl) + Alt + J    | 快速选中同文本                                               |
| Ctrl + C/Ctrl + X/Ctrl + D  | 快速复制或剪切                                               |
| Ctrl + Y                    | 删除整行                                                     |
| 滚轮点击变量/方法/类        | 快速进入变量/方法/类的定义处                                 |
| Shift + 点击Tab/ Ctrl + F4  | 快速关闭tab                                                  |
| Ctrl + Z 、Ctrl + Shift + Z | 后悔药，撤销/取消撤销                                        |
| Alt + enter                 | 快速修复                                                     |
| Alt + ↑/↓                   | 方法快速跳转                                                 |
| CTRL + SHIFT + ↑↓           | 代码上下移动                                                 |
| F2                          | 跳转到下一个高亮错误 或 警告位置                             |
| Alt + Insert                | 代码自动生成，如生成对象的 set / get 方法，构造函数，toString() 等 |
| Ctrl + Alt + L              | 格式化代码                                                   |
| Shift + F6                  | 快速修改方法名、变量名、文件名、类名等                       |
| CTRL + ALT + O              | 删除无用导包                                                 |
| CTRL + SHIFT + u            | 大小写转换                                                   |
| Ctrl + Alt + T              | 代码块包裹功能                                               |

* 代码阅读

| 快捷键                     | 介绍                           |
| :------------------------- | :----------------------------- |
| Ctrl + P                   | 方法参数提示显示               |
| Alt + F7                   | 可以列出变量在哪些地方被使用了 |
| 光标在子类接口名，Ctrl + u | 跳到父类接口                   |
| Alt + F1 + 1               | project视图                    |
| (Shift) + Ctrl + +/-       | 代码块折叠                     |
| Ctrl + Alt + B             | 跳转方法定义/实现              |
| Ctrl + H                   | 类的层级关系                   |
| Ctrl + F12                 | Show Members 类成员快速显示    |

# 实用小技巧

".for" -> 遍历集合
".null" -> 判断对象是否为null
".nn" -> 判断对象是否不为null
".if" -> 判断boolean对象
".sout" -> 输出对象

# 调试技巧

CTRL + F8 -> 打/取消断点
Show Execution Point (Alt + F10) -> 如果你的光标在其它行或其它页面，点击这个按钮可跳转到当前代码执行的行
Step Over (F8) -> 步过，一行一行地往下走，如果这一行上有方法不会进入方法
Step Into (F7) -> 步入，如果当前行有方法，可以进入方法内部，一般用于进入自定义方法内，不会进入官方类库的方法
Force Step Into (Alt + Shift + F7) -> 强制步入，能进入任何方法，查看底层源码的时候可以用这个进入官方类库的方法
Smart Step Into (Shift + F7) -> 智能步入,会自动定位到当前断点行，并列出需要进入的方法
Step Out (Shift + F8) -> 步出，从步入的方法内退出到方法调用处，此时方法已执行完毕，只是还没有完成赋值
Run to Cursor (Alt + F9) -> 运行到光标处，你可以将光标定位到你需要查看的那一行，然后使用这个功能，代码会运行至光标行，而不需要打断点
Evaluate Expression (Alt + F8) -> 计算表达式
View Breakpoints (Ctrl + Shift + F8) -> 查看所有断点
Update application (Ctrl + F5) -> 更新程序，一般在你的代码有改动后可执行这个功能
Resume Program (F9) -> 恢复程序，比如，你在第20行和25行有两个断点，当前运行至第20行，按F9，则运行到下一个断点(即第25行)，再按F9，则运行完整个流程，因为后面已经没有断点了

# 常用配置

* 代码提示不区分大小写

 Settings -> Editor -> General -> Code Completion 

* 自动导包功能及相关优化功能

 Settings -> Editor -> General -> Auto Import 

* CTRL + 滑动滚轮 调整窗口显示大小

 Settings -> Editor -> General -> Change font size (Zoom) with Ctrl+Mouse wheel 

* tab 多行显示

 Window -> Editor Tabs -> Tabs Placement，取消勾选 Show Tabs In Single Row选项 

* 代码编辑区显示行号

 Settings -> Editor -> General -> Appearance `勾选 `Show Line Numbers 

* 隐藏不想看到的文件/文件夹

Settings -> Editor -> File Types

* 文件代码模板

 Settings -> Editor -> File and Code Template 

# 插件推荐

#### Alibaba Java Coding Guidelines

阿里Java编程规约插件

#### FindBugs

代码缺陷扫描

#### lombok plugin

lombok 插件

#### maven helper

maven 依赖管理助手 ，解析maven pom结构，分析冲突；

#### Translation

翻译插件，阅读源码必备

#### GsonFormat

通过json快速生成java bean

