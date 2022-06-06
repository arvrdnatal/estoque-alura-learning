package br.com.alura.estoque.ui.dialog;

import android.content.Context;

import br.com.alura.estoque.model.Product;

public class EditProductDialog extends FormProductDialog {

    private static final String EDIT_PRODUCT_DIALOG_TITLE = "Editando produto";
    private static final String YES_BUTTON_TITTLE = "Editar";

    public EditProductDialog(Context context, Product product, ConfirmListener listener) {
        super(context, EDIT_PRODUCT_DIALOG_TITLE, YES_BUTTON_TITTLE, listener, product);
    }
}
