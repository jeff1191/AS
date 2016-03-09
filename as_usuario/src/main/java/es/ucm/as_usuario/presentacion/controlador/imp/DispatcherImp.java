package es.ucm.as_usuario.presentacion.controlador.imp;

import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.ucm.as_usuario.negocio.suceso.Reto;
import es.ucm.as_usuario.negocio.suceso.TransferReto;
import es.ucm.as_usuario.negocio.suceso.TransferTarea;
import es.ucm.as_usuario.presentacion.Ayuda;
import es.ucm.as_usuario.presentacion.Contexto;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.presentacion.VerEventos;
import es.ucm.as_usuario.presentacion.VerInforme;
import es.ucm.as_usuario.presentacion.VerReto;
import es.ucm.as_usuario.presentacion.controlador.Dispatcher;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class DispatcherImp extends Dispatcher{
    @Override
    public void dispatch(String accion, Object datos) {

        switch(accion){
            case ListaComandos.VER_EVENTOS:
                Intent intent = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerEventos.class);
                List<Evento> eventosModelo= (List<Evento>) datos;
                Log.d("Info", "LLEGA: " + eventosModelo.get(0).getTextoPregunta());
                ArrayList<String> listaActividad= new ArrayList<String>();

                for(int i=0; i < eventosModelo.size(); i++){
                    String addEvento= eventosModelo.get(i).getTextoPregunta() + " a las " + eventosModelo.get(i).getFechaIni();
                    listaActividad.add(addEvento);
                }
                intent.putStringArrayListExtra("listaEventos", listaActividad);
                Contexto.getInstancia().getContext().startActivity(intent);
                break;

            case ListaComandos.AYUDA:
                Intent intentAyuda = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), Ayuda.class);
                intentAyuda.putExtra("pantalla", (String)datos);
                Contexto.getInstancia().getContext().startActivity(intentAyuda);
                break;

            case ListaComandos.VER_RETO:
                Intent intentR = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerReto.class);
                TransferReto r = (TransferReto)datos;
                if (r != null) {
                    intentR.putExtra("textReto", r.getNombre());
                    intentR.putExtra("contadorReto", r.getContador());
                    intentR.putExtra("superadoReto", r.getSuperado());
                }

                Contexto.getInstancia().getContext().startActivity(intentR);
                break;

            case ListaComandos.RESPONDER_RETO:
                break;

            case ListaComandos.VER_INFORME:

                Intent intentTareas = new Intent(Contexto.getInstancia().getContext().getApplicationContext(), VerInforme.class);
                List<TransferTarea> tareasModelo= (List<TransferTarea>) datos;
                ArrayList<String> listaActividad2 = new ArrayList<String>();

                for(int j=0; j < tareasModelo.size(); j++){
                    String addTarea= tareasModelo.get(j).getTextoPregunta();
                    Log.d("¿mostrando?", "informe");
                    listaActividad2.add(addTarea);
                }
                intentTareas.putStringArrayListExtra("listaTareas", listaActividad2);
                Contexto.getInstancia().getContext().startActivity(intentTareas);
        }
    }
}
