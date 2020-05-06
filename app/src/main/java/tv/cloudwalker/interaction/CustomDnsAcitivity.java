package tv.cloudwalker.interaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.druk.rxdnssd.BonjourService;
import com.github.druk.rxdnssd.RxDnssd;
import com.github.druk.rxdnssd.RxDnssdEmbedded;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CustomDnsAcitivity extends AppCompatActivity {

    private static final String TAG = "CustomDnsAcitivity";
    private boolean foundService = false;
    private RxDnssd rxDnssd = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dns_acitivity);
        CwMdns cwMdns = new CwMdns(this);
        cwMdns.browseServices(this).subscribe(new Subscriber<BonjourService>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.getLocalizedMessage());
            }

            @Override
            public void onNext(BonjourService bonjourService) {
                if(bonjourService.getServiceName().contains("CloudTv_") && !foundService){
                    foundService = true;
                    Intent mainIntent = new Intent(CustomDnsAcitivity.this, MainActivity.class);
                    mainIntent.putExtra("port", bonjourService.getPort());
                    mainIntent.putExtra("ip", bonjourService.getInet4Address().getHostAddress());
                    startActivity(mainIntent);
                    onCompleted();
                    CustomDnsAcitivity.this.finish();
                }
            }
        });
    }


    private void getMDNSServices(){
        if(rxDnssd == null){
            rxDnssd = new RxDnssdEmbedded(CustomDnsAcitivity.this);
        }
        rxDnssd.browse("_http._tcp", "local.")
                .compose(rxDnssd.resolve())
                .compose(rxDnssd.queryRecords())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bonjourService -> {
                    if("CloudwalkerTV".equals(bonjourService.getServiceName()) && !foundService){
                        foundService = true;
                        Intent mainIntent = new Intent(CustomDnsAcitivity.this, MainActivity.class);
                        mainIntent.putExtra("port", bonjourService.getPort());
                        mainIntent.putExtra("ip", bonjourService.getInet4Address().getHostAddress());
                        startActivity(mainIntent);
                        CustomDnsAcitivity.this.finish();
                    }
                    Log.d(TAG, bonjourService.toString());
                    Log.d(TAG, "IP  "+bonjourService.getInet4Address());

                } , throwable -> {
                    Log.e(TAG, "error", throwable);
                });
    }


}























