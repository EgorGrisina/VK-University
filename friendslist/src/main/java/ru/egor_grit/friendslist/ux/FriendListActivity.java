package ru.egor_grit.friendslist.ux;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKUsersArray;

import ru.egor_grit.friendslist.R;


public class FriendListActivity extends ActionBarActivity {

    ListView friendListView;
    Context mContext;
    ProgressBar loadProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list_activity);
        mContext = this;

        friendListView = (ListView) findViewById(R.id.lv_friends_list);

        loadProgressView = (ProgressBar) findViewById(R.id.pb_loading_progress);

        if (!VKSdk.isLoggedIn()) {

            VKSdk.login(this, VKScope.FRIENDS);
        } else {
            loadFriendsList();
        }
    }

    private void loadFriendsList(){

        VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "photo_50,photo_100,photo_200_orig,online"))
                .executeWithListener(new VKRequest.VKRequestListener() {

                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        loadProgressView.setVisibility(View.GONE);

                        if (response.parsedModel instanceof VKUsersArray) {

                            FriendListAdapter friendListAdapter =
                                    new FriendListAdapter(mContext, (VKUsersArray)response.parsedModel);
                            friendListView.setAdapter(friendListAdapter);
                        }
                    }

                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                        loadProgressView.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                loadFriendsList();
            }

            @Override
            public void onError(VKError error) {

            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_friend_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
