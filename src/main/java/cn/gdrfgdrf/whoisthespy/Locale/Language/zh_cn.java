package cn.gdrfgdrf.whoisthespy.Locale.Language;

import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.Language;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleSection;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class zh_cn extends Language {

    public static final LocaleSection INFO = new LocaleSection("Info", ChatColor.GRAY);
    public static final LocaleSection COMMANDS = new LocaleSection("Commands", ChatColor.GRAY);
    public static final LocaleSection HEADERS = new LocaleSection("Headers", ChatColor.RED);
    public static final LocaleSection SUCCESS = new LocaleSection("Success", ChatColor.GREEN);
    public static final LocaleSection ERROR = new LocaleSection("Error", ChatColor.RED);
    public static final LocaleSection ITEMS = new LocaleSection("Items", ChatColor.WHITE);
    public static final LocaleSection PLACEHOLDER = new LocaleSection("Placeholder", ChatColor.RED);
    public static final LocaleSection ARGUMENT = new LocaleSection("Argument", ChatColor.YELLOW);
    public static final LocaleSection YELLOW = new LocaleSection("Yellow", ChatColor.YELLOW);

    public static final LocaleString PREFIX = new LocaleString(INFO, "&4&l[&e&l谁是卧底&4&l] &r&8» &r");
    public static final LocaleString ENABLED = new LocaleString(INFO, "&a开启");
    public static final LocaleString DISABLED = new LocaleString(INFO, "&4关闭");
    public static final LocaleString NULL = new LocaleString(INFO, "无");

    public static final LocaleString SUCCESS_CREATE_GAME = new LocaleString(SUCCESS, "创建游戏 %GAME% 成功");
    public static final LocaleString SUCCESS_DELETE_GAME = new LocaleString(SUCCESS, "游戏 %GAME% 删除成功");
    public static final LocaleString SUCCESS_SAVE_GAME = new LocaleString(SUCCESS, "游戏 %GAME% 已保存");
    public static final LocaleString SUCCESS_SET_GAME_NAME = new LocaleString(SUCCESS, "已为名为 %OLD_NAME% 的游戏设置了一个新名字 %NEW_NAME%");
    public static final LocaleString SUCCESS_SET_GAME_MIN_PLAYER = new LocaleString(SUCCESS, "游戏 %GAME% 的最小玩家数已设置为 %MIN%");
    public static final LocaleString SUCCESS_SET_GAME_MIN_PLAYER_WITH_MAX_PLAYER = new LocaleString(SUCCESS, "当前最低玩家数大于最高玩家数，最高玩家数已被设置为 %MIN% + 1");
    public static final LocaleString SUCCESS_SET_GAME_MAX_PLAYER = new LocaleString(SUCCESS, "游戏 %GAME% 的最大玩家数已设置为 %MAX%");
    public static final LocaleString SUCCESS_SET_GAME_COUNTDOWN = new LocaleString(SUCCESS, "游戏 %GAME% 的等待时长已被设置为 %TIME% 秒");
    public static final LocaleString SUCCESS_SET_GAME_DURATION = new LocaleString(SUCCESS, "游戏 %GAME% 的时长已被设置为 %TIME% 秒");
    public static final LocaleString SUCCESS_SET_GAME_SELECT_BE_QUESTIONED_DURATION = new LocaleString(SUCCESS, "游戏 %GAME% 的选择被提问者时长已被设置为 %TIME% 秒");
    public static final LocaleString SUCCESS_SET_GAME_ANSWER_DURATION = new LocaleString(SUCCESS, "游戏 %GAME% 的被提问者回答时长已被设置为 %TIME% 秒");
    public static final LocaleString SUCCESS_SET_GAME_VOTE_DURATION = new LocaleString(SUCCESS, "游戏 %GAME% 的投票时长已被设置为 %TIME% 秒");
    public static final LocaleString SUCCESS_SWITCH_GAME_STATUS = new LocaleString(SUCCESS, "游戏 %GAME% 的状态已切换为：%STATUS%");
    public static final LocaleString SUCCESS_FORCE_STOP_GAME = new LocaleString(SUCCESS, "游戏 %GAME% 已被强制停止");
    public static final LocaleString SUCCESS_ADD_SIGN = new LocaleString(SUCCESS, "成功为游戏 %GAME% 添加了一个加入告示牌");
    public static final LocaleString SUCCESS_REMOVE_SIGN = new LocaleString(SUCCESS, "已移除游戏 %GAME% 的一个加入告示牌");
    public static final LocaleString SUCCESS_RELOAD = new LocaleString(SUCCESS, "重载成功");

    public static final LocaleString ERROR_CREATE_GAME = new LocaleString(ERROR, "游戏 %GAME% 已经存在");
    public static final LocaleString ERROR_NO_GAME = new LocaleString(ERROR, "没有找到游戏");
    public static final LocaleString ERROR_GAME_NOT_EXIST = new LocaleString(ERROR, "该游戏不存在");
    public static final LocaleString ERROR_GAME_IS_ENABLED = new LocaleString(ERROR, "该游戏目前处于开启状态，无法编辑内容");
    public static final LocaleString ERROR_GAME_IS_DISABLED = new LocaleString(ERROR, "该游戏目前处于关闭状态");
    public static final LocaleString ERROR_GAME_IS_NOT_STARTED = new LocaleString(ERROR, "该游戏还未开始");
    public static final LocaleString ERROR_SAVE_GAME = new LocaleString(ERROR, "保存游戏失败");
    public static final LocaleString ERROR_NAME_ALREADY_EXIST = new LocaleString(ERROR, "名为 %NAME% 的游戏已经存在，请另取一个名字");
    public static final LocaleString ERROR_ADD_SIGN = new LocaleString(ERROR, "无法添加加入牌子");
    public static final LocaleString ERROR_REMOVE_SIGN = new LocaleString(ERROR, "无法移除加入牌子");
    public static final LocaleString ERROR_ONLY_INTEGER = new LocaleString(ERROR, "只允许输入数字");
    public static final LocaleString ERROR_IS_NEGATIVE_INTEGER = new LocaleString(ERROR, "输入的数字不能为负数");
    public static final LocaleString ERROR_IS_ZERO_INTEGER = new LocaleString(ERROR, "输入的数字不能为0");
    public static final LocaleString ERROR_MUST_MORE_THAN_OR_EQUALS_INTEGER = new LocaleString(ERROR, "输入的数字必须大于等于 %INTEGER%");
    public static final LocaleString ERROR_MUST_MORE_THAN_OR_EQUALS_MIN_PLAYER = new LocaleString(ERROR, "最高玩家数必须大于等于最低玩家数（%MIN%）");
    public static final LocaleString ERROR_OUT_OF_INTEGER_LIMITED = new LocaleString(ERROR, "超过整型数字限制 %MIN% - %MAX%");
    public static final LocaleString ERROR_NOT_FOUND_WORD = new LocaleString(ERROR, "没有找到词语，请通知管理员添加");
    public static final LocaleString ERROR_NO_PERMISSIONS = new LocaleString(ERROR, "权限不足");
    public static final LocaleString ERROR_COMMAND_NOT_FOUND = new LocaleString(ERROR, "没有找到该指令，使用 /who help 来查看插件帮助");
    public static final LocaleString ERROR_ONLY_PLAYERS = new LocaleString(ERROR, "只有玩家才能执行该指令");
    public static final LocaleString ERROR_SYNTAX = new LocaleString(ERROR, "语法: %SYNTAX%");

    public static final LocaleString ERROR_GAME_NAME_RULES = new LocaleString(ERROR, "以下为游戏名规则：\n" +
            "   1：名字中不能包含 / : * ? \\ \" <> | 中的任意字符\n" +
            "   2：不能大于255个字符");

    public static final LocaleString ERROR_ALREADY_JOINED_GAME = new LocaleString(ERROR, "你已经加入了一个游戏，不能重复加入，使用 /who leave 来退出");
    public static final LocaleString ERROR_GAME_FULL = new LocaleString(ERROR, "该游戏已经满员了");
    public static final LocaleString ERROR_GAME_STARTED = new LocaleString(ERROR, "该游戏已经开始了");
    public static final LocaleString ERROR_NOT_IN_GAME = new LocaleString(ERROR, "你不在游戏中");

    public static final LocaleString COMMAND_FORMAT = new LocaleString(COMMANDS, "&e%SYNTAX% &l&7- &r%DESCRIPTION%");
    public static final LocaleString COMMAND_JOIN_GAME = new LocaleString(COMMANDS, "加入游戏");
    public static final LocaleString COMMAND_LEAVE_GAME = new LocaleString(COMMANDS, "离开游戏");
    public static final LocaleString COMMAND_CREATE_GAME = new LocaleString(COMMANDS, "创建游戏");
    public static final LocaleString COMMAND_DELETE = new LocaleString(COMMANDS, "删除游戏");
    public static final LocaleString COMMAND_SAVE = new LocaleString(COMMANDS, "保存游戏");
    public static final LocaleString COMMAND_LIST_GAME = new LocaleString(COMMANDS, "游戏列表");
    public static final LocaleString COMMAND_SET_GAME_NAME = new LocaleString(COMMANDS, "设置游戏名字");
    public static final LocaleString COMMAND_SET_GAME_MIN_PLAYER = new LocaleString(COMMANDS, "设置游戏最小人数");
    public static final LocaleString COMMAND_SET_GAME_MAX_PLAYER = new LocaleString(COMMANDS, "设置游戏最大人数");
    public static final LocaleString COMMAND_SET_GAME_COUNTDOWN = new LocaleString(COMMANDS, "设置等待时长");
    public static final LocaleString COMMAND_SET_GAME_DURATION = new LocaleString(COMMANDS, "设置游戏时长");
    public static final LocaleString COMMAND_SET_GAME_SELECT_BE_QUESTIONED_DURATION = new LocaleString(COMMANDS, "设置选择被提问者时长");
    public static final LocaleString COMMAND_SET_GAME_ANSWER_DURATION = new LocaleString(COMMANDS, "设置被提问者回答时长");
    public static final LocaleString COMMAND_SET_GAME_VOTE_DURATION = new LocaleString(COMMANDS, "设置投票时长");
    public static final LocaleString COMMAND_SWITCH_GAME_STATUS = new LocaleString(COMMANDS, "切换游戏状态");
    public static final LocaleString COMMAND_FORCE_STOP_GAME = new LocaleString(COMMANDS, "强制停止游戏");
    public static final LocaleString COMMAND_ADMIN = new LocaleString(COMMANDS, "显示所有管理员命令");
    public static final LocaleString COMMAND_HELP = new LocaleString(COMMANDS, "显示插件帮助");
    public static final LocaleString COMMAND_RELOAD = new LocaleString(COMMANDS, "重载插件");

    public static final LocaleString HEADER_HELP = new LocaleString(HEADERS, "&l谁是卧底指令");
    public static final LocaleString HEADER_ADMIN = new LocaleString(HEADERS, "&l管理员指令");
    public static final LocaleString HEADER_GAMES_LIST = new LocaleString(HEADERS, "&l游戏列表");

    public static final LocaleString SCOREBOARD_WAITING = new LocaleString(INFO, "&3谁是卧底", "玩家数: %CURRENT_PLAYER% / %MAX_PLAYER%", "开始倒计时: %TIME%", "游戏: %GAME%");
    public static final LocaleString SCOREBOARD_INGAME_FOR_GOOD = new LocaleString(INFO, "&4&l谁是卧底", "&7剩余时间: %TIME%", "&e你的身份: &2&l%CHARACTER%", "&e你的词语: &2&l%WORD%", "&e当前提问者: &2&l%PLAYER%", "&e当前被提问者: &2&l%BE_QUESTIONED%", "", "&e&l你的任务是找出卧底并发起投票", "", "游戏: %GAME%");
    public static final LocaleString SCOREBOARD_INGAME_FOR_UNDERCOVER = new LocaleString(INFO, "&4&l谁是卧底", "&7剩余时间: %TIME%", "&e你的身份: &4&l%CHARACTER%", "&e当前提问者: &2&l%PLAYER%", "&e当前被提问者: &2&l%BE_QUESTIONED%", "", "&4&l你的任务是隐藏好自己不被好人投票", "", "游戏: %GAME%");

    public static final LocaleString SIGN_DISABLED = new LocaleString(INFO, "&4[%GAME%]", "&8&l%CURRENT_PLAYER%/%MAX_PLAYER%", "&4• 未开启 •", "");
    public static final LocaleString SIGN_WAITING = new LocaleString(INFO, "&5[%GAME%]", "&8&l%CURRENT_PLAYER%/%MAX_PLAYER%", "&5• 等待中 •", "");
    public static final LocaleString SIGN_FULL = new LocaleString(INFO, "&4[%GAME%]", "&8&l%CURRENT_PLAYER%/%MAX_PLAYER%", "&5• 已满员 •", "");
    public static final LocaleString SIGN_PREPARING = new LocaleString(INFO, "&8[%GAME%]", "&8&l%CURRENT_PLAYER%/%MAX_PLAYER%", "&8• 准备中 •", "");
    public static final LocaleString SIGN_STARTED = new LocaleString(INFO, "&8[%GAME%]", "&8&l%CURRENT_PLAYER%/%MAX_PLAYER%", "&8• 正在游戏 •", "");

    public static final LocaleString PLAYER_JOINED_GAME = new LocaleString(INFO, "玩家 %PLAYER% 加入了游戏");
    public static final LocaleString PLAYER_LEAVED_GAME = new LocaleString(INFO, "玩家 %PLAYER% 离开了游戏");
    public static final LocaleString PLAYER_LEFT_GAME = new LocaleString(INFO, "已离开游戏 %GAME%");
    public static final LocaleString ADMINISTRATOR_DISABLED_GAME = new LocaleString(ERROR, "管理员关闭了该游戏");
    public static final LocaleString ADMINISTRATOR_FORCE_STOP = new LocaleString(ERROR, "管理员强制停止了该游戏");
    public static final LocaleString TIME_LEFT = new LocaleString(INFO, "距离开始游戏还有 %TIME% 秒");
    public static final LocaleString WAITING_FOR_PLAYER = new LocaleString(ERROR, "等待玩家中");
    public static final LocaleString COUNTDOWN_ABORTED = new LocaleString(ERROR, "由于人数不足，开始游戏倒计时已重置");
    public static final LocaleString GAME_STARTED = new LocaleString(SUCCESS, "游戏开始");
    public static final LocaleString GET_RANDOM_CHARACTER = new LocaleString(SUCCESS, "正在抽取身份");
    public static final LocaleString GAME_END_PLAYER_LEAVE = new LocaleString(ERROR, "由于玩家退出，游戏结束");
    public static final LocaleString GAME_END_VOTE_FINISHED = new LocaleString(SUCCESS, "投票结束");

    public static final LocaleString SPACE = new LocaleString(INFO, "                ");
    public static final LocaleString GOOD_LIST_FORMAT = new LocaleString(INFO, "&7- &e%PLAYER%&r\n");
    public static final LocaleString VOTE_LIST_FORMAT = new LocaleString(INFO, "&7- &e%VOTER% &7--> &4%BE_VOTED%\n");
    public static final LocaleString NOT_VOTE_LIST_FORMAT = new LocaleString(INFO, "&7- &e%PLAYER%&r\n");
    public static final LocaleString ABSTAIN_LIST_FORMAT = new LocaleString(INFO, "&7- &e%PLAYER%&r\n");

    public static final LocaleString SHOW_GAME_INFO = new LocaleString(INFO,
            "\n       &7=============== &l&2这局游戏的信息 &r&7===============\n" +
                    "               游戏: %GAME%\n" +
                    "               用时: %SPEND_TIME%\n" +
                    "               词语: &e%WORD%\n" +
                    "               &7卧底猜词: &e%GUESS_WORD%\n" +
                    "               &7好人列表: \n%GOOD_LIST%" +
                    "               &7投票情况: \n%VOTE_LIST%\n" +
                    "               &7没有投票的玩家: \n%NOT_VOTE_LIST%\n" +
                    "               &7弃权的玩家: \n%ABSTAIN_LIST%\n" +
                    "               &7卧底: &4%UNDERCOVER%&r\n" +
                    "       &7=============== &l&2这局游戏的信息 &r&7===============");

    public static final LocaleString ARGUMENT_PLACEHOLDER_START = new LocaleString(PLACEHOLDER, "<");
    public static final LocaleString ARGUMENT_PLACEHOLDER_END = new LocaleString(PLACEHOLDER, ">");
    public static final LocaleString NAME = new LocaleString(ARGUMENT, "&l名称");
    public static final LocaleString OLD_NAME = new LocaleString(ARGUMENT, "&l旧名称");
    public static final LocaleString NEW_NAME = new LocaleString(ARGUMENT, "&l新名称");
    public static final LocaleString INTEGER = new LocaleString(ARGUMENT, "&l数字");

    public static final LocaleString GOOD = new LocaleString(YELLOW, "好人");
    public static final LocaleString UNDERCOVER = new LocaleString(ERROR, "卧底");
    public static final LocaleString CHARACTER_TITLE = new LocaleString(SUCCESS, "你的身份");
    public static final LocaleString WORD_TITLE = new LocaleString(SUCCESS, "你的词语");
    public static final LocaleString WORD = new LocaleString(YELLOW, "");
    public static final LocaleString QUESTIONER_TITLE = new LocaleString(INFO, "&a提问者");
    public static final LocaleString QUESTIONER_TO_BE_QUESTIONED_TITLE = new LocaleString(INFO, "&a被提问者");
    public static final LocaleString QUESTIONER_TO_BE_QUESTIONED_MESSAGE = new LocaleString(INFO, "提问: &e%QUESTIONER% &l&4--> &r&e%BE_QUESTIONED%");
    public static final LocaleString ALREADY_SELECT_BE_QUESTIONED = new LocaleString(ERROR, "你已经选择了被提问者");
    public static final LocaleString QUESTIONER_SELECT_BE_QUESTIONED_TIMEOUT = new LocaleString(ERROR, "%QUESTIONER% 选择超时，已随机选择 %BE_QUESTIONED% 为被提问者");
    public static final LocaleString NOT_QUESTIONER = new LocaleString(ERROR, "你不是提问者");
    public static final LocaleString CANNOT_SELECT_SELF = new LocaleString(ERROR, "不能选择自己");
    public static final LocaleString PLAYER_END_ANSWER = new LocaleString(SUCCESS, "%PLAYER% 结束了回答，正在选择下一位被提问者");
    public static final LocaleString ANSWER_COUNTDOWN_ACTIONBAR = new LocaleString(INFO, "剩余回答时间: %TIME% ，若超时将会强制进入选择被提问者");
    public static final LocaleString ANSWER_COUNTDOWN_TIMEOUT = new LocaleString(ERROR, "%BE_QUESTIONED% 回答超时，已强制进入选择被提问者");
    public static final LocaleString NOT_GOOD = new LocaleString(ERROR, "你不是好人");
    public static final LocaleString NOT_UNDERCOVER = new LocaleString(ERROR, "你不是卧底");
    public static final LocaleString PLAYER_INIT_VOTE_TITLE = new LocaleString(INFO, "&a投票");
    public static final LocaleString PLAYER_INIT_VOTE_SUBTITLE = new LocaleString(INFO, "&4玩家 &e%PLAYER% &4发起了投票");
    public static final LocaleString PLAYER_INIT_VOTE = new LocaleString(SUCCESS, "&4玩家 &e%PLAYER% &4发起了投票");
    public static final LocaleString ALREADY_INIT_VOTE = new LocaleString(ERROR, "你已经发起了投票");
    public static final LocaleString ALREADY_VOTED = new LocaleString(ERROR, "你已经投票了");
    public static final LocaleString VOTE_TITLE = new LocaleString(INFO, "&a投票");
    public static final LocaleString VOTE_SUBTITLE = new LocaleString(INFO, "&e%VOTER% &l&4--> &r&e%BE_VOTED%");
    public static final LocaleString VOTE_MESSAGE = new LocaleString(INFO, "投票: &e%VOTER% &l&4--> &r&e%BE_VOTED%");
    public static final LocaleString VOTE_TIMEOUT = new LocaleString(ERROR, "%PLAYER% 投票超时，视为弃权");
    public static final LocaleString VOTE_ABSTAIN = new LocaleString(ERROR, "你已弃权");
    public static final LocaleString ALREADY_GUESS_WORD = new LocaleString(ERROR, "你已经猜词了");
    public static final LocaleString GUESS_WORD_TYPE_FULL = new LocaleString(ERROR, "请输入内容");
    public static final LocaleString UNDERCOVER_GUESS_WORD_MESSAGE = new LocaleString(INFO, "&4卧底猜词: %WORD%");

    public static final LocaleString ITEM_LEAVE_GAME = new LocaleString(ITEMS, "&4离开游戏");
    public static final LocaleString ITEM_LEAVE_GAME_LORE = new LocaleString(ITEMS, "&e右键点击该物品来退出游戏");
    public static final LocaleString ITEM_NAVIGATION_BACKGROUND = new LocaleString(ITEMS, "");
    public static final LocaleString ITEM_NAVIGATION_BACKGROUND_LORE = new LocaleString(ITEMS, "");
    public static final LocaleString ITEM_NAVIGATION_PREVIOUS = new LocaleString(ITEMS, "&c上一页");
    public static final LocaleString ITEM_NAVIGATION_PREVIOUS_LORE = new LocaleString(ITEMS, "");
    public static final LocaleString ITEM_NAVIGATION_COUNTDOWN = new LocaleString(ITEMS, "&3在该页面选择被提问者");
    public static final LocaleString ITEM_NAVIGATION_COUNTDOWN_LORE = new LocaleString(ITEMS, "&5剩余选择时间: %TIME%", "若超时未选择将会随机选择一位玩家作为被提问者");
    public static final LocaleString ITEM_NAVIGATION_COUNTDOWN_VOTE = new LocaleString(ITEMS, "&3在该页面选择你要投票的玩家");
    public static final LocaleString ITEM_NAVIGATION_COUNTDOWN_VOTE_LORE = new LocaleString(ITEMS, "&5剩余选择时间: %TIME%", "若超时则视为弃权");
    public static final LocaleString ITEM_NAVIGATION_NEXT = new LocaleString(ITEMS, "&a下一页");
    public static final LocaleString ITEM_NAVIGATION_NEXT_LORE = new LocaleString(ITEMS, "");
    public static final LocaleString ITEM_END_ANSWER = new LocaleString(ITEMS, "&c结束回答&7（下一个被提问者）");
    public static final LocaleString ITEM_END_ANSWER_LORE = new LocaleString(ITEMS, "&e结束本轮回答并选择下一个被提问者");
    public static final LocaleString ITEM_INIT_VOTE = new LocaleString(ITEMS, "&4发起投票（右键点击）");
    public static final LocaleString ITEM_INIT_VOTE_LORE = new LocaleString(ITEMS, "&e发起投票后请选择被投票玩家，完成投票之前将无法关闭投票界面");
    public static final LocaleString ITEM_INIT_GUESS_WORD = new LocaleString(ITEMS, "&4猜词（右键点击）");
    public static final LocaleString ITEM_INIT_GUESS_WORD_LORE = new LocaleString(ITEMS, "&e点击后会出现铁砧界面，输入你猜的词语并点击右方的物品");
    public static final LocaleString ITEM_GUESS_WORD_GUI_PLACEHOLDER = new LocaleString(ITEMS, "");
    public static final LocaleString ITEM_GUESS_WORD_GUI_PLACEHOLDER_LORE = new LocaleString(ITEMS, "");

    public static final LocaleString INVENTORY_QUESTIONER_SELECT_BE_QUESTIONED_TITLE = new LocaleString(INFO, "请选择被提问者");
    public static final LocaleString INVENTORY_SELECT_BE_VOTE_TITLE = new LocaleString(INFO, "请选择你要投票的玩家");
    public static final LocaleString INVENTORY_TEXT_INPUT_GUESS_WORD_TITLE = new LocaleString(INFO, "请输入你猜的词语");

    public static final LocaleString PLAYER_HEAD_PREFIX = new LocaleString(INFO, "&a%PLAYER%");
    public static final LocaleString PLAYER_HEAD_LORE = new LocaleString(INFO, "&e点击选择该玩家");

    public static final LocaleString GAME_END_TITLE = new LocaleString(INFO, "&a游戏结束");

    public static final LocaleString UNDERCOVER_WIN_MESSAGE = new LocaleString(INFO, "&a卧底猜出了词语，卧底胜利");
    public static final LocaleString UNDERCOVER_WIN_SUBTITLE = new LocaleString(INFO, "&a卧底猜出了词语");

    public static final LocaleString UNDERCOVER_GUESS_WRONG_LOSE_MESSAGE = new LocaleString(INFO, "&4卧底猜错了词语，卧底失败");
    public static final LocaleString UNDERCOVER_GUESS_WRONG_LOSE_SUBTITLE = new LocaleString(INFO, "&4卧底猜错了词语");

    public static final LocaleString GAME_DRAW_MESSAGE = new LocaleString(INFO, "时间到，平局");
    public static final LocaleString GAME_DRAW_SUBTITLE = new LocaleString(INFO, "时间到");

    public static final LocaleString GOOD_WIN_MESSAGE = new LocaleString(INFO, "&a好人投出了卧底，好人胜利");
    public static final LocaleString GOOD_WIN_SUBTITLE = new LocaleString(INFO, "&a好人投出了卧底");

    public static final LocaleString GOOD_VOTE_WRONG_LOSE_MESSAGE = new LocaleString(INFO, "&4好人投票失败，卧底胜利");
    public static final LocaleString GOOD_VOTE_WRONG_LOSE_SUBTITLE = new LocaleString(INFO, "&4好人投票失败");

    public static void writeTo(File file) {
        try {
            writeTo(zh_cn.class, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
