package de.batschkoto.overflowlobby;

import net.cubespace.Yamler.Config.InvalidConfigurationException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class Commandoverflowlobby extends Command {

    public Commandoverflowlobby() {
        super( "overflowlobby", "overflowlobby.reload" );
    }

    @Override
    public void execute( CommandSender sender, String[] args ) {
        if ( args.length == 0 ) {
            sender.sendMessage( ChatColor.RED + "Usage: /overflowlobby reload" );
            return;
        }

        if ( args[0].equalsIgnoreCase( "reload" ) ) {
            try {
                OverflowLobby.getInstance().getDbConfig().reload();
            } catch ( InvalidConfigurationException e ) {
                e.printStackTrace();
            }

            OverflowLobby.getInstance().reloadConfig();

            sender.sendMessage( ChatColor.GREEN + "Config reloaded!" );
        } else {
            execute( sender, new String[0] );
        }
    }
}
