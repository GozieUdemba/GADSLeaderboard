package android.example.learnerleaderboard;

import android.example.learnerleaderboard.databinding.FragmentLearningBinding;
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

public class LearningFragment extends Fragment {

    private FragmentLearningBinding binding;
    RetrofitApiCalls service;
    LearningAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_learning, container, false);
       service = RetrofitClient.getRetrofitInstance().create(RetrofitApiCalls.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeAdapter();
    }

    public void initializeAdapter() {
        Call<List<LearningModel>> call = service.getLearningLeaders();
        call.enqueue(new Callback<List<LearningModel>>() {
            @Override
            public void onResponse(Call<List<LearningModel>> call, Response<List<LearningModel>> response) {
                if (response.isSuccessful()) {
                    List<LearningModel> res = response.body();

                    binding.pb.setVisibility(View.GONE);
                    binding.recyclerview.setVisibility(View.VISIBLE);

                    adapter = new LearningAdapter(getActivity(), res, leader -> {
                        //Do Nothing
                    });
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    binding.recyclerview.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<LearningModel>> call, Throwable t) {
                binding.pb.setVisibility(View.GONE);
                binding.tv.setVisibility(View.VISIBLE);

            }
        });
    }


}
