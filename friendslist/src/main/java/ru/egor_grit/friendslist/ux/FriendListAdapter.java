package ru.egor_grit.friendslist.ux;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKUsersArray;

import ru.egor_grit.friendslist.R;


public class FriendListAdapter extends BaseAdapter{

    VKUsersArray friendList = null;
    Context mContext;

    class FriendItemViewHolder {
        ImageView friendPhoto;
        TextView friendName;
        TextView friendOnline;
    }

    public FriendListAdapter(Context context, VKUsersArray data) {
        mContext = context;
        friendList = data;
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int i) {
        return friendList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        VKApiUserFull friendItem = (VKApiUserFull)getItem(i);

        if (view == null) {

            view = ((Activity)mContext).getLayoutInflater().inflate(R.layout.friends_list_item, null);

            FriendItemViewHolder holder = new FriendItemViewHolder();

            holder.friendPhoto = (ImageView) view.findViewById(R.id.iv_friend_item_photo);
            holder.friendName = (TextView) view.findViewById(R.id.iv_friend_item_name);
            holder.friendOnline = (TextView) view.findViewById(R.id.iv_friend_item_online);

            view.setTag(holder);
        }

        FriendItemViewHolder holder = (FriendItemViewHolder) view.getTag();

        holder.friendName.setText(friendItem.first_name+" "+friendItem.last_name);

        if(friendItem.online) {
            holder.friendOnline.setText(mContext.getString(R.string.friend_online));
        } else {
            holder.friendOnline.setText(mContext.getString(R.string.friend_offline));
        }

        Picasso.with(mContext).load(friendItem.photo_100).into(holder.friendPhoto);

        return view;
    }
}
