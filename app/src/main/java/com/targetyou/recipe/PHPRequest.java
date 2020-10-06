package com.targetyou.recipe;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PHPRequest {
    private URL url;

    public PHPRequest(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    private String readStream(InputStream in) throws IOException {
        StringBuilder jsonHtml = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = null;

        while ((line = reader.readLine()) != null)
            jsonHtml.append(line);

        reader.close();
        return jsonHtml.toString();
    }

    public String PhPDetail(final String sex, final String age, final String high,
                            final String weight, final String energy, final String activity,
                            final String usercarbo, final String userfat, final String usernatrium, final String calory,
                            final String disease, final String tv, final String email) {
        try {
            String postData = "sex=" + sex + "&" +
                    "age=" + age + "&" +
                    "high=" + high + "&" +
                    "weight=" + weight + "&" +
                    "tv=" + tv + "&" +
                    "energy=" + energy + "&" +
                    "activity=" + activity + "&" +
                    "usercarbo=" + usercarbo + "&" +
                    "userfat=" + userfat + "&" +
                    "usernatrium=" + usernatrium + "&" +
                    "calory=" + calory + "&" +
                    "disease=" + disease + "&" +
                    "email=" + email;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    public String PhPDelete(final String email, final String material) {
        try {
            String postData = "email=" + email + "&" +
                    "material=" + material;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    public String PhPRef(final String email, final String material, final String amount, final String day) {
        try {
            String postData = "email=" + email + "&" + "material=" + material + "&" + "amount=" + amount + "&" + "day=" + day;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    public String PhPunit(final String material) {
        try {
            String postData = "material=" + material;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }


    public String PhPChoice(final String items) {
        try {
            String postData = "items=" + items;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    //sst post
    public String PhPDietSST(final String email, final String food_name, final String amount, final String date, final String time) {
        try {
            String postData = "email=" + email + "&" + "food_name=" + food_name + "&" + "amount=" + amount + "&" + "date=" + date + "&" + "time=" + time;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    public String PhPDietMainDelete(final String email, final String food_name, final String date, final String time) {
        try {
            String postData = "email=" + email + "&" + "food_name=" + food_name + "&" + "date=" + date + "&" + "time=" + time;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    public String PhPDietMain(final String email, final String date) {
        try {
            String postData = "email=" + email + "&" + "date=" + date;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    //처음에 Search화면을 띄워줄 때
    public String PhPDietSearch(final String email) {
        try {
            String postData = "email=" + email;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    //serach에 검색어 입력 후, 일치하는 화면을 띄워줄 때
    public String PhPDietSearchSelect(final String email, final String material) {
        try {
            String postData = "email=" + email + "&" + "material=" + material;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    public String PhPDietSearchAdd(final String email, final String food_name, final String amount, final String date, final String time) {
        try {
            String postData = "email=" + email + "&" + "food_name=" + food_name + "&" + "amount=" + amount + "&" + "date=" + date + "&" + "time=" + time;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    //refrigerator에 있는 material의 unit 찾기
    public String PhPDietSearchUnit(final String material) {
        try {
            String postData = "material=" + material;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    public String PhPDietFood(final String food) {
        try {
            String postData = "food=" + food;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }
    public String PhPDietFavorite(final String email) {
        try {
            String postData = "email=" + email;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }
    public String PhPDietFoodReco(final String food) {
        try {
            String postData = "food=" + food;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    public String PhPDietFoodADD(final String email, final String food_name, final String amount, final String date, final String time) {
        try {
            String postData = "email=" + email + "&" + "food_name=" + food_name + "&" + "amount=" + amount + "&" + "date=" + date + "&" + "time=" + time;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }


    public String PhPChoice0(final String price, final String email) {
        try {
            String postData = "price=" + price + "&" + "email=" + email;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    public String PhPChoice1(final String user1, final String email) {
        try {
            String postData = "user1=" + user1 + "&" + "email=" + email;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }


    public String phpCalendar(final String email, final String date) {
        try {
            String postData = "email=" + email + "&" + "date=" + date;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    public String phpCalendar2(final String email) {
        try {
            String postData = "email=" + email;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }

    public String phpCalendar3(final String email, final String date, final String date1, final String date2, final String date3,
                               final String date4, final String date5, final String date6, final String date7) {
        try {
            String postData = "email=" + email + "&" + "date=" + date + "&" + "date1=" + date1 + "&" + "date2=" + date2 + "&" + "date3=" + date3
                    + "&" + "date4=" + date4 + "&" + "date5=" + date5 + "&" + "date6=" + date6 + "&" + "date7=" + date7;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }


    public String PhPemail(final String email) {
        try {
            String postData = "email=" + email;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }

    }

    public String PhPInfo(final String email) {
        try {
            String postData = "email=" + email;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }
    public String PhPprice(final String food_name) {
        try {
            String postData = "food_name=" + food_name;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }
    public String PhPdel(final String email, final String material, final String amount) {
        try {
            String postData = "email=" + email +"&" + "material=" + material +"&" + "amount=" + amount;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }
    public String PhPdisease(final String disease) {
        try {
            String postData = "disease=" + disease;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }
    public String PhPadd(final String email, final String food_name, final String amount) {
        try {
            String postData = "email=" + email + "&" + "food_name=" + food_name +"&" + "amount=" + amount;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            String result = readStream(conn.getInputStream());
            conn.disconnect();
            return result;
        } catch (Exception e) {
            Log.i("PHPRequest", "request was failed.");
            return null;
        }
    }
}