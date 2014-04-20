package de.batschkoto.overflowlobby;

import lombok.Getter;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;

public class OverflowLobby extends Plugin implements Listener {

    @Getter
    private static OverflowLobby instance;

    @Getter
    private Database dbConfig;

    private final HashMap<String, LobbyServer> lobbies = new HashMap<>();
    private LobbyServer defaultLobby;
    private String kickMessage;

    @Override
    public void onEnable() {
        instance = this;

        try {
            dbConfig = new Database( this );
            dbConfig.init();
        } catch ( InvalidConfigurationException ex ) {
            ex.printStackTrace();
        }

        reloadConfig();

        getProxy().getPluginManager().registerCommand( this, new Commandoverflowlobby() );
        getProxy().getPluginManager().registerListener( this, this );
    }

    public void reloadConfig() {
        lobbies.clear();

        kickMessage = ChatColor.translateAlternateColorCodes( '&', dbConfig.getKickMessage() );

        for ( String server : dbConfig.getLobbies().keySet() ) {
            if ( server.equalsIgnoreCase( dbConfig.getDefaultLobby() ) ) {
                defaultLobby = new LobbyServer( server, dbConfig.getLobbies().get( server ) );
            } else {
                lobbies.put( server.toLowerCase(), new LobbyServer( server, dbConfig.getLobbies().get( server ) ) );
            }
        }
    }

    @EventHandler
    public void onLogin( LoginEvent event ) {
        if ( defaultLobby.getServerInfo().getPlayers().size() < defaultLobby.getSlots() ) {
            return;
        }

        for ( LobbyServer lobbyServer : lobbies.values() ) {
            if ( lobbyServer.getServerInfo().getPlayers().size() < lobbyServer.getSlots() ) {
                return;
            }
        }

        event.setCancelled( true );
        event.setCancelReason( kickMessage );
    }

    @EventHandler
    public void onServerConnect( ServerConnectEvent event ) {
        if ( !event.getTarget().getName().equalsIgnoreCase( defaultLobby.getServerName() ) ) {
            return;
        }

        if ( event.getTarget().getPlayers().size() < defaultLobby.getSlots() ) {
            return;
        }

        for ( LobbyServer lobbyServer : lobbies.values() ) {
            if ( lobbyServer.getServerInfo().getPlayers().size() < lobbyServer.getSlots() ) {
                event.setTarget( lobbyServer.getServerInfo() );
                return;
            }
        }

        event.getPlayer().disconnect( kickMessage );
    }
}
