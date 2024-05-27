package com.cb.moviesapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.cb.moviesapp.MainActivity;
import com.cb.moviesapp.R;
import com.cb.moviesapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.loginBtn.setOnClickListener(v->{
            String username = binding.editTextUsername.getText().toString();
            String password = binding.editTextPassword.getText().toString();
            if (username.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Username or password empty!", Toast.LENGTH_LONG).show();

            } else if (username.equals("admin") && password.equals("admin")) {
                startActivity(new Intent(this, MainActivity.class));
            }
            else{
                Toast.makeText(this, "Username or password not found!", Toast.LENGTH_LONG).show();

            }
        });
    }
}