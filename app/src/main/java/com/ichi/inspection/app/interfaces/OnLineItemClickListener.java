package com.ichi.inspection.app.interfaces;

import android.view.View;

import com.ichi.inspection.app.models.SubSectionsItem;

/**
 * Created by Palak on 08-01-2017.
 */

public interface OnLineItemClickListener {

    void onLineItemClick(View view, SubSectionsItem subSectionsItem, int position);
}
