package cn.gdrfgdrf.whoisthespy.Locale;

import cn.gdrfgdrf.whoisthespy.Locale.Language.zh_cn;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.Locale;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;

import java.io.File;

public class WhoIsTheSpyLocale extends Locale {
    public static LocaleString PREFIX;
    public static LocaleString ENABLED;
    public static LocaleString DISABLED;
    public static LocaleString NULL;

    public static LocaleString SUCCESS_CREATE_GAME;
    public static LocaleString SUCCESS_DELETE_GAME;
    public static LocaleString SUCCESS_SAVE_GAME;
    public static LocaleString SUCCESS_SET_GAME_NAME;
    public static LocaleString SUCCESS_SET_GAME_MIN_PLAYER;
    public static LocaleString SUCCESS_SET_GAME_MIN_PLAYER_WITH_MAX_PLAYER;
    public static LocaleString SUCCESS_SET_GAME_MAX_PLAYER;
    public static LocaleString SUCCESS_SET_GAME_COUNTDOWN;
    public static LocaleString SUCCESS_SET_GAME_DURATION;
    public static LocaleString SUCCESS_SET_GAME_SELECT_BE_QUESTIONED_DURATION;
    public static LocaleString SUCCESS_SET_GAME_ANSWER_DURATION;
    public static LocaleString SUCCESS_SET_GAME_VOTE_DURATION;
    public static LocaleString SUCCESS_SWITCH_GAME_STATUS;
    public static LocaleString SUCCESS_FORCE_STOP_GAME;
    public static LocaleString SUCCESS_ADD_SIGN;
    public static LocaleString SUCCESS_REMOVE_SIGN;
    public static LocaleString SUCCESS_RELOAD;

    public static LocaleString ERROR_CREATE_GAME;
    public static LocaleString ERROR_NO_GAME;
    public static LocaleString ERROR_GAME_NOT_EXIST;
    public static LocaleString ERROR_GAME_IS_ENABLED;
    public static LocaleString ERROR_GAME_IS_DISABLED;
    public static LocaleString ERROR_GAME_IS_NOT_STARTED;
    public static LocaleString ERROR_SAVE_GAME;
    public static LocaleString ERROR_NAME_ALREADY_EXIST;
    public static LocaleString ERROR_ADD_SIGN;
    public static LocaleString ERROR_REMOVE_SIGN;
    public static LocaleString ERROR_ONLY_INTEGER;
    public static LocaleString ERROR_IS_NEGATIVE_INTEGER;
    public static LocaleString ERROR_IS_ZERO_INTEGER;
    public static LocaleString ERROR_MUST_MORE_THAN_OR_EQUALS_INTEGER;
    public static LocaleString ERROR_MUST_MORE_THAN_OR_EQUALS_MIN_PLAYER;
    public static LocaleString ERROR_OUT_OF_INTEGER_LIMITED;
    public static LocaleString ERROR_NOT_FOUND_WORD;
    public static LocaleString ERROR_NO_PERMISSIONS;
    public static LocaleString ERROR_COMMAND_NOT_FOUND;
    public static LocaleString ERROR_ONLY_PLAYERS;
    public static LocaleString ERROR_SYNTAX;

    public static LocaleString ERROR_GAME_NAME_RULES;

    public static LocaleString ERROR_ALREADY_JOINED_GAME;
    public static LocaleString ERROR_GAME_FULL;
    public static LocaleString ERROR_GAME_STARTED;
    public static LocaleString ERROR_NOT_IN_GAME;

    public static LocaleString COMMAND_FORMAT;
    public static LocaleString COMMAND_JOIN_GAME;
    public static LocaleString COMMAND_LEAVE_GAME;
    public static LocaleString COMMAND_CREATE_GAME;
    public static LocaleString COMMAND_DELETE;
    public static LocaleString COMMAND_SAVE;
    public static LocaleString COMMAND_LIST_GAME;
    public static LocaleString COMMAND_SET_GAME_NAME;
    public static LocaleString COMMAND_SET_GAME_MIN_PLAYER;
    public static LocaleString COMMAND_SET_GAME_MAX_PLAYER;
    public static LocaleString COMMAND_SET_GAME_COUNTDOWN;
    public static LocaleString COMMAND_SET_GAME_DURATION;
    public static LocaleString COMMAND_SET_GAME_SELECT_BE_QUESTIONED_DURATION;
    public static LocaleString COMMAND_SET_GAME_ANSWER_DURATION;
    public static LocaleString COMMAND_SET_GAME_VOTE_DURATION;
    public static LocaleString COMMAND_SWITCH_GAME_STATUS;
    public static LocaleString COMMAND_FORCE_STOP_GAME;
    public static LocaleString COMMAND_ADMIN;
    public static LocaleString COMMAND_HELP;
    public static LocaleString COMMAND_RELOAD;

    public static LocaleString HEADER_HELP;
    public static LocaleString HEADER_ADMIN;
    public static LocaleString HEADER_GAMES_LIST;

    public static LocaleString SCOREBOARD_WAITING;
    public static LocaleString SCOREBOARD_INGAME_FOR_GOOD;
    public static LocaleString SCOREBOARD_INGAME_FOR_UNDERCOVER;

    public static LocaleString SIGN_DISABLED;
    public static LocaleString SIGN_WAITING;
    public static LocaleString SIGN_FULL;
    public static LocaleString SIGN_PREPARING;
    public static LocaleString SIGN_STARTED;

    public static LocaleString PLAYER_JOINED_GAME;
    public static LocaleString PLAYER_LEAVED_GAME;
    public static LocaleString PLAYER_LEFT_GAME;
    public static LocaleString ADMINISTRATOR_DISABLED_GAME;
    public static LocaleString ADMINISTRATOR_FORCE_STOP;
    public static LocaleString TIME_LEFT;
    public static LocaleString WAITING_FOR_PLAYER;
    public static LocaleString COUNTDOWN_ABORTED;
    public static LocaleString GAME_STARTED;
    public static LocaleString GET_RANDOM_CHARACTER;
    public static LocaleString GAME_END_PLAYER_LEAVE;
    public static LocaleString GAME_END_VOTE_FINISHED;

    public static LocaleString SPACE;
    public static LocaleString GOOD_LIST_FORMAT;
    public static LocaleString VOTE_LIST_FORMAT;
    public static LocaleString NOT_VOTE_LIST_FORMAT;
    public static LocaleString ABSTAIN_LIST_FORMAT;

    public static LocaleString SHOW_GAME_INFO;

    public static LocaleString ARGUMENT_PLACEHOLDER_START;
    public static LocaleString ARGUMENT_PLACEHOLDER_END;
    public static LocaleString NAME;
    public static LocaleString OLD_NAME;
    public static LocaleString NEW_NAME;
    public static LocaleString INTEGER;

    public static LocaleString GOOD;
    public static LocaleString UNDERCOVER;
    public static LocaleString CHARACTER_TITLE;
    public static LocaleString WORD_TITLE;
    public static LocaleString WORD;
    public static LocaleString QUESTIONER_TITLE;
    public static LocaleString QUESTIONER_TO_BE_QUESTIONED_TITLE;
    public static LocaleString QUESTIONER_TO_BE_QUESTIONED_MESSAGE;
    public static LocaleString ALREADY_SELECT_BE_QUESTIONED;
    public static LocaleString QUESTIONER_SELECT_BE_QUESTIONED_TIMEOUT;
    public static LocaleString NOT_QUESTIONER;
    public static LocaleString CANNOT_SELECT_SELF;
    public static LocaleString PLAYER_END_ANSWER;
    public static LocaleString ANSWER_COUNTDOWN_ACTIONBAR;
    public static LocaleString ANSWER_COUNTDOWN_TIMEOUT;
    public static LocaleString NOT_GOOD;
    public static LocaleString NOT_UNDERCOVER;
    public static LocaleString PLAYER_INIT_VOTE_TITLE;
    public static LocaleString PLAYER_INIT_VOTE_SUBTITLE;
    public static LocaleString PLAYER_INIT_VOTE;
    public static LocaleString ALREADY_INIT_VOTE;
    public static LocaleString ALREADY_VOTED;
    public static LocaleString VOTE_TITLE;
    public static LocaleString VOTE_SUBTITLE;
    public static LocaleString VOTE_MESSAGE;
    public static LocaleString VOTE_TIMEOUT;
    public static LocaleString VOTE_ABSTAIN;
    public static LocaleString ALREADY_GUESS_WORD;
    public static LocaleString GUESS_WORD_TYPE_FULL;
    public static LocaleString UNDERCOVER_GUESS_WORD_MESSAGE;

    public static LocaleString ITEM_LEAVE_GAME;
    public static LocaleString ITEM_LEAVE_GAME_LORE;
    public static LocaleString ITEM_NAVIGATION_BACKGROUND;
    public static LocaleString ITEM_NAVIGATION_BACKGROUND_LORE;
    public static LocaleString ITEM_NAVIGATION_PREVIOUS;
    public static LocaleString ITEM_NAVIGATION_PREVIOUS_LORE;
    public static LocaleString ITEM_NAVIGATION_COUNTDOWN;
    public static LocaleString ITEM_NAVIGATION_COUNTDOWN_LORE;
    public static LocaleString ITEM_NAVIGATION_COUNTDOWN_VOTE;
    public static LocaleString ITEM_NAVIGATION_COUNTDOWN_VOTE_LORE;
    public static LocaleString ITEM_NAVIGATION_NEXT;
    public static LocaleString ITEM_NAVIGATION_NEXT_LORE;
    public static LocaleString ITEM_END_ANSWER;
    public static LocaleString ITEM_END_ANSWER_LORE;
    public static LocaleString ITEM_INIT_VOTE;
    public static LocaleString ITEM_INIT_VOTE_LORE;
    public static LocaleString ITEM_INIT_GUESS_WORD;
    public static LocaleString ITEM_INIT_GUESS_WORD_LORE;
    public static LocaleString ITEM_GUESS_WORD_GUI_PLACEHOLDER;
    public static LocaleString ITEM_GUESS_WORD_GUI_PLACEHOLDER_LORE;

    public static LocaleString INVENTORY_QUESTIONER_SELECT_BE_QUESTIONED_TITLE;
    public static LocaleString INVENTORY_SELECT_BE_VOTE_TITLE;
    public static LocaleString INVENTORY_TEXT_INPUT_GUESS_WORD_TITLE;

    public static LocaleString PLAYER_HEAD_PREFIX;
    public static LocaleString PLAYER_HEAD_LORE;

    public static LocaleString GAME_END_TITLE;

    public static LocaleString UNDERCOVER_WIN_MESSAGE;
    public static LocaleString UNDERCOVER_WIN_SUBTITLE;

    public static LocaleString UNDERCOVER_GUESS_WRONG_LOSE_MESSAGE;
    public static LocaleString UNDERCOVER_GUESS_WRONG_LOSE_SUBTITLE;

    public static LocaleString GAME_DRAW_MESSAGE;
    public static LocaleString GAME_DRAW_SUBTITLE;

    public static LocaleString GOOD_WIN_MESSAGE;
    public static LocaleString GOOD_WIN_SUBTITLE;

    public static LocaleString GOOD_VOTE_WRONG_LOSE_MESSAGE;
    public static LocaleString GOOD_VOTE_WRONG_LOSE_SUBTITLE;

    public static void init() {
        defaultLanguage = zh_cn.class;
        File folder = new File(WhoIsTheSpy.PLUGIN_FOLDER + "Locale/");
        zh_cn.writeTo(new File(folder, "zh_cn.yml"));
    }

    public static void loadLocale(File file) {
        loadLocale(WhoIsTheSpyLocale.class, file);
    }
}
