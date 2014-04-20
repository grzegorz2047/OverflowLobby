package de.batschkoto.overflowlobby;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.config.ServerInfo;

@Getter
@RequiredArgsConstructor
public class LobbyServer {

    private final String serverName;
    private final int slots;

    public ServerInfo getServerInfo() {
        return OverflowLobby.getInstance().getProxy().getServerInfo( serverName );
    }

}
