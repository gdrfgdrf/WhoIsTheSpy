<h1>WhoIsTheSpy —— 谁是卧底</h1>

[For Chinese](https://github.com/gdrfgdrf/WhoIsTheSpy/blob/master/README.md)

Inspired by the video of XiaoChaoYuanZhang: https://www.bilibili.com/video/BV1qS4y1q7Ld

The code references BlockParty: https://github.com/lmk02/BlockParty

=======================
=======================

<br><font size=5>The rules of the game are very simple</font></br>
<br><font size=5>It's a Q&A of who's undercover</font></br>
<br><font size=5>We can ask<font size=5 color=red> each other questions </font>to get information</font></br>
<br><font size=5>You can vote at any time during the process</font></br>
<br><font size=5>The vote goes undercover and we win</font></br>
<br><font size=5>The vote was wrong or the undercover agent guessed the words</font></br>
<br><font size=5>The undercover wins</font></br>
<br><font size=5>The rules of the game are that simple</font></br>
<br><font size=5>Do you understand?</font></br>

<br><font size=6>𝖂𝖍𝖊𝖙𝖍𝖊𝖗 𝖞𝖔𝖚 𝖚𝖓𝖉𝖊𝖗𝖘𝖙𝖆𝖓𝖉 𝖙𝖍𝖊 𝖗𝖚𝖑𝖊𝖘 𝖔𝖗 𝖓𝖔𝖙</font></br>
<br><font size=6>𝖏𝖚𝖘𝖙 𝖜𝖆𝖙𝖈𝖍 𝖙𝖍𝖊 𝖛𝖎𝖉𝖊𝖔</font></br>

=======================
=======================

<br><h2>Command list</h2></br>

| Basic Permission  | Default User |
|-------------------|-------------:|
| whoisthespy.user  |         User |
| whoisthespy.admin |        Admin |

| Permission                                      | Default User |              Command               |                 Operation                 |
|-------------------------------------------------|:------------:|:----------------------------------:|:-----------------------------------------:|
| whoisthespy.user.help                           |  User\Admin  |             /who help              |                 Show help                 |
| whoisthespy.user.join                           |  User\Admin  |             /who join              |                 Join game                 |
| whoisthespy.user.leave                          |  User\Admin  |             /who leave             |                Leave game                 |
| whoisthespy.user.list                           |  User\Admin  |             /who list              |                 Game list                 |
| whoisthespy.admin.help                          |    Admin     |             /who admin             |              Show admin help              |
| whoisthespy.admin.create                        |    Admin     |            /who create             |                Create game                |
| whoisthespy.admin.delete                        |    Admin     |            /who delete             |                Delete game                |
| whoisthespy.admin.save                          |    Admin     |             /who save              |                 Save game                 |
| whoisthespy.admin.setName                       |    Admin     |            /who setName            |               Set game name               |
| whoisthespy.admin.setMinPlayer                  |    Admin     |         /who setMinPlayer          |            Set game min player            |
| whoisthespy.admin.setMaxPlayer                  |    Admin     |         /who setMaxPlayer          |            Set game max player            |
| whoisthespy.admin.setCountdown                  |    Admin     |         /who setCountdown          |          Set the game wait time           |
| whoisthespy.admin.setDuration                   |    Admin     |          /who setDuration          |             Set game duration             |
| whoisthespy.admin.setSelectBeQuestionedDuration |    Admin     | /who setSelectBeQuestionedDuration | Set Select the duration of the questioner |
| whoisthespy.admin.setAnswerDuration             |    Admin     |       /who setAnswerDuration       |            Set answer duration            |
| whoisthespy.admin.setVoteDuration               |    Admin     |        /who setVoteDuration        |             Set vote duration             |
| whoisthespy.admin.switch                        |    Admin     |            /who switch             |             Switch game state             |
| whoisthespy.admin.stop                          |    Admin     |             /who stop              |              Force game stop              |
| whoisthespy.admin.reload                        |    Admin     |            /who reload             |               Reload plugin               |
| whoisthespy.admin.addSign                       |    Admin     |                None                |            Add join game sign             |
| whoisthespy.admin.removeSign                    |    Admin     |                None                |           Remove join game sign           |

<br><h2>Join Sign Style</h2></br>

![english_join_sign.png](Picture%2Fenglish_join_sign.png)

```yaml
Locale: zh_cn.yml
#Set language, The file needs to be located under Locale/

Sign:
  UpdateMillis: 500
  #Join sign update frequency, default 500, in milliseconds

Item: #Set what item to use, the set item must exist in the enumeration class Material, if not, will use the default Material
  LEAVE_GAME: RED_BED #Leave the game
  NAVIGATION_BACKGROUND: BLACK_STAINED_GLASS_PANE #Navigation bar background
  NAVIGATION_PREVIOUS: RED_WOOL #Navigation bar - Previous page
  NAVIGATION_COUNTDOWN: NETHER_STAR #Navigation bar - Select answerer countdown
  NAVIGATION_COUNTDOWN_VOTE: NETHER_STAR #Navigation bar - Countdown to voting
  NAVIGATION_NEXT: GREEN_WOOL #Navigation bar - Next page
  END_ANSWER: REDSTONE_BLOCK #End answer
  GUESS_WORD_GUI_PLACEHOLDER: PAPER #Guess word interface placeholder items

ItemSlot: #Set which slot of the player's inventory the item is in
  LEAVE_GAME: 8 #Leave the game
  END_ANSWER: 4 #End answer
  INIT_VOTE: 8 #Call a vote
  INIT_GUESS_WORD: 8 #Guess the word

#In-game chat format
GameChatFormat: "&8[&7%GAME%&8] &7%PLAYER% &8> &r%MESSAGE%"

#Set word
Word:
  - example1
  - example2
  - example3
```

<br><h1>dependencies</h1></br>

[Anvil GUI](https://github.com/WesJD/AnvilGUI)

[IF](https://github.com/stefvanschie/IF)

[Pauhull-Utils](https://github.com/pauhull/pauhull-utils)

<h2>Only Chinese translation by default</h2>
