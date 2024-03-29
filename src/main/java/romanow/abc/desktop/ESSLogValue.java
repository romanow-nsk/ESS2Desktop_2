package romanow.abc.desktop;

import lombok.Getter;
import lombok.Setter;

public class ESSLogValue {
    public final String name;
    public final String value;
    public final String time;
    @Getter @Setter private int idx=0;
    public ESSLogValue(String name, String value, String time) {
        this.name = name;
        this.value = value;
        this.time = time;
        }
}
