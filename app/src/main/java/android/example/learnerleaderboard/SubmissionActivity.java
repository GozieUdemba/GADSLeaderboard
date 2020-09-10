package android.example.learnerleaderboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.example.learnerleaderboard.databinding.ActivitySubmissionBinding;
import android.example.learnerleaderboard.databinding.ConfirmdialogBinding;
import android.example.learnerleaderboard.databinding.FailuredialogBinding;
import android.example.learnerleaderboard.databinding.SuccessdialogBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmissionActivity extends AppCompatActivity {

    ActivitySubmissionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_submission);

       binding.button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(binding.editText.getText().toString().equals("")||binding.editText2.getText().toString().equals("")||binding.editTextTextPersonName.getText().toString().equals("")||binding.editTextTextPersonName4.getText().toString().equals("")){
                   Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(getApplicationContext(), "calling api...", Toast.LENGTH_SHORT).show();
                   confirmSubmission();

               }

           }
       });

    }

    public void confirmSubmission() {
        ConfirmdialogBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(SubmissionActivity.this), R.layout.confirmdialog, null, false);

        Dialog dialog = new Dialog(this, R.style.Theme_MaterialComponents_Light_Dialog);
        dialog.setContentView(binding.getRoot());
        binding.cancel.setOnClickListener(view -> dialog.dismiss());
        binding.btnPositive.setOnClickListener(view -> {
            //Make the actual project submission here.
            submitData();
            Toast.makeText(SubmissionActivity.this, "Submitting data", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    public void submitData() {
        String email_address = binding.editText.getText().toString().trim();
        String first_name = binding.editText2.getText().toString().trim();
        String last_name = binding.editTextTextPersonName.getText().toString().trim();
        String project_link = binding.editTextTextPersonName4.getText().toString().trim();

        RetrofitApiCalls google_service = RetrofitClient.getGoogleFormsRetrofitInstance().create(RetrofitApiCalls.class);
        Call<ResponseBody> call = google_service.submitGoogleForm(email_address, first_name, last_name, project_link);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    //Display Success dialog
                    successDialog();
                    clearInputs();
                    //Toast.makeText(SubmissionActivity.this, "Response Code: " + response.code(), Toast.LENGTH_LONG).show();

                } else {
                    failedDialog();
                    //Toast.makeText(SubmissionActivity.this, "Response Code: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Display failed dialog
                failedDialog();
            }
        });
    }

    public void successDialog() {
        SuccessdialogBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(SubmissionActivity.this), R.layout.successdialog, null, false);

        Dialog dialog = new Dialog(this, R.style.Theme_MaterialComponents_Light_Dialog);
        dialog.setContentView(binding.getRoot());
        dialog.show();
    }

    public void failedDialog() {
        FailuredialogBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(SubmissionActivity.this), R.layout.failuredialog, null, false);

        Dialog dialog = new Dialog(this, R.style.Theme_MaterialComponents_Light_Dialog);
        dialog.setContentView(binding.getRoot());
        dialog.show();
    }

    public void clearInputs() {
        binding.editText.setText("");
        binding.editText2.setText("");
        binding.editTextTextPersonName.setText("");
        binding.editTextTextPersonName4.setText("");
    }
}