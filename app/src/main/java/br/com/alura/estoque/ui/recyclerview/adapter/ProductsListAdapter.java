package br.com.alura.estoque.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.alura.estoque.R;
import br.com.alura.estoque.model.Product;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ViewHolder> {

    private final Context context;
    private final OnItemClickListener onItemClickListener;
    private OnItemClickRemoveContextMenuListener onItemClickRemoveContextMenuListener = (posicao, produtoRemovido) -> {
    };
    private final List<Product> products = new ArrayList<>();

    public ProductsListAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemClickRemoveContextMenuListener(OnItemClickRemoveContextMenuListener onItemClickRemoveContextMenuListener) {
        this.onItemClickRemoveContextMenuListener = onItemClickRemoveContextMenuListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.link(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void update(List<Product> products) {
        notifyItemRangeRemoved(0, this.products.size());
        this.products.clear();
        this.products.addAll(products);
        notifyItemRangeInserted(0, this.products.size());
    }

    public void add(Product... products) {
        int size = this.products.size();
        Collections.addAll(this.products, products);
        int newSize = this.products.size();
        notifyItemRangeInserted(size, newSize);
    }

    public void edit(int position, Product product) {
        products.set(position, product);
        notifyItemChanged(position);
    }

    public void remove(int posicao) {
        products.remove(posicao);
        notifyItemRemoved(posicao);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView idField;
        private final TextView nameField;
        private final TextView priceField;
        private final TextView amountField;
        private Product product;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            idField = itemView.findViewById(R.id.id_product_item);
            nameField = itemView.findViewById(R.id.name_product_item);
            priceField = itemView.findViewById(R.id.price_product_item);
            amountField = itemView.findViewById(R.id.amount_product_item);
            configItem(itemView);
            configMenu(itemView);
        }

        private void configMenu(@NonNull View itemView) {
            itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
                new MenuInflater(context).inflate(R.menu.lista_produtos_menu, menu);
                menu.findItem(R.id.menu_remove_products_list).setOnMenuItemClickListener(item -> {
                    onItemClickRemoveContextMenuListener.onItemClick(getAdapterPosition(), product);
                    return true;
                });
            });
        }

        private void configItem(@NonNull View itemView) {
            itemView.setOnClickListener(v -> onItemClickListener
                    .onItemClick(getAdapterPosition(), product));
        }

        void link(Product product) {
            this.product = product;
            idField.setText(String.valueOf(product.getId()));
            nameField.setText(product.getNome());
            priceField.setText(formatCurrency(product.getPreco()));
            amountField.setText(String.valueOf(product.getQuantidade()));
        }

        private String formatCurrency(BigDecimal valor) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            return formatter.format((valor != null) ? valor : 0.0);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int posicao, Product product);
    }

    public interface OnItemClickRemoveContextMenuListener {
        void onItemClick(int posicao, Product product);
    }

}
