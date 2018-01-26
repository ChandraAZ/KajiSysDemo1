package id.azset.studio.kajisystemdev1.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;

import java.util.List;

import id.azset.studio.kajisystemdev1.R;

public class MediaPlaybackService extends MediaBrowserServiceCompat {

    private MediaSessionCompat mMediaSession;

    public MediaPlaybackService() {}

    @Override
    public void onCreate() {
        super.onCreate();
        // Create your MediaSessionCompat.
        // You should already be doing this
            mMediaSession = new MediaSessionCompat(this,
                MediaPlaybackService.class.getSimpleName());
        // Make sure to configure your MediaSessionCompat as per
        // https://www.youtube.com/watch?v=FBC1FgWe5X4
        setSessionToken(mMediaSession.getSessionToken());
    }

   // @Override
    //public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
    //    return null;
    //}

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        // Returning null == no one can connect
        // so we’ll return something
        return new BrowserRoot(
                getString(R.string.app_name), // Name visible in Android Auto
                null); // Bundle of optional extras
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        // I promise we’ll get to browsing
        result.sendResult(null);
    }
}
