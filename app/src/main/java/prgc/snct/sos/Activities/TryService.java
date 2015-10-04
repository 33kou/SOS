package prgc.snct.sos.Activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import prgc.snct.sos.R;

// ______________________________________________________________________________
// �T�[�r�X�{��
public class TryService extends Service {

    private final static String TAG = "TryService#";
    int oldscount=0;
    int flag=0;
    Context con =this;
    int scount;
    // Toast������\�����ꂽ�������邽�߂̃J�E���g
    private int mCount = 0;

    // Toast��\�������邽�߂Ɏg���n���h��
    private Handler mHandler = new Handler();

    // �X���b�h���~���邽�߂ɕK�v
    private boolean mThreadActive = true;

    // �X���b�h����
    protected Runnable mTask = new Runnable() {

        @Override
        public void run() {

            // �A�N�e�B�u�ȊԂ�������������
            while (mThreadActive) {

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    // TODO �����������ꂽ catch �u���b�N
                    e.printStackTrace();
                }

                // �n���h���[���͂��܂Ȃ���Toast�ŃG���[�ł�
                // UI�X���b�h���ŏ��������Ȃ��Ƃ����Ȃ��炵��
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        if(mThreadActive==true) {
                            mCount++;

                            GetSOS d=new GetSOS();

                                scount=d.geter(con);


                            if(scount>oldscount) {
                                showNotification(TryService.this);
                            }
                            flag=1;

                            //if(oldscount<scount)
                                oldscount=scount;


                        }
                    }
                });
            }


            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    showText("Thread end");
                }
            });
        }
    };
    private Thread mThread;

    // ______________________________________________________________________________
    /**
     * �e�L�X�g��\������
     * @param text �\���������e�L�X�g
     */
    private void showText(Context ctx, final String text) {
        Toast.makeText(this, TAG + text, Toast.LENGTH_SHORT).show();
    }

    // ______________________________________________________________________________
    /**
     * �e�L�X�g��\������
     * @param text �e�L�X�g
     */
    private void showText(final String text) {
        showText(this, text);
    }


    // ______________________________________________________________________________
    @Override   // onBind:�T�[�r�X���o�C���h���ꂽ�Ƃ��ɌĂяo�����
    public IBinder onBind(Intent intent) {
        this.showText("Service was bound.");
        return null;
    }

    // ______________________________________________________________________________
    @Override   // onStartCommand:
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        this.showText("onStartCommand");
        this.mThread = new Thread(null, mTask, "NortifyingService");
        this.mThread.start();

        // �ʒm�o�[��\������
        //showNotification(this);

        // �߂�l�ŃT�[�r�X�������I�����ꂽ�Ƃ��̋������ς��
        // START_NOT_STICKY,START_REDELIVER_INTENT,START_STICKY_COMPATIBILITY
        return START_STICKY;
    }

    // ______________________________________________________________________________
    @Override   // onCreate:�T�[�r�X���쐬���ꂽ�Ƃ��ɌĂт����(�ŏ���1�񂾂�)
    public void onCreate() {
        this.showText("Service has been begun.");
        super.onCreate();
    }


    // ______________________________________________________________________________
    @Override   // onDestroy:
    public void onDestroy() {
        this.showText("Service has been ended.");

        // �X���b�h��~
        this.mThread.interrupt();
        this.mThreadActive = false;

        this.stopNotification(this);
        super.onDestroy();
    }

    // ______________________________________________________________________________
    // �ʒm�o�[������
    public static void stopNotification(final Context ctx) {
        NotificationManager mgr = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        mgr.cancel(R.layout.activity_service);
    }

    // ______________________________________________________________________________
    // �ʒm�o�[���o��
    private void showNotification(final Context ctx) {

        NotificationManager mgr = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(ctx, ActivityService.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // �ʒm�o�[�̓��e�����߂�
        Notification n = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("There is a rescue request person..")
                .setWhen(System.currentTimeMillis())    // ����
                .setContentTitle("SOS")
                .setContentText("When a tap is done, the location of the linchpin savior is indicated.")
                .setContentIntent(contentIntent)// �C���e���g
                .build();
        n.defaults |= Notification.DEFAULT_ALL;
        n.flags = Notification.FLAG_NO_CLEAR;

        mgr.notify(R.layout.activity_service, n);

    }

    //This bar, please choose "service end" behind the tap.
}
