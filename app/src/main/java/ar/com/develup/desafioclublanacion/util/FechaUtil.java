package ar.com.develup.desafioclublanacion.util;

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
}
