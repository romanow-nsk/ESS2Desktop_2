package romanow.abc.desktop.view2;

import lombok.Getter;
import lombok.Setter;
import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.API.RestAPIESS2;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2GUIForm;
import romanow.abc.core.entity.metadata.Meta2GUIView;
import romanow.abc.core.entity.metadata.render.FormContextBase;
import romanow.abc.core.entity.metadata.render.I_ContextBack;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.subject2area.ESS2View;
import romanow.abc.core.entity.subjectarea.AccessManager;
import romanow.abc.desktop.MainBaseFrame;


public class FormContext2 extends FormContextBase {
    @Getter @Setter private MainBaseFrame main=null;
    @Getter @Setter private boolean showInsertButtion=false;
    public FormContext2(I_ContextBack back0, int idx1, int idx2, int idx3) {
        super(back0, idx1, idx2, idx3);
        }
    public FormContext2(I_ContextBack back0) {
        super(back0);
        }
    }
