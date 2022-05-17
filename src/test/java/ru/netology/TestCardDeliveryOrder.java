package ru.netology;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class TestCardDeliveryOrder {
    @Test
    void shouldPlaceCardOrder() {
        open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue("Казань");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7); //how about a week from today?
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String formattedSampleDate = simpleDateFormat.format(calendar.getTime());

        // [facepalm] цирк с конями
        while($("[data-test-id='date'] input").val().length() > 0) {
            $("[data-test-id='date'] input").sendKeys(Keys.BACK_SPACE);
        }

        $("[data-test-id='date'] input").setValue(formattedSampleDate);
        $("[data-test-id='name'] input").setValue("Тояма Токанава Товыбоина");
        $("[data-test-id='phone'] input").setValue("+79012345678");

        $("[data-test-id='agreement'] [role='presentation']").click();

        $$(".button_theme_alfa-on-white").find(exactText("Забронировать")).click();

        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
    }

}
