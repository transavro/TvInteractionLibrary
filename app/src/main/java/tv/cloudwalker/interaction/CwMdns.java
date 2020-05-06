package tv.cloudwalker.interaction;

import android.content.Context;

import com.github.druk.rxdnssd.BonjourService;
import com.github.druk.rxdnssd.RxDnssd;
import com.github.druk.rxdnssd.RxDnssdEmbedded;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CwMdns
{

    private RxDnssd rxDnssd = null;

    public CwMdns(Context context){
        init(context);
    }

    private void init(Context context){
        rxDnssd = new RxDnssdEmbedded(context);
    }



    /* This method will discover all the service on the local network..
    *  Resolve the service and pass it on to your @return Observable
    * Note : For now it is showing same services multiple time in OnNext
    * so please implement some logic which maintain the list of service
    * and if in onNext, a service gets  discovered and already present in the list ? ignore it.
    * */

    public Observable<BonjourService> browseServices(Context context){
        if(rxDnssd == null){
            init(context);
        }

        return rxDnssd.browse("_http._tcp", "local.")
                .compose(rxDnssd.resolve())
                .compose(rxDnssd.queryIPRecords())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}




















