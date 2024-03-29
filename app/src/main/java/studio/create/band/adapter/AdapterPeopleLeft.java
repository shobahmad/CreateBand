package studio.create.band.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import studio.create.band.R;
import studio.create.band.model.People;
import studio.create.band.util.Tools;

public class AdapterPeopleLeft extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<People> items = new ArrayList<>();
    private Context ctx;

    private OnItemClickListener onItemClickListener;
    private OnMoreButtonClickListener onMoreButtonClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnMoreButtonClickListener(final OnMoreButtonClickListener onMoreButtonClickListener) {
        this.onMoreButtonClickListener = onMoreButtonClickListener;
    }


    public AdapterPeopleLeft(Context context, List<People> items) {
        this.items = items;
        ctx = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView image;
        public TextView name;
        public TextView role;
        public View lyt_parent;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            role = (TextView) v.findViewById(R.id.role);
            lyt_parent = (View) v.findViewById(R.id.lyt_parent);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_people_left, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder view = (ViewHolder) holder;
            final People o = items.get(position);
            view.name.setText(o.name);
            view.role.setText(o.role);
            Tools.displayImageRound(ctx, view.image, o.image);

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener == null) return;
                    onItemClickListener.onItemClick(view, o, position);
                }
            });
        }
    }

    private void onMoreButtonClick(final View view, final People people) {
        PopupMenu popupMenu = new PopupMenu(ctx, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onMoreButtonClickListener.onItemClick(view, people, item);
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_people_more);
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public People getItem(int position) {
        return items.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, People obj, int pos);
    }

    public interface OnMoreButtonClickListener {
        void onItemClick(View view, People obj, MenuItem item);
    }
}