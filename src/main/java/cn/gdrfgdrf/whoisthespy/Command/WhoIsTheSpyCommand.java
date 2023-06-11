package cn.gdrfgdrf.whoisthespy.Command;

import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class WhoIsTheSpyCommand implements TabExecutor {
    public static final String PERMISSION_ADMINISTRATOR_PREFIX = "whoisthespy.admin.";
    public static final String PERMISSION_USER_PREFIX = "whoisthespy.user.";

    public static final List<SubCommand> commands = new LinkedList<>();

    public WhoIsTheSpyCommand(WhoIsTheSpy whoIsTheSpy) {
        Objects.requireNonNull(whoIsTheSpy.getPlugin().getCommand("whoisthespy")).setExecutor(this);

        commands.add(new WhoIsTheSpyHelpCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpyJoinGameCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpyLeaveGameCommand(whoIsTheSpy));

        commands.add(new WhoIsTheSpyAdminHelpCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpyCreateGameCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpyDeleteGameCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpySaveGameCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpyListGameCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpySetGameNameCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpySetGameMinPlayerCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpySetGameMaxPlayerCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpySetGameCountdownCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpySetGameDurationCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpySetGameSelectBeQuestionedDurationCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpySetGameAnswerDurationCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpySetGameVoteDurationCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpySwitchGameStatusCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpyForceStopGameCommand(whoIsTheSpy));
        commands.add(new WhoIsTheSpyReloadCommand(whoIsTheSpy));

        Permission administrator = new Permission(PERMISSION_ADMINISTRATOR_PREFIX + "*", "Provides access to all WhoIsTheSpy commands", PermissionDefault.OP);
        Permission user = new Permission(PERMISSION_USER_PREFIX + "*", "Provides access to basic WhoIsTheSpy commands", PermissionDefault.TRUE);

        for (SubCommand subCommand : commands) {
            if (subCommand.getPermission().startsWith(WhoIsTheSpyCommand.PERMISSION_ADMINISTRATOR_PREFIX)) {
                administrator.getChildren().put(subCommand.permission, true);
            } else {
                user.getChildren().put(subCommand.permission, true);
            }
        }

        Bukkit.getPluginManager().addPermission(administrator);
        Bukkit.getPluginManager().addPermission(user);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!label.equalsIgnoreCase("whoisthespy") && !label.equalsIgnoreCase("who")) {
            return true;
        }

        if (args.length == 0) {
            for (SubCommand subCommand : commands) {
                if (subCommand.getName().equalsIgnoreCase("help")) {
                    execute(sender, args, subCommand);
                }
            }

            return true;
        }

        boolean showHelp = true;
        for (SubCommand subCommand : commands) {
            if (subCommand.getName().equalsIgnoreCase(args[0])) {
                showHelp = false;

                execute(sender, args, subCommand);
            }
        }

        if (showHelp) {
            WhoIsTheSpyLocale.ERROR_COMMAND_NOT_FOUND.message(WhoIsTheSpyLocale.PREFIX, sender);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> result = new LinkedList<>();

        if (args.length == 1) {
            for (SubCommand subCommand : commands) {
                if (sender.hasPermission(subCommand.getPermission())) {
                    result.add(subCommand.name);
                }
            }
        } else {
            if (args.length > 1) {
                SubCommand subCommand = null;

                for (SubCommand cmd : commands) {
                    if (sender.hasPermission(cmd.getPermission())) {
                        if (cmd.getName().equalsIgnoreCase(args[0])) {
                            subCommand = cmd;
                        }
                    }
                }

                if (subCommand != null) {
                    return subCommand.onTabComplete(sender, args);
                }
            }
        }

        return result;
    }

    private void execute(CommandSender sender, String[] args, SubCommand subCommand) {
        if (sender.hasPermission(subCommand.getPermission())) {
            if (!subCommand.isOnlyPlayers() || sender instanceof Player) {
                if (args.length == 0) {
                    subCommand.onCommand(sender, args);
                } else {
                    if (args.length >= subCommand.getMinArgs() && args.length <= subCommand.maxArgs) {
                        subCommand.onCommand(sender, args);
                    } else {
                        WhoIsTheSpyLocale.ERROR_SYNTAX.message(WhoIsTheSpyLocale.PREFIX, sender, "%SYNTAX%", subCommand.getSyntax());
                    }
                }
            } else {
                WhoIsTheSpyLocale.ERROR_ONLY_PLAYERS.message(WhoIsTheSpyLocale.PREFIX, sender);
            }
        } else {
            WhoIsTheSpyLocale.ERROR_NO_PERMISSIONS.message(WhoIsTheSpyLocale.PREFIX, sender);
        }
    }


}
