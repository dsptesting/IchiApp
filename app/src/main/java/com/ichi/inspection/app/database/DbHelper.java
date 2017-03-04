
package com.ichi.inspection.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;


import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.PreferencesHelper;
import com.ichi.inspection.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteAssetHelper{

    private static final String TAG = DbHelper.class.getSimpleName();
    private static DbHelper instance;
    public static PreferencesHelper prefs;
    public Context mContext;

    //database configuration
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "cartrack_elV4.db"; //put new database name here

    public DbHelper(Context context) {
        //super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // you can use an alternate constructor to specify a database location
        // (such as a folder on the sd card)
        // you must ensure that this folder is available and you have permission
        // to write to it
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static synchronized DbHelper getInstance(Context context) {
        prefs = PreferencesHelper.getInstance(context);
        if (instance == null) {
            instance = new DbHelper(context);
        }
        return instance;
    }

    //Common Column Names

    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_CREATE_TS = "create_ts";
    public static final String KEY_USER_NAME = "user_name";

    //ALL TABLE NAME
    public static final String TABLE_EVENTS = "events";
    public static final String TABLE_VEHICLES = "vehicles";
    public static final String TABLE_CONTACT = "contact";
    public static final String TABLE_MESSAGE = "message";
    public static final String TABLE_EVENT_TYPE = "event_type";
    public static final String TABLE_DEFECTS = "defects";
    public static final String TABLE_CYCLE_RULES = "cycle_rules";
    public static final String TABLE_LOCATIONS = "locations";


    /**
     * Event table Column Names
     */

    public static final String KEY_EVENTS_EVENT_ID = "event_id";
    public static final String KEY_EVENTS_DRIVER_ID = "driver_id";
    public static final String KEY_EVENTS_VEHICLE_ID = "vehicle_id";
    public static final String KEY_EVENTS_SEQ = "seq";
    public static final String KEY_EVENTS_EVENT_TYPE_ID = "event_type_id";
    public static final String KEY_EVENTS_EVENT_TYPE = "event_type";
    public static final String KEY_EVENTS_LAT = "lat";
    public static final String KEY_EVENTS_LON = "lon";
    public static final String KEY_EVENTS_LAT_LON = "latlon";
    public static final String KEY_EVENTS_ADDRESS = "address";
    public static final String KEY_EVENTS_EVENT_NOTE = "event_note";
    public static final String KEY_EVENTS_EVENT_START_TIME = "event_start_time";
    public static final String KEY_EVENTS_EVENT_END_TIME = "event_end_time";

    /**
     * Vehicle Table Column Names
     */
    public static final String KEY_VEHICLES_ID = "vehicle_id";
    public static final String KEY_VEHICLES_REGISTRATION = "registration";
    public static final String KEY_VEHICLES_DESCRIPTION = "description";
    public static final String KEY_VEHICLES_VIN_NUMBER = "vin_number";
    public static final String KEY_VEHICLES_ODOMETER = "odometer";
    public static final String KEY_VEHICLES_CARTRACK_UNIT = "cartrack_unit";
    public static final String KEY_VEHICLES_BLE_NAME = "eld_name";
    public static final String KEY_VEHICLES_LAST_MODIFIED = "last_modified";


    /*
	* Contact Table Column Names
	*/
    public static final String KEY_DEVICE_NAME = "device_name";
    public static final String KEY_DEVICE_DESCRIPTION = "device_description";
    public static final String KEY_MOBILE_DEVICE_ID = "mobile_device_id";
    public static final String KEY_IMEI = "imei";
    public static final String KEY_ANDROID_ID = "android_id";
    public static final String KEY_RECENT_MESSAGE = "recent_message";
    public static final String KEY_RECENT_MESSAGE_TIME = "recent_message_time";
    public static final String KEY_RECENT_MESSAGE_STATE_ID = "recent_message_state_id";
    public static final String KEY_RECENT_MESSAGE_READ = "recent_message_read";
    public static final String KEY_RECENT_MESSAGE_COLOUR = "recent_message_colour";

    /*
	* Message Table Column Names
	*/
    public static final String KEY_MESSAGE_ID = "message_id";
    public static final String KEY_ORIGINAL_MESSAGE_ID = "original_message_id";
    public static final String KEY_MESSAGE_STATE_ID = "message_state_id";
    public static final String KEY_MESSAGE_TYPE_ID = "message_type_id";
    public static final String KEY_MESSAGE_SOURCE_ID = "message_source_id";
    public static final String KEY_MESSAGE_DIRECTION_ID = "message_direction_id";
    public static final String KEY_IDENTIFIER = "identifier";
    public static final String KEY_MESSAGE_BODY = "message_body";
    public static final String KEY_MESSAGE_ROUTE = "message_route";
    public static final String KEY_TARGET = "target";
    public static final String KEY_ACTION = "action";
    public static final String KEY_LEFT = "left";


    /**
     * Event type Table Column Names
     */
    public static final String KEY_EVENT_TYPE_ID = "event_type_id";
    public static final String KEY_EVENT_TYPE = "event_type";


    /**
     *Defect table column names
     */
    public static final String KEY_DEFECTS_ID = "defect_id";
    public static final String KEY_DEFECTS_DESCRIPTION = "defect_description";
    public static final String KEY_DEFECTS_CATEGORY_ID = "category_id";
    public static final String KEY_DEFECTS_CATEGORY_DESCRIPTION = "category_description";
    public static final String KEY_DEFECTS_LAST_MODIFIED = "last_modified";


    /**
     * CYCLE RULES table column names
     */
    public static final String KEY_CYCLE_RULES_ID = "cycle_rules_id";
    public static final String KEY_CYCLE_RULES_DESCRIPTION = "description";
    public static final String KEY_CYCLE_RULES_HOURS = "hours";
    public static final String KEY_CYCLE_RULES_DAYS = "days";
    public static final String KEY_CYCLE_RULES_LAST_MODIFIED = "last_modified";

    /**
     * Locations table column names
     */

    public static final String KEY_LOCATIONS_ID = "location_id";
    public static final String KEY_LOCATIONS_DESCRIPTION = "description";
    public static final String KEY_LOCATIONS_LAT = "lat";
    public static final String KEY_LOCATIONS_LON = "lon";
    public static final String KEY_LOCATIONS_LAT_LON = "latlon";





    /**
     ****************************************
     * Contact Methods
     ****************************************
     */

    // Insert Contact
    public long insertContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get values
        ContentValues values = new ContentValues();
        values.put(KEY_DEVICE_NAME, Utils.Capitalize(contact.getDeviceName()));
        values.put(KEY_DEVICE_DESCRIPTION, Utils.Capitalize(contact.getDeviceDescription()));
        values.put(KEY_MOBILE_DEVICE_ID, contact.getMobileDeviceId());
        values.put(KEY_IMEI, contact.getIMEI());
        values.put(KEY_USER_ID, contact.getUserId());
        values.put(KEY_ANDROID_ID, contact.getAndroidId());

        // Insert Row
        try {
            long key_id = db.insertOrThrow(TABLE_CONTACT, null, values);
            if (key_id > 0) {
                return key_id;
            } else {
                updateContact(contact);
            }
        } catch (SQLiteConstraintException e) {
            if (Constants.showDebugMessages) Log.d(TAG, "Updating Contact: " + e.toString());
            updateContact(contact);
        } finally {
            if (Constants.showDebugMessages)
                Log.d(TAG, "Insert Complete: KEY: " + contact.getMobileDeviceId());
        }
        return 0;
    }

    // Insert all the contacts.
    public void insertAllContact(List<Contact> contacts) {

        for (int i=0;i<contacts.size();i++){
            insertContact(contacts.get(i));
        }
    }

    // Update Contact
    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get values
        ContentValues values = new ContentValues();
        values.put(KEY_DEVICE_NAME, Utils.Capitalize(contact.getDeviceName()));
        values.put(KEY_DEVICE_DESCRIPTION, Utils.Capitalize(contact.getDeviceDescription()));
        values.put(KEY_MOBILE_DEVICE_ID, contact.getMobileDeviceId());
        values.put(KEY_IMEI, contact.getIMEI());
        values.put(KEY_USER_ID, contact.getUserId());
        values.put(KEY_ANDROID_ID, contact.getAndroidId());

        // Update the row
        db.update(TABLE_CONTACT, values, KEY_MOBILE_DEVICE_ID + " = ? AND " + KEY_USER_ID + " = ?", new String[]{String.valueOf(contact.getMobileDeviceId()), String.valueOf(contact.getUserId())});
    }

    // Update Contact
    public void updateContactAll(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get values
        ContentValues values = new ContentValues();
        values.put(KEY_DEVICE_NAME, Utils.Capitalize(contact.getDeviceName()));
        values.put(KEY_DEVICE_DESCRIPTION, Utils.Capitalize(contact.getDeviceDescription()));
        values.put(KEY_MOBILE_DEVICE_ID, contact.getMobileDeviceId());
        values.put(KEY_IMEI, contact.getIMEI());
        values.put(KEY_USER_ID, contact.getUserId());
        values.put(KEY_ANDROID_ID, contact.getAndroidId());
        values.put(KEY_RECENT_MESSAGE, contact.getRecentMessage());
        values.put(KEY_RECENT_MESSAGE_STATE_ID, contact.getRecentMessageStateID());
        values.put(KEY_RECENT_MESSAGE_TIME, contact.getRecentMessageTime());
        values.put(KEY_RECENT_MESSAGE_COLOUR, contact.getRecentMessageColour());
        values.put(KEY_RECENT_MESSAGE_READ, contact.getRecentMessageRead());

        // Update the row
        db.update(TABLE_CONTACT, values, KEY_MOBILE_DEVICE_ID + " = ? AND " + KEY_USER_ID + " = ?", new String[]{String.valueOf(contact.getMobileDeviceId()), String.valueOf(contact.getUserId())});
    }

    // Update Contact
    public void updateContactMessageRead(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get values
        ContentValues values = new ContentValues();
        values.put(KEY_RECENT_MESSAGE_READ, contact.getRecentMessageRead());

        // Update the row
        db.update(TABLE_CONTACT, values, KEY_MOBILE_DEVICE_ID + " = ? AND " + KEY_USER_ID + " = ?", new String[]{String.valueOf(contact.getMobileDeviceId()), String.valueOf(contact.getUserId())});
    }

    // Populate Recent Contact Messages
    public List<Contact> populateRecentContactMessages(Context context, long user_id) {
        List<Contact> contacts = getAllContacts(user_id);
        PreferencesHelper preferencesHelper = PreferencesHelper.getInstance(context);
        List<Contact> contactsNew = new ArrayList<>();
        if (contacts != null && contacts.size() > 0) {
            for (Contact contact : contacts) {
                Message message = getMostRecentContactMessage(contact.getIMEI(), user_id);
                if (message != null) {
                    int read = 0;

                    if (message.getMessageBody().equals(contact.getRecentMessage())) {
                        read = contact.getRecentMessageRead();
                    }

                    contact.setRecentMessage(message.getMessageBody());
                    contact.setRecentMessageTime(message.getCreateTS());

                    if (message!=null && message.getIdentifier()!=null && message.getIdentifier().
                            equals(preferencesHelper.getString(PreferencesHelper.DEVICE_ID))) {
                        read = 1;
                    }

                    contact.setRecentMessageShowStatus(message.getLeft() ? 0 : 1);
                    contact.setRecentMessageStateID(message.getMessageStateID());
                    contact.setRecentMessageRead(read);
                    updateContactAll(contact);
                    contactsNew.add(contact);
                }
            }
            return contactsNew;
        }
        return contactsNew;
    }

    // Fetch All Contacts
    public List<Contact> getAllContacts(long user_id) {
        List<Contact> contacts = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACT + " WHERE " + KEY_USER_ID + " = " + String.valueOf(user_id) + " ORDER BY " + KEY_DEVICE_NAME + " ASC";

        if (Constants.showDebugMessages) Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // Loop through all of the rows and add items to the list
        if (c.moveToFirst()) {
            do {
                // Set values
                Contact contact = new Contact();
                contact.setDeviceName(Utils.Capitalize(c.getString(c.getColumnIndex(KEY_DEVICE_NAME))));
                contact.setDeviceDescription(Utils.Capitalize(c.getString(c.getColumnIndex(KEY_DEVICE_DESCRIPTION))));
                contact.setMobileDeviceId(c.getInt(c.getColumnIndex(KEY_MOBILE_DEVICE_ID)));
                contact.setIMEI(c.getString(c.getColumnIndex(KEY_IMEI)));
                contact.setUserId(c.getInt(c.getColumnIndex(KEY_USER_ID)));
                contact.setAndroidId(c.getString(c.getColumnIndex(KEY_ANDROID_ID)));
                contact.setRecentMessage(c.getString(c.getColumnIndex(KEY_RECENT_MESSAGE)));
                contact.setRecentMessageStateID(c.getLong(c.getColumnIndex(KEY_RECENT_MESSAGE_STATE_ID)));
                contact.setRecentMessageTime(c.getString(c.getColumnIndex(KEY_RECENT_MESSAGE_TIME)));
                contact.setRecentMessageColour(c.getString(c.getColumnIndex(KEY_RECENT_MESSAGE_COLOUR)));
                contact.setRecentMessageRead(c.getInt(c.getColumnIndex(KEY_RECENT_MESSAGE_READ)));
                contacts.add(contact);
            } while (c.moveToNext());
        }

        c.close();

        return contacts;
    }

    // Fetch All Messages
    public Message getMostRecentContactMessage(String imei, long user_id) {
        String selectQuery = "SELECT * FROM " + TABLE_MESSAGE + " WHERE (" + KEY_TARGET + " = '" + imei + "' OR " + KEY_IDENTIFIER + " = '" + imei + "') AND " + KEY_USER_ID + " = " + String.valueOf(user_id) + " ORDER BY " + KEY_CREATE_TS + " DESC LIMIT 1";

//        if (Constants.showDebugMessages) Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // Loop through all of the rows and add items to the list
        if (c != null) {
            if (c.moveToFirst()) {
                // Set values
                Message message = new Message();
                message.setMessageID(c.getString(c.getColumnIndex(KEY_MESSAGE_ID)));
                message.setOriginalMessageID(c.getString(c.getColumnIndex(KEY_ORIGINAL_MESSAGE_ID)));
                message.setMessageStateID(c.getInt(c.getColumnIndex(KEY_MESSAGE_STATE_ID)));
                message.setMessageTypeID(c.getInt(c.getColumnIndex(KEY_MESSAGE_TYPE_ID)));
                message.setMessageSourceID(c.getInt(c.getColumnIndex(KEY_MESSAGE_SOURCE_ID)));
                message.setMessageDirectionID(c.getInt(c.getColumnIndex(KEY_MESSAGE_DIRECTION_ID)));
                message.setIdentifier(c.getString(c.getColumnIndex(KEY_IDENTIFIER)));
                message.setMessageBody(c.getString(c.getColumnIndex(KEY_MESSAGE_BODY)));
                message.setMessageRoute(c.getString(c.getColumnIndex(KEY_MESSAGE_ROUTE)));
                message.setTarget(c.getString(c.getColumnIndex(KEY_TARGET)));
                message.setUserName(c.getString(c.getColumnIndex(KEY_USER_NAME)));
                message.setAction(c.getString(c.getColumnIndex(KEY_ACTION)));
                message.setUserID(c.getInt(c.getColumnIndex(KEY_USER_ID)));
                message.setCreateTS(c.getString(c.getColumnIndex(KEY_CREATE_TS)));
                boolean left = c.getInt(c.getColumnIndex(KEY_LEFT)) > 0;
                message.setLeft(left);
                return message;
            }
            c.close();
        }

        return null;
    }

    // Delete All Contacts
    public void deleteAllContacts() {
        String deleteQuery = "DELETE FROM " + TABLE_CONTACT;

        if (Constants.showDebugMessages) Log.d(TAG, deleteQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(deleteQuery, null);

        if (c != null) {
            c.moveToFirst();
            c.close();
        }
    }



    /**
     ****************************************
     * Message Methods
     ****************************************
     */

    // Insert Message
    public long insertMessage(Message message, String myDeviceId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get values
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE_ID, message.getMessageID());
        values.put(KEY_ORIGINAL_MESSAGE_ID, message.getOriginalMessageID());
        values.put(KEY_MESSAGE_STATE_ID, message.getMessageStateID());
        values.put(KEY_MESSAGE_TYPE_ID, message.getMessageTypeID());
        values.put(KEY_MESSAGE_SOURCE_ID, message.getMessageSourceID());
        values.put(KEY_MESSAGE_DIRECTION_ID, message.getMessageDirectionID());
        values.put(KEY_IDENTIFIER, message.getIdentifier());
        values.put(KEY_MESSAGE_BODY, message.getMessageBody());
        values.put(KEY_MESSAGE_ROUTE, message.getMessageRoute());
        values.put(KEY_TARGET, message.getTarget());
        values.put(KEY_USER_NAME, message.getUserName());
        values.put(KEY_ACTION, message.getAction());
        values.put(KEY_USER_ID, message.getUserID());
        values.put(KEY_CREATE_TS, message.getCreateTS());

        int left = 0;
        if (myDeviceId != null) {
            left = myDeviceId.equals(message.getTarget()) ? 1 : 0;
        }

        values.put(KEY_LEFT, left);

        // Insert Row
        try {
            long key_id = db.insertOrThrow(TABLE_MESSAGE, null, values);
            if (key_id > 0) {
                return key_id;
            } else {
                throw new SQLException("Failed to insert message into table. Key: " + message.getMessageID());
            }
        } catch (SQLiteConstraintException e) {
            if (Constants.showDebugMessages)
                Log.d(TAG, "Ignoring constraint failure. " + e.toString());
        } finally {
            if (Constants.showDebugMessages)
                Log.d(TAG, "Insert Complete: KEY: " + message.getMessageID());
        }
        return 0;
    }

    /*
	****************************************
	* Message Methods
	****************************************
	*/

    public void insertAllMessage(List<Message> messages, String myDeviceId){
        for(Message message : messages){
            insertMessage(message,myDeviceId);
        }
    }

    // Update Message
    public void updateMessage(Message message, String myDeviceId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get values
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE_ID, message.getMessageID());
        values.put(KEY_ORIGINAL_MESSAGE_ID, message.getOriginalMessageID());
        values.put(KEY_MESSAGE_STATE_ID, message.getMessageStateID());
        values.put(KEY_MESSAGE_TYPE_ID, message.getMessageTypeID());
        values.put(KEY_MESSAGE_SOURCE_ID, message.getMessageSourceID());
        values.put(KEY_MESSAGE_DIRECTION_ID, message.getMessageDirectionID());
        values.put(KEY_IDENTIFIER, message.getIdentifier());
        values.put(KEY_MESSAGE_BODY, message.getMessageBody());
        values.put(KEY_MESSAGE_ROUTE, message.getMessageRoute());
        values.put(KEY_TARGET, message.getTarget());
        values.put(KEY_USER_NAME, message.getUserName());
        values.put(KEY_ACTION, message.getAction());
        values.put(KEY_USER_ID, message.getUserID());
        values.put(KEY_CREATE_TS, message.getCreateTS());
        int left = myDeviceId.equals(message.getTarget()) ? 1 : 0;
        values.put(KEY_LEFT, left);

        // Update the row
        db.update(TABLE_MESSAGE, values, KEY_MESSAGE_ID + " = ?", new String[]{message.getMessageID()});
    }

    // Delete Message
    public void deleteMessage(String message_id) {
        String deleteQuery = "DELETE FROM " + TABLE_MESSAGE + " WHERE " + KEY_MESSAGE_ID + " = '" + message_id + "' ";

        if (Constants.showDebugMessages) Log.d(TAG, deleteQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(deleteQuery, null);

        if (c != null) {
            c.moveToFirst();
            c.close();
        }
    }


    // Delete all Message when user is change
    public void deleteAllMessage() {
        String deleteQuery = "DELETE FROM " + TABLE_MESSAGE +" WHERE " + KEY_MESSAGE_STATE_ID + " != '" + "0" + "' ";

        if (Constants.showDebugMessages) Log.d(TAG, deleteQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(deleteQuery, null);

        if (c != null) {
            c.moveToFirst();
            c.close();
        }
    }

    // Fetch Single Message
    public Message getMessage(String message_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE + " WHERE " + KEY_MESSAGE_ID + " = '" + String.valueOf(message_id) + "' ";

        if (Constants.showDebugMessages) Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        Message message = new Message();

        if (c != null) {
            c.moveToFirst();

            // Set values
            message.setMessageID(c.getString(c.getColumnIndex(KEY_MESSAGE_ID)));
            message.setOriginalMessageID(c.getString(c.getColumnIndex(KEY_ORIGINAL_MESSAGE_ID)));
            message.setMessageStateID(c.getInt(c.getColumnIndex(KEY_MESSAGE_STATE_ID)));
            message.setMessageTypeID(c.getInt(c.getColumnIndex(KEY_MESSAGE_TYPE_ID)));
            message.setMessageSourceID(c.getInt(c.getColumnIndex(KEY_MESSAGE_SOURCE_ID)));
            message.setMessageDirectionID(c.getInt(c.getColumnIndex(KEY_MESSAGE_DIRECTION_ID)));
            message.setIdentifier(c.getString(c.getColumnIndex(KEY_IDENTIFIER)));
            message.setMessageBody(c.getString(c.getColumnIndex(KEY_MESSAGE_BODY)));
            message.setMessageRoute(c.getString(c.getColumnIndex(KEY_MESSAGE_ROUTE)));
            message.setTarget(c.getString(c.getColumnIndex(KEY_TARGET)));
            message.setUserName(c.getString(c.getColumnIndex(KEY_USER_NAME)));
            message.setAction(c.getString(c.getColumnIndex(KEY_ACTION)));
            message.setUserID(c.getInt(c.getColumnIndex(KEY_USER_ID)));
            message.setCreateTS(c.getString(c.getColumnIndex(KEY_CREATE_TS)));
            boolean left = c.getInt(c.getColumnIndex(KEY_LEFT)) > 0;
            message.setLeft(left);

            c.close();
        }

        return message;
    }

    // Fetch All Message With Filters
    public List<Message> getAllMessagesByFilter(String _whereClause, String _orderByColumn, int _limit, long user_id) {
        List<Message> messages = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MESSAGE;
        String nextComparisonOperator = " WHERE ";

        if (_whereClause != null && !TextUtils.isEmpty(_whereClause)) {
            selectQuery = selectQuery + nextComparisonOperator + _whereClause;
            nextComparisonOperator = " AND ";
        }

        selectQuery = selectQuery + nextComparisonOperator + KEY_USER_ID + " = " + String.valueOf(user_id);

        if (_orderByColumn != null) {
            // Use descending order to display the most recent items
            selectQuery = selectQuery + " ORDER BY " + _orderByColumn + " DESC ";
        }

        if (_limit > 0) {
            selectQuery = selectQuery + " LIMIT " + _limit;
        }

        if (Constants.showDebugMessages) Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // Loop through all of the rows and add items to the list in reverse order
        if (c != null) {
            if (c.moveToLast()) {
                do {
                    // Set values
                    Message message = new Message();
                    message.setMessageID(c.getString(c.getColumnIndex(KEY_MESSAGE_ID)));
                    message.setOriginalMessageID(c.getString(c.getColumnIndex(KEY_ORIGINAL_MESSAGE_ID)));
                    message.setMessageStateID(c.getInt(c.getColumnIndex(KEY_MESSAGE_STATE_ID)));
                    message.setMessageTypeID(c.getInt(c.getColumnIndex(KEY_MESSAGE_TYPE_ID)));
                    message.setMessageSourceID(c.getInt(c.getColumnIndex(KEY_MESSAGE_SOURCE_ID)));
                    message.setMessageDirectionID(c.getInt(c.getColumnIndex(KEY_MESSAGE_DIRECTION_ID)));
                    message.setIdentifier(c.getString(c.getColumnIndex(KEY_IDENTIFIER)));
                    message.setMessageBody(c.getString(c.getColumnIndex(KEY_MESSAGE_BODY)));
                    message.setMessageRoute(c.getString(c.getColumnIndex(KEY_MESSAGE_ROUTE)));
                    message.setTarget(c.getString(c.getColumnIndex(KEY_TARGET)));
                    message.setUserName(c.getString(c.getColumnIndex(KEY_USER_NAME)));
                    message.setAction(c.getString(c.getColumnIndex(KEY_ACTION)));
                    message.setUserID(c.getInt(c.getColumnIndex(KEY_USER_ID)));
                    message.setCreateTS(c.getString(c.getColumnIndex(KEY_CREATE_TS)));
                    boolean left = c.getInt(c.getColumnIndex(KEY_LEFT)) > 0;
                    message.setLeft(left);
                    messages.add(message);
                } while (c.moveToPrevious());
            }

            c.close();
        }

        return messages;
    }

    // Delete Messages By Filter
    public void deleteAllMessagesByFilter(String _whereClause, long user_id) {
        String deleteQuery = "DELETE FROM " + TABLE_MESSAGE;
        String nextComparisonOperator = " WHERE ";

        if (_whereClause != null && !TextUtils.isEmpty(_whereClause)) {
            deleteQuery = deleteQuery + nextComparisonOperator + _whereClause;
            nextComparisonOperator = " AND ";
        }

        deleteQuery = deleteQuery + nextComparisonOperator + KEY_USER_ID + " = " + String.valueOf(user_id);

        if (Constants.showDebugMessages) Log.d(TAG, deleteQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(deleteQuery, null);

        if (c != null) {
            c.moveToFirst();
            c.close();
        }
    }

    // Get Maximum Primary Key Integer Value
    public int getMessageMaxID() {
        String selectQuery = "SELECT " + KEY_MESSAGE_ID + " as MAX FROM " + TABLE_MESSAGE + " WHERE length(" + KEY_MESSAGE_ID + ") < 8 ORDER BY " + KEY_MESSAGE_ID + " DESC LIMIT 1";

        if (Constants.showDebugMessages) Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        int max = 0;
        if (c != null) {
            c.moveToFirst();
            max = c.getInt(c.getColumnIndex("MAX"));
            c.close();
        }

        return max;
    }


    /**
     ****************************************
     * Cycle Rules Methods
     ****************************************
     */

    // Fetch All Cycle Rules
    public List<CycleRules> getAllCycleRules() {

        List<CycleRules> cycleRules = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CYCLE_RULES;

        if (Constants.showDebugMessages) Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // Loop through all of the rows and add items to the list
        if (c.moveToFirst()) {
            do {
                // Set values
                CycleRules cycleRule = new CycleRules();
                cycleRule.set_cycle_rules_id(c.getInt(c.getColumnIndex(KEY_CYCLE_RULES_ID)));
                cycleRule.set_description(c.getString(c.getColumnIndex(KEY_CYCLE_RULES_DESCRIPTION)));
                cycleRule.set_hours(c.getInt(c.getColumnIndex(KEY_CYCLE_RULES_HOURS)));
                cycleRule.set_days(c.getInt(c.getColumnIndex(KEY_CYCLE_RULES_DAYS)));
                cycleRule.set_last_modified(c.getString(c.getColumnIndex(KEY_CYCLE_RULES_LAST_MODIFIED)));
                cycleRules.add(cycleRule);
            } while (c.moveToNext());
        }

        c.close();

        return cycleRules;
    }


    /**
     * *****************************
     * Vehicle list data
     * *****************************
     */

    /**
     * Inserts new Vehicle element into the db
     *
     * @param vehicle Object
     *
     * @return row id or -1 on error
     */
    public long insertVehicle(Vehicle vehicle) {
        ContentValues values = new ContentValues();
        SQLiteDatabase mDatabase=this.getReadableDatabase();
        if (vehicle.getVehicleId() != 0) {
            values.put(KEY_VEHICLES_ID, vehicle.getVehicleId());
        }
        values.put(KEY_VEHICLES_REGISTRATION, vehicle.getRegistration());
        values.put(KEY_VEHICLES_DESCRIPTION, vehicle.getDescription());
        values.put(KEY_VEHICLES_VIN_NUMBER, vehicle.getVinNumber());
        values.put(KEY_VEHICLES_ODOMETER, vehicle.getOdometer());
        values.put(KEY_VEHICLES_CARTRACK_UNIT, vehicle.getCartrackUnit());
        values.put(KEY_VEHICLES_BLE_NAME,vehicle.getBleName());
        values.put(KEY_VEHICLES_LAST_MODIFIED, vehicle.getLastModified());

        return mDatabase.replace(TABLE_VEHICLES, null, values);
    }

    /**
     ****************************************
     * Events Methods
     ****************************************
     */
    // Fetch Event by Id
    public Events getEventById(long eventId) {

        List<Events> eventsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS + " WHERE " + KEY_EVENTS_EVENT_ID + " = '" + eventId+"'";

        if (Constants.showDebugMessages) Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        // Loop through all of the rows and add items to the list
        if (c.moveToFirst()) {
            do {
                // Set values
                Events event = getEventsFromCursor(c);
                eventsList.add(event);
            } while (c.moveToNext());
        }
        c.close();

        //Check if there is event, then we return it. We know that there will be only one event with supplied id.
        if(eventsList.size()>0){
            return eventsList.get(0);
        }
        return null;
    }

    // Insert Events
    public long insertEvent(Events events) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get values
        ContentValues values = getContentValuesFromEvents(events);

        // Insert Row
        try {
            long key_id = db.insertOrThrow(TABLE_EVENTS, null, values);
            if (key_id > 0) {
                return key_id;
            } else {
                updateEvent(events);
            }
        } catch (SQLiteConstraintException e) {
            if (Constants.showDebugMessages) Log.d(TAG, "Updating Contact: " + e.toString());
            updateEvent(events);
        } finally {
            if (Constants.showDebugMessages)
                Log.d(TAG, "Insert Complete: KEY: " + events.getEventId());
        }
        return 0;
    }

    /**
     * get event by event id and update details
     *
     * @param events
     */
    public void updateEvent(Events events) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get values
        ContentValues values = getContentValuesFromEvents(events);

        // Update the row
        db.update(TABLE_EVENTS, values, KEY_EVENTS_EVENT_ID + " = ? ", new String[]{String.valueOf(events.getEventId())});
    }


    /**
     * get list of all events from events table
     *
     * @return Events List
     */
    public List<Events> getAllEvents() {
        List<Events> events = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS;
        if (Constants.showDebugMessages) Log.d(TAG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // Loop through all of the rows and add items to the list
        if (c.moveToFirst()) {
            do {
                // Set values
                Events event = getEventsFromCursor(c);
                events.add(event);
            } while (c.moveToNext());
        }
        c.close();

        return events;
    }

    /**
     * This function is used to get event list for the day
     * @param seq
     * @return
     */
    public List<Events> getEventsForDay(long seq){
        List<Events> events = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_EVENTS+ " WHERE " + KEY_EVENTS_SEQ + " = '"+seq+"'";
        if (Constants.showDebugMessages) Log.d(TAG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // Loop through all of the rows and add items to the list
        if (c.moveToFirst()) {
            do {
                // Set values
                Events event = getEventsFromCursor(c);
                events.add(event);
            } while (c.moveToNext());
        }
        c.close();

        return events;
    }


    /**
     * get distinct sequence number from events
     *
     * @return Events List
     */
    public List<Long> getEventsDistinctBySequenceNumber() {
        List<Long> seqNos = new ArrayList<>();
        String selectQuery = "SELECT DISTINCT("+KEY_EVENTS_SEQ+") FROM " + TABLE_EVENTS +" ORDER BY " + KEY_EVENTS_SEQ +" DESC ";

        if (Constants.showDebugMessages) Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if(c.getCount() == 0){
            return seqNos;
        }

        // Loop through all of the rows and add items to the list
        if (c.moveToFirst()) {
            do {
                seqNos.add(Long.parseLong(c.getString(c.getColumnIndex(KEY_EVENTS_SEQ))));
            } while (c.moveToNext());
        }

        c.close();

        return seqNos;
    }


    /**
     * get last selected event status id events table
     *
     * @return
     */
    public int getLastSelectedEventStatusId() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE " + KEY_EVENTS_EVENT_END_TIME + " = -1", null);
        c.moveToLast();

        int eventTypeId;

        if (c.getCount() == 0){
            eventTypeId = 1 ;
        }
        else{
            eventTypeId  = Integer.parseInt(c.getString(c.getColumnIndex(KEY_EVENTS_EVENT_TYPE_ID)));
        }

        Log.v(TAG , "Event type Id : " + eventTypeId);

        return eventTypeId;
    }


    /**
     * update specific event and update event status and set end event time to previous running event
     *
     * @param eventStartTime
     */
    public void updateEventStatusById(long eventStartTime) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_EVENTS + " WHERE " + KEY_EVENTS_EVENT_END_TIME + " = -1", null);
        c.moveToLast();

        if(c.getCount() == 0){
            return;
        }

        Events lastEvents = getEventsFromCursor(c);

        // Get values

        lastEvents.setEventEndTime(eventStartTime);

        ContentValues values = getContentValuesFromEvents(lastEvents);

        // Update the row
        db.update(TABLE_EVENTS, values, KEY_EVENTS_EVENT_ID + " = ? ", new String[]{String.valueOf(lastEvents.getEventId())});
    }

    /**
     * get event object from cursor
     * @param c
     * @return
     */
    private Events getEventsFromCursor(Cursor c){
        Events events = new Events();
        events.setEventId(Long.parseLong(c.getString(c.getColumnIndex(KEY_EVENTS_EVENT_ID))));
        events.setSeq(Long.parseLong(c.getString(c.getColumnIndex(KEY_EVENTS_SEQ))));
        events.setDriverId(c.getString(c.getColumnIndex(KEY_EVENTS_DRIVER_ID)));
        events.setVehicleId(Long.parseLong(c.getString(c.getColumnIndex(KEY_EVENTS_VEHICLE_ID))));
        events.setEventTypeId(Integer.parseInt(c.getString(c.getColumnIndex(KEY_EVENTS_EVENT_TYPE_ID))));
        events.setEventType(c.getString(c.getColumnIndex(KEY_EVENTS_EVENT_TYPE)));
        events.setLat(Double.parseDouble(c.getString(c.getColumnIndex(KEY_EVENTS_LAT))));
        events.setLon(Double.parseDouble(c.getString(c.getColumnIndex(KEY_EVENTS_LON))));
        events.setAddress(c.getString(c.getColumnIndex(KEY_EVENTS_ADDRESS)));
        events.setEventNote(c.getString(c.getColumnIndex(KEY_EVENTS_EVENT_NOTE)));
        events.setEventStartTime(Long.parseLong(c.getString(c.getColumnIndex(KEY_EVENTS_EVENT_START_TIME))));
        events.setEventEndTime(Long.parseLong(c.getString(c.getColumnIndex(KEY_EVENTS_EVENT_END_TIME))));
        events.setLatLon(Double.parseDouble(c.getString(c.getColumnIndex(KEY_EVENTS_LAT_LON))));

        return events;
    }

    /**
     * get content values from event object
     * @param events
     * @return
     */
    private ContentValues getContentValuesFromEvents(Events events){
        ContentValues values = new ContentValues();
        values.put(KEY_EVENTS_SEQ, events.getSeq());
        values.put(KEY_EVENTS_VEHICLE_ID, events.getVehicleId());
        values.put(KEY_EVENTS_DRIVER_ID, events.getDriverId());
        values.put(KEY_EVENTS_EVENT_TYPE_ID, events.getEventTypeId());
        values.put(KEY_EVENTS_EVENT_TYPE, events.getEventType());
        values.put(KEY_EVENTS_LAT, events.getLat());
        values.put(KEY_EVENTS_LON, events.getLon());
        values.put(KEY_EVENTS_ADDRESS, events.getAddress());
        values.put(KEY_EVENTS_EVENT_NOTE, events.getEventNote());
        values.put(KEY_EVENTS_EVENT_START_TIME, events.getEventStartTime());
        values.put(KEY_EVENTS_EVENT_END_TIME, events.getEventEndTime());
        values.put(KEY_EVENTS_LAT_LON, events.getLatLon());

        return values;
    }

    /**
     ****************************************
     * Event Type Methods
     ****************************************
     */

    // Fetch Event Name by Id
    public String getEventNameById(int event_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_EVENT_TYPE + " WHERE " + KEY_EVENT_TYPE_ID + " = " + event_id;

        if (Constants.showDebugMessages) Log.d(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        EventType eventType = new EventType();

        if (c != null) {
            c.moveToFirst();

            // Set values
            eventType.setEventTypeId(c.getString(c.getColumnIndex(KEY_EVENT_TYPE_ID)));
            eventType.setEventTypeName(c.getString(c.getColumnIndex(KEY_EVENT_TYPE)));

            c.close();
        }

        return eventType.getEventTypeName();
    }



    /**
     * Get all Location items in the db
     *
     * @return a list of Location items
     */
    public Locations getClosestLocation(double mLat,double mLon) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_LOCATIONS+" ORDER BY ABS("+KEY_LOCATIONS_LAT_LON+" - ABS("+mLat+"+ ("+mLon+"))) ASC LIMIT 1;",null);
        cursor.moveToFirst();
        Locations mLocation = cursorToLocation(cursor);
        cursor.close();
        return mLocation;
    }

    /**
     * Extract Location from a db cursor
     *
     * @param cursor
     * @return Location item in the current cursor
     */
    protected Locations cursorToLocation(Cursor cursor) {
        Locations mLocation = new Locations();

        mLocation.setLocationId(cursor.getLong(cursor.getColumnIndex(KEY_LOCATIONS_ID)));
        mLocation.setDescription(cursor.getString(cursor.getColumnIndex(KEY_LOCATIONS_DESCRIPTION)));
        mLocation.setLat(cursor.getDouble(cursor.getColumnIndex(KEY_LOCATIONS_LAT)));
        mLocation.setLon(cursor.getDouble(cursor.getColumnIndex(KEY_LOCATIONS_LON)));
        mLocation.setLatLon(cursor.getDouble(cursor.getColumnIndex(KEY_LOCATIONS_LAT_LON)));
        return mLocation;
    }






    /**
     * Get all Vehicle items in the db
     *
     * @return a list of Vehicle items
     */
    public List<Vehicle> getVehicles() {
        List<Vehicle> mVehicles = new ArrayList<Vehicle>();
        String selectQuery = "SELECT * FROM " + TABLE_VEHICLES;
        if (Constants.showDebugMessages) Log.d(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Vehicle mVehicle = cursorToVehicle(cursor);
            mVehicles.add(mVehicle);
            cursor.moveToNext();
        }
        cursor.close();
        return mVehicles;
    }

    /**
     * Extract Vehicle from a db cursor
     *
     * @param cursor
     * @return Vehicle item in the current cursor
     */
    protected Vehicle cursorToVehicle(Cursor cursor) {
        Vehicle mVehicle = new Vehicle();

        mVehicle.setVehicleId(cursor.getLong(cursor.getColumnIndex(KEY_VEHICLES_ID)));
        mVehicle.setRegistration(cursor.getString(cursor.getColumnIndex(KEY_VEHICLES_REGISTRATION)));
        mVehicle.setDescription(cursor.getString(cursor.getColumnIndex(KEY_VEHICLES_DESCRIPTION)));
        mVehicle.setVinNumber(cursor.getString(cursor.getColumnIndex(KEY_VEHICLES_VIN_NUMBER)));
        mVehicle.setOdometer(cursor.getString(cursor.getColumnIndex(KEY_VEHICLES_ODOMETER)));
        mVehicle.setCartrackUnit(cursor.getInt(cursor.getColumnIndex(KEY_VEHICLES_CARTRACK_UNIT)));
        mVehicle.setBleName(cursor.getString(cursor.getColumnIndex(KEY_VEHICLES_BLE_NAME)));
        mVehicle.setLastModified(cursor.getLong(cursor.getColumnIndex(KEY_VEHICLES_LAST_MODIFIED)));
        return mVehicle;
    }

    /**
     * get log day event list according to sequence number in descending order
     *
     * @return logDayEvents
     */
    public List<LogDayEvent> getLogDayEventsList(){
        List<LogDayEvent> logDayEvents = new ArrayList<>();

        for( Long seqNo : getEventsDistinctBySequenceNumber()){

            List<Events> eventses = getEventsForDay(seqNo);

            LogDayEvent logDayEvent = new LogDayEvent();

            logDayEvent.setSeq(seqNo);

            logDayEvent.setDate(Utils.getConvertedTime(Constants.LOGS_DATE_FORMAT , seqNo));

            logDayEvent.getEventsList().addAll(eventses);

            logDayEvent.setDayHours(Utils.getDayHoursFromEventList(eventses));

            logDayEvents.add(logDayEvent);
        }

        return logDayEvents;
    }

    /**
     *
     * @param id primary key
     * @return
     */
    /*public Vehicle getVehicle(long id) {

        SQLiteDatabase mDatabase=mAbstractDbAdapter.getReadableDatabase();
        Cursor cursor = mDatabase.query(TABLE, allColumns, KEY_ID + "=?", new String[] {
                String.valueOf(id)
        }, null, null, null, null);
        Vehicle mVehicle = null;
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                mVehicle = cursorToVehicle(cursor);
            }
            cursor.close();
        }

        return mVehicle;
    }

    public void deleteVehicle(long id) {
        SQLiteDatabase mDatabase=mAbstractDbAdapter.getWritableDatabase();
        mDatabase.delete(TABLE, KEY_ID + "=?", new String[] {
                String.valueOf(id)
        });
    }

    public void deleteVehicle(Vehicle Vehicle) {
        deleteVehicle(Vehicle.getVehicleId());
    }

    public int updateVehicle(Vehicle Vehicle) {
        ContentValues values = new ContentValues();
        SQLiteDatabase mDatabase=mAbstractDbAdapter.getWritableDatabase();

        values.put(KEY_REGISTRATION, Vehicle.getRegistration());
        values.put(KEY_DESCRIPTION, Vehicle.getDescription());
        values.put(KEY_VIN_NUMBER, Vehicle.getVinNumber());
        values.put(KEY_ODOMETER, Vehicle.getOdometer());
        values.put(KEY_CARTRACK_UNIT, Vehicle.getCartrack_unit());
        values.put(KEY_BLE_NAME, Vehicle.getBleName());
        values.put(KEY_LAST_MODIFIED, DateUtils.getDateTimeNow(DateUtils.DATE_TIME_FORMAT));

        return mDatabase.update(TABLE, values, KEY_ID + "=?", new String[] {
                String.valueOf(Vehicle.getVehicleId())
        });
    }*/
}
