package ar.com.develup.desafioclublanacion.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FechaUtil {

    public static boolean pasoAyerOAntes(Date fecha) {


        return (fecha.before(FechaUtil.ayer()) ) ;

    }

    public static Date ayer() {

        Calendar ayer = new GregorianCalendar();
        ayer.setLenient(false);

        ayer.setTime(new Date());
        ayer.add(Calendar.DAY_OF_YEAR, -1);

        setearUltimominutoDelDia(ayer);

        return ayer.getTime();
    }

    public static void setearUltimominutoDelDia(Calendar calendar) {

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

    }

    public static boolean horarioActualAceptaNotificaciones(Context context) {

        Calendar calendarActual = Calendar.getInstance();
        Calendar desde = Preferencias.getNotificacionesDesdeHora(context);
        Calendar hasta = Preferencias.getNotificacionesHastaHora(context);

        int horaActual = calendarActual.get(Calendar.HOUR_OF_DAY);
        int minutoActual = calendarActual.get(Calendar.MINUTE);

        int horaDesde = desde.get(Calendar.HOUR_OF_DAY);
        int minutoDesde = desde.get(Calendar.MINUTE);
        int horaHasta = hasta.get(Calendar.HOUR_OF_DAY);
        int minutoHasta = hasta.get(Calendar.MINUTE);

        boolean acepta = (horaActual > horaDesde && horaActual < horaHasta)
                         || (horaActual == horaDesde && minutoActual >= minutoDesde)
                         || (horaActual == horaHasta && minutoActual <= minutoHasta);

        return acepta;
    }

    public static String formatear(Date fecha) {

        String patron = "dd/MM/yyyy";
        return new SimpleDateFormat(patron).format(fecha);
    }
}
