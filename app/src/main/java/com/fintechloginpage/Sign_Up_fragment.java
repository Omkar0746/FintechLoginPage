package com.fintechloginpage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fintechloginpage.Fragment.set_up_screen;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Sign_Up_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sign_Up_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Sign_Up_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sign_Up_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Sign_Up_fragment newInstance(String param1, String param2) {
        Sign_Up_fragment fragment = new Sign_Up_fragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign__up_fragment, container, false);
    }

    private TextInputLayout nameLayout, gmailLayout, phoneLayout, homeLayout, passwordLayout, confirmPasswordLayout;
    private TextInputEditText editTextName, editTextGmail, editTextPhone, editTextHome, editTextPassword, editTextConfirmPassword;
    MaterialCheckBox rememberCheck;
    Boolean isAllFieldChecked;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton ContinueButton = view.findViewById(R.id.continue_button);
        MaterialButton SignIn = view.findViewById(R.id.SignInBTN);
        SignIn.setRippleColor(null);
        MaterialButton terms_policies_signup = view.findViewById(R.id.terms_policies_signup);
        terms_policies_signup.setRippleColor(null);

        nameLayout = view.findViewById(R.id.layout_name);
        gmailLayout = view.findViewById(R.id.layout_gmail);
        phoneLayout = view.findViewById(R.id.layout_phone);
        homeLayout = view.findViewById(R.id.layout_address);
        passwordLayout = view.findViewById(R.id.layout_password);
        confirmPasswordLayout = view.findViewById(R.id.layout_confirm_password);

        editTextName = view.findViewById(R.id.editText_name);
        editTextGmail = view.findViewById(R.id.editText_gmail);
        editTextPhone = view.findViewById(R.id.editText_phone);
        editTextHome = view.findViewById(R.id.editText_address);
        editTextPassword = view.findViewById(R.id.editText_password);
        editTextConfirmPassword = view.findViewById(R.id.editText_confirm_password);

        rememberCheck = view.findViewById(R.id.checkboxRemember);
        isAllFieldChecked = false;

        //Continue Button Click
        ContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldChecked = validateFields();
                if(isAllFieldChecked){

                    Fragment messageFragment = new set_up_screen();
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout, messageFragment)
                            .addToBackStack(null)
                            .commit();

                    clearForm();
                }
            }
        });

        //SignIn Button Click
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Sign_In_Fragment","Register Btn Clicked");
                Fragment backSignInPage = new Sign_In_Fragment();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, backSignInPage)
                        .addToBackStack(null)
                        .commit();
            }
        });
        clearErrorOnTyping(editTextName, nameLayout);
        clearErrorOnTyping(editTextGmail, gmailLayout);
        clearErrorOnTyping(editTextPhone, phoneLayout);
        clearErrorOnTyping(editTextHome, homeLayout);
        clearErrorOnTyping(editTextPassword, passwordLayout);
        clearErrorOnTyping(editTextConfirmPassword, confirmPasswordLayout);
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
                confirmPasswordLayout.setEndIconMode((confirmPasswordLayout.END_ICON_PASSWORD_TOGGLE));
                layout.setError(null);
            }
        });
    }
    private boolean validateFields(){
        String name = editTextName.getText().toString().trim();
        String email = editTextGmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String home = editTextHome.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirm_password = editTextConfirmPassword.getText().toString().trim();

        if(name.isEmpty()){
            editTextName.requestFocus();
            editTextName.setError("Required to fill field");
            return false;
        }else {
            nameLayout.setError(null);
        }

        if(email.isEmpty()){
            editTextGmail.requestFocus();
            editTextGmail.setError("Required to fill field");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextGmail.requestFocus();
            editTextGmail.setError("Enter valid email");
            return false;
        } else {
            gmailLayout.setErrorEnabled(false);
        }

        if(phone.isEmpty()){
            editTextPhone.requestFocus();
            editTextPhone.setError("Required to fill field");
            return false;
        } else if (phone.length() < 10 || !Patterns.PHONE.matcher(phone).matches()  || phone.contains(" ")) {
            editTextPhone.requestFocus();
            editTextPhone.setError("Enter valid phone number");
            return false;
        } else{
            phoneLayout.setError(null);
        }

        if(home.isEmpty()){
            editTextHome.requestFocus();
            editTextHome.setError("Required to fill field");
            return false;
        }else {
            homeLayout.setError(null);
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

        if(confirm_password.isEmpty()){
            confirmPasswordLayout.setEndIconMode(confirmPasswordLayout.END_ICON_NONE);
            editTextConfirmPassword.requestFocus();
            editTextConfirmPassword.setError("Required to fill password");
            return false;
        } else if (confirm_password.length() < 8 || !confirm_password.equals(password) || password.contains(" ")) {
            confirmPasswordLayout.setEndIconMode(confirmPasswordLayout.END_ICON_NONE);
            editTextConfirmPassword.requestFocus();
            editTextConfirmPassword.setError("Password must be equal to above password");
            return false;
        } else {
            confirmPasswordLayout.setError(null);
            confirmPasswordLayout.setEndIconMode(confirmPasswordLayout.END_ICON_PASSWORD_TOGGLE);
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
        editTextName.setText("");
        editTextGmail.setText("");
        editTextPhone.setText("");
        editTextHome.setText("");
        editTextPassword.setText("");
        editTextConfirmPassword.setText("");
    }
}