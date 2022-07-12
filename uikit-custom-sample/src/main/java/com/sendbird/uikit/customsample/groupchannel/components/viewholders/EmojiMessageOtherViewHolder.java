package com.sendbird.uikit.customsample.groupchannel.components.viewholders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.sendbird.android.channel.BaseChannel;
import com.sendbird.android.message.BaseMessage;
import com.sendbird.android.message.Reaction;
import com.sendbird.android.message.SendingStatus;
import com.sendbird.android.user.Sender;
import com.sendbird.uikit.activities.viewholder.GroupChannelMessageViewHolder;
import com.sendbird.uikit.consts.ClickableViewIdentifier;
import com.sendbird.uikit.consts.MessageGroupType;
import com.sendbird.uikit.customsample.R;
import com.sendbird.uikit.customsample.databinding.ViewEmojiMessageOtherHolderBinding;
import com.sendbird.uikit.customsample.utils.DrawableUtils;
import com.sendbird.uikit.interfaces.OnItemClickListener;
import com.sendbird.uikit.interfaces.OnItemLongClickListener;

import java.util.List;
import java.util.Map;

/**
 * ViewHolder to draw the emoji message sent from other users in the <code>GroupChannel</code>.
 */
public class EmojiMessageOtherViewHolder extends GroupChannelMessageViewHolder {
    final private ViewEmojiMessageOtherHolderBinding binding;

    public EmojiMessageOtherViewHolder(@NonNull ViewEmojiMessageOtherHolderBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        clickableViewMap.put(ClickableViewIdentifier.Chat.name(),binding.ivEmoji);
        clickableViewMap.put(ClickableViewIdentifier.Profile.name(), binding.ivProfileView);
    }

    @Override
    public void bind(@NonNull BaseChannel channel, @NonNull BaseMessage message, @NonNull MessageGroupType messageGroupType) {
        Context context = binding.getRoot().getContext();
        boolean sendingState = message.getSendingStatus() == SendingStatus.SUCCEEDED;
        binding.tvSentAt.setVisibility(sendingState ? View.VISIBLE : View.GONE);
        String sentAt = DateUtils.formatDateTime(context, message.getCreatedAt(), DateUtils.FORMAT_SHOW_TIME);
        binding.tvSentAt.setText(sentAt);

        Sender sender = message.getSender();
        String nickname = sender == null || TextUtils.isEmpty(sender.getNickname()) ?
                context.getString(R.string.sb_text_channel_list_title_unknown) :
                sender.getNickname();
        binding.tvNickname.setText(nickname);

        String url = "";
        if (sender != null && !TextUtils.isEmpty(sender.getProfileUrl())) {
            url = sender.getProfileUrl();
        }

        Drawable errorIcon = DrawableUtils.createOvalIcon(binding.getRoot().getContext(),
                R.color.background_300, R.drawable.icon_user, R.color.ondark_01);
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(new ObjectKey(url))
                .error(errorIcon)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivProfileView);

        Glide.with(itemView)
                .load(message.getMessage())
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.ivEmoji);
    }

    @NonNull
    @Override
    public Map<String, View> getClickableViewMap() {
        return clickableViewMap;
    }


    @Override
    public void setEmojiReaction(@NonNull List<Reaction> reactionList, @Nullable OnItemClickListener<String> emojiReactionClickListener, @Nullable OnItemLongClickListener<String> emojiReactionLongClickListener, @Nullable View.OnClickListener moreButtonClickListener) {}
}
