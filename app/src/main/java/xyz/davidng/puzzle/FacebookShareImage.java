package xyz.davidng.puzzle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.Arrays;
import java.util.List;

public class FacebookShareImage extends Activity {
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    byte[] byteArrayImage;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_facebook_share_image);

            FacebookSdk.sdkInitialize(getApplicationContext());
            callbackManager = CallbackManager.Factory.create();

            byteArrayImage = getIntent().getByteArrayExtra("image");
            bmp = BitmapFactory.decodeByteArray(byteArrayImage, 0, byteArrayImage.length);
            List<String> permissionNeeds = Arrays.asList("publish_actions");

            loginManager = LoginManager.getInstance();

            loginManager.logInWithPublishPermissions(this, permissionNeeds);

            loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    shareImage();
                    finish();
                }

                @Override
                public void onCancel() {}

                @Override
                public void onError(FacebookException exception) {}
            });
        }
    }

    private void shareImage() {
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bmp)
                .setCaption("Simple puzzle")
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }
}
