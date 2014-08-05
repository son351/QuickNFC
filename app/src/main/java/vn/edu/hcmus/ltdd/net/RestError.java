package vn.edu.hcmus.ltdd.net;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.web.client.RestClientException;

import vn.edu.hcmus.ltdd.app.R;

/**
 * Created by mac on 8/5/14.
 */
@EBean
public class RestError implements RestErrorHandler {
    @RootContext
    Context context;

    @Override
    public void onRestClientExceptionThrown(RestClientException e) {
        showDialog();
        Log.e("SERVER ERROR: ", e.getMessage());
    }

    @UiThread
    protected void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.network_error));
        builder.setPositiveButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
