package es.ucm.as_usuario.presentacion.notificaciones;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class ServicioNotificaciones extends Service {

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        Log.d(this.getClass().getSimpleName(),"onCreate()");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Tendriamos que pensar en tener to*do en una cola que la tendria que haber cogido de base de datos - esto es IMPORTANTE
        Log.d("notificaciones","onStartCommand() SERVICIO");

        AlarmManager am =( AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getApplicationContext(), CargarNotificaciones.class);
        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int pendingId = Integer.valueOf(last4Str);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), pendingId, i, PendingIntent.FLAG_ONE_SHOT);

        // Establecer la hora a la que cargara de BBDD las notificaciones
        Integer h = 17;
        Integer m = 36;
        Calendar horaNotificacionCal = Calendar.getInstance();
        horaNotificacionCal.set(Calendar.HOUR_OF_DAY, h);
        horaNotificacionCal.set(Calendar.MINUTE, m);
        horaNotificacionCal.set(Calendar.SECOND, 00);
        long horaNotificacion = horaNotificacionCal.getTimeInMillis();

        Log.e("notificaciones", "CargarNotificaciones a las " + h + ":" + m);

        setAlarm(am, horaNotificacion, pi);

        return START_NOT_STICKY;
    }

    @TargetApi(19)
    private void setAlarmFromKitkat(AlarmManager am, long ms, PendingIntent pi){
        am.setExact(AlarmManager.RTC, ms, pi);
    }

    private void setAlarm(AlarmManager am,long ms, PendingIntent pendingIntent){

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            am.set(AlarmManager.RTC, ms, pendingIntent);
        } else {
            setAlarmFromKitkat(am, ms, pendingIntent);
        }
    }

}
