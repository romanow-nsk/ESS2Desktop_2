package romanow.abc.desktop.wizard;

import romanow.abc.core.entity.metadata.Meta2Entity;

public class WizardMeta2GUI3StateBox extends WizardMeta2GUI2StateBox{
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        FailMode().setVisible(true);
        }
}
