package com.tongilsoft.tscore.gui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.tongilsoft.tscore.interfaces.dialogs.ServerNotificationActionCallbacks;
import com.tongilsoft.tscore.utilidades.TSLogging;

public class NotificationDialog extends DialogFragment {

    public static final int
            ACTION_NOTIFICATION_SHOW = 1,
            ACTION_NOTIFICATION_HIDE = 2;

    public static final String
            TAG     =   "NotificationDialog",
            FIRMA   =   "DLG_NOTIFICATION";

    private String
            DLG_TITLE="Mensaje de error indefinido.",
            DLG_MESSAGE="Mensaje no definido.",
            DLG_NEGATIVE_BUTTON_CAPTION = "Negative",
            DLG_POSITIVE_BUTTON_CAPTION = "Positive";

    public static final int
            OPTION_DLG_TYPE_RETRY_CANCEL = 0,
            OPTION_DLG_TYPE_ACCEPT = 1,
            OPTION_DLG_TYPE_CLOSE = 3,
            OPTION_DLG_TYPE_NO_YES = 4;


    private int dlg_type = 0;
    public TSLogging log;
    public ServerNotificationActionCallbacks serverNotificationActionCallbacks;

    public NotificationDialog(){
        log=new TSLogging(true,true,true);
    }

    public void setTitle(String title){
        DLG_TITLE = title;
    }

    public void setMessage(String message){
        DLG_MESSAGE = message;
    }

    public void setNegativeButtonCaption(String caption){
        DLG_NEGATIVE_BUTTON_CAPTION = caption;
    }

    public void setPositiveButtonCaption(String caption){
        DLG_POSITIVE_BUTTON_CAPTION = caption;
    }

    public void setDialogType(int dialogType){
        dlg_type=dialogType;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        log.d(TAG,"onAttach()");
        try {
            serverNotificationActionCallbacks = (ServerNotificationActionCallbacks) getActivity();
        } catch (ClassCastException cce) {
            String msg = "La interfaz ServerNotificationActionCallbacks no está implementada.";
            log.e(TAG,"onAttach() -> ClassCastException",cce);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        log.d(TAG,"onCreateDialog()");

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle(DLG_TITLE).setMessage(DLG_MESSAGE);
        switch (dlg_type){
            case OPTION_DLG_TYPE_ACCEPT:
                DLG_POSITIVE_BUTTON_CAPTION = "Aceptar";
                builder.setPositiveButton(DLG_POSITIVE_BUTTON_CAPTION, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        serverNotificationActionCallbacks.onPositiveButtonPressed(NotificationDialog.this);
                    }
                });
                break;

            case OPTION_DLG_TYPE_CLOSE:
                DLG_POSITIVE_BUTTON_CAPTION = "Cerrar";
                builder.setPositiveButton(DLG_POSITIVE_BUTTON_CAPTION, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        serverNotificationActionCallbacks.onPositiveButtonPressed(NotificationDialog.this);
                    }
                });
                break;
            case OPTION_DLG_TYPE_RETRY_CANCEL:
                DLG_NEGATIVE_BUTTON_CAPTION = "Cancelar";
                DLG_POSITIVE_BUTTON_CAPTION = "Reintentar";
                builder.setNegativeButton(DLG_NEGATIVE_BUTTON_CAPTION, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        serverNotificationActionCallbacks.onNegativeButtonPressed(NotificationDialog.this);
                    }
                }).setPositiveButton(DLG_POSITIVE_BUTTON_CAPTION,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        serverNotificationActionCallbacks.onPositiveButtonPressed(NotificationDialog.this);
                    }
                });
                break;

            case OPTION_DLG_TYPE_NO_YES:
                DLG_NEGATIVE_BUTTON_CAPTION = "NO";
                DLG_POSITIVE_BUTTON_CAPTION = "SI";
                builder.setNegativeButton(DLG_NEGATIVE_BUTTON_CAPTION, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        serverNotificationActionCallbacks.onNegativeButtonPressed(NotificationDialog.this);
                    }
                }).setPositiveButton(DLG_POSITIVE_BUTTON_CAPTION,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        serverNotificationActionCallbacks.onPositiveButtonPressed(NotificationDialog.this);
                    }
                });
                break;
        }
        Dialog d=builder.create();
        d.setCanceledOnTouchOutside(false);
        return d;
    }
}
