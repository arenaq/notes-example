package org.kuska.bscapp.feature.home;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.kuska.bscapp.R;
import org.kuska.bscapp.models.Note;

import java.util.List;

/**
 * @author Petr Kuška (kuska.petr@gmail.com)
 * @since 2018/06/23
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.NoteViewHolder> {
    private List<Note> notes;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public HomeAdapter(Context context, List<Note> notes) {
        this.notes = notes;
        this.context = context;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        final Note note = notes.get(position);
        holder.txtTitle.setText(note.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(note);
                }
            }
        });
        // TODO check if text is not empty and show alert dialog if necessary
        if (note.getTitle() != null && !note.getTitle().isEmpty()) {
            holder.txtIcon.setText(String.valueOf(note.getTitle().charAt(0)));
        }

        // TODO move to custom view with color as parameter or setter
        ShapeDrawable oval = new ShapeDrawable(new OvalShape());
//        oval.getPaint().setColor(getMatColor("500"));
        oval.getPaint().setColor(getMatColor(position));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.txtIcon.setBackground(oval);
        } else {
            holder.txtIcon.setBackgroundDrawable(oval);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtIcon;

        public NoteViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtIcon = (TextView) itemView.findViewById(R.id.txtIcon);
        }
    }

    private int getMatColor(String typeColor) {
        int returnColor = Color.BLACK;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + typeColor, "array", context.getApplicationContext().getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            int index = (int) (Math.random() * colors.length());
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }

    private int getMatColor(int index) {
        int returnColor = Color.BLACK;
        int arrayId = context.getResources().getIdentifier("mdcolor_" + "400", "array", context.getApplicationContext().getPackageName());

        if (arrayId != 0) {
            TypedArray colors = context.getResources().obtainTypedArray(arrayId);
            index = index % colors.length();
            returnColor = colors.getColor(index, Color.BLACK);
            colors.recycle();
        }
        return returnColor;
    }

}
