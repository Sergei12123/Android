package ru.mirea.ivanov.mireaproject.ui.weather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ru.mirea.ivanov.mireaproject.MainActivity;
import ru.mirea.ivanov.mireaproject.R;
import ru.mirea.ivanov.mireaproject.databinding.FragmentWeatherBinding;

public class WeatherFragment extends Fragment {

    public StringBuilder url = new StringBuilder().append("https://api.openweathermap.org/data/2.5/weather?");

    private String apiKey = "2fe9682522c1f8218a6e8c89844713f8";

    private FragmentWeatherBinding binding;

    private TextView textViewWeather;
    private EditText editLatitude, editLongitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        root.findViewById(R.id.buttonGetWeather).setOnClickListener(this::onClick);
        textViewWeather = root.findViewById(R.id.textViewWeather);
        editLatitude = root.findViewById(R.id.editLatitude);
        editLongitude = root.findViewById(R.id.editLongitude);
        // Inflate the layout for this fragment
        return root;
    }

    public void onClick(View view) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = null;
        if (connectivityManager != null) {
            networkinfo = connectivityManager.getActiveNetworkInfo();
        }
        if (editLongitude.getText().toString().equals("") || editLatitude.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Поля широта и долгота должны быть заполнены!", Toast.LENGTH_SHORT).show();
        } else {
            url.append("lat=").append(editLatitude.getText().toString()).append("&")
                    .append("lon=").append(editLongitude.getText().toString())
                    .append("&appid=").append(apiKey)
                    .append("&lang=ru")
                    .append("&units=metric");
            if (networkinfo != null && networkinfo.isConnected()) {
                new DownloadPageTask().execute(url.toString()); // запускаем в новом потоке
            } else {
                Toast.makeText(getContext(), "Нет интернета", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private class DownloadPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textViewWeather.setText("Загружаем...");
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadIpInfo(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(WeatherFragment.class.getSimpleName(), result);
            try {
                JSONObject responseJson = new JSONObject(result);
                String weather = responseJson.getJSONArray("weather").getJSONObject(0).getString("description");
                String degrees = responseJson.getJSONObject("main").getString("temp");
                String howItFeels = responseJson.getJSONObject("main").getString("feels_like");

                String sb = "Погода : " + weather + "\n" +
                        "Температура : " + degrees + "\n" +
                        "Как ощущается : " + howItFeels + "\n";
                textViewWeather.setText(sb);
                Log.d(MainActivity.class.getSimpleName(), weather + degrees + howItFeels);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }

    private String downloadIpInfo(String address) throws IOException {
        InputStream inputStream = null;
        String data = "";
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setReadTimeout(100000);
            connection.setConnectTimeout(100000);
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                inputStream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read = 0;
                while ((read = inputStream.read()) != -1) {
                    bos.write(read);
                }
                byte[] result = bos.toByteArray();
                bos.close();
                data = new String(result);
            } else {
                data = connection.getResponseMessage() + " . Error Code : " + responseCode;
            }
            connection.disconnect();
            //return data;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return data;
    }
}