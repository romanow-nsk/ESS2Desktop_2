package romanow.abc.desktop.wizard;

public interface I_WizardEntitySelector {
    public void onEdit(int type, int idx);
    public void onRemove(int type, int idx);
    public void onAdd(int type);
    public void onSelect(int type, int idx);
}
