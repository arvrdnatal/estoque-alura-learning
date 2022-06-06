package br.com.alura.estoque.ui.dialog;

import android.content.Context;

public class SaveProductDialog extends FormProductDialog {

    private static final String SAVE_PRODUCT_DIALOG_TITLE = "Salvando produto";
    private static final String YES_BUTTON_TITTLE = "Salvar";

    public SaveProductDialog(Context context, ConfirmListener listener) {
        super(context, SAVE_PRODUCT_DIALOG_TITLE, YES_BUTTON_TITTLE, listener);
    }

}
