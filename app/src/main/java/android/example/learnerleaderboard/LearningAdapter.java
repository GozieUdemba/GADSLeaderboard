package android.example.learnerleaderboard;

import android.content.Context;
import android.example.learnerleaderboard.databinding.ItemLearningBinding;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class LearningAdapter extends RecyclerView.Adapter<LearningAdapter.ViewHolder> {

    private final OnItemClickListener listener;
    private Context mCtx;
    private List<LearningModel> leaders_list;

    public LearningAdapter(Context mCtx, List<LearningModel> leaders_list, OnItemClickListener listener) {
        this.listener = listener;
        this.mCtx = mCtx;
        this.leaders_list = leaders_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        ItemLearningBinding binding = ItemLearningBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LearningModel learningLeaders = leaders_list.get(position);
        holder.bind(learningLeaders);
    }

    @Override
    public int getItemCount() {
        return leaders_list.size();
    }

    public interface OnItemClickListener {
        void onClick(LearningModel leader);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLearningBinding binding;

        public ViewHolder(ItemLearningBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(leaders_list.get(ViewHolder.this.getLayoutPosition()));
                }
            });

        }

        public void bind(LearningModel list) {
            binding.mtvName.setText(list.getName());
            binding.mtvScoreCountry.setText(list.getHours() + " learning hours, " + list.getCountry());
            Picasso.get()
                    .load(Uri.parse(list.getBadgeUrl()))
                    .placeholder(R.drawable.ic_loader)
                    .into(binding.imgViewBadge);

            binding.executePendingBindings();
        }
    }

}
