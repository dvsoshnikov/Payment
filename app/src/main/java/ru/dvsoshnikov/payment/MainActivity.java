package ru.dvsoshnikov.payment;

import static ru.yoomoney.sdk.kassa.payments.Checkout.createTokenizationResult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import android.os.Bundle;
import android.widget.Toast;
import ru.yoomoney.sdk.kassa.payments.Checkout;
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.Amount;
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.GooglePayParameters;
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.MockConfiguration;
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentMethodType;
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.PaymentParameters;
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.SavePaymentMethod;
import ru.yoomoney.sdk.kassa.payments.checkoutParameters.TestParameters;

public class MainActivity extends AppCompatActivity {

    static Integer REQUEST_CODE_TOKENIZE = 1;

    //private TextView  helloTv;
    //private Timer     mTimer;
    //private TimerTask mMyTimerTask;
    static Product product;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_TOKENIZE) {
            switch (resultCode) {
                case RESULT_OK:
                    // successful tokenization
                    showToken (data);

                    break;
                case RESULT_CANCELED:
                    // user canceled tokenization
                    showToken (data);
                    break;
            }
        }
    }

    public MainActivity() throws UnknownHostException {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button helloBtn = findViewById(R.id.productButton1);
        Button helloBtn1 = findViewById(R.id.productButton2);
        Button helloBtn2 = findViewById(R.id.productButton3);

        helloBtn.setOnClickListener(onClickListener);
        helloBtn1.setOnClickListener(onClickListener);
        helloBtn2.setOnClickListener(onClickListener);
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener onClickListener = v -> {
        switch (v.getId()) {

            case R.id.productButton1:

                BigDecimal coast1 = new BigDecimal("101.25");
                product = new Product(coast1,"Продукт 1", "Кросовки");
                showDialog (product);

                    break;

            case R.id.productButton2:

                BigDecimal coast2 = new BigDecimal("200");
                product = new Product(coast2,"Продукт 2", "Кепка спортивная");
                //onFragmentSendDataListener.onSendData(product2);
                showDialog (product);

                break;

            case R.id.productButton3:

                BigDecimal coast3 = new BigDecimal("803.7");
                product = new Product(coast3,"Продукт 3", "Майка");
               // onFragmentSendDataListener.onSendData(product3);
                showDialog (product);
                break;
        }
    };
    void startTokenize(Product product) {
        Set<PaymentMethodType> paymentMethodTypes = new HashSet<PaymentMethodType>(){{
            add(PaymentMethodType.SBERBANK); // выбранный способ оплаты - SberPay
            add(PaymentMethodType.YOO_MONEY); // выбранный способ оплаты - ЮMoney
            add(PaymentMethodType.BANK_CARD); // выбранный способ оплаты - Банковская карта
        }};
        PaymentParameters paymentParameters = new PaymentParameters(
                new Amount(product.getCoast(), Currency.getInstance("RUB")),
                product.getProductName(),
                product.getProductDescription(),
                "live_thisKeyIsNotReal", // ключ для клиентских приложений из личного кабинета ЮKassa
                "12345", // идентификатор магазина ЮKassa
                SavePaymentMethod.OFF, // флаг выключенного сохранения платежного метода
                paymentMethodTypes, // передан весь список доступных методов оплаты
                "gatewayId", // gatewayId магазина для платежей Google Pay (необходим в случае, если в способах оплаты есть Google Pay)
                "https://custom.redirect.url", // url страницы (поддерживается только https), на которую надо вернуться после прохождения 3ds. Должен использоваться только при при использовании своего Activity для 3ds url.
                "+79041234567", // номер телефона пользователя. Используется для автозаполнения поля при оплате через SberPay. Поддерживаемый формат данных: "+7XXXXXXXXXX".
                new GooglePayParameters(), // настройки для токенизации через GooglePay,
                "example_authCenterClientId", // идентификатор, полученный при регистрации приложения на сайте https://yookassa.ru
                "uniqueCustomerId" // уникальный идентификатор покупателя в вашей системе
        );

        MockConfiguration mockConfiguration = new MockConfiguration(
                true, // completeWithError - возвращать всегда при токенизации ошибку
                true, // paymentAuthPassed - авторизован пользователь или нет, для оплаты кошельком
                2,// linkedCardsCount - количество карт, привязанных к кошельку пользователя;
                new Amount(BigDecimal.ONE, Currency.getInstance("RUB")) // serviceFee - комиссия, которая будет отображена на экране выбранного способа оплаты
        );
        TestParameters testParameters = new TestParameters(
                true, // showLogs - включить/выключить отображение логов sdk
                true, // googlePayTestEnvironment - какую, тестовую или боевую, среду нужно использовать для Google Pay, подробнее можно почитать тут: https://developers.google.com/pay/api/android/guides/test-and-deploy/integration-checklist
                mockConfiguration
        );
        Intent intent = Checkout.createTokenizeIntent(this, paymentParameters, testParameters);
        startActivityForResult(intent, REQUEST_CODE_TOKENIZE);
    }

    void showToken(Intent data) {
        if (data != null) {
            String token = createTokenizationResult(data).getPaymentToken();
            Toast.makeText(
                    this,
                    String.format(Locale.getDefault(), "Токен получен успешно", token),
                    Toast.LENGTH_LONG
            ).show();
        } else {
            showError();
        }
    }

    void showError() {
        Toast.makeText(this, "Токен не получен", Toast.LENGTH_SHORT).show();
    }

    void showDialog (Product product) {

        Bundle bundle = new Bundle();
        bundle.putFloat("product_coast",product.getCoast().floatValue());
        bundle.putString("product_name",product.getProductName());
        bundle.putString("product_description",product.getProductDescription());

        DialogPriceFragment dialogPriceFragment = new DialogPriceFragment();
        dialogPriceFragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        dialogPriceFragment.show(transaction, "dialog");
    }

    public static class DialogPriceFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Потверждение суммы покупки");  // заголовок
            builder.setMessage("Сумма покупки: " + product.getCoast()); // сообщение
            builder.setPositiveButton("Да", (dialog, id) -> (
                    (MainActivity) requireActivity()).startTokenize(product));
            builder.setNegativeButton("Нет", (dialog, id) -> dialog.cancel());
            builder.setCancelable(true);
            return builder.create();
        }

    }



}



