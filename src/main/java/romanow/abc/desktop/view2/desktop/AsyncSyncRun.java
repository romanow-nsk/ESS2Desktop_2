package romanow.abc.desktop.view2.desktop;

//------------------ Синхронно-асинхронное выполнение
public abstract class AsyncSyncRun<T> {
    public AsyncSyncRun(){
        this(true);
        }
    public AsyncSyncRun(boolean mode){
        if (!mode){
            try{
                T res = runCode();
                onSuccess(res);
                } catch (Exception ee){
                    onExeption(ee);
                    }
            }
        else{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final T res = runCode();
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                onSuccess(res);
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
    abstract public T runCode() throws Exception;
    abstract public void onExeption(Exception ee);
    abstract public void onSuccess(T result);
}
