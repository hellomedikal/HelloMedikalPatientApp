package com.pradeep.hellomedikalpatientapp.Fragment.Signup;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pradeep.hellomedikalpatientapp.Activities.Otp_Screen;
import com.pradeep.hellomedikalpatientapp.Activities.Register_Form;
import com.pradeep.hellomedikalpatientapp.Country_Phone_Picker.CCPCountry;
import com.pradeep.hellomedikalpatientapp.Country_Phone_Picker.CCPCountryGroup;
import com.pradeep.hellomedikalpatientapp.Country_Phone_Picker.CountryCodeDialog;
import com.pradeep.hellomedikalpatientapp.Country_Phone_Picker.InternationalPhoneTextWatcher;
import com.pradeep.hellomedikalpatientapp.Interface.ApiLogin_Interface;
import com.pradeep.hellomedikalpatientapp.POJO.ModelEmailVerify;
import com.pradeep.hellomedikalpatientapp.POJO.ModelPhoneVerify;
import com.pradeep.hellomedikalpatientapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;
import io.paperdb.Paper;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Signup_Phone  extends Fragment {


    String country_code1,phone1;
    Register_Form register_details;
    Button btn_register_phn;
    View holderView;
    LayoutInflater mInflater;
    TextView textView_selectedCountry,textView_Country_code;
    EditText editText_registeredCarrierNumber,edtxt_phn_number;
    RelativeLayout holder;
    ImageView imageViewArrow;
    ImageView imageViewFlag;
    LinearLayout linearFlagBorder,layout_back_login_register;
    LinearLayout linearFlagHolder;
    CCPCountry selectedCCPCountry;
    CCPCountry defaultCCPCountry;
    RelativeLayout relativeClickConsumer;
    Signup_Phone codePicker;
    String originalHint = "";
    int ccpPadding;
    Context context;
    String country_code = "";
    String number;

    private CountryCodeDialog mCountryCodeDialog;
    public static final int DEFAULT_UNSET = -99;
    static String TAG = "CCP";
    static String BUNDLE_SELECTED_CODE = "selectedCode";
    static int LIB_DEFAULT_COUNTRY_CODE = 91;
    private static int TEXT_GRAVITY_LEFT = -1, TEXT_GRAVITY_RIGHT = 1, TEXT_GRAVITY_CENTER = 0;
    private static String ANDROID_NAME_SPACE = "http://schemas.android.com/apk/res/android";
    String CCP_PREF_FILE = "CCP_PREF_FILE";
    int defaultCountryCode;
    String defaultCountryNameCode;
    // see attr.xml to see corresponding values for pref
    Signup_Phone.AutoDetectionPref selectedAutoDetectionPref = Signup_Phone.AutoDetectionPref.SIM_NETWORK_LOCALE;
    PhoneNumberUtil phoneUtil;
    boolean showNameCode = true;
    boolean showPhoneCode = true;
    boolean ccpDialogShowPhoneCode = true;
    boolean showFlag = true;
    boolean showFullName = false;
    boolean showFastScroller = true;
    boolean ccpDialogShowTitle = true;
    boolean ccpDialogShowFlag = true;
    boolean searchAllowed = true;
    boolean showArrow = true;
    boolean showCloseIcon = false;
    boolean rememberLastSelection = false;
    boolean detectCountryWithAreaCode = true;
    boolean ccpDialogShowNameCode = true;
    boolean ccpDialogInitialScrollToSelection = false;
    public boolean ccpUseEmoji = false;
    boolean ccpUseDummyEmojiForPreview = false;
    boolean internationalFormattingOnly = true;
    Signup_Phone.PhoneNumberType hintExampleNumberType = Signup_Phone.PhoneNumberType.MOBILE;
    String selectionMemoryTag = "ccp_last_selection";
    int contentColor = DEFAULT_UNSET;
    int arrowColor = DEFAULT_UNSET;
    int borderFlagColor;
    Typeface dialogTypeFace;
    int dialogTypeFaceStyle;
    public List<CCPCountry> preferredCountries;
    int ccpTextgGravity = TEXT_GRAVITY_CENTER;
    //this will be "AU,IN,US"
    String countryPreference;

    int fastScrollerBubbleColor = 0;
    public List<CCPCountry> customMasterCountriesList;
    //this will be "AU,IN,US"
    String customMasterCountriesParam, excludedCountriesParam;
    Signup_Phone.Language customDefaultLanguage = Signup_Phone.Language.ENGLISH;
    Signup_Phone.Language languageToApply = Signup_Phone.Language.ENGLISH;

    boolean dialogKeyboardAutoPopup = true;
    boolean ccpClickable = true;
    boolean autoDetectLanguageEnabled = false, autoDetectCountryEnabled = false, numberAutoFormattingEnabled = true, hintExampleNumberEnabled = false;
    String xmlWidth = "notSet";
    TextWatcher validityTextWatcher;
    InternationalPhoneTextWatcher formattingTextWatcher;
    boolean reportedValidity;
    TextWatcher areaCodeCountryDetectorTextWatcher;
    boolean countryDetectionBasedOnAreaAllowed;
    String lastCheckedAreaCode = null;
    int lastCursorPosition = 0;
    boolean countryChangedDueToAreaCode = false;
    private Signup_Phone.OnCountryChangeListener onCountryChangeListener;
    private Signup_Phone.PhoneNumberValidityChangeListener phoneNumberValidityChangeListener;
    private Signup_Phone.FailureListener failureListener;
    private Signup_Phone.DialogEventsListener dialogEventsListener;
    private Signup_Phone.CustomDialogTextProvider customDialogTextProvider;
    private int fastScrollerHandleColor = 0;
    private int dialogBackgroundResId, dialogBackgroundColor, dialogTextColor, dialogSearchEditTextTintColor;
    private int fastScrollerBubbleTextAppearance = 0;
    private CCPCountryGroup currentCountryGroup;
    private View.OnClickListener customClickListener;

    View.OnClickListener countryCodeHolderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (customClickListener == null) {
                if (isCcpClickable()) {
                    if (ccpDialogInitialScrollToSelection) {
                        launchCountrySelectionDialog(getSelectedCountryNameCode());
                    } else {
                        launchCountrySelectionDialog();
                    }
                }
            } else {
                customClickListener.onClick(v);
            }
        }
    };


    public enum PhoneNumberType {
        MOBILE,
        FIXED_LINE,
        // In some regions (e.g. the USA), it is impossible to distinguish between fixed-line and
        // mobile numbers by looking at the phone number itself.
        FIXED_LINE_OR_MOBILE,
        // Freephone lines
        TOLL_FREE,
        PREMIUM_RATE,
        // The cost of this call is shared between the caller and the recipient, and is hence typically
        // less than PREMIUM_RATE calls. See // http://en.wikipedia.org/wiki/Shared_Cost_Service for
        // more information.
        SHARED_COST,
        // Voice over IP numbers. This includes TSoIP (Telephony Service over IP).
        VOIP,
        // A personal number is associated with a particular person, and may be routed to either a
        // MOBILE or FIXED_LINE number. Some more information can be found here:
        // http://en.wikipedia.org/wiki/Personal_Numbers
        PERSONAL_NUMBER,
        PAGER,
        // Used for "Universal Access Numbers" or "Company Numbers". They may be further routed to
        // specific offices, but allow one number to be used for a company.
        UAN,
        // Used for "Voice Mail Access Numbers".
        VOICEMAIL,
        // A phone number is of type UNKNOWN when it does not fit any of the known patterns for a
        // specific region.
        UNKNOWN
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup_phone, container, false);

        btn_register_phn=view.findViewById(R.id.btn_register_phn);
        register_details=new Register_Form();


        edtxt_phn_number=view.findViewById(R.id.edtxt_phn_number);
        textView_selectedCountry = view.findViewById(R.id.textView_selectedCountry);
        textView_Country_code= view.findViewById(R.id.textView_Country_code);
        holder = view.findViewById(R.id.countryCodeHolder);
        imageViewArrow =view.findViewById(R.id.imageView_arrow);
        imageViewFlag =view.findViewById(R.id.image_flag);
        linearFlagHolder =view.findViewById(R.id.linear_flag_holder);
        linearFlagBorder =view.findViewById(R.id.linear_flag_border);
        relativeClickConsumer =view.findViewById(R.id.rlClickConsumer);
        codePicker = this;

        country_code1 = textView_Country_code.getText().toString();
        phone1 = edtxt_phn_number.getText().toString();

        relativeClickConsumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (customClickListener == null) {
                    if (isCcpClickable()) {
                        if (ccpDialogInitialScrollToSelection) {
                            launchCountrySelectionDialog(getSelectedCountryNameCode());
                        } else {
                            launchCountrySelectionDialog();
                        }
                    }
                } else {
                    customClickListener.onClick(v);
                }

            }
        });


        btn_register_phn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edtxt_phn_number.getText().toString())){




                    Phone_verify(textView_Country_code.getText().toString(),edtxt_phn_number.getText().toString());

                }

                else {

                    Toast.makeText(getActivity(),"Please Enter Phone Number", Toast.LENGTH_SHORT).show();

                }
            }

        });


        return view;
    }

    public void Phone_verify(String country_code, String phn) {



       // final String Phn_number= country_code + phn;

        final String Phn_number = phn;

        final ProgressDialog progressDialogs = new ProgressDialog(getActivity(),R.style.AlertDialogCustom);
        progressDialogs.setCancelable(false);
        progressDialogs.setMessage("Please Wait.......");
        progressDialogs.show();


        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.PHONE_VERIFY("adeladmin","Basic YWRtaW46MTIzNA==","application/x-www-form-urlencoded",Phn_number).enqueue(new Callback<ModelPhoneVerify>() {
            @Override
            public void onResponse(Call<ModelPhoneVerify> call, Response<ModelPhoneVerify> response) {
                if (response.body().getStatusCode().equals(200)){

                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), Otp_Screen.class);

                    Paper.book().write("otp",response.body().getOTP().toString());
                    Paper.book().write("phn",Phn_number);

                    startActivity(intent);

                }

                else {

                    Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_SHORT).show();

                }

                progressDialogs.dismiss();
            }

            @Override
            public void onFailure(Call<ModelPhoneVerify> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                progressDialogs.dismiss();

            }
        });
    }


    /**
     * This will be color of dialog background
     *
     * @param dialogBackgroundColor
     */
    public void setDialogBackgroundColor(int dialogBackgroundColor) {
        this.dialogBackgroundColor = dialogBackgroundColor;
    }

    public int getDialogBackgroundResId() {
        return dialogBackgroundResId;
    }

    /**
     * This will be color of dialog background
     *
     * @param dialogBackgroundResId
     */
    public void setDialogBackground(@IdRes int dialogBackgroundResId) {
        this.dialogBackgroundResId = dialogBackgroundResId;
    }

    public int getDialogSearchEditTextTintColor() {
        return dialogSearchEditTextTintColor;
    }

    /**
     * If device is running above or equal LOLLIPOP version, this will change tint of search edittext background.
     *
     * @param dialogSearchEditTextTintColor
     */
    public void setDialogSearchEditTextTintColor(int dialogSearchEditTextTintColor) {
        this.dialogSearchEditTextTintColor = dialogSearchEditTextTintColor;
    }

    public int getDialogTextColor() {
        return dialogTextColor;
    }

    /**
     * This color will be applied to
     * Title of dialog
     * Name of country
     * Phone code of country
     * "X" button to clear query
     * preferred country divider if preferred countries defined (semi transparent)
     *
     * @param dialogTextColor
     */
    public void setDialogTextColor(int dialogTextColor) {
        this.dialogTextColor = dialogTextColor;
    }

    public int getDialogTypeFaceStyle() {
        return dialogTypeFaceStyle;
    }

    /**
     * Publicly available functions from library
     */

    public Typeface getDialogTypeFace() {
        return dialogTypeFace;
    }

    /**
     * To change font of ccp views
     *
     * @param typeFace
     */
    public void setDialogTypeFace(Typeface typeFace) {
        try {
            dialogTypeFace = typeFace;
            dialogTypeFaceStyle = DEFAULT_UNSET;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setContentColor(int contentColor) {
        this.contentColor = contentColor;
        textView_selectedCountry.setTextColor(this.contentColor);

        //change arrow color only if explicit arrow color is not specified.
        if (this.arrowColor == DEFAULT_UNSET) {
            imageViewArrow.setColorFilter(this.contentColor, PorterDuff.Mode.SRC_IN);
        }
    }

    public void setArrowSize(int arrowSize) {
        if (arrowSize > 0) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageViewArrow.getLayoutParams();
            params.width = arrowSize;
            params.height = arrowSize;
            imageViewArrow.setLayoutParams(params);
        }
    }


    public void setArrowColor(int arrowColor) {
        this.arrowColor = arrowColor;
        if (this.arrowColor == DEFAULT_UNSET) {
            if (contentColor != DEFAULT_UNSET) {
                imageViewArrow.setColorFilter(this.contentColor, PorterDuff.Mode.SRC_IN);
            }
        } else {
            imageViewArrow.setColorFilter(this.arrowColor, PorterDuff.Mode.SRC_IN);
        }
    }


    private void refreshArrowViewVisibility() {
        if (showArrow) {
            imageViewArrow.setVisibility(VISIBLE);
        } else {
            imageViewArrow.setVisibility(GONE);
        }
    }

    boolean isCcpClickable() {
        return ccpClickable;
    }

    public String getSelectedCountryNameCode() {
        return getSelectedCountry().nameCode.toUpperCase();
    }
    private CCPCountry getDefaultCountry() {
        return defaultCCPCountry;
    }

    private void setDefaultCountry(CCPCountry defaultCCPCountry) {
        this.defaultCCPCountry = defaultCCPCountry;
        //        Log.d(TAG, "Setting default country:" + defaultCountry.logString());
    }


    private CCPCountry getSelectedCountry() {
        if (selectedCCPCountry == null) {
            setSelectedCountry(getDefaultCountry());
        }
        return selectedCCPCountry;
    }

    void setSelectedCountry(CCPCountry selectedCCPCountry) {

        //force disable area code country detection
        countryDetectionBasedOnAreaAllowed = false;
        lastCheckedAreaCode = "";

        //as soon as country is selected, textView should be updated
        if (selectedCCPCountry == null) {
            selectedCCPCountry = CCPCountry.getCountryForCode(getActivity(), getLanguageToApply(), preferredCountries, defaultCountryCode);
            if (selectedCCPCountry == null) {
                return;
            }
        }

        this.selectedCCPCountry = selectedCCPCountry;

        String displayText = "";

        // add flag if required
        if (showFlag && ccpUseEmoji) {
            if (isInEditMode()) {
//                android studio preview shows huge space if 0 width space is not added.
                if (ccpUseDummyEmojiForPreview) {
                    //show chequered flag if dummy preview is expected.
                    displayText += "\uD83C\uDFC1\u200B ";
                } else {
                    displayText += CCPCountry.getFlagEmoji(selectedCCPCountry) + "\u200B ";
                }

            } else {
                displayText += CCPCountry.getFlagEmoji(selectedCCPCountry) + "  ";
            }
        }

        // add full name to if required
        if (showFullName) {
            displayText = displayText + selectedCCPCountry.getName();
        }

        // adds name code if required
        if (showNameCode) {
            if (showFullName) {
                displayText += " (" + selectedCCPCountry.getNameCode().toUpperCase() + ")";
            } else {
                displayText += " " + selectedCCPCountry.getNameCode().toUpperCase();
            }
        }
        textView_selectedCountry.setText(displayText);

        // hide phone code if required
        if (showPhoneCode) {
            if (displayText.length() > 0) {
                displayText += "  ";
            }
            displayText += "+" + selectedCCPCountry.getPhoneCode();
        }

        country_code="+" + selectedCCPCountry.getPhoneCode();

        textView_Country_code.setText("+" + selectedCCPCountry.getPhoneCode());

        //avoid blank state of ccp
        if (showFlag == false && displayText.length() == 0) {
            // displayText += "+" + selectedCCPCountry.getPhoneCode();
            textView_selectedCountry.setText(displayText);
            textView_Country_code.setText(displayText);
        }


        Log.e("flagid",selectedCCPCountry.getFlagID()+"");
        imageViewFlag.setImageResource(selectedCCPCountry.getFlagID());

        //register_details.image_flag_register_details.setImageResource(selectedCCPCountry.getFlagID());


        Paper.book().write("image_flag",selectedCCPCountry.getFlagID());

        if (onCountryChangeListener != null) {
            onCountryChangeListener.onCountrySelected();
        }
        updateFormattingTextWatcher();

        updateHint();

        //notify to registered validity listener
        if (editText_registeredCarrierNumber != null && phoneNumberValidityChangeListener != null) {
            reportedValidity = isValidFullNumber();
            phoneNumberValidityChangeListener.onValidityChanged(reportedValidity);
        }

        //once updates are done, this will release lock
        countryDetectionBasedOnAreaAllowed = true;

        //if the country was auto detected based on area code, this will correct the cursor position.
        if (countryChangedDueToAreaCode) {
            try {
                editText_registeredCarrierNumber.setSelection(lastCursorPosition);
                countryChangedDueToAreaCode = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //update country group
        updateCountryGroup();
    }

    /**
     * update country group
     */
    private void updateCountryGroup() {
        currentCountryGroup = CCPCountryGroup.getCountryGroupForPhoneCode(getSelectedCountryCodeAsInt());
    }


    public int getSelectedCountryCodeAsInt() {
        int code = 0;
        try {
            code = Integer.parseInt(getSelectedCountryCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    public String getSelectedCountryCode() {
        return getSelectedCountry().phoneCode;
    }

    public boolean isValidFullNumber() {
        try {
            if (getEditText_registeredCarrierNumber() != null && getEditText_registeredCarrierNumber().getText().length() != 0) {
                Phonenumber.PhoneNumber phoneNumber = getPhoneUtil().parse("+" + selectedCCPCountry.getPhoneCode() + getEditText_registeredCarrierNumber().getText().toString(), selectedCCPCountry.getNameCode());
                return getPhoneUtil().isValidNumber(phoneNumber);
            } else if (getEditText_registeredCarrierNumber() == null) {
                Toast.makeText(context, "No editText for Carrier number found.", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return false;
            }
        } catch (NumberParseException e) {
            //            when number could not be parsed, its not valid
            return false;
        }
    }


    /**
     * updates hint
     */
    private void updateHint() {
        if (editText_registeredCarrierNumber != null && hintExampleNumberEnabled) {
            String formattedNumber = "";
            Phonenumber.PhoneNumber exampleNumber = getPhoneUtil().getExampleNumberForType(getSelectedCountryNameCode(), getSelectedHintNumberType());
            if (exampleNumber != null) {
                formattedNumber = exampleNumber.getNationalNumber() + "";
//                Log.d(TAG, "updateHint: " + formattedNumber);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    formattedNumber = PhoneNumberUtils.formatNumber(getSelectedCountryCodeWithPlus() + formattedNumber, getSelectedCountryNameCode());
                } else {
                    formattedNumber = PhoneNumberUtils.formatNumber(getSelectedCountryCodeWithPlus() + formattedNumber);
                }
                if (formattedNumber != null) {
                    formattedNumber = formattedNumber.substring(getSelectedCountryCodeWithPlus().length()).trim();
                }
//                Log.d(TAG, "updateHint: after format " + formattedNumber + " " + selectionMemoryTag);
            } else {
//                Log.w(TAG, "updateHint: No example number found for this country (" + getSelectedCountryNameCode() + ") or this type (" + hintExampleNumberType.name() + ").");
            }

            //fallback to original hint
            if (formattedNumber == null) {
                formattedNumber = originalHint;
            }

            editText_registeredCarrierNumber.setHint(formattedNumber);
        }
    }

    public String getSelectedCountryCodeWithPlus() {
        return "+" + getSelectedCountryCode();
    }


    private PhoneNumberUtil getPhoneUtil() {
        if (phoneUtil == null) {
            phoneUtil = PhoneNumberUtil.createInstance(context);
        }
        return phoneUtil;
    }

    private PhoneNumberUtil.PhoneNumberType getSelectedHintNumberType() {
        switch (hintExampleNumberType) {
            case MOBILE:
                return PhoneNumberUtil.PhoneNumberType.MOBILE;
            case FIXED_LINE:
                return PhoneNumberUtil.PhoneNumberType.FIXED_LINE;
            case FIXED_LINE_OR_MOBILE:
                return PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE;
            case TOLL_FREE:
                return PhoneNumberUtil.PhoneNumberType.TOLL_FREE;
            case PREMIUM_RATE:
                return PhoneNumberUtil.PhoneNumberType.PREMIUM_RATE;
            case SHARED_COST:
                return PhoneNumberUtil.PhoneNumberType.SHARED_COST;
            case VOIP:
                return PhoneNumberUtil.PhoneNumberType.VOIP;
            case PERSONAL_NUMBER:
                return PhoneNumberUtil.PhoneNumberType.PERSONAL_NUMBER;
            case PAGER:
                return PhoneNumberUtil.PhoneNumberType.PAGER;
            case UAN:
                return PhoneNumberUtil.PhoneNumberType.UAN;
            case VOICEMAIL:
                return PhoneNumberUtil.PhoneNumberType.VOICEMAIL;
            case UNKNOWN:

                return PhoneNumberUtil.PhoneNumberType.UNKNOWN;
            default:
                return PhoneNumberUtil.PhoneNumberType.MOBILE;
        }
    }

    private void updateFormattingTextWatcher() {
        if (editText_registeredCarrierNumber != null && selectedCCPCountry != null) {
            String enteredValue = getEditText_registeredCarrierNumber().getText().toString();
            String digitsValue = PhoneNumberUtil.normalizeDigitsOnly(enteredValue);

            if (formattingTextWatcher != null) {
                editText_registeredCarrierNumber.removeTextChangedListener(formattingTextWatcher);
            }

            if (areaCodeCountryDetectorTextWatcher != null) {
                editText_registeredCarrierNumber.removeTextChangedListener(areaCodeCountryDetectorTextWatcher);
            }

            if (numberAutoFormattingEnabled) {
                formattingTextWatcher = new InternationalPhoneTextWatcher(context, getSelectedCountryNameCode(), getSelectedCountryCodeAsInt(), internationalFormattingOnly);
                editText_registeredCarrierNumber.addTextChangedListener(formattingTextWatcher);
            }

            //if country detection from area code is enabled, then it will add areaCodeCountryDetectorTextWatcher
            if (detectCountryWithAreaCode) {
                areaCodeCountryDetectorTextWatcher = getCountryDetectorTextWatcher();
                editText_registeredCarrierNumber.addTextChangedListener(areaCodeCountryDetectorTextWatcher);
            }

            //text watcher stops working when it finds non digit character in previous phone code. This will reset its function
            editText_registeredCarrierNumber.setText("");
            editText_registeredCarrierNumber.setText(digitsValue);
            editText_registeredCarrierNumber.setSelection(editText_registeredCarrierNumber.getText().length());
        } else {
            if (editText_registeredCarrierNumber == null) {
                Log.v(TAG, "updateFormattingTextWatcher: EditText not registered " + selectionMemoryTag);
            } else {
                Log.v(TAG, "updateFormattingTextWatcher: selected country is null " + selectionMemoryTag);
            }
        }
    }

    EditText getEditText_registeredCarrierNumber() {
//        Log.d(TAG, "getEditText_registeredCarrierNumber");
        return editText_registeredCarrierNumber;
    }


    private TextWatcher getCountryDetectorTextWatcher() {

        if (editText_registeredCarrierNumber != null) {
            if (areaCodeCountryDetectorTextWatcher == null) {
                areaCodeCountryDetectorTextWatcher = new TextWatcher() {
                    String lastCheckedNumber = null;


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        CCPCountry selectedCountry = getSelectedCountry();
                        if (selectedCountry != null && (lastCheckedNumber == null || !lastCheckedNumber.equals(s.toString())) && countryDetectionBasedOnAreaAllowed) {
                            //possible countries
                            if (currentCountryGroup != null) {
                                String enteredValue = getEditText_registeredCarrierNumber().getText().toString();
                                if (enteredValue.length() >= currentCountryGroup.areaCodeLength) {
                                    String digitsValue = PhoneNumberUtil.normalizeDigitsOnly(enteredValue);
                                    if (digitsValue.length() >= currentCountryGroup.areaCodeLength) {
                                        String currentAreaCode = digitsValue.substring(0, currentCountryGroup.areaCodeLength);
                                        if (!currentAreaCode.equals(lastCheckedAreaCode)) {
                                            CCPCountry detectedCountry = currentCountryGroup.getCountryForAreaCode(context, getLanguageToApply(), currentAreaCode);
                                            if (!detectedCountry.equals(selectedCountry)) {
                                                countryChangedDueToAreaCode = true;
                                                lastCursorPosition = Selection.getSelectionEnd(s);
                                                setSelectedCountry(detectedCountry);
                                            }
                                            lastCheckedAreaCode = currentAreaCode;
                                        }
                                    }
                                }
                            }
                            lastCheckedNumber = s.toString();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {


                    }
                };
            }
        }
        return areaCodeCountryDetectorTextWatcher;
    }


    /**
     * Manually trigger selection dialog and set
     * scroll position to specified country.
     */

    public void launchCountrySelectionDialog(final String countryNameCode) {
        CountryCodeDialog.openCountryCodeDialog(codePicker, countryNameCode);
    }

    public void setFlagSize(int flagSize) {
        imageViewFlag.getLayoutParams().height = flagSize;
        imageViewFlag.requestLayout();
    }

    public void showFlag(boolean showFlag) {
        this.showFlag = showFlag;
        refreshFlagVisibility();
        if (!isInEditMode())
            setSelectedCountry(selectedCCPCountry);
    }

    private void refreshFlagVisibility() {
        if (showFlag) {
            if (ccpUseEmoji) {
                linearFlagHolder.setVisibility(GONE);
            } else {
                linearFlagHolder.setVisibility(VISIBLE);
            }
        } else {
            linearFlagHolder.setVisibility(GONE);
        }
    }


    private void updateLanguageToApply() {
        //when in edit mode, it will return default language only
        if (isInEditMode()) {
            if (customDefaultLanguage != null) {
                languageToApply = customDefaultLanguage;
            } else {
                languageToApply = Signup_Phone.Language.ENGLISH;
            }
        } else {
            if (isAutoDetectLanguageEnabled()) {
                Signup_Phone.Language localeBasedLanguage = getCCPLanguageFromLocale();
                if (localeBasedLanguage == null) { //if no language is found from locale
                    if (getCustomDefaultLanguage() != null) { //and custom language is defined
                        languageToApply = getCustomDefaultLanguage();
                    } else {
                        languageToApply = Signup_Phone.Language.ENGLISH;
                    }
                } else {
                    languageToApply = localeBasedLanguage;
                }
            } else {
                if (getCustomDefaultLanguage() != null) {
                    languageToApply = customDefaultLanguage;
                } else {
                    languageToApply = Signup_Phone.Language.ENGLISH;  //library default
                }
            }
        }
    }



    private Signup_Phone.Language getCCPLanguageFromLocale() {
        Locale currentLocale = context.getResources().getConfiguration().locale;
//        Log.d(TAG, "getCCPLanguageFromLocale: current locale language" + currentLocale.getLanguage());
        for (Signup_Phone.Language language : Signup_Phone.Language.values()) {
            if (language.getCode().equalsIgnoreCase(currentLocale.getLanguage())) {

                if (language.getCountry() == null
                        || language.getCountry().equalsIgnoreCase(currentLocale.getCountry()))
                    return language;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (language.getScript() == null
                            || language.getScript().equalsIgnoreCase(currentLocale.getScript()))
                        return language;

                }
            }
        }
        return null;
    }

    Signup_Phone.Language getCustomDefaultLanguage() {
        return customDefaultLanguage;
    }

    private void setCustomDefaultLanguage(Signup_Phone.Language customDefaultLanguage) {
        this.customDefaultLanguage = customDefaultLanguage;
        updateLanguageToApply();
        setSelectedCountry(CCPCountry.getCountryForNameCodeFromLibraryMasterList(context, getLanguageToApply(), selectedCCPCountry.getNameCode()));
    }

    public Signup_Phone.Language getLanguageToApply() {
        if (languageToApply == null) {
            updateLanguageToApply();
        }
        return languageToApply;
    }

    public void refreshCustomMasterList() {
        //if no custom list specified then check for exclude list
        if (customMasterCountriesParam == null || customMasterCountriesParam.length() == 0) {
            //if excluded param is also blank, then do nothing
            if (excludedCountriesParam != null && excludedCountriesParam.length() != 0) {
                excludedCountriesParam = excludedCountriesParam.toLowerCase();
                List<CCPCountry> libraryMasterList = CCPCountry.getLibraryMasterCountryList(context, getLanguageToApply());
                List<CCPCountry> localCCPCountryList = new ArrayList<>();
                for (CCPCountry ccpCountry : libraryMasterList) {
                    //if the country name code is in the excluded list, avoid it.
                    if (!excludedCountriesParam.contains(ccpCountry.getNameCode().toLowerCase())) {
                        localCCPCountryList.add(ccpCountry);
                    }
                }

                if (localCCPCountryList.size() > 0) {
                    customMasterCountriesList = localCCPCountryList;
                } else {
                    customMasterCountriesList = null;
                }

            } else {
                customMasterCountriesList = null;
            }
        } else {
            //else add custom list
            List<CCPCountry> localCCPCountryList = new ArrayList<>();
            for (String nameCode : customMasterCountriesParam.split(",")) {
                CCPCountry ccpCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(getActivity(), getLanguageToApply(), nameCode);
                if (ccpCountry != null) {
                    if (!isAlreadyInList(ccpCountry, localCCPCountryList)) { //to avoid duplicate entry of country
                        localCCPCountryList.add(ccpCountry);
                    }
                }
            }

            if (localCCPCountryList.size() == 0) {
                customMasterCountriesList = null;
            } else {
                customMasterCountriesList = localCCPCountryList;
            }
        }

        if (customMasterCountriesList != null) {
            //            Log.d("custom master list:", customMasterCountriesList.size() + " countries");
            for (CCPCountry ccpCountry : customMasterCountriesList) {
                ccpCountry.log();
            }
        } else {
            //            Log.d("custom master list", " has no country");
        }
    }

    public void setFullNumber(String fullNumber) {
        CCPCountry country = CCPCountry.getCountryForNumber(getActivity(), getLanguageToApply(), preferredCountries, fullNumber);
        if (country == null)
            country = getDefaultCountry();
        setSelectedCountry(country);
        String carrierNumber = detectCarrierNumber(fullNumber, country);
        if (getEditText_registeredCarrierNumber() != null) {
            getEditText_registeredCarrierNumber().setText(carrierNumber);
            updateFormattingTextWatcher();
        } else {
            Log.w(TAG, "EditText for carrier number is not registered. Register it using registerCarrierNumberEditText() before getFullNumber() or setFullNumber().");
        }
    }

    private String detectCarrierNumber(String fullNumber, CCPCountry CCPCountry) {
        String carrierNumber;
        if (CCPCountry == null || fullNumber == null || fullNumber.isEmpty()) {
            carrierNumber = fullNumber;
        } else {
            int indexOfCode = fullNumber.indexOf(CCPCountry.getPhoneCode());
            if (indexOfCode == -1) {
                carrierNumber = fullNumber;
            } else {
                carrierNumber = fullNumber.substring(indexOfCode + CCPCountry.getPhoneCode().length());
            }
        }
        return carrierNumber;
    }

    /**
     * Update every time new language is supported #languageSupport
     */
    //add an entry for your language in attrs.xml's <attr name="language" format="enum"> enum.
    //add here so that language can be set programmatically
    public enum Language {
        AFRIKAANS("af"),
        ARABIC("ar"),
        BENGALI("bn"),
        CHINESE_SIMPLIFIED("zh", "CN", "Hans"),
        CHINESE_TRADITIONAL("zh", "TW", "Hant"),
        CZECH("cs"),
        DANISH("da"),
        DUTCH("nl"),
        ENGLISH("en"),
        FARSI("fa"),
        FRENCH("fr"),
        GERMAN("de"),
        GREEK("el"),
        GUJARATI("gu"),
        HEBREW("iw"),
        HINDI("hi"),
        INDONESIA("in"),
        ITALIAN("it"),
        JAPANESE("ja"),
        KAZAKH("kk"),
        KOREAN("ko"),
        MARATHI("mr"),
        POLISH("pl"),
        PORTUGUESE("pt"),
        PUNJABI("pa"),
        RUSSIAN("ru"),
        SLOVAK("sk"),
        SLOVENIAN("si"),
        SPANISH("es"),
        SWEDISH("sv"),
        TURKISH("tr"),
        UKRAINIAN("uk"),
        URDU("ur"),
        UZBEK("uz"),
        VIETNAMESE("vi");

        private String code;
        private String country;
        private String script;

        Language(String code, String country, String script) {
            this.code = code;
            this.country = country;
            this.script = script;
        }

        Language(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getScript() {
            return script;
        }

        public void setScript(String script) {
            this.script = script;
        }
    }

    public enum AutoDetectionPref {
        SIM_ONLY("1"),
        NETWORK_ONLY("2"),
        LOCALE_ONLY("3"),
        SIM_NETWORK("12"),
        NETWORK_SIM("21"),
        SIM_LOCALE("13"),
        LOCALE_SIM("31"),
        NETWORK_LOCALE("23"),
        LOCALE_NETWORK("32"),
        SIM_NETWORK_LOCALE("123"),
        SIM_LOCALE_NETWORK("132"),
        NETWORK_SIM_LOCALE("213"),
        NETWORK_LOCALE_SIM("231"),
        LOCALE_SIM_NETWORK("312"),
        LOCALE_NETWORK_SIM("321");

        String representation;

        AutoDetectionPref(String representation) {
            this.representation = representation;
        }

        public static Signup_Phone.AutoDetectionPref getPrefForValue(String value) {
            for (Signup_Phone.AutoDetectionPref autoDetectionPref : Signup_Phone.AutoDetectionPref.values()) {
                if (autoDetectionPref.representation.equals(value)) {
                    return autoDetectionPref;
                }
            }
            return SIM_NETWORK_LOCALE;
        }
    }


    public void setAutoDetectedCountry(boolean loadDefaultWhenFails) {

        String representation;
        try {
            boolean successfullyDetected = false;
            for (int i = 0; i < selectedAutoDetectionPref.representation.length(); i++) {
                switch (selectedAutoDetectionPref.representation.charAt(i)) {
                    case '1':
                        successfullyDetected = detectSIMCountry(false);
                        break;
                    case '2':
                        successfullyDetected = detectNetworkCountry(false);
                        break;
                    case '3':
                        successfullyDetected = detectLocaleCountry(false);
                        break;
                }
                if (successfullyDetected) {
                    break;
                } else {
                    if (failureListener != null) {
                        failureListener.onCountryAutoDetectionFailed();
                    }
                }
            }

            if (!successfullyDetected && loadDefaultWhenFails) {
                resetToDefaultCountry();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "setAutoDetectCountry: Exception" + e.getMessage());
            if (loadDefaultWhenFails) {
                resetToDefaultCountry();
            }
        }
    }

    /**
     * This will detect country from SIM info and then load it into CCP.
     *
     * @param loadDefaultWhenFails true if want to reset to default country when sim country cannot be detected. if false, then it
     *                             will not change currently selected country
     * @return true if it successfully sets country, false otherwise
     */
    public boolean detectSIMCountry(boolean loadDefaultWhenFails) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String simCountryISO = telephonyManager.getSimCountryIso();
            if (simCountryISO == null || simCountryISO.isEmpty()) {
                if (loadDefaultWhenFails) {
                    resetToDefaultCountry();
                }
                return false;
            }
            setSelectedCountry(CCPCountry.getCountryForNameCodeFromLibraryMasterList(getActivity(), getLanguageToApply(), simCountryISO));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (loadDefaultWhenFails) {
                resetToDefaultCountry();
            }
            return false;
        }
    }

    private void loadLastSelectedCountryInCCP() {
        //get the shared pref
        SharedPreferences sharedPref = context.getSharedPreferences(
                CCP_PREF_FILE, Context.MODE_PRIVATE);

        // read last selection value
        String lastSelectedCountryNameCode = sharedPref.getString(selectionMemoryTag, null);

        //if last selection value is not null, load it into the CCP
        if (lastSelectedCountryNameCode != null) {
            setCountryForNameCode(lastSelectedCountryNameCode);
        }
    }

    public void setCountryForNameCode(String countryNameCode) {
        CCPCountry country = CCPCountry.getCountryForNameCodeFromLibraryMasterList(getActivity(), getLanguageToApply(), countryNameCode); //xml stores data in string format, but want to allow only numeric value to country code to user.
        if (country == null) {
            if (defaultCCPCountry == null) {
                defaultCCPCountry = country.getCountryForCode(getActivity(), getLanguageToApply(), preferredCountries, defaultCountryCode);
            }
            setSelectedCountry(defaultCCPCountry);
        } else {
            setSelectedCountry(country);
        }
    }


    public boolean detectNetworkCountry(boolean loadDefaultWhenFails) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String networkCountryISO = telephonyManager.getNetworkCountryIso();
            if (networkCountryISO == null || networkCountryISO.isEmpty()) {
                if (loadDefaultWhenFails) {
                    resetToDefaultCountry();
                }
                return false;
            }
            setSelectedCountry(CCPCountry.getCountryForNameCodeFromLibraryMasterList(getActivity(), getLanguageToApply(), networkCountryISO));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (loadDefaultWhenFails) {
                resetToDefaultCountry();
            }
            return false;
        }
    }

    public void launchCountrySelectionDialog() {
        launchCountrySelectionDialog(null);
    }

    /**
     * This will detect country from LOCALE info and then load it into CCP.
     *
     * @param loadDefaultWhenFails true if want to reset to default country when locale country cannot be detected. if false, then it
     *                             will not change currently selected country
     * @return true if it successfully sets country, false otherwise
     */
    public boolean detectLocaleCountry(boolean loadDefaultWhenFails) {
        try {
            String localeCountryISO = context.getResources().getConfiguration().locale.getCountry();
            if (localeCountryISO == null || localeCountryISO.isEmpty()) {
                if (loadDefaultWhenFails) {
                    resetToDefaultCountry();
                }
                return false;
            }
            setSelectedCountry(CCPCountry.getCountryForNameCodeFromLibraryMasterList(getActivity(), getLanguageToApply(), localeCountryISO));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (loadDefaultWhenFails) {
                resetToDefaultCountry();
            }
            return false;
        }
    }

    public void resetToDefaultCountry() {
        defaultCCPCountry = CCPCountry.getCountryForNameCodeFromLibraryMasterList(getActivity(), getLanguageToApply(), getDefaultCountryNameCode());
        setSelectedCountry(defaultCCPCountry);
    }

    public String getDefaultCountryNameCode() {
        return getDefaultCountry().nameCode.toUpperCase();
    }

    public List<CCPCountry> getCustomMasterCountriesList() {
        return customMasterCountriesList;
    }

    private boolean isAlreadyInList(CCPCountry CCPCountry, List<CCPCountry> CCPCountryList) {
        if (CCPCountry != null && CCPCountryList != null) {
            for (CCPCountry iterationCCPCountry : CCPCountryList) {
                if (iterationCCPCountry.getNameCode().equalsIgnoreCase(CCPCountry.getNameCode())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void refreshPreferredCountries() {
        if (countryPreference == null || countryPreference.length() == 0) {
            preferredCountries = null;
        } else {
            List<CCPCountry> localCCPCountryList = new ArrayList<>();
            for (String nameCode : countryPreference.split(",")) {
                CCPCountry ccpCountry = CCPCountry.getCountryForNameCodeFromCustomMasterList(getActivity(), customMasterCountriesList, getLanguageToApply(), nameCode);
                if (ccpCountry != null) {
                    if (!isAlreadyInList(ccpCountry, localCCPCountryList)) { //to avoid duplicate entry of country
                        localCCPCountryList.add(ccpCountry);
                    }
                }
            }

            if (localCCPCountryList.size() == 0) {
                preferredCountries = null;
            } else {
                preferredCountries = localCCPCountryList;
            }
        }
        if (preferredCountries != null) {
            //            Log.d("preference list", preferredCountries.size() + " countries");
            for (CCPCountry CCPCountry : preferredCountries) {
                CCPCountry.log();
            }
        } else {
            //            Log.d("preference list", " has no country");
        }
    }

    public boolean isDialogKeyboardAutoPopup() {
        return dialogKeyboardAutoPopup;
    }

    public boolean isSearchAllowed() {
        return searchAllowed;
    }

    public int getDialogBackgroundColor() {
        return dialogBackgroundColor;
    }

    public boolean isShowCloseIcon() {
        return showCloseIcon;
    }

    public boolean getCcpDialogShowTitle() {
        return ccpDialogShowTitle;
    }


    public boolean isShowFastScroller() {
        return showFastScroller;
    }

    public int getFastScrollerBubbleColor() {
        return fastScrollerBubbleColor;
    }

    public int getFastScrollerHandleColor() {
        return fastScrollerHandleColor;
    }

    public int getFastScrollerBubbleTextAppearance() {
        return fastScrollerBubbleTextAppearance;
    }

    public enum TextGravity {
        LEFT(-1), CENTER(0), RIGHT(1);

        int enumIndex;

        TextGravity(int i) {
            enumIndex = i;
        }
    }


    public void onUserTappedCountry(CCPCountry CCPCountry) {
        if (codePicker.rememberLastSelection) {
            codePicker.storeSelectedCountryNameCode(CCPCountry.getNameCode());
        }
        setSelectedCountry(CCPCountry);
    }


    //add entry here
    private Signup_Phone.Language getLanguageEnum(int index) {
        if (index < Signup_Phone.Language.values().length) {
            return Signup_Phone.Language.values()[index];
        } else {
            return Signup_Phone.Language.ENGLISH;
        }
    }

    /**
     * @return If custom text provider is registered, it will return value from provider else default.
     */
    public String getDialogTitle() {
        String defaultTitle = CCPCountry.getDialogTitle(context, getLanguageToApply());
        if (customDialogTextProvider != null) {
            return customDialogTextProvider.getCCPDialogTitle(getLanguageToApply(), defaultTitle);
        } else {
            return defaultTitle;
        }
    }

    /**
     * @return If custom text provider is registered, it will return value from provider else default.
     */
    public String getSearchHintText() {
        String defaultHint = CCPCountry.getSearchHintMessage(context, getLanguageToApply());
        if (customDialogTextProvider != null) {
            return customDialogTextProvider.getCCPDialogSearchHintText(getLanguageToApply(), defaultHint);
        } else {
            return defaultHint;
        }
    }


    public Signup_Phone.DialogEventsListener getDialogEventsListener() {
        return dialogEventsListener;
    }

    /**
     * Dialog events listener will give call backs on various dialog events
     *
     * @param dialogEventsListener
     */
    public void setDialogEventsListener(Signup_Phone.DialogEventsListener dialogEventsListener) {
        this.dialogEventsListener = dialogEventsListener;
    }




    /**
     * @return If custom text provider is registered, it will return value from provider else default.
     */
    public String getNoResultACK() {
        String defaultNoResultACK = CCPCountry.getNoResultFoundAckMessage(context, getLanguageToApply());
        if (customDialogTextProvider != null) {
            return customDialogTextProvider.getCCPDialogNoResultACK(getLanguageToApply(), defaultNoResultACK);
        } else {
            return defaultNoResultACK;
        }
    }

    void storeSelectedCountryNameCode(String selectedCountryNameCode) {
        //get the shared pref
        SharedPreferences sharedPref = context.getSharedPreferences(
                CCP_PREF_FILE, Context.MODE_PRIVATE);

        //we want to write in shared pref, so lets get editor for it
        SharedPreferences.Editor editor = sharedPref.edit();

        // add our last selection country name code in pref
        editor.putString(selectionMemoryTag, selectedCountryNameCode);

        //finally save it...
        editor.apply();
    }

    public void setCurrentTextGravity(Signup_Phone.TextGravity textGravity) {
        //this.currentTextGravity = textGravity;
        applyTextGravity(textGravity.enumIndex);
    }

    private void applyTextGravity(int enumIndex) {
        if (enumIndex == Signup_Phone.TextGravity.LEFT.enumIndex) {
            textView_selectedCountry.setGravity(Gravity.LEFT);
        } else if (enumIndex == Signup_Phone.TextGravity.CENTER.enumIndex) {
            textView_selectedCountry.setGravity(Gravity.CENTER);
        } else {
            textView_selectedCountry.setGravity(Gravity.RIGHT);
        }
    }



    public boolean isInEditMode() {
        return false;
    }
    public void setDialogKeyboardAutoPopup(boolean dialogKeyboardAutoPopup) {
        this.dialogKeyboardAutoPopup = dialogKeyboardAutoPopup;
    }

    boolean isAutoDetectLanguageEnabled() {
        return autoDetectLanguageEnabled;
    }

    boolean isAutoDetectCountryEnabled() {
        return autoDetectCountryEnabled;
    }



    @Deprecated
    public void setDefaultCountryUsingPhoneCode(int defaultCountryCode) {
        CCPCountry defaultCCPCountry = CCPCountry.getCountryForCode(getActivity(), getLanguageToApply(), preferredCountries, defaultCountryCode); //xml stores data in string format, but want to allow only numeric value to country code to user.
        if (defaultCCPCountry == null) { //if no correct country is found
            //            Log.d(TAG, "No country for code " + defaultCountryCode + " is found");
        } else { //if correct country is found, set the country
            this.defaultCountryCode = defaultCountryCode;
            setDefaultCountry(defaultCCPCountry);
        }
    }
    public interface OnCountryChangeListener {
        void onCountrySelected();
    }

    /**
     * interface to listen to failure events
     */
    public interface FailureListener {
        //when country auto detection failed.
        void onCountryAutoDetectionFailed();
    }

    /**
     * Interface to check phone number validity change listener
     */
    public interface PhoneNumberValidityChangeListener {
        void onValidityChanged(boolean isValidNumber);
    }

    public interface DialogEventsListener {
        void onCcpDialogOpen(Dialog dialog);

        void onCcpDialogDismiss(DialogInterface dialogInterface);

        void onCcpDialogCancel(DialogInterface dialogInterface);
    }

    public interface CustomDialogTextProvider {
        String getCCPDialogTitle(Signup_Phone.Language language, String defaultTitle);

        String getCCPDialogSearchHintText(Signup_Phone.Language language, String defaultSearchHintText);

        String getCCPDialogNoResultACK(Signup_Phone.Language language, String defaultNoResultACK);
    }

    public boolean isCcpDialogShowPhoneCode() {
        return ccpDialogShowPhoneCode;
    }

    public boolean getCcpDialogShowFlag() {
        return this.ccpDialogShowFlag;
    }

    public boolean getCcpDialogShowNameCode() {
        return this.ccpDialogShowNameCode;
    }

}


