package br.com.alura.estoque.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;

import androidx.appcompat.app.AlertDialog;

import br.com.alura.estoque.R;
import br.com.alura.estoque.model.Product;

abstract public class FormProductDialog {

    private static final String TITLE_NO_BUTTON = "Cancelar";
    private final Context context;
    private final String title;
    private final String titleYesBtn;
    private final ConfirmListener listener;
    private Product product;

    FormProductDialog(Context context, String title, String titleYesBtn, ConfirmListener listener) {
        this.context = context;
        this.title = title;
        this.titleYesBtn = titleYesBtn;
        this.listener = listener;
    }

    @SuppressWarnings("SameParameterValue")
    FormProductDialog(Context context, String title, String titleYesBtn, ConfirmListener listener, Product product) {
        this(context, title, titleYesBtn, listener);
        this.product = product;
    }

    public void show() {
        View view = LayoutInflater.from(context).inflate(R.layout.form_product, null);
        fillForm(view);
        new AlertDialog.Builder(context).setTitle(title).setView(view)
                .setPositiveButton(titleYesBtn, (dialog, which) -> {
                    EditText nameField = getEditText(view, R.id.name_form_product);
                    EditText priceField = getEditText(view, R.id.price_form_product);
                    EditText amountField = getEditText(view, R.id.amount_form_product);
                    createProduct(nameField, priceField, amountField);
                }).setNegativeButton(TITLE_NO_BUTTON, null).show();
    }

    private void fillForm(View view) {
        if (product != null) {
            TextView idField = view.findViewById(R.id.id_form_product);
            idField.setText(String.valueOf(product.getId()));
            idField.setVisibility(View.VISIBLE);
            EditText nameField = getEditText(view, R.id.name_form_product);
            nameField.setText(product.getNome());
            EditText priceField = getEditText(view, R.id.price_form_product);
            priceField.setText(String.valueOf(product.getPreco()));
            EditText amountField = getEditText(view, R.id.amount_form_product);
            amountField.setText(String.valueOf(product.getQuantidade()));
        }
    }

    private void createProduct(EditText nameField, EditText priceField, EditText amountField) {
        long id = (product != null) ? product.getId() : 0;
        BigDecimal price = triesToConvertPrice(priceField);
        int amount = triesToConvertAmount(amountField);
        Product product = new Product(id, nameField.getText().toString(), price, amount);
        listener.whenConfirmed(product);
    }

    private BigDecimal triesToConvertPrice(EditText priceField) {
        try {
            return new BigDecimal(priceField.getText().toString());
        } catch (NumberFormatException ignored) {
            return BigDecimal.ZERO;
        }
    }

    private int triesToConvertAmount(EditText amountField) {
        try {
            return Integer.parseInt(amountField.getText().toString());
        } catch (NumberFormatException ignored) {
            return 0;
        }
    }

    private EditText getEditText(View view, int idTextInputLayout) {
        TextInputLayout textInputLayout = view.findViewById(idTextInputLayout);
        return textInputLayout.getEditText();
    }

    public interface ConfirmListener {
        void whenConfirmed(Product product);
    }
}
