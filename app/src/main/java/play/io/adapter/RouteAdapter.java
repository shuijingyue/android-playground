package play.io.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import play.io.R;
import play.io.repository.entities.Route;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> implements View.OnClickListener {

    private final List<Route> mRoutes = new ArrayList<>();
    private LayoutInflater mInflater;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (mInflater == null) {
            mInflater = LayoutInflater.from(context);
        }
        View view = mInflater.inflate(R.layout.item_route, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setTag(R.id.TAG_KEY_POS, position);
        holder.onBind(mRoutes.get(position).clazz.getSimpleName());
    }

    public void setRoutes(List<Route> routes) {
        mRoutes.addAll(routes);
    }

    @Override
    public int getItemCount() {
        return mRoutes.size();
    }

    @Override
    public void onClick(View v) {
        Context context = v.getContext();
        int pos = (int) v.getTag(R.id.TAG_KEY_POS);
        Route route = mRoutes.get(pos);
        Intent intent = new Intent(context, route.clazz);
        if (route.extra != null) {
            intent.putExtras(route.extra);
        }
        context.startActivity(intent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }

        public void onBind(String text) {
            textView.setText(text);
        }
    }
}
