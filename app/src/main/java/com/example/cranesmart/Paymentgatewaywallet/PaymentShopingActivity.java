package com.example.cranesmart.Paymentgatewaywallet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cranesmart.Activity.AddressActivity;
import com.example.cranesmart.Activity.CartActivity;
import com.example.cranesmart.Adapter.addresslistadapter;
import com.example.cranesmart.Api.Apiused.APIService;
import com.example.cranesmart.Api.Apiused.APIUrl;
import com.example.cranesmart.Api.refreshinterface.Adapterinterface;
import com.example.cranesmart.R;
import com.example.cranesmart.StringName.Sharedprefence;
import com.example.cranesmart.pojo.Checkoutpojo.Checkoutapi;
import com.example.cranesmart.pojo.Userdetail.UserDetailpojo;
import com.example.cranesmart.pojo.address.Addaddresslist;
import com.example.cranesmart.pojo.address.Addresslistdatum;
import com.google.android.material.textfield.TextInputLayout;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PaymentShopingActivity extends BaseActivity implements View.OnClickListener, Adapterinterface {
    public static final String TAG = "MainActivity : ";
    private boolean isDisableExitConfirmation = false;
    private String userMobile, userEmail;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private SharedPreferences userDetailsPreference;
    private EditText email_et, mobile_et, amount_et;
    private TextInputLayout email_til, mobile_til;
    private RadioGroup radioGroup_color_theme, radioGroup_select_env;
    private SwitchCompat switch_disable_wallet, switch_disable_netBanks, switch_disable_cards, switch_disable_ThirdPartyWallets, switch_disable_ExitConfirmation;
    private TextView logoutBtn;
    private RadioButton radio_btn_default;
    private AppPreference mAppPreference;
    private RadioButton radio_btn_theme_purple, radio_btn_theme_pink, radio_btn_theme_green, radio_btn_theme_grey;
    String taxid,hashkeyk,encoded_orderid;
    int amountshoping;
    private Button payNowButton;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    String status;
    Boolean outStatus=false;
    RelativeLayout relative1;
    RecyclerView recyleaddress;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Addresslistdatum> product;
    ImageView menu;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppPreference = new AppPreference();
        settings = getSharedPreferences("settings", MODE_PRIVATE);

        logoutBtn = (TextView) findViewById(R.id.logout_button);
        email_et = (EditText) findViewById(R.id.email_et);
        mobile_et = (EditText) findViewById(R.id.mobile_et);
        amount_et = (EditText) findViewById(R.id.amount_et);
//        amount_et.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});
        email_til = (TextInputLayout) findViewById(R.id.email_til);
        mobile_til = (TextInputLayout) findViewById(R.id.mobile_til);
        radioGroup_color_theme = (RadioGroup) findViewById(R.id.radio_grp_color_theme);
        radio_btn_default =  findViewById(R.id.radio_btn_theme_default);
        radio_btn_theme_pink =  findViewById(R.id.radio_btn_theme_pink);
        radio_btn_theme_purple = findViewById(R.id.radio_btn_theme_purple);
        radio_btn_theme_green =  findViewById(R.id.radio_btn_theme_green);
        radio_btn_theme_grey =  findViewById(R.id.radio_btn_theme_grey);

        checkout();
        userdetail();
        product= new ArrayList<>();
        recyleaddress=findViewById(R.id.recyleaddress);
        relative1=findViewById(R.id.relative1);
        Sizee();
        menu=findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        relative1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PaymentShopingActivity.this, AddressActivity.class);
                i.putExtra("postion","123456789");
                startActivity(i);
            }
        });
        if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            logoutBtn.setVisibility(View.GONE);
        }

        logoutBtn.setOnClickListener(this);
        RadioButton radio_btn_sandbox =findViewById(R.id.radio_btn_sandbox);
        RadioButton radio_btn_production =  findViewById(R.id.radio_btn_production);
        radioGroup_select_env = (RadioGroup) findViewById(R.id.radio_grp_env);

        payNowButton = (Button) findViewById(R.id.pay_now_button);
        payNowButton.setOnClickListener(this);
        setUpUserDetails();
        initListeners();

        //Set Up SharedPref


        if (settings.getBoolean("is_prod_env", false)) {
            ((BaseApplication) getApplication()).setAppEnvironment(AppEnvironment.PRODUCTION);
            radio_btn_production.setChecked(true);
        } else {
            ((BaseApplication) getApplication()).setAppEnvironment(AppEnvironment.SANDBOX);
            radio_btn_sandbox.setChecked(true);
        }
        setupCitrusConfigs();
    }

    public static String hashCal(String str) {

        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            Log.d(TAG, "hashCal: "+hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                Log.d(TAG, "hashCal: "+hex);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }


    public static void setErrorInputLayout(EditText editText, String msg, TextInputLayout textInputLayout) {
        textInputLayout.setError(msg);
        editText.requestFocus();
    }

    public static boolean isValidEmail(String strEmail) {
        return strEmail != null && android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches();
    }

    public static boolean isValidPhone(String phone) {
        Pattern pattern = Pattern.compile(AppPreference.PHONE_PATTERN);

        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private void setUpUserDetails() {
        userDetailsPreference = getSharedPreferences(AppPreference.USER_DETAILS, MODE_PRIVATE);
        userEmail = userDetailsPreference.getString(AppPreference.USER_EMAIL, mAppPreference.getDummyEmail());

        userMobile = userDetailsPreference.getString(AppPreference.USER_MOBILE, "");
        SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
        String email=pref.getString("email","0");
        String mobil=pref.getString("mobile","0");
        String amountshoping=pref.getString("amountaddress","0");
        Log.d(TAG, "setUpUserDetails: "+String.valueOf(amountshoping));
        email_et.setText(email);
        mobile_et.setText(mobil);
        amount_et.setText(String.valueOf(amountshoping));
        launchPayUMoneyFlow();
        restoreAppPref();

    }

    private void restoreAppPref() {


        //Set Up saved theme pref
        switch (AppPreference.selectedTheme) {
            case -1:
                radio_btn_default.setChecked(true);
                break;
            case R.style.AppTheme_pink:
                radio_btn_theme_pink.setChecked(true);
                break;
            case R.style.AppTheme_Grey:
                radio_btn_theme_grey.setChecked(true);
                break;
            case R.style.AppTheme_purple:
                radio_btn_theme_purple.setChecked(true);
                break;
            case R.style.AppTheme_Green:
                radio_btn_theme_green.setChecked(true);
                break;
            default:
                radio_btn_default.setChecked(true);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        payNowButton.setEnabled(true);

        if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            logoutBtn.setVisibility(View.GONE);
        }
        if (outStatus=true) {}
    }

    /**
     * This function sets the mode to PRODUCTION in Shared Preference
     */
    private void selectProdEnv() {

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ((BaseApplication) getApplication()).setAppEnvironment(AppEnvironment.PRODUCTION);
                editor = settings.edit();
                editor.putBoolean("is_prod_env", true);
                editor.apply();

                if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
                    logoutBtn.setVisibility(View.VISIBLE);
                } else {
                    logoutBtn.setVisibility(View.GONE);
                }

                setupCitrusConfigs();
            }
        }, AppPreference.MENU_DELAY);
    }

    /**
     * This function sets the mode to SANDBOX in Shared Preference
     */
    private void selectSandBoxEnv() {
        ((BaseApplication) getApplication()).setAppEnvironment(AppEnvironment.SANDBOX);
        editor = settings.edit();
        editor.putBoolean("is_prod_env", false);
        editor.apply();

        if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
            logoutBtn.setVisibility(View.VISIBLE);
        } else {
            logoutBtn.setVisibility(View.GONE);

        }
        setupCitrusConfigs();
    }

    private void setupCitrusConfigs() {
        AppEnvironment appEnvironment = ((BaseApplication) getApplication()).getAppEnvironment();
        if (appEnvironment == AppEnvironment.PRODUCTION) {
//            Toast.makeText(PaymentShopingActivity.this, "Environment Set to Production", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(PaymentShopingActivity.this, "Environment Set to SandBox", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This function sets the layout for activity
     */
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_payment;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                    surl();
                } else {
                    furl();
                    //Failure Transaction
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
                Log.d(TAG, "onActivityResult: "+merchantResponse);

                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage("Payu's Data : " + payuResponse + "\n\n\n Merchant's Data: " + merchantResponse)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                                Intent i= new Intent(PaymentShopingActivity.this,CartActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }).show();
                outStatus=true;

            } else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
                outStatus=true;
            } else {
                Log.d(TAG, "Both objects are null!");
                outStatus=true;
            }
        }
    }

    @Override
    public void onClick(View v) {
        userEmail = email_et.getText().toString().trim();
        userMobile = mobile_et.getText().toString().trim();
        if (v.getId() == R.id.logout_button || validateDetails(userEmail, userMobile)) {
            switch (v.getId()) {
                case R.id.pay_now_button:
                    payNowButton.setEnabled(false);
                    launchPayUMoneyFlow();
                    break;
                case R.id.logout_button:
                    PayUmoneyFlowManager.logoutUser(getApplicationContext());
                    logoutBtn.setVisibility(View.GONE);
                    break;
            }
        }
    }

    private void initListeners() {
        email_et.addTextChangedListener(new EditTextInputWatcher(email_til));
        mobile_et.addTextChangedListener(new EditTextInputWatcher(mobile_til));


        radioGroup_color_theme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                mAppPreference.setOverrideResultScreen(true);

                switch (i) {
                    case R.id.radio_btn_theme_default:
                        AppPreference.selectedTheme = -1;
                        break;
                    case R.id.radio_btn_theme_pink:
                        AppPreference.selectedTheme = R.style.AppTheme_pink;
                        break;
                    case R.id.radio_btn_theme_grey:
                        AppPreference.selectedTheme = R.style.AppTheme_Grey;
                        break;
                    case R.id.radio_btn_theme_purple:
                        AppPreference.selectedTheme = R.style.AppTheme_purple;
                        break;
                    case R.id.radio_btn_theme_green:
                        AppPreference.selectedTheme = R.style.AppTheme_Green;
                        break;
                    default:
                        AppPreference.selectedTheme = -1;
                        break;
                }
            }
        });

        radioGroup_select_env.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.radio_btn_sandbox:
                        selectSandBoxEnv();
                        break;
                    case R.id.radio_btn_production:
                        selectProdEnv();
                        break;
                    default:
                        selectSandBoxEnv();
                        break;
                }
            }
        });
    }

    /**
     * This fucntion checks if email and mobile number are valid or not.
     *
     * @param email  email id entered in edit text
     * @param mobile mobile number entered in edit text
     * @return boolean value
     */
    public boolean validateDetails(String email, String mobile) {
        email = email.trim();
        mobile = mobile.trim();

        if (TextUtils.isEmpty(mobile)) {
            setErrorInputLayout(mobile_et, getString(R.string.err_phone_empty), mobile_til);
            return false;
        } else if (!isValidPhone(mobile)) {
            setErrorInputLayout(mobile_et, getString(R.string.err_phone_not_valid), mobile_til);
            return false;
        } else if (TextUtils.isEmpty(email)) {
            setErrorInputLayout(email_et, getString(R.string.err_email_empty), email_til);
            return false;
        } else if (!isValidEmail(email)) {
            setErrorInputLayout(email_et, getString(R.string.email_not_valid), email_til);
            return false;
        } else {
            return true;
        }
    }

    /**
     * This function prepares the data for payment and launches payumoney plug n play sdk
     */
    private void launchPayUMoneyFlow() {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText(((EditText) findViewById(R.id.status_page_et)).getText().toString());

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle(((EditText) findViewById(R.id.activity_title_et)).getText().toString());

        payUmoneyConfig.disableExitConfirmation(isDisableExitConfirmation);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        double amount = 0;
        try {
            amount = Double.parseDouble(amount_et.getText().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        String txnId = System.currentTimeMillis() + "TXNID720431525261327973";
        Log.d(TAG, "launchPayUMoneyFlow: "+txnId);
        //String txnId = "TXNID720431525261327973";
        String phone = mobile_til.getEditText().getText().toString().trim();
        String productName = mAppPreference.getProductInfo();
        String firstName = mAppPreference.getFirstName();
        String email = email_til.getEditText().getText().toString().trim();
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";

        SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
        String merchant_ID=pref.getString(Sharedprefence.Merchant_Id,"0");
        Log.d(TAG, "launchPayUMoneyFlow: "+merchant_ID);
        String merchant_Key=pref.getString(Sharedprefence.Merchant_Key,"0");
        Log.d(TAG, "launchPayUMoneyFlow: "+merchant_Key);

        AppEnvironment appEnvironment = ((BaseApplication) getApplication()).getAppEnvironment();
        builder.setAmount(String.valueOf(amount))
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(appEnvironment.surl())
                .setfUrl(appEnvironment.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(appEnvironment.debug())
                .setKey(merchant_Key)
                .setMerchantId(merchant_ID);

        try {
            mPaymentParams = builder.build();

            /*
             * Hash should always be generated from your server side.
             * */
            //    generateHashFromServer(mPaymentParams);

            /*            *//**
             * Do not use below code when going live
             * Below code is provided to generate hash from sdk.
             * It is recommended to generate hash from server side only.
             * */
            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);

            if (AppPreference.selectedTheme != -1) {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentShopingActivity.this, AppPreference.selectedTheme,mAppPreference.isOverrideResultScreen());
            } else {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentShopingActivity.this, R.style.AppTheme_default, mAppPreference.isOverrideResultScreen());
            }

        } catch (Exception e) {
            // some exception occurred
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//            payNowButton.setEnabled(true);
        }
    }

    /**
     * Thus function calculates the hash for transaction
     *
     * @param paymentParam payment params of transaction
     * @return payment params along with calculated merchant hash
     */
    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");
        SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
        String merchant_salt=pref.getString(Sharedprefence.Salt,"0");
        AppEnvironment appEnvironment = ((BaseApplication) getApplication()).getAppEnvironment();
        stringBuilder.append(merchant_salt);
        Log.d(TAG, "calculateServerSideHashAndInitiatePayment1: "+merchant_salt);
        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);
        Log.d(TAG, "calculateServerSideHashAndInitiatePaymenthash: "+hash);
        return paymentParam;
    }

    /**
     * This method generates hash from server.
     *
     * @param paymentParam payments params used for hash generation
     */
    public void generateHashFromServer(PayUmoneySdkInitializer.PaymentParam paymentParam) {
        //nextButton.setEnabled(false); // lets not allow the user to click the button again and again.

        HashMap<String, String> params = paymentParam.getParams();

        // lets create the post params
        StringBuffer postParamsBuffer = new StringBuffer();
        postParamsBuffer.append(concatParams(PayUmoneyConstants.KEY, params.get(PayUmoneyConstants.KEY)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.AMOUNT, params.get(PayUmoneyConstants.AMOUNT)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.TXNID, params.get(PayUmoneyConstants.TXNID)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.EMAIL, params.get(PayUmoneyConstants.EMAIL)));
        postParamsBuffer.append(concatParams("productinfo", params.get(PayUmoneyConstants.PRODUCT_INFO)));
        postParamsBuffer.append(concatParams("firstname", params.get(PayUmoneyConstants.FIRSTNAME)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF1, params.get(PayUmoneyConstants.UDF1)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF2, params.get(PayUmoneyConstants.UDF2)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF3, params.get(PayUmoneyConstants.UDF3)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF4, params.get(PayUmoneyConstants.UDF4)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF5, params.get(PayUmoneyConstants.UDF5)));

        String postParams = postParamsBuffer.charAt(postParamsBuffer.length() - 1) == '&' ? postParamsBuffer.substring(0, postParamsBuffer.length() - 1).toString() : postParamsBuffer.toString();

        // lets make an api call
        GetHashesFromServerTask getHashesFromServerTask = new GetHashesFromServerTask();
        getHashesFromServerTask.execute(postParams);
    }


    protected String concatParams(String key, String value) {
        return key + "=" + value + "&";
    }

    @Override
    public void onMethodCallback() {

    }

    /**
     * This AsyncTask generates hash from server.
     */
    private class GetHashesFromServerTask extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PaymentShopingActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... postParams) {

            String merchantHash = "";
            try {
                //TODO Below url is just for testing purpose, merchant needs to replace this with their server side hash generation url
                URL url = new URL("https://payu.herokuapp.com/get_hash");

                String postParam = postParams[0];

                byte[] postParamsByte = postParam.getBytes("UTF-8");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postParamsByte);

                InputStream responseInputStream = conn.getInputStream();
                StringBuffer responseStringBuffer = new StringBuffer();
                byte[] byteContainer = new byte[1024];
                for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
                    responseStringBuffer.append(new String(byteContainer, 0, i));
                }

                JSONObject response = new JSONObject(responseStringBuffer.toString());

                Iterator<String> payuHashIterator = response.keys();
                while (payuHashIterator.hasNext()) {
                    String key = payuHashIterator.next();
                    switch (key) {
                        /**
                         * This hash is mandatory and needs to be generated from merchant's server side
                         *
                         */
                        case "payment_hash":
                            merchantHash = response.getString(key);
                            Log.d(TAG, "doInBackground: "+merchantHash);
                            break;
                        default:
                            break;
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return merchantHash;
        }

        @Override
        protected void onPostExecute(String merchantHash) {
            super.onPostExecute(merchantHash);

            progressDialog.dismiss();
            payNowButton.setEnabled(true);

            if (merchantHash.isEmpty() || merchantHash.equals("")) {
                Toast.makeText(PaymentShopingActivity.this, "Could not generate hash", Toast.LENGTH_SHORT).show();
            } else {
                mPaymentParams.setMerchantHash(merchantHash);

                if (AppPreference.selectedTheme != -1) {
                    PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentShopingActivity.this, AppPreference.selectedTheme, mAppPreference.isOverrideResultScreen());
                } else {
                    PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentShopingActivity.this, R.style.AppTheme_default, mAppPreference.isOverrideResultScreen());
                }
            }
        }
    }

    public static class EditTextInputWatcher implements TextWatcher {

        private TextInputLayout textInputLayout;

        EditTextInputWatcher(TextInputLayout textInputLayout) {
            this.textInputLayout = textInputLayout;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() > 0) {
                textInputLayout.setError(null);
                textInputLayout.setErrorEnabled(false);
            }
        }
    }

    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }

    }
    private void  surl() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        SharedPreferences pref = this.getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value1 = pref.getString(Sharedprefence.UserID, "0");
        String value = pref.getString(Sharedprefence.Encoded_id, "0");
        String email=pref.getString(Sharedprefence.Email,"0");
        String hashkey=pref.getString(Sharedprefence.HashKeys,"0");
        String tax=pref.getString(Sharedprefence.Taxnid,"0");
        String firstname=pref.getString(Sharedprefence.Name,"0");
        String merchant_Key=pref.getString(Sharedprefence.Merchant_Key,"0");
        String amo=pref.getString(Sharedprefence.Amountshop,"0");
        assert value1 != null;
        Log.d("@@shared@@12surl", value1);
        Log.d("@@shared@@12surl", value);
        Log.d("surlresponse", "surl: "+email);
        Log.d("surlresponse", "surl: "+amo);
        Log.d("surlresponse", "surl: "+hashkey);
        Log.d("surlresponse", "surl: "+tax);
        Log.d("surlresponse", "surl: "+firstname);
        Log.d("surlresponse", "surl: "+merchant_Key);
        Call<Paymentshopingpojo> call = service.paymentorder(value1,value, String.valueOf(amo),"success",tax,hashkey,firstname,merchant_Key,Sharedprefence.Productinfshopping,email);
        call.enqueue(new Callback<Paymentshopingpojo>() {
            @Override
            public void onResponse(Call<Paymentshopingpojo> call, Response<Paymentshopingpojo> response) {
                progressDialog.dismiss();
                Log.d(TAG, "onResponse: "+response.body().getStatus());
                Log.d(TAG, "onResponse: "+response.body().getMessage());

            }

            @Override
            public void onFailure(Call<Paymentshopingpojo> call, Throwable t) {

            }


        });

    }
    private void  furl() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        SharedPreferences pref = this.getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value1 = pref.getString(Sharedprefence.UserID, "0");
        String value = pref.getString(Sharedprefence.Encoded_id, "0");
        String tax=pref.getString(Sharedprefence.Taxnid,"0");
        String amo=pref.getString(Sharedprefence.Amountshop,"0");
        assert value1 != null;
        Log.d("@@shared@@12furl", value1);
        Log.d("@@shared@@12furl", value);
        Log.d("surlresponse", "furl: "+amountshoping);
        Log.d("surlresponse", "furl: "+tax);
        Call<JSONObject> call = service.failorder(value1,encoded_orderid, amo,"failed",taxid);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                progressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }


        });

    }

    private void userdetail() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        SharedPreferences sh=getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value=sh.getString(Sharedprefence.UserID,"0"); assert value != null;



        Call<UserDetailpojo> call = service.userdetail(value);
        call.enqueue(new Callback<UserDetailpojo>() {
            @Override
            public void onResponse(Call<UserDetailpojo> call, Response<UserDetailpojo> response) {
//                progressDialog.dismiss();

                if(response.body().getUserDetail().getProfilePhoto()!=null||response.body().getUserDetail().getName()!=null){
                    String nameurl=response.body().getUserDetail().getName().toString().trim();
                    SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString(Sharedprefence.Name,nameurl);
                    editor.putString(Sharedprefence.Email,response.body().getUserDetail().getEmail());
                    editor.commit();
                    editor.apply();

                }
                else{
                    Toast.makeText(PaymentShopingActivity.this, "null pointer expecption is there", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDetailpojo> call, Throwable t) {
                Toast.makeText(PaymentShopingActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void checkout() {
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        SharedPreferences sh = this.getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value = sh.getString(Sharedprefence.UserID, "0");
        String value1 = sh.getString("addID", "0");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<Checkoutapi> call = service.order(
                value, value1
        );

        call.enqueue(new Callback<Checkoutapi>() {
            @Override
            public void onResponse(Call<Checkoutapi> call, Response<Checkoutapi> response) {
                Log.d("Sharedpre", String.valueOf(response.body().getStatus()));
                Log.d("Sharedpre", String.valueOf(response.body().getMessage()));
                progressDialog.dismiss();
                if(response.body().getStatus()==1){
                    SharedPreferences pref =getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("amountaddress", response.body().getData().getAmount().toString());
                    Log.d(TAG, "onResponse: "+ response.body().getData().getAmount().toString());
                    editor.commit();
                    editor.apply();
//                amountorder=response.body().getData().getAmount();
                    Log.d(TAG, "onResponse010: "+ response.body().getData().getAmount().toString());
                    amountshoping= Integer.parseInt(response.body().getData().getAmount().toString());
                    Log.d(TAG+"hfghfg", "onResponse: "+amountshoping);
                taxid=response.body().getData().getTxnid();
                hashkeyk=response.body().getData().getHash();
                encoded_orderid=response.body().getData().getEncodedOrderId();}
                else {
                    amountshoping = 0000;
                    taxid = "0000";
                    hashkeyk = "0000";
                    encoded_orderid = "0000";
                }
            }

            @Override
            public void onFailure(Call<Checkoutapi> call, Throwable t) {
                   progressDialog.dismiss();
            }


        });
    }
    private void Addresslist(){
        layoutManager = new LinearLayoutManager(PaymentShopingActivity.this);
        recyleaddress.setLayoutManager(layoutManager);
        final addresslistadapter adapter = new addresslistadapter(product,this);
        recyleaddress.setHasFixedSize(true);
        recyleaddress.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void Sizee() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, null, null, true);
        progressDialog.setContentView(R.layout.custom_loader);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        SharedPreferences sh=getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
        String value=sh.getString(Sharedprefence.UserID,"0");
        Call<Addaddresslist> call = service.addresslist(value);
        call.enqueue(new Callback<Addaddresslist>() {
            @Override
            public void onResponse(Call<Addaddresslist> call, Response<Addaddresslist> response) {
                progressDialog.dismiss();
                if(response.body().getStatus()==1) {
                    if (response.body().getData()!=null) {
                        for (i = 0; i < response.body().getData().size(); i++) {
                            SharedPreferences pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("addID", response.body().getData().get(i).getAddID().trim());
                            editor.putString("CountryId11", response.body().getData().get(i).getCountryId().trim());
                            editor.putString("StateId11", response.body().getData().get(i).getStateId().trim());
                            Log.d("##!!!", "onResponse: "+response.body().getData().get(i).getCountryId().trim());
//                            Log.d("Addressid!!!", "onResponse: "+response.body().getData().get(i).getCountry().trim());
//                            Log.d("Addressid!!!", "onResponse: "+response.body().getData().get(i).getState().trim());
                            Log.d("Addressid!!!", "onResponse: "+response.body().getData().get(i).getAddress1().trim());
                            Log.d("Addressid!!!", "onResponse: "+response.body().getData().get(i).getName().trim());
                            Log.d("Addressid!!!", "onResponse: "+response.body().getData().get(i).getPhoneNo().trim());
                            Log.d("Addressid!!!", "onResponse: "+response.body().getData().get(i).getCity().trim());
                            Log.d("Addressid!!!", "onResponse: "+response.body().getData().get(i).getZipCode().trim());
                            editor.commit();
                            editor.apply();
                            product.addAll(Arrays.asList(response.body().getData().get(i)));
                        }
                        Addresslist();
                    }
                }
                else if(response.body().getStatus()==0){
                }

            }

            @Override
            public void onFailure(Call<Addaddresslist> call, Throwable t) {

            }


        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
      SharedPreferences  pref = getSharedPreferences(Sharedprefence.SharedprefenceName, Context.MODE_PRIVATE);
      SharedPreferences.Editor  editor = pref.edit();
        editor.putString("amountaddress", "0");
        editor.putString("taxaddress","0");
        editor.putString("oredreidaddress","0");
        editor.putString("hashaddress","0");
        editor.apply();
        editor.commit();
        Intent i=new Intent(PaymentShopingActivity.this, CartActivity.class);
        startActivity(i);
        finish();
    }
}
