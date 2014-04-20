package de.batschkoto.overflowlobby;

import lombok.Getter;
import lombok.Setter;
import net.cubespace.Yamler.Config.Comment;
import net.cubespace.Yamler.Config.Config;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.util.HashMap;

@Getter
@Setter
public class Database extends Config {

    public Database( Plugin plugin ) {
        CONFIG_HEADER = new String[]{ "Configuration for the OverflowLobby-Plugin" };
        CONFIG_FILE = new File( plugin.getDataFolder(), "config.yml" );
    }

    @Comment("All lobbyservers also containing the default lobby server")
    private HashMap<String, Integer> lobbies = new HashMap<String, Integer>() {{
        put( "lobby", 150 );
        put( "lobby2", 150 );
        put( "lobby3", 150 );
    }};

    @Comment("The default lobby server")
    private String defaultLobby = "lobby";

    @Comment("Kick-message if all lobbies are full")
    private String kickMessage = "&cSorry, our servers are currently overloaded.";
}
