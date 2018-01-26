package id.azset.studio.kajisystemdev1.services;

import android.support.v4.media.session.PlaybackStateCompat;

/**
 * Created by id_admchaset on 12/22/2017.
 */

public abstract class PlaybackInfoListener {
    public abstract void onPlaybackStateChange(PlaybackStateCompat state);

    public void onPlaybackCompleted() {}
}
