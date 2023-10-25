package romanow.abc.desktop.view2.desktop;

public abstract class AsyncSyncRunV {
    public AsyncSyncRunV(){
        this(true);
        }
    public AsyncSyncRunV(boolean mode){
        if (!mode){
            try {
                runCode();
                onSuccess();
                } catch (Exception ee){
                    onExeption(ee);
                    }
                }
        else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        runCode();
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                onSuccess();
                                }
                            });
                        } catch (final Exception ee){
                            java.awt.EventQueue.invokeLater(new Runnable() {
                                public void run() {
                                    onExeption(ee);
                                }
                            });
                        }
                    }
                }).start();
            }
        }
    abstract public void runCode() throws Exception;
    abstract public void onExeption(Exception e);
    abstract public void onSuccess();
    }
