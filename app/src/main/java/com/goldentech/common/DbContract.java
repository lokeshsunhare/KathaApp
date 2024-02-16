package com.goldentech.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextPaint;
import android.util.Base64;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.goldentech.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.regex.Pattern;

public class DbContract {

    public static final String CART_TABLE = "cart_table";


    public static int HOME = 3;
    public static int LANGUAGE = 4;

    public static int FINANCE_INBOX = 30;

    public static int WEBSITE = 5;
    public static int TELL_FRIEND = 6;
    public static int LIKE_APP = 10;
    public static int MORE_APP = 11;
    public static int THEME = 15;

    public static String SEASON = "16";
    public static String ALL_PLANT = "17";
    public static String PLANT_COLOR = "18";
    public static int LOGOUT = 19;
    public static int FEEDBACK = 20;

    public static int SET_APP_LOCK = 21;
    public static int TEAM_MEMBER = 22;
    public static int OLD_QUESTION_PAPER = 23;

    public static final String SMS_ORIGIN = "NICSMS";
    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";

    public static String UNIQUE_ID = "200";
    public static String STUDENT_COMPLAIN = "31";
    public static String EMPLOYEE_COMPLAIN = "32";
    public static String OTHER_COMPLAIN = "33";

    public static String TOTAL = "40";
    public static String RESOLVED = "41";
    public static String FORWARDED = "42";
    public static String PENDING = "43";

    public static String NEWS_NOTIFICATION = "50";
    public static String BIRTH_LIST = "51";


    //documents
    public static int REGISTRATION_CARD = 1;
    public static int FEE_RECEIPT = 2;
    public static int ADMIT_CARD = 3;
    public static int SRC = 4;
    public static int SYLLABUS = 5;


    //YouTube Adapter
    public static int YOUTUBE_LIST = 1;

    //this for from activity

    public static final int ASSIGNMENT_ACTIVITY = 1;


    public static final String IS_WELCOME_ACTIVITY_SHOWN = "is_Welcome_activity_shown";
    public static final String SHOW_WELCOME_ACT = "show_welcome_activity";


    public static boolean isValidLandlineNumber(CharSequence target) {
        return !(target == null || target.length() < 6 || target.length() > 13) && android.util.Patterns.PHONE.matcher(target).matches();
    }

    public static boolean isValidPhoneNumber(String mobile) {
        String regEx = "^(?:(?:\\+|0{0,2})91(\\s*[\\ -]\\s*)?|[0]?)?[6789]\\d{9}|(\\d[ -]?){10}\\d$";
        return mobile.matches(regEx);
    }

    public static boolean isValidGSTNumber(String gst_no) {
        String regEx = "^([0][1-9]|[1-2][0-9]|[3][0-7])([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+$";
        return gst_no.matches(regEx);
    }

    public static boolean isInteger(String str) {
        return (str.lastIndexOf("-") == 0 && !str.equals("-0")) ? str.replace("-", "").matches(
                "[0-9]+") : str.matches("[0-9]+");
    }

    public static boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static boolean isValidAddress(String address) {
        String regEx = "^[#.0-9a-zA-Z\\s,-]+$";
        return address.matches(regEx);
    }

    public static boolean validateName(String name) {
        String regx = "[a-zA-Z ]+";
//        String regx = "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$";
        return name.matches(regx);
    }

    public enum SharedPreferenceKeys {
        USER_NAME("userName"),
        USER_EMAIL("userEmail"),
        USER_IMAGE_URL("userImageUrl"),
        USER_GENDER("gender");

        private String value;

        SharedPreferenceKeys(String value) {
            this.value = value;
        }

        public String getKey() {
            return value;
        }
    }

    public static final String GOOGLE_CLIENT_ID = "307391029465-607c9mqr415k3i2i8fib8qu7b2gj5vn3.apps.googleusercontent.com";


    public static Bitmap stringToImage(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

//    public static String getIPAddress(boolean useIPv4) {
//        try {
//            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
//            for (NetworkInterface intf : interfaces) {
//                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
//                for (InetAddress addr : addrs) {
//                    if (!addr.isLoopbackAddress()) {
//                        String sAddr = addr.getHostAddress();
//
//                        boolean isIPv4 = sAddr.indexOf(':') < 0;
//
//                        if (useIPv4) {
//                            if (isIPv4)
//                                return sAddr;
//                        } else {
//                            if (!isIPv4) {
//                                int delim = sAddr.indexOf('%');
//                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception ignored) {
//        }
//        return "";
//    }


    public static String getPublicIPAddress(Context context) {
        //final NetworkInfo info = NetworkUtils.getNetworkInfo(context);

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo info = cm.getActiveNetworkInfo();

        RunnableFuture<String> futureRun = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                if ((info != null && info.isAvailable()) && (info.isConnected())) {
                    StringBuilder response = new StringBuilder();

                    try {
                        HttpURLConnection urlConnection = (HttpURLConnection) (
                                new URL("http://api.ipify.org/").openConnection());
                        urlConnection.setRequestProperty("User-Agent", "Android-device");
                        //urlConnection.setRequestProperty("Connection", "close");
                        urlConnection.setReadTimeout(15000);
                        urlConnection.setConnectTimeout(15000);
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setRequestProperty("Content-type", "application/json");
                        urlConnection.connect();

                        int responseCode = urlConnection.getResponseCode();

                        if (responseCode == HttpURLConnection.HTTP_OK) {

                            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }

                        }
                        urlConnection.disconnect();
                        return response.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //Log.w(TAG, "No network available INTERNET OFF!");
                    return null;
                }
                return null;
            }
        });

        new Thread(futureRun).start();

        try {
            return futureRun.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent();
        int width = (int) (paint.measureText(text) + 0.0f);
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

//    public static Shader getGradientColor(Context context, TextView textView) {
//
//        TextPaint paint = textView.getPaint();
//        float width = paint.measureText(textView.getText().toString().trim());
//        return new LinearGradient(0, 0, width, textView.getTextSize(),
//                ContextCompat.getColor(context,
//                        R.color.start_color),
//                ContextCompat.getColor(context,
//                        R.color.end_color)
//                , Shader.TileMode.CLAMP);
//
//    }


}
