package club.neru.player.playerdata.objects.persistent;

import club.neru.utils.serialization.SerializableInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SettingsData implements SerializableInterface {
    private String themeColor = "&d";
}
