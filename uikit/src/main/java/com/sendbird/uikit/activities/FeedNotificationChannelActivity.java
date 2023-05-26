package com.sendbird.uikit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.sendbird.uikit.R;
import com.sendbird.uikit.SendbirdUIKit;
import com.sendbird.uikit.consts.StringSet;
import com.sendbird.uikit.utils.ContextUtils;
import com.sendbird.uikit.utils.TextUtils;

/**
 * Activity displays notifications of a channel by user.
 */
public class FeedNotificationChannelActivity extends AppCompatActivity {

    /**
     * Create an intent for a {@link FeedNotificationChannelActivity}.
     *
     * @param context    A Context of the application package implementing this class.
     * @param channelUrl the url of the channel will be implemented.
     * @return FeedNotificationChannelActivity Intent.
     * since 3.5.0
     */
    @NonNull
    public static Intent newIntent(@NonNull Context context, @NonNull String channelUrl) {
        return newIntentFromCustomActivity(context, FeedNotificationChannelActivity.class, channelUrl);
    }

    /**
     * Create an intent for a {@link FeedNotificationChannelActivity}.
     *
     * @param context    A Context of the application package implementing this class.
     * @param channelUrl the url of the channel will be implemented.
     * @param themeResId the resource identifier for custom theme.
     * @return FeedNotificationChannelActivity Intent.
     * since 3.5.6
     */
    @NonNull
    public static Intent newIntent(@NonNull Context context, @NonNull String channelUrl, @StyleRes int themeResId) {
        return newIntentFromCustomActivity(context, FeedNotificationChannelActivity.class, channelUrl, themeResId);
    }

    /**
     * Create an intent for a custom activity. The custom activity must inherit {@link FeedNotificationChannelActivity}.
     *
     * @param context    A Context of the application package implementing this class.
     * @param cls        The activity class that is to be used for the intent.
     * @param channelUrl the url of the channel will be implemented.
     * @return Returns a newly created Intent that can be used to launch the activity.
     * since 3.5.0
     */
    @NonNull
    public static Intent newIntentFromCustomActivity(@NonNull Context context, @NonNull Class<? extends FeedNotificationChannelActivity> cls, @NonNull String channelUrl) {
        return newIntentFromCustomActivity(context, cls, channelUrl, SendbirdUIKit.getDefaultThemeMode().getResId());
    }

    /**
     * Create an intent for a custom activity. The custom activity must inherit {@link FeedNotificationChannelActivity}.
     *
     * @param context    A Context of the application package implementing this class.
     * @param cls        The activity class that is to be used for the intent.
     * @param channelUrl the url of the channel will be implemented.
     * @param themeResId the resource identifier for custom theme.
     * @return Returns a newly created Intent that can be used to launch the activity.
     * since 3.5.6
     */
    @NonNull
    public static Intent newIntentFromCustomActivity(@NonNull Context context, @NonNull Class<? extends FeedNotificationChannelActivity> cls, @NonNull String channelUrl, @StyleRes int themeResId) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(StringSet.KEY_CHANNEL_URL, channelUrl);
        intent.putExtra(StringSet.KEY_THEME_RES_ID, themeResId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int themeResId = getIntent().getIntExtra(StringSet.KEY_THEME_RES_ID, SendbirdUIKit.getDefaultThemeMode().getResId());
        setTheme(themeResId);
        setContentView(R.layout.sb_activity);

        String url = getIntent().getStringExtra(StringSet.KEY_CHANNEL_URL);
        if (TextUtils.isEmpty(url)) {
            ContextUtils.toastError(this, R.string.sb_text_error_get_channel);
        } else {
            Fragment fragment = createFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStack();
            manager.beginTransaction()
                    .replace(R.id.sb_fragment_container, fragment)
                    .commit();
        }
    }

    /**
     * It will be called when the {@link FeedNotificationChannelActivity} is being created.
     * The data contained in Intent is delivered to Fragment's Bundle.
     *
     * @return {@link com.sendbird.uikit.fragments.FeedNotificationChannelFragment}
     * since 3.5.0
     */
    @NonNull
    protected Fragment createFragment() {
        final Bundle args = getIntent() != null && getIntent().getExtras() != null ? getIntent().getExtras() : new Bundle();
        return SendbirdUIKit.getFragmentFactory().newFeedNotificationChannelFragment(args.getString(StringSet.KEY_CHANNEL_URL, ""), args);
    }
}
