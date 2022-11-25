package romanow.abc.desktop;

import romanow.abc.core.entity.metadata.StreamRegisterData;
import romanow.abc.core.entity.subjectarea.DataSet;

/**
 * Created by romanow on 09.04.2018.
 */
public interface I_Trend {
    public void toFront();
    public void addTrend(StreamRegisterData hh);
    public void addTrendView(StreamRegisterData hh);
    public void close();
    }
