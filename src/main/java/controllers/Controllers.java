package controllers;

public interface Controllers {
    NotificationController NOTIFICATION_CONTROLLER = new NotificationController();
    SettingController SETTING_CONTROLLER = new SettingController();
    UserController USER_CONTROLLER = new UserController();
    TweetController TWEET_CONTROLLER = new TweetController();
    FactionsController FACTIONS_CONTROLLER = new FactionsController();
    MessageController MESSAGE_CONTROLLER = new MessageController();
    ChatController CHAT_CONTROLLER = new ChatController();
    AuthController AUTH_CONTROLLER = new AuthController();
    RegisterManager REGISTER_MANAGER = new RegisterManager();
}
