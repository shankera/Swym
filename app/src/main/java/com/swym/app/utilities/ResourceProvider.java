package com.swym.app.utilities;

import android.content.Context;
import android.support.annotation.StringRes;

public class ResourceProvider {
    private Context context;

    public ResourceProvider(Context context) {
        this.context = context;
    }

    public String getString(@StringRes int id) {
        return this.context.getString(id);
    }
}
