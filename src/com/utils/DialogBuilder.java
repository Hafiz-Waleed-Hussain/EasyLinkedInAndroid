package com.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

public class DialogBuilder {

	public DialogBuilder() {

	}

	public static Dialog BuildDialog(Context context) {

		return BuildDialog(context, false);
	}

	public static Dialog BuildDialog(Context context, boolean cancelable) {

		Dialog dialog = new ProgressDialog(context);
		dialog.setCancelable(cancelable);

		return dialog;
	}

}
