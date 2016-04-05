package com.mangopay.android.sdk;

import com.mangopay.android.sdk.domain.service.ServiceCallback;
import com.mangopay.android.sdk.model.CardRegistration;

/**
 * Interface callback used when all the SDK process has been completed or failed
 */
public interface Callback extends ServiceCallback<CardRegistration> {
}
