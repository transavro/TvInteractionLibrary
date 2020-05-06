package tv.cloudwalker.interaction;

import java.util.List;
import java.util.Map;

import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import main.TvInteractionServiceGrpc;

public class TvInteraction
{
    private ManagedChannel channel = null;

    public TvInteraction(String ipAddress , int port){
        channel = ManagedChannelBuilder.forAddress(ipAddress, port).usePlaintext().build();
    }

    public ConnectivityState checkConnectionStatus(){
        return channel.getState(true);
    }

    public boolean triggerKeyCode(String keycode){
        try {
            main.TvInteraction.Resp resp = TvInteractionServiceGrpc.newBlockingStub(channel).keycodeAction(main.TvInteraction.KeycodeReq.newBuilder().setKeycode(keycode).build());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Map<String, String> getAppList(){
        return TvInteractionServiceGrpc.newBlockingStub(channel).getAllApps(main.TvInteraction.GetAllAppsReq.newBuilder().build()).getAppListMap();
    }

    public List<String> getTvSourceList(){
        return TvInteractionServiceGrpc.newBlockingStub(channel).getListOfTvSources(main.TvInteraction.Req.newBuilder().build()).getTvsourcesList();
    }

    public boolean deleteApp(String packageName){
        try{
            main.TvInteraction.Resp resp = TvInteractionServiceGrpc.newBlockingStub(channel).appAction(main.TvInteraction.AppReq.newBuilder()
                    .setApp(main.TvInteraction.App.APP_UNINSTALL).setPackage(packageName).build());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean switchSource(String sourceName){
        try{
            main.TvInteraction.Resp resp = TvInteractionServiceGrpc.newBlockingStub(channel)
                    .tvAction(main.TvInteraction.TvActionReq.newBuilder().setSource(sourceName).build());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean playContent(String packageName, String deeplink){
        try{
            main.TvInteraction.Resp resp = TvInteractionServiceGrpc.newBlockingStub(channel)
                    .playContent(main.TvInteraction.PlayReq.newBuilder().setPackage(packageName).setDeepLink(deeplink).build());
            return true;
        }catch (Exception e){
            return false;
        }
    }
}


















