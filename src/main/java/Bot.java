import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    String qwe = "На федеральной территории Сириус находятся: \n" +
            "/_University   <_ Университет >\n" +
            "/_Licey    <_ Лицей \"Сириус\" >\n" +
            "/_EC   <_ Образовательный центр Сириус \"Сириус\" >\n" +
            "/_Arena    <_ Адлер-арена >\n" +
            "/_Shayba   <_ Шайба (ледовая арена) >\n";
    String University = "Университет ";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            setButton(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if(message !=null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    sendMsg(message, "Добро пожаловать. Вас приветствует чат бот экскурсовод");
                    break;
                case "Покажи карту ФТ Сириус":
                    sendPhotoMsg(message, "Map");
                    break;
                case "Где я нахожусь?":
                    break;
                case "Что находится на ФТ Сириус":
                    sendMsg(message, qwe);
                    break;
                case "/Licey":
                    sendPhotoMsg(message, "Licey");
                    break;
                case "/University":
                    sendPhotoMsg(message, "University");
                    break;
                case "/EC":
                    sendPhotoMsg(message, "EC");
                    break;
                case "/Arena":
                    sendPhotoMsg(message, "Arena");
                    break;
                case "/Shayba":
                    sendPhotoMsg(message, "Shayba");
                    break;
                case "Узнать погоду в Сириусе":
                    try {
                        sendMsg(message, Weather.getWeather("Адлер", model));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
            }
        }
    }

    public void setButton(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);


        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("Покажи карту ФТ Сириус"));
        keyboardFirstRow.add(new KeyboardButton("Что находится на ФТ Сириус"));

        keyboardSecondRow.add(new KeyboardButton("Где я нахожусь?").setRequestLocation(true));
        keyboardSecondRow.add(new KeyboardButton("Узнать погоду в Сириусе"));

        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    private void sendPhotoMsg(Message message, String photo) {
        SendPhoto msg = new SendPhoto().setChatId(message.getChatId());
        switch (photo) {
            case "Map": msg.setPhoto("http://prnt.sc/11z5y8k"); break;
            case "Licey": msg.setPhoto("https://sochinews.io/wp-content/uploads/2021/01/70d06b845bfd2ddddbe530141769ec5d.jpg"); break;
            case "University": msg.setPhoto("https://intc-sirius.ru/img/news/9.jpg").setCaption(University); break;
            case "EC": msg.setPhoto("https://img-fotki.yandex.ru/get/361493/163672223.1/0_1c9a1f_6e683e6_orig.jpg"); break;
            case "Arena": msg.setPhoto("https://sochigram.com/sites/sochigram.com/files/styles/adaptive/public/images/o/10378/adler-arena_56a270e4.jpg"); break;
            case "Shayba": msg.setPhoto("https://stillmedab.olympic.org/media/Images/OlympicOrg/IOC/What_We_Do/Olympic_Legacy/Legacy_Stories/2019/11/12/Sochi-2014-The-Shayba-Arena.jpg"); break;
        }
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "BotToSirius";
    }

    @Override
    public String getBotToken() {
        return "1776904675:AAGsOWSUPd5dxdv_hDIvF_tGy3RxDkO61Lo";
    }
}
