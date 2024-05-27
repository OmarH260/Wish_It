package com.example.wishit;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

public class PaymentActivity extends AppCompatActivity {

    PaymentSheet paymentSheet;
    private String PUBLISHABLE_KEY = "pk_live_51Oc7KTJOFcueWEXiprglF06IgeL40if39h5BdscKRJki1eLHKpd9KrTGSLYPVLKYvQM6Rq7vp7Vk2eaZ46jyIaJk00rL7xErIu";
    private String SECRET_KEY = "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";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentSheet = new PaymentSheet(this, PaymentSheetResult ->{

        });
    }
}