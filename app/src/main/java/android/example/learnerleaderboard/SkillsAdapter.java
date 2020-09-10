package android.example.learnerleaderboard;

import android.content.Context;
import android.example.learnerleaderboard.databinding.ItemSkillsBinding;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ViewHolder> {

    private final OnItemClickListener listener;
    private Context mCtx;
    private List<SkillsModel> skills_list;

    public SkillsAdapter(Context mCtx, List<SkillsModel> skills_list, OnItemClickListener listener) {
        this.listener = listener;
        this.mCtx = mCtx;
        this.skills_list = skills_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        ItemSkillsBinding binding = ItemSkillsBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SkillsModel skillLeaders = skills_list.get(position);
        holder.bind(skillLeaders);
    }

    @Override
    public int getItemCount() {
        return skills_list.size();
    }

    public interface OnItemClickListener {
        void onClick(SkillsModel leader);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemSkillsBinding binding;

        public ViewHolder(ItemSkillsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(skills_list.get(ViewHolder.this.getLayoutPosition()));
                }
            });
        }

        public void bind(SkillsModel list) {
            binding.mtvName.setText(list.getName());
            binding.mtvScoreCountry.setText(list.getScore() + " skill IQ Score. " + list.getCountry());
            Picasso.get()
                    .load(Uri.parse(list.getBadgeUrl()))
                    .placeholder(R.drawable.ic_loader)
                    .into(binding.imgViewBadge);

            binding.executePendingBindings();
        }
    }
}
