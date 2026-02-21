package com.fintechloginpage.Fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.fintechloginpage.R;
import com.fintechloginpage.SignUpMessage;
import com.fintechloginpage.Sign_In_Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link set_up_screen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class set_up_screen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public set_up_screen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment set_up_screen.
     */
    // TODO: Rename and change types and number of parameters
    public static set_up_screen newInstance(String param1, String param2) {
        set_up_screen fragment = new set_up_screen();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_up_screen, container, false);
    }

    private TextInputLayout birthday_layout, currencyLayout;
    private TextInputEditText editTextBirthday;
    MaterialCheckBox rememberCheck;
    MaterialButton terms_policies_setUp, skipButton, finishSetUpButton;
    Boolean isAllFieldChecked;
    AutoCompleteTextView currencyMenu;
    MaterialSwitch subscription, expense, pushAlert;
    CircularProgressIndicator profileImageLoader;

    private ImageView profileImage;
    private ImageButton uploadButton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        terms_policies_setUp = view.findViewById(R.id.terms_policies_setUp);
        terms_policies_setUp.setRippleColor(null);
        skipButton = view.findViewById(R.id.skipButton);
        finishSetUpButton = view.findViewById(R.id.finishSetupButton);

        birthday_layout = view.findViewById(R.id.layout_birthday);
        currencyLayout = view.findViewById(R.id.layout_currency);
        editTextBirthday = view.findViewById(R.id.editText_Birthday);

        subscription = view.findViewById(R.id.subscriptionCheckBox);
        expense = view.findViewById(R.id.expenseCheckBox);
        pushAlert = view.findViewById(R.id.pushAlertCheckBox);
        rememberCheck = view.findViewById(R.id.rememberMeSetUp);
        isAllFieldChecked = false;

        profileImage = view.findViewById(R.id.profileImage);
        uploadButton = view.findViewById(R.id.uploadButton);
        profileImageLoader = view.findViewById(R.id.profileImageLoadingIndicator);

        finishSetUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAllFieldChecked = validateFields();
                if(isAllFieldChecked){
                    clearForm();
                    Fragment messageScreen = new SignUpMessage();
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout, messageScreen)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment backToSignIn = new Sign_In_Fragment();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, backToSignIn)
                        .addToBackStack(null)
                        .commit();
            }
        });

        currencyMenu = view.findViewById(R.id.currency_menu);
        currencyMenu.setVerticalScrollBarEnabled(false);
        currencyMenu.setOverScrollMode(View.OVER_SCROLL_NEVER);
        String  dropDownMenu[] = {"Dollar ($)", "Euro (€)", "Pound (£)", "Yen (¥)", "Yuan (¥)", "Rupee (₹)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_dropdown_item_1line, dropDownMenu
        );

        currencyMenu.setAdapter(adapter);
        currencyMenu.setOnItemClickListener((parent, view1, position, id) ->{
            currencyLayout.setError(null);
        });
        currencyMenu.setDropDownWidth(300);
        currencyMenu.setDropDownHeight(500);

        subscription.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if(isChecked){
                subscription.setThumbTintList(
                        ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.thumbColorGreen)
                        )
                );
                subscription.setTrackTintList(
                        ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.trackColorGreen)
                        )
                );
                Toast.makeText(requireContext(), "Subscription enabled", Toast.LENGTH_SHORT).show();
            }else{
                subscription.setThumbTintList(
                        ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.thumbColorWhite)
                        )
                );
                subscription.setTrackTintList(
                        ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.trackColorGrey)
                        )
                );
                Toast.makeText(requireContext(), "Subscription disabled", Toast.LENGTH_SHORT).show();
            }
        });

        expense.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if(isChecked){
                expense.setThumbTintList(
                        ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.thumbColorGreen)
                        )
                );
                expense.setTrackTintList(
                        ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.trackColorGreen)
                        )
                );
                Toast.makeText(requireContext(), "Expense enabled", Toast.LENGTH_SHORT).show();
            }else{
                expense.setThumbTintList(
                        ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.thumbColorWhite)
                        )
                );
                expense.setTrackTintList(
                        ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.trackColorGrey)
                        )
                );
                Toast.makeText(requireContext(), "Expense disabled", Toast.LENGTH_SHORT).show();
            }
        });

        pushAlert.setOnCheckedChangeListener((buttonView, isChecked) ->{
            if(isChecked){
                pushAlert.setThumbTintList(
                        ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.thumbColorGreen)
                        )
                );
                pushAlert.setTrackTintList(
                        ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.trackColorGreen)
                        )
                );
                Toast.makeText(requireContext(), "Alert enabled", Toast.LENGTH_SHORT).show();
            }else{
                pushAlert.setThumbTintList(
                        ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.thumbColorWhite)
                        )
                );
                pushAlert.setTrackTintList(
                        ColorStateList.valueOf(
                                ContextCompat.getColor(requireContext(), R.color.trackColorGrey)
                        )
                );
                Toast.makeText(requireContext(), "Alert disabled", Toast.LENGTH_SHORT).show();
            }
        });

        //Birthday Calendar
        editTextBirthday.setOnClickListener(v ->{
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -18);
            long eighteenYearsAgo = calendar.getTimeInMillis();
            CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
            constraintBuilder.setEnd(eighteenYearsAgo);
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Birthday")
                    .setCalendarConstraints(constraintBuilder.build())
                    .build();

            datePicker.show(getParentFragmentManager(), "DATE_PICKER");
            datePicker.addOnPositiveButtonClickListener(selection -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = sdf.format(new Date(selection));
                editTextBirthday.setText(formattedDate);
                editTextBirthday.setError(null);
            });
        });

        //Profile Upload Image Picker
        uploadButton.setOnClickListener(v ->{
            imagePickerLauncher.launch("image/*");
        });

        //Text Watcher
        clearErrorOnTyping(editTextBirthday, birthday_layout);
    }

    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(),
                    uri -> {
                        if (uri != null) {
                            profileImageLoader.setVisibility(View.VISIBLE);
                            new Handler(Looper.getMainLooper()).postDelayed(() ->{
                                profileImageLoader.setVisibility(View.GONE);
                                profileImage.setImageURI(uri);
                            },500);
                        }
                    });

    private void clearErrorOnTyping(TextInputEditText editText, TextInputLayout layout) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()) {
                    birthday_layout.setError(null);
                    birthday_layout.setErrorEnabled(false);
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layout.setError(null);
            }
        });
    }

    private boolean validateFields(){
        String birthday = editTextBirthday.getText().toString().trim();
        String currency = currencyMenu.getText().toString().trim();

        if(birthday.isEmpty()){
            editTextBirthday.requestFocus();
            editTextBirthday.setError("Required to fill field");
            return false;
        } else {
            birthday_layout.setError(null);
        }

        if(currency.isEmpty()){
            currencyLayout.setError("Please select the currency");
            currencyMenu.requestFocus();
            return false;
        }else {
            currencyLayout.setError(null);
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
        editTextBirthday.setText("");
        currencyMenu.setText("");
        rememberCheck.setChecked(false);
    }
}