package com.example.fastlogic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class VentanaPuntaje extends DialogFragment {

    public class FireMissilesDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setView(getActivity().getLayoutInflater().inflate(R.layout.ventanapuntaje,null))
                    .setTitle("Puntaje")
                    .setPositiveButton("Regresar a Menu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            // Create the AlertDialog object and return it
            return builder.create();
        }
    }


}
