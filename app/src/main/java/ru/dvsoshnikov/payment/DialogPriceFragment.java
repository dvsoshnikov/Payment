package ru.dvsoshnikov.payment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DialogPriceFragment extends DialogFragment {
    Product product;
    public DialogPriceFragment (Product product) {
        this.product = product;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Потверждение суммы покупки");  // заголовок
        builder.setMessage("Сумма покупки: " + product.getCoast()); // сообщение
        builder.setPositiveButton("Да", (dialog, id) -> (
                (MainActivity) requireActivity()).startTokenize(product));
        builder.setNegativeButton("Нет", (dialog, id) -> dialog.cancel());
        builder.setCancelable(true);
        return builder.create();
    }
}
