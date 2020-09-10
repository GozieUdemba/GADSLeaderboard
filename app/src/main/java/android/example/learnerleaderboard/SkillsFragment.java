package android.example.learnerleaderboard;

import android.example.learnerleaderboard.databinding.FragmentLearningBinding;
import android.example.learnerleaderboard.databinding.FragmentSkillsBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkillsFragment extends Fragment {
    private FragmentSkillsBinding binding;
    RetrofitApiCalls service;
    SkillsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_skills, container, false);
        service = RetrofitClient.getRetrofitInstance().create(RetrofitApiCalls.class);
        return binding.getRoot();    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeAdapter();

    }

    public void initializeAdapter() {
        Call<List<SkillsModel>> call = service.getSkillLeaders();
        call.enqueue(new Callback<List<SkillsModel>>() {
            @Override
            public void onResponse(Call<List<SkillsModel>> call, Response<List<SkillsModel>> response) {
                if (response.isSuccessful()) {
                    List<SkillsModel> res = response.body();

                    binding.pb.setVisibility(View.GONE);
                    binding.recyclerview.setVisibility(View.VISIBLE);

                    adapter = new SkillsAdapter(getActivity(), res, leader -> {
                        //Do Nothing
                    });
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    binding.recyclerview.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<SkillsModel>> call, Throwable t) {
                binding.pb.setVisibility(View.GONE);
                binding.tv.setVisibility(View.VISIBLE);

            }
        });
    }
}
