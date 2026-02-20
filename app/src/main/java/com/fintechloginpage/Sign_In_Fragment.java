package com.fintechloginpage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Sign_In_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sign_In_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Sign_In_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Sign_In_Fragment newInstance(String param1, String param2) {
        Sign_In_Fragment fragment = new Sign_In_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextInputLayout gmailLayout, passwordLayout;
    private TextInputEditText editTextGmail, editTextPassword;
    MaterialCheckBox rememberCheck;

    Boolean isAllFieldChecked;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton signInButton = view.findViewById(R.id.signInBtn);
        MaterialButton createAccount = view.findViewById(R.id.account_creation);
        createAccount.setRippleColor(null);
        MaterialButton forgetPassword = view.findViewById(R.id.forgetPassword);
        forgetPassword.setRippleColor(null);
        MaterialButton terms_policies = view.findViewById(R.id.terms_policies);
        terms_policies.setRippleColor(null);

        gmailLayout = view.findViewById(R.id.gmail_layout);
        passwordLayout = view.findViewById(R.id.password_layout);

        editTextGmail = view.findViewById(R.id.gmail_editText);
        editTextPassword = view.findViewById(R.id.password_editText);

        rememberCheck = view.findViewById(R.id.rememberMe);
        isAllFieldChecked = false;

        //Login Button Click
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldChecked = validateFields();
                if(isAllFieldChecked){
                    Fragment messageFragment = new MessagePage();
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main, messageFragment)
                            .addToBackStack(null)
                            .commit();
                    clearForm();
                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Sign_In_Fragment","Register Btn Clicked");
                Fragment newSignUpPage = new Sign_Up_fragment();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, newSignUpPage)
                        .addToBackStack(null)
                        .commit();
            }
        });

        clearErrorOnTyping(editTextGmail, gmailLayout);
        clearErrorOnTyping(editTextPassword, passwordLayout);
    }

    private void clearErrorOnTyping(TextInputEditText editText, TextInputLayout layout) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordLayout.setEndIconMode(passwordLayout.END_ICON_PASSWORD_TOGGLE);
                layout.setError(null);
            }
        });
    }
    private boolean validateFields(){
        String email = editTextGmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextGmail.requestFocus();
            editTextGmail.setError("Required to fill email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextGmail.requestFocus();
            editTextGmail.setError("Enter valid email");
            return false;
        } else {
            gmailLayout.setErrorEnabled(false);
        }

        if(password.isEmpty()){
            passwordLayout.setEndIconMode(passwordLayout.END_ICON_NONE);
            editTextPassword.requestFocus();
            editTextPassword.setError("Required to fill password");
            return false;
        } else if (password.length() < 8 || password.contains(" ")) {
            passwordLayout.setEndIconMode(passwordLayout.END_ICON_NONE);
            editTextPassword.requestFocus();
            editTextPassword.setError("Enter valid 8 digit valid password without space");
            return false;
        } else {
            passwordLayout.setError(null);
            passwordLayout.setEndIconMode(passwordLayout.END_ICON_PASSWORD_TOGGLE);
        }

        if(!rememberCheck.isChecked()){
            Toast.makeText(requireContext(), "Read all terms & policies carefully and check remember me", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            rememberCheck.setError(null);
        }
        return true;
    }

    private void clearForm(){
        editTextGmail.setText("");
        editTextPassword.setText("");
        rememberCheck.setChecked(false);

    }
}