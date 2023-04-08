package romanow.abc.desktop;

import romanow.abc.core.I_EmptyEvent;

public class GUITimer {
    private Thread thread=null;
    public synchronized void start(final int delay, final I_EmptyEvent back){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delay*1000);
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            back.onEvent();
                            }
                        });
                    } catch (InterruptedException e) {}
                }
            });
        thread.start();
        }
    public synchronized void cancel(){
        if (thread!=null)
            thread.interrupt();
        }
}
