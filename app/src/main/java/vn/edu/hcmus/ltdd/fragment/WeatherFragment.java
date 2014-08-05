package vn.edu.hcmus.ltdd.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import vn.edu.hcmus.ltdd.app.R;
import vn.edu.hcmus.ltdd.model.Final;
import vn.edu.hcmus.ltdd.net.RestError;
import vn.edu.hcmus.ltdd.net.RestInterface;

/**
 * Created by mac on 8/5/14.
 */
@EFragment(R.layout.weather_fragment)
public class WeatherFragment extends Fragment {
    private Context context;
    private int count = 0;
    private ProgressDialog pd = null;
    private Final response;
    @RestService
    public RestInterface restClient;
    @Bean
    public RestError errorHandler;
    @ViewById
    TextView lblTempToday;
    @ViewById
    TextView lblHumidityToday;
    @ViewById
    TextView lblWindspeedToday;
    @ViewById
    TextView lblVisibilityToday;
    @ViewById
    TextView lblDate1;
    @ViewById
    TextView lblDate2;
    @ViewById
    TextView lblDate3;
    @ViewById
    TextView lblHigh1;
    @ViewById
    TextView lblHigh2;
    @ViewById
    TextView lblHigh3;
    @ViewById
    TextView lblLow1;
    @ViewById
    TextView lblLow2;
    @ViewById
    TextView lblLow3;
    @ViewById
    TextView lblDetailToday;
    @ViewById
    ImageView imgToday;
    @ViewById
    ImageView img1;
    @ViewById
    ImageView img2;
    @ViewById
    ImageView img3;

    public WeatherFragment() { }

    public static WeatherFragment newInstance(Context context) {
        WeatherFragment fm = new WeatherFragment_();
        fm.context = context;
        return fm;
    }

    @AfterViews
    protected void initFragment() {
        getData();
    }

    @Background
    protected void getData() {
        showProgressDialog();
        response = restClient.getWeather();
        dismissProgressDialog();
        if (response != null) {
            setData();
        }
    }

    @UiThread
    protected void setData() {
        if (!response.getData().getCurrent_condition().get(0).getWeatherIconUrl().get(0).getValue().equals(""))
            UrlImageViewHelper.setUrlDrawable(imgToday, response.getData().getCurrent_condition().get(0).getWeatherIconUrl().get(0).getValue());
        if (!response.getData().getWeather().get(0).getWeatherIconUrl().get(0).getValue().equals(""))
            UrlImageViewHelper.setUrlDrawable(img1, response.getData().getWeather().get(0).getWeatherIconUrl().get(0).getValue());
        if (!response.getData().getWeather().get(1).getWeatherIconUrl().get(0).getValue().equals(""))
            UrlImageViewHelper.setUrlDrawable(img2, response.getData().getWeather().get(0).getWeatherIconUrl().get(0).getValue());
        if (!response.getData().getWeather().get(2).getWeatherIconUrl().get(0).getValue().equals(""))
            UrlImageViewHelper.setUrlDrawable(img3, response.getData().getWeather().get(0).getWeatherIconUrl().get(0).getValue());

        lblDate1.setText(response.getData().getWeather().get(0).getDate());
        lblDate2.setText(response.getData().getWeather().get(1).getDate());
        lblDate3.setText(response.getData().getWeather().get(2).getDate());

        lblHigh1.setText(response.getData().getWeather().get(0).getTempMaxC() + " C");
        lblHigh2.setText(response.getData().getWeather().get(1).getTempMaxC() + " C");
        lblHigh3.setText(response.getData().getWeather().get(2).getTempMaxC() + " C");

        lblLow1.setText(response.getData().getWeather().get(0).getTempMinC() + " C");
        lblLow2.setText(response.getData().getWeather().get(1).getTempMinC() + " C");
        lblLow3.setText(response.getData().getWeather().get(2).getTempMinC() + " C");

        lblTempToday.setText(response.getData().getCurrent_condition().get(0).getTemp_C() + " C");
        lblHumidityToday.setText(response.getData().getCurrent_condition().get(0).getHumidity() + " %");
        lblWindspeedToday.setText(response.getData().getCurrent_condition().get(0).getWindspeedKmph() + " Kmph");
        lblVisibilityToday.setText(response.getData().getCurrent_condition().get(0).getVisibility());
        lblDetailToday.setText(response.getData().getCurrent_condition().get(0).getWeatherDesc().get(0).getValue());
    }

    @AfterInject
    protected void afterInject() {
        restClient.setRestErrorHandler(errorHandler);
    }

    @UiThread
    protected void showProgressDialog() {
        if (count == 0) {
            pd = new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
            pd.setMessage(getString(R.string.loading));
            pd.show();
            count++;
        }
    }

    @UiThread
    protected void dismissProgressDialog() {
        if (pd != null && pd.isShowing())
            pd.dismiss();
    }
}
