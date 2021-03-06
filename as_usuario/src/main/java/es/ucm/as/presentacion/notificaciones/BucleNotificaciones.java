package es.ucm.as.presentacion.notificaciones;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;
import java.util.Date;

import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;
import es.ucm.as.presentacion.vista.Contexto;

/**
 */
public class BucleNotificaciones extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Contexto.getInstancia().setContext(context);

        Controlador.getInstancia().ejecutaComando(ListaComandos.ACTUALIZAR_NOTIFICACIONES, null);
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AutoArranque.class);

        long time = new Date().getTime();
        String tmpStr = String.valueOf(time);
        String last4Str = tmpStr.substring(tmpStr.length() - 5);
        int pendingId = Integer.valueOf(last4Str);
        PendingIntent pi = PendingIntent.getBroadcast(context, pendingId, i, PendingIntent.FLAG_ONE_SHOT);

        Calendar horaNotificacionCal = Calendar.getInstance();
        horaNotificacionCal.set(Calendar.HOUR_OF_DAY, 1);
        horaNotificacionCal.set(Calendar.MINUTE, 11);
        horaNotificacionCal.set(Calendar.SECOND, 00);
        horaNotificacionCal.add(Calendar.DAY_OF_MONTH, 1); //Esto hace que se haga la dia siguiente
        long horaNotificacion = horaNotificacionCal.getTimeInMillis();
        setAlarm(am, horaNotificacion, pi);
    }


    private void setAlarm(AlarmManager am,long ms, PendingIntent pendingIntent){

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            am.set(AlarmManager.RTC, ms, pendingIntent);
        } else {
            setAlarmFromKitkat(am, ms, pendingIntent);
        }
    }

    @TargetApi(19)
    private void setAlarmFromKitkat(AlarmManager am, long ms, PendingIntent pi){
        am.setExact(AlarmManager.RTC, ms, pi);
    }

}
