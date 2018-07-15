package com.chookstogo.orderingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/* Procedure:
 * 1.) THE ADMIN WILL SET PENDING ORDERS TO DELIVERED, THE ACTIVITY MUST HAVE THE FOLLOWING TEXT FIELDS:
 * - ORDER ID (NON-EDITABLE)
 * - ORDER DATE (NON-EDITABLE)
 * - ORDER STATUS (EDITABLE)
 * - PRODUCT NAME (NON-EDITABLE)
 * - QUANTITY PER PRODUCT (NON-EDITABLE)
 * - CUSTOMER'S FULL NAME (NON-EDITABLE)
 * - SAVE BUTTON
 * - CANCEL BUTTON
 *
 * 2) CONSTRAINTS
 *  2.1) IF THE CUSTOMER MARKED 0 IN THE QUANTITY FIELD:
 *      - AUTOMATICALLY THE PRODUCT WILL BE DELETED (?)
 *  2.2) IF THE CUSTOMER DELETED THE PRODUCT
 *      - DELETE FIRST THE PRODUCT
 *      - FOLLOWED BY PROMPT FOR SUCCESS
 *      - HEAD BACK TO __________ ACTIVITY
 * */
public class UserReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report);
    }
}
