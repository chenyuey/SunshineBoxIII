package com.tipchou.sunshineboxiii.ui.phone_number_verify;

import android.support.annotation.NonNull;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.tipchou.sunshineboxiii.support.ToastUtils;


/**
 * 由邵励治于2017/11/29创造.
 */

public class PhoneNumberVerifyModel implements PhoneNumberVerifyContract.Model {

    private PhoneNumberVerifyContract.CallBack callBack;

    PhoneNumberVerifyModel(PhoneNumberVerifyContract.CallBack callBack) {
        this.callBack = callBack;
    }

    //Call API1:发送验证码
    @Override
    public void requestSendCaptchaBean(@NonNull String phoneNumber) {
        AVUser.requestLoginSmsCodeInBackground(phoneNumber, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    callBack.requestSendCaptchaBeanSuccess();
                } else {
                    ToastUtils.showToast(e.getMessage());
                }
            }
        });
    }

    //Call API2：验证验证码
    @Override
    public void requestCheckCaptchaBean(@NonNull String phoneNumber, @NonNull String captcha) {
        AVUser.loginBySMSCodeInBackground(phoneNumber, captcha, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    //验证成功
                    callBack.requestCheckCaptchaBeanSuccess();
                } else {
                    //验证失败
                    callBack.requestCheckCaptchaBeanFailure();
                }
            }
        });
    }

    //Call API3: 验证手机号
    @Override
    public void requestToLogin(@NonNull String phoneNumber, @NonNull String password) {
        AVUser.logInInBackground(phoneNumber, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    //登录成功
                    callBack.requestToLoginSuccess();
                } else {
                    //登录失败
                    callBack.requestToLoginFailure();
                }
            }
        });
    }
}
