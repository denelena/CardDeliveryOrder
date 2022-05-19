package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class TestCardDeliveryOrder {
    @Test
    void shouldPlaceCardOrder() {
        open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue("Казань");

        String planningDate = generateDate(4);

        // методом научного тыка было найдено, что поле date надо сначала очистить
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        //а вот сработает ли оно в каждой породе линукса?  никакой гарантии...

        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Тояма Токанава Товыбоина");
        $("[data-test-id='phone'] input").setValue("+79012345678");

        $("[data-test-id='agreement'] [role='presentation']").click();

        $$(".button_theme_alfa-on-white").find(exactText("Забронировать")).click();

        $(withText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
