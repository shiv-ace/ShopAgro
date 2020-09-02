package com.codeshiv.shopagro.social;

import com.facebook.CallbackManager;

import javax.inject.Inject;

public class FacebookAuthManager {

    private CallbackManager callbackManager;

    @Inject
    public FacebookAuthManager() {
        callbackManager = CallbackManager.Factory.create();
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }
}
