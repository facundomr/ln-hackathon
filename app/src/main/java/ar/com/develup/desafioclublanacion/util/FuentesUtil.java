package ar.com.develup.desafioclublanacion.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class FuentesUtil {

    public static void aplicarFuente (Fuente fuente, TextView textView, Context context) {

        if (textView != null) {

            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fuente.ruta);
            textView.setTypeface(typeface);
        }
    }

    public enum Fuente {

        HELVETICA_LIGHT( "HelveticaNeueLTPro-Lt.ttf"),
        HELVETICA_LIGHT_ITALIC( "HelveticaNeueLTPro-LtCnO.ttf"),
        HELVETICA_BOLD( "HelveticaNeueLTPro-BdCn.ttf"),
        HELVETICA_MEDIUM( "HelveticaNeueLTPro-MdCn.ttf");

        private String ruta;

        Fuente (String ruta) {
            this.ruta = ruta;
        }
    }
}
