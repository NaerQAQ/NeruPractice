package club.neru.player.playerdata.objects.nonpersistent;

import club.neru.player.playerdata.enums.PlayerState;
import club.neru.player.playerdata.objects.persistent.SettingsData;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PlayerData {
    private PlayerState playerState = PlayerState.IN_LOBBY;

    private SettingsData settingsData;
}
