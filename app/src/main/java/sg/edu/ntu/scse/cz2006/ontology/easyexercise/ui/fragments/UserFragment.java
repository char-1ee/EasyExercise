package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.authentication.LoginActivity;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities.authentication.UserActivity;


/**
 * The fragment class for showing all information of the user.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 * @author Li Xingjian
 * @author Ma Xinyi
 */
public class UserFragment extends Fragment implements View.OnClickListener {
    private static final List<Integer> weightRange =
            IntStream.rangeClosed(40, 100).boxed().collect(Collectors.toList());
    private static final List<Integer> heightRange =
            IntStream.range(140, 200).boxed().collect(Collectors.toList());
    private static final List<String> genderChoice =
            Arrays.asList("Female", "Male", "Prefer not to disclose");

    private View view;
    private ImageView profilePhoto;
    private TextView emailText;
    private TextView usernameText;
    private TextView genderText;
    private TextView heightText;
    private TextView weightText;
    private TextView BMIText;
    private GoogleApiClient googleApiClient;
    private GoogleSignInClient mSignInClient;
    private FirebaseAuth auth;

    private String gender;
    private int weight;
    private int height;
    private OptionsPickerView pvOptions1;
    private OptionsPickerView pvOptions2;
    private OptionsPickerView pvOptions3;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_user, container, false);
        initGoogleClient();
        initView();

        // get firebase auth instance
        auth = FirebaseAuth.getInstance();

        // get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // user auth state is changed - user is null then launch login activity
        FirebaseAuth.AuthStateListener authListener = firebaseAuth -> {
            FirebaseUser user1 = firebaseAuth.getCurrentUser();
            if (user1 == null) {

                // user auth state is changed - user is null then launch login activity
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        };
        initPicker();

        return view;
    }

    private void initView() {
        profilePhoto = view.findViewById(R.id.profile_photo);
        emailText = view.findViewById(R.id.text_email);
        usernameText = view.findViewById(R.id.profile_name);
        BMIText = view.findViewById(R.id.text_bmi);
        heightText = view.findViewById(R.id.text_height);
        weightText = view.findViewById(R.id.text_weight);
        genderText = view.findViewById(R.id.text_gender);
        Button buttonSignOut = view.findViewById(R.id.new_sign_out_button);
        Button buttonAuth = view.findViewById(R.id.edit_login_info_button);

        buttonSignOut.setOnClickListener(this);
        buttonAuth.setOnClickListener(this);
        BMIText.setOnClickListener(this);
        heightText.setOnClickListener(this);
        weightText.setOnClickListener(this);
        genderText.setOnClickListener(this);
        getUserInfo();
        weightText.setText(String.valueOf(weight));
        heightText.setText(String.valueOf(height));
        genderText.setText(gender);
        DecimalFormat df = new DecimalFormat("##.##");
        BMIText.setText(df.format(weight / (height * height / 10000.0)));
    }

    private void initGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(requireContext())
                .enableAutoManage(requireActivity(), connectionResult -> {
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_sign_out_button:
                signOut();
                break;

            case R.id.edit_login_info_button:
                startActivity(new Intent(getActivity(), UserActivity.class));
                break;

            case R.id.text_gender:
//                clearUserInfo("gender");
                clearUserGender();
                gender = null;
                pvOptions3.show();
                Handler handler3 = new Handler();
                Runnable runnable3 = new Runnable() {
                    @Override
                    public void run() {
                        if (gender == null) {
                            handler3.postDelayed(this, 1000);
                        } else {
//                            saveUserInfo("gender");
                            saveUserGender();
                            genderText.setText(gender);
                        }
                    }
                };
                handler3.post(runnable3);
                break;

            case R.id.text_weight:
//                clearUserInfo("weight");
                clearUserWeight();
                weight = 0;
                pvOptions1.show();
                Handler handler1 = new Handler();
                Runnable runnable1 = new Runnable() {
                    @Override
                    public void run() {
                        if (weight == 0) {
                            handler1.postDelayed(this, 1000);
                        } else {
//                            saveUserInfo("weight");
                            saveUserWeight();
                            weightText.setText(String.valueOf(weight));
                            DecimalFormat df = new DecimalFormat("##.##");
                            BMIText.setText(df.format(weight / (height * height / 10000.0)));
                        }
                    }
                };
                handler1.post(runnable1);
                break;

            case R.id.text_height:
//                clearUserInfo("height");
                clearUserHeight();
                height = 0;
                pvOptions2.show();
                Handler handler2 = new Handler();
                Runnable runnable2 = new Runnable() {
                    @Override
                    public void run() {
                        if (height == 0) {
                            handler2.postDelayed(this, 1000);
                        } else {
//                            saveUserInfo("height");
                            saveUserHeight();
                            heightText.setText(String.valueOf(height));
                            DecimalFormat df = new DecimalFormat("##.##");
                            BMIText.setText(df.format(weight / (height * height / 10000.0)));

                        }
                    }
                };
                handler2.post(runnable2);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(this::handleSignInResult);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(requireActivity());
        googleApiClient.disconnect();
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            assert account != null;
            usernameText.setText(account.getDisplayName());
            emailText.setText(account.getEmail());
            try {
                Glide.with(this)
                        .load(account.getPhotoUrl())
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(profilePhoto);
            } catch (NullPointerException e) {
                Toast.makeText(getContext(), "Image not found", Toast.LENGTH_LONG).show();
            }
        } else {
            gotoLoginActivity();
        }
    }

    private void signOut() {
        auth.signOut();
        mSignInClient.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    private void gotoLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


    private void saveUserHeight() {
        SharedPreferences userInfo = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.putInt("height", height);
        editor.apply();
    }

    private void saveUserWeight() {
        SharedPreferences userInfo = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.putInt("weight", weight);
        editor.apply();
    }

    private void saveUserGender() {
        SharedPreferences userInfo = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.putString("gender", gender);
        editor.apply();
    }

    private void getUserInfo() {
        SharedPreferences userInfo = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        gender = userInfo.getString("gender", "Click here to choose");
        weight = userInfo.getInt("weight", 0);
        height = userInfo.getInt("height", 0);
    }


    private void clearUserHeight() {
        SharedPreferences userInfo = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.remove("height");
        editor.commit();
    }

    private void clearUserWeight() {
        SharedPreferences userInfo = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.remove("weight");
        editor.commit();
    }

    private void clearUserGender() {
        SharedPreferences userInfo = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.remove("gender");
        editor.commit();
    }

    private void initPicker() {
        pvOptions1 = new OptionsPickerBuilder(getContext(), (options1, option2, options3, v) -> weight = weightRange.get(options1))
                .setSubmitText("Confirm")
                .setCancelText("Cancel")
                .setTitleText("Weight")
                .setSubCalSize(18)
                .setTitleSize(20)
                .setContentTextSize(18)
                .isCenterLabel(false)
                .setCyclic(true, false, false)
                .setSelectOptions(1, 1, 1)
                .setOutSideCancelable(false)
                .isDialog(false)
                .isRestoreItem(true)
                .build();
        pvOptions1.setPicker(weightRange);

        pvOptions2 = new OptionsPickerBuilder(getContext(), (options1, option2, options3, v) -> height = heightRange.get(options1))
                .setSubmitText("Confirm")
                .setCancelText("Cancel")
                .setTitleText("Height")
                .setSubCalSize(18)
                .setTitleSize(20)
                .setContentTextSize(18)
                .isCenterLabel(false)
                .setCyclic(true, false, false)
                .setSelectOptions(1, 1, 1)
                .setOutSideCancelable(false)
                .isDialog(false)
                .isRestoreItem(true)
                .build();
        pvOptions2.setPicker(heightRange);

        pvOptions3 = new OptionsPickerBuilder(getContext(), (options1, option2, options3, v) -> gender = genderChoice.get(options1))
                .setSubmitText("Confirm")
                .setCancelText("Cancel")
                .setTitleText("Gender")
                .setSubCalSize(18)
                .setTitleSize(20)
                .setContentTextSize(18)
                .isCenterLabel(false)
                .setCyclic(false, false, false)
                .setSelectOptions(1, 1, 1)
                .setOutSideCancelable(false)
                .isDialog(false)
                .isRestoreItem(true)
                .build();
        pvOptions3.setPicker(genderChoice);
    }

}
