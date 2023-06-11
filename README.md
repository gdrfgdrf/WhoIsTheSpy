<h1>WhoIsTheSpy —— 谁是卧底</h1>

[For English]("https://github.com/gdrfgdrf/WhoIsTheSpy/blob/master/README_EN.md")

灵感来源于B站小潮院长视频：https://www.bilibili.com/video/BV1qS4y1q7Ld

代码参考了BlockParty：https://github.com/lmk02/BlockParty

=======================
=======================

<br><font size=5>游戏的规则非常的简单呐</font></br>
<br><font size=5>就是谁是卧底的问答版</font></br>
<br><font size=5>我们可以<font size=5 color=red>互相提问</font>获得信息</font></br>
<br><font size=5>过程中随时可以发起投票</font></br>
<br><font size=5>投出卧底我们就赢了</font></br>
<br><font size=5>投错了 或者 卧底猜出词了</font></br>
<br><font size=5>卧底就赢了</font></br>
<br><font size=5>游戏的规则就是这么简单</font></br>
<br><font size=5>你 听懂了吗？</font></br>

<br><font size=6>𝖂𝖍𝖊𝖙𝖍𝖊𝖗 𝖞𝖔𝖚 𝖚𝖓𝖉𝖊𝖗𝖘𝖙𝖆𝖓𝖉 𝖙𝖍𝖊 𝖗𝖚𝖑𝖊𝖘 𝖔𝖗 𝖓𝖔𝖙</font></br>
<br><font size=6>𝖏𝖚𝖘𝖙 𝖜𝖆𝖙𝖈𝖍 𝖙𝖍𝖊 𝖛𝖎𝖉𝖊𝖔</font></br>

=======================
=======================

<br><h2>命令列表</h2></br>

| 基本权限              | 默认用户 |
|-------------------|-----:|
| whoisthespy.admin |  管理员 |
| whoisthespy.user  |   用户 |

| 权限                                              |  默认用户  |                对应指令                |     操作     |
|-------------------------------------------------|:------:|:----------------------------------:|:----------:|
| whoisthespy.user.help                           | 用户\管理员 |             /who help              |    显示帮助    |
| whoisthespy.user.join                           | 用户\管理员 |             /who join              |    加入游戏    |
| whoisthespy.user.leave                          | 用户\管理员 |             /who leave             |    离开游戏    |
| whoisthespy.user.list                           | 用户\管理员 |             /who list              |    游戏列表    |
| whoisthespy.admin.help                          |  管理员   |             /who admin             |  显示管理员命令   |
| whoisthespy.admin.create                        |  管理员   |            /who create             |    创建游戏    |
| whoisthespy.admin.delete                        |  管理员   |            /who delete             |    删除游戏    |
| whoisthespy.admin.save                          |  管理员   |             /who save              |    保存游戏    |
| whoisthespy.admin.setName                       |  管理员   |            /who setName            |   设置游戏名字   |
| whoisthespy.admin.setMinPlayer                  |  管理员   |         /who setMinPlayer          |  设置游戏最小人数  |
| whoisthespy.admin.setMaxPlayer                  |  管理员   |         /who setMaxPlayer          |  设置游戏最大人数  |
| whoisthespy.admin.setCountdown                  |  管理员   |         /who setCountdown          |  设置游戏等待时长  |
| whoisthespy.admin.setDuration                   |  管理员   |          /who setDuration          |   设置游戏时长   |
| whoisthespy.admin.setSelectBeQuestionedDuration |  管理员   | /who setSelectBeQuestionedDuration | 设置选择被提问者时长 |
| whoisthespy.admin.setAnswerDuration             |  管理员   |       /who setAnswerDuration       |   设置回答时长   |
| whoisthespy.admin.setVoteDuration               |  管理员   |        /who setVoteDuration        |   设置投票时长   |
| whoisthespy.admin.switch                        |  管理员   |            /who switch             |   切换游戏状态   |
| whoisthespy.admin.stop                          |  管理员   |             /who stop              |   强制停止游戏   |
| whoisthespy.admin.reload                        |  管理员   |            /who reload             |    重载插件    |
| whoisthespy.admin.addSign                       |  管理员   |                 无                  |  添加加入告示牌   |
| whoisthespy.admin.removeSign                    |  管理员   |                 无                  |  移除加入告示牌   |

<br><h2>加入告示牌样式</h2></br>

![chinese_join_sign.png](Picture%2Fchinese_join_sign.png)

<br><h1>依赖</h1></br>

[Anvil GUI]("https://github.com/WesJD/AnvilGUI")

[IF]("https://github.com/stefvanschie/IF")

[Pauhull-Utils]("https://github.com/pauhull/pauhull-utils")

<h2>默认只有中文翻译</h2>

